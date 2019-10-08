package top.neospot.cloud.user.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/article")
public class ArticleController {

    @GetMapping("/list")
    public ResponseEntity<List<Map>> list(){
        return null;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Map> read(@PathVariable Long id){
        return null;
    }


}
