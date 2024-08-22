package com.study.SpringSecurity.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@EnableWebSecurity // 직접 SecurityConfig를 설정하겠다.
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Bean //암호화를 할때마다 엔코더를 생성하지 않기 위해 bean으로 등록
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override //서버가 실행되면 실행되는 초기 세팅들
    protected void configure(HttpSecurity http) throws Exception {
        http.formLogin().disable(); //formLogin 사용X
        http.httpBasic().disable(); //basicLogin 사용X
        http.csrf().disable(); //csrf: 위조 방지 스티커(토큰)

        //http.sessionManagement().disable();
        //스프링 시큐리티가 세션을 생성하지도 않고 기존의 세션을 사용하지도 않겠다.

        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        //스프링 시큐리티가 세션을 생성하지 않겠다. 기존의 세션을 완전히 사용하지 않겠다는 뜻은 아님.
        //JWT 등의 토큰 인증방식을 사용할 때 설정하는 것.

        http.cors(); //crossOrigin(다른 서버에서 요청 허용) 사용
        http.authorizeRequests() 
                .antMatchers("/auth/**", "/h2-console/**", "/test/**") //요청을 받을 주소
                .permitAll() //antMatchers 설정한 주소의 접근을 인증절차 없이 허용
                .anyRequest() //위 주소 외의 주소로 오는 모든 요청들은
                .authenticated() //인증을 완료해야 접근이 가능하다.
                .and()
                .headers()
                .frameOptions()
                .disable();
    }
}