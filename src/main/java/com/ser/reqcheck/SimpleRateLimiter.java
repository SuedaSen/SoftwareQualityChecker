package com.ser.reqcheck;

import java.time.Clock;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Very small in-memory sliding-window rate limiter.
 * Good enough for a single-instance demo deployment.
 */
public final class SimpleRateLimiter {

    private final Map<String, Deque<Long>> hitsByKey = new ConcurrentHashMap<>();
    private final Clock clock;
    private final int maxRequestsPerMinute;

    public SimpleRateLimiter(int maxRequestsPerMinute) {
        this(maxRequestsPerMinute, Clock.systemUTC());
    }

    SimpleRateLimiter(int maxRequestsPerMinute, Clock clock) {
        this.maxRequestsPerMinute = Math.max(1, maxRequestsPerMinute);
        this.clock = clock;
    }

    /** Returns true if request is allowed, otherwise false. */
    public boolean allow(String key) {
        long now = clock.millis();
        long cutoff = now - 60_000L;

        Deque<Long> q = hitsByKey.computeIfAbsent(key == null ? "" : key, k -> new ArrayDeque<>());
        synchronized (q) {
            while (!q.isEmpty() && q.peekFirst() < cutoff) q.removeFirst();
            if (q.size() >= maxRequestsPerMinute) return false;
            q.addLast(now);
            return true;
        }
    }
}

