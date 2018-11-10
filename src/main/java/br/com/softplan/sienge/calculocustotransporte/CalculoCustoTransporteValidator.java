package br.com.softplan.sienge.calculocustotransporte;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class CalculoCustoTransporteValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return CalculoCustoTransporteDTO.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        CalculoCustoTransporteDTO dto = (CalculoCustoTransporteDTO) target;
        if (!isAoMenosUmTipoViaPreenchido(dto)) {
            errors.rejectValue("tiposVias", "tiposVias.obrigatorio");
        }

        if (dto.getVeiculo() == null || dto.getVeiculo().getId() == null) {
            errors.rejectValue("veiculo", "veiculo.obrigatorio");
        }

        if (dto.getPesoCarga() == null) {
            errors.rejectValue("pesoCarga", "pesoCarga.obrigatorio");
        } else if (dto.getPesoCarga() <= 0) {
            errors.rejectValue("pesoCarga", "pesoCarga.menor.igual.zero");
        }
    }

    /**
     * Verifica se ao menos um dos campos "tipos de via" foram preenchidos
     */
    private boolean isAoMenosUmTipoViaPreenchido(CalculoCustoTransporteDTO dto) {
        boolean isAoMenosUmTipoViaPreenchido = !(dto.getTiposVias() == null || dto.getTiposVias().isEmpty());
        if (isAoMenosUmTipoViaPreenchido) {
            long qtdTipoViaPrenchido = dto.getTiposVias().stream().filter(tipoVia -> tipoVia.getKmsRodados() != null && tipoVia.getKmsRodados() > 0).count();
            isAoMenosUmTipoViaPreenchido = qtdTipoViaPrenchido > 0;
        }
        return isAoMenosUmTipoViaPreenchido;
    }
}
