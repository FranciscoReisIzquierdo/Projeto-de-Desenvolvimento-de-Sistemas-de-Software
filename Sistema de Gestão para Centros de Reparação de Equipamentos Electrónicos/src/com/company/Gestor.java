package com.company;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Gestor extends Utilizador implements IGestor{


    /**
     * Construtor parametrizado de Gestor
     */
    public Gestor(String id, String nome, String palavraPasse) {
        super(id, nome, palavraPasse);
    }

    public String getId() { return super.getId(); }

    public void setId(String id){ super.setId(id); }

    public String getNome(){ return super.getNome(); }

    public void setNome(String nome){ super.setNome(nome); }

    public String getPalavraPasse(){ return super.getPalavraPasse(); }

    public void setPalavraPasse(String palavraPasse){ super.setPalavraPasse(palavraPasse); }

    /**
     * Método que permite adicionar um funcionário ao Centro de Reparação
     * @param id
     * @param nome
     * @param palavraPasse
     */
    public void addFuncionario(String id, String nome, String palavraPasse){
        IFuncionario f= new Funcionario(id, nome, palavraPasse);
        SystemInfo.funcionarios.put(id, f);
    }

    /**
     * Método que permite adicionar um técnico ao Centro de Reparação
     * @param id
     * @param nome
     * @param palavraPasse
     */
    public void addTecnico(String id, String nome, String palavraPasse){
        ITecnico t= new Tecnico(id, nome, palavraPasse);
        SystemInfo.tecnicos.put(id, t);
    }

    /**
     * Método que devolve a informação relevante a todos os funcionários
     * @return
     */
    public List<String> estatisticasFuncionarios(){

        List<String> lista= new ArrayList<>();

        for(Map.Entry<String, IFuncionario> entry: SystemInfo.funcionarios.entrySet()){
            int entradas= 0;
            if(SystemInfo.entradas.get(entry.getValue().getId())!= null) entradas= SystemInfo.entradas.get(entry.getValue().getId()).size();
            int saidas= 0;
            if(SystemInfo.saidas.get(entry.getValue().getId())!= null) saidas= SystemInfo.saidas.get(entry.getValue().getId()).size();

            String info= new String("Funcionário: " + entry.getValue().getNome() + " (id: " + entry.getKey() + "); Entradas: " + entradas + "; Saidas: " + saidas + ";");
            lista.add(info);
        }
        return lista;
    }

    public List<String> estatisticasTecnicos(){
        List<String> lista= new ArrayList<>();

        for(Map.Entry<String, ITecnico> entry: SystemInfo.tecnicos.entrySet()) {

            int reparacoes= 0;
            if(SystemInfo.reparacoesEfetuadas.get(entry.getValue().getId())!= null) reparacoes= SystemInfo.reparacoesEfetuadas.get(entry.getValue().getId()).size();
            int reparacoesExpress= 0;
            if(SystemInfo.reparacoesExpressEfetuadas.get(entry.getValue().getId())!= null) reparacoesExpress= SystemInfo.reparacoesExpressEfetuadas.get(entry.getValue().getId()).size();
            double media= mediaTempoReparacoes(SystemInfo.reparacoesEfetuadas.get(entry.getValue().getId()));
            double desvio= desvioReparacoes(SystemInfo.reparacoesEfetuadas.get(entry.getValue().getId()));

            String info= new String("Técnico: " + SystemInfo.tecnicos.get(entry.getKey()).getNome() + " (id: " + entry.getKey() + "); Reparações programadas realizadas: " + reparacoes + "; " +
                    "Reparações express realizadas: " + reparacoesExpress + "; Média das reparações programadas realizadas: " + media + "; Desvio em relações às durações previstas: " + desvio + ";");

            lista.add(info);
        }
        return lista;
    }

    public List<String> estatisticasTecnicosExaustiva(){
        List<String> lista= new ArrayList<>();

        for(Map.Entry<String, ITecnico> entry: SystemInfo.tecnicos.entrySet()) {
            StringBuilder s= new StringBuilder();

            s.append("-> Nome do técnico: ").append(entry.getValue().getNome()).append(" (id: ").append(entry.getValue().getId()).append("):\n");

            if(SystemInfo.reparacoesEfetuadas.containsKey(entry.getKey())) {
                List<IRegisto> reparacoesEfetuadas = SystemInfo.reparacoesEfetuadas.get(entry.getKey());
                s.append("Lista de reparações efetuadas: ").append("\n");
                for(IRegisto r: reparacoesEfetuadas)
                    s.append(r.toString()).append("\n");
            }

            if(SystemInfo.reparacoesExpressEfetuadas.containsKey(entry.getKey())) {
                List<IRegisto> reparacoesExpressEfetuadas = SystemInfo.reparacoesExpressEfetuadas.get(entry.getKey());
                s.append("Lista de reparações express efetuadas: ").append("\n");
                for(IRegisto r: reparacoesExpressEfetuadas) {
                    s.append(r.getCod()).append(" ");
                    s.append(SystemInfo.getDescricaoServico(r.getExpress())).append("\n");
                }
            }

            lista.add(s.toString());
        }
        return lista;
    }

    public double mediaTempoReparacoes(List<IRegisto> lista){
        if(lista== null) return 0;
        double tempo= 0;

        for(IRegisto r: lista){ tempo+= r.getTempoGasto(); }

        return tempo/ lista.size();
    }

    public double desvioReparacoes(List<IRegisto> lista){
        if(lista== null) return 0;
        double desvio= 0;

        for(IRegisto r: lista){
            desvio+= (r.getTempoGasto()- r.getTempoReparacao());
        }

        return desvio/ lista.size();
    }

    @Override
    public boolean isValid(String palavraPasse) { return super.getPalavraPasse().equals(palavraPasse); }
}