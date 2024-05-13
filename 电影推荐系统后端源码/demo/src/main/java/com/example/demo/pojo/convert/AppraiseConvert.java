package com.example.demo.pojo.convert;


import com.example.demo.pojo.dto.AppraiseDTO;
import com.example.demo.pojo.entity.Appraise;
import com.example.demo.pojo.vo.AppraiseVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface AppraiseConvert {

    AppraiseConvert INSTANCE = Mappers.getMapper( AppraiseConvert.class );
    Appraise voConvertEntity(AppraiseVO user);
    AppraiseDTO entityConvertDTO(Appraise user);

    List<AppraiseDTO> entitiesConvertListDTO(List<Appraise> user);


}
