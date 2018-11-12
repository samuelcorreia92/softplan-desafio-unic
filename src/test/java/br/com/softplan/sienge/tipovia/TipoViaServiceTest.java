package br.com.softplan.sienge.tipovia;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TipoViaServiceTest {

    @Autowired
    private TipoViaService service;

    @Test
    public void getTiposViasAtivasTest() {
        List<TipoViaDTO> tiposViasAtivas = service.getTiposViasAtivas();
        Assert.assertEquals("Deveria retornar 2 registros de tipos de vias", 2, tiposViasAtivas.size());
        for (TipoViaDTO tipoVia : tiposViasAtivas) {
            Assert.assertTrue("Não deveria retornar tipo de via inativo", tipoVia.isAtivo());
        }
    }

    @Test
    public void getTipoViaTest() {
        Assert.assertFalse("Esse registro não deveria estar ativo", service.getTipoVia(3L).isAtivo());
    }

}
