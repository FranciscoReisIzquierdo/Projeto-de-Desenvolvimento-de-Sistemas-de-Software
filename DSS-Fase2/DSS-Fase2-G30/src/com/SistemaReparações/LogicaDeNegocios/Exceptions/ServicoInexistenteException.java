package com.SistemaReparações.LogicaDeNegocios.Exceptions;

public class ServicoInexistenteException extends Exception{

    public ServicoInexistenteException(){
        super();
    }

    public ServicoInexistenteException(String s){
        super(s);
    }
}
