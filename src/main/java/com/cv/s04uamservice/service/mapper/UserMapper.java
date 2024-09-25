package com.cv.s04uamservice.service.mapper;

import com.cv.s01coreservice.dto.UserDto;
import com.cv.s01coreservice.entity.UserDetail;
import com.cv.s01coreservice.service.mapper.generic.GenericMapper;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper extends GenericMapper<UserDto, UserDetail> {

}
