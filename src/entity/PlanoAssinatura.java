package entity;

import java.math.BigDecimal;

/**
 * ENTIDADE (Entity) - PlanoAssinatura.
 *
 * Representa um plano de marmitas disponível para contratação.
 * Carregado a partir do arquivo CSV pela PlanoCollection.
 */
public class PlanoAssinatura {

    private String id;
    private String nome;
    private int quantidadeRefeicoes;
    private String periodicidade;
    private BigDecimal valor;

    public PlanoAssinatura(String id, String nome, int quantidadeRefeicoes,
                           String periodicidade, BigDecimal valor) {
        this.id = id;
        this.nome = nome;
        this.quantidadeRefeicoes = quantidadeRefeicoes;
        this.periodicidade = periodicidade;
        this.valor = valor;
    }

    public String getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public int getQuantidadeRefeicoes() {
        return quantidadeRefeicoes;
    }

    public String getPeriodicidade() {
        return periodicidade;
    }

    public BigDecimal getValor() {
        return valor;
    }

    @Override
    public String toString() {
        return String.format("[%s] %s - %d refeicoes/%s - R$ %.2f",
                id, nome, quantidadeRefeicoes, periodicidade, valor);
    }
}
