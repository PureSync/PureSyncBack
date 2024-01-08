package com.fcc.PureSync.core.config;

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
    /*
    사용자 그리고 포트에 따른 필터 메소드 분리

     */

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    private void configureCommonAuthentication(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.httpBasic(config -> config.disable()); //폼기반시 비활성화
        httpSecurity.cors(config -> config.disable());
        httpSecurity.csrf(config -> config.disable());
    }

    //관리자 측 폼
    @Bean
    public SecurityFilterChain filterChainForAdmin(HttpSecurity httpSecurity) throws Exception {
        configureCommonAuthentication(httpSecurity);
        httpSecurity
                .authorizeHttpRequests(admin -> admin
                        .requestMatchers("/admin/login","/img/**","/js/**","/assets/**").permitAll()
                        .requestMatchers("/admin/**", "/").hasAnyAuthority(UserRoleConfig.UserRole.ADMIN.toString())
                        .requestMatchers("/**").permitAll()

                )
                .formLogin(form -> form
                        .loginPage("/admin/login")
                        .usernameParameter("memId")
                        .passwordParameter("memPassword")
                        .loginProcessingUrl("/admin/login")
                        .defaultSuccessUrl("/"))
                .logout(logout -> logout
                        .logoutUrl("/admin/logout")
                        .deleteCookies("JSESSIONID")
                        .logoutSuccessUrl("/admin/login")
//                        .invalidateHttpSession
                )
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED))
                .portMapper(portMapper -> portMapper
                        .http(4000)
                        .mapsTo(4443));
        return httpSecurity.build();
    }

    //사용자 측 JWT
    @Bean
    public SecurityFilterChain filterChainForMember(HttpSecurity httpSecurity) throws Exception {
        configureCommonAuthentication(httpSecurity);
        httpSecurity
                .authorizeHttpRequests(user -> user
                        .requestMatchers("/api/member/**", "/landing","/member/**").permitAll()
                        .requestMatchers("/api/**").hasAnyAuthority(UserRoleConfig.UserRole.USER.toString()))
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .portMapper(portMapper -> portMapper
                        .http(9000)
                        .mapsTo(9443))
                .formLogin(config-> config.disable());
        return httpSecurity.build();
    }


}