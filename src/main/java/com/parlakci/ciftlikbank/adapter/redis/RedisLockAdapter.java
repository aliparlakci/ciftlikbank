package com.parlakci.ciftlikbank.adapter.redis;

import com.parlakci.ciftlikbank.application.port.LockPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;


@Slf4j
@Service
@RequiredArgsConstructor
public class RedisLockAdapter implements LockPort {

    private final RedisTemplate<String, String> redisTemplate;

    @Override
    public void tryLock(String key) {

    }

    @Override
    public void tryLock(String firstKey, String secondKey) {

    }

    @Override
    public void unlock(String key) {

    }

    @Override
    public void unlock(String firstKey, String secondKey) {

    }
}
