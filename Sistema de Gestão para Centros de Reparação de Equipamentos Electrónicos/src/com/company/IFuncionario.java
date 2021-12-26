package com.company;

public interface IFuncionario {
    public boolean isValid(String palavraPasse);
    public void registarServico(String equipamento, String nif, String descricaoProblema, int urgente);
    public int registarServicoExpresso(String nome, String nif, int servico);
    public int registarEntregaPagamento(String codigo);
    public String getId();
    public void setId(String id);
    public String getNome();
    public void setNome(String nome);
    public String getPalavraPasse();
    public void setPalavraPasse(String palavraPasse);
}
