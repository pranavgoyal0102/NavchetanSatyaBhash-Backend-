package com.example.NavchetanSatyabhash.Models;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Document("Article")
public class Article {
    @Id
    private String id;
    private String title;
    private String description;
    private List<Comments> comments = new ArrayList<>();
    private List<String> images = new ArrayList<>();
    private List<String> view = new ArrayList<>();
    private List<String> videos = new ArrayList<>();
    private String category;
    private String liveOrVideoOrAd;
    private int views;
    private String reaction;
    private LocalDateTime date;
}
