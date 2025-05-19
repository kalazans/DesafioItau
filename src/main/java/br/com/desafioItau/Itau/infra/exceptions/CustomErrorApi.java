package br.com.desafioItau.Itau.infra.exceptions;

import org.springframework.http.HttpStatus;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class CustomErrorApi {
    private HttpStatus status;
    private String message;
    private List<String> errors;

    public CustomErrorApi(HttpStatus status,String message,List<String> errors){
        super();
        this.status = status;
        this.message = message;
        this.errors = errors;
    }
    public CustomErrorApi(HttpStatus status, String message, String error) {
        super();
        this.status = status;
        this.message = message;
        errors = Collections.singletonList(error);
    }

    public CustomErrorApi(){
    }

    public HttpStatus getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public List<String> getErrors() {
        return errors;
    }
}
