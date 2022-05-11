package net.sopod.soim.common.util;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.json.JsonReadFeature;
import com.fasterxml.jackson.core.json.PackageVersion;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.type.CollectionLikeType;
import com.fasterxml.jackson.databind.type.MapType;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalTimeSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 *
 * @author tmy
 * @date 2022-03-04 16:37
 */
public class Jackson {

	private static final Logger log = LoggerFactory.getLogger(Jackson.class);

	private static final String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
	private static final String DATE_FORMAT = "yyyy-MM-dd";
	private static final String TIME_FORMAT = "HH:mm:ss";

	private static volatile Jackson JSON_INSTANCE;
	private static volatile Jackson YAML_INSTANCE;
	private static volatile Jackson XML_INSTANCE;
	private static volatile Jackson MSGPACK_INSTANCE;

	private final ObjectMapper objectMapper;

	private Jackson(ObjectMapper objectMapper) {
		this.objectMapper = objectMapper;

	}

	private static void getFactoryInstance(String clazz, Supplier<Boolean> predicate, Consumer<Jackson> setter) {
		getFactoryInstance(clazz, predicate, mapper->{}, setter);
	}

	private static void getFactoryInstance(String clazz, Supplier<Boolean> predicate, Consumer<ObjectMapper> setting, Consumer<Jackson> setter) {
		if (predicate.get()) {
			synchronized (Jackson.class) {
				if (predicate.get()) {
					try {
						Class<?> factoryClazz = Class.forName(clazz);
						Object factoryInstance = factoryClazz.getDeclaredConstructor().newInstance();
						ObjectMapper mapper;
						if (factoryInstance instanceof ObjectMapper) {
							mapper = new JacksonObjectMapper((ObjectMapper) factoryInstance);
						} else {
							mapper = new JacksonObjectMapper((JsonFactory) factoryInstance);
						}
						setter.accept(new Jackson(mapper));
					} catch (ReflectiveOperationException e) {
						throw new RuntimeException(clazz, e);
					}
				}
			}
		}
	}

	private static class JacksonObjectMapper extends ObjectMapper {

		private static final long serialVersionUID = -1848248131995072046L;

		public JacksonObjectMapper(ObjectMapper src) {
			super(src);
			init();
		}

		public JacksonObjectMapper(JsonFactory factory) {
			super(factory);
			init();
		}

		@Override
		public ObjectMapper copy() {
			return new JacksonObjectMapper(this);
		}


		private void init() {
			super.setLocale(Locale.CHINA);
			super.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
			super.setTimeZone(TimeZone.getTimeZone(ZoneId.systemDefault()));
			super.setDateFormat(new SimpleDateFormat(DATE_TIME_FORMAT, Locale.CHINA));
			super.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);
			super.configure(JsonReadFeature.ALLOW_UNESCAPED_CONTROL_CHARS.mappedFeature(), true);
			super.configure(JsonReadFeature.ALLOW_BACKSLASH_ESCAPING_ANY_CHARACTER.mappedFeature(), true);

			super.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
			super.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
			super.configure(JsonReadFeature.ALLOW_SINGLE_QUOTES.mappedFeature(), true);
			super.getDeserializationConfig().withoutFeatures(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

			super.findAndRegisterModules();
			// 放到jackson-datatype-jsr310后注册，不然被覆盖
			super.registerModule(new MyJavaTimeModule());
		}

	}

	/**
     * jsr310 默认 LocalDateTime 正反序列化格式 DateTimeFormatter.ISO_LOCAL_DATE_TIME
	 */
	private static class MyJavaTimeModule extends SimpleModule {
		public MyJavaTimeModule() {
			super(PackageVersion.VERSION);
			DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(DATE_TIME_FORMAT);
			DateTimeFormatter DateFormatter = DateTimeFormatter.ofPattern(DATE_FORMAT);
			DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern(TIME_FORMAT);
			this.addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer(dateTimeFormatter));
			this.addDeserializer(LocalDate.class, new LocalDateDeserializer(DateFormatter));
			this.addDeserializer(LocalTime.class, new LocalTimeDeserializer(timeFormatter));
			this.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer(dateTimeFormatter));
			this.addSerializer(LocalDate.class, new LocalDateSerializer(DateFormatter));
			this.addSerializer(LocalTime.class, new LocalTimeSerializer(timeFormatter));
		}
	}

	public static Jackson json() {
		getFactoryInstance("com.fasterxml.jackson.core.JsonFactory",
			() -> JSON_INSTANCE == null,
			jackson -> JSON_INSTANCE = jackson);
		return JSON_INSTANCE;
	}

	/**
	 * 依赖 jackson-dataformat-yaml
	 */
	public static Jackson yaml() {
		getFactoryInstance("com.fasterxml.jackson.dataformat.yaml.YAMLFactory",
			() -> YAML_INSTANCE == null,
			jackson -> YAML_INSTANCE = jackson);
		return YAML_INSTANCE;
	}

	/**
	 * 依赖 jackson-dataformat-xml
	 */
	public static Jackson xml() {
		getFactoryInstance("com.fasterxml.jackson.dataformat.xml.XmlMapper",
			() -> XML_INSTANCE == null,
			mapper -> {
				// 设置返回 null 转为 空字符串""
				mapper.getSerializerProvider().setNullValueSerializer(new JsonSerializer<Object>() {
					@Override
					public void serialize(Object paramT, JsonGenerator paramJsonGenerator,
										  SerializerProvider paramSerializerProvider) throws IOException {
						paramJsonGenerator.writeString("");
					}
				});
			},
			jackson -> XML_INSTANCE = jackson);
		return XML_INSTANCE;
	}

	/**
	 * 序列化的时候，不写入字段名字，会按字段顺序写入值
	 * 如果在bean中要增加新字段，请务必保证新字段加在字段序的最后！
	 * 对象新增字段，放在中间位置，会导致序列化失败！
	 */
	public static Jackson msgpack() {
		getFactoryInstance("org.msgpack.jackson.dataformat.MessagePackFactory",
			() -> MSGPACK_INSTANCE == null,
			jackson -> MSGPACK_INSTANCE = jackson
		);
		return MSGPACK_INSTANCE;
	}

	public boolean canSerialize(Class<?> clazz) {
		return objectMapper.canSerialize(clazz);
	}

	public <T> T deserialize(String content, Class<T> valueType) {
		try {
			return objectMapper.readValue(content, valueType);
		} catch (JsonProcessingException e) {
			log.error(e.getMessage(), e);
			return null;
		}
	}

	public <T> String serialize(T value) {
		try {
			return objectMapper.writeValueAsString(value);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			log.error(e.getMessage(), e);
			return null;
		}
	}

	public <T> byte[] serializeBytes(T value) {
		try {
			return objectMapper.writeValueAsBytes(value);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			return null;
		}
	}

	public <T> T deserializeBytes(byte[] bytes, Class<T> valueType) {
		try {
			return objectMapper.readValue(bytes, valueType);
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	public <T> T toPojo(Map<String, Object> fromValue, Class<T> toValueType) {
		return objectMapper.convertValue(fromValue, toValueType);
	}

	public <K, V> Map<K, V> readMap(String content, Class<K> keyClass, Class<V> valueClass) {
		if (content == null || content.length() == 0) {
			return Collections.emptyMap();
		} else {
			try {
				return objectMapper.readValue(content, getMapType(keyClass, valueClass));
			} catch (IOException e) {
				e.printStackTrace();
				return null;
			}
		}
	}

	public <K, V> Map<K, V> readMap(byte[] content, Class<K> keyClass, Class<V> valueClass) {
		if (content == null || content.length == 0) {
			return Collections.emptyMap();
		} else {
			try {
				return objectMapper.readValue(content, getMapType(keyClass, valueClass));
			} catch (IOException e) {
				e.printStackTrace();
				return null;
			}
		}
	}

	private MapType getMapType(Class<?> keyClass, Class<?> valueClass) {
		return objectMapper.getTypeFactory().constructMapType(Map.class, keyClass, valueClass);
	}


	public List<Map<String, Object>> readListMap(String content) {
		return readListMap(content, Object.class);
	}

	public <V> List<Map<String, V>> readListMap(String content, Class<V> valueType) {
		if (content == null || content.length() == 0) {
			return Collections.emptyList();
		} else {
			try {
				return objectMapper.readValue(content, objectMapper.getTypeFactory()
						.constructCollectionLikeType(List.class, getMapType(String.class, valueType)));
			} catch (IOException e) {
				e.printStackTrace();
				return null;
			}
		}
	}


	public <T> List<T> readList(byte[] content, Class<T> elementClass) {
		if (content == null || content.length == 0) {
			return Collections.emptyList();
		} else {
			try {
				return objectMapper.readValue(content, getListType(elementClass));
			} catch (IOException e) {
				e.printStackTrace();
				return null;
			}
		}
	}

	public <T> List<T> readList(String content, Class<T> elementClass) {
		if (content == null || content.length() == 0) {
			return Collections.emptyList();
		} else {
			try {
				return objectMapper.readValue(content, getListType(elementClass));
			} catch (IOException e) {
				e.printStackTrace();
				return null;
			}
		}
	}

	private CollectionLikeType getListType(Class<?> elementClass) {
		return objectMapper.getTypeFactory().constructCollectionLikeType(List.class, elementClass);
	}

	public ObjectMapper getObjectMapper() {
		return objectMapper;
	}

	public TypeFactory getTypeFactory() {
		return objectMapper.getTypeFactory();
	}

	/**
	 * @param content 序列化内容
	 * @param type 方法参数类型等可能包含泛型类型
	 * @return 解析结果
	 */
	public Object readWithType(String content, Type type) {
		if (content == null || content.length() == 0) {
			return null;
		} else {
			JavaType javaType = getJavaType(type);
			try {
				return objectMapper.readValue(content, javaType);
			} catch (JsonProcessingException e) {
				e.printStackTrace();
				return null;
			}
		}
	}

	public Object readWithType(byte[] bytes, Type type) {
		if (bytes == null || bytes.length == 0) {
			return null;
		} else {
			JavaType javaType = getJavaType(type);
			try {
				return objectMapper.readValue(bytes, javaType);
			} catch (IOException e) {
				e.printStackTrace();
				return null;
			}
		}
	}

	/**
	 * 解析任意层数泛型对象
	 * @param type 泛型类型
	 * @return jackson JavaType
	 */
	private JavaType getJavaType(Type type) {
        //判断是否带有泛型
        if (type instanceof ParameterizedType) {
            Type[] actualTypeArguments = ((ParameterizedType) type).getActualTypeArguments();
            System.out.println(Arrays.asList(actualTypeArguments));
            //获取泛型类型
            Class<?> rowClass = (Class<?>) ((ParameterizedType) type).getRawType();

            JavaType[] javaTypes = new JavaType[actualTypeArguments.length];

            for (int i = 0; i < actualTypeArguments.length; i++) {
                //泛型也可能带有泛型，递归获取
                javaTypes[i] = getJavaType(actualTypeArguments[i]);
            }
            return objectMapper.getTypeFactory().constructParametricType(rowClass, javaTypes);
        } else {
            //简单类型直接用该类构建JavaType
            Class<?> cla = (Class<?>) type;
            // TypeFactory.defaultInstance()
            return objectMapper.getTypeFactory().constructParametricType(cla, new JavaType[0]);
        }
    }

}
