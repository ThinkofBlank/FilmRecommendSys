package com.example.demo.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.demo.pojo.dto.UserDTO;
import com.example.demo.pojo.entity.User;
import com.example.demo.pojo.vo.UserVO;
import com.example.demo.util.ResultEntity;


public interface IUserService extends IService<User> {

    ResultEntity login(UserVO userVO);
    ResultEntity save(UserVO userVO);
    ResultEntity update(UserVO userVO);
    ResultEntity del(String userId);
    ResultEntity getUserInfo(String ticket);

    void loginOut(String userId);

    ResultEntity listPage(UserVO userVO, Page<User> ipage);
    ResultEntity resetPassword(UserVO userVO);
    ResultEntity count(UserVO userVO);

}