package com.SistemaReparações.LogicaDeNegocios.Exceptions;

public class EquipamentoNaoReparadoException extends Exception{

    public EquipamentoNaoReparadoException(){
        super();
    }

    public EquipamentoNaoReparadoException(String s){
        super(s);
    }
}
