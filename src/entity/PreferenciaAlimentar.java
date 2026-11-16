package entity;

/**
 * ENTIDADE (Entity) - PreferenciaAlimentar.
 *
 * Cada preferência informada pelo assinante (ex.: "sem lactose").
 * Criada dentro da Assinatura (composição) conforme o loop do sequência.
 */
public class PreferenciaAlimentar {

    private String tipo;

    public PreferenciaAlimentar(String tipo) {
        this.tipo = tipo;
    }

    public String getTipo() {
        return tipo;
    }

    @Override
    public String toString() {
        return tipo;
    }
}
