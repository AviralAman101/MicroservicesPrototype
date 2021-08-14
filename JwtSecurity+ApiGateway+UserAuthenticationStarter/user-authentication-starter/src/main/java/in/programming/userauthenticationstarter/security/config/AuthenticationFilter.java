package in.programming.userauthenticationstarter.security.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import in.programming.userauthenticationstarter.dto.LoginRequest;
import in.programming.userauthenticationstarter.dto.UserDto;
import in.programming.userauthenticationstarter.service.UserService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.http.HttpHeaders;
import java.util.ArrayList;
import java.util.Date;

public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    UserService userService;
    Environment env;

    public AuthenticationFilter(UserService userService, Environment env, AuthenticationManager manager) {
        this.userService = userService;
        this.env = env;
        super.setAuthenticationManager(manager);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request,
                                                HttpServletResponse response) throws AuthenticationException {
        try{
            LoginRequest creds = new ObjectMapper()
                    .readValue(request.getInputStream(),LoginRequest.class);
            return getAuthenticationManager().authenticate(
                    new UsernamePasswordAuthenticationToken(
                            creds.getName(),
                            creds.getPassword(),
                            new ArrayList<>()
                    )
            );

        } catch (IOException e){
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response,
                                            FilterChain chain,
                                            Authentication authResult) throws IOException, ServletException {
        String userName = ((User) authResult.getPrincipal()).getUsername();
        UserDto user =  userService.getUserObject();
        String token = Jwts.builder()
                .setSubject(user.getUserName())
                .setExpiration(new Date(System.currentTimeMillis() +
                        Long.parseLong(env.getProperty("token.expiration.milliseconds"))))
                .signWith(SignatureAlgorithm.HS512, env.getProperty("token.secret"))
                .compact();
        response.addHeader("Authorization","Bearer "+token);
        response.addHeader("User","Inspector");

    }
}
