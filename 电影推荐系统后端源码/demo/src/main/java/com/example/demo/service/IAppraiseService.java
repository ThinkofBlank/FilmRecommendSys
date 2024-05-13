package com.example.demo.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.demo.pojo.dto.AppraiseDTO;
import com.example.demo.pojo.dto.FilmDTO;
import com.example.demo.pojo.entity.Appraise;
import com.example.demo.pojo.vo.AppraiseVO;
import com.example.demo.util.ResultEntity;

public interface IAppraiseService extends IService<Appraise> {

    ResultEntity listPage(AppraiseVO appraiseVO, Page<Appraise> iPage);

    ResultEntity saveAppraise (AppraiseVO appraiseVO);

    ResultEntity updateAppraise (AppraiseVO appraiseVO);

    ResultEntity delAppraise (String appraiseId);
}
