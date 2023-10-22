package com.example.mongodbconnection;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MongoDBController {
    private final MongoTemplate mongoTemplate;

    @Bean
    void Init(){
        mongoTemplate.createCollection("name");
    }

}
