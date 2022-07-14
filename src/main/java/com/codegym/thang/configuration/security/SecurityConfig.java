package com.codegym.thang.configuration.security;

import com.codegym.thang.configuration.CustomAccessDeniedHandler;
import com.codegym.thang.configuration.JWTProvider;
import com.codegym.thang.configuration.JwtAuthenticationFilter;
import com.codegym.thang.configuration.RestAuthenticationEntryPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.Filter;

public class SecurityConfig {
    @Autowired
    private RestAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    @Autowired
    private JWTProvider jwtProvider;

    @Bean
    public PasswordEncoder passwordEncoder() { //bean mã hóa pass người dùng
        return new BCryptPasswordEncoder();
    }


    @Bean
    public CustomAccessDeniedHandler customAccessDeniedHandler() {  //Cấu hình lại lỗi không có quyền truy cập
        return new CustomAccessDeniedHandler();
    }
    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter() {
        return new JwtAuthenticationFilter();
    }
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().ignoringAntMatchers("/**"); // vô hiệu hóa csrf cho 1 số đường dẫn nhất định
        http.httpBasic().authenticationEntryPoint(jwtAuthenticationEntryPoint);//Tùy chỉnh lại thông báo 401 thông qua class restEntryPoint
        http.authorizeRequests()
                .antMatchers("/login",
                        "/register", "/**").permitAll() // tất cả truy cập được
                .anyRequest().authenticated()  //các request còn lại cần xác thực
                .and().csrf().disable(); // vô hiệu hóa bảo vệ của csrf (kiểm soát quyền truy cập)
        http.addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class) // lớp filter kiểm tra chuỗi jwt
                .exceptionHandling().accessDeniedHandler(customAccessDeniedHandler()); //xử lý ngoaoj lệ khi không có quyền truy cập
        http.sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
//        http.cors();// ngăn chăn truy cập từ miền khác
    }

}
