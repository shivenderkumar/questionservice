package com.shivender.quetionservice.exceptions;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatusCode;

@Getter
@Setter
public class ApiException extends RuntimeException{
    private HttpStatusCode statusCode;
    public ApiException(String message, HttpStatusCode statusCode){
        super(message);
        this.statusCode = statusCode;
    }
}
