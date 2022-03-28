package net.sopod.soim.common.util;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.json.JsonReadFeature;
import com.fasterxml.jackson.core.json.PackageVersion;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalTimeSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;
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
			jackson -> XML_INSTANCE = jackson);
		return XML_INSTANCE;
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

	public <T> T toPojo(Map<String, Object> fromValue, Class<T> toValueType) {
		return objectMapper.convertValue(fromValue, toValueType);
	}

}
