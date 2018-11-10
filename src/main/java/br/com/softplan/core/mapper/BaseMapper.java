package br.com.softplan.core.mapper;

import java.util.List;

/**
 * Interface padrão para as classes Mappers (repsonsáveis por converter de entidade para DTO e vice-versa) do projeto
 *
 * @param <ENTIDADE> Entidade
 * @param <DTO>      DTO das telas de cadastro/alteração
 * @author Samuel Correia Guimarães
 */
public interface BaseMapper<ENTIDADE, DTO> {

    ENTIDADE paraEntidade(DTO dto);

    DTO paraDTO(ENTIDADE entidade);

    List<DTO> paraDTO(List<ENTIDADE> entidade);

}