package com.example.sistemapedidos.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Classe Pedido.
 * Representa um pedido feito por um Cliente, contendo uma lista de ItensPedido,
 * data, status e valor total.
 * A construção desta classe é idealmente feita através do PedidoBuilder.
 */
public class Pedido {
    private String id;
    private Cliente cliente;
    private List<ItemPedido> itens;
    private LocalDateTime data;
    private double total;
    private String status; // Ex: PENDENTE, PAGO, ENVIADO, ENTREGUE, CANCELADO

    // Construtor package-private para ser usado principalmente pelo PedidoBuilder
    Pedido() {
        this.itens = new ArrayList<>();
        // ID, data e status podem ser inicializados pelo Builder
    }

    // Getters
    public String getId() {
        return id;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public List<ItemPedido> getItens() {
        // Retorna uma cópia para proteger a lista interna de modificações externas diretas
        return new ArrayList<>(itens);
    }

    public LocalDateTime getData() {
        return data;
    }

    public double getTotal() {
        return total;
    }

    public String getStatus() {
        return status;
    }

    // Setters - Usados pelo PedidoBuilder ou para atualizações de status, etc.
    // É importante ter cuidado ao expor setters para atributos que afetam o estado
    // de forma complexa, como a lista de itens ou o total.

    void setId(String id) { // package-private, para ser usado pelo Builder
        this.id = id;
    }

    void setCliente(Cliente cliente) { // package-private
        this.cliente = cliente;
    }

    void setData(LocalDateTime data) { // package-private
        this.data = data;
    }

    public void setStatus(String status) { // public, pois o status pode mudar após a criação
        if (status == null || status.trim().isEmpty()) {
            throw new IllegalArgumentException("Status não pode ser vazio.");
        }
        this.status = status;
    }

    // Método para adicionar item, usado pelo Builder
    void adicionarItem(ItemPedido item) { // package-private
        if (item != null) {
            this.itens.add(item);
            // O total será recalculado pelo builder ou por um método específico
        }
    }

    // Método para calcular o total do pedido
    // Pode ser chamado pelo Builder ao construir ou quando itens são modificados
    void calcularTotal() { // package-private
        this.total = 0;
        for (ItemPedido item : this.itens) {
            this.total += item.getSubtotal();
        }
    }

    public void exibirDetalhes() {
        System.out.println("--- Detalhes do Pedido ---");
        System.out.println("ID do Pedido: " + id);
        System.out.println("Cliente: " + (cliente != null ? cliente.getNome() : "N/A"));
        System.out.println("Data: " + (data != null ? data.toString() : "N/A"));
        System.out.println("Status: " + status);
        System.out.println("Itens:");
        for (ItemPedido item : itens) {
            item.getProduto().exibirDetalhes(); // Mostra detalhes do produto
            System.out.println("  -> Quantidade: " + item.getQuantidade() + ", Subtotal Item: R$" + String.format("%.2f", item.getSubtotal()));
        }
        System.out.println("TOTAL DO PEDIDO: R$" + String.format("%.2f", total));
        System.out.println("--------------------------");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pedido pedido = (Pedido) o;
        return Objects.equals(id, pedido.id); // Pedidos são iguais se o ID for igual
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Pedido{" +
               "id='" + id + '\'' +
               ", cliente=" + (cliente != null ? cliente.getNome() : "null") +
               ", itens=" + itens.size() +
               ", data=" + data +
               ", total=" + String.format("%.2f", total) +
               ", status='" + status + '\'' +
               '}';
    }
}