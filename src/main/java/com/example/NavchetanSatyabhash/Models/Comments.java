package com.example.NavchetanSatyabhash.Models;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
public class Comments {
    @Id
    private String id;
    private String userId;
    private String comment;
    private LocalDateTime date;
}
