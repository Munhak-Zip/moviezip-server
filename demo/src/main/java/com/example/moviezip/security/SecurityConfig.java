package com.example.moviezip.security;

import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity // Spring Security 활성화
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    // 정적 자원에 대해서는 Security 설정을 적용하지 않음.
    @Override
    public void configure(WebSecurity web) {
        web.ignoring().requestMatchers(PathRequest.toStaticResources().atCommonLocations());
    }

    @Bean // 암호화
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        // 인가(접근권한) 설정 (index : 모든 링크(사용자)에 대해 허용을 해 준 상태, 권한관리필터)
        http
                .cors(withDefaults()) // CORS 설정 활성화
                .authorizeRequests(authorizeRequests ->
                        authorizeRequests
                                .antMatchers("/", "/loginProc", "/join", "/joinProc","/session-expired","/findUserId", "/checkExistsId", "/changePassword").permitAll()
                                .antMatchers("/getId").authenticated() // 인증된 사용자만 접근할 수 있도록 설정
                                .anyRequest().authenticated() // 그 외 모든 요청은 인증 필요
                )
                .formLogin(formLogin ->
                        formLogin
                                .loginProcessingUrl("/loginProc") // 로그인 처리 URL
                                .usernameParameter("username") // 로그인 폼에서 사용할 사용자명 파라미터 이름과 일치하도록
                                .passwordParameter("password") // 로그인 폼에서 사용할 비밀번호 파라미터 이름과 일치하도록
                                .successHandler(successHandler()) // 로그인 성공 시 동작
                                .failureHandler(failureHandler()) // 로그인 실패 시 동작
                                .permitAll() // 로그인 페이지는 누구나 접근 가능
                )
                .sessionManagement(sessionManagement ->
                        sessionManagement
                                .invalidSessionUrl("/session-expired") // 세션 만료 시 이동할 URL
                                .maximumSessions(1) // 하나의 세션만 허용
                                .expiredUrl("/session-expired") // 세션 만료 시 이동할 URL
                )
                .logout(logout ->
                        logout
                                .logoutUrl("/logout")   // 로그아웃 처리 URL (= form action url)
                                //.logoutSuccessUrl("/login") // 로그아웃 성공 후 targetUrl,
                                // logoutSuccessHandler 가 있다면 효과 없으므로 주석처리.
                                .addLogoutHandler((request, response, authentication) -> {
                                    // 사실 굳이 내가 세션 무효화하지 않아도 됨.
                                    // LogoutFilter가 내부적으로 해줌.
                                    HttpSession session = request.getSession();
                                    if (session != null) {
                                        session.invalidate();  // 세션 무효화
                                    }
                                })  // 로그아웃 핸들러 추가
                                .logoutSuccessHandler((request, response, authentication) -> {
                                }) // 로그아웃 성공 핸들러
                                .deleteCookies("JSESSIONID","remember-me") // 로그아웃 후 삭제할 쿠키 지정
                )
                .csrf(csrf ->
                        csrf.disable() // CSRF 보호 비활성화
                );
    }

    @Bean
    public AuthenticationSuccessHandler successHandler() {

        //기본적으로 인증 성공 후 특정 URL로 리다이렉트하는 기능을 제공하는 클래스이지만,
        // 여기서는 해당 클래스의 기본 동작을 오버라이드하여 리다이렉트가 아닌 HTTP 상태 코드만 변경하는 방식으로 커스터마이징
        return new SimpleUrlAuthenticationSuccessHandler() {
            @Override
            public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                                org.springframework.security.core.Authentication authentication) throws IOException, ServletException {
                response.setStatus(HttpServletResponse.SC_OK);
            }
        };
    }

    @Bean
    public AuthenticationFailureHandler failureHandler() {
        return new SimpleUrlAuthenticationFailureHandler();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.addAllowedOrigin("http://localhost:3000");
        configuration.addAllowedMethod("*");
        configuration.addAllowedHeader("*");
        configuration.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
