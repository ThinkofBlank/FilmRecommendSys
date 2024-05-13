package com.example.demo.pojo.convert;


import com.example.demo.pojo.dto.FilmDTO;
import com.example.demo.pojo.entity.Film;
import com.example.demo.pojo.vo.FilmVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface FilmConvert {

    FilmConvert INSTANCE = Mappers.getMapper( FilmConvert.class );
    Film voConvertEntity(FilmVO user);
    FilmDTO entityConvertDTO(Film user);

    List<FilmDTO> entitiesConvertListDTO(List<Film> user);


}
