package br.com.softplan.sienge.tipovia;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
public class TipoViaDTO {

    private Long id;
    private String descricao;
    private BigDecimal custo;
    private boolean ativo;
    private Integer kmsRodados;

    public TipoViaDTO(Long id, Integer kmsRodados) {
        this.id = id;
        this.kmsRodados = kmsRodados;
    }

}
