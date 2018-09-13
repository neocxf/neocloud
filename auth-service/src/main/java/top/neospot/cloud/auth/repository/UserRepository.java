package top.neospot.cloud.auth.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import top.neospot.cloud.auth.entity.User;

import java.util.Optional;

public interface UserRepository extends MongoRepository<User, Long> {
    Optional<User> findByUsername(String username);
}
