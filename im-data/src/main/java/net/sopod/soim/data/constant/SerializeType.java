package net.sopod.soim.data.constant;

/**
 * SerializeType
 *
 * @author tmy
 * @date 2022-03-27 17:14
 */
public enum SerializeType {

    json,

    protobuf;


    public static interface Converter {

        <T> T deserialize(byte[] data, Class<T> clazz) throws Exception;

        byte[] serialize(Object pojo);

    }

    public static void main(String[] args) {

    }

}
