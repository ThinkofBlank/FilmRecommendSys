package com.example.demo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.demo.mapper.AppraiseMapper;
import com.example.demo.mapper.FilmMapper;
import com.example.demo.pojo.convert.AppraiseConvert;
import com.example.demo.pojo.convert.FilmConvert;
import com.example.demo.pojo.convert.NoticeConvert;
import com.example.demo.pojo.dto.AppraiseDTO;
import com.example.demo.pojo.dto.FilmDTO;
import com.example.demo.pojo.entity.Appraise;
import com.example.demo.pojo.entity.Film;
import com.example.demo.pojo.vo.AppraiseVO;
import com.example.demo.pojo.vo.FilmVO;
import com.example.demo.service.IAppraiseService;
import com.example.demo.service.IFilmService;
import com.example.demo.util.ResultEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.Objects;

@Service("appraiseServiceImpl")
@Slf4j
public class AppraiseServiceImpl extends ServiceImpl<AppraiseMapper, Appraise>
        implements IAppraiseService {

    @Resource
    private AppraiseMapper appraiseMapper;


    @Override
    public ResultEntity listPage(AppraiseVO appraiseVO, Page<Appraise> iPage) {
//        LambdaQueryWrapper<Appraise> appraiseLambdaQueryWrapper = new LambdaQueryWrapper<>();
//        appraiseLambdaQueryWrapper.eq(Objects.nonNull(appraiseVO.getFilmId()),Appraise::getFilmId,appraiseVO.getFilmId())
//                .eq(Objects.nonNull(appraiseVO.getUserId()),Appraise::getUserId,appraiseVO.getUserId());
        ResultEntity resultEntity = new ResultEntity();
        try {
//            this.page(iPage,appraiseLambdaQueryWrapper).convert(item -> AppraiseConvert.INSTANCE.entityConvertDTO(item))
            resultEntity.setData(appraiseMapper.listPage(appraiseVO,iPage));
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
    public ResultEntity saveAppraise(AppraiseVO appraiseVO) {
        Appraise appraise = AppraiseConvert.INSTANCE.voConvertEntity(appraiseVO);
        ResultEntity resultEntity = new ResultEntity();
        try {
            appraise.setAppraiseTime(new Date());
            this.save(appraise);
            resultEntity.setData(appraise.getId());
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
    public ResultEntity updateAppraise(AppraiseVO appraiseVO) {
        Appraise appraise = AppraiseConvert.INSTANCE.voConvertEntity(appraiseVO);
        ResultEntity resultEntity = new ResultEntity();
        try {
            this.updateById(appraise);
            resultEntity.setData(appraise.getId());
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
    public ResultEntity delAppraise(String appraiseId) {
        ResultEntity resultEntity = new ResultEntity();
        try {
            resultEntity.setData(this.removeById(appraiseId));
            resultEntity.setCode("200");
            resultEntity.setMessage("删除成功");
            return resultEntity;
        } catch (Exception e) {
            resultEntity.setCode("500");
            resultEntity.setMessage("fail");
            return resultEntity;
        }
    }


}
