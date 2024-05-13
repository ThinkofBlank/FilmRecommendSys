package com.example.demo.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.pojo.dto.UserDTO;
import com.example.demo.pojo.entity.User;
import com.example.demo.pojo.vo.UserVO;
import com.example.demo.service.IUserService;
import com.example.demo.util.ResultEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * 用户控制层
 */
@RestController
@RequestMapping("/auth")
public class UserController {

    @Autowired
    private IUserService userService;

    @PostMapping("/login")
    public ResultEntity login (@RequestBody UserVO userVO) {
        return userService.login(userVO);
    }

    @PostMapping("/save")
    public ResultEntity save (@RequestBody UserVO userVO) {
        return userService.save(userVO);
    }

    @PostMapping("/update")
    public ResultEntity update (@RequestBody UserVO userVO) {
        return userService.update(userVO);
    }

    @GetMapping("/del/{userId}")
    public ResultEntity del (@PathVariable("userId")String userId) {
        return userService.del(userId);
    }

    @GetMapping("/getUserInfo")
    public ResultEntity getUserInfo ( HttpServletRequest request) {
        String ticket = request.getHeader("ticket");
        return userService.getUserInfo(ticket);
    }

    @GetMapping("/loginOut/{userId}")
    public void loginOut (@PathVariable("userId")String userId) {
        userService.loginOut(userId);
    }

    @GetMapping("/list")
    public ResultEntity listPage (UserVO userVO, Page<User> iPage) {
        return userService.listPage(userVO,iPage);
    }

    @PostMapping("/resetPassword")
    public ResultEntity resetPassword(@RequestBody UserVO userVO) {
        return userService.resetPassword(userVO);
    }

    @PostMapping("/count")
    public ResultEntity count (@RequestBody UserVO userVO) {
        return userService.count(userVO);
    }

}
