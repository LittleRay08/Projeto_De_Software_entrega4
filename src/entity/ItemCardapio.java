package entity;

/**
 * ENTIDADE (Entity) - ItemCardapio.
 *
 * Marmita disponível no cardápio da semana.
 * Carregada do arquivo CSV pela CardapioCollection.
 */
public class ItemCardapio {

    private String nome;
    private String descricao;
    private CategoriaItem categoria;
    private boolean disponivel;

    public ItemCardapio(String nome, String descricao,
                        CategoriaItem categoria, boolean disponivel) {
        this.nome = nome;
        this.descricao = descricao;
        this.categoria = categoria;
        this.disponivel = disponivel;
    }

    public String getNome() {
        return nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public CategoriaItem getCategoria() {
        return categoria;
    }

    public boolean isDisponivel() {
        return disponivel;
    }

    @Override
    public String toString() {
        return String.format("%s (%s) - %s", nome, categoria, descricao);
    }
}
