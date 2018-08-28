package com.lib.nextwork.exception;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by「 The Khaeng 」on 02 Oct 2017 :)
 */
public class NullBodyException extends NullPointerException {

    private List<String> messages = new ArrayList<>();


    public NullBodyException() {
    }

    public NullBodyException(String s) {
        super(s);
    }

    public void addMessage(String message) {
        messages.add(message);
    }

    public List<String> getMessages() {
        return messages;
    }
}
