package in.programming.springcloudapigateway;

import io.jsonwebtoken.Jwts;
import org.apache.http.HttpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;


@Component
public class AuthorizationHeaderFilter extends AbstractGatewayFilterFactory<AuthorizationHeaderFilter.Config> {

    @Autowired
    private Environment env;

    public AuthorizationHeaderFilter() {
        super(Config.class);
    }

    public static class Config{
    }

    @Override
    public GatewayFilter apply(Config config) {
        return ((exchange, chain) -> {
            ServerHttpRequest request = exchange.getRequest();
            if(!request.getHeaders().containsKey(HttpHeaders.AUTHORIZATION)){
                return onError(exchange, "No authorization header", HttpStatus.UNAUTHORIZED);
            }


            String token = request.getHeaders().get(HttpHeaders.AUTHORIZATION).get(0);
            String jwt = token.replace("Bearer ","");
            if(!isJwtValid(jwt)){
                return onError(exchange, "Invalid Jwt Token", HttpStatus.UNAUTHORIZED);
            }
            return chain.filter(exchange);
        });
    }

    private Mono<Void> onError(ServerWebExchange exchange, String no_authorization_header, HttpStatus unauthorized) {
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(unauthorized);
        return response.setComplete();
    }

    private boolean isJwtValid(String jwtToken){
        boolean returnValue = true;

        String subject = Jwts.parser()
                .setSigningKey(env.getProperty("token.secret"))
                .parseClaimsJwt(jwtToken)
                .getBody()
                .getSubject();
        if(subject == null || subject.isEmpty()){
            returnValue = false;
        }
        return returnValue;
    }

}
