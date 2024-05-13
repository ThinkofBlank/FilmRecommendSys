package com.example.demo.pojo.vo;

import lombok.Data;

import java.time.LocalDate;
import java.util.Date;

@Data
public class NoticeVO {

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
