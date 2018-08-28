package com.lib.nextwork.exception;

/**
 * Created by「 The Khaeng 」on 24 Apr 2018 :)
 */
public class InternalErrorException extends StatusCodeException {

    public InternalErrorException(int code) {
        super(code, "An internal server error occurred");
    }
}
