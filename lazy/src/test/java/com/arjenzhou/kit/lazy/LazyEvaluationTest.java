package com.arjenzhou.kit.lazy;

import org.junit.jupiter.api.Test;

import java.util.function.BinaryOperator;

/**
 * @author bm-kit@arjenzhou.com
 * @since 2023/7/8
 */
public class LazyEvaluationTest {
    @Test
    public void testLazyEvaluation() {
        Lazy<String> a = Lazy.of(() -> "a");
        Lazy<String> b = Lazy.of(() -> "b");
        Lazy<String> c = Lazy.of(() -> "c");
        Lazy<String> d = Lazy.of(() -> "d");
        Lazy<String> e = Lazy.of(() -> "e");

        BinaryOperator<String> add = (x, y) -> String.format("(%s + %s)", x, y);
        BinaryOperator<String> mul = (x, y) -> String.format("(%s * %s)", x, y);

        Lazy<String> ab = Lazy.of(a, b, add);
        Lazy<String> de = Lazy.of(d, e, add);
        Lazy<String> abc = Lazy.of(ab, b, mul);
        Lazy<String> dec = Lazy.of(de, c, add);
        Lazy<String> result = Lazy.of(abc, dec, add);
        assert "(((a + b) * b) + ((d + e) + c))".equals(result.get());
    }

    @Test
    public void testLazyContext() {
        LazyContext ctx = new LazyContext();
        assert "(a + b)".equals(ctx.ab.get());
        assert "(((a + b) * b) + ((d + e) + c))".equals(ctx.result.get());
    }
}
