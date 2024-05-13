package com.example.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.pojo.dto.AppraiseDTO;
import com.example.demo.pojo.entity.Appraise;
import com.example.demo.pojo.vo.AppraiseVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface AppraiseMapper extends BaseMapper<Appraise> {

    IPage<AppraiseDTO> listPage(@Param("params")AppraiseVO appraiseVO, Page<Appraise> iPage);
}
