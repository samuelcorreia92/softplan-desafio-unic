package br.com.softplan.sienge.veiculo;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
public class VeiculoDTO {

    private Long id;
    private String descricao;
    private BigDecimal fatorCusto;
    private boolean ativo;

    public VeiculoDTO(Long id) {
        this.id = id;
    }

}
