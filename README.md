# Softplan - Avaliação Desenvolvedor UNIC

Avaliação para Desenvolvedor de Software – Softplan UNIC

## Tecnologias
##### Backend
- Java 8
- Spring Boot 2.1.0
- Banco de Dados H2 `(Banco em memória)`
- Lombok 1.18.2 `(Para geração de métodos get/set, construtores, toString, etc)`
- MapStruct 1.2.0 `(Para realizar a conversão de entidades em DTOs e vice-versa)`
- Maven `(Empacotador e gerenciador de dependências)`

##### Frontend
- Thymeleaf 3.0.11
- Bootstrap 4.1.3 `(Framework para construção de sites responsivos)`
- Font Awesome 5.5.0 `(Biblioteca de icones)`

## Desafio
##### Decisões Técnicas
- A decisão pelo `Spring Boot` é quase que unânimidade para novos projetos, pelos vários benefícios que ele nos trás principalmente relacionados a alta produtividade.
- Das tecnologias utilizadas a única com a qual ainda não havia trabalhado era o `Thymeleaf`, mas para facilitar a implementação e principalmente a execução do projeto optei por utilizar tecnologias com suporte nativo ao `Spring Boot`, e dentre as opções de tecnologias utilizadas no front-end achei o `Thymeleaf` interessante e como nunca havia trabalhado com ele seria uma boa oportunidade para conhecer.
- Optei por utilizar o `H2` por ser um excelente banco de dados relacional para a criação de MOCKs, por não exigir instalações e configurações em excesso para a sua utilização.

## Rodando o projeto
Para rodar o projeto basta executar o seguinte comando na raíz do projeto **softplan-desafio-unic**:

*$ mvn spring-boot:run*

**Obs.: para executar o comando acima o Maven (mvn) precisa estar configurado nas variáveis de ambiente do SO**

Agora basta acessar a URL: http://localhost:8082/calculo-custo-transporte

## Considerações Finais
- Gostaria de agradecer pela oportunidade de participar do processo seletivo e espero que eu tenha conseguido demonstrar minhas caracteristicas e habilidades relacionadas a desenvolvimento de sistemas e que as mesmas tenham despertado um maior interesse da empresa a meu respeito
- Infelizmente não deixei o projeto 100% funcional em dispositivos móveis, e nem criei um Dockerfile para o projeto