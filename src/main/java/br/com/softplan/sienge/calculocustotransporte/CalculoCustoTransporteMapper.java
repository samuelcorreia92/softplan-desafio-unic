package br.com.softplan.sienge.calculocustotransporte;

import br.com.softplan.sienge.tipovia.TipoViaDTO;
import org.mapstruct.Mapper;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
interface CalculoCustoTransporteMapper {

    /**
     * Copia os valores dos campos "Kms rodados" da lista de origem para a de destino
     *
     * @param destino Lista de destino (a que receberá os valores de "Kms rodados")
     * @param origem  Lista de origem (A que possui os valores a serem copiados
     */
    default void copiarKmsRodados(List<TipoViaDTO> destino, List<TipoViaDTO> origem) {
        if (destino != null && origem != null) {
            Map<Long, TipoViaDTO> mapaOrigem = origem.stream().collect(Collectors.toMap(TipoViaDTO::getId, tipoViaDTO -> tipoViaDTO));
            destino.forEach(tipoViaDestino -> tipoViaDestino.setKmsRodados(mapaOrigem.get(tipoViaDestino.getId()).getKmsRodados()));
        }
    }

}
