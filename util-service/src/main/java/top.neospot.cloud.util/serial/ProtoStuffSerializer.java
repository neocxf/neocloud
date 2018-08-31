package top.neospot.cloud.util.serial;

import io.protostuff.LinkedBuffer;
import io.protostuff.ProtostuffIOUtil;
import io.protostuff.Schema;
import io.protostuff.runtime.RuntimeSchema;
import org.objenesis.Objenesis;
import org.objenesis.ObjenesisStd;
import org.objenesis.instantiator.ObjectInstantiator;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * By neo.chen{neocxf@gmail.com} on 2018/8/29.
 */
@SuppressWarnings("all")
public class ProtoStuffSerializer implements ISerializer {
    private static Map<Class<?>, Schema<?>> cachedSchema = new ConcurrentHashMap<>();

    private static <T> Schema<T> getSchema(Class<T> cls) {
        Schema<T> schema = (Schema<T>) cachedSchema.get(cls);

        if (schema == null) {
            schema = RuntimeSchema.createFrom(cls);
            cachedSchema.put(cls, schema);
        }

        return schema;
    }

    @Override
    public <T> byte[] serialize(T obj) {
        Class<T> cls = (Class<T>) obj.getClass();

        LinkedBuffer buffer = LinkedBuffer.allocate(LinkedBuffer.DEFAULT_BUFFER_SIZE);

        Schema<T> schema = getSchema(cls);

        return ProtostuffIOUtil.toByteArray(obj, schema, buffer);
    }

    @Override
    public <T> T deserialize(byte[] bytes, Class<T> clazz) {
        Objenesis objenesis1 = new ObjenesisStd();
        ObjectInstantiator instantiator = objenesis1.getInstantiatorOf(clazz);
        T t = objenesis1.newInstance(clazz);
        return t;
    }
}
