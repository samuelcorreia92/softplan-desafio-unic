package br.com.softplan.sienge.calculocustotransporte;

import br.com.softplan.sienge.tipovia.TipoViaDTO;
import br.com.softplan.sienge.tipovia.TipoViaService;
import br.com.softplan.sienge.veiculo.VeiculoService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class CalculoCustoTransporteService {

    private static final int PESO_CARGA_MAXIMO_SEM_CUSTO_ADICIONAL = 5;
    private static final BigDecimal CUSTO_ADICIONAL_POR_TONELADA = new BigDecimal(0.02).setScale(2, RoundingMode.HALF_DOWN);

    private final VeiculoService veiculoService;
    private final TipoViaService tipoViaService;

    public CalculoCustoTransporteService(VeiculoService veiculoService, TipoViaService tipoViaService) {
        this.veiculoService = veiculoService;
        this.tipoViaService = tipoViaService;
    }

    /**
     * Calcula o custo do transporte de acordo com os parâmetros informados
     *
     * @param dto Parâmetros para calculo do custo
     * @return Objeto informado por parâmetro com o valor do custo de transporte
     */
    public CalculoCustoTransporteDTO calcular(final CalculoCustoTransporteDTO dto) {
        Map<Long, Integer> kmsRodadosPorTipoVia = dto.getTiposVias().stream().collect(Collectors.toMap(TipoViaDTO::getId, TipoViaDTO::getKmsRodados));

        // Consulta as informações no banco de dados (para ter as informações corretas)
        dto.setVeiculo(veiculoService.getVeiculo(dto.getVeiculo().getId()));
        dto.setTiposVias(new ArrayList<>(dto.getTiposVias().size()));
        kmsRodadosPorTipoVia.forEach((id, kms) -> {
            TipoViaDTO tipoVia = tipoViaService.getTipoVia(id);
            tipoVia.setKmsRodados(kms);
            dto.getTiposVias().add(tipoVia);
        });

        BigDecimal custoTransporte = BigDecimal.ZERO;
        BigDecimal custoAdicionalPorKm = getCustoAdicionalPorKm(dto.getPesoCarga());
        BigDecimal fatorMultiplicadorVeiculo = dto.getVeiculo().getFatorCusto();

        for (TipoViaDTO tipoVia : dto.getTiposVias()) {
            BigDecimal custoPorKm = tipoVia.getCusto();
            BigDecimal kmsRodados = BigDecimal.valueOf(tipoVia.getKmsRodados());

            BigDecimal custoTransporteTipoVia = custoPorKm.multiply(kmsRodados).multiply(fatorMultiplicadorVeiculo);
            BigDecimal custoTransporteTipoViaAdicional = custoAdicionalPorKm.multiply(kmsRodados);
            custoTransporte = custoTransporte.add(custoTransporteTipoVia.add(custoTransporteTipoViaAdicional));
        }
        dto.setCustoTransporteCalculado(custoTransporte.setScale(2, RoundingMode.HALF_DOWN));
        return dto;
    }

    /**
     * Calcula o custo adicional (R$) por km rodado sobre o peso da carga transportada.
     *
     * <p>Se o peso total da carga for de 5 toneladas ou menos, nada muda no custo do
     * transporte. No entanto, se o peso total for maior do que 5 toneladas, para cada tonelada que
     * exceder esse limite, é preciso acrescentar R$ 0,02 por quilômetro rodado, devido ao aumento
     * no custo de manutenção, como desgaste de pneus por exemplo.</p>
     *
     * @param pesoCarga Peso da carga transportada
     * @return Custo adicional (R$)
     */
    private BigDecimal getCustoAdicionalPorKm(Integer pesoCarga) {
        BigDecimal custoAdicionalPorKm = BigDecimal.ZERO;
        if (pesoCarga > PESO_CARGA_MAXIMO_SEM_CUSTO_ADICIONAL) {
            int diferencaPeso = pesoCarga - PESO_CARGA_MAXIMO_SEM_CUSTO_ADICIONAL;
            custoAdicionalPorKm = CUSTO_ADICIONAL_POR_TONELADA.multiply(BigDecimal.valueOf(diferencaPeso));
        }
        return custoAdicionalPorKm;
    }

}
