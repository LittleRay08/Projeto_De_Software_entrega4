package collection;

import entity.Assinatura;
import entity.PreferenciaAlimentar;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * COLLECTION - AssinaturaCollection.
 *
 * Persiste e recupera as assinaturas finalizadas em arquivo CSV
 * (meio de persistência exigido na entrega).
 *
 * Formato do CSV:
 * protocolo;celular;planoId;planoNome;valorTotal;status;preferencias;endereco;previsaoEntrega
 */
public class AssinaturaCollection {

    private final String arquivo;

    public AssinaturaCollection(String arquivo) {
        this.arquivo = arquivo;
        criarArquivoSeNecessario();
    }

    private void criarArquivoSeNecessario() {
        File f = new File(arquivo);
        if (!f.exists()) {
            try (PrintWriter pw = new PrintWriter(new FileWriter(arquivo))) {
                pw.println("protocolo;celular;planoId;planoNome;valorTotal;"
                        + "status;preferencias;endereco;previsaoEntrega");
            } catch (IOException e) {
                System.err.println("Erro ao criar arquivo de assinaturas: " + e.getMessage());
            }
        }
    }

    /**
     * Mensagem do sequência: adicionar(assinatura).
     * Grava a assinatura ativada ao final do arquivo CSV.
     */
    public void adicionar(Assinatura a) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(arquivo, true))) {
            String preferencias = a.getPreferencias().stream()
                    .map(PreferenciaAlimentar::getTipo)
                    .collect(Collectors.joining("|"));

            String endereco = a.getEndereco() != null
                    ? a.getEndereco().toString().replace(";", ",")
                    : "";

            pw.printf("%s;%s;%s;%s;%.2f;%s;%s;%s;%s%n",
                    a.getNumeroProtocolo(),
                    a.getAssinante().getNumeroCelular(),
                    a.getPlano().getId(),
                    a.getPlano().getNome(),
                    a.getPedido().getValorTotal(),
                    a.getStatus(),
                    preferencias,
                    endereco,
                    a.getPrevisaoPrimeiraEntrega());
        } catch (IOException e) {
            System.err.println("Erro ao gravar assinatura: " + e.getMessage());
        }
    }

    /**
     * Mensagem do sequência: buscarPorProtocolo(numeroProtocolo).
     * Retorna a linha bruta do CSV (demonstração da leitura da persistência).
     */
    public String buscarPorProtocolo(String numeroProtocolo) {
        try (BufferedReader br = new BufferedReader(new FileReader(arquivo))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                if (linha.startsWith(numeroProtocolo + ";")) {
                    return linha;
                }
            }
        } catch (IOException e) {
            System.err.println("Erro ao ler assinaturas: " + e.getMessage());
        }
        return null;
    }

    public List<String> listarTodas() {
        List<String> linhas = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(arquivo))) {
            String linha;
            boolean cabecalho = true;
            while ((linha = br.readLine()) != null) {
                if (cabecalho) {
                    cabecalho = false;
                    continue;
                }
                if (!linha.trim().isEmpty()) {
                    linhas.add(linha);
                }
            }
        } catch (IOException e) {
            System.err.println("Erro ao ler assinaturas: " + e.getMessage());
        }
        return linhas;
    }
}
