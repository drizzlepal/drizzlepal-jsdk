package com.drizzlepal.utils.functions;

/**
 * 表示一个接受单个输入参数且不返回结果的操作，该操作可能抛出Throwable异常。
 * 作为可抛出异常的函数式接口，允许在lambda表达式或方法引用中抛出受检异常。
 *
 * @param <T> 输入参数的类型
 */
@FunctionalInterface
public interface ConsumerThrowable<T> {

    /**
     * 对给定参数执行操作。
     *
     * @param o 输入参数
     * @throws Throwable 当执行操作时发生任何异常
     */
    void accept(T o) throws Throwable;

}