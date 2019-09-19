package top.neospot.cloud.stats.entity;

public class HotelModule {
    private String hotelCode;
    private String module;

    public String getHotelCode() {
        return hotelCode;
    }

    public HotelModule setHotelCode(String hotelCode) {
        this.hotelCode = hotelCode;
        return this;
    }

    public String getModule() {
        return module;
    }

    public HotelModule setModule(String module) {
        this.module = module;
        return this;
    }
}
