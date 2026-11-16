import boundary.GatewayOperadoraCartao;
import boundary.TelaAssinatura;
import collection.AssinaturaCollection;
import collection.CardapioCollection;
import collection.PlanoCollection;
import control.AssinaturaController;
import control.PagamentoProcessador;
import control.PedidoProcessador;

/**
 * Ponto de entrada da aplicacao "Minha Refeicao".
 *
 * Monta a cadeia de objetos (injecao de dependencia manual) seguindo as
 * camadas do diagrama de classes de projeto:
 *   Boundary -> Control -> Entity / Collection
 * e inicia o cenario "Assinar Plano de Marmitas".
 */
public class Main {

    public static void main(String[] args) {
        // Caminhos dos arquivos de persistencia (CSV)
        String arquivoPlanos = "dados/planos.csv";
        String arquivoCardapio = "dados/cardapio.csv";
        String arquivoAssinaturas = "dados/assinaturas.csv";

        // Collections (persistencia)
        PlanoCollection planoCollection = new PlanoCollection(arquivoPlanos);
        CardapioCollection cardapioCollection = new CardapioCollection(arquivoCardapio);
        AssinaturaCollection assinaturaCollection = new AssinaturaCollection(arquivoAssinaturas);

        // Boundary do ator secundario
        GatewayOperadoraCartao gateway = new GatewayOperadoraCartao();

        // Controles / processadores
        PedidoProcessador pedidoProcessador = new PedidoProcessador(cardapioCollection);
        PagamentoProcessador pagamentoProcessador = new PagamentoProcessador(gateway);
        AssinaturaController controller = new AssinaturaController(
                planoCollection, assinaturaCollection,
                pedidoProcessador, pagamentoProcessador);

        // Boundary principal e inicio do fluxo
        TelaAssinatura tela = new TelaAssinatura(controller);
        tela.iniciar();
    }
}
