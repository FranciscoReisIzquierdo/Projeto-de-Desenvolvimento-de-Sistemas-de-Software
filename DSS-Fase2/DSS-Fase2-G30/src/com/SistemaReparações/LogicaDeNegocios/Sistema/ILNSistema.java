package com.SistemaReparações.LogicaDeNegocios.Sistema;

import com.SistemaReparações.LogicaDeNegocios.Exceptions.*;

import java.util.List;

public interface ILNSistema {
    public void autenticarFuncionario(String id, String palavraPasse) throws PalavraPasseIncorretaException, UtilizadorInexistenteException;
    public void autenticarTecnico(String id, String palavraPasse) throws PalavraPasseIncorretaException, UtilizadorInexistenteException;
    public void autenticarGestor(String id, String palavraPasse) throws PalavraPasseIncorretaException, UtilizadorInexistenteException;
    public void registarServico(String equipamento, String nif, String descricaoProblema, int urgente);
    public void setPlanoTrabalhos(List<String> plano) throws PedidosOrcamentosException;
    public void setOrcamento(double orcamento) throws PedidosOrcamentosException;
    public void setTempoReparacao(double tempo) throws PedidosOrcamentosException;
    public void respostaAoPedidoOrcamento(boolean flag, String cod) throws EquipamentoInexistenteException;
    public void registarServicoExpresso(String nome, String nif, int servico) throws ServicoExpressFullException, ServicoInexistenteException;
    public void registarEntregaPagamento(String codigo) throws EquipamentoInexistenteException, EquipamentoNaoReparadoException;
    public List<String> estatisticasFuncionario();
    public List<String> estatisticasTecnicos();
    public List<String> estatisticasTecnicosExaustiva();
    public void colocaEmEspera(String codigo);
    public List<String> standbyCodigos();
    public void avancaProgresso(String cod, double preco, int tempo);
    public int getProgresso(String cod);
    public String maisUrgente();
    public String selecionaReparacao(String codigo);
    public String infoRegisto();
    public void atualizaArmazem();
    public void verificarAbandono();
    public boolean haOrcamentosParaCalcular();
    public boolean haEquipamentoParaLevantar();
    public boolean haReparacoesParaExecutar();
    public boolean haReparacoesParaRetomar();
    public boolean haOrcamentosParaConfirmar();
    public boolean haTecnicos();
    public boolean haFuncionarios();
    public boolean haHistoricoDeReparacoes();
    public List<String> orcamentoCodigos();
    public boolean haAvaliacoes();
    public void fazerAvaliacao(Integer avaliacao);
    public double getPrecoTotal(String cod);//--
    public double getOrcamento(String cod);//--
    public void vaiAssimMesmo(String cod);//--
    public List<String> finalizadosCodigos();//--
    public List<String> verAsAvaliacoes();
    public void addFuncionario(String id, String nome, String pass);
    public void addTecnico(String id, String nome, String pass);
}
