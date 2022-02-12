package com.SistemaReparações.Menu;

import com.SistemaReparações.LogicaDeNegocios.Exceptions.*;
import com.SistemaReparações.LogicaDeNegocios.Sistema.ILNSistema;
import com.SistemaReparações.LogicaDeNegocios.Sistema.LNSistema;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class MenuUI{

    private Scanner sc;
    private ILNSistema centro;
    public static final String TEXT_RESET = "\u001B[0m";
    public static final String TEXT_RED = "\u001B[31m";
    public static final String TEXT_GREEN = "\u001B[32m";
    public static final String TEXT_YELLOW = "\u001B[33m";

    // Construtor vazio
    public MenuUI() {
        this.sc = new Scanner(System.in);
        this.centro = new LNSistema();
    }


    // Executa o menu principal e invoca o método correspondente à opção seleccionada.
    public void run() {
        System.out.println("Bem vindo ao Sistema de Gestão para Centros de Reparação de equipamentos electrónicos!");
        this.menuAutenticar();
        System.out.println("Até breve...");
    }


    // <------------------- AUTENTICAÇÃO --------------------->

    // Menu de autenticação
    private void menuAutenticar(){
        IMenu menu = new Menu("AUTENTICAÇÃO", new String[]{
                "Autenticar Funcionário",
                "Autenticar Técnico",
                "Autenticar Gestor",
        });

        // Registar os handlers das transições
        menu.setHandler(1, ()->autenticarFuncionario());
        menu.setHandler(2, ()->autenticarTecnico());
        menu.setHandler(3, ()->autenticarGestor());

        menu.run();
    }


    // Autenticar Funcionário
    public void autenticarFuncionario(){
        System.out.println("Insira o seu ID:");
        String id = getLine();
        System.out.println("Insira a palavra-passe");
        String pass = getLine();
        try{
            centro.autenticarFuncionario(id, pass);
            System.out.println(TEXT_GREEN + "Autenticação bem sucedida" + TEXT_RESET);
            this.menuFuncionario();
        }
        catch(UtilizadorInexistenteException e){
            System.out.println(TEXT_RED + "ID inexistente" + TEXT_RESET);
        }
        catch (PalavraPasseIncorretaException e){
            System.out.println(TEXT_RED + "Palavra-passe incorreta" + TEXT_RESET);
        }
    }

    // Autenticar Técnico
    public void autenticarTecnico(){
        System.out.println("Insira o seu ID:");
        String id = getLine();
        System.out.println("Insira a palavra-passe");
        String pass = getLine();
        try{
            centro.autenticarTecnico(id, pass);
            System.out.println(TEXT_GREEN + "Autenticação bem sucedida" + TEXT_RESET);
            this.menuTecnico();
        }
        catch(UtilizadorInexistenteException e){
            System.out.println(TEXT_RED + "ID inexistente" + TEXT_RESET);
        }
        catch (PalavraPasseIncorretaException e){
            System.out.println(TEXT_RED + "Palavra-passe incorreta" + TEXT_RESET);
        }
    }

    // Autenticar Gestor
    public void autenticarGestor(){
        System.out.println("Insira o seu ID:");
        String id = getLine();
        System.out.println("Insira a palavra-passe");
        String pass = getLine();
        try{
            centro.autenticarGestor(id, pass);
            System.out.println(TEXT_GREEN + "Autenticação bem sucedida" + TEXT_RESET);
            this.menuGestor();
        }
        catch(UtilizadorInexistenteException e){
            System.out.println(TEXT_RED + "ID inexistente" + TEXT_RESET);
        }
        catch (PalavraPasseIncorretaException e){
            System.out.println(TEXT_RED + "Palavra-passe incorreta" + TEXT_RESET);
        }
    }


    // <------------------- FUNCIONARIO --------------------->

    // Menu do Funcionário
    private void menuFuncionario() {
        Menu menu = new Menu("MENU FUNCIONÁRIO", new String[]{
                "Registar serviço expresso (TM)",
                "Pedido de Orcamento",
                "Levantar Equipamento",
        });

        // Registar pré-condições das transições
        menu.setPreCondition(3, ()->this.centro.haEquipamentoParaLevantar());

        // Registar os handlers das transições
        menu.setHandler(1, ()->pedirServicoExpresso());
        menu.setHandler(2, ()->pedidoOrcamento());
        menu.setHandler(3, ()->levantarEquipamento());

        // Executar o menu
        menu.run();
    }

    // Função que permite ao utilizador pedir um serviço normal
    public void pedidoOrcamento() {
        System.out.println("Inserir o NIF do cliente: ");
        String nif = getLine();
        System.out.println("Inserir o tipo de equipamento: ");
        String equipamento = getLine();
        System.out.println("Insira o problema sobre o equipamento: ");
        String descricaoProblema = getLine();
        System.out.println("Insira a urgencia do serviço (1-10): ");
        int urgente = getInt();
        this.centro.registarServico(equipamento,nif,descricaoProblema,urgente);
        System.out.println(TEXT_GREEN + "Pedido registado com sucesso" + TEXT_RESET);
    }

    // Função que permite ao utilizador pedir um serviço Expresso(TM)
    public void pedirServicoExpresso() {
        System.out.println("<--- Serviços Expressos(TM) --->");
        System.out.println(" 0 -> ARRANJAR_ECRA    - 30.0 €");
        System.out.println(" 1 -> INSTALAR_SO      - 10.0 €");
        System.out.println(" 2 -> TROCAR_BATERIA   - 25.0 €");
        System.out.println(" 3 -> LIMPEZA          - 50.0 €");
        System.out.println("Selecione o serviço: ");
        int servico = getInt();
        System.out.println("Inserir o NIF do cliente: ");
        String nif = getLine();
        System.out.println("Inserir o tipo de equipamento: ");
        String equipamento = getLine();
        try{
            this.centro.registarServicoExpresso(equipamento,nif,servico);
            System.out.println(TEXT_GREEN + "Equipamento registado com sucesso" + TEXT_RESET);
        }
        catch(ServicoExpressFullException e){
            System.out.println(TEXT_RED + "Sem serviços express disponiveis" + TEXT_RESET);
        }
        catch (ServicoInexistenteException e){
            System.out.println(TEXT_RED + "Serviço inexistente" + TEXT_RESET);
        }
    }

    // Permite ao utilizador levantar e/ou pagar um equipamento
    public void levantarEquipamento() {
        System.out.println("Equipamentos para levantamento\n0 -> Sair");
        List<String> list = centro.finalizadosCodigos();//.forEach(System.out::println);
        int count = 1;
        for(String s : list){
            System.out.println(count++ + " -> " + s);
        }
        System.out.print("Codigo: ");
        int opt = getInt();
        if(opt == 0 || opt > list.size()) return;
        String cod = list.get(opt - 1);
        double valor = LNSistema.valorAPagar(cod);
        if(valor == -1) {
            System.out.println(TEXT_RED + "Código inválido" + TEXT_RESET);
            return;
        }
        System.out.println("Tem a pagar: " + valor + "€");
        try{
            System.out.println("1 -> Levantado\n2 -> Cancelar");
            int op = getInt();
            if(op == 1) {
                this.centro.registarEntregaPagamento(cod);
                System.out.println(TEXT_GREEN + "Foi efetuado o pagamento do serviço e levantamento do equipamento" + TEXT_RESET);
            }
            else System.out.println("Levantamento cancelado");
        }
        catch(EquipamentoInexistenteException e){
            System.out.println(TEXT_RED + "Equipamento inexistente" + TEXT_RESET);
        }
        catch (EquipamentoNaoReparadoException e){
            System.out.println(TEXT_RED + "Equipamento não reparado" + TEXT_RESET);
        }
    }


    // <------------------ TECNICO -------------------->

    // Menu do Técnico
    private void menuTecnico(){
        Menu menu = new Menu("MENU TÉCNICO", new String[]{
                "Calcular orçamento",
                "Executar reparação",
                "Retomar reparação",
                "Confirmar orçamentos",
        });

        // Registar pré-condições das transições
        menu.setPreCondition(1, ()->this.centro.haOrcamentosParaCalcular());
        menu.setPreCondition(2, ()->this.centro.haReparacoesParaExecutar());
        menu.setPreCondition(3, ()->this.centro.haReparacoesParaRetomar());
        menu.setPreCondition(4, ()->this.centro.haOrcamentosParaConfirmar());

        // Registar os handlers das transições
        menu.setHandler(1, ()->calcularOrcamento());
        menu.setHandler(2, ()->executarReparacao());
        menu.setHandler(3, ()->retomarReparacao());
        menu.setHandler(4, ()->confirmarOrcamento());

        // Executar o menu
        menu.run();
    }

    // Pemrite ao técnico inserir um plano de trabalhos e um orçamento de um equipamento
    public void calcularOrcamento(){
        System.out.println(centro.infoRegisto());
        System.out.println("Insira o plano de trabalho");
        String linha;
        List<String> plt = new ArrayList<>();
        int count = 1;
        double preco = 0;
        double tempo = 0;
        System.out.println("Insira o passo " + count++);
        while(!(linha = getLine()).isEmpty() && !linha.equals("\n")){
            System.out.println("Insira uma estimativa do custo do passo");
            preco += getDouble();
            System.out.println("Insira uma estimativa do tempo do passo em minutos");
            tempo += getDouble();
            System.out.println("Insira o passo " + count++);
            plt.add(linha);
        }
        if(plt.isEmpty()){
            System.out.println(TEXT_RED + "Plano de trabalhos não realizado" + TEXT_RESET);
            return;
        }
        try {
            centro.setPlanoTrabalhos(plt);
            centro.setTempoReparacao(tempo);
            centro.setOrcamento(preco);
            System.out.println(TEXT_GREEN + "Envie email ao cliente com o valor\ndo orçamento e o codigo do equipamento" + TEXT_RESET);
        }
        catch (PedidosOrcamentosException e){
            System.out.println(TEXT_RED + "Sem pedidos de orçamentos" + TEXT_RESET);
        }
    }

    // Mostra ao técnico qual o equipemento a reparar e permite avançar com o plano de trabalhos
    public void executarReparacao(){
        String cod = centro.maisUrgente();
        System.out.println(centro.selecionaReparacao(cod));
        int passo = centro.getProgresso(cod);
        double orcamento = centro.getOrcamento(cod);
        double preco_total = centro.getPrecoTotal(cod);
        if(passo == -1){
            System.out.println("1 -> Reparação feita\n2 -> Cancelar");
            int op = getInt();
            if(op == 1){
                centro.avancaProgresso(cod, orcamento, 0);
                System.out.println(TEXT_GREEN + "Reparação concluida" + TEXT_RESET);
                return;
            }
            else{
                centro.colocaEmEspera(cod);
                System.out.println(TEXT_YELLOW + "A reparação está em espera para ser retomada" + TEXT_RESET);
                return;
            }
        }
        System.out.println("1 -> Passo " + (passo + 1) + " feito");
        System.out.println("2 -> Cancelar");
        int op = getInt();
        while(passo >= 0){
            if(op == 1){
                System.out.println("Insira custo do passo " + (passo + 1));
                double preco = getDouble();
                if(preco + preco_total > orcamento * 1.2){
                    System.out.println(TEXT_RED + "Custo da reparação ultrapassou os 120% do orcamento inicial\nContacte o cliente por email" + TEXT_RESET);
                    centro.colocaEmEspera(cod);
                    System.out.println("A reparação está em espera para ser retomada");
                    return;
                }
                if(preco < 0) {
                    System.out.println(TEXT_RED + "Custo incorreto" + TEXT_RESET);
                    return;
                }
                System.out.println("Insira tempo gasto no passo " + (passo + 1));
                int tempo = getInt();
                if(tempo < 0){
                    System.out.println(TEXT_RED + "Tempo inválido" + TEXT_RESET);
                    return;
                }
                preco_total += preco;
                centro.avancaProgresso(cod, preco, tempo);
                if(passo == -1){
                    System.out.println(TEXT_GREEN + "Reparação concluida" + TEXT_RESET);
                    return;
                }
            }
            else{
                centro.colocaEmEspera(cod);
                System.out.println(TEXT_YELLOW + "A reparação está em espera para ser retomada" + TEXT_RESET);
                return;
            }
            passo = centro.getProgresso(cod);
            if(passo == -1){
                System.out.println(TEXT_GREEN + "Reparação concluida" + TEXT_RESET);
                return;
            }
            System.out.println("1 -> Passo " + (passo + 1) + " feito");
            System.out.println("2 -> Cancelar");
            op = getInt();
        }
    }

    // Permite ao técnico retormar uma reparação que tenha sido posta em espera
    public void retomarReparacao(){
        System.out.println("Equipamentos em espera\n0 -> Sair");
        List<String> list = centro.standbyCodigos();//.forEach(System.out::println);
        int count = 1;
        for(String s : list){
            System.out.println(count++ + " -> " + s);
        }
        System.out.print("Codigo: ");
        int opt = getInt();
        if(opt == 0 || opt > list.size()) return;
        String cod = list.get(opt - 1);
        String info = centro.selecionaReparacao(cod);
        if(info == null){
            System.out.println(TEXT_RED + "Codigo incorreto" + TEXT_RESET);
            return;
        }
        System.out.println(info);
        int passo = centro.getProgresso(cod);
        double orcamento = centro.getOrcamento(cod);
        double preco_total = centro.getPrecoTotal(cod);
        if(passo == -1){
            System.out.println("1 -> Reparação feita\n2 -> Cancelar");
            int op = getInt();
            if(op == 1){
                centro.avancaProgresso(cod, orcamento, 0);
                System.out.println(TEXT_GREEN + "Reparação concluida" + TEXT_RESET);
                return;
            }
            else{
                centro.colocaEmEspera(cod);
                System.out.println(TEXT_YELLOW + "A reparação está em espera para ser retomada" + TEXT_RESET);
                return;
            }
        }
        System.out.println("1 -> Passo " + (passo + 1) + " feito");
        System.out.println("2 -> Enviar para levantamento\n3 -> Cancelar");
        int op = getInt();
        while(passo >= 0){
            if(op == 1){
                System.out.println("Insira custo do passo " + (passo + 1));
                double preco = getDouble();
                if(preco + preco_total > orcamento * 1.2){
                    System.out.println(TEXT_YELLOW + "ATENÇÂO: Custo da reparação ultrapassou os 120% do orcamento inicial" + TEXT_RESET);
                }
                if(preco < 0) {
                    System.out.println(TEXT_RED + "Custo incorreto" + TEXT_RESET);
                    return;
                }
                System.out.println("Insira tempo gasto no passo " + (passo + 1));
                int tempo = getInt();
                if(tempo < 0){
                    System.out.println(TEXT_RED + "Tempo inválido" + TEXT_RESET);
                    return;
                }
                preco_total += preco;
                centro.avancaProgresso(cod, preco, tempo);
                if(passo == -1){
                    System.out.println(TEXT_GREEN + "Reparação concluida" + TEXT_RESET);
                    return;
                }
            }
            else if(op == 2){
                centro.vaiAssimMesmo(cod);
                System.out.println(TEXT_YELLOW + "Equipamento enviado para levantamento" + TEXT_RESET);
                return;
            }
            else{
                centro.colocaEmEspera(cod);
                System.out.println(TEXT_YELLOW + "A reparação está em espera para ser retomada" + TEXT_RESET);
                return;
            }
            passo = centro.getProgresso(cod);
            if(passo == -1){
                System.out.println(TEXT_GREEN + "Reparação concluida" + TEXT_RESET);
                return;
            }
            System.out.println("1 -> Passo " + (passo + 1) + " feito");
            System.out.println("2 -> Enviar para levantamento\n3 -> Cancelar");
            op = getInt();
        }
    }

    // Permite ao técnico confirmar ou negar uma proposta de orçamento
    public void confirmarOrcamento(){
        System.out.println("0 -> Sair");
        List<String> list = centro.orcamentoCodigos();
        int count = 1;
        for(String s : list){
            System.out.println(count++ + " -> " + s);
        }
        System.out.print("Codigo: ");
        int opt = getInt();
        if(opt == 0 || opt > list.size()) return;
        String cod = list.get(opt - 1);
        System.out.println("0 -> Sair\n1 -> Aceitou\n2 -> Não Aceitou");
        int res = getInt();
        if(res != 1 && res != 2){
            return;
        }
        try{
            centro.respostaAoPedidoOrcamento(res == 1 ? true : false, cod);
            if(res == 1) System.out.println(TEXT_GREEN + "Equipamento enviado para reparação" + TEXT_RESET);
            else System.out.println(TEXT_GREEN + "Equipamento pronto para ser levantado" + TEXT_RESET);
        }
        catch (EquipamentoInexistenteException e){
            System.out.println(TEXT_RED + "Equipamento inexistente" + TEXT_RESET);
        }
    }


    // <---------------- GESTOR ------------------>

    // Menu do Gestor
    private void menuGestor(){
        Menu menu = new Menu("MENU GESTOR", new String[]{
                "Consultar estatisticas dos técnicos",
                "Consultar estatisticas dos funcionários",
                "Consultar lista de reparações",
                "Atribuir avaliação ao centro",
                "Adicionar/Atualizar funcionário",
                "Adicionar/Atualizar técnico",
                "Ver avaliações",
        });

        // Registar pré-condições das transições
        menu.setPreCondition(1, ()->this.centro.haTecnicos());
        menu.setPreCondition(2, ()->this.centro.haFuncionarios());
        menu.setPreCondition(3, ()->this.centro.haHistoricoDeReparacoes());
        menu.setPreCondition(7, ()->this.centro.haAvaliacoes());

        // Registar os handlers das transições
        menu.setHandler(1, ()->estatisticasTecnicos());
        menu.setHandler(2, ()->estatisticasFuncionarios());
        menu.setHandler(3, ()->listaDeReparacoes());
        menu.setHandler(4, ()->fazAvaliacoes());
        menu.setHandler(5, ()->addFuncionario());
        menu.setHandler(6, ()->addTecnico());
        menu.setHandler(7, ()->verAvaliacoes());

        // Executar o menu
        menu.run();
    }

    // Permite ao gestor visulizar estatisticas relativas a cada técnico
    public void estatisticasTecnicos(){
        System.out.println("Estatisticas dos técnicos");
        centro.estatisticasTecnicos().forEach(System.out::println);
    }

    // Permite ao gestor visualizar estatisticas relativas a cada funcionário
    public void estatisticasFuncionarios(){
        System.out.println("Estatisticas dos funcionários");
        centro.estatisticasFuncionario().forEach(System.out::println);
    }

    // Permite ao gestor visualizar uma lista de todas as reparações feitas
    public void listaDeReparacoes(){
        System.out.println("Lista de reparações realizadas");
        centro.estatisticasTecnicos().forEach(System.out::println);
    }

    // Permite ao gestor fazer uma avalição do centro
    public void fazAvaliacoes(){
        System.out.println("Atribui uma avaliação do centro (0-10) ");
        int avaliacao = getInt();
        if(avaliacao < 0  || avaliacao > 10){
            System.out.println(TEXT_RED + "Avaliação inválida" + TEXT_RESET);
            return;
        }
        centro.fazerAvaliacao(avaliacao);
    }

    // Permite ao gestor adicionar/atualizar informações de um funcionário
    public void addFuncionario(){
        System.out.println("Insira o ID do funcionário");
        String id = getLine();
        System.out.println("Insira o nome do funcionário");
        String nome = getLine();
        System.out.println("Insira a palavra-passe do funcionário");
        String pass = getLine();
        centro.addFuncionario(id, nome, pass);
    }

    // Permite ao gestor adicionar/atualizar informações de um técnico
    public void addTecnico(){
        System.out.println("Insira o ID do técnico");
        String id = getLine();
        System.out.println("Insira o nome do técnico");
        String nome = getLine();
        System.out.println("Insira a palavra-passe do técnico");
        String pass = getLine();
        centro.addTecnico(id, nome, pass);
    }

    // Permite ver as avaliações feitas ao centro
    public void verAvaliacoes(){
        System.out.println("Avalições feitas ao centro por cada gestor:");
        centro.verAsAvaliacoes().forEach(System.out::println);
    }
    // <----------------- SCANNER ------------------>

    private String getLine(){
        return sc.nextLine();
    }

    private int getInt(){
        try {
            int num = sc.nextInt();
            sc.nextLine();
            return num;
        }catch (Exception e) {
            sc.nextLine();
            return -1;
        }
    }

    private double getDouble(){
        try {
            double num = sc.nextDouble();
            sc.nextLine();
            return num;
        }catch (Exception e) {
            sc.nextLine();
            return -1;
        }
    }
}