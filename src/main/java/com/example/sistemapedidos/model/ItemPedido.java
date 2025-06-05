package com.example.sistemapedidos.model;

import com.example.sistemapedidos.model.produto.Produto;
import java.util.Objects;

/**
 * Classe ItemPedido.
 * Representa um item dentro de um Pedido, contendo um Produto,
 * a quantidade e o preço unitário no momento da compra.
 */
public class ItemPedido {
    private Produto produto;
    private int quantidade;
    private double precoUnitario; // Preço do produto no momento da inclusão no pedido

    public ItemPedido(Produto produto, int quantidade, double precoUnitario) {
        if (produto == null) {
            throw new IllegalArgumentException("Produto não pode ser nulo.");
        }
        if (quantidade <= 0) {
            throw new IllegalArgumentException("Quantidade deve ser positiva.");
        }
        if (precoUnitario <= 0) {
            throw new IllegalArgumentException("Preço unitário deve ser positivo.");
        }
        this.produto = produto;
        this.quantidade = quantidade;
        this.precoUnitario = precoUnitario;
    }

    public Produto getProduto() {
        return produto;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public double getPrecoUnitario() {
        return precoUnitario;
    }

    public double getSubtotal() {
        return quantidade * precoUnitario;
    }

    // Métodos para alterar quantidade, se necessário (ex: no carrinho antes de fechar pedido)
    public void setQuantidade(int quantidade) {
        if (quantidade <= 0) {
            throw new IllegalArgumentException("Quantidade deve ser positiva.");
        }
        this.quantidade = quantidade;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ItemPedido that = (ItemPedido) o;
        // Um item é igual a outro se for o mesmo produto (poderia ser mais complexo, ex: ID do item)
        return Objects.equals(produto, that.produto);
    }

    @Override
    public int hashCode() {
        return Objects.hash(produto);
    }

    @Override
    public String toString() {
        return "ItemPedido{" +
               "produto=" + produto.getNome() +
               ", quantidade=" + quantidade +
               ", precoUnitario=" + String.format("%.2f", precoUnitario) +
               ", subtotal=" + String.format("%.2f", getSubtotal()) +
               '}';
    }
}