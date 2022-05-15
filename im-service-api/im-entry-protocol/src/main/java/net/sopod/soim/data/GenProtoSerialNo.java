package net.sopod.soim.data;

import com.google.protobuf.GeneratedMessageV3;
import net.sopod.soim.data.proto.ProtoMessageManager;
import org.reflections.Reflections;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Set;

/**
 * GenMessageSerialNo
 * TODO DB存储，消息版本号唯一
 * client 连接时从服务端获取
 * TODO 消息类型分组 (user, group, ...) 避免大数据量传输
 *
 * @author tmy
 * @date 2022-04-12 11:38
 */
public class GenProtoSerialNo {

    private static final String msgSerialNoTableFilePath = "./im-service-api/im-entry-protocol/src/main/resources/" + ProtoMessageManager.protoSerialNoTableName;

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

    public static void main(String[] args) throws IOException {
        // 将 protobuf java class 编码 id
        StringBuilder msgTable = genProtoMessageTable();
        // 生成 message 表格, TODO 时间+md5 版本号
        FileWriter fileWriter = new FileWriter(new File(msgSerialNoTableFilePath));
        fileWriter.write(msgTable.toString());
        fileWriter.flush();
        fileWriter.close();
    }


}
