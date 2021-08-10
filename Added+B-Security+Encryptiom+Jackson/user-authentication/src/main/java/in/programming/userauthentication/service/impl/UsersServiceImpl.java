package in.programming.userauthentication.service.impl;

import in.programming.userauthentication.domain.UserEntity;
import in.programming.userauthentication.dto.UserDto;
import in.programming.userauthentication.mapper.UserMapper;
import in.programming.userauthentication.model.CreateUserRequestModel;
import in.programming.userauthentication.repository.UserRepository;
import in.programming.userauthentication.service.UsersService;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UsersServiceImpl implements UsersService {

    UserMapper mapper = Mappers.getMapper(UserMapper.class);

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDto create(UserDto userDetails) {
        userDetails.setUserId(UUID.randomUUID().toString());
        UserEntity entity = mapper.mapToUserEntity(userDetails);
        entity.setEncryptedPassword("test");
        return  mapper.mapToUserDto(userRepository.save(entity));
    }
}
