package com.dstz.base.core.spring;

import org.nustaq.serialization.FSTConfiguration;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;

/**
 * fst序列化
 *
 * @author wacxhs
 */
public class FstRedisSerializer implements RedisSerializer<Object> {

    private final FSTConfiguration fstConfiguration;

    private final byte[] EMPTY_BYTES = new byte[0];

    public FstRedisSerializer() {
        fstConfiguration = FSTConfiguration.getDefaultConfiguration();
        fstConfiguration.setClassLoader(FstRedisSerializer.class.getClassLoader());
    }

    @Override
    public byte[] serialize(Object o) throws SerializationException {
        if (o == null) {
            return EMPTY_BYTES;
        }
        return fstConfiguration.asByteArray(o);
    }

    @Override
    public Object deserialize(byte[] bytes) throws SerializationException {
        if (bytes == null || bytes.length == 0) {
            return null;
        }
        return fstConfiguration.asObject(bytes);
    }
}
