package com.company;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class MenuUI{

    private Scanner sc;
    private ILNSistema centro;

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
        IMenu menu = new Menu(new String[]{
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
            System.out.println("Autenticação bem sucedida");
            this.menuFuncionario();
        }
        catch(UtilizadorInexistenteException e){
            System.out.println("ID inexistente");
        }
        catch (PalavraPasseIncorretaException e){
            System.out.println("Palavra-passe incorreta");
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
            System.out.println("Autenticação bem sucedida");
            this.menuTecnico();
        }
        catch(UtilizadorInexistenteException e){
            System.out.println("ID inexistente");
        }
        catch (PalavraPasseIncorretaException e){
            System.out.println("Palavra-passe incorreta");
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
            System.out.println("Autenticação bem sucedida");
            this.menuGestor();
        }
        catch(UtilizadorInexistenteException e){
            System.out.println("ID inexistente");
        }
        catch (PalavraPasseIncorretaException e){
            System.out.println("Palavra-passe incorreta");
        }
    }


    // <------------------- FUNCIONARIO --------------------->

    // Menu do Funcionário
    private void menuFuncionario() {
        Menu menu = new Menu(new String[]{
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
        System.out.println("Pedido registado com sucesso");
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
            System.out.println("Equipamento registado com sucesso");
        }
        catch(ServicoExpressFullException e){
            System.out.println("Sem serviços express disponiveis");
        }
        catch (FalhaSistemaException e){
            System.out.println("Não faço a minima ideia porque é que isto aconteceu!!!");
        }
    }

    // Permite ao utilizador levantar e/ou pagar um equipamento
    public void levantarEquipamento() {
        System.out.println("Inserir o codigo do equipamento: ");
        String cod = getLine();
        double valor = LNSistema.valorAPagar(cod);
        if(valor == -1) {
            System.out.println("Código inválido");
            return;
        }
        System.out.println("Tem a pagar: " + valor + "€");
        try{
            System.out.println("1 -> Levantado\n2 -> Cancelar");
            int op = getInt();
            if(op == 1) {
                this.centro.registarEntregaPagamento(cod);
                System.out.println("Foi efetuado o pagamento do serviço e levantamento do equipamento");
            }
            else System.out.println("Levantamento cancelado");
        }
        catch(EquipamentoInexistenteException e){
            System.out.println("Equipamento inexistente");
        }
        catch (EquipamentoNaoReparadoException e){
            System.out.println("Equipamento não reparado");
        }
    }


    // <------------------ TECNICO -------------------->

    // Menu do Técnico
    private void menuTecnico(){
        Menu menu = new Menu(new String[]{
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
        System.out.println("Insira o passo " + count++);
        while(!(linha = getLine()).isEmpty() && !linha.equals("\n")){
            System.out.println("Insira o passo " + count++);
            plt.add(linha);
        }
        if(plt.isEmpty()){
            System.out.println("Plano de trabalhos não realizado");
            return;
        }
        System.out.println("Insira uma estimativa do custo da reparação");
        double preco;
        preco = getDouble();
        if(preco <= 0){
            System.out.println("Plano de trabalhos não realizado");
            return;
        }
        System.out.println("Insira uma estimativa do tempo da reparação");
        double tempo;
        tempo = getDouble();
        if(tempo <= 0){
            System.out.println("Plano de trabalhos não realizado");
            return;
        }
        try {
            centro.setPlanoTrabalhos(plt);
            centro.setTempoReparacao(tempo);
            centro.setOrcamento(preco);
            System.out.println("Envie email ao cliente com o valor\ndo orçamento e o codigo do equipamento");
        }
        catch (PedidosOrcamentosException  e){
            System.out.println("Sem pedidos de orçamentos");
        }
    }

    // Mostra ao técnico qual o equipemento a reparar e permite avançar com o plano de trabalhos
    public void executarReparacao(){
        String cod = centro.maisUrgente();
        System.out.println(centro.selecionaReparacao(cod));
        int passo = centro.getProgresso(cod);
        if(passo == -1){
            System.out.println("1 -> Reparação feita\n2 -> Cancelar");
            int op = getInt();
            if(op == 1){
                System.out.println("Insira custo ds reparação");
                double preco = getDouble();
                if(preco >= 0)
                    centro.avancaProgresso(cod, preco);
                else System.out.println("Custo incorreto");
                return;
            }
            else{
                centro.colocaEmEspera(cod);
                System.out.println("A reparação está em espera para ser retomada");
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
                if(preco >= 0)
                    centro.avancaProgresso(cod, preco);
                else System.out.println("Custo incorreto");
            }
            else{
                centro.colocaEmEspera(cod);
                System.out.println("A reparação está em espera para ser retomada");
                return;
            }
            passo = centro.getProgresso(cod);
            if(passo == -1){
                System.out.println("Reparação concluida");
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
            System.out.println("Codigo incorreto");
            return;
        }
        System.out.println(info);
        int passo = centro.getProgresso(cod);
        if(passo == -1){
            System.out.println("1 -> Reparação feita\n2 -> Cancelar");
            int op = getInt();
            if(op == 1){
                System.out.println("Insira custo ds reparação");
                double preco = getDouble();
                if(preco >= 0)
                    centro.avancaProgresso(cod, preco);
                else System.out.println("Custo incorreto");
                return;
            }
            else{
                centro.colocaEmEspera(cod);
                System.out.println("A reparação está em espera para ser retomada");
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
                if(preco >= 0)
                    centro.avancaProgresso(cod, preco);
                else System.out.println("Custo incorreto");
            }
            else{
                centro.colocaEmEspera(cod);
                System.out.println("A reparação está em espera para ser retomada");
                return;
            }
            passo = centro.getProgresso(cod);
            if(passo == -1){
                System.out.println("Reparação concluida");
                return;
            }
            System.out.println("1 -> Passo " + (passo + 1) + " feito");
            System.out.println("2 -> Cancelar");
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
            if(res == 1) System.out.println("Equipamento enviado para reparação");
            else System.out.println("Equipamento pronto para ser levantado");
        }
        catch (EquipamentoInexistenteException e){
            System.out.println("Equipamento inexistente");
        }
    }


    // <---------------- GESTOR ------------------>

    // Menu do Gestor
    private void menuGestor(){
        Menu menu = new Menu(new String[]{
                "Consultar estatisticas dos técnicos",
                "Consultar estatisticas dos funcionários",
                "Consultar lista de reparações",
        });

        // Registar pré-condições das transições
        menu.setPreCondition(1, ()->this.centro.haTecnicos());
        menu.setPreCondition(2, ()->this.centro.haFuncionarios());
        menu.setPreCondition(3, ()->this.centro.haHistoricoDeReparacoes());

        // Registar os handlers das transições
        menu.setHandler(1, ()->estatisticasTecnicos());
        menu.setHandler(2, ()->estatisticasFuncionarios());
        menu.setHandler(3, ()->listaDeReparacoes());

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