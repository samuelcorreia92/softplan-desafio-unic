
import java.util.Iterator;
import java.util.List;

public class GeradorObservacaoAlterado {

    // Textos pré-definidos
    private static final String TEXTO_UMA_NOTA = "Fatura da nota fiscal de simples remessa: %s.";
    private static final String TEXTO_MULTIPLAS_NOTAS = "Fatura das notas fiscais de simples remessa: %s.";

    /**
     * Gera observações, com texto pre-definido, incluindo os números, das notas fiscais, recebidos no parâmetro
     */
    public String geraObservacao(List<Integer> lista) {
        if (lista == null) {
            throw new IllegalArgumentException("O parâmetro não pode ser nulo");
        }
        return lista.isEmpty() ? "" : getTextoFormatado(lista);
    }

    private String getTextoFormatado(List<Integer> lista) {
        return String.format(lista.size() == 1 ? TEXTO_UMA_NOTA : TEXTO_MULTIPLAS_NOTAS, formataCodigos(lista));
    }

    private String formataCodigos(List<Integer> lista) {
        StringBuilder codigosFormatados = new StringBuilder();
        for (Iterator<Integer> iterator = lista.iterator(); iterator.hasNext(); ) {
            Integer codigo = iterator.next();
            if (codigosFormatados.length() > 0) {
                if (iterator.hasNext()) {
                    codigosFormatados.append(", ");
                } else {
                    codigosFormatados.append(" e ");
                }
            }
            codigosFormatados.append(codigo);
        }
        return codigosFormatados.toString();
    }
}