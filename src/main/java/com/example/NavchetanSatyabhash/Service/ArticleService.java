package com.example.NavchetanSatyabhash.Service;

import com.example.NavchetanSatyabhash.Models.Article;
import com.example.NavchetanSatyabhash.Models.Comments;
import com.example.NavchetanSatyabhash.Repository.ArticleRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ArticleService {

    @Autowired
    private ArticleRepo articleRepo;

    public List<Article> getAllArticle(){
        return articleRepo.findAll();
    }

    public Optional<Article> findById(String id){
        return articleRepo.findById(id);
    }

    public void saveArticle(Article article){
        article.setViews(0);
        article.setDate(LocalDateTime.now());
        articleRepo.save(article);
    }
    public Article addComment(String articleId, String uid,Comments comments){
        Article article = articleRepo.findById(articleId).orElse(null);
        if(article != null){
            comments.setUserId(uid);
            comments.setDate(LocalDateTime.now());
            article.getComments().add(comments);
            return articleRepo.save(article);
        }
        return null;
    }

    public void recordView(String articleId,String uid){
            Article article = articleRepo.findById(articleId).orElse(null);
            if(article!=null && !article.getView().contains(uid)){
                article.getView().add(uid);
                article.setViews(article.getView().size());
                articleRepo.save(article);
            }
    }

    public void deleteById(String id){
        articleRepo.deleteById(id);
    }

}
