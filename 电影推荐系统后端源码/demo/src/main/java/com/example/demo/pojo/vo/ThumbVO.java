package com.example.demo.pojo.vo;

import lombok.Data;

import java.util.Date;

@Data
public class ThumbVO {
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
     * 点赞时间
     */
    private Date thumbUpTime;

}
