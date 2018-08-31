package top.neospot.cloud.util.serial;

/**
 * By neo.chen{neocxf@gmail.com} on 2018/8/29.
 */
public interface ISerializer {
    <T> byte[] serialize(T obj);

    <T> T deserialize(byte[] bytes, Class<T> clazz);
}
