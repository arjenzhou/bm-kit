package com.arjenzhou.kit.lazy;

import java.util.Objects;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 * Lazy evaluation
 *
 * @author bm-kit@arjenzhou.com
 * @since 2023/7/8
 */
public final class Lazy<T> implements Supplier<T> {
    private transient Supplier<T> supplier;
    private volatile T value;

    private Lazy(Supplier<T> supplier) {
        this.supplier = Objects.requireNonNull(supplier);
    }

    @Override
    public T get() {
        if (value == null) {
            synchronized (this) {
                if (value == null) {
                    value = Objects.requireNonNull(supplier.get());
                    supplier = null;
                }
            }
        }
        return value;
    }

    public <R> Lazy<R> map(Function<T, R> mapper) {
        return new Lazy<>(() -> mapper.apply(this.get()));
    }

    public <R> Lazy<R> flatMap(Function<T, Lazy<R>> mapper) {
        return new Lazy<>(() -> mapper.apply(this.get()).get());
    }

    public Lazy<Optional<T>> filter(Predicate<T> predicate) {
        return new Lazy<>(() -> Optional.of(get()).filter(predicate));
    }

    /**
     * construct a new Lazy instance by supplier function
     *
     * @param supplier to supply argument1
     * @param <T>      type of value hold by Lazy
     * @return new Lazy evaluation
     */
    public static <T> Lazy<T> of(Supplier<T> supplier) {
        return new Lazy<>(supplier);
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
    public static <T, E> Lazy<E> of(Supplier<T> supplier, Function<T, E> function) {
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
    public static <U, V, T> Lazy<T> of(Supplier<U> s1, Supplier<V> s2, BiFunction<U, V, T> function) {
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

    /**
     * @param <T> value type hold by Lazy
     * @return construct a virtual node
     */
    public static <T> Lazy<T> unset() {
        return of(()-> {
            throw new UnsetException(); 
        });
    }
}