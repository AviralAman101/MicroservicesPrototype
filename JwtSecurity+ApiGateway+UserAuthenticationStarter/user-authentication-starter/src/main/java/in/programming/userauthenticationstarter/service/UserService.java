package in.programming.userauthenticationstarter.service;

import in.programming.userauthenticationstarter.dto.UserDto;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {

    UserDto getUserObject();
}
