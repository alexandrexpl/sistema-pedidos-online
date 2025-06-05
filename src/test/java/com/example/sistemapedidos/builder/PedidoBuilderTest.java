package com.example.sistemapedidos.builder;

import com.example.sistemapedidos.model.Cliente;
import com.example.sistemapedidos.model.Pedido;
import com.example.sistemapedidos.model.produto.Produto;
import com.example.sistemapedidos.model.produto.ProdutoFisico;
import com.example.sistemapedidos.singleton.ConfiguracaoSistema; // Para testar limite de itens
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;

class PedidoBuilderTest {
    private Cliente clienteTeste;
    private Produto produtoTeste1;
    private Produto produtoTeste2;

    @BeforeEach // Método executado antes de cada teste nesta classe
    void setUp() {
        clienteTeste = new Cliente("C001", "Cliente de Teste", "teste@example.com");
        produtoTeste1 = new ProdutoFisico("Produto A", 10.0, 0.1);
        produtoTeste2 = new ProdutoFisico("Produto B", 20.0, 0.2);

        // Garante que a configuração de maxItensPorPedido seja conhecida para os testes
        // É importante notar que o Singleton mantém o estado entre os testes.
        // Idealmente, para testes totalmente isolados, o Singleton precisaria de um método de reset
        // ou usaríamos injeção de dependência para as configurações.
        // Por simplicidade, vamos definir aqui.
        ConfiguracaoSistema.getInstance().setMaxItensPorPedido(5); // Limite para teste
    }

    @Test
    void construir_deveCriarPedidoComClienteEItensCorretamente() {
        Pedido pedido = new PedidoBuilder()
                .comCliente(clienteTeste)
                .adicionarItem(produtoTeste1, 2) // 2 * 10.0 = 20.0
                .adicionarItem(produtoTeste2, 1) // 1 * 20.0 = 20.0
                .construir();

        assertNotNull(pedido, "Pedido não deveria ser nulo.");
        assertEquals(clienteTeste, pedido.getCliente(), "Cliente incorreto no pedido.");
        assertEquals(2, pedido.getItens().size(), "Número de tipos de item incorreto.");
        assertEquals(40.0, pedido.getTotal(), 0.001, "Total do pedido incorreto."); // 0.001 é a tolerância para double
        assertEquals("PENDENTE", pedido.getStatus(), "Status padrão incorreto."); // Builder muda de PENDENTE_INICIAL para PENDENTE
        assertNotNull(pedido.getId(), "ID do pedido não deveria ser nulo.");
        assertNotNull(pedido.getData(), "Data do pedido não deveria ser nula.");
    }

    @Test
    void construir_deveLancarExcecaoSeClienteNaoForDefinido() {
        PedidoBuilder builder = new PedidoBuilder().adicionarItem(produtoTeste1, 1);
        IllegalStateException exception = assertThrows(IllegalStateException.class, builder::construir);
        assertEquals("Cliente é obrigatório para construir o pedido.", exception.getMessage());
    }

    @Test
    void construir_deveLancarExcecaoSeNenhumItemForAdicionado() {
        PedidoBuilder builder = new PedidoBuilder().comCliente(clienteTeste);
        IllegalStateException exception = assertThrows(IllegalStateException.class, builder::construir);
        assertEquals("O pedido deve ter pelo menos um item.", exception.getMessage());
    }

    @Test
    void adicionarItem_deveLancarExcecaoParaQuantidadeInvalida() {
        PedidoBuilder builder = new PedidoBuilder().comCliente(clienteTeste);
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            builder.adicionarItem(produtoTeste1, 0);
        });
        assertEquals("Quantidade do item deve ser positiva.", exception.getMessage());
    }
    
    @Test
    void adicionarItem_deveLancarExcecaoParaProdutoNulo() {
        PedidoBuilder builder = new PedidoBuilder().comCliente(clienteTeste);
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            builder.adicionarItem(null, 1);
        });
        assertEquals("Produto não pode ser nulo ao adicionar item.", exception.getMessage());
    }


    @Test
    void construir_comDataEStatusEspecificos() {
        LocalDateTime dataEspecifica = LocalDateTime.of(2023, 1, 15, 10, 30);
        String statusEspecifico = "PROCESSANDO";

        Pedido pedido = new PedidoBuilder()
                .comCliente(clienteTeste)
                .adicionarItem(produtoTeste1, 1)
                .comData(dataEspecifica)
                .comStatusInicial(statusEspecifico) // Usa comStatusInicial
                .construir();

        assertEquals(dataEspecifica, pedido.getData(), "Data do pedido incorreta.");
        assertEquals(statusEspecifico, pedido.getStatus(), "Status do pedido incorreto.");
    }
    
    @Test
    void adicionarItem_deveLancarExcecaoSeMaximoDeItensForExcedido() {
        ConfiguracaoSistema.getInstance().setMaxItensPorPedido(2); // Define limite baixo para teste
        PedidoBuilder builder = new PedidoBuilder().comCliente(clienteTeste);
        builder.adicionarItem(produtoTeste1, 1); // Item 1
        builder.adicionarItem(produtoTeste2, 1); // Item 2

        // Tenta adicionar o terceiro item, excedendo o limite
        IllegalStateException exception = assertThrows(IllegalStateException.class, () -> {
            builder.adicionarItem(new ProdutoFisico("Produto C", 5.0, 0.05), 1);
        });
        assertEquals("Número máximo de itens (2) por pedido excedido.", exception.getMessage());
        
        // Restaura configuração para não afetar outros testes
        ConfiguracaoSistema.getInstance().setMaxItensPorPedido(50);
    }

    @Test
    void comCliente_deveLancarExcecaoSeClienteForNulo() {
        PedidoBuilder builder = new PedidoBuilder();
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            builder.comCliente(null);
        });
        assertEquals("Cliente não pode ser nulo.", exception.getMessage());
    }
}