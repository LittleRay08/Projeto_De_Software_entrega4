package collection;

import entity.PlanoAssinatura;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * COLLECTION - PlanoCollection.
 *
 * Localiza e lista os planos disponíveis. Substitui o antigo objeto genérico
 * "Banco de Dados": lê os planos do arquivo CSV de dados.
 *
 * Formato do CSV: id;nome;quantidadeRefeicoes;periodicidade;valor
 */
public class PlanoCollection {

    private final String arquivo;
    private final List<PlanoAssinatura> planos;

    public PlanoCollection(String arquivo) {
        this.arquivo = arquivo;
        this.planos = new ArrayList<>();
        carregar();
    }

    private void carregar() {
        try (BufferedReader br = new BufferedReader(new FileReader(arquivo))) {
            String linha;
            boolean cabecalho = true;
            while ((linha = br.readLine()) != null) {
                if (cabecalho) {
                    cabecalho = false;
                    continue;
                }
                if (linha.trim().isEmpty()) {
                    continue;
                }
                String[] campos = linha.split(";");
                PlanoAssinatura plano = new PlanoAssinatura(
                        campos[0].trim(),
                        campos[1].trim(),
                        Integer.parseInt(campos[2].trim()),
                        campos[3].trim(),
                        new BigDecimal(campos[4].trim()));
                planos.add(plano);
            }
        } catch (IOException e) {
            System.err.println("Erro ao carregar planos: " + e.getMessage());
        }
    }

    /**
     * Mensagem do sequência: listarDisponiveis().
     */
    public List<PlanoAssinatura> listarDisponiveis() {
        return new ArrayList<>(planos);
    }

    /**
     * Mensagem do sequência: buscarPorId(idPlano).
     */
    public PlanoAssinatura buscarPorId(String idPlano) {
        for (PlanoAssinatura p : planos) {
            if (p.getId().equalsIgnoreCase(idPlano)) {
                return p;
            }
        }
        return null;
    }
}
