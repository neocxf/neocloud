package top.neospot.cloud.stats.entity;

import lombok.Data;

@Data
public class MappingHotelURL {
    private String identifier;
    private String urlPattern;
    private String customField;
    private String additionIdentifier;
}
