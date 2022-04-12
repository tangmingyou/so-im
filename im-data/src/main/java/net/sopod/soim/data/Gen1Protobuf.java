package net.sopod.soim.data;

import net.sopod.soim.common.util.ExecUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;

public class Gen1Protobuf {

    private static final Logger logger = LoggerFactory.getLogger(Gen1Protobuf.class);

    private static final String protocPath = "./bin/protoc-3.19.4/bin/protoc";

    private static final String javaOutPack = "im-data/src/main/java";

    private static final String protoFileDir = "im-data/src/main/resources/protobuf"; // /*.proto

    /** !!生成新class删除.java文件目录 */
    private static final String javaClassDir = "im-data/src/main/java/net/sopod/soim/data/msg";

    private static void genProtobufMsgClasses(File protoDir) {
        String command = protocPath + " --java_out=" + javaOutPack + " " + protoDir.getPath() + File.separator + "*.proto";
        logger.info("gen command: {}", command);
        ExecUtil.exec(command, new String[0], new File("."));

        // 递归子目录
        File[] files = protoDir.listFiles();
        if (files != null && files.length > 0) {
            for (File file : files) {
                if (file.isDirectory()) {
                    genProtobufMsgClasses(file);
                }
            }
        }
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
        // 删除旧的编译文件
        removeOldClass(new File(javaClassDir));
        // 从.proto生成.java
        genProtobufMsgClasses(new File(protoFileDir));
    }

}
