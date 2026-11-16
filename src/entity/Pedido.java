package entity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * ENTIDADE (Entity) - Pedido.
 *
 * GRASP: Especialista da Informação. O pedido detém os itens, logo é quem
 * compõe (adicionarItem) e calcula o próprio total (calcularValorTotal).
 */
public class Pedido {

    private LocalDate dataPedido;
    private StatusPedido status;
    private BigDecimal valorTotal;
    private List<ItemPedido> itens;

    public Pedido() {
        this.dataPedido = LocalDate.now();
        this.status = StatusPedido.EM_ANDAMENTO;
        this.valorTotal = BigDecimal.ZERO;
        this.itens = new ArrayList<>();
    }

    /**
     * Mensagem do sequência: adicionarItem(item).
     */
    public void adicionarItem(ItemPedido item) {
        this.itens.add(item);
    }

    /**
     * Mensagem do sequência: calcularValorTotal().
     * O valor total do pedido é derivado do valor do plano contratado.
     */
    public BigDecimal calcularValorTotal(BigDecimal valorPlano) {
        this.valorTotal = valorPlano;
        return this.valorTotal;
    }

    /**
     * Mensagem do sequência: aprovar().
     */
    public void aprovar() {
        this.status = StatusPedido.APROVADO;
    }

    public List<ItemPedido> getItens() {
        return itens;
    }

    public int getQuantidadeItens() {
        return itens.size();
    }

    public LocalDate getDataPedido() {
        return dataPedido;
    }

    public StatusPedido getStatus() {
        return status;
    }

    public BigDecimal getValorTotal() {
        return valorTotal;
    }
}
