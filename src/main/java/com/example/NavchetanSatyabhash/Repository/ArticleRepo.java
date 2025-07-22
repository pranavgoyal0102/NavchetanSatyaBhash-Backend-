package com.example.NavchetanSatyabhash.Repository;

import com.example.NavchetanSatyabhash.Models.Article;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface ArticleRepo extends MongoRepository<Article,String> {
    Optional<Article> findById(String id);
}
