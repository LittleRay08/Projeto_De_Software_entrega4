package control;

import collection.AssinaturaCollection;
import collection.PlanoCollection;
import entity.Assinante;
import entity.Assinatura;
import entity.CartaoCredito;
import entity.EnderecoEntrega;
import entity.PlanoAssinatura;
import entity.PreferenciaAlimentar;

import java.util.List;

/**
 * CONTROLE (Control) - AssinaturaController.
 *
 * GRASP: Controlador / Invenção Pura. Orquestra o fluxo principal do caso de
 * uso "Assinar Plano de Marmitas" e delega para os processadores e coleções,
 * sem conter a lógica de negócio pesada.
 */
public class AssinaturaController {

    private final PlanoCollection planoCollection;
    private final AssinaturaCollection assinaturaCollection;
    private final PedidoProcessador pedidoProcessador;
    private final PagamentoProcessador pagamentoProcessador;

    public AssinaturaController(PlanoCollection planoCollection,
                                AssinaturaCollection assinaturaCollection,
                                PedidoProcessador pedidoProcessador,
                                PagamentoProcessador pagamentoProcessador) {
        this.planoCollection = planoCollection;
        this.assinaturaCollection = assinaturaCollection;
        this.pedidoProcessador = pedidoProcessador;
        this.pagamentoProcessador = pagamentoProcessador;
    }

    /**
     * Estímulo 1: identificarAssinante(numeroCelular).
     * Cria o assinante e dispara o envio do código de confirmação.
     */
    public Assinante identificarAssinante(String numeroCelular) {
        Assinante assinante = new Assinante(numeroCelular);
        assinante.enviarCodigoConfirmacao();
        return assinante;
    }

    /**
     * Estímulo 2: validarCodigo(codigoConfirmacao).
     */
    public boolean validarCodigo(Assinante assinante, String codigo) {
        return assinante.validar(codigo);
    }

    /**
     * Lista os planos disponíveis (resposta ao estímulo 2).
     */
    public List<PlanoAssinatura> listarPlanos() {
        return planoCollection.listarDisponiveis();
    }

    /**
     * Estímulo 3: escolherPlano(idPlano).
     */
    public PlanoAssinatura escolherPlano(String idPlano) {
        return planoCollection.buscarPorId(idPlano);
    }

    /**
     * Estímulo 4: criarAssinatura(assinante, plano, tipos, endereco).
     * Cria a assinatura, registra preferências/endereço e delega a montagem
     * do pedido ao PedidoProcessador.
     */
    public Assinatura criarAssinatura(Assinante assinante, PlanoAssinatura plano,
                                      List<String> tipos, EnderecoEntrega endereco) {
        Assinatura assinatura = new Assinatura(assinante, plano);

        for (String tipo : tipos) {
            assinatura.adicionarPreferencia(new PreferenciaAlimentar(tipo));
        }
        assinatura.setEndereco(endereco);

        // Ctrl -> PedidoProcessador: gerarPedido(assinatura)
        pedidoProcessador.gerarPedido(assinatura, tipos);

        return assinatura;
    }

    /**
     * Estímulo 5: efetuarPagamento(assinatura, dadosCartao).
     * Processa o pagamento, ativa a assinatura, gera o protocolo e persiste.
     * @return o número de protocolo gerado, ou null se o pagamento for negado.
     */
    public String efetuarPagamento(Assinatura assinatura, CartaoCredito cartao) {
        boolean autorizado = pagamentoProcessador.processarPagamento(assinatura, cartao);
        if (!autorizado) {
            return null;
        }

        String protocolo = gerarProtocolo();

        // Ctrl -> Assinatura: ativar(numeroProtocolo)
        assinatura.ativar(protocolo);

        // Ctrl -> AssinaturaCollection: adicionar(assinatura)  [persistência CSV]
        assinaturaCollection.adicionar(assinatura);

        return protocolo;
    }

    private String gerarProtocolo() {
        return "MR" + System.currentTimeMillis();
    }
}
