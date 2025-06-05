package com.example.sistemapedidos.model.produto;

/**
 * Interface Produto.
 * Define o contrato básico para todos os tipos de produtos no sistema.
 * Um produto tem um nome e um preço.
 * Também inclui um método para exibir informações do produto.
 */
public interface Produto {
    String getNome();
    double getPreco();
    void exibirDetalhes(); // Método para mostrar informações específicas do produto
    String getTipo(); // Para identificar o tipo de produto
}
