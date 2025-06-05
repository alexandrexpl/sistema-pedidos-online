package com.example.sistemapedidos.model;

import java.util.Objects;

/**
 * Classe Cliente.
 * Representa um cliente no sistema, com ID, nome e email.
 */
public class Cliente {
    private String id;
    private String nome;
    private String email;

    public Cliente(String id, String nome, String email) {
        if (id == null || id.trim().isEmpty()) {
            throw new IllegalArgumentException("ID do cliente não pode ser vazio.");
        }
        if (nome == null || nome.trim().isEmpty()) {
            throw new IllegalArgumentException("Nome do cliente não pode ser vazio.");
        }
        // Validação simples de email (pode ser mais robusta)
        if (email == null || !email.contains("@")) {
            throw new IllegalArgumentException("Email do cliente inválido.");
        }
        this.id = id;
        this.nome = nome;
        this.email = email;
    }

    public String getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getEmail() {
        return email;
    }

    public void setNome(String nome) {
        if (nome == null || nome.trim().isEmpty()) {
            throw new IllegalArgumentException("Nome do cliente não pode ser vazio.");
        }
        this.nome = nome;
    }

    public void setEmail(String email) {
        if (email == null || !email.contains("@")) {
            throw new IllegalArgumentException("Email do cliente inválido.");
        }
        this.email = email;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cliente cliente = (Cliente) o;
        return Objects.equals(id, cliente.id); // Clientes são iguais se o ID for igual
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Cliente{" +
               "id='" + id + '\'' +
               ", nome='" + nome + '\'' +
               ", email='" + email + '\'' +
               '}';
    }
}