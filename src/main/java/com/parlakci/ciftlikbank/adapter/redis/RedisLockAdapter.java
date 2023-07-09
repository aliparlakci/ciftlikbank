package com.parlakci.ciftlikbank.adapter.redis;

import com.parlakci.ciftlikbank.application.port.LockPort;
import org.springframework.stereotype.Service;

@Service
public class RedisLockAdapter implements LockPort {

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
