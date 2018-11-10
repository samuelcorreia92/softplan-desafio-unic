package br.com.softplan.sienge.tipovia;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TipoViaService {

    private TipoViaRepository repository;
    private TipoViaMapper mapper;

    public TipoViaService(TipoViaRepository repository,
                          TipoViaMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public TipoViaDTO getTipoVia(Long id) {
        return mapper.paraDTO(repository.findById(id).orElse(null));
    }

    public List<TipoViaDTO> getTiposViasAtivas() {
        return mapper.paraDTO(repository.findByAtivoIsTrue());
    }

}
