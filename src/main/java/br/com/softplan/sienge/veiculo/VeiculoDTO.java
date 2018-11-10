package br.com.softplan.sienge.veiculo;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class VeiculoDTO {

    private Long id;
    private String descricao;
    private BigDecimal fatorCusto;

}
