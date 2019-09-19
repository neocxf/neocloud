package top.neospot.cloud.stats.dao;

import org.apache.ibatis.annotations.*;
import top.neospot.cloud.stats.entity.HotelModule;

/**
 * @author DT283
 */
@Mapper
public interface HotelModuleMapper {
    @Select("<script>" +
            "<if test=\"hotelCode.split('-').length > 1\">" +
            "   <bind name=\"hotelCode2\" value=\"hotelCode.split('-')[0]\"/>" +
            "</if>" +
            "SELECT * FROM hotel_module WHERE hotel_code = #{hotelCode}" +
            "<if test=\"hotelCode.split('-').length > 1\">" +
            "   or hotel_code = #{hotelCode2}" +
            "</if>" +
            "</script>")
    @Results(id = "HotelModule", value = {
            @Result(property = "hotelCode", column = "hotel_code"),
            @Result(property = "module", column = "module")
    })
    HotelModule getMatchHotelModule(@Param("hotelCode") String hotelCode);

    @Select("SELECT * FROM hotel_module WHERE hotel_code = #{hotelCode}")
    @ResultMap("HotelModule")
    HotelModule getMHotelModule(@Param("hotelCode") String hotelCode);

    @Insert("insert into hotel_module(hotel_code,module) " +
            "values (#{hotelCode},#{module})")
    int save(HotelModule hotelModule);

    @Insert("update hotel_module set module = #{module} " +
            "where hotel_code = #{hotelCode}")
    int update(HotelModule hotelModule);

}