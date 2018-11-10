package br.com.softplan.sienge.calculocustotransporte;

import br.com.softplan.sienge.tipovia.TipoViaDTO;
import br.com.softplan.sienge.veiculo.VeiculoDTO;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class CalculoCustoTransporteDTO {

    private List<TipoViaDTO> tiposVias;
    private VeiculoDTO veiculo;
    private Integer pesoCarga;
    private BigDecimal custoTransporteCalculado;

}
