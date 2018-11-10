package br.com.softplan.sienge.veiculo;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

interface VeiculoRepository extends JpaRepository<VeiculoEntity, Long> {

    List<VeiculoEntity> findByAtivoIsTrue();

}
