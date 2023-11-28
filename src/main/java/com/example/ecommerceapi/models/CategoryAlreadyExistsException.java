package com.example.ecommerceapi.models;

public class CategoryAlreadyExistsException extends RuntimeException{

    public CategoryAlreadyExistsException(String message){
        super(message);
    }
}
