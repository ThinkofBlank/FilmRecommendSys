package com.example.demo.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.demo.pojo.dto.FilmDTO;
import com.example.demo.pojo.dto.ThumbDTO;
import com.example.demo.pojo.entity.Thumb;
import com.example.demo.pojo.vo.ThumbVO;
import com.example.demo.pojo.vo.UserVO;
import com.example.demo.util.ResultEntity;

public interface IThumbService extends IService<Thumb> {

    ResultEntity listPage(ThumbVO thumbVO, Page<Thumb> iPage);

    ResultEntity saveThumb (ThumbVO thumbVO);

    ResultEntity updateThumb (ThumbVO thumbVO);

    ResultEntity delThumb (String thumbId);

    ResultEntity thumbNum (String filmId);

    ResultEntity getLike(ThumbVO thumbVO);

}
