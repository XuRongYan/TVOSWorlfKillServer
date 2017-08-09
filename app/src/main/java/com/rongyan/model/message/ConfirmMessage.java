package com.rongyan.model.message;

import java.io.Serializable;

/**
 * Created by XRY on 2017/8/6.
 */

public class ConfirmMessage implements Serializable{
    public static final String CONFIRM_WATCH_CARD = "CONFIRM_WATCH_CARD";
    public static final String CONFIRM = "CONFIRM";

    private int id;
    private String message;

    public ConfirmMessage(int id, String message) {
        this.id = id;
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "ConfirmMessage{" +
                "id=" + id +
                ", message='" + message + '\'' +
                '}';
    }
}
