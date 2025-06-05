package com.example.sistemapedidos.builder;

import com.example.sistemapedidos.model.Cliente;
import com.example.sistemapedidos.model.ItemPedido;
import com.example.sistemapedidos.model.Pedido;
import com.example.sistemapedidos.model.produto.Produto;
import com.example.sistemapedidos.singleton.ConfiguracaoSistema; // Exemplo de uso do Singleton

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Classe PedidoBuilder - Implementação do Padrão Builder.
 * Facilita a construção de objetos Pedido complexos, passo a passo.
 * Permite criar um Pedido com diferentes configurações de forma legível e flexível.
 */
public class PedidoBuilder {
    private Pedido pedidoEmConstrucao;
    private int contadorItens; // Para respeitar o limite de itens por pedido, por exemplo

    public PedidoBuilder() {
        this.pedidoEmConstrucao = new Pedido(); // Cria a instância base do Pedido
        // Define valores padrão ou iniciais para o Pedido
        this.pedidoEmConstrucao.setId(UUID.randomUUID().toString().substring(0, 8)); // ID curto
        this.pedidoEmConstrucao.setData(LocalDateTime.now());
        this.pedidoEmConstrucao.setStatus("PENDENTE_INICIAL");
        this.contadorItens = 0;
    }

    /**
     * Define o cliente do pedido.
     * @param cliente O cliente que está fazendo o pedido.
     * @return O próprio PedidoBuilder para encadeamento de métodos (fluent interface).
     */
    public PedidoBuilder comCliente(Cliente cliente) {
        if (cliente == null) {
            throw new IllegalArgumentException("Cliente não pode ser nulo.");
        }
        this.pedidoEmConstrucao.setCliente(cliente);
        return this;
    }

    /**
     * Adiciona um item ao pedido.
     * @param produto O produto a ser adicionado.
     * @param quantidade A quantidade do produto.
     * @return O próprio PedidoBuilder.
     * @throws IllegalArgumentException se produto for nulo ou quantidade não positiva.
     * @throws IllegalStateException se o número máximo de itens for excedido.
     */
    public PedidoBuilder adicionarItem(Produto produto, int quantidade) {
        if (produto == null) {
            throw new IllegalArgumentException("Produto não pode ser nulo ao adicionar item.");
        }
        if (quantidade <= 0) {
            throw new IllegalArgumentException("Quantidade do item deve ser positiva.");
        }

        // Exemplo de uso do Singleton para obter uma configuração
        ConfiguracaoSistema config = ConfiguracaoSistema.getInstance();
        if (contadorItens >= config.getMaxItensPorPedido()) {
            throw new IllegalStateException("Número máximo de itens (" + config.getMaxItensPorPedido() + ") por pedido excedido.");
        }

        ItemPedido item = new ItemPedido(produto, quantidade, produto.getPreco());
        this.pedidoEmConstrucao.adicionarItem(item);
        this.contadorItens++;
        return this;
    }

    /**
     * Define a data do pedido. Se não chamado, usa a data/hora atual.
     * @param data A data e hora do pedido.
     * @return O próprio PedidoBuilder.
     */
    public PedidoBuilder comData(LocalDateTime data) {
        if (data == null) {
            throw new IllegalArgumentException("Data não pode ser nula.");
        }
        this.pedidoEmConstrucao.setData(data);
        return this;
    }

    /**
     * Define o status inicial do pedido. Se não chamado, usa "PENDENTE_INICIAL".
     * @param status O status inicial do pedido.
     * @return O próprio PedidoBuilder.
     */
    public PedidoBuilder comStatusInicial(String status) {
        if (status == null || status.trim().isEmpty()) {
            throw new IllegalArgumentException("Status inicial não pode ser vazio.");
        }
        this.pedidoEmConstrucao.setStatus(status);
        return this;
    }


    /**
     * Constrói e retorna o objeto Pedido finalizado.
     * Realiza validações finais e cálculos (como o total do pedido).
     * @return O objeto Pedido construído.
     * @throws IllegalStateException se dados obrigatórios não foram fornecidos (ex: cliente, itens).
     */
    public Pedido construir() {
        // Validações antes de construir o objeto final
        if (this.pedidoEmConstrucao.getCliente() == null) {
            throw new IllegalStateException("Cliente é obrigatório para construir o pedido.");
        }
        if (this.pedidoEmConstrucao.getItens().isEmpty()) {
            throw new IllegalStateException("O pedido deve ter pelo menos um item.");
        }

        // Calcula o total do pedido
        this.pedidoEmConstrucao.calcularTotal();

        // Define um status final se ainda for o inicial (opcional, depende da lógica de negócio)
        if ("PENDENTE_INICIAL".equals(this.pedidoEmConstrucao.getStatus())) {
            this.pedidoEmConstrucao.setStatus("PENDENTE");
        }

        // Retorna uma cópia ou o objeto em si. Para este exemplo, retornamos o objeto.
        // Em cenários mais complexos, poderia-se retornar uma cópia imutável.
        Pedido pedidoFinalizado = this.pedidoEmConstrucao;
        
        // Opcional: Resetar o builder para uma nova construção, embora geralmente se crie um novo builder.
        // this.pedidoEmConstrucao = new Pedido(); 
        // this.contadorItens = 0;

        return pedidoFinalizado;
    }
}