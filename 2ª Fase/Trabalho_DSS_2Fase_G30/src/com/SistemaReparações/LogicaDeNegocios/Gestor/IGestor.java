package com.SistemaReparações.LogicaDeNegocios.Gestor;

import com.SistemaReparações.LogicaDeNegocios.Registo.IRegisto;

import java.util.List;

public interface IGestor {
    public boolean isValid(String palavraPasse);
    public String getId();
    public void setId(String id);
    public String getNome();
    public void setNome(String nome);
    public String getPalavraPasse();
    public void setPalavraPasse(String palavraPasse);
    public void addFuncionario(String id, String nome, String palavraPasse);
    public void addTecnico(String id, String nome, String palavraPasse);
    public List<String> estatisticasFuncionarios();
    public List<String> estatisticasTecnicos();
    public List<String> estatisticasTecnicosExaustiva();
    public double mediaTempoReparacoes(List<IRegisto> lista);
    public double desvioReparacoes(List<IRegisto> lista);
    public void fazerAvaliacao(Integer avaliacao);
    public List<String> verAsAvaliacoes();
}
