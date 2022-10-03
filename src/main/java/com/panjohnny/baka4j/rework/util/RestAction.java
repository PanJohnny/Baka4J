package com.panjohnny.baka4j.rework.util;

import lombok.SneakyThrows;

import java.util.function.Consumer;

public abstract class RestAction<T> {

    /**
     * Executes the given success consumer with the RestAction result. Gives the throwable to consumer instead of not handling it.
     * @param success Consumer that will consume the RestAction result
     * @param failure  Consumer that will consume throwable if thrown during execution
     */
    public void queue(Consumer<T> success, Consumer<Throwable> failure) {
        Thread executor = new Thread(() -> {
            try {
                T result = handle();
                success.accept(result);
            } catch (Throwable t) {
                failure.accept(t);
            }
        }, "asyncExecutor@" + this.hashCode());
        executor.start();
    }

    /**
     * Executes the given success consumer with the RestAction result.
     * @param success Consumer that will consume the RestAction result
     */
    @SneakyThrows
    @SuppressWarnings({"Convert2Lambda"})
    public void queue(Consumer<T> success) {
        Thread executor = new Thread(new Runnable() {
            @Override
            @SneakyThrows
            public void run() {
                T result = handle();
                success.accept(result);
            }
        }, "asyncExecutor@" + this.hashCode());
        executor.start();
    }


    /**
     * Queues this rest action in this thread. It is not async.
     * @return RestAction result
     */
    @SneakyThrows
    public T queue() {
        return handle();
    }

    protected abstract T handle() throws Throwable;
}
