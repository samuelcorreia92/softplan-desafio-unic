package br.com.softplan.sienge.tipovia;

import br.com.softplan.core.mapper.BaseMapper;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
interface TipoViaMapper extends BaseMapper<TipoViaEntity, TipoViaDTO> {

}
