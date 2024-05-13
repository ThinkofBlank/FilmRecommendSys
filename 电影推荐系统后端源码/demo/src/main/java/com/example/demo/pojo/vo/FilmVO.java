package com.example.demo.pojo.vo;

import lombok.Data;

@Data
public class FilmVO {
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
    private String type;
    private Integer enabled = 1;
    private String keyword;
}
