package control;

import boundary.GatewayOperadoraCartao;
import entity.Assinatura;
import entity.CartaoCredito;
import entity.Pagamento;

import java.math.BigDecimal;

/**
 * CONTROLE (Control) - PagamentoProcessador.
 *
 * Cria o Pagamento, integra com a Operadora de Cartão (ator secundário)
 * via GatewayOperadoraCartao e confirma a autorização.
 */
public class PagamentoProcessador {

    private final GatewayOperadoraCartao gateway;

    public PagamentoProcessador(GatewayOperadoraCartao gateway) {
        this.gateway = gateway;
    }

    /**
     * Mensagem do sequência: processarPagamento(assinatura, dadosCartao).
     * @return true se a transação foi autorizada.
     */
    public boolean processarPagamento(Assinatura assinatura, CartaoCredito cartao) {
        BigDecimal valor = assinatura.getPedido().getValorTotal();

        // PagProc -> Pagamento: <<create>> Pagamento(valor, cartao)
        Pagamento pagamento = new Pagamento(valor, cartao);
        assinatura.setPagamento(pagamento);

        // PagProc -> Operadora: autorizar(cartao, valor)
        String codigoAutorizacao = gateway.autorizar(cartao, valor);

        if (codigoAutorizacao == null) {
            pagamento.negar();
            return false;
        }

        // PagProc -> Pagamento: confirmarAutorizacao(codigoAutorizacao)
        pagamento.confirmarAutorizacao(codigoAutorizacao);
        return true;
    }
}
