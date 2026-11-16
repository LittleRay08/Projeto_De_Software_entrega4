package entity;

/**
 * ENTIDADE (Entity) - Assinante.
 *
 * Corresponde ao objeto "Assinante" do diagrama de sequência.
 * Responsável por manter os dados de identificação e por VALIDAR
 * o próprio código de confirmação (Especialista da Informação - GRASP).
 */
public class Assinante {

    private String numeroCelular;
    private String codigoConfirmacao;
    private String nome;

    public Assinante(String numeroCelular) {
        this.numeroCelular = numeroCelular;
    }

    /**
     * Mensagem do sequência: enviarCodigoConfirmacao().
     * Gera um código de 4 dígitos (simulação do envio de SMS).
     */
    public void enviarCodigoConfirmacao() {
        int codigo = 1000 + (int) (Math.random() * 9000);
        this.codigoConfirmacao = String.valueOf(codigo);
    }

    /**
     * Mensagem do sequência: validar(codigoConfirmacao).
     * O assinante detém o código, logo valida a si mesmo.
     */
    public boolean validar(String codigoInformado) {
        return this.codigoConfirmacao != null
                && this.codigoConfirmacao.equals(codigoInformado);
    }

    public String getNumeroCelular() {
        return numeroCelular;
    }

    public String getCodigoConfirmacao() {
        return codigoConfirmacao;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}
