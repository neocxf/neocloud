package top.neospot.cloud.stats;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;

/**
 * By neo.chen{neocxf@gmail.com} on 2018/10/15.
 */

public class MockStarwoodRequestTest extends ApplicationTest {
    @Autowired
    RestTemplate restTemplate;

    @Test
    public void testSkyscanner_desktop() {
        String url = "http://35.167.228.215/dplatform-linkcenter/booking.htm?hotelCode=STARWOOD-97&checkInDate=2018-10-26&checkOutDate=2018-11-01&channelCode=dh-skyscanner&language=en-US&rooms=2&guests=3&deviceType=desktop&country=US&currency=USD&testClick=true&party=[{\"adults\":\"2\",\"children\":\"1\"}]&fclid=fc123";
        String prod_url = "https://www.starwoodhotels.com/preferredguest/room.html?numberOfChildren=0&ariesDomain=prod&localeCode=en_US&od=en_US&numberOfAdults=3&language=en_US&numberOfRooms=1&departureDate=2018-11-01&propertyID=97&rp=RC%3ADFRLM&arrivalDate=2018-10-26&refskin=SPG";

        restTemplate.getForEntity(url, Object.class);

    }

    @Test
    public void testSkyscanner_mobile() {
        String url = "http://35.167.228.215/dplatform-linkcenter/booking.htm?hotelCode=STARWOOD-97&checkInDate=2018-10-26&checkOutDate=2018-11-01&channelCode=dh-skyscanner&language=en-US&rooms=2&guests=3&deviceType=mobile&country=US&currency=USD&testClick=true&party=[{\"adults\":\"2\",\"children\":\"1\"}]&fclid=fc123";
        String prod_url = "https://www.starwoodhotels.com/preferredguest/room.html?numberOfChildren=0&ariesDomain=prod&localeCode=en_US&od=en_US&numberOfAdults=3&language=en_US&numberOfRooms=1&departureDate=2018-11-01&propertyID=97&rp=RC%3ADFRLM&arrivalDate=2018-10-26&refskin=SPG";

        restTemplate.getForEntity(url, Object.class);

    }


    @Test
    public void testDhTrivago_desktop() {
        String url = "http://35.167.228.215/dplatform-linkcenter/booking.htm?hotelCode=STARWOOD-97&checkInDate=2019-01-17&checkOutDate=2019-01-21&channelCode=dh-trivago&language=en-US&rooms=2&guests=5&deviceType=desktop&country=US&currency=USD&party=[{\"adults\":\"2\",\"children\":\"1\"}]&testClick=true";
        String prod_url = "https://www.starwoodhotels.com/preferredguest/room.html?numberOfChildren=0&ariesDomain=prod&localeCode=en_US&od=en_US&numberOfAdults=3&language=en_US&numberOfRooms=1&departureDate=2019-01-21&propertyID=97&rp=RC%3ADFRLM&arrivalDate=2019-01-17&refskin=SPG";

        restTemplate.getForEntity(url, Object.class);

    }

    @Test
    public void testDhTrivago_mobile() {
        String url = "http://35.167.228.215/dplatform-linkcenter/booking.htm?hotelCode=STARWOOD-97&checkInDate=2019-01-17&checkOutDate=2019-01-21&channelCode=dh-trivago&language=en-US&rooms=2&guests=5&deviceType=mobile&country=US&currency=USD&party=[{\"adults\":\"2\",\"children\":\"1\"}]&testClick=true";
        String prod_url = "http://35.167.228.215/dplatform-linkcenter/booking.htm?hotelCode=STARWOOD-97&checkInDate=2019-01-17&checkOutDate=2019-01-21&channelCode=dh-trivago&language=en-US&rooms=2&guests=5&deviceType=mobile&country=US&currency=USD&party=[{\"adults\":\"2\",\"children\":\"1\"}]&testClick=true";

        restTemplate.getForEntity(url, Object.class);

    }


    @Test
    public void testStarwood_hipmunk_desktop() {
        String url = "http://35.167.228.215/dplatform-linkcenter/booking.htm?hotelCode=STARWOOD-97&checkInDate=2019-01-02&checkOutDate=2019-01-03&channelCode=starwood-hipmunk&language=en-US&rooms=2&guests=4&deviceType=desktop&country=US&currency=USD&party=[{\"adults\":\"2\",\"children\":\"1\"}]&testClick=true\n";
        String prod_url = "https://www.starwoodhotels.com/preferredguest/room.html?numberOfChildren=0&ariesDomain=prod&localeCode=en_US&od=en_US&numberOfAdults=3&language=en_US&numberOfRooms=1&departureDate=2019-01-03&propertyID=97&rp=RC%3ADFRLM&arrivalDate=2019-01-02&refskin=SPG";

        restTemplate.getForEntity(url, Object.class);

    }

    @Test
    public void testStarwood_hipmunk_mobile() {
        String url = "http://35.167.228.215/dplatform-linkcenter/booking.htm?hotelCode=STARWOOD-97&checkInDate=2019-01-02&checkOutDate=2019-01-03&channelCode=starwood-hipmunk&language=en-US&rooms=2&guests=4&deviceType=mobile&country=US&currency=USD&party=[{\"adults\":\"2\",\"children\":\"1\"}]&testClick=true\n";
        String prod_url = "https://www.starwoodhotels.com/preferredguest/room.html?numberOfChildren=0&ariesDomain=prod&localeCode=en_US&od=en_US&numberOfAdults=3&language=en_US&numberOfRooms=1&departureDate=2019-01-03&propertyID=97&rp=RC%3ADFRLM&arrivalDate=2019-01-02&refskin=SPG";

        restTemplate.getForEntity(url, Object.class);

    }

    @Test
    public void testStarwood_tripadvisor_desktop() {
        String url = "http://us.linkcenter.derbysoftca.com/dplatform-linkcenter/booking.htm?hotelCode=STARWOOD-97&checkInDate=2018-11-14&checkOutDate=2018-11-18&channelCode=starwood-tripadvisor&languageType=en-US&rooms=2&guests=4&deviceType=desktop&country=US&currency=USD&testClick=true&party={adults=2}{children=1}&sitetype=US&refid=DerbyTest&source=tripadvisor";
        String prod_url = "http://www.starwoodhotels.com/luxury/rates/room.html?lpqRatePlanName=null&bTax=null&departureDate=2018-11-18&arrivalDate=2018-11-14&propertyId=97&numberOfRooms=1&numberOfAdults=3&lpqTotalRate=null&taxAmount=0&currencyCode=USD&language=en_US&localeCode=en_US";

        restTemplate.getForEntity(url, Object.class);

    }

    @Test
    public void testStarwood_tripadvisor_mobile() {
        String url = "e=en-US&rooms=2&guests=4&deviceType=mobile&country=US&currency=USD&testClick=true&party={adults=2}{children=1}&sitetype=US&refid=DerbyTest&source=tripadvisor";
        String prod_url = "https://www.starwoodhotels.com/preferredguest/room.html?numberOfChildren=0&ariesDomain=prod&localeCode=en_US&od=en_US&numberOfAdults=3&language=en_US&numberOfRooms=1&departureDate=2018-11-18&propertyID=97&rp=RC%3ADFRLM&arrivalDate=2018-11-14&refskin=SPG";

        restTemplate.getForEntity(url, Object.class);

    }
}
