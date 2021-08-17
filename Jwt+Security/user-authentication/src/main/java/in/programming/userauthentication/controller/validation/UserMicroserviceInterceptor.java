package in.programming.userauthentication.controller.validation;

import io.jsonwebtoken.Jwts;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class  UserMicroserviceInterceptor implements HandlerInterceptor {

    @Autowired
    Environment env;

    Logger log = LoggerFactory.getLogger(UserMicroserviceInterceptor.class);

    @Override
    public boolean preHandle(
            HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        log.info("logging Uri {}",request.getRequestURL());

        if(request.getHeader("token")==null || request.getHeader("token").isEmpty()){
            return false;
        }
        String myStr = Jwts.parser()
                .setSigningKey("Top-secret")
                .parseClaimsJws(request.getHeader("token"))
                .getBody()
                .getSubject();
        log.info("This is subject : {}",myStr);
        return true;
    }
    @Override
    public void postHandle(
            HttpServletRequest request, HttpServletResponse response, Object handler,
            ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response,
                                Object handler, Exception exception) throws Exception {}
}
