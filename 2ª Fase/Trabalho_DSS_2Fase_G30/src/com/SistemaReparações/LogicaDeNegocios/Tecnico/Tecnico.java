package com.SistemaReparações.LogicaDeNegocios.Tecnico;

import com.SistemaReparações.LogicaDeNegocios.Registo.IRegisto;
import com.SistemaReparações.LogicaDeNegocios.Sistema.SystemInfo;
import com.SistemaReparações.LogicaDeNegocios.Utilizador.Utilizador;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Tecnico extends Utilizador implements ITecnico {

    /**
     * Construtor parametrizado de Tecnico
     */
    public Tecnico(String id, String nome, String palavraPasse) {
        super(id, nome, palavraPasse);
    }

    @Override
    public boolean isValid(String palavraPasse) { return super.getPalavraPasse().equals(palavraPasse); }

    public String getId() { return super.getId(); }

    public void setId(String id){ super.setId(id); }

    public String getNome(){ return super.getNome(); }

    public void setNome(String nome){ super.setNome(nome); }

    public String getPalavraPasse(){ return super.getPalavraPasse(); }

    public void setPalavraPasse(String palavraPasse){ super.setPalavraPasse(palavraPasse); }


    /**
     * Método que atribui um plano de trabalho ao pedido de orçamento mais antigo
     * @param plano
     * @return
     */
    public boolean setPlanoTrabalhos(List<String> plano){
        if(SystemInfo.orcamentos.size()== 0) return false;
        SystemInfo.orcamentos.get(0).setPlanoTrabalho(plano);
        return true;
    }

    /**
     * Método que atribui um orçamento ao pedido de orçamento mais antigo
     * @param orcamento
     * @return
     */
    public boolean setOrcamento(double orcamento){
        if(SystemInfo.orcamentos.size()== 0) return false;
        SystemInfo.orcamentos.get(0).setOrcamento(orcamento);
        IRegisto r= SystemInfo.orcamentos.get(0);
        SystemInfo.mapEspera.put(r.getCod(), r);
        SystemInfo.orcamentos.remove(0);
        return true;
    }

    /**
     * Método que atribui um tempo de reparação ao pedido de orçamento mais antigo
     * @param tempo
     * @return
     */
    public boolean setTempoReparacao(double tempo){
        if(SystemInfo.orcamentos.size()== 0) return false;
        SystemInfo.orcamentos.get(0).setTempoReparacao(tempo);
        return true;
    }


    /**
     * Método que permite atualizar a resposta ao pedido de orçamento do cliente
     * @param flag
     * @param cod
     * @return
     */
    public boolean respostaAoPedidoOrcamento(boolean flag, String cod){
        if(!SystemInfo.mapEspera.containsKey(cod)) return false;
        if(!flag){
            IRegisto r= SystemInfo.mapEspera.get(cod);
            SystemInfo.mapEspera.remove(cod);
            SystemInfo.finalizados.put(r.getCod(), r);
            return true;
        }
        else{
            IRegisto r= SystemInfo.mapEspera.get(cod);
            SystemInfo.mapEspera.remove(cod);
            SystemInfo.reparacoes.add(r);
            return true;
        }
    }

    public void colocaEmEspera(String codigo){
        if(SystemInfo.standBy.containsKey(this.getId()) &&
                SystemInfo.standBy.get(this.getId()).containsKey(codigo))
            return;
        IRegisto ret = null;
        int flag = 0;
        for(IRegisto r : SystemInfo.reparacoes){
            if(r.getCod() == codigo){
                ret = r;
                break;
            }
        }
        if(ret == null){
            for(IRegisto r : SystemInfo.servicosExpress){
                if(r.getCod() == codigo){
                    ret = r;
                    flag = 1;
                    break;
                }
            }
        }

        if(!SystemInfo.standBy.containsKey(super.getId())){
            HashMap<String, IRegisto> map= new HashMap<>();
            SystemInfo.standBy.put(super.getId(), map);
        }

        SystemInfo.standBy.get(super.getId()).put(ret.getCod(), ret);
        if(flag == 0) SystemInfo.reparacoes.remove(ret);
        else SystemInfo.servicosExpress.remove(ret);
    }

    public List<String> standbyCodigos(){
        List<String> lista= new ArrayList<>();

        for(Map.Entry<String, IRegisto> entry: SystemInfo.standBy.get(super.getId()).entrySet()) {
            lista.add(entry.getValue().getCod());
        }
        return lista;
    }

    public void avancaProgresso(String cod, double preco, int tempo){
        IRegisto ret = null;
        int flag= 0;
        for(IRegisto r : SystemInfo.reparacoes){
            if(r.getCod() == cod){
                ret = r;
                break;
            }
        }

        if(ret == null){
            for(IRegisto r : SystemInfo.servicosExpress){
                if(r.getCod() == cod){
                    ret = r;
                    flag = 1;
                    break;
                }
            }
        }

        if(ret == null){
            if(SystemInfo.standBy.containsKey(super.getId())){
                if(SystemInfo.standBy.get(super.getId()).containsKey(cod))
                    ret= SystemInfo.standBy.get(super.getId()).get(cod);
                flag = 2;
            }
        }

        if(ret == null) return;

        ret.setProgresso(ret.getProgresso() + 1);
        ret.setCusto(ret.getCusto() + preco);
        if(tempo > 0) ret.setTempoGasto(ret.getTempoGasto() + tempo);
        if(ret.getPlanoTrabalho().size() <= ret.getProgresso()) {
            if(flag == 2) SystemInfo.standBy.get(super.getId()).remove(cod);
            else if(flag == 0) SystemInfo.reparacoes.remove(ret);
            else SystemInfo.servicosExpress.remove(ret);
            ret.finalizaRegisto();
            SystemInfo.finalizados.put(cod, ret);
            if(flag == 1 || ret.getExpress() != -1){
                if (!SystemInfo.reparacoesExpressEfetuadas.containsKey(super.getId()))
                    SystemInfo.reparacoesExpressEfetuadas.put(super.getId(), new ArrayList<>());

                SystemInfo.reparacoesExpressEfetuadas.get(super.getId()).add(ret);
            }
            else {
                if (!SystemInfo.reparacoesEfetuadas.containsKey(super.getId()))
                    SystemInfo.reparacoesEfetuadas.put(super.getId(), new ArrayList<>());

                SystemInfo.reparacoesEfetuadas.get(super.getId()).add(ret);
            }
        }
    }

    public int getProgresso(String cod){
        IRegisto ret = null;
        for(IRegisto r : SystemInfo.reparacoes){
            if(r.getCod() == cod){
                ret = r;
                break;
            }
        }
        if(ret == null && SystemInfo.standBy.containsKey(this.getId()) &&
                SystemInfo.standBy.get(this.getId()).containsKey(cod))
            ret = SystemInfo.standBy.get(this.getId()).get(cod);
        if(ret == null) return -1;
        int p = ret.getProgresso();
        return p >= ret.getPlanoTrabalho().size() ? -1 : p;
    }

    public List<String> orcamentoCodigos(){
        List<String> lista= new ArrayList<>();

        for(IRegisto r: SystemInfo.mapEspera.values()) {
            lista.add(r.getCod());
        }
        return lista;
    }

    public double getPrecoTotal(String cod){ //-------------------------
        IRegisto ret = null;
        for(IRegisto r : SystemInfo.reparacoes){
            if(r.getCod() == cod){
                ret = r;
                break;
            }
        }
        if(ret == null && SystemInfo.standBy.containsKey(this.getId()) &&
                SystemInfo.standBy.get(this.getId()).containsKey(cod))
            ret = SystemInfo.standBy.get(this.getId()).get(cod);

        if(ret == null){
            for(IRegisto r : SystemInfo.servicosExpress){
                if(r.getCod() == cod){
                    ret = r;
                    break;
                }
            }
        }

        if(ret == null) return -1;
        double p = ret.getCusto();
        return p;
    }
    public void vaiAssimMesmo(String cod){ //-------------------------
        IRegisto ret = null;
        int flag= 0;
        for(IRegisto r : SystemInfo.reparacoes){
            if(r.getCod() == cod){
                ret = r;
                break;
            }
        }

        if(ret == null){
            for(IRegisto r : SystemInfo.servicosExpress){
                if(r.getCod() == cod){
                    ret = r;
                    flag = 1;
                    break;
                }
            }
        }

        if(ret == null){
            if(SystemInfo.standBy.containsKey(super.getId())){
                if(SystemInfo.standBy.get(super.getId()).containsKey(cod))
                    ret= SystemInfo.standBy.get(super.getId()).get(cod);
                flag = 2;
            }
        }

        if(ret == null) return;

        if(flag == 2) SystemInfo.standBy.get(super.getId()).remove(cod);
        else if(flag == 0) SystemInfo.reparacoes.remove(ret);
        else SystemInfo.servicosExpress.remove(ret);
        ret.vaiAssimMesmo();
        SystemInfo.finalizados.put(cod, ret);
        if(flag == 1){
            if (!SystemInfo.reparacoesExpressEfetuadas.containsKey(super.getId()))
                SystemInfo.reparacoesExpressEfetuadas.put(super.getId(), new ArrayList<>());

            SystemInfo.reparacoesExpressEfetuadas.get(super.getId()).add(ret);
        }
        else {
            if (!SystemInfo.reparacoesEfetuadas.containsKey(super.getId()))
                SystemInfo.reparacoesEfetuadas.put(super.getId(), new ArrayList<>());

            SystemInfo.reparacoesEfetuadas.get(super.getId()).add(ret);
        }
    }

    public double getOrcamento(String cod){ //--------------------------
        IRegisto ret = null;
        for(IRegisto r : SystemInfo.reparacoes){
            if(r.getCod() == cod){
                ret = r;
                break;
            }
        }
        if(ret == null && SystemInfo.standBy.containsKey(this.getId()) &&
                SystemInfo.standBy.get(this.getId()).containsKey(cod))
            ret = SystemInfo.standBy.get(this.getId()).get(cod);

        if(ret == null){
            for(IRegisto r : SystemInfo.servicosExpress){
                if(r.getCod() == cod){
                    ret = r;
                    break;
                }
            }
        }

        if(ret == null) return -1;
        double p = ret.getOrcamento();
        return p;
    }
}