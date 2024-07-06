package com.daocao.support.config.security;


import com.daocao.support.filter.JwtAuthticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Collections;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    private final SysUserDetailsService sysUserDetailsService;
    private final JwtAuthticationFilter jwtAuthticationFilterl;

    public SecurityConfig(SysUserDetailsService sysUserDetailsService, JwtAuthticationFilter jwtAuthticationFilterl) {
        this.sysUserDetailsService = sysUserDetailsService;
        this.jwtAuthticationFilterl = jwtAuthticationFilterl;
    }

    /**
     * 配置过滤连
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, JwtAuthticationFilter jwtAuthticationFilter) throws Exception{
        //禁用csrf
        http.csrf(csrf -> csrf.disable());
        //配置拦截策略
        http.authorizeHttpRequests(auth -> auth.requestMatchers("/**").permitAll().anyRequest().authenticated());
        //开启form认证
        http.formLogin(Customizer.withDefaults());
        //配置跨域
        http.cors(cors -> cors.configurationSource(configurationSource()));
        // 配置过滤器
        http.addFilterBefore(jwtAuthticationFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    //创建AuthenticationManager
    @Bean
    public AuthenticationManager sysUserAuthenticationManager(){
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(passwordEncoder());
        provider.setUserDetailsService(sysUserDetailsService);
        return new ProviderManager(provider);
    }
    //配置密码编译器
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    // 配置跨域，允许跨域
    public CorsConfigurationSource configurationSource(){
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.setAllowedMethods(Collections.singletonList("*"));
        corsConfiguration.setAllowedHeaders(Collections.singletonList("*"));
        corsConfiguration.setAllowedOrigins(Collections.singletonList("*"));

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfiguration);
        return source;
    }
}
