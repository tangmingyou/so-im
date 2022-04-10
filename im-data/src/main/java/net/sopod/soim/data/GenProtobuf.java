package net.sopod.soim.data;

import com.google.protobuf.GeneratedMessageV3;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Set;

import net.sopod.soim.common.util.ExecUtil;
import net.sopod.soim.data.proto.ProtoMessageManager;
import org.reflections.Reflections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * TODO DB存储，消息版本号唯一
 * client 连接时从服务端获取
 * TODO 消息类型分组 (user, group, ...) 避免大数据量传输
 */
public class GenProtobuf {

    private static final Logger logger = LoggerFactory.getLogger(GenProtobuf.class);

    private static final String protocPath = "./bin/protoc-3.19.4/bin/protoc";

    private static final String javaOutPack = "im-data/src/main/java";

    private static final String protoFileDir = "im-data/src/main/resources/protobuf/*.proto";

    /** !!生成新class删除.java文件目录 */
    private static final String javaClassDir = "im-data/src/main/java/net/sopod/soim/data/msg";

    private static final String msgSerialNoTableFilePath = "./im-data/src/main/resources/" + ProtoMessageManager.protoSerialNoTableName;

    private static void genProtobufMsgClasses() {
        removeOldClass(new File(javaClassDir));
        String command = protocPath + " --java_out=" + javaOutPack + " " + protoFileDir;
        logger.info("gen command: {}", command);
        ExecUtil.exec(command, new String[0], new File("."));
    }

    /** 将 protobuf java class 编码 id */
    private static StringBuilder genProtoMessageTable() {
        String pack = "net.sopod.soim.data.msg";
        Reflections collect = new Reflections(pack);
        Set<Class<? extends GeneratedMessageV3>> types = collect.getSubTypesOf(GeneratedMessageV3.class);
        // TreeSet<String> classNames = new TreeSet<>();
        StringBuilder msgTableBuilder = new StringBuilder();
        // 已存在不修改, TODO classDict id生成
        int idx = 10000;
        for (Class<? extends GeneratedMessageV3> type : types) {
            // classNames.add(type.getName());
            msgTableBuilder.append(type.getName()).append('=').append(idx++).append("\n");
        }
        return msgTableBuilder;
    }

    private static void removeOldClass(File pack) {
        if (!pack.exists() || !pack.isDirectory()) {
            return;
        }
        File[] files = pack.listFiles();
        if (files == null || files.length == 0) {
            return;
        }
        for (File childFile : files) {
            if (childFile.isDirectory()) {
                removeOldClass(childFile);
            } else if (childFile.getName().endsWith(".java")) {
                childFile.delete();
                logger.info("delete old class: {}", childFile.getAbsolutePath());
            }
        }
    }

    public static void main(String[] args) throws IOException {
        // 从.proto生成.java
        genProtobufMsgClasses();
        // 将 protobuf java class 编码 id
        StringBuilder msgTable = genProtoMessageTable();
        // 生成 message 表格, TODO 时间+md5 版本号
        FileWriter fileWriter = new FileWriter(new File(msgSerialNoTableFilePath));
        fileWriter.write(msgTable.toString());
        fileWriter.flush();
        fileWriter.close();
    }

}
