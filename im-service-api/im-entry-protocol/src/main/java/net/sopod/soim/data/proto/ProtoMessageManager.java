package net.sopod.soim.data.proto;

import com.google.protobuf.MessageLite;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Nullable;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Method;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * MessageHolder
 *
 * @author tmy
 * @date 2022-04-08 18:02
 */
public class ProtoMessageManager {

    private static final Logger logger = LoggerFactory.getLogger(ProtoMessageManager.class);

    public static final String protoSerialNoTableName = "protoSerialNoTable.txt";

    private static final Map<Integer, String> serialNoTypeMap = new HashMap<>(32);
    private static final Map<String, Integer> typeSerialNoMap = new HashMap<>(32);
    private static Map<String, MessageLite> typeNameClazzMap = new ConcurrentHashMap<>();

    static {
        try {
            init();
        } catch (IOException e) {
            throw new IllegalStateException("protobuf序列号列表初始化失败", e);
        }
    }

    private static void init() throws IOException {
        InputStream in = ProtoMessageManager.class.getClassLoader().getResourceAsStream(protoSerialNoTableName);
        if (in == null) {
            throw new IllegalStateException("classpath:" + protoSerialNoTableName + " 文件未找到");
        }
        InputStreamReader reader = new InputStreamReader(in, StandardCharsets.UTF_8);
        BufferedReader bufReader = new BufferedReader(reader);
        String line;
        while (null != (line = bufReader.readLine())) {
            int idx = line.indexOf('=');
            String clazz = line.substring(0, idx);
            Integer num = Integer.valueOf(line.substring(idx + 1));
            serialNoTypeMap.put(num, clazz);
            typeSerialNoMap.put(clazz, num);
        }
        bufReader.close();
        reader.close();
        in.close();
    }

    @Nullable
    public static MessageLite getDefaultInstance(Integer serialNo) {
        String clazz = serialNoTypeMap.get(serialNo);
        if (clazz == null) {
            logger.error("protoMsgDict serialNo proto class not found: {}", serialNo);
            return null;
        }
        return typeNameClazzMap.computeIfAbsent(clazz, c -> {
            try {
                Class<?> type = Class.forName(c);
                if (!MessageLite.class.isAssignableFrom(type)) {
                    return null;
                }
                return getDefaultInstance((Class<? extends MessageLite>) type);
            } catch (ClassNotFoundException e) {
                logger.error("protoMsgDict proto class not found: {}, {}", serialNo, c);
                return null;
            }
        });
    }

    @Nullable
    public static Integer getSerialNo(Class<? extends MessageLite> type) {
        return typeSerialNoMap.get(type.getName());
    }

    private static MessageLite getDefaultInstance(Class<? extends MessageLite> clazz) {
        try {
            Method getDefaultInstance = clazz.getDeclaredMethod("getDefaultInstance");
            return (MessageLite)getDefaultInstance.invoke(null);
        } catch (Exception e) {
            System.out.println("get instance exception:" + clazz.getName());
        }
        return null;
    }

}
