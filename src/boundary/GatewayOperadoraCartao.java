package boundary;

import entity.CartaoCredito;

import java.math.BigDecimal;

/**
 * FRONTEIRA (Boundary) - GatewayOperadoraCartao.
 *
 * Interface com o ator secundário "Operadora de Cartão".
 * Simula a autorização da transação (retorna um código de autorização).
 */
public class GatewayOperadoraCartao {

    /**
     * Mensagem do sequência: autorizar(cartao, valor).
     * Retorna o código de autorização ou null se negado.
     */
    public String autorizar(CartaoCredito cartao, BigDecimal valor) {
        // Regra simulada: nega cartão cujo número termina em "0000".
        if (cartao.getNumeroCartao().endsWith("0000")) {
            return null;
        }
        return "AUT" + System.currentTimeMillis();
    }
}
