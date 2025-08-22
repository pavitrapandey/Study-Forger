package com.studyForger.Study_Forger.Security.Jwt;

import com.studyForger.Study_Forger.Security.RefreshToken.RefreshTokenDto;
import com.studyForger.Study_Forger.Security.RefreshToken.RefreshTokenRequest;
import com.studyForger.Study_Forger.Security.RefreshToken.RefreshTokenService;
import com.studyForger.Study_Forger.User.User;
import com.studyForger.Study_Forger.User.UserDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class JwtAuthenticationController{

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtHelper jwtHelper;

    @Autowired
    private UserDetailsService userDetails;

    @Autowired
    private RefreshTokenService refreshTokenService;

    @Autowired
            private ModelMapper mapper;

    Logger logger= LoggerFactory.getLogger(JwtAuthenticationController.class);
    //Generate JWT Token
    @PostMapping("/generate-token")
    @Operation(method = "POST",description = "Generate a JWT token for a user")
    @ApiResponses(value={
            @ApiResponse(description = "Token generated successfully", responseCode = "200"),
            @ApiResponse(description = "Invalid credentials", responseCode = "401")
    })
    public ResponseEntity<JwtResponse> generateToken( @RequestBody JwtRequest request){
        // Logic to generate JWT token
        logger.info("Generating token for user: {}", request.getUsername());

        this.doAuthentication(request.getUsername(), request.getPassword());

        User user=(User)userDetails.loadUserByUsername(request.getUsername());

        RefreshTokenDto refreshTokenDto=refreshTokenService.createRefreshToken(user.getUsername());

        String token=jwtHelper.generateToken(user);

        JwtResponse response=JwtResponse.builder()
                .token(token)
                .user(user.getUsername())
                .refreshToken(refreshTokenDto)
                .build();

        return new ResponseEntity<>(response, HttpStatus.OK); // Replace with actual implementation
    }

    private void doAuthentication(String username, String password){
        try {
            Authentication authentication= new UsernamePasswordAuthenticationToken(username,password);
            authenticationManager.authenticate(authentication);
            logger.info("Authentication successful for user: {}", username);
        }catch (BadCredentialsException ex){
            throw new BadCredentialsException("Invalid username or password");
        }
    }


    @PostMapping("/regenerate-token")
    @Operation(method = "POST",description = "Regenerate a JWT token for a user using refresh token")
    @ApiResponses(value={
            @ApiResponse(description = "Token generated successfully", responseCode = "200"),
            @ApiResponse(description = "Invalid refresh token", responseCode = "401")
    })
    private ResponseEntity<JwtResponse> regenerateToken(@RequestBody RefreshTokenRequest request){
       RefreshTokenDto tokenDto= refreshTokenService.findByToken(request.getRefreshToken());
       RefreshTokenDto verified=refreshTokenService.verifyToken(tokenDto);
       UserDto user=refreshTokenService.getUser(verified);
       String jwtToken=jwtHelper.generateToken(mapper.map(user,User.class));

       JwtResponse response=JwtResponse.builder()
               .refreshToken(verified)
               .token(jwtToken)
               .user(user.getUsername())
               .build();

       return new ResponseEntity<>(response,HttpStatus.OK);
    }
}
