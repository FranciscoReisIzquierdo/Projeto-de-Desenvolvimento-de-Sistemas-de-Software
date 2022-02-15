package com.SistemaReparações.LogicaDeNegocios.Registo;

import com.SistemaReparações.LogicaDeNegocios.Sistema.SystemInfo;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Registo implements IRegisto{

    private String nif;
    private String cod;
    private String descricaoProblema;
    private double orcamento= 0;
    private double custo = 0;
    private String equipamento;
    private boolean reparado= false;
    private int urgente = 5;
    private int progresso= 0;
    private int tempoGasto= 0;
    private double tempoReparacao= 0;
    private int express= -1;
    private LocalDateTime dataEntrada;
    private LocalDateTime dataInicioReparacao;
    private LocalDateTime dataFimReparacao;
    private LocalDateTime dataSaida;
    private List<String> planoTrabalho= new ArrayList<>();

    /**
     * Construtor para registos "normais"
     * @param equipamento
     * @param nif
     * @param descricaoProblema
     */
    public Registo(String equipamento, String nif, String descricaoProblema, int urgente){
        this.equipamento= equipamento;
        this.nif= nif;
        this.dataEntrada= LocalDateTime.now();
        this.cod= geraCodigo(this.nif, this.equipamento, this.dataEntrada);
        this.descricaoProblema= descricaoProblema;
        this.urgente= urgente;
    }

    /**
     * Construtor para registos express
     * @param equipamento
     * @param nif
     * @param orcamento
     * @param express
     */
    public Registo(String equipamento, String nif, double orcamento, int express){
        this.equipamento= equipamento;
        this.nif= nif;
        this.dataEntrada= LocalDateTime.now();
        String desc;
        switch (express){
            case SystemInfo.ARRANJAR_ECRA: desc = "Arranjar ecrã";
                break;
            case SystemInfo.INSTALAR_SO: desc = "Instalar sistema operativo";
                break;
            case SystemInfo.TROCAR_BATERIA: desc = "Trocar bateria";
                break;
            case SystemInfo.LIMPEZA: desc = "Limpar";
                break;
            default: desc = null;
        }
        this.descricaoProblema = desc;
        this.cod= geraCodigo(this.nif, this.equipamento, this.dataEntrada);
        this.orcamento= orcamento;
        this.express= express;
    }

    /**
     * Método que gera o código associado ao registo
     * @return
     */
    public static String geraCodigo(String nif, String equipamento, LocalDateTime dataEntrada){ return nif + equipamento + dataEntrada; }
    //public static String geraCodigo(String nif, String equipamento, LocalDateTime dataEntrada){ return (nif + equipamento + dataEntrada).hashCode() + ""; }


    /* Getters e Setters */

    public String getNif() { return nif; }

    public void setNif(String nif) { this.nif = nif; }

    public String getDescricaoProblema() {
        return descricaoProblema;
    }

    public String getEquipamento() {
        return equipamento;
    }

    public List<String> getPlanoTrabalho() {
        return planoTrabalho;
    }

    public void setPlanoTrabalho(List<String> planoTrabalho) {
        this.planoTrabalho = planoTrabalho;
    }

    public void setEquipamento(String equipamento) {
        this.equipamento = equipamento;
    }

    public void setDescricaoProblema(String descricaoProblema) {
        this.descricaoProblema = descricaoProblema;
    }

    public void setCod(String cod) { this.cod= cod; }

    public String getCod(){ return this.cod; }

    public double getOrcamento() { return orcamento; }

    public void setOrcamento(double orcamento) { this.orcamento = orcamento; }

    public boolean isReparado() { return !reparado; }

    public void setReparado(boolean reparado) { this.reparado = reparado; }

    public LocalDateTime getDataEntrada() { return dataEntrada; }

    public void setDataEntrada(LocalDateTime dataEntrada) { this.dataEntrada = dataEntrada; }

    public double getTempoReparacao() { return tempoReparacao; }

    public void setTempoReparacao(double tempoReparacao) { this.tempoReparacao = tempoReparacao; }

    public LocalDateTime getDataSaida() { return dataSaida; }

    public void setDataSaida(LocalDateTime dataSaida) { this.dataSaida = dataSaida; }

    public int getUrgente() { return urgente; }

    public void setUrgente(int urgente) { this.urgente = urgente; }

    public int getTempoGasto() { return tempoGasto; }

    public void setTempoGasto(int tempoGasto) { this.tempoGasto = tempoGasto; }

    public String toString(){
        StringBuilder s= new StringBuilder();

        s.append("Código do registo: ").append(this.cod).append("\n");
        s.append("Descrição do problema: ").append(this.descricaoProblema).append("\n");
        s.append("Plano de trabalhos: ").append(this.planoTrabalho.toString()).append("\n");

        return s.toString();
    }

    public int getExpress() { return express; }

    public void setExpress(int express) { this.express = express; }

    public int getProgresso() { return progresso; }

    public void setProgresso(int progresso) { this.progresso = progresso; }

    public void finalizaRegisto(){
        this.reparado= true;
        this.dataFimReparacao= LocalDateTime.now();
    }

    public void iniciaReparacao(){ this.dataInicioReparacao= LocalDateTime.now(); }

    public double getCusto() { return custo; }

    public void setCusto(double custo) { this.custo = custo; }

    public void vaiAssimMesmo(){//------------------------
        this.dataFimReparacao= LocalDateTime.now();
    }
}