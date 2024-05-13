package com.example.demo.pojo.dto;


import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
public class UserDTO {
    /**
     * 主键id
     */
    private String id;

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 昵称
     */
    private String nickname;

    /**
     * 账号可用标识
     */
    private String enabled;


    /**
     * 是否为管理员（0：不是；1：是）
     */
    private String adminStatus;

    /**
     * 审核状态（1：待审核，2：审核通过）
     */
    private String auditStatus;

    /**
     * 账号
     */
    private String account;
}