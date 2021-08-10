package in.programming.userauthentication.mapper;

import in.programming.userauthentication.domain.UserEntity;
import in.programming.userauthentication.dto.UserDto;
import in.programming.userauthentication.model.CreateUserRequestModel;
import org.mapstruct.*;

@Mapper(nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public interface UserMapper {


    UserEntity mapToUserEntity(UserDto userDetails);

    UserDto mapToUserDto(UserEntity entity);
    UserDto mapToUserDto(CreateUserRequestModel userDetails);
}
