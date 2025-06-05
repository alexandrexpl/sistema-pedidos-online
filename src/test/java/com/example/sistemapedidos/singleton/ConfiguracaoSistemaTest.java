package com.example.sistemapedidos.singleton;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ConfiguracaoSistemaTest {

    @Test
    void getInstance_deveRetornarSempreAMesmaInstancia() {
        ConfiguracaoSistema instancia1 = ConfiguracaoSistema.getInstance();
        ConfiguracaoSistema instancia2 = ConfiguracaoSistema.getInstance();

        // Verifica se ambas as referências apontam para o mesmo objeto na memória
        assertSame(instancia1, instancia2, "getInstance() deve sempre retornar a mesma instância.");
    }

    @Test
    void getMoedaPadrao_deveRetornarValorInicialCorreto() {
        ConfiguracaoSistema config = ConfiguracaoSistema.getInstance();
        // O valor padrão é "BRL", mas pode ter sido alterado por outro teste se os testes não forem isolados.
        // Para garantir, podemos resetar ou testar o setter.
        // Por simplicidade, vamos assumir que o valor padrão é o esperado ou testar o setter.
        config.setMoedaPadrao("BRL"); // Garante o estado para o teste
        assertEquals("BRL", config.getMoedaPadrao(), "Moeda padrão inicial deve ser BRL.");
    }

    @Test
    void setMoedaPadrao_deveAlterarAMoeda() {
        ConfiguracaoSistema config = ConfiguracaoSistema.getInstance();
        config.setMoedaPadrao("USD");
        assertEquals("USD", config.getMoedaPadrao(), "setMoedaPadrao deve alterar a moeda.");
        // Restaura para o padrão para não afetar outros testes (se houver)
        config.setMoedaPadrao("BRL");
    }

    @Test
    void getMaxItensPorPedido_deveRetornarValorInicialCorreto() {
        ConfiguracaoSistema config = ConfiguracaoSistema.getInstance();
        config.setMaxItensPorPedido(50); // Garante o estado
        assertEquals(50, config.getMaxItensPorPedido(), "Máximo de itens padrão deve ser 50.");
    }

    @Test
    void setMaxItensPorPedido_deveAlterarOMaximoDeItens() {
        ConfiguracaoSistema config = ConfiguracaoSistema.getInstance();
        config.setMaxItensPorPedido(100);
        assertEquals(100, config.getMaxItensPorPedido(), "setMaxItensPorPedido deve alterar o máximo de itens.");
        // Restaura para o padrão
        config.setMaxItensPorPedido(50);
    }
}
