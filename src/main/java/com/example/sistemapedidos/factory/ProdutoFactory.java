package com.example.sistemapedidos.factory;

import com.example.sistemapedidos.model.produto.Produto;
import com.example.sistemapedidos.model.produto.ProdutoFisico;
import com.example.sistemapedidos.model.produto.ProdutoDigital;

/**
 * Classe ProdutoFactory - Implementação do Padrão Factory Method (abordagem Simple Factory).
 * Centraliza a lógica de criação de diferentes tipos de objetos Produto.
 * O cliente não precisa saber qual classe concreta de Produto está sendo instanciada.
 *
 * Esta é uma forma simplificada, muitas vezes chamada de "Simple Factory".
 * Um Factory Method mais canônico envolveria uma interface de fábrica e classes
 * de fábrica concretas, ou um método abstrato na superclasse que as subclasses implementam.
 */
public class ProdutoFactory {

    /**
     * Cria um objeto Produto com base no tipo especificado e argumentos.
     *
     * @param tipo        String indicando o tipo de produto ("FISICO" ou "DIGITAL"). Case-insensitive.
     * @param nome        Nome do produto.
     * @param preco       Preço do produto.
     * @param args        Argumentos adicionais específicos do tipo de produto.
     * Para "FISICO": args[0] deve ser o peso (Double).
     * Para "DIGITAL": args[0] deve ser a URL de download (String).
     * @return Uma instância de Produto (ProdutoFisico ou ProdutoDigital).
     * @throws IllegalArgumentException se o tipo for desconhecido ou os argumentos forem inválidos.
     */
    public static Produto criarProduto(String tipo, String nome, double preco, Object... args) {
        if (tipo == null) {
            throw new IllegalArgumentException("Tipo de produto não pode ser nulo.");
        }

        if ("FISICO".equalsIgnoreCase(tipo)) {
            double peso = 0.0; // Valor padrão
            if (args.length > 0 && args[0] instanceof Double) {
                peso = (Double) args[0];
            } else if (args.length > 0) {
                // Se um argumento foi passado mas não é Double, pode ser um erro de uso
                System.err.println("Aviso: Argumento para peso de ProdutoFisico não é Double. Usando peso padrão 0.0.");
            }
            return new ProdutoFisico(nome, preco, peso);
        } else if ("DIGITAL".equalsIgnoreCase(tipo)) {
            String urlDownload = ""; // Valor padrão
            if (args.length > 0 && args[0] instanceof String) {
                urlDownload = (String) args[0];
            } else if (args.length > 0) {
                System.err.println("Aviso: Argumento para URL de ProdutoDigital não é String. Usando URL padrão vazia.");
            }
            return new ProdutoDigital(nome, preco, urlDownload);
        } else {
            throw new IllegalArgumentException("Tipo de produto desconhecido: " + tipo);
        }
    }
}
