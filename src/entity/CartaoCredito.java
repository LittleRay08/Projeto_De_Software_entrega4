package entity;

/**
 * ENTIDADE (Entity) - CartaoCredito.
 *
 * Dados do cartão informado pelo assinante.
 */
public class CartaoCredito {

    private String numeroCartao;
    private String nomeTitular;
    private String validade;
    private String codigoSeguranca;

    public CartaoCredito(String numeroCartao, String nomeTitular,
                         String validade, String codigoSeguranca) {
        this.numeroCartao = numeroCartao;
        this.nomeTitular = nomeTitular;
        this.validade = validade;
        this.codigoSeguranca = codigoSeguranca;
    }

    public String getNumeroCartao() {
        return numeroCartao;
    }

    public String getNomeTitular() {
        return nomeTitular;
    }

    public String getValidade() {
        return validade;
    }

    public String getCodigoSeguranca() {
        return codigoSeguranca;
    }

    /**
     * Mostra apenas os 4 últimos dígitos (nunca exibir/gravar o cartão completo).
     */
    public String getNumeroMascarado() {
        if (numeroCartao == null || numeroCartao.length() < 4) {
            return "****";
        }
        return "**** **** **** " + numeroCartao.substring(numeroCartao.length() - 4);
    }
}
