package com.estore.estoreapi.exceptionHandling;

public class ResourceAlreadyExistsException extends Exception{
    public ResourceAlreadyExistsException(){

    }
    public ResourceAlreadyExistsException(String msg){
        super(msg);
    }
}
