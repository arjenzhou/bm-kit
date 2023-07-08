package com.arjenzhou.kit.lazy;

import org.junit.platform.commons.logging.Logger;
import org.junit.platform.commons.logging.LoggerFactory;

import java.util.function.BinaryOperator;

/**
 * @author bm-kit@arjenzhou.com
 * @since 2023/7/8
 */
public class LazyContext {
    private static final Logger LOG = LoggerFactory.getLogger(LazyContext.class);
    Lazy<Long> start = Lazy.of(System::currentTimeMillis);
    Lazy<String> a = Lazy.of(() -> sleepAndLog("a", 1));
    Lazy<String> b = Lazy.of(() -> sleepAndLog("b", 1));
    Lazy<String> c = Lazy.of(() -> "c");
    Lazy<String> d = Lazy.of(() -> "d");
    Lazy<String> e = Lazy.of(() -> "e");
    BinaryOperator<String> add = (x, y) -> sleepAndLog(String.format("(%s + %s)", x, y), 1);
    BinaryOperator<String> mul = (x, y) -> sleepAndLog(String.format("(%s * %s)", x, y), 2);
    Lazy<String> ab = Lazy.of(a, b, add);
    Lazy<String> de = Lazy.of(d, e, add);
    Lazy<String> abc = Lazy.of(ab, b, mul);
    Lazy<String> dec = Lazy.of(de, c, add);
    Lazy<String> result = Lazy.of(abc, dec, add);

    String sleepAndLog(String value, int second) {
        sleep(second);
        LOG.info(() -> String.format("[%s] %s at %d", Thread.currentThread().getName(), value, System.currentTimeMillis() - start.get()));
        return value;
    }

    void sleep(int second) {
        try {
            Thread.sleep(second * 1000L);
        } catch (Exception ignored) {
        }
    }
}
