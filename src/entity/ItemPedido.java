package entity;

/**
 * ENTIDADE (Entity) - ItemPedido.
 *
 * Associa um ItemCardapio a uma quantidade dentro de um Pedido.
 * Criada no loop de montagem do pedido (diagrama de sequência).
 */
public class ItemPedido {

    private ItemCardapio itemCardapio;
    private int quantidade;
    private CategoriaItem categoria;

    public ItemPedido(ItemCardapio itemCardapio, int quantidade) {
        this.itemCardapio = itemCardapio;
        this.quantidade = quantidade;
        this.categoria = itemCardapio.getCategoria();
    }

    public ItemCardapio getItemCardapio() {
        return itemCardapio;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public CategoriaItem getCategoria() {
        return categoria;
    }

    @Override
    public String toString() {
        return String.format("%dx %s", quantidade, itemCardapio.getNome());
    }
}
