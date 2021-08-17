package in.programming.userauthentication.controller.validation;


import feign.RequestInterceptor;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.context.annotation.Bean;

import java.util.Date;

public class FeignConfig {

    @Bean
    public RequestInterceptor requestInterceptor() {
        return requestTemplate -> {
            String  token= Jwts.builder()
                    .setSubject("I'm a random string")
                    .setExpiration(new Date(System.currentTimeMillis()+Long.parseLong("84000000")))
                    .signWith(SignatureAlgorithm.HS512, "Top-secret")
                    .compact();

            requestTemplate.header("token", token);
            requestTemplate.header("Accept", "application/json");
            requestTemplate.header("header_1", "value_1");
            requestTemplate.header("header_2", "value_2");
            requestTemplate.header("header_3", "value_3");
        };
    }

}
