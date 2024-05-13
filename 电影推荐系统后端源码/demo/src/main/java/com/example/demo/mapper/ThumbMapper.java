package com.example.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.pojo.dto.AppraiseDTO;
import com.example.demo.pojo.dto.ThumbDTO;
import com.example.demo.pojo.entity.Appraise;
import com.example.demo.pojo.entity.Thumb;
import com.example.demo.pojo.vo.AppraiseVO;
import com.example.demo.pojo.vo.ThumbVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ThumbMapper extends BaseMapper<Thumb> {

    IPage<ThumbDTO> listPage(@Param("params") ThumbVO thumbVO, Page<Thumb> iPage);
    List<ThumbDTO> getList(@Param("params") ThumbVO thumbVO);
}
