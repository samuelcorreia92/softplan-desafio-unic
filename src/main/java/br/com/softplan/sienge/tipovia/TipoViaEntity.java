package br.com.softplan.sienge.tipovia;

import br.com.softplan.core.model.AbstractEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;

@Data
@Entity
@Table(name = "tipo_via")
@EqualsAndHashCode(callSuper = false)
public class TipoViaEntity extends AbstractEntity {

    @Id
    private Long id;
    private String descricao;
    private BigDecimal custo;
    private boolean ativo;

}
