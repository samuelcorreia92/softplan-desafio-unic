package br.com.softplan.sienge.veiculo;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class VeiculoServiceTest {

    @Autowired
    private VeiculoService service;

    @Test
    public void getVeiculosAtivosTest() {
        List<VeiculoDTO> veiculosAtivos = service.geVeiculosAtivos();
        Assert.assertEquals("Deveria retornar 3 registros de veiculos", 3, veiculosAtivos.size());
        for (VeiculoDTO veiculo : veiculosAtivos) {
            Assert.assertTrue("Não deveria retornar veiculo inativo", veiculo.isAtivo());
        }
    }

    @Test
    public void getVeiculoTest() {
        Assert.assertFalse("Esse registro não deveria estar ativo", service.getVeiculo(4L).isAtivo());
    }


}
