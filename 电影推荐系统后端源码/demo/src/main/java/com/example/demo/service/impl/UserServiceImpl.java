package com.example.demo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.demo.cache.Cache;
import com.example.demo.cache.CacheManager;
import com.example.demo.mapper.UserMapper;
import com.example.demo.pojo.convert.UserConvert;
import com.example.demo.pojo.dto.UserDTO;
import com.example.demo.pojo.entity.User;
import com.example.demo.pojo.vo.UserVO;
import com.example.demo.service.IThumbService;
import com.example.demo.service.IUserService;
import com.example.demo.util.ResultEntity;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.security.MD5Encoder;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.DigestUtils;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service("userServiceImpl")
@Slf4j
public class UserServiceImpl
        extends ServiceImpl<UserMapper, User>
        implements IUserService {



    @Override
    public ResultEntity login(UserVO userVO) {
        ResultEntity resultEntity = new ResultEntity();
        LambdaQueryWrapper<User> userService = new LambdaQueryWrapper<>();
        userService.eq(User::getAccount,userVO.getAccount())
                        .eq(User::getEnabled,1);
        User user = this.getOne(userService);
        if (Objects.isNull(user)) {
            resultEntity.setCode("500");
            resultEntity.setMessage("账号不存在");
            return resultEntity;
        }
        if (!Objects.equals(user.getPassword(),userVO.getPassword())) {
            resultEntity.setCode("500");
            resultEntity.setMessage("密码错误");
            return resultEntity;
        }
        String md5 = DigestUtils.md5DigestAsHex(String.valueOf(user.getId()).getBytes());
        //失效期为两个小时
        CacheManager.putCacheInfo(md5,user, LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.UTC).toEpochMilli());
        resultEntity.setData(md5);
        resultEntity.setCode("200");
        resultEntity.setMessage("登录成功");
        return resultEntity;

    }

    @Override
    public ResultEntity save(UserVO userVO) {
        ResultEntity resultEntity = new ResultEntity();
        User user = UserConvert.INSTANCE.voConvertEntity(userVO);
        LambdaQueryWrapper<User> userQueryWrapper = new LambdaQueryWrapper<>();
        userQueryWrapper.eq(User::getEnabled,1)
                .and(wq->wq.eq(User::getAccount,userVO.getAccount()).or().eq(User::getPhone,userVO.getPhone()));
        List<User> list = this.list(userQueryWrapper);
        if (list.size() > 0) {
            resultEntity.setData(null);
            resultEntity.setCode("555");
            resultEntity.setMessage("账号或手机号已存在，请重新输入!");
            return resultEntity;
        }
        if (Objects.equals(user.getAdminStatus(),"1")) {
            LambdaQueryWrapper<User> item = new LambdaQueryWrapper();
            item.eq(User::getAdminStatus,1);
            int count = this.count(item);
            if (count >= 10) {
                resultEntity.setData(null);
                resultEntity.setCode("555");
                resultEntity.setMessage("管理员已满，请联系管理员!");
                return resultEntity;
            }
            user.setEnabled("0");
        }
        if (this.save(user)) {
            resultEntity.setData(user.getId());
            resultEntity.setCode("200");
            resultEntity.setMessage("保存成功");
            return resultEntity;
        }
        resultEntity.setCode("500");
        resultEntity.setMessage("fail");
        return resultEntity;
    }

    @Override
    public ResultEntity update(UserVO userVO) {
        User user = UserConvert.INSTANCE.voConvertEntity(userVO);
        if (Objects.equals(user.getAuditStatus(),"1")) {
            user.setEnabled("0");
        }
        ResultEntity resultEntity = new ResultEntity();
        if (this.updateById(user)) {
            resultEntity.setData(user.getId());
            resultEntity.setCode("200");
            resultEntity.setMessage("更新成功");
            return resultEntity;
        }
        resultEntity.setCode("500");
        resultEntity.setMessage("fail");
        return resultEntity;
    }

    @Override
    public ResultEntity del(String userId) {

        ResultEntity resultEntity = new ResultEntity();
        try {
            resultEntity.setData(this.removeById(userId));
            resultEntity.setCode("200");
            resultEntity.setMessage("更新成功");
            return resultEntity;
        } catch (Exception e) {
            e.printStackTrace();
            resultEntity.setCode("500");
            resultEntity.setMessage("fail");
            return resultEntity;
        }
    }

    @Override
    public ResultEntity getUserInfo(String ticket) {
        Cache cacheInfo = CacheManager.getCacheInfo(ticket);
        User user = (User) cacheInfo.getValue();
        ResultEntity resultEntity = new ResultEntity();
        try {
            resultEntity.setCode("200");
            resultEntity.setMessage("success");
            resultEntity.setData(UserConvert.INSTANCE.entityConvertDTO(user));
            return resultEntity;
        } catch (Exception e) {
            e.printStackTrace();
            resultEntity.setCode("500");
            resultEntity.setMessage("fail");
            return resultEntity;
        }
    }
    @Override
    public void loginOut(String userId) {
        String md5 = MD5Encoder.encode(String.valueOf(userId).getBytes());
        CacheManager.delCacheInfo(md5);
    }

    @Override
    public ResultEntity listPage(UserVO userVO, Page<User> userPage) {
        LambdaQueryWrapper<User> userQueryWrapper = new LambdaQueryWrapper<>();
        userQueryWrapper.like(Objects.nonNull(userVO.getNickname()),User::getNickname,userVO.getNickname())
                .eq(Objects.nonNull(userVO.getEnabled()),User::getEnabled,userVO.getEnabled())
                .eq(Objects.nonNull(userVO.getAuditStatus()), User::getAuditStatus,userVO.getAuditStatus() )
                .eq(Objects.nonNull(userVO.getAdminStatus()), User::getAdminStatus,userVO.getAdminStatus() );
        ResultEntity resultEntity = new ResultEntity();
        try {
            resultEntity.setCode("200");
            resultEntity.setMessage("success");
            resultEntity.setData(this.page(userPage,userQueryWrapper).convert(item -> UserConvert.INSTANCE.entityConvertDTO(item)));
            return resultEntity;
        } catch (Exception e) {
            e.printStackTrace();
            resultEntity.setCode("500");
            resultEntity.setMessage("fail");
            return resultEntity;
        }
    }

    @Override
    public ResultEntity resetPassword(UserVO userVO) {
        ResultEntity resultEntity = new ResultEntity();
        LambdaQueryWrapper<User> userQueryWrapper = new LambdaQueryWrapper<>();
        userQueryWrapper.eq(User::getAccount,userVO.getAccount())
                .eq(User::getPhone,userVO.getPhone())
                .eq(User::getEnabled,"1");
        User user = this.getOne(userQueryWrapper);
        if (Objects.isNull(user)) {
            resultEntity.setCode("500");
            resultEntity.setMessage("账号不存在");
            return resultEntity;
        } else {
            user.setPassword(userVO.getPassword());
            this.updateById(user);
            resultEntity.setCode("200");
            resultEntity.setMessage("重置成功");
            return resultEntity;
        }

    }

    @Override
    public ResultEntity count(UserVO userVO) {
        LambdaQueryWrapper<User> userQueryWrapper = new LambdaQueryWrapper<>();
        userQueryWrapper.like(Objects.nonNull(userVO.getNickname()),User::getNickname,userVO.getNickname())
                .eq(Objects.nonNull(userVO.getAuditStatus()),User::getAuditStatus,userVO.getAuditStatus());
        ResultEntity resultEntity = new ResultEntity();
        try {
            resultEntity.setCode("200");
            resultEntity.setMessage("success");
            resultEntity.setData(this.count(userQueryWrapper));
            return resultEntity;
        } catch (Exception e) {
            e.printStackTrace();
            resultEntity.setCode("500");
            resultEntity.setMessage("fail");
            return resultEntity;
        }

    }



}