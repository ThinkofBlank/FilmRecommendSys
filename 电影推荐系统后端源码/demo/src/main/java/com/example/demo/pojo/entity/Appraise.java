package com.example.demo.pojo.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName("appraise")
public class Appraise {

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
