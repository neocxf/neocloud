package top.neospot.cloud.util.serial;

import org.springframework.util.StringUtils;

import java.util.Objects;

/**
 * By neo.chen{neocxf@gmail.com} on 2018/8/29.
 */
public enum  SerializeType {
    DEFAULT("DefaultJavaSerializer"),
    XML("XMLServializer"),
    PROTOSTUFF("ProtoStuffSerializer");

    private String serializeType;

    private SerializeType(String serializeType) {
        this.serializeType =serializeType;
    }

    public static SerializeType queryByType(String serializeType) {
        if (StringUtils.isEmpty(serializeType)) {
            return null;
        }

        for (SerializeType type : SerializeType.values()) {
            if (Objects.equals(serializeType, type.serializeType)) {
                return type;
            }
        }

        return null;
    }

    public String getSerializeType() {
        return this.serializeType;
    }

}
