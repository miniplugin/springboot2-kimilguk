package com.edu.springboot2.config;

import com.edu.springboot2.auth.Role;
import com.edu.springboot2.service.oauth2.CustomOAuth2UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.sql.DataSource;

/**
 * 이 클래스는 스프링 시큐리티 이용 로그인 및 권한부여 하는 기능
 */
@RequiredArgsConstructor //이 어노테이션은 초기화 되지않은 final 객체에 대해 생성자를 생성해 줍니다.
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private DataSource dataSource;//데이터베이스에서 쿼리로 인증 결과를 사용하는 객체로 사용
    private final CustomOAuth2UserService customOAuth2UserService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //super.configure(http);
        http
                .csrf().disable().headers().frameOptions().disable().and()
                .authorizeRequests()
                .antMatchers("/posts/read/**").permitAll()
                .antMatchers("/mypage/**").hasRole(Role.USER.name())
                .antMatchers("/simple_users/**").hasRole(Role.ADMIN.name())
                .antMatchers("/api/v1/**","/posts/**").hasAnyRole(Role.USER.name(),Role.ADMIN.name())
                .antMatchers("/**").permitAll()
                .anyRequest().authenticated()
                .and()//여기부터 폼Login 로그인처리
                .formLogin().loginPage("/login").failureUrl("/login?message=error").permitAll()//로그인페이지 추가
                .defaultSuccessUrl("/")//추가
                .and()
                .logout().invalidateHttpSession(true)
                .logoutSuccessUrl("/")//여기까지가 DB 로그인처리
                .and()//여기부터 OAuth 로그인처리
                .oauth2Login()
                .loginPage("/login")//로그인페이지 추가
                .userInfoEndpoint()
                .userService(customOAuth2UserService);//네이버 API OAuth2 를 처리할 서비스 지정
    }
    //인증 매니저(Authentication Manager)가 인증에 대한 실제적 처리를 담당합니다.(아래)
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.jdbcAuthentication()
                .dataSource(dataSource)
                .rolePrefix("ROLE_")
                .usersByUsernameQuery("select username, replace(password, '$2y', '$2a'), true from simple_users where username = ?")
                .authoritiesByUsernameQuery("select username, role from simple_users where username = ?");
    }
    // passwordEncoder() 추가
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
