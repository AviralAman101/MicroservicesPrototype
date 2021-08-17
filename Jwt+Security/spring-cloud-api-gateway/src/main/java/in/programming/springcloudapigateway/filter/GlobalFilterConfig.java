package in.programming.springcloudapigateway.filter;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.bouncycastle.jcajce.BCFKSLoadStoreParameter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Date;

@Configuration
public class GlobalFilterConfig {

    @Autowired
    Environment env;

    final Logger log = LoggerFactory.getLogger(GlobalFilterConfig.class);

    @Bean
    public GlobalFilter myFilter(){
        return ((exchange, chain) -> {
            String  token= Jwts.builder()
                    .setSubject("I'm a random string")
                    .setExpiration(new Date(System.currentTimeMillis()+Long.parseLong("84000000")))
                    .signWith(SignatureAlgorithm.HS512, "Top-secret")
                    .compact();


            ServerHttpRequest request = exchange.getRequest().mutate().header("token", token).build();
            ServerWebExchange exchange1 = exchange.mutate().request(request).build();
            return chain.filter(exchange1);
        });

//            log.info("Pre Filter Api Gateway");
//            return chain.filter(exchange).then(Mono.fromRunnable(() ->{
//                log.info("My Poast filter Api gateway");
//            }));
//        });
    }
}
