package com.example.demo.pojo.convert;


import com.example.demo.pojo.dto.NoticeDTO;
import com.example.demo.pojo.entity.Notice;
import com.example.demo.pojo.vo.NoticeVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface NoticeConvert {

    NoticeConvert INSTANCE = Mappers.getMapper( NoticeConvert.class );
    Notice voConvertEntity(NoticeVO user);
    NoticeDTO entityConvertDTO(Notice user);

    List<NoticeDTO> entitiesConvertListDTO(List<Notice> user);


}
