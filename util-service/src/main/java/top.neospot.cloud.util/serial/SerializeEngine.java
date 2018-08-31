package top.neospot.cloud.util.serial;

import com.google.common.collect.Maps;

import java.util.Map;

/**
 * By neo.chen{neocxf@gmail.com} on 2018/8/29.
 */
public class SerializeEngine {
    public static final Map<SerializeType, ISerializer> serializeMap = Maps.newConcurrentMap();

    static {
        serializeMap.put(SerializeType.DEFAULT, new DefaultJavaSerializer());
        serializeMap.put(SerializeType.XML, new XMLServializer());
        serializeMap.put(SerializeType.PROTOSTUFF, new ProtoStuffSerializer());
    }

    public static <T> byte[] serialize(T obj, String serializeType) {
        SerializeType type = SerializeType.queryByType(serializeType);

        if (type == null) {
            throw new RuntimeException("serialize is null");
        }

        ISerializer serializer = serializeMap.get(type);
        if (serializer == null) {
            throw new RuntimeException("serialize error");
        }

        try {
            return serializer.serialize(obj);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }

    }

    public static <T> T deserialize(byte[] data, Class<T> clazz, String serializeType) {
        SerializeType type = SerializeType.queryByType(serializeType);

        if (type == null) {
            throw new RuntimeException("serialize is null");
        }

        ISerializer serializer = serializeMap.get(type);
        if (serializer == null) {
            throw new RuntimeException("serialize error");
        }

        try {
            return serializer.deserialize(data, clazz);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }

    }
}
