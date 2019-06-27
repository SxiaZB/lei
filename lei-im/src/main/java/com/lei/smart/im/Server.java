package com.lei.smart.im;

/**
 * @author dingjianlei
 */
public interface Server {

    void start();
    void start(int port);

    void shutdown();
}
