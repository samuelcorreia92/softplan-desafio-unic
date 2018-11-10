package br.com.softplan.sienge.veiculo;

import br.com.softplan.core.model.AbstractEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;

@Data
@Entity
@Table(name = "veiculo")
@EqualsAndHashCode(callSuper = false)
public class VeiculoEntity extends AbstractEntity {

    @Id
    private Long id;
    private String descricao;
    @Column(name = "FATOR_CUSTO")
    private BigDecimal fatorCusto;
    private boolean ativo;

}
