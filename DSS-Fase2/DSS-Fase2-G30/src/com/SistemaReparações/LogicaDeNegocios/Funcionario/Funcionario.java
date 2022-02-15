package com.SistemaReparações.LogicaDeNegocios.Funcionario;

import com.SistemaReparações.LogicaDeNegocios.Sistema.LNSistema;
import com.SistemaReparações.LogicaDeNegocios.Sistema.SystemInfo;
import com.SistemaReparações.LogicaDeNegocios.Registo.IRegisto;
import com.SistemaReparações.LogicaDeNegocios.Registo.Registo;
import com.SistemaReparações.LogicaDeNegocios.Utilizador.Utilizador;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Funcionario extends Utilizador implements IFuncionario{
    private List<Registo> clientes;


    /**
     * Construtor parametrizado
     */
    public Funcionario(String id, String nome, String palavraPasse){
        super(id, nome, palavraPasse);
    }

    public String getId() { return super.getId(); }

    public void setId(String id){ super.setId(id); }

    public String getNome(){ return super.getNome(); }

    public void setNome(String nome){ super.setNome(nome); }

    public String getPalavraPasse(){ return super.getPalavraPasse(); }

    public void setPalavraPasse(String palavraPasse){ super.setPalavraPasse(palavraPasse); }

    @Override
    public boolean isValid(String palavraPasse) { return super.getPalavraPasse().equals(palavraPasse); }


    /**
     * Método que permite criar um pedido de orçamento
     * @return
     */
    public void registarServico(String equipamento, String nif, String descricaoProblema, int urgente){

        IRegisto reg = new Registo(equipamento, nif, descricaoProblema, urgente);
        SystemInfo.orcamentos.add(reg);

        if(!SystemInfo.entradas.containsKey(super.getId())) {
            List<IRegisto> lista= new ArrayList<>();
            SystemInfo.entradas.put(super.getId(), lista);
        }
        SystemInfo.entradas.get(super.getId()).add(reg);
    }

    /**
     * Metodo que regista um serviço express
     * @param nome
     * @param nif
     * @param servico
     * @return
     */
    public int registarServicoExpresso(String nome, String nif, int servico){
        if(SystemInfo.servicosExpress.size() >= SystemInfo.maxSizeExpress){
            return -1;
        }

        double orcamento = 0;
        switch (servico){
            case SystemInfo.ARRANJAR_ECRA: orcamento = SystemInfo.PRECO_ARRANJAR_ECRA;
                break;
            case SystemInfo.INSTALAR_SO: orcamento = SystemInfo.PRECO_INSTALAR_SO;
                break;
            case SystemInfo.TROCAR_BATERIA: orcamento = SystemInfo.PRECO_TROCAR_BATERIA;
                break;
            case SystemInfo.LIMPEZA: orcamento = SystemInfo.PRECO_LIMPEZA;
                break;
            default: return -2;
        }

        IRegisto reg = new Registo(nome, nif, orcamento, servico);
        SystemInfo.servicosExpress.add(reg);
        if(!SystemInfo.entradas.containsKey(super.getId())) {
            List<IRegisto> lista= new ArrayList<>();
            SystemInfo.entradas.put(super.getId(), lista);
        }
        SystemInfo.entradas.get(super.getId()).add(reg);
        return 1;
    }

    /**
     * Método que regista uma entrega ao cliente e pagamanto do equipamento
     * @param codigo
     * @return
     */
    public int registarEntregaPagamento(String codigo){
        if(!SystemInfo.finalizados.containsKey(codigo)){
            return -1;
        }
        double preco= LNSistema.valorAPagar(codigo);
        IRegisto registo= SystemInfo.finalizados.get(codigo);
        SystemInfo.finalizados.get(codigo).setDataSaida(LocalDateTime.now());
        SystemInfo.finalizados.remove(codigo);
        if(!SystemInfo.saidas.containsKey(super.getId())) {
            List<IRegisto> lista= new ArrayList<>();
            SystemInfo.saidas.put(super.getId(), lista);
        }
        SystemInfo.saidas.get(super.getId()).add(registo);
        SystemInfo.dinheiro += preco;
        return 2;

    }

    public List<String> finalizadosCodigos(){//--------------
        List<String> lista= new ArrayList<>();

        for(IRegisto r: SystemInfo.finalizados.values()) {
            lista.add(r.getCod());
        }
        return lista;
    }
}