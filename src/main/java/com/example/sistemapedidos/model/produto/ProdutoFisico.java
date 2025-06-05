package com.example.sistemapedidos.model.produto;

import java.util.Objects;

/**
 * Classe ProdutoFisico.
 * Representa um produto físico que tem um peso.
 * Implementa a interface Produto.
 */
public class ProdutoFisico implements Produto {
    private String nome;
    private double preco;
    private double pesoKg; // Peso do produto em quilogramas

    public ProdutoFisico(String nome, double preco, double pesoKg) {
        if (nome == null || nome.trim().isEmpty()) {
            throw new IllegalArgumentException("Nome do produto não pode ser vazio.");
        }
        if (preco <= 0) {
            throw new IllegalArgumentException("Preço do produto deve ser positivo.");
        }
        if (pesoKg < 0) { // Peso pode ser 0, mas não negativo
            throw new IllegalArgumentException("Peso do produto não pode ser negativo.");
        }
        this.nome = nome;
        this.preco = preco;
        this.pesoKg = pesoKg;
    }

    @Override
    public String getNome() {
        return nome;
    }

    @Override
    public double getPreco() {
        return preco;
    }

    public double getPesoKg() {
        return pesoKg;
    }

    @Override
    public String getTipo() {
        return "FISICO";
    }

    @Override
    public void exibirDetalhes() {
        System.out.println("Produto Físico: " + nome + ", Preço: R$" + String.format("%.2f", preco) + ", Peso: " + pesoKg + "kg");
    }

    // Métodos equals e hashCode para comparações corretas, útil em coleções
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProdutoFisico that = (ProdutoFisico) o;
        return Double.compare(that.preco, preco) == 0 &&
               Double.compare(that.pesoKg, pesoKg) == 0 &&
               Objects.equals(nome, that.nome);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nome, preco, pesoKg);
    }

    @Override
    public String toString() {
        return "ProdutoFisico{" +
               "nome='" + nome + '\'' +
               ", preco=" + preco +
               ", pesoKg=" + pesoKg +
               '}';
    }
}