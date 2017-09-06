package com.rongyan.model.message;

/**
 * Created by XRY on 2017/8/15.
 */

public class ToastMessage {
    private String message;

    public ToastMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
