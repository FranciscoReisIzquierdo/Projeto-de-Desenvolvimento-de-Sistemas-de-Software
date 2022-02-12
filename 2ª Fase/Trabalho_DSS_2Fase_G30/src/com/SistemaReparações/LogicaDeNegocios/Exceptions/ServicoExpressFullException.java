package com.SistemaReparações.LogicaDeNegocios.Exceptions;

public class ServicoExpressFullException extends Exception{

    public ServicoExpressFullException(){
        super();
    }

    public ServicoExpressFullException(String s){
        super(s);
    }
}
