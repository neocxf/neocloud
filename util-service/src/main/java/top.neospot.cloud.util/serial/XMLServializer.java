package top.neospot.cloud.util.serial;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

/**
 * By neo.chen{neocxf@gmail.com} on 2018/8/29.
 */
@SuppressWarnings("all")
public class XMLServializer implements ISerializer {
    private static final XStream xStream = new XStream(new DomDriver());

    @Override
    public <T> byte[] serialize(T obj) {
        return xStream.toXML(obj).getBytes();
    }

    @Override
    public <T> T deserialize(byte[] bytes, Class<T> clazz) {
        String xml = new String(bytes);
        return (T) xStream.fromXML(xml);
    }
}
