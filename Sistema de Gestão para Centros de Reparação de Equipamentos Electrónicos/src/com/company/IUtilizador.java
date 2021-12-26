package com.company;

public interface IUtilizador {
    public String getId();
    public void setId(String id);
    public String getNome();
    public void setNome(String nome);
    public String getPalavraPasse();
    public void setPalavraPasse(String palavraPasse);
    public abstract boolean isValid(String palavraPasse);
}
