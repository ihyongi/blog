package com.my.blog.config;

import com.my.blog.config.auth.PrincipalDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

//빈등록: 스프링컨테이너에서 객체를 관리할수있게 하는것
@Configuration
@EnableWebSecurity //시큐리티필터를 추가->스프링시큐리티가 활성화 되어있는데 어떤 설정을 여기서 하겠다는거
@EnableGlobalMethodSecurity(prePostEnabled = true)//특정주소로 접근을 하면 권한 및 인증을 미리 체크하겠다
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private PrincipalDetailService principalDetailService;

    //시큐리티가 대신 로그인하면 패스워드 가로채기를 하는데 해당 패스워드가 뭘로 해시가 되어 회원가입이 되었는지 알아야
    //같은해쉬로 암호화해서 DB에 있는 해쉬랑 비교할 수 있음 --null자리에 객체에게 알려줘야됌
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception{
        auth.userDetailsService(principalDetailService).passwordEncoder(encodePWD());
    }

    //해싱 --IoC가 됨.. 스프링이 관리
    @Bean
    public BCryptPasswordEncoder encodePWD(){
        String encPassword=new BCryptPasswordEncoder().encode("1234");
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception{
        http
                .csrf().disable() //csrf토큰 비활성화
                .authorizeRequests()
                    .antMatchers("/","/auth/**","/js/**","/css/**","/image/**")
                    .permitAll()
                    .anyRequest()
                    .authenticated()
                .and()
                    .formLogin()
                    .loginPage("/auth/loginForm") //인증이 되지않은 요청은 무조건 여기로
                    .loginProcessingUrl("/auth/loginProc") //스프링시큐리티가 로그인을 가로채서 대신로그인 해줌
                    .defaultSuccessUrl("/");
        //이쪽으로 들어오는건 누구가 가능, 이쪽이 아니면 인증을 받아야돼
    }
}
