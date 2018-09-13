package top.neospot.cloud.auth;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
import top.neospot.cloud.auth.entity.User;
import top.neospot.cloud.auth.repository.UserRepository;
import top.neospot.cloud.common.BaseCloud;

import java.util.Optional;

@SpringBootApplication
@EnableSwagger2
@Slf4j
public class AuthEndpoint extends BaseCloud {
    public static void main(String[] args) {
        SpringApplication.run(AuthEndpoint.class, args);
    }

    @Autowired
    UserRepository userRepository;

    @Override
    public void run(String... args) {
        super.run(args);

        User user = new User("neo", "pass");

        userRepository.insert(user);

        Optional<User> persistUserOptional = userRepository.findByUsername("neo");

        persistUserOptional.ifPresent(user1 -> log.info(String.format("find the user with %s", user1)));

    }

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("top.neospot.cloud"))
                .build();
    }
}
