package com.example.demo.pojo.entity;


import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("film_detail")
public class Film {
    @TableId()
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
    private Integer enabled;
}
