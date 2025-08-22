package com.studyForger.Study_Forger.Security;

import com.studyForger.Study_Forger.Security.Jwt.JwtAuthenticationEntryPoint;
import com.studyForger.Study_Forger.Security.Jwt.JwtAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@EnableWebSecurity(debug = true)
@Configuration
public class SecurityConfig {

    @Autowired
    private JwtAuthenticationEntryPoint authenticationEntryPoint;

    @Autowired
    private JwtAuthenticationFilter authenticationFilter;

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

                        //Authentication
                        .requestMatchers(HttpMethod.POST,"/api/auth/generate-token","/api/auth/regenerate-token").permitAll()
                        .requestMatchers("/api/auth/**").authenticated()

                        // Allow error fallback route
                        .requestMatchers("/error**").permitAll()

                        // Everything else is denied unless explicitly allowed
                        .anyRequest().authenticated()
        );

        //expection handle
        httpSecurity.exceptionHandling(ex->ex.authenticationEntryPoint(authenticationEntryPoint));

        //Session management
        httpSecurity.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        //Authentication
        httpSecurity.addFilterBefore(authenticationFilter, UsernamePasswordAuthenticationFilter.class);

//

        return httpSecurity.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}
