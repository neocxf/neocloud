package top.neospot.cloud.auth;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AuthAppTest {

    @Test
    public void boxingTest() {
        Boolean flag = null;
        boolean flag2 = flag;
    }
}
