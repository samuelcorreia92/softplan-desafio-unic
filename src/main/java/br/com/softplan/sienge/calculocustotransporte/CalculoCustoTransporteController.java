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
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/calculo-custo-transporte")
public class CalculoCustoTransporteController {

    private CalculoCustoTransporteService service;
    private TipoViaService tipoViaService;
    private VeiculoService veiculoService;
    private CalculoCustoTransporteValidator validator;
    private CalculoCustoTransporteMapper mapper;

    public CalculoCustoTransporteController(CalculoCustoTransporteService service,
                                            TipoViaService tipoViaService,
                                            VeiculoService veiculoService,
                                            CalculoCustoTransporteValidator validator,
                                            CalculoCustoTransporteMapper mapper) {
        this.service = service;
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
        if (!bindingResult.hasErrors()) {
            // Deixa na lista de tipos vias apenas as que foram preenchidas na tela
            calculoCustoTransporteDTO.setTiposVias(calculoCustoTransporteDTO.getTiposVias()
                    .stream()
                    .filter(tipoViaDTO -> ObjectUtils.defaultIfNull(tipoViaDTO.getKmsRodados(), 0) > 0)
                    .collect(Collectors.toList()));

            // Realiza o calculo e adiciona o resultado na lista
            model.addAttribute("resultado", service.calcular(calculoCustoTransporteDTO));

            // Recria o objeto para que as informações sejam "limpas" na tela
            calculoCustoTransporteDTO = new CalculoCustoTransporteDTO();
        }

        return abrirPagina(calculoCustoTransporteDTO, model);
    }

}
