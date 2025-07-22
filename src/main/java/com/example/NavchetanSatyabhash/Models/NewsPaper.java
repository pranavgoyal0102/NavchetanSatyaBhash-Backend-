package com.example.NavchetanSatyabhash.Models;


import jakarta.websocket.Decoder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

import org.bson.types.Binary;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@Document("newspaper")
public class NewsPaper {
    @Id
    private String id;
    private String title;
    private LocalDateTime date;
    private List<String> view = new ArrayList<>();
    private int views;
    private String reaction;
    private String newsPaper;
}
