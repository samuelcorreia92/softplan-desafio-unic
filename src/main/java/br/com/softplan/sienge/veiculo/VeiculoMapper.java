package br.com.softplan.sienge.veiculo;

import br.com.softplan.core.mapper.BaseMapper;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
interface VeiculoMapper extends BaseMapper<VeiculoEntity, VeiculoDTO> {

}
