package com.company;

import java.util.List;

public interface ITecnico {
    public boolean isValid(String palavraPasse);
    public String getId();
    public void setId(String id);
    public String getNome();
    public void setNome(String nome);
    public String getPalavraPasse();
    public void setPalavraPasse(String palavraPasse);
    public boolean setPlanoTrabalhos(List<String> plano);
    public boolean setOrcamento(double orcamento);
    public boolean setTempoReparacao(double tempo);
    public boolean respostaAoPedidoOrcamento(boolean flag, String cod);
    public void colocaEmEspera(String codigo);
    public List<String> standbyCodigos();
    public void avancaProgresso(String cod, double preco);
    public int getProgresso(String cod);
    public List<String> orcamentoCodigos();
}
