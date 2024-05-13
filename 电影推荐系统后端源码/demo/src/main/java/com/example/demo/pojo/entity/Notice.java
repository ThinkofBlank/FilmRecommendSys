package com.example.demo.pojo.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Data
@TableName("notice")
public class Notice {

    /**
     * 主键
     */
    private String id;

    /**
     * 通知内容
     */
    private String comment;

    /**
     * 通知时间
     */
    @TableField("notice_time")
    private Date noticeTime;

    /**
     * 状态标识（0：不可用；1：可用）
     */
    private String enabled;

    /**
     * 通知标题
     */
    private String title;

}
