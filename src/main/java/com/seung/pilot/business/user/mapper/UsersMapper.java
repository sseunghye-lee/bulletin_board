package com.seung.pilot.business.user.mapper;

import com.seung.pilot.business.user.domain.Users;
import com.seung.pilot.commons.dto.response.user.GetUserListResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UsersMapper {
    UsersMapper INSTANCE = Mappers.getMapper(UsersMapper.class);

    GetUserListResponse convertUserListDto(Users users);
}
