package com.example.sistemapedidos.singleton;

/**
 * Classe ConfiguracaoSistema - Implementação do Padrão Singleton.
 * Garante que exista apenas uma instância desta classe em toda a aplicação,
 * fornecendo um ponto de acesso global para configurações do sistema.
 *
 * Exemplo de uso: armazenar a moeda padrão, URLs de APIs, etc.
 */
public class ConfiguracaoSistema {

    // 1. A única instância da classe (estática e privada)
    private static ConfiguracaoSistema instancia;

    // Atributos de configuração
    private String moedaPadrao;
    private int maxItensPorPedido;

    // 2. Construtor privado para impedir a instanciação direta de fora da classe.
    private ConfiguracaoSistema() {
        // Simula o carregamento de configurações (poderia vir de um arquivo, banco de dados, etc.)
        this.moedaPadrao = "BRL"; // Real Brasileiro
        this.maxItensPorPedido = 50;
        System.out.println("Instância de ConfiguracaoSistema criada.");
    }

    // 3. Método público estático para obter a única instância da classe.
    // Esta é a "lazy initialization" (inicialização preguiçosa): a instância só é
    // criada quando o método getInstance() é chamado pela primeira vez.
    public static ConfiguracaoSistema getInstance() {
        if (instancia == null) { // Se a instância ainda não foi criada
            // Bloco sincronizado para garantir thread-safety na primeira criação
            // Em ambientes com múltiplas threads, duas threads poderiam tentar criar a instância ao mesmo tempo.
            synchronized (ConfiguracaoSistema.class) {
                if (instancia == null) { // Dupla verificação para eficiência após a primeira criação
                    instancia = new ConfiguracaoSistema();
                }
            }
        }
        return instancia;
    }

    // Getters para as configurações
    public String getMoedaPadrao() {
        return moedaPadrao;
    }

    public int getMaxItensPorPedido() {
        return maxItensPorPedido;
    }

    // Setters (se as configurações puderem ser alteradas em tempo de execução)
    public void setMoedaPadrao(String moedaPadrao) {
        if (moedaPadrao != null && !moedaPadrao.trim().isEmpty()) {
            this.moedaPadrao = moedaPadrao;
        }
    }

    public void setMaxItensPorPedido(int maxItensPorPedido) {
        if (maxItensPorPedido > 0) {
            this.maxItensPorPedido = maxItensPorPedido;
        }
    }

    // Método de exemplo para exibir as configurações
    public void exibirConfiguracoes() {
        System.out.println("--- Configurações do Sistema ---");
        System.out.println("Moeda Padrão: " + moedaPadrao);
        System.out.println("Máximo de Itens por Pedido: " + maxItensPorPedido);
        System.out.println("------------------------------");
    }
}