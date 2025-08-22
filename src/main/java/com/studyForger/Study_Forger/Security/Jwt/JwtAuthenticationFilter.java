package com.studyForger.Study_Forger.Security.Jwt;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import jakarta.annotation.Nonnull;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    protected JwtHelper helper;

    @Autowired
    protected UserDetailsService userDetailsService;

    Logger logger = LoggerFactory.getLogger(JwtAuthenticationFilter.class);

    @Override
    protected void doFilterInternal(HttpServletRequest request, @Nonnull HttpServletResponse response, @Nonnull FilterChain filterChain) throws ServletException, IOException {
        //api se phle chlega Jwt ko verifiy krne ke liye

        //Authorization : bearer dkjxdidjw
        String authorizationHeader = request.getHeader("Authorization");
        logger.info(STR."Authorization Header: \{authorizationHeader}");

        String username = null;
        String token=null;
        if(authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {

            token = authorizationHeader.substring(7);
            try {

                username = helper.getUsernameFromToken(token);
               logger.info(" Username: " , username);

            }catch (IllegalArgumentException e){
                logger.info("Invalid token", e.getMessage());
            }catch (ExpiredJwtException e){
                logger.info("Token has expired");
            }catch (MalformedJwtException e){
                logger.info("Error while verifying token: " , e.getMessage());
            }catch (Exception ex){
                ex.printStackTrace();
            }

        }else {
            logger.info("Authorization header is missing or not in the correct format");
        }


        if(username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
           if(helper.validateToken(token,userDetails)){
               UsernamePasswordAuthenticationToken authenticationToken=new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
               authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
               SecurityContextHolder.getContext().setAuthentication(authenticationToken);
           }
        }

        filterChain.doFilter(request, response);
    }
}
