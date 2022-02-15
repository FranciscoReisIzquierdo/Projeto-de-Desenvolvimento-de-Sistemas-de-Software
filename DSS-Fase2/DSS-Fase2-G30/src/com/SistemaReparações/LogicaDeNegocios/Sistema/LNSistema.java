package com.SistemaReparações.LogicaDeNegocios.Sistema;

import com.SistemaReparações.LogicaDeNegocios.Exceptions.*;
import com.SistemaReparações.LogicaDeNegocios.Funcionario.Funcionario;
import com.SistemaReparações.LogicaDeNegocios.Funcionario.IFuncionario;
import com.SistemaReparações.LogicaDeNegocios.Gestor.Gestor;
import com.SistemaReparações.LogicaDeNegocios.Gestor.IGestor;
import com.SistemaReparações.LogicaDeNegocios.Registo.IRegisto;
import com.SistemaReparações.LogicaDeNegocios.Utilizador.IUtilizador;
import com.SistemaReparações.LogicaDeNegocios.Tecnico.ITecnico;
import com.SistemaReparações.LogicaDeNegocios.Tecnico.Tecnico;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class LNSistema implements ILNSistema{

    private static String idAtual;
    private static IUtilizador utilizador;

    public LNSistema(){
        //Preencher SystemInfo
        SystemInfo.funcionarios.put("1", new Funcionario("1", "Duarte", "d123"));
        SystemInfo.funcionarios.put("2", new Funcionario("2", "Pedro", "p123"));
        SystemInfo.tecnicos.put("3", new Tecnico("3", "Filipe", "f123"));
        SystemInfo.gestores.put("4", new Gestor("4", "Esquerdo", "e123"));
        //SystemInfo.orcamentos.add(new Registo("Iphone", "1234567", "Arranja-me isto por favor!", 10));
        this.atualizaArmazem();
        this.verificarAbandono();
    }


    /**
     * Método que permite autenticar um Funcionário
     * @param id
     * @param palavraPasse
     * @return
     */
    public void autenticarFuncionario(String id, String palavraPasse) throws PalavraPasseIncorretaException, UtilizadorInexistenteException{
        if(!SystemInfo.funcionarios.containsKey(id))  throw new UtilizadorInexistenteException("Funcionário Inexistente!");

        IFuncionario f= SystemInfo.funcionarios.get(id);

        if(f.isValid(palavraPasse)){
            utilizador= (IUtilizador) f;
            idAtual= f.getId();
        }
        else throw new PalavraPasseIncorretaException("Palavra passe incorreta!");
    }

    /**
     * Método que permite autenticar um Técnico
     * @param id
     * @param palavraPasse
     * @return
     */
    public void autenticarTecnico(String id, String palavraPasse) throws PalavraPasseIncorretaException, UtilizadorInexistenteException{
        if(!SystemInfo.tecnicos.containsKey(id)) throw new UtilizadorInexistenteException("Tecnico Inexistente!");
        ITecnico t= SystemInfo.tecnicos.get(id);

        if(t.isValid(palavraPasse)){
            utilizador= (IUtilizador) t;
            idAtual= t.getId();
        }
        else throw new PalavraPasseIncorretaException("Palavra passe incorreta!");
    }

    /**
     * Método que permite autenticar um Gestor
     * @param id
     * @param palavraPasse
     * @return
     */
    public void autenticarGestor(String id, String palavraPasse) throws PalavraPasseIncorretaException, UtilizadorInexistenteException{
        if(!SystemInfo.gestores.containsKey(id)) throw new UtilizadorInexistenteException("Gestor Inexistente!");
        IGestor g= SystemInfo.gestores.get(id);

        if(g.isValid(palavraPasse)){
            utilizador= (IUtilizador) g;
            idAtual= g.getId();
        }
        else throw new PalavraPasseIncorretaException("Palavra passe incorreta!");
    }

    /**
     * Método que permite criar (ou substituir informações de) um funcionário
     * @param id
     * @param nome
     * @param pass
     */
    public void addFuncionario(String id, String nome, String pass){
        SystemInfo.funcionarios.put(id, new Funcionario(id, nome, pass));
    }

    /**
     * Método que permite criar (ou substituir informações de) um técnico
     * @param id
     * @param nome
     * @param pass
     */
    public void addTecnico(String id, String nome, String pass){
        SystemInfo.tecnicos.put(id, new Tecnico(id, nome, pass));
    }

//------------------------------------------------------------------------------------------------------------------------------------------------------------------------------//

    /**
     * Métodos wrappers
     */

    /**
     * Método que permite criar um pedido de orçamento
     * @return
     */
    public void registarServico(String equipamento, String nif, String descricaoProblema, int urgente){
        IFuncionario f= (IFuncionario) utilizador;
        f.registarServico(equipamento, nif, descricaoProblema, urgente);
    }

    /**
     * Método que atribui um plano de trabalho ao pedido de orçamento mais antigo
     * @param plano
     * @return
     */
    public void setPlanoTrabalhos(List<String> plano) throws PedidosOrcamentosException {
        ITecnico t = (ITecnico) utilizador;
        boolean flag= t.setPlanoTrabalhos(plano);
        if(!flag) throw new PedidosOrcamentosException("Não há pedidos de orçamento!");
    }

    /**
     * Método que atribui um orçamento ao pedido de orçamento mais antigo
     * @param orcamento
     * @return
     */
    public void setOrcamento(double orcamento) throws PedidosOrcamentosException{
        ITecnico t = (ITecnico) utilizador;
        boolean flag= t.setOrcamento(orcamento);
        if(!flag) throw new PedidosOrcamentosException("Não há pedidos de orçamento!");
    }

    /**
     * Método que atribui um tempo de reparação ao pedido de orçamento mais antigo
     * @param tempo
     * @return
     */
    public void setTempoReparacao(double tempo) throws PedidosOrcamentosException{
        ITecnico t = (ITecnico) utilizador;
        boolean flag= t.setTempoReparacao(tempo);
        if(!flag) throw new PedidosOrcamentosException("Não há pedidos de orçamento!");
    }

    /**
     * Método que permite atualizar a resposta ao pedido de orçamento do cliente
     * @param flag
     * @param cod
     * @return
     */
    public void respostaAoPedidoOrcamento(boolean flag, String cod) throws EquipamentoInexistenteException {
        ITecnico t= (ITecnico) utilizador;
        boolean f= t.respostaAoPedidoOrcamento(flag, cod);
        if(!f) throw new EquipamentoInexistenteException("Equipamento inexistente!");
    }

    /**
     * Metodo que regista um serviço express
     * @param nome
     * @param nif
     * @param servico
     * @return
     */
    public void registarServicoExpresso(String nome, String nif, int servico) throws ServicoExpressFullException, ServicoInexistenteException {
        IFuncionario f= (IFuncionario) utilizador;
        int value= f.registarServicoExpresso(nome, nif, servico);
        if(value== -2) throw new ServicoInexistenteException("Falha no sistema!");
        else if(value== -1) throw new ServicoExpressFullException("Serviço Express indisponível. Registo de serviço express não efetuado!");
    }

    /**
     * Método que regista uma entrega ao cliente e pagamanto do equipamento
     * @param codigo
     * @return
     */
    public void registarEntregaPagamento(String codigo) throws EquipamentoInexistenteException, EquipamentoNaoReparadoException {
        IFuncionario f= (IFuncionario) utilizador;
        int value= f.registarEntregaPagamento(codigo);

        if(value== -1) throw new EquipamentoInexistenteException("Equipamento inexistente!");
        else if(value== 0) throw new EquipamentoNaoReparadoException("Equipamento ainda não está reparado!");
    }

    public List<String> estatisticasFuncionario(){
        IGestor g= (IGestor) utilizador;
        return g.estatisticasFuncionarios();
    }

    public List<String> estatisticasTecnicos(){
        IGestor g = (IGestor) utilizador;
        return g.estatisticasTecnicos();
    }

    public List<String> estatisticasTecnicosExaustiva(){
        IGestor g = (IGestor) utilizador;
        return g.estatisticasTecnicosExaustiva();
    }

    public void colocaEmEspera(String codigo){
        ITecnico t= (ITecnico) utilizador;
        t.colocaEmEspera(codigo);
    }

    public List<String> standbyCodigos(){
        ITecnico t= (ITecnico) utilizador;
        return t.standbyCodigos();
    }

    public void avancaProgresso(String cod, double preco, int tempo){
        ITecnico t= (ITecnico) utilizador;
        t.avancaProgresso(cod, preco, tempo);
    }

    public int getProgresso(String cod){
        ITecnico t= (ITecnico) utilizador;
        return t.getProgresso(cod);
    }

    public List<String> orcamentoCodigos(){
        Tecnico t= (Tecnico) utilizador;
        return t.orcamentoCodigos();
    }

    /**
     * Metodo que permite ao gestor fazer uma avaliacao ao centro
     * @param avaliacao
     */
    public void fazerAvaliacao(Integer avaliacao) {
        IGestor g= (IGestor) utilizador;
        g.fazerAvaliacao(avaliacao);
    }

    public List<String> finalizadosCodigos(){//----------
        IFuncionario f= (IFuncionario) utilizador;
        return f.finalizadosCodigos();
    }
    public double getOrcamento(String cod){ //------------------------------------
        ITecnico t= (ITecnico) utilizador;
        return t.getOrcamento(cod);
    }

    public double getPrecoTotal(String cod){ //-----------------------------------
        ITecnico t= (ITecnico) utilizador;
        return t.getPrecoTotal(cod);
    }

    public void vaiAssimMesmo(String cod){ //-----------------------------------
        ITecnico t= (ITecnico) utilizador;
        t.vaiAssimMesmo(cod);
    }

    public List<String> verAsAvaliacoes() {
        IGestor g= (IGestor) utilizador;
        return g.verAsAvaliacoes();
    }

//------------------------------------------------------------------------------------------------------------------------------------------------------------------------------//

    /**
     * Métodos relacionados com a Base de Dados
     */



    /**
     * Método que devolve o valor a pagar após uma reparação
     * @param cod
     * @return
     */
    public static double valorAPagar(String cod){
        if(!SystemInfo.finalizados.containsKey(cod)) return -1;
        IRegisto r= SystemInfo.finalizados.get(cod);
        return r.getCusto();
    }

    /**
     * Método que devolve o registo (pedido de reparação) mais urgente
     * @return
     */
    public String maisUrgente(){

        IRegisto ret = null;
        int max = -1;
        for(IRegisto r : SystemInfo.reparacoes) {
            if (r.getUrgente() > max) {
                ret = r;
                max = ret.getUrgente();
            }
        }
        if(!SystemInfo.servicosExpress.isEmpty()){
            IRegisto expr = SystemInfo.servicosExpress.get(0);
            if(ret == null) return expr.getCod();
            else if(ret.getUrgente() < 5) return expr.getCod();
            else if(ret.getUrgente() == 5 && ret.getDataEntrada().isAfter(expr.getDataEntrada()))
                return expr.getCod();
        }
        return ret.getCod();
    }

    /**
     *Função que seleciona o servico a efetuar dependendo da urgencia
     */
    public String selecionaReparacao(String codigo) {
        IRegisto ret = null;
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
                    break;
                }
            }
        }
        if(ret == null && SystemInfo.standBy.containsKey(utilizador.getId()) &&
                SystemInfo.standBy.get(utilizador.getId()).containsKey(codigo))
            ret = SystemInfo.standBy.get(utilizador.getId()).get(codigo);
        if(ret == null) return null;

        if(ret.getProgresso()== 0) ret.iniciaReparacao();
        StringBuilder info= new StringBuilder();
        info.append("Nif do cliente: ").append(ret.getNif()).append("\n");
        info.append("Código de registo: ").append(ret.getCod()).append("\n");
        info.append("Decrição do problema: ").append(ret.getDescricaoProblema()).append("\n");
        info.append("Equipamento do registo: ").append(ret.getEquipamento()).append("\n");
        info.append("Orcamento da reparação: ").append(ret.getOrcamento()).append("\n");
        info.append("Data de entrega do equipamento: ").append(ret.getDataEntrada()).append("\n");
        if(ret.getExpress() != -1) info.append("Plano de trabalho associado ao servico: ").append("\n");
        int count = 1;
        for(String s : ret.getPlanoTrabalho()) {
            info.append("Passo ").append(count).append(" ").append(s).append("\n");
            count++;
        }

        return info.toString();
    }

    /**
     * Método que devolve a informação do pedido de orçamento mais antigo
     * @return
     */
    public String infoRegisto(){
        if(SystemInfo.orcamentos.size()== 0) return null;

        StringBuilder info= new StringBuilder();
        info.append("Nif do cliente: ").append(SystemInfo.orcamentos.get(0).getNif()).append("\n");
        info.append("Equipamento: ").append(SystemInfo.orcamentos.get(0).getEquipamento()).append("\n");
        info.append("Código de registo: ").append(SystemInfo.orcamentos.get(0).getCod()).append("\n");
        info.append("Decrição do problema: ").append(SystemInfo.orcamentos.get(0).getDescricaoProblema()).append("\n");
        return info.toString();
    }

//------------------------------------------------------------------------------------------------------------------------------------------------------------------------------//

    /**
     * Os métodos abaixo têm de ser invocados no arranque do sistema, de forma a atualizar a informação
     */

    /**
     * Método que atualiza a lista de pedidos de orçamentos
     * @return
     */
    public void atualizaArmazem(){
        if(SystemInfo.orcamentos.size()!= 0) {
            for (int i = 0; i < SystemInfo.orcamentos.size(); ) {
                IRegisto r = SystemInfo.orcamentos.get(i);
                if (Duration.between(r.getDataEntrada(), LocalDateTime.now()).toDays() >= 30) {
                    SystemInfo.armazenados.put(r.getCod(), r);
                    SystemInfo.orcamentos.remove(i);
                } else i++;
            }
        }
    }

    // Envia todos os equimentos finalizados à mais de 90 dias para uma lista de abandono

    public void verificarAbandono(){
        List<String> cods = new ArrayList<>();
        for(IRegisto reg : SystemInfo.finalizados.values())
            if(Duration.between(reg.getDataSaida(), LocalDateTime.now()).toDays() > 90)
                cods.add(reg.getCod());

        for(String cod : cods){
            SystemInfo.abandonados.put(cod, SystemInfo.finalizados.get(cod));
            SystemInfo.finalizados.remove(cod);
        }
    }

//region Pré-Condições

    public boolean haOrcamentosParaCalcular() { return !SystemInfo.orcamentos.isEmpty(); }

    public boolean haEquipamentoParaLevantar() { return !SystemInfo.finalizados.isEmpty(); }

    /**
     * Função que verifica se existe reparações para executar
     */
    public boolean haReparacoesParaExecutar() {
        return (!SystemInfo.reparacoes.isEmpty() || !SystemInfo.servicosExpress.isEmpty()); }

    /**
     * Função que verifica se existe reparações para retomar
     */
    public boolean haReparacoesParaRetomar() {
        return (SystemInfo.standBy.containsKey(utilizador.getId()) &&
                !SystemInfo.standBy.get(utilizador.getId()).isEmpty());
    }

    /**
     * Função que verifica se existe orçamentos para confimar
     */
    public boolean haOrcamentosParaConfirmar() { return !SystemInfo.mapEspera.isEmpty(); }

    public boolean haTecnicos(){
        return !SystemInfo.tecnicos.isEmpty();
    }

    public boolean haFuncionarios(){
        return !SystemInfo.funcionarios.isEmpty();
    }

    public boolean haHistoricoDeReparacoes(){
        return (!SystemInfo.reparacoesEfetuadas.isEmpty() ||
                !SystemInfo.reparacoesExpressEfetuadas.isEmpty());
    }

    public boolean haAvaliacoes() { return !SystemInfo.avaliacoes.isEmpty(); }
    //endregion
}