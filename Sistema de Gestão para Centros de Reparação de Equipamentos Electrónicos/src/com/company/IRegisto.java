package com.company;

import java.time.LocalDateTime;
import java.util.List;

public interface IRegisto {
    public String getNif();
    public void setNif(String nif);
    public String getDescricaoProblema();
    public String getEquipamento();
    public List<String> getPlanoTrabalho();
    public void setPlanoTrabalho(List<String> planoTrabalho);
    public void setEquipamento(String equipamento);
    public void setDescricaoProblema(String descricaoProblema);
    public void setCod(String cod);
    public String getCod();
    public double getOrcamento();
    public void setOrcamento(double orcamento);
    public boolean isReparado();
    public void setReparado(boolean reparado);
    public LocalDateTime getDataEntrada();
    public void setDataEntrada(LocalDateTime dataEntrada);
    public double getTempoReparacao();
    public void setTempoReparacao(double tempoReparacao);
    public LocalDateTime getDataSaida();
    public void setDataSaida(LocalDateTime dataSaida);
    public int getUrgente();
    public void setUrgente(int urgente);
    public int getTempoGasto();
    public void setTempoGasto(int tempoGasto);
    public String toString();
    public int getExpress();
    public void setExpress(int express);
    public int getProgresso();
    public void setProgresso(int progresso);
    public void finalizaRegisto();
    public void iniciaReparacao();
    public double getCusto();
    public void setCusto(double custo);
}
