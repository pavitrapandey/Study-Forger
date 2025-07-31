package com.studyForger.Study_Forger.Configuration;

import com.fasterxml.jackson.databind.SerializationFeature;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

@Configuration
public class AppConfig {




    @Bean
    public ModelMapper modelMapper(){
        return new ModelMapper();
    }
}
