package com.example.sistemapedidos;

import com.example.sistemapedidos.builder.PedidoBuilder;
import com.example.sistemapedidos.factory.ProdutoFactory;
import com.example.sistemapedidos.model.Cliente;
import com.example.sistemapedidos.model.Pedido;
import com.example.sistemapedidos.model.produto.Produto;
import com.example.sistemapedidos.singleton.ConfiguracaoSistema;

import java.time.LocalDateTime;

public class Main {
    public static void main(String[] args) {
        System.out.println("Bem-vindo ao Sistema de Pedidos Online (Simplificado)!\n");

        // --- Demonstração do Singleton ---
        System.out.println("--- Testando Singleton: ConfiguracaoSistema ---");
        ConfiguracaoSistema config1 = ConfiguracaoSistema.getInstance();
        config1.exibirConfiguracoes();

        // Tenta obter a instância novamente
        ConfiguracaoSistema config2 = ConfiguracaoSistema.getInstance();
        System.out.println("Config1 e Config2 são a mesma instância? " + (config1 == config2)); // Deve ser true
        config2.setMoedaPadrao("USD"); // Modifica através de config2
        System.out.println("Moeda padrão em config1 (após modificar via config2): " + config1.getMoedaPadrao()); // Deve ser USD
        System.out.println();

        // --- Demonstração do Factory Method (Simple Factory) ---
        System.out.println("--- Testando Factory: ProdutoFactory ---");
        Produto livro = ProdutoFactory.criarProduto("FISICO", "O Senhor dos Anéis", 75.90, 1.2); // 1.2 kg
        Produto ebook = ProdutoFactory.criarProduto("DIGITAL", "Java para Iniciantes Ebook", 29.99, "http://example.com/java-ebook.pdf");
        Produto software = ProdutoFactory.criarProduto("DIGITAL", "Antivirus Pro", 99.50, "http://example.com/antivirus-key");
        // Produto invalido = ProdutoFactory.criarProduto("INVALIDO", "Produto Teste", 10.0); // Descomente para testar exceção

        livro.exibirDetalhes();
        ebook.exibirDetalhes();
        software.exibirDetalhes();
        System.out.println();

        // --- Demonstração do Builder ---
        System.out.println("--- Testando Builder: PedidoBuilder ---");
        Cliente cliente1 = new Cliente("CLI001", "Ana Silva", "ana.silva@example.com");
        Cliente cliente2 = new Cliente("CLI002", "Bruno Costa", "bruno.costa@example.com");

        // Criando o primeiro pedido
        System.out.println("Criando Pedido 1 para " + cliente1.getNome() + "...");
        try {
            PedidoBuilder builderPedido1 = new PedidoBuilder();
            Pedido pedido1 = builderPedido1
                    .comCliente(cliente1)
                    .adicionarItem(livro, 1)
                    .adicionarItem(ebook, 2)
                    .comData(LocalDateTime.now().minusDays(1)) // Pedido de ontem
                    .comStatusInicial("AGUARDANDO_PAGAMENTO")
                    .construir();

            pedido1.exibirDetalhes();
            System.out.println("Pedido 1 criado com sucesso!\n");

        } catch (IllegalStateException | IllegalArgumentException e) {
            System.err.println("Erro ao criar Pedido 1: " + e.getMessage());
        }


        // Criando o segundo pedido
        System.out.println("Criando Pedido 2 para " + cliente2.getNome() + "...");
        try {
            PedidoBuilder builderPedido2 = new PedidoBuilder();
            Pedido pedido2 = builderPedido2
                    .comCliente(cliente2)
                    .adicionarItem(software, 1)
                    // .adicionarItem(livro, 0) // Descomente para testar exceção de quantidade
                    .construir(); // Usa data e status padrão

            pedido2.exibirDetalhes();
            System.out.println("Pedido 2 criado com sucesso!\n");

        } catch (IllegalStateException | IllegalArgumentException e) {
            System.err.println("Erro ao criar Pedido 2: " + e.getMessage());
        }

        // Tentativa de criar um pedido inválido (sem cliente)
        System.out.println("Tentando criar um pedido inválido (sem cliente)...");
        try {
            Pedido pedidoInvalido = new PedidoBuilder()
                    .adicionarItem(livro, 1)
                    .construir();
            pedidoInvalido.exibirDetalhes();
        } catch (IllegalStateException e) {
            System.err.println("Erro esperado ao criar pedido inválido: " + e.getMessage());
        }
        System.out.println();

        System.out.println("Demonstração concluída.");
    }
}