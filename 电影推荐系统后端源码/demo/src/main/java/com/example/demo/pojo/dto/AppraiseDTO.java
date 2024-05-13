package com.example.demo.pojo.dto;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
public class AppraiseDTO {

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

    /**
     * 昵称
     */
    private String nickName;

    /**
     * 电影禁用状态
     */
    private String enabled;

    private String title;
    private String imageUrl;
    private String directors;
    private String filmScore;
    private String types;
    private String playwright;
    private String thumbCount;
}
