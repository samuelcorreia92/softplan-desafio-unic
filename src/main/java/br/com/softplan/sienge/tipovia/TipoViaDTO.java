package br.com.softplan.sienge.tipovia;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class TipoViaDTO {

    private Long id;
    private String descricao;
    private BigDecimal custo;
    private Integer kmsRodados;

}
