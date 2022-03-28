package net.sopod.soim.data.serialize;

/**
 * ByteSerializer
 *
 * @author tmy
 * @date 2022-03-28 11:03
 */
public interface ByteSerializer {

    <T> T deserialize(byte[] data, Class<T> clazz) throws Exception;

    byte[] serialize(Object pojo) throws Exception;

}
