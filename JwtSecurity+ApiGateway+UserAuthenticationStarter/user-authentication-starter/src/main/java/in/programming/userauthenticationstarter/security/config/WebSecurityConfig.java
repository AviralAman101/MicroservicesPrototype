package in.programming.userauthenticationstarter.security.config;

import in.programming.userauthenticationstarter.service.UserService;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.servlet.Filter;


@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    Environment env;
    UserService usersService;
    PasswordEncoder bCryptPasswordEncoder;

    public WebSecurityConfig(UserService usersService, PasswordEncoder bCryptPasswordEncoder
    ,Environment env) {
        this.usersService = usersService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.env = env;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.authorizeRequests().antMatchers("/users/**").permitAll()
                .and()
                .addFilter(getAutheticationFilter());
        http.headers().frameOptions().disable();
    }

    private Filter getAutheticationFilter() throws Exception {
        AuthenticationFilter filter = new AuthenticationFilter(usersService,env,authenticationManager());
        return filter;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(usersService).passwordEncoder(bCryptPasswordEncoder);
    }
}
