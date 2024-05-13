package com.example.demo.pojo.vo;

import lombok.Data;

import java.util.Date;

@Data
public class AppraiseVO {

    /**
     * 主键
     */
    private String id;

    /**
     * 电影id
     */
    private String filmId;

    /**
     * 用户id
     */
    private String userId;

    /**
     * 评论内容
     */
    private String comment;

    /**
     * 评分
     */
    private String score;

    /**
     * 评价时间
     */
    private Date appraiseTime;



}
