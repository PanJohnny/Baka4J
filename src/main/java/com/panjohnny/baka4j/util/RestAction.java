package com.panjohnny.baka4j.util;

import java.util.function.Consumer;

public abstract class RestAction<T> {
    private final System.Logger logger = System.getLogger(this.getClass().getName());

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
    @SuppressWarnings({"Convert2Lambda"})
    public void queue(Consumer<T> success) {
        Thread executor = new Thread(new Runnable() {
            @Override
            public void run() {
                T result = null;
                try {
                    result = handle();
                } catch (Throwable e) {
                    logger.log(System.Logger.Level.ERROR, e);
                }
                success.accept(result);
            }
        }, "asyncExecutor@" + this.hashCode());
        executor.start();
    }


    /**
     * Queues this rest action in this thread. It is not async.
     * @return RestAction result
     */
    public T queue() {
        try {
            return handle();
        } catch (Throwable e) {
            logger.log(System.Logger.Level.ERROR, e);
            return null;
        }
    }

    protected abstract T handle() throws Throwable;
}
