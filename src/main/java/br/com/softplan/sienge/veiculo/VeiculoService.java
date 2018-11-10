package br.com.softplan.sienge.veiculo;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VeiculoService {

    private VeiculoRepository repository;
    private VeiculoMapper mapper;

    public VeiculoService(VeiculoRepository repository,
                          VeiculoMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public VeiculoDTO getVeiculo(Long id) {
        return mapper.paraDTO(repository.findById(id).orElse(null));
    }

    public List<VeiculoDTO> geVeiculosAtivos() {
        return mapper.paraDTO(repository.findByAtivoIsTrue());
    }

}
