package com.leisure.config.mapping;

import com.leisure.entity.mapping.AdminMapper;
import com.leisure.entity.mapping.ClientMapper;
import com.leisure.entity.mapping.TeamMapper;
import com.leisure.entity.mapping.UserMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration("enhancedModelMapperConfiguration")

public class MappingConfiguration {
    @Bean
    public EnhancedModelMapper modelMapper() {
        return new EnhancedModelMapper();
    }
    @Bean
    public AdminMapper adminMapper(){
        return new AdminMapper();
    }
    @Bean
    public ClientMapper clientMapper(){
        return new ClientMapper();
    }
    @Bean
    public TeamMapper teamMapper(){
        return new TeamMapper();
    }
    @Bean
    public UserMapper userMapper(){
        return new UserMapper();
    }
}
