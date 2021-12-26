package com.company;

public abstract class Utilizador implements IUtilizador{
    private String id;
    private String nome;
    private String palavraPasse;

    /**
     * Construtor parameterizado de Utilizador
     * @param id
     * @param nome
     * @param palavraPasse
     */
    public Utilizador(String id, String nome, String palavraPasse){
        this.id= id;
        this.nome= nome;
        this.palavraPasse= palavraPasse;
    }

    /**
     * Get e Setter de Id
     *
     */
    public String getId() { return id; }

    public void setId(String id) { this.id = id; }

    /**
     * Get e Setter de nome
     *
     */
    public String getNome() { return nome; }

    public void setNome(String nome) { this.nome = nome; }

    public String getPalavraPasse() {
        return palavraPasse;
    }

    public void setPalavraPasse(String palavraPasse) {
        this.palavraPasse = palavraPasse;
    }

    public abstract boolean isValid(String palavraPasse);
}