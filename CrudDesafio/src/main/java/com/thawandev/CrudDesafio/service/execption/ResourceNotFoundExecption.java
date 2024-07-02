package com.thawandev.CrudDesafio.service.execption;

public class ResourceNotFoundExecption extends RuntimeException {
    public ResourceNotFoundExecption(String msg){
        super(msg);
    }
}
