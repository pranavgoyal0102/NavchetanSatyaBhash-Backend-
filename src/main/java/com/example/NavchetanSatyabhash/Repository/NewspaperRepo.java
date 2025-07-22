package com.example.NavchetanSatyabhash.Repository;

import com.example.NavchetanSatyabhash.Models.NewsPaper;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface NewspaperRepo extends MongoRepository<NewsPaper,String> {

    Optional<NewsPaper> findById(String id);
}
