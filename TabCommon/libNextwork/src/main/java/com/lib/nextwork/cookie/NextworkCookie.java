package com.lib.nextwork.cookie;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by「 The Khaeng 」on 08 Apr 2018 :)
 */
public class NextworkCookie {
    public static final String NO_FORMAT = "";
    public static final String COOKIE_FORMAT = "Set-Cookie";
    public static final String COOKIE_V2_FORMAT = "Set-Cookie2";

    private String format = NO_FORMAT;
    private String key;
    private String value;
    private Map<String, String> options = new HashMap<>();

    public NextworkCookie setFormat(String format) {
        this.format = format;
        return this;
    }

    public NextworkCookie setKey(String key) {
        this.key = key;
        return this;
    }

    public NextworkCookie setValue(String value) {
        this.value = value;
        return this;
    }

    public NextworkCookie addOptions(String key, String value) {
        this.options.put(key, value);
        return this;
    }



    public String getFormat() {
        return format;
    }


    public String getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }


    public Map<String, String> getOptions() {
        return options;
    }

    @Override
    public String toString() {
        StringBuilder cookieString;
        if (format == null || format.isEmpty()) {
            cookieString = new StringBuilder(key + "=" + value);
        }else{
            cookieString = new StringBuilder(format + ": " + key + "=" + value);
        }

        for (Map.Entry<String, String> entry : options.entrySet()) {
            cookieString.append("; ").append(entry.getKey()).append("=").append(entry.getValue());
        }
        return cookieString.toString();
    }
}
