
package com.microne.mall.common;

public class MicroneMallException extends RuntimeException {

    public MicroneMallException() {
    }

    public MicroneMallException(String message) {
        super(message);
    }

    /**
     * 丢出一个异常
     *
     * @param message
     */
    public static void fail(String message) {
        throw new MicroneMallException(message);
    }

}
