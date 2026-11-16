package entity;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * ENTIDADE (Entity) - Pagamento.
 *
 * Registra o valor e o status da autorização junto à operadora.
 */
public class Pagamento {

    private BigDecimal valor;
    private LocalDate dataPagamento;
    private StatusPagamento statusAutorizacao;
    private String codigoAutorizacao;
    private CartaoCredito cartao;

    public Pagamento(BigDecimal valor, CartaoCredito cartao) {
        this.valor = valor;
        this.cartao = cartao;
        this.statusAutorizacao = StatusPagamento.PENDENTE;
    }

    /**
     * Mensagem do sequência: confirmarAutorizacao(codigoAutorizacao).
     * Registra a autorização retornada pela operadora.
     */
    public void confirmarAutorizacao(String codigoAutorizacao) {
        this.codigoAutorizacao = codigoAutorizacao;
        this.statusAutorizacao = StatusPagamento.AUTORIZADO;
        this.dataPagamento = LocalDate.now();
    }

    public void negar() {
        this.statusAutorizacao = StatusPagamento.NEGADO;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public LocalDate getDataPagamento() {
        return dataPagamento;
    }

    public StatusPagamento getStatusAutorizacao() {
        return statusAutorizacao;
    }

    public String getCodigoAutorizacao() {
        return codigoAutorizacao;
    }

    public CartaoCredito getCartao() {
        return cartao;
    }
}
