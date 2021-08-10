package in.programming.userauthentication.controller;

import in.programming.userauthentication.dto.UserDto;
import in.programming.userauthentication.mapper.UserMapper;
import in.programming.userauthentication.model.CreateUserRequestModel;
import in.programming.userauthentication.model.ResponseModel;
import in.programming.userauthentication.service.UsersService;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import javax.validation.Valid;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserMapper mapper = Mappers.getMapper(UserMapper.class);

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private UsersService usersService;
    @GetMapping("/status")
    public String welcoe(){
        return "hello world";
    }

    @PostMapping("/create")
    public ResponseEntity<ResponseModel> createUser(@Valid @RequestBody CreateUserRequestModel userDetails){
       UserDto userDto = mapper.mapToUserDto(userDetails);
        userDto.setEncryptedPassword(bCryptPasswordEncoder.encode(userDetails.getPassword()));
        ResponseModel responseModel = new ResponseModel();
        responseModel.setResponse(usersService.create(userDto).toString());
        return ResponseEntity.status(HttpStatus.CREATED).body(responseModel);
    }
}
