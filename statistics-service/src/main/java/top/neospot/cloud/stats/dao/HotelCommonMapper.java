package top.neospot.cloud.stats.dao;

import org.apache.ibatis.annotations.*;
import top.neospot.cloud.stats.entity.HotelInfo;
import top.neospot.cloud.stats.entity.HotelLanguageMapping;
import top.neospot.cloud.stats.entity.MappingHotelURL;

/**
 * @author DT283
 */
@Mapper
public interface HotelCommonMapper {
    @Select("SELECT \n" +
            "    HOTEL_LANGUAGE,(CASE\n" +
            "                WHEN passport = #{passport} and language = #{userLanguage} THEN 1\n" +
            "                WHEN brand = #{brandCode} and language = #{userLanguage} THEN 2\n" +
            "                WHEN language = #{userLanguage} THEN 3\n" +
            "                WHEN passport = #{passport} and language = left(#{userLanguage},2) THEN 4\n" +
            "                WHEN brand = #{brandCode} and language = left(#{userLanguage},2) THEN 5\n" +
            "                WHEN language = left(#{userLanguage},2) THEN 6\n" +
            "                WHEN passport = #{passport} and language = 'en' THEN 7\n" +
            "                WHEN brand = #{brandCode} and language = 'en' THEN 8\n" +
            "                WHEN language = 'en' THEN 9\n" +
            "            END) as resultOrder\n" +
            "FROM\n" +
            "    hotel_language_mapping\n" +
            "WHERE\n" +
            "    account_code = #{accountCode}\n" +
            "        AND (passport = #{passport}\n" +
            "        OR brand = #{brandCode}\n" +
            "        OR TRUE)\n" +
            "        AND (language = #{userLanguage} or language = left(#{userLanguage},2) or language = 'en')\n" +
            "order by resultOrder limit 1;")
    @Results(id = "HotelLanguageMapping", value = {
            @Result(property = "hotelLanguage", column = "HOTEL_LANGUAGE")
    })
    HotelLanguageMapping findHotelLanguage(@Param("accountCode") String accountCode,
                                           @Param("brandCode") String brandCode,
                                           @Param("passport") String passport,
                                           @Param("userLanguage") String userLanguage);

    @Select("SELECT account_code,brand_code,currency_code,ctyhocn,provider_hotel_code,passport, brand FROM hotel_info WHERE passport = #{passport}")
    @Results(id = "HotelInfo", value = {
            @Result(property = "accountCode", column = "account_code"),
            @Result(property = "brandCode", column = "brand_code"),
            @Result(property = "currencyCode", column = "currency_code"),
            @Result(property = "ctyhocn", column = "ctyhocn"),
            @Result(property = "providerHotelCode", column = "provider_hotel_code"),
            @Result(property = "passport", column = "passport"),
            @Result(property = "brand", column = "brand"),
    })
    HotelInfo findHotelInfo(@Param("passport") String passport);

    @Select("SELECT identifier,url_pattern,additional_identifier,custom_field FROM mapping_hotelurl WHERE hotel_code = #{hotelCode}")
    @Results(id = "MappingHotelURL", value = {
            @Result(property = "identifier", column = "identifier"),
            @Result(property = "urlPattern", column = "url_pattern"),
            @Result(property = "customField", column = "custom_field"),
            @Result(property = "additionIdentifier", column = "additional_identifier")
    })
    MappingHotelURL findMappingHotelURL(@Param("hotelCode") String hotelCode);

}