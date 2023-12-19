package com.fcc.PureSync.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        //Authorization 헤더를 통한 인증 사용하지 않음
        http.httpBasic(config -> config.disable());
        //폼을 통한 인증 사용하지 않음
        http.formLogin(config -> config.disable());
        //CORS 설정
        http.cors(config -> config.disable());
        //사이트간 요청 위조 방지 비활성화
        http.csrf(config -> config.disable());
        //서버 세션 비활성화
        http.sessionManagement(management -> management
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        //요청 경로별 권한 설정
        http.authorizeHttpRequests(customizer -> customizer
                        //방법1
//                .antMatchers("/api/**").permitAll() //회원만 가능
//                .antMatchers("/user/**").hasAuthority("ROLE_USER") //회원만 가능
//                .antMatchers("/doctor/**").hasAuthority("ROLE_DOCTOR") //의사만 가능
//                .antMatchers("/hoslital/**").hasAuthority("ROLE_HADMIN") //병원 관리자만 가능
//                .antMatchers(("/doctorAdd")).permitAll()
                        //방법2
                        //.antMatchers(HttpMethod.GET, "/board/list").hasAuthority("ROLE_USER") //ROLE_생략하면 안됨
                        //.antMatchers(HttpMethod.POST, "/board/create").hasAnyRole("USER") //ROLE_ 붙이면 안됨
                        //그 이외의 모든 경로 허가
                        .anyRequest().permitAll())
                .logout(logout ->logout.deleteCookies().logoutUrl("/api/member/logout").logoutSuccessUrl("/")); //위에 사이트 외에는 권한다 허용
        return http.build();
    }
    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

}