package com.rongyan.tvosworlfkillserver;

/**
 * Created by XRY on 2017/8/1.
 */

public class MessageEvent {
    private String message;

    public MessageEvent(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
