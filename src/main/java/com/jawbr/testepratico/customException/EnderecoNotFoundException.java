package com.jawbr.testepratico.customException;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.text.SimpleDateFormat;
import java.util.Date;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class EnderecoNotFoundException extends RuntimeException {

    private String message;
    private String timeStamp;

    public EnderecoNotFoundException(String message, long timeStamp) {
        this.message = message;
        this.timeStamp = formatTimestamp(timeStamp);
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    private String formatTimestamp(long timeStamp) {
        Date date = new Date(timeStamp);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss a");
        return formatter.format(date);
    }

}
