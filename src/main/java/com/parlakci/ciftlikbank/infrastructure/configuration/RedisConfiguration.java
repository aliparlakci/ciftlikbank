package com.parlakci.ciftlikbank.infrastructure.configuration;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.math.BigDecimal;

@Configuration
@RequiredArgsConstructor
public class RedisConfiguration {

    @Bean
    public LettuceConnectionFactory redisConnectionFactoryForCluster() {
        return new LettuceConnectionFactory();
    }

    @Bean
    public RedisTemplate<String, BigDecimal> redisTemplate(LettuceConnectionFactory lettuceConnectionFactory) {
        final RedisTemplate<String, BigDecimal> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(lettuceConnectionFactory);
        redisTemplate.setDefaultSerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new GenericJackson2JsonRedisSerializer());
        return redisTemplate;
    }

}
