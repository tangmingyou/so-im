package net.sopod.soim.router.datasync.server.codec;

import net.sopod.soim.common.util.Jackson;
import org.xerial.snappy.Snappy;

import java.io.IOException;

/**
 * CodecUtil
 *
 * @author tmy
 * @date 2022-05-08 16:54
 */
public class CodecUtil {

    public static byte[] encode(Object data) {
        return Jackson.msgpack().serializeBytes(data);
    }

    public static byte[] compress(byte[] bytes) {
        try {
            return Snappy.compress(bytes);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static byte[] codecAndCompress(Object data) {
        byte[] bytes = encode(data);
        return compress(bytes);
    }

    public static <T> T decode(byte[] bytes, Class<T> type) {
        return Jackson.msgpack().deserializeBytes(bytes, type);
    }

    public static byte[] uncompress(byte[] bytes) {
        try {
            return Snappy.uncompress(bytes);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static <T> T uncompressAndDecode(byte[] bytes, Class<T> type) {
        byte[] uncompress = uncompress(bytes);
        return decode(uncompress, type);
    }

}
