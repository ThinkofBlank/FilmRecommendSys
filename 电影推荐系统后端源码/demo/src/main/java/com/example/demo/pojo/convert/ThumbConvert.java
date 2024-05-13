package com.example.demo.pojo.convert;


import com.example.demo.pojo.dto.ThumbDTO;
import com.example.demo.pojo.entity.Thumb;
import com.example.demo.pojo.vo.ThumbVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface ThumbConvert {

    ThumbConvert INSTANCE = Mappers.getMapper( ThumbConvert.class );
    Thumb voConvertEntity(ThumbVO user);
    ThumbDTO entityConvertDTO(Thumb user);

    List<ThumbDTO> entitiesConvertListDTO(List<Thumb> user);


}
