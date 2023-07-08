package com.arjenzhou.kit.lazy;

import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * Lazy evaluation
 *
 * @author bm-kit@arjenzhou.com
 * @since 2023/7/8
 */
public interface Lazy<T> extends Supplier<T> {
    /**
     * set value for Lazy
     *
     * @param value hold by Lazy
     */
    void set(T value);

    /**
     * @param <T> value type hold by Lazy
     * @return construct a virtual node
     */
    static <T> Lazy<T> unset() {
        return of(() -> {
            throw new UnsetException();
        });
    }

    /**
     * construct a new Lazy instance by supplier function
     *
     * @param supplier to supply argument1
     * @param <T>      type of value hold by Lazy
     * @return new Lazy evaluation
     */
    static <T> Lazy<T> of(Supplier<T> supplier) {
        return new Lazy<>() {
            T cache;

            @Override
            public void set(T value) {
                cache = value;
            }

            @Override
            public T get() {
                if (cache == null) {
                    cache = supplier.get();
                }
                return cache;
            }
        };
    }

    /**
     * construct a new lazy evaluation with Function, which to apply calculation on one argument
     *
     * @param supplier to supply argument1
     * @param function function to apply calculation
     * @param <T>      type of value hold by Lazy
     * @param <E>      type of result
     * @return new Lazy evaluation
     */
    static <T, E> Lazy<E> of(Supplier<T> supplier, Function<T, E> function) {
        return of(() -> function.apply(supplier.get()));
    }

    /**
     * construct a new lazy evaluation with BiFunction, which to apply calculation on two arguments
     *
     * @param s1       to supply argument1
     * @param s2       to supply argument2
     * @param function function to apply calculation
     * @param <U>      type of value hold by s1
     * @param <V>      type of value hold by s2
     * @param <T>      type of result
     * @return new Lazy evaluation
     */
    static <U, V, T> Lazy<T> of(Supplier<U> s1, Supplier<V> s2, BiFunction<U, V, T> function) {
        return of(() -> function.apply(s1.get(), s2.get()));
    }

    /**
     * construct a new lazy evaluation with TriFunction, which to apply calculation on three arguments
     *
     * @param s1       to supply argument1
     * @param s2       to supply argument2
     * @param s3       to supply argument3
     * @param function function to apply calculation
     * @param <U>      type of value hold by s1
     * @param <V>      type of value hold by s2
     * @param <W>      type of value hold by s3
     * @param <T>      type of result
     * @return new Lazy evaluation
     */
    static <U, V, W, T> Lazy<T> of(Supplier<U> s1, Supplier<V> s2, Supplier<W> s3, TriFunction<U, V, W, T> function) {
        return of(() -> function.apply(s1.get(), s2.get(), s3.get()));
    }
}
