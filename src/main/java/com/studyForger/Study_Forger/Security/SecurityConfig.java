package com.studyForger.Study_Forger.Security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@EnableWebSecurity(debug = true)
@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {


        httpSecurity
                 .csrf(AbstractHttpConfigurer::disable)
                .cors(Customizer.withDefaults())
        .authorizeHttpRequests(
                request -> request
                        //User related endpoints
                        .requestMatchers("/api/users/image/**").hasRole("NORMAL")
                        .requestMatchers(HttpMethod.DELETE, "/api/users/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/api/users/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.POST, "/api/users").permitAll()
                        .requestMatchers(HttpMethod.PUT,"/api/users/**").hasAnyRole("NORMAL", "ADMIN")

                        //Subject related endpoints
                        .requestMatchers("/api/users/*/subjects/**").hasRole("NORMAL")

                        //Topic related endpoints
                        .requestMatchers("/api/subjects/*/topics/**").hasRole("NORMAL")
                        .requestMatchers("/api/user/*/topic").hasRole("NORMAL")

                        //Revision related endpoints
                        .requestMatchers("/api/revisions/**").hasRole("NORMAL")

                        //Dashboard related endpoints
                        .requestMatchers("/api/dashboard/**").hasRole("NORMAL")

                        // Allow error fallback route
                        .requestMatchers("/error**").permitAll()

                        // Everything else is denied unless explicitly allowed
                        .anyRequest().authenticated()
        ).sessionManagement(sessions -> sessions .sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        httpSecurity.httpBasic(Customizer.withDefaults());
//        httpSecurity.formLogin(Customizer.withDefaults());

        return httpSecurity.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOrigins("http://localhost:5173") // React app
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                        .allowedHeaders("*")
                        .allowCredentials(true);
            }
        };
    }
}
