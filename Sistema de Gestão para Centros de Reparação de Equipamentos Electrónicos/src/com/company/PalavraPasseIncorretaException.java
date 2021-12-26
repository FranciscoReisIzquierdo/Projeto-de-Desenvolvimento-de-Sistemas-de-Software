package com.company;

public class PalavraPasseIncorretaException extends Exception{

    public PalavraPasseIncorretaException(){
        super();
    }

    public PalavraPasseIncorretaException(String s){
        super(s);
    }
}
