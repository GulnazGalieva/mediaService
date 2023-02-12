package com.example.mediaservice.config;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static org.modelmapper.config.Configuration.AccessLevel.PRIVATE;

@Configuration
public class ModelMapperConfig {
    @Bean
    public ModelMapper modelMapper(){
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration()
                .setMatchingStrategy(MatchingStrategies.STRICT)//пропуcкает, если только все совпадает
                .setFieldMatchingEnabled(true)//сравнивает все поля
                .setSkipNullEnabled(true)// пропускает пустые поля
                .setFieldAccessLevel(PRIVATE);// поля делает приватными
        return mapper;
    }
}
