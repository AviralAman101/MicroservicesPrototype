package in.programming.userauthenticationstarter.service.impl;

import in.programming.userauthenticationstarter.dto.UserDto;
import in.programming.userauthenticationstarter.service.UserService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private BCryptPasswordEncoder encoder;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {

        //Check username in db?
        if(StringUtils.equalsIgnoreCase(s,"Admin"))
            return new User(s, encoder.encode("admin"),true,true,true,
                    true,
                    Collections.emptyList());
        throw new UsernameNotFoundException("Not authorized ie. Unauthorized");
    }

    @Override
    public UserDto getUserObject() {
        //Query Repo to getDetails
        UserDto dto =new UserDto();
        dto.setUserName("admin");
        dto.setPassword("admin");
        dto.setEmail("admin");
        dto.setPhoneNumber("admin");
        return dto;
    }
}
