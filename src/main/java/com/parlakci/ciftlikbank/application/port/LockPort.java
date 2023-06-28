package com.parlakci.ciftlikbank.application.port;

public interface LockPort {

    void tryLock(String key);

    void tryLock(String firstKey, String secondKey);

    void unlock(String key);

    void unlock(String firstKey, String secondKey);

}
