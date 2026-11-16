package control;

import collection.CardapioCollection;
import entity.Assinatura;
import entity.ItemCardapio;
import entity.ItemPedido;
import entity.Pedido;

import java.util.List;

/**
 * CONTROLE (Control) - PedidoProcessador.
 *
 * GRASP: Criador. Reúne os dados (assinatura + cardápio) e cria o Pedido
 * com seus ItemPedido, conforme o loop de montagem do diagrama de sequência.
 */
public class PedidoProcessador {

    private final CardapioCollection cardapioCollection;

    public PedidoProcessador(CardapioCollection cardapioCollection) {
        this.cardapioCollection = cardapioCollection;
    }

    /**
     * Mensagem do sequência: gerarPedido(assinatura).
     */
    public Pedido gerarPedido(Assinatura assinatura, List<String> tipos) {
        Pedido pedido = new Pedido();

        // Ctrl -> CardapioCollection: obterCardapioSemana(plano, tipos)
        List<ItemCardapio> itensCardapio =
                cardapioCollection.obterCardapioSemana(assinatura.getPlano(), tipos);

        int limite = assinatura.getPlano().getQuantidadeRefeicoes();
        int adicionados = 0;

        // loop: para cada item do cardapio -> cria ItemPedido e adiciona
        for (ItemCardapio itemCardapio : itensCardapio) {
            if (adicionados >= limite) {
                break;
            }
            ItemPedido item = new ItemPedido(itemCardapio, 1);
            pedido.adicionarItem(item);
            adicionados++;
        }

        // Pedido calcula o próprio total (Especialista da Informação)
        pedido.calcularValorTotal(assinatura.getPlano().getValor());

        assinatura.setPedido(pedido);
        return pedido;
    }
}
