package br.com.softplan.sienge.calculocustotransporte;

import br.com.softplan.sienge.tipovia.TipoViaDTO;
import br.com.softplan.sienge.tipovia.TipoViaService;
import br.com.softplan.sienge.veiculo.VeiculoService;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/calculo-custo-transporte")
public class CalculoCustoTransporteController {

    private static final int PESO_CARGA_MAXIMO_SEM_CUSTO_ADICIONAL = 5;
    private static final BigDecimal CUSTO_ADICIONAL_POR_TONELADA = new BigDecimal(0.02).setScale(2, RoundingMode.DOWN);

    private TipoViaService tipoViaService;
    private VeiculoService veiculoService;
    private CalculoCustoTransporteValidator validator;
    private CalculoCustoTransporteMapper mapper;

    public CalculoCustoTransporteController(TipoViaService tipoViaService,
                                            VeiculoService veiculoService,
                                            CalculoCustoTransporteValidator validator,
                                            CalculoCustoTransporteMapper mapper) {
        this.tipoViaService = tipoViaService;
        this.veiculoService = veiculoService;
        this.validator = validator;
        this.mapper = mapper;
    }

    @InitBinder("calculoCustoTransporteDTO")
    public void initBinder(WebDataBinder binder) {
        binder.setValidator(validator);
    }

    @GetMapping
    public String abrirPagina(CalculoCustoTransporteDTO calculoCustoTransporteDTO, Model model) {
        List<TipoViaDTO> listaTipoVias = tipoViaService.getTiposViasAtivas();
        mapper.copiarKmsRodados(listaTipoVias, calculoCustoTransporteDTO.getTiposVias());
        calculoCustoTransporteDTO.setTiposVias(listaTipoVias);

        model.addAttribute("calculoCustoTransporteDTO", calculoCustoTransporteDTO);
        model.addAttribute("veiculos", veiculoService.geVeiculosAtivos());
        return "index";
    }

    @PostMapping
    public String calcular(@Valid CalculoCustoTransporteDTO calculoCustoTransporteDTO, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute(BindingResult.MODEL_KEY_PREFIX.concat(bindingResult.getObjectName()));
        } else {
            List<TipoViaDTO> tiposViasPreenchidas = calculoCustoTransporteDTO.getTiposVias();
            calculoCustoTransporteDTO.setVeiculo(veiculoService.getVeiculo(calculoCustoTransporteDTO.getVeiculo().getId()));
            calculoCustoTransporteDTO.setTiposVias(new ArrayList<>());

            BigDecimal custoTransporte = BigDecimal.ZERO;
            BigDecimal custoAdicionalPorKm = getCustoAdicionalPorKm(calculoCustoTransporteDTO.getPesoCarga());
            BigDecimal fatorMultiplicadorVeiculo = calculoCustoTransporteDTO.getVeiculo().getFatorCusto();

            for (TipoViaDTO tipoVia : tiposViasPreenchidas) {
                if (ObjectUtils.defaultIfNull(tipoVia.getKmsRodados(), 0) > 0) {
                    TipoViaDTO tipoViaBanco = tipoViaService.getTipoVia(tipoVia.getId());
                    tipoViaBanco.setKmsRodados(tipoVia.getKmsRodados());
                    calculoCustoTransporteDTO.getTiposVias().add(tipoViaBanco);

                    BigDecimal custoPorKm = tipoViaBanco.getCusto();
                    BigDecimal kmsRodados = BigDecimal.valueOf(tipoViaBanco.getKmsRodados());

                    BigDecimal custoTransporteTipoVia = custoPorKm.multiply(kmsRodados).multiply(fatorMultiplicadorVeiculo);
                    BigDecimal custoTransporteTipoViaAdicional = custoAdicionalPorKm.multiply(kmsRodados);
                    custoTransporte = custoTransporte.add(custoTransporteTipoVia.add(custoTransporteTipoViaAdicional));
                }
            }

            calculoCustoTransporteDTO.setCustoTransporteCalculado(custoTransporte);

            // Adiciona o resultado do valor calculado na lista
            model.addAttribute("resultado", calculoCustoTransporteDTO);

            // Recria o objeto para que as informações sejam "limpas" na tela
            calculoCustoTransporteDTO = new CalculoCustoTransporteDTO();
        }

        return abrirPagina(calculoCustoTransporteDTO, model);
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
