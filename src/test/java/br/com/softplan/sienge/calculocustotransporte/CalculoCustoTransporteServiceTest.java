package br.com.softplan.sienge.calculocustotransporte;

import br.com.softplan.sienge.tipovia.TipoViaDTO;
import br.com.softplan.sienge.veiculo.VeiculoDTO;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CalculoCustoTransporteServiceTest {

    // IDs dos tipos de via
    private static final Long TIPO_VIA_PAVIMENTADA = 1L;
    private static final Long TIPO_VIA_NAO_PAVIMENTADA = 2L;

    // IDs dos tipos de veiculo
    private static final Long VEICULO_CAMINHAO_BAU = 1L;
    private static final Long VEICULO_CAMINHAO_CACAMBA = 2L;
    private static final Long VEICULO_CARRETA = 3L;

    @Autowired
    private CalculoCustoTransporteService service;

    @Test
    public void calcularTest() {
        realizarTesteEValidarResultado(montarDTO(VEICULO_CAMINHAO_CACAMBA, 8, 100, null), 62.7);
        realizarTesteEValidarResultado(montarDTO(VEICULO_CAMINHAO_BAU, 4, null, 60), 37.2);
        realizarTesteEValidarResultado(montarDTO(VEICULO_CARRETA, 12, null, 180), 150.19);
        realizarTesteEValidarResultado(montarDTO(VEICULO_CAMINHAO_BAU, 6, 80, 20), 57.6);
        realizarTesteEValidarResultado(montarDTO(VEICULO_CAMINHAO_CACAMBA, 5, 50, 30), 47.88);
    }

    private void realizarTesteEValidarResultado(CalculoCustoTransporteDTO dto, double resultadoEsperado) {
        Assert.assertEquals(formatarNumero(resultadoEsperado), service.calcular(dto).getCustoTransporteCalculado());
    }

    private BigDecimal formatarNumero(double valor) {
        return new BigDecimal(valor).setScale(2, RoundingMode.HALF_DOWN);
    }

    private CalculoCustoTransporteDTO montarDTO(Long idVeiculo, Integer pesoCarga, Integer kmsPavimentada, Integer kmsNaoPavimentada) {
        CalculoCustoTransporteDTO dto = new CalculoCustoTransporteDTO();
        dto.setVeiculo(new VeiculoDTO(idVeiculo));
        dto.setPesoCarga(pesoCarga);
        dto.setTiposVias(new ArrayList<>(2));
        if (kmsPavimentada != null) {
            dto.getTiposVias().add(new TipoViaDTO(TIPO_VIA_PAVIMENTADA, kmsPavimentada));
        }
        if (kmsNaoPavimentada != null) {
            dto.getTiposVias().add(new TipoViaDTO(TIPO_VIA_NAO_PAVIMENTADA, kmsNaoPavimentada));
        }
        return dto;
    }

}
