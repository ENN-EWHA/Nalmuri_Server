package com.example.nalmuri;

import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class Message {
    private HttpStatus status;
    private String message;
    private Object data;

    public Message() {
        this.status = null;
        this.data = null;
        this.message = null;
    }
}
