package com.company;

import java.util.*;

public class SystemInfo {
    public static Map<String, IFuncionario> funcionarios= new HashMap<>();
    public static Map<String, ITecnico> tecnicos= new HashMap<>();
    public static Map<String, IGestor> gestores= new HashMap<>();
    public static List<IRegisto> orcamentos= new ArrayList<>();
    public static Map<String, IRegisto> mapEspera= new HashMap<>();
    public static List<IRegisto> reparacoes= new ArrayList<>();
    public static Map<String, IRegisto> abandonados= new HashMap<>();
    public static Map<String, IRegisto> finalizados= new HashMap<>();
    public static Map<String, IRegisto> armazenados= new HashMap<>();
    public static List<IRegisto> servicosExpress= new ArrayList<>();
    public static Map<String, Map<String, IRegisto>> standBy= new HashMap<>();

    public static Map<String, List<IRegisto>> entradas= new HashMap<>();
    public static Map<String, List<IRegisto>> saidas= new HashMap<>();
    public static Map<String, List<IRegisto>> reparacoesEfetuadas= new HashMap<>();
    public static Map<String, List<IRegisto>> reparacoesExpressEfetuadas= new HashMap<>();


    public final static int maxSizeExpress= 10;

    public static final int ARRANJAR_ECRA= 0;
    public static final int INSTALAR_SO= 1;
    public static final int TROCAR_BATERIA= 2;
    public static final int LIMPEZA= 3;
    public static final double PRECO_ARRANJAR_ECRA= 30.0;
    public static final double PRECO_INSTALAR_SO= 10.0;
    public static final double PRECO_TROCAR_BATERIA= 25.0;
    public static final double PRECO_LIMPEZA= 50.0;

    public static double dinheiro= 0;


    public static String getDescricaoServico(int servico){
        switch (servico){
            case SystemInfo.ARRANJAR_ECRA:
                return "Arranjar ecrã";
            case SystemInfo.INSTALAR_SO:
                return "Instalar sistema operativo";
            case SystemInfo.TROCAR_BATERIA:
                return "Trocar bateria";
            case SystemInfo.LIMPEZA:
                return "Limpeza";
            default:
                return null;
        }
    }




}