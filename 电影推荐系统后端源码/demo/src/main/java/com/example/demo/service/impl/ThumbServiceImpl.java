package com.example.demo.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.demo.mapper.ThumbMapper;
import com.example.demo.pojo.convert.ThumbConvert;
import com.example.demo.pojo.convert.UserConvert;
import com.example.demo.pojo.dto.ThumbDTO;
import com.example.demo.pojo.dto.UserDTO;
import com.example.demo.pojo.entity.Thumb;
import com.example.demo.pojo.entity.User;
import com.example.demo.pojo.vo.ThumbVO;
import com.example.demo.pojo.vo.UserVO;
import com.example.demo.service.IThumbService;
import com.example.demo.service.IUserService;
import com.example.demo.util.ResultEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.*;

@Service("thumbServiceImpl")
@Slf4j
public class ThumbServiceImpl extends ServiceImpl<ThumbMapper, Thumb>
        implements IThumbService {

    @Autowired
    private IUserService userService;

    @Resource
    private ThumbMapper thumbMapper;

    @Override
    public ResultEntity listPage(ThumbVO thumbVO, Page<Thumb> iPage) {
//        LambdaQueryWrapper<Thumb> thumbLambdaQueryWrapper = new LambdaQueryWrapper<>();
//        thumbLambdaQueryWrapper.eq(Objects.nonNull(thumbVO.getUserId()),Thumb::getUserId,thumbVO.getUserId())
//                .eq(Objects.nonNull(thumbVO.getUserId()),Thumb::getUserId,thumbVO.getUserId())
//                .eq(Objects.nonNull(thumbVO.getFilmId()),Thumb::getFilmId,thumbVO.getFilmId());

        ResultEntity resultEntity = new ResultEntity();
        try {
            resultEntity.setData(thumbMapper.listPage(thumbVO,iPage));
            resultEntity.setCode("200");
            resultEntity.setMessage("获取成功");
            return resultEntity;
        } catch (Exception e) {
            resultEntity.setCode("500");
            resultEntity.setMessage("fail");
            return resultEntity;
        }
    }

    @Override
    public ResultEntity saveThumb(ThumbVO thumbVO) {
        Thumb thumb = ThumbConvert.INSTANCE.voConvertEntity(thumbVO);
        ResultEntity resultEntity = new ResultEntity();
        try {
            thumb.setThumbUpTime(new Date());
            this.save(thumb);
            resultEntity.setData(thumb.getId());
            resultEntity.setCode("200");
            resultEntity.setMessage("保存成功");
            return resultEntity;
        } catch (Exception e) {
            resultEntity.setCode("500");
            resultEntity.setMessage("fail");
            return resultEntity;
        }
    }

    @Override
    public ResultEntity updateThumb(ThumbVO thumbVO) {
        Thumb thumb = ThumbConvert.INSTANCE.voConvertEntity(thumbVO);
        ResultEntity resultEntity = new ResultEntity();
        try {
            this.updateById(thumb);
            resultEntity.setData(thumb.getId());
            resultEntity.setCode("200");
            resultEntity.setMessage("更新成功");
            return resultEntity;
        } catch (Exception e) {
            resultEntity.setCode("500");
            resultEntity.setMessage("fail");
            return resultEntity;
        }
    }

    @Override
    public ResultEntity delThumb(String thumbId) {
        ResultEntity resultEntity = new ResultEntity();
        try {
            resultEntity.setData(this.removeById(thumbId));
            resultEntity.setCode("200");
            resultEntity.setMessage("删除成功");
            return resultEntity;
        } catch (Exception e) {
            resultEntity.setCode("500");
            resultEntity.setMessage("fail");
            return resultEntity;
        }
    }
    @Override
    public ResultEntity thumbNum(String filmId) {
        ResultEntity resultEntity = new ResultEntity();
        try {
            LambdaQueryWrapper<Thumb> thumbLambdaQueryWrapper = new LambdaQueryWrapper<>();
            thumbLambdaQueryWrapper.eq(Thumb::getFilmId,filmId);
            resultEntity.setData(this.count(thumbLambdaQueryWrapper));
            resultEntity.setCode("200");
            resultEntity.setMessage("点赞总数");
            return resultEntity;
        } catch (Exception e) {
            resultEntity.setCode("500");
            resultEntity.setMessage("fail");
            return resultEntity;
        }
    }

    @Override
    public ResultEntity getLike(ThumbVO thumbVO) {

        ResultEntity resultEntity = new ResultEntity();
        try {
            List<ThumbDTO> thumbDTOS = thumbMapper.getList(thumbVO);
            JSONObject result = new JSONObject();
            if (Objects.nonNull(thumbVO.getUserId())) {
                LambdaQueryWrapper<User> userLambdaQueryWrapper = new LambdaQueryWrapper<>();
                userLambdaQueryWrapper.eq(User::getId,thumbVO.getUserId());
                User user = userService.getOne(userLambdaQueryWrapper);

                result.put("userInfo",UserConvert.INSTANCE.entityConvertDTO(user));
            }
            if (!CollectionUtils.isEmpty(thumbDTOS)) {
                Map<String,Integer> eCheatsInfo = new HashMap<>();
                thumbDTOS.stream().forEach(item -> {
                    String types = item.getTypes();
                    if (!StringUtils.isEmpty(types)) {
                        String[] split = types.split(" ");
                        for (String type : split) {
                            Integer num = eCheatsInfo.get(type);
                            if (Objects.isNull(num)) {
                                eCheatsInfo.put(type,1);
                            } else {
                                eCheatsInfo.put(type,eCheatsInfo.get(type)+1);
                            }
                        }
                    }
                    result.put("eCharts",eCheatsInfo);
                });
                resultEntity.setData(result);
                resultEntity.setCode("200");
                resultEntity.setMessage("获取成功");
                return resultEntity;
            }
            resultEntity.setData(result);
            resultEntity.setCode("200");
            resultEntity.setMessage("暂无数据");
            return resultEntity;
        }catch (Exception e) {
            resultEntity.setCode("500");
            resultEntity.setMessage("获取失败");
            e.printStackTrace();
        }


        return resultEntity;
    }


}
