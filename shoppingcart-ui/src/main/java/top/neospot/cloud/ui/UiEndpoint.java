package top.neospot.cloud.ui;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * By neo.chen{neocxf@gmail.com} on 2018/8/30.
 */
@SpringBootApplication
@Controller
public class UiEndpoint {
    public static void main(String[] args) {
        SpringApplication.run(UiEndpoint.class, args);
    }

    @GetMapping("/hello")
    public String hello(Model model) {
        model.addAttribute("name", "neo");
        return "index";
    }
}
