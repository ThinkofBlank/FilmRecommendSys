package com.example.demo.pojo.convert;

import com.example.demo.pojo.dto.UserDTO;
import com.example.demo.pojo.entity.User;
import com.example.demo.pojo.vo.UserVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface UserConvert {

    UserConvert INSTANCE = Mappers.getMapper( UserConvert.class );
    User voConvertEntity(UserVO user);
    UserDTO entityConvertDTO(User user);

    List<UserDTO> entitiesConvertListDTO(List<User> user);


}
