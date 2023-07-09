package com.parlakci.ciftlikbank.adapter.redis;

import com.parlakci.ciftlikbank.application.port.TicketCachePort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class TicketCacheRedisAdapter implements TicketCachePort {

    private final RedisTemplate<String, BigDecimal> redisTemplate;

    @Override
    public void saveTicket(String ticket, BigDecimal rate, Duration ttlInSeconds) {
        redisTemplate.opsForValue().set(ticket, rate, ttlInSeconds);
    }

    @Override
    public Optional<BigDecimal> retrieveTicket(String ticket) {
        return Optional.ofNullable(redisTemplate.opsForValue().get(ticket));
    }
}
