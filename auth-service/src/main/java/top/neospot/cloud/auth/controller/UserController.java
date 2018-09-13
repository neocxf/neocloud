package top.neospot.cloud.auth.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.neospot.cloud.auth.entity.User;
import top.neospot.cloud.auth.repository.UserRepository;

import java.util.Optional;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    UserRepository userRepository;

    @GetMapping("/{username}")
    public Optional<User> findUser(@PathVariable("username") String username) {

        Optional<User> userOptional = userRepository.findByUsername(username);

        return userOptional;
    }
}
