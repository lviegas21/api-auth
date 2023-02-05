package com.api.autenticacao.exception;

public class SenhaInvalidaException extends RuntimeException{
    public SenhaInvalidaException(){
        super("Senha Invalida");
    }
}
