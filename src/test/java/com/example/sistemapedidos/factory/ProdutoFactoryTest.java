package com.example.sistemapedidos.factory;

import com.example.sistemapedidos.model.produto.Produto;
import com.example.sistemapedidos.model.produto.ProdutoFisico;
import com.example.sistemapedidos.model.produto.ProdutoDigital;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ProdutoFactoryTest {

    @Test
    void criarProduto_deveCriarProdutoFisicoCorretamente() {
        Produto produto = ProdutoFactory.criarProduto("FISICO", "Livro Teste", 50.0, 0.5);
        assertNotNull(produto, "Produto não deveria ser nulo.");
        assertTrue(produto instanceof ProdutoFisico, "Produto deveria ser uma instância de ProdutoFisico.");
        assertEquals("Livro Teste", produto.getNome());
        assertEquals(50.0, produto.getPreco());
        assertEquals(0.5, ((ProdutoFisico) produto).getPesoKg());
        assertEquals("FISICO", produto.getTipo());
    }

    @Test
    void criarProduto_deveCriarProdutoDigitalCorretamente() {
        Produto produto = ProdutoFactory.criarProduto("DIGITAL", "Software Teste", 150.0, "http://example.com/download");
        assertNotNull(produto, "Produto não deveria ser nulo.");
        assertTrue(produto instanceof ProdutoDigital, "Produto deveria ser uma instância de ProdutoDigital.");
        assertEquals("Software Teste", produto.getNome());
        assertEquals(150.0, produto.getPreco());
        assertEquals("http://example.com/download", ((ProdutoDigital) produto).getUrlDownload());
        assertEquals("DIGITAL", produto.getTipo());
    }

    @Test
    void criarProduto_deveLancarExcecaoParaTipoDesconhecido() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            ProdutoFactory.criarProduto("INVALIDO", "Produto Ruim", 10.0);
        });
        assertEquals("Tipo de produto desconhecido: INVALIDO", exception.getMessage());
    }

    @Test
    void criarProduto_deveLancarExcecaoParaTipoNulo() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            ProdutoFactory.criarProduto(null, "Produto Sem Tipo", 10.0);
        });
        assertEquals("Tipo de produto não pode ser nulo.", exception.getMessage());
    }

    @Test
    void criarProduto_produtoFisicoComArgumentoInvalidoParaPeso() {
        // A fábrica atualmente imprime um aviso e usa peso padrão 0.0 se o tipo do arg não for Double.
        // Poderíamos alterar para lançar exceção ou testar o comportamento de aviso (mais complexo).
        // Vamos testar o comportamento atual: usa peso padrão.
        Produto produto = ProdutoFactory.criarProduto("FISICO", "Livro com Arg Errado", 60.0, "texto_em_vez_de_double");
        assertTrue(produto instanceof ProdutoFisico);
        assertEquals(0.0, ((ProdutoFisico) produto).getPesoKg(), "Peso deveria ser 0.0 se argumento for inválido.");
    }

     @Test
    void criarProduto_produtoDigitalComArgumentoInvalidoParaUrl() {
        Produto produto = ProdutoFactory.criarProduto("DIGITAL", "Ebook com Arg Errado", 20.0, 12345); // int em vez de String
        assertTrue(produto instanceof ProdutoDigital);
        assertEquals("", ((ProdutoDigital) produto).getUrlDownload(), "URL deveria ser vazia se argumento for inválido.");
    }
}