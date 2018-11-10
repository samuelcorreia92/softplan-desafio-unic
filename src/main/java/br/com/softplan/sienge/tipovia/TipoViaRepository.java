package br.com.softplan.sienge.tipovia;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

interface TipoViaRepository extends JpaRepository<TipoViaEntity, Long> {

    List<TipoViaEntity> findByAtivoIsTrue();

}
