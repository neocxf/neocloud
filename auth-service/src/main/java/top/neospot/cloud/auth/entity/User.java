package top.neospot.cloud.auth.entity;
import lombok.Data;
import org.springframework.data.annotation.Id;

@Data
public class User {
    @Id
    private long id;
    private String username;
    private String password;

    public User(){}

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }



}
