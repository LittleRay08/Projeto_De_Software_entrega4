package boundary;

import control.AssinaturaController;
import entity.Assinante;
import entity.Assinatura;
import entity.CartaoCredito;
import entity.EnderecoEntrega;
import entity.ItemPedido;
import entity.PlanoAssinatura;
import entity.PreferenciaAlimentar;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

/**
 * FRONTEIRA (Boundary) - TelaAssinatura.
 *
 * Interface de console. Captura os estímulos do Assinante e exibe as respostas
 * do sistema, delegando toda a lógica ao AssinaturaController.
 *
 * Cada passo abaixo corresponde a um estímulo do diagrama de sequência.
 */
public class TelaAssinatura {

    private final AssinaturaController controller;
    private final Scanner scanner;

    public TelaAssinatura(AssinaturaController controller) {
        this.controller = controller;
        this.scanner = new Scanner(System.in);
    }

    public void iniciar() {
        cabecalho();

        // ---- Estímulo 1: informarCelular ----
        Assinante assinante = etapaIdentificacao();
        if (assinante == null) {
            return;
        }

        // ---- Estímulo 2: confirmarCodigo ----
        if (!etapaConfirmacaoCodigo(assinante)) {
            return;
        }

        // ---- Estímulo 3: selecionarPlano ----
        PlanoAssinatura plano = etapaSelecaoPlano();
        if (plano == null) {
            return;
        }

        // ---- Estímulo 4: informarPreferencias (+ montagem do pedido) ----
        Assinatura assinatura = etapaPreferenciasEEndereco(assinante, plano);

        exibirResumoPagamento(assinatura);

        // ---- Estímulo 5: informarDadosCartao ----
        etapaPagamento(assinatura);

        scanner.close();
    }

    private void cabecalho() {
        System.out.println("============================================");
        System.out.println("   MINHA REFEICAO - Assinar Plano de Marmitas");
        System.out.println("============================================\n");
    }

    // Estímulo 1
    private Assinante etapaIdentificacao() {
        System.out.print("[1] Informe seu numero de celular (DDD + numero): ");
        String celular = scanner.nextLine().trim();

        Assinante assinante = controller.identificarAssinante(celular);

        // Simulação do envio de SMS: o código é exibido no terminal.
        System.out.println(">> SMS enviado! Seu codigo de confirmacao e: "
                + assinante.getCodigoConfirmacao());
        return assinante;
    }

    // Estímulo 2
    private boolean etapaConfirmacaoCodigo(Assinante assinante) {
        System.out.print("[2] Digite o codigo recebido por SMS: ");
        String codigo = scanner.nextLine().trim();

        if (!controller.validarCodigo(assinante, codigo)) {
            System.out.println(">> Codigo invalido. Encerrando o fluxo.");
            return false;
        }
        System.out.println(">> Codigo confirmado! Acesso liberado.\n");
        return true;
    }

    // Estímulo 3
    private PlanoAssinatura etapaSelecaoPlano() {
        System.out.println("[3] Planos disponiveis:");
        List<PlanoAssinatura> planos = controller.listarPlanos();
        for (PlanoAssinatura p : planos) {
            System.out.println("    " + p);
        }

        System.out.print("    Informe o ID do plano desejado: ");
        String idPlano = scanner.nextLine().trim();

        PlanoAssinatura plano = controller.escolherPlano(idPlano);
        if (plano == null) {
            System.out.println(">> Plano nao encontrado. Encerrando o fluxo.");
            return null;
        }
        System.out.println(">> Plano selecionado: " + plano.getNome() + "\n");
        return plano;
    }

    // Estímulo 4
    private Assinatura etapaPreferenciasEEndereco(Assinante assinante, PlanoAssinatura plano) {
        System.out.print("[4] Informe suas preferencias alimentares (separadas por virgula): ");
        String prefsLinha = scanner.nextLine().trim();
        List<String> tipos = new ArrayList<>();
        if (!prefsLinha.isEmpty()) {
            for (String t : prefsLinha.split(",")) {
                tipos.add(t.trim());
            }
        }

        System.out.println("    Agora informe o endereco de entrega:");
        System.out.print("    Logradouro: ");
        String logradouro = scanner.nextLine().trim();
        System.out.print("    Numero: ");
        String numero = scanner.nextLine().trim();
        System.out.print("    Bairro: ");
        String bairro = scanner.nextLine().trim();
        System.out.print("    Cidade: ");
        String cidade = scanner.nextLine().trim();
        System.out.print("    UF: ");
        String uf = scanner.nextLine().trim();
        System.out.print("    CEP: ");
        String cep = scanner.nextLine().trim();

        EnderecoEntrega endereco =
                new EnderecoEntrega(logradouro, numero, bairro, cidade, uf, cep);

        Assinatura assinatura =
                controller.criarAssinatura(assinante, plano, tipos, endereco);

        System.out.println(">> Assinatura criada e pedido montado com sucesso.\n");
        return assinatura;
    }

    private void exibirResumoPagamento(Assinatura assinatura) {
        System.out.println("----- RESUMO DO PEDIDO -----");
        System.out.println("Plano: " + assinatura.getPlano().getNome());
        System.out.println("Preferencias: " + listaPrefs(assinatura.getPreferencias()));
        System.out.println("Endereco: " + assinatura.getEndereco());
        System.out.println("Marmitas selecionadas:");
        for (ItemPedido item : assinatura.getPedido().getItens()) {
            System.out.println("   - " + item);
        }
        System.out.printf("Valor total: R$ %.2f%n",
                assinatura.getPedido().getValorTotal());
        System.out.println("----------------------------\n");
    }

    private String listaPrefs(List<PreferenciaAlimentar> prefs) {
        if (prefs.isEmpty()) {
            return "(nenhuma)";
        }
        List<String> nomes = new ArrayList<>();
        for (PreferenciaAlimentar p : prefs) {
            nomes.add(p.getTipo());
        }
        return String.join(", ", nomes);
    }

    // Estímulo 5
    private void etapaPagamento(Assinatura assinatura) {
        System.out.println("[5] Dados do cartao de credito:");
        System.out.print("    Numero do cartao: ");
        String numeroCartao = scanner.nextLine().trim();
        System.out.print("    Nome do titular: ");
        String titular = scanner.nextLine().trim();
        System.out.print("    Validade (MM/AA): ");
        String validade = scanner.nextLine().trim();
        System.out.print("    CVV: ");
        String cvv = scanner.nextLine().trim();

        CartaoCredito cartao =
                new CartaoCredito(numeroCartao, titular, validade, cvv);

        System.out.println(">> Autorizando pagamento junto a operadora...");
        String protocolo = controller.efetuarPagamento(assinatura, cartao);

        if (protocolo == null) {
            System.out.println(">> PAGAMENTO NEGADO pela operadora. Assinatura nao ativada.");
            return;
        }

        exibirProtocoloEConfirmacao(assinatura, protocolo);
    }

    private void exibirProtocoloEConfirmacao(Assinatura assinatura, String protocolo) {
        System.out.println("\n============================================");
        System.out.println("   ASSINATURA ATIVADA COM SUCESSO!");
        System.out.println("============================================");
        System.out.println("Protocolo: " + protocolo);
        System.out.println("Status: " + assinatura.getStatus());
        System.out.println("Previsao da 1a entrega: "
                + assinatura.getPrevisaoPrimeiraEntrega());
        System.out.println("Pagamento: " + assinatura.getPagamento().getStatusAutorizacao()
                + " (cartao " + assinatura.getPagamento().getCartao().getNumeroMascarado() + ")");
        System.out.println("Dados salvos em: dados/assinaturas.csv");
        System.out.println("============================================\n");
    }
}
