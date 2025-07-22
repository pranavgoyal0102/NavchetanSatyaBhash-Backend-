package com.example.NavchetanSatyabhash.Controller;

import com.example.NavchetanSatyabhash.Models.Article;
import com.example.NavchetanSatyabhash.Models.Comments;
import com.example.NavchetanSatyabhash.Service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/article")
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    @GetMapping("/all")
    public ResponseEntity<List<Article>> getAll(){
        return ResponseEntity.ok(articleService.getAllArticle());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Article> getById(@PathVariable  String id){
        Optional<Article> article = articleService.findById(id);
        return article.map(ResponseEntity::ok).orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }




    @PostMapping("/{id}/{uid}/comment")
    public ResponseEntity<Article> addComment(@PathVariable String id, @PathVariable String uid,@RequestBody Comments comment){
        Article updated = articleService.addComment(id,uid ,comment);
        return updated != null ? ResponseEntity.ok(updated) : ResponseEntity.notFound().build();
    }

    @GetMapping("/{id}/{uid}")
    public void recordView(@PathVariable String id, @PathVariable String uid){
        articleService.recordView(id,uid);
    }
}
