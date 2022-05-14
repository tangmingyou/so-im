package net.sopod.soim.data.constant;

import net.sopod.soim.data.serialize.ByteSerializer;
import net.sopod.soim.data.serialize.JacksonByteSerializer;

/**
 * SerializeType
 *
 * @author tmy
 * @date 2022-03-27 17:14
 */
public enum SerializeType {

    json(new JacksonByteSerializer()),

    protobuf(null);

    private final ByteSerializer serializer;

    SerializeType(ByteSerializer serializer) {
        this.serializer = serializer;
    }

    public ByteSerializer getSerializer() {
        return serializer;
    }

    public static SerializeType getSerializeByOrdinal(int ordinal) {
        return values()[ordinal];
    }

}
