package com.example.demo.pojo.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName("thumb")
public class Thumb {
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
