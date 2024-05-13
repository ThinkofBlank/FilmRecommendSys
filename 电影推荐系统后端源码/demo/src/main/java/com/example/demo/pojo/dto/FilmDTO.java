package com.example.demo.pojo.dto;


import lombok.Data;

@Data
public class FilmDTO {
    private String id;
    private String title;
    private String imageUrl;
    private String directors;
    private String playwright;
    private String actors;
    private String types;
    private String releaseTime;
    private String score;
    private String introduction;
    private String status;
    private Integer enabled;
}
