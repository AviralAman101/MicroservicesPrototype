package in.programming.testfeign.filter;

import io.jsonwebtoken.Jwts;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@Component
public class ValidationEx implements HandlerInterceptor {

    Logger log = LoggerFactory.getLogger(ValidationEx.class);

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
