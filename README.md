# Sistema de Pedidos Online (E-commerce Simplificado) - Entrega 1

Este projeto tem como objetivo aplicar, de forma incremental, os principais padrões de projeto de software (criacionais, estruturais e comportamentais) em um sistema de e-commerce simplificado. Esta é a primeira entrega, focada nos **Padrões Criacionais**.

## Estrutura de Pastas do Projeto (Maven)

O projeto segue a estrutura padrão do Maven:

sistema-pedidos-online/├── pom.xml├── README.md└── src/├── main/│   └── java/│       └── com/│           └── example/│               └── sistemapedidos/│                   ├── Main.java│                   ├── model/│                   │   ├── produto/│                   │   │   ├── Produto.java│                   │   │   ├── ProdutoFisico.java│                   │   │   └── ProdutoDigital.java│                   │   ├── Cliente.java│                   │   ├── Pedido.java│                   │   └── ItemPedido.java│                   ├── factory/│                   │   └── ProdutoFactory.java│                   ├── builder/│                   │   └── PedidoBuilder.java│                   └── singleton/│                       └── ConfiguracaoSistema.java└── test/└── java/└── com/└── example/└── sistemapedidos/├── factory/│   └── ProdutoFactoryTest.java├── builder/│   └── PedidoBuilderTest.java└── singleton/└── ConfiguracaoSistemaTest.java
## Padrões Criacionais Utilizados

Nesta primeira entrega, foram implementados os seguintes padrões criacionais:

1.  **Singleton**
2.  **Factory Method (abordagem simplificada)**
3.  **Builder**

---

### 1. Singleton

* **O que é?**
    O padrão Singleton garante que uma classe tenha apenas uma instância e fornece um ponto de acesso global a essa instância. É útil para gerenciar recursos compartilhados ou configurações globais.

* **Por que foi escolhido?**
    Para gerenciar configurações globais do sistema, como a moeda padrão ou URL de um serviço externo. A classe `ConfiguracaoSistema` utiliza este padrão para garantir que haja apenas um objeto de configuração em toda a aplicação.

* **Como foi implementado?**
    A classe `ConfiguracaoSistema` possui:
    1.  Um construtor privado para impedir a instanciação direta de fora da classe.
    2.  Um atributo estático privado para armazenar a única instância (`private static ConfiguracaoSistema instancia;`).
    3.  Um método público estático `getInstance()` que retorna a única instância. Se a instância ainda não existir, ela é criada dentro deste método (lazy initialization).

    *Trecho de Código (`ConfiguracaoSistema.java`):*
    ```java
    // com.example.sistemapedidos.singleton.ConfiguracaoSistema

    private static ConfiguracaoSistema instancia;
    private String moedaPadrao;

    private ConfiguracaoSistema() {
        // Construtor privado para evitar instanciação externa
        this.moedaPadrao = "BRL"; // Valor padrão
    }

    public static ConfiguracaoSistema getInstance() {
        if (instancia == null) {
            instancia = new ConfiguracaoSistema();
        }
        return instancia;
    }
    ```

---

### 2. Factory Method (Abordagem Simplificada: Simple Factory)

* **O que é?**
    O padrão Factory Method define uma interface para criar um objeto, mas deixa as subclasses decidirem qual classe concreta instanciar. Ele permite que uma classe adie a instanciação para subclasses.
    Neste projeto, utilizamos uma abordagem mais simples, frequentemente chamada de **Simple Factory**, que não é estritamente o Factory Method, mas serve como um bom introdutor ao conceito de fábricas. Uma única classe fábrica decide qual produto concreto criar com base em um parâmetro.

* **Por que foi escolhido?**
    Para desacoplar o código cliente da criação de diferentes tipos de `Produto` (como `ProdutoFisico` e `ProdutoDigital`). Com a `ProdutoFactory`, o cliente não precisa saber os detalhes de como cada tipo de produto é instanciado. Isso facilita a adição de novos tipos de produtos no futuro sem modificar o código cliente.

* **Como foi implementado?**
    A classe `ProdutoFactory` possui um método estático `criarProduto()`:
    1.  Este método recebe parâmetros como `tipo`, `nome`, `preco` e outros atributos específicos do produto.
    2.  Com base no `tipo` informado (ex: "FISICO", "DIGITAL"), ele instancia e retorna o objeto `Produto` correspondente (`ProdutoFisico` ou `ProdutoDigital`).

    *Trecho de Código (`ProdutoFactory.java`):*
    ```java
    // com.example.sistemapedidos.factory.ProdutoFactory

    public static Produto criarProduto(String tipo, String nome, double preco, Object... args) {
        if ("FISICO".equalsIgnoreCase(tipo)) {
            double peso = (args.length > 0 && args[0] instanceof Double) ? (Double) args[0] : 0.0;
            return new ProdutoFisico(nome, preco, peso);
        } else if ("DIGITAL".equalsIgnoreCase(tipo)) {
            String urlDownload = (args.length > 0 && args[0] instanceof String) ? (String) args[0] : "";
            return new ProdutoDigital(nome, preco, urlDownload);
        }
        throw new IllegalArgumentException("Tipo de produto desconhecido: " + tipo);
    }
    ```
    A interface `Produto` e suas classes concretas `ProdutoFisico` e `ProdutoDigital` definem os diferentes tipos de produtos que podem ser criados.

---

### 3. Builder

* **O que é?**
    O padrão Builder separa a construção de um objeto complexo de sua representação, permitindo que o mesmo processo de construção possa criar diferentes representações. É útil quando um objeto pode ter muitos parâmetros de configuração, alguns obrigatórios e outros opcionais.

* **Por que foi escolhido?**
    Para construir o objeto `Pedido`. Um pedido pode ser complexo, contendo um cliente, uma lista de itens (cada um com produto e quantidade), data, status, etc. O Builder torna a criação de um `Pedido` mais legível e flexível, especialmente se alguns atributos forem opcionais ou se a ordem de configuração não importar.

* **Como foi implementado?**
    A classe `PedidoBuilder`:
    1.  Possui métodos para configurar cada parte do `Pedido` (ex: `comCliente()`, `adicionarItem()`, `comData()`).
    2.  Cada um desses métodos retorna a própria instância do `PedidoBuilder` (fluent interface), permitindo chamadas encadeadas.
    3.  Um método `construir()` (ou `build()`) finaliza o processo e retorna o objeto `Pedido` construído.

    *Trecho de Código (`PedidoBuilder.java`):*
    ```java
    // com.example.sistemapedidos.builder.PedidoBuilder

    public class PedidoBuilder {
        private Pedido pedidoEmConstrucao;

        public PedidoBuilder() {
            this.pedidoEmConstrucao = new Pedido();
            // Define um ID único para o pedido, por exemplo
            this.pedidoEmConstrucao.setId(java.util.UUID.randomUUID().toString());
            this.pedidoEmConstrucao.setData(java.time.LocalDateTime.now()); // Data atual por padrão
            this.pedidoEmConstrucao.setStatus("PENDENTE"); // Status inicial
        }

        public PedidoBuilder comCliente(Cliente cliente) {
            this.pedidoEmConstrucao.setCliente(cliente);
            return this;
        }

        public PedidoBuilder adicionarItem(Produto produto, int quantidade) {
            if (produto == null || quantidade <= 0) {
                throw new IllegalArgumentException("Produto inválido ou quantidade deve ser positiva.");
            }
            ItemPedido item = new ItemPedido(produto, quantidade, produto.getPreco());
            this.pedidoEmConstrucao.adicionarItem(item);
            return this;
        }
        // ... outros métodos para configurar o pedido

        public Pedido construir() {
            if (this.pedidoEmConstrucao.getCliente() == null) {
                throw new IllegalStateException("Cliente é obrigatório para construir o pedido.");
            }
            if (this.pedidoEmConstrucao.getItens().isEmpty()) {
                throw new IllegalStateException("Pedido deve ter pelo menos um item.");
            }
            // Calcula o total do pedido antes de retornar
            this.pedidoEmConstrucao.calcularTotal();
            return this.pedidoEmConstrucao;
        }
    }
    ```

## Como Executar

1.  Certifique-se de ter o Java JDK (versão 11 ou superior) e o Maven instalados.
2.  Clone o repositório.
3.  Navegue até a pasta raiz do projeto (`sistema-pedidos-online`).
4.  Compile o projeto: `mvn compile`
5.  Execute os testes: `mvn test`
6.  Para executar a classe `Main` de demonstração (após compilar):
    * Via Maven: `mvn exec:java -Dexec.mainClass="com.example.sistemapedidos.Main"`
    * Ou execute diretamente pela sua IDE.

## Próximos Passos (Outras Entregas)

* **2ª Entrega:** Implementação de Padrões Estruturais (Adapter, Decorator, Composite, etc.) para integrar novos componentes e funcionalidades.
* **3ª Entrega:** Implementação de Padrões Comportamentais (Strategy, Observer, Command, etc.) para gerenciar o fluxo e a lógica de negócios da aplicação.
