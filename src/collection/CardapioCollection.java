package collection;

import entity.CategoriaItem;
import entity.ItemCardapio;
import entity.PlanoAssinatura;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * COLLECTION - CardapioCollection.
 *
 * Fornece os itens do cardápio da semana, lidos de um arquivo CSV.
 *
 * Formato do CSV: nome;descricao;categoria;disponivel
 */
public class CardapioCollection {

    private final String arquivo;
    private final List<ItemCardapio> itens;

    public CardapioCollection(String arquivo) {
        this.arquivo = arquivo;
        this.itens = new ArrayList<>();
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
                ItemCardapio item = new ItemCardapio(
                        campos[0].trim(),
                        campos[1].trim(),
                        CategoriaItem.valueOf(campos[2].trim()),
                        Boolean.parseBoolean(campos[3].trim()));
                itens.add(item);
            }
        } catch (IOException e) {
            System.err.println("Erro ao carregar cardapio: " + e.getMessage());
        }
    }

    /**
     * Mensagem do sequência: obterCardapioSemana(plano, tipos).
     * Retorna os itens disponíveis do cardápio da semana.
     */
    public List<ItemCardapio> obterCardapioSemana(PlanoAssinatura plano, List<String> tipos) {
        List<ItemCardapio> disponiveis = new ArrayList<>();
        for (ItemCardapio item : itens) {
            if (item.isDisponivel()) {
                disponiveis.add(item);
            }
        }
        return disponiveis;
    }

    public List<ItemCardapio> listarPorCategoria(CategoriaItem categoria) {
        List<ItemCardapio> filtrados = new ArrayList<>();
        for (ItemCardapio item : itens) {
            if (item.isDisponivel() && item.getCategoria() == categoria) {
                filtrados.add(item);
            }
        }
        return filtrados;
    }
}
