package com.example.sistemapedidos.model.produto;

import java.util.Objects;

/**
 * Classe ProdutoDigital.
 * Representa um produto digital que tem uma URL para download.
 * Implementa a interface Produto.
 */
public class ProdutoDigital implements Produto {
    private String nome;
    private double preco;
    private String urlDownload; // URL para baixar o produto digital

    public ProdutoDigital(String nome, double preco, String urlDownload) {
        if (nome == null || nome.trim().isEmpty()) {
            throw new IllegalArgumentException("Nome do produto não pode ser vazio.");
        }
        if (preco <= 0) {
            throw new IllegalArgumentException("Preço do produto deve ser positivo.");
        }
        this.nome = nome;
        this.preco = preco;
        this.urlDownload = (urlDownload == null) ? "" : urlDownload; // Garante que não seja nulo
    }

    @Override
    public String getNome() {
        return nome;
    }

    @Override
    public double getPreco() {
        return preco;
    }

    public String getUrlDownload() {
        return urlDownload;
    }

    @Override
    public String getTipo() {
        return "DIGITAL";
    }

    @Override
    public void exibirDetalhes() {
        System.out.println("Produto Digital: " + nome + ", Preço: R$" + String.format("%.2f", preco) + ", URL: " + (urlDownload.isEmpty() ? "N/A" : urlDownload));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProdutoDigital that = (ProdutoDigital) o;
        return Double.compare(that.preco, preco) == 0 &&
               Objects.equals(nome, that.nome) &&
               Objects.equals(urlDownload, that.urlDownload);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nome, preco, urlDownload);
    }

    @Override
    public String toString() {
        return "ProdutoDigital{" +
               "nome='" + nome + '\'' +
               ", preco=" + preco +
               ", urlDownload='" + urlDownload + '\'' +
               '}';
    }
}