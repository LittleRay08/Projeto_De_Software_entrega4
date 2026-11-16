# 🍱 Minha Refeição — Implementação do Caso de Uso "Assinar Plano de Marmitas"

Implementação em **Java** do cenário de caso de uso **"Assinar Plano de Marmitas"**
da plataforma *Minha Refeição*, correspondente à **Entrega 4** da disciplina de
Projeto de Software.

O código traduz diretamente o **Diagrama de Sequência** e o **Diagrama de Classes
de Projeto**: cada mensagem do diagrama vira uma chamada de método, e as camadas de
**Fronteira (Boundary) → Controle (Control) → Entidade (Entity) / Coleção
(Collection)** estão organizadas em pacotes.

> **Integrante:** Rayane Yumi — RA: 10410892

---

## 📂 Estrutura do projeto

```
minha-refeicao-java/
├── dados/                       # Persistência em arquivos CSV
│   ├── planos.csv               # Planos disponíveis (dados de entrada)
│   ├── cardapio.csv             # Cardápio da semana (dados de entrada)
│   └── assinaturas.csv          # Assinaturas ativadas (gravado pelo sistema)
├── src/
│   ├── Main.java                # Ponto de entrada (monta as camadas)
│   ├── boundary/                # FRONTEIRA (Boundary)
│   │   ├── TelaAssinatura.java          # Interface de console com o Assinante
│   │   └── GatewayOperadoraCartao.java  # Interface com o ator secundário (Operadora)
│   ├── control/                 # CONTROLE (Control)
│   │   ├── AssinaturaController.java     # Orquestra o fluxo principal
│   │   ├── PedidoProcessador.java        # Monta o pedido (GRASP: Criador)
│   │   └── PagamentoProcessador.java     # Processa o pagamento
│   ├── entity/                  # ENTIDADE (Entity) — domínio
│   │   ├── Assinante.java
│   │   ├── Assinatura.java
│   │   ├── PlanoAssinatura.java
│   │   ├── PreferenciaAlimentar.java
│   │   ├── Pedido.java
│   │   ├── ItemPedido.java
│   │   ├── ItemCardapio.java
│   │   ├── EnderecoEntrega.java
│   │   ├── Pagamento.java
│   │   ├── CartaoCredito.java
│   │   └── (enums) StatusAssinatura, StatusPedido, StatusPagamento, CategoriaItem
│   └── collection/              # COLEÇÃO (Collection) — persistência
│       ├── PlanoCollection.java          # Lê planos do CSV
│       ├── CardapioCollection.java       # Lê cardápio do CSV
│       └── AssinaturaCollection.java     # Lê/grava assinaturas no CSV
└── README.md
```

---

## ▶️ Como executar

Pré-requisito: **JDK 8 ou superior** instalado (`java -version` para conferir).

A partir da pasta `minha-refeicao-java/`:

### 1. Compilar

```bash
javac -d out $(find src -name "*.java")
```

> No Windows (PowerShell), use:
> ```powershell
> javac -d out (Get-ChildItem -Recurse src -Filter *.java).FullName
> ```

### 2. Executar

```bash
java -cp out Main
```

> ⚠️ Execute **a partir da pasta `minha-refeicao-java/`**, pois os arquivos CSV são
> referenciados pelo caminho relativo `dados/`.

---

## 🧭 Fluxo do cenário (correspondência com o Diagrama de Sequência)

O programa guia o Assinante pelos **5 estímulos** do caso de uso. Cada passo mostra
a correspondência direta com as mensagens do diagrama de sequência.

| Passo | Estímulo (Boundary) | Mensagem → Controle | Objetos de domínio / Coleção |
|-------|---------------------|---------------------|------------------------------|
| 1 | `informarCelular(numeroCelular)` | `identificarAssinante()` | cria `Assinante`, `enviarCodigoConfirmacao()` |
| 2 | `confirmarCodigo(codigo)` | `validarCodigo()` | `Assinante.validar()` |
| 3 | `selecionarPlano(idPlano)` | `escolherPlano()` | `PlanoCollection.buscarPorId()` |
| 4 | `informarPreferencias(tipos, endereco)` | `criarAssinatura()` | cria `Assinatura`; `PedidoProcessador.gerarPedido()` monta o `Pedido` usando `CardapioCollection` |
| 5 | `informarDadosCartao(dadosCartao)` | `efetuarPagamento()` | `PagamentoProcessador` → `GatewayOperadoraCartao.autorizar()`; `Assinatura.ativar()`; `AssinaturaCollection.adicionar()` |

---

## 🧪 Fluxos exercitados (para o vídeo)

### Fluxo principal (sucesso)
1. Informe um celular → o sistema exibe o **código SMS** no terminal (simulação).
2. Digite exatamente o código exibido.
3. Escolha um plano pelo ID (ex.: `P1`).
4. Informe preferências e endereço.
5. Informe um cartão válido (ex.: `4111222233334444`).
   → Assinatura **ATIVADA**, protocolo gerado e **gravado em `dados/assinaturas.csv`**.

### Fluxo alternativo A — código inválido
- No passo 2, digite um código errado → o sistema recusa e encerra o fluxo.

### Fluxo alternativo B — pagamento negado
- No passo 5, use um cartão terminado em `0000` (ex.: `4111222233330000`).
  → A operadora **nega** a transação e a assinatura **não** é ativada.

---

## 💾 Persistência (CSV)

- **Entrada:** `dados/planos.csv` e `dados/cardapio.csv` são lidos na inicialização.
- **Saída:** cada assinatura ativada é **acrescentada** em `dados/assinaturas.csv`.
- O número do cartão **não** é gravado (apenas os dados da assinatura), seguindo boa
  prática de segurança.

Formato de `assinaturas.csv`:
```
protocolo;celular;planoId;planoNome;valorTotal;status;preferencias;endereco;previsaoEntrega
```

---

## 🏛️ Padrões de projeto (GRASP) aplicados

| Elemento | Padrão | Papel |
|----------|--------|-------|
| `AssinaturaController` / `PedidoProcessador` / `PagamentoProcessador` | Controlador / Invenção Pura | Orquestram sem conter a lógica pesada |
| `PedidoProcessador.gerarPedido()` | Criador | Reúne os dados e cria `Pedido` + `ItemPedido` |
| `Pedido` (`adicionarItem`, `calcularValorTotal`) | Especialista da Informação | Detém os itens, logo compõe e calcula o total |
| `Assinante.validar()` | Especialista da Informação | Detém o código, logo valida a si mesmo |
| `PlanoCollection` / `CardapioCollection` / `AssinaturaCollection` | Baixo Acoplamento | Isolam o acesso aos dados (substituem o "Banco de Dados") |
| `GatewayOperadoraCartao` | Indireção / Baixo Acoplamento | Isola o sistema do ator secundário |
