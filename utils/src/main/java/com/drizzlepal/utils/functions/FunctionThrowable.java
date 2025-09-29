package com.drizzlepal.utils.functions;

@FunctionalInterface
public interface FunctionThrowable<T, R> {

    R apply(T o) throws Throwable;

}
