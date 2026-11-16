package entity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * ENTIDADE (Entity) - Assinatura.
 *
 * Agrega o plano contratado, as preferências, o pedido, o endereço e o
 * pagamento. GRASP: Criador (cria o Pedido via gerarPedido).
 */
public class Assinatura {

    private LocalDate dataContratacao;
    private StatusAssinatura status;
    private String numeroProtocolo;
    private LocalDate previsaoPrimeiraEntrega;

    private Assinante assinante;
    private PlanoAssinatura plano;
    private EnderecoEntrega endereco;
    private List<PreferenciaAlimentar> preferencias;
    private Pedido pedido;
    private Pagamento pagamento;

    public Assinatura(Assinante assinante, PlanoAssinatura plano) {
        this.assinante = assinante;
        this.plano = plano;
        this.dataContratacao = LocalDate.now();
        this.status = StatusAssinatura.AGUARDANDO_PAGAMENTO;
        this.preferencias = new ArrayList<>();
    }

    /**
     * Mensagem do sequência: adicionarPreferencia(pref).
     */
    public void adicionarPreferencia(PreferenciaAlimentar pref) {
        this.preferencias.add(pref);
    }

    /**
     * Mensagem do sequência: ativar(numeroProtocolo).
     * Ativa a assinatura, aprova o pedido e define a previsão de entrega.
     */
    public void ativar(String numeroProtocolo) {
        this.numeroProtocolo = numeroProtocolo;
        this.status = StatusAssinatura.ATIVA;
        this.previsaoPrimeiraEntrega = LocalDate.now().plusDays(3);
        if (this.pedido != null) {
            this.pedido.aprovar();
        }
    }

    public void setEndereco(EnderecoEntrega endereco) {
        this.endereco = endereco;
    }

    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
    }

    public void setPagamento(Pagamento pagamento) {
        this.pagamento = pagamento;
    }

    public Assinante getAssinante() {
        return assinante;
    }

    public PlanoAssinatura getPlano() {
        return plano;
    }

    public EnderecoEntrega getEndereco() {
        return endereco;
    }

    public List<PreferenciaAlimentar> getPreferencias() {
        return preferencias;
    }

    public Pedido getPedido() {
        return pedido;
    }

    public Pagamento getPagamento() {
        return pagamento;
    }

    public StatusAssinatura getStatus() {
        return status;
    }

    public String getNumeroProtocolo() {
        return numeroProtocolo;
    }

    public LocalDate getPrevisaoPrimeiraEntrega() {
        return previsaoPrimeiraEntrega;
    }

    public LocalDate getDataContratacao() {
        return dataContratacao;
    }
}
