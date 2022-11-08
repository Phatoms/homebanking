package com.santander.homebanking.utils;

public final class ResponseUtils {

    private Boolean isDone;
    private Integer statusCode;
    private String message;
    private String[] args;

    public ResponseUtils(Boolean isDone, Integer statusCode, String message, String[] args) {
        this.isDone = isDone;
        this.statusCode = statusCode;
        this.message = message;
        this.args = args;
    }

    public ResponseUtils(Boolean isDone, Integer statusCode, String message) {
        this.isDone = isDone;
        this.statusCode = statusCode;
        this.message = message;
        this.args = new String[]{};
    }

    public Boolean getDone() {
        return isDone;
    }

    public Integer getStatusCode() {
        return statusCode;
    }

    public String getMessage() {
        return message;
    }

    public String[] getArgs() {
        return args;
    }

    public void setDone(Boolean done) {
        isDone = done;
    }

    public void setStatusCode(Integer statusCode) {
        this.statusCode = statusCode;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setArgs(String[] args) {
        this.args = args;
    }
}
