# Softplan - Avaliação Desenvolvedor UNIC

Avaliação para Desenvolvedor de Software – Softplan UNIC

# Atividade 01
### Tecnologias
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

<br />

### Desafio
##### Decisões Técnicas
- A decisão pelo `Spring Boot` é quase que unânimidade para novos projetos, pelos vários benefícios que ele nos trás principalmente relacionados a alta produtividade.
- Das tecnologias utilizadas a única com a qual ainda não havia trabalhado era o `Thymeleaf`, mas para facilitar a implementação e principalmente a execução do projeto optei por utilizar tecnologias com suporte nativo ao `Spring Boot`, e dentre as opções de tecnologias utilizadas no front-end achei o `Thymeleaf` interessante e como nunca havia trabalhado com ele seria uma boa oportunidade para conhecer.
- Optei por utilizar o `H2` por ser um excelente banco de dados relacional para a criação de MOCKs, por não exigir instalações e configurações em excesso para a sua utilização.

<br />

### Rodando o projeto
Para rodar o projeto basta executar o seguinte comando na raíz do projeto **softplan-desafio-unic**:

`$ mvn spring-boot:run`

**Obs.: para executar o comando acima o Maven (mvn) precisa estar configurado nas variáveis de ambiente do SO**

Agora basta acessar a URL: http://localhost:8082/calculo-custo-transporte

<br />
<br />

# Atividade 02

Realizei o commit das classes `GeradorObservacao` e `GeradorObservacaoTest` que foi enviada junto com o código da atividade 1. As classes estão na raiz dos pacotes de código fonte e de teste respectivamente, e nos mesmos pacotes estão as classes alteradas (com as melhorias listadas abaixo). A classe de `Test` não foi alterada eu simplemente criei uma cópia alterando para testar a nova classe `GeradorObservacaoAlterado`.
 
Segue abaixo a lista das alterações que foram identificadas e realizadas no código.

### 1. Utilizar Generics
A primeira alteração a ser realizada para evitar (muitos) problemas é utilizar `generics` na assinatura dos métodos abaixo, para que seja aceito apenas numeros inteiros (`int` ou `Integer`).

*Antes*
```java
public String geraObservacao(List lista) {}

private String retornaCodigos(List lista) {}
```

*Depois*
```java
public String geraObservacao(List<Integer> lista) {}

private String retornaCodigos(List<Integer> lista) {}
```

<br />

### 2. Validar Objetos Nulos
Utilizando as dicas de programação defensiva deveriamos verificar se a lista recebida por parâmetro no método `geraObservacao(List<Integer> lista)` é diferente de `null` já que esse método é público e não temos como saber se receberemos sempre uma lista instanciada.

Essa validação (nesse caso) não é necessária no método `retornaCodigos(List<Integer> lista)` pois ele é privado e sendo assim só será chamado internamente, e como já verificamos anteriormente sabemos que se chegar nesse ponto do código temos uma lista válida (diferente de `null`).

*Código com validação*
```java
public String geraObservacao(List<Integer> lista) {
    if(lista == null) {
        throw new IllegalArgumentException("O parâmetro não pode ser nulo");
    }
    ...
}
```

<br />

### 3. Validação Desnecessária e Código Não Utilizado
No trecho de código abaixo temos três pequenos "problemas" que por mais que não gerem erro, são desnecessários e fazem com que o entendimento fique um pouco mais complicado ou diminua a performance da execução do código (por mais que não seja tão perceptível nesse caso).

*Trecho de Código*
```java
String s = "";
if (cod.toString() == null || cod.toString().length() <= 0)
    s = "";
```

O primeiro "problema" é executar uma validação desnecessária (`cod.toString() == null`) pois sempre irá retornar `false` já que a variável `cod` do tipo `StringBuilder` foi instanciada antes da instrução `for` e ao fazer isso o valor interno é uma string vazia e não `null`. É válido lembrar que se a variável citada não tivesse sido inicializada, ou seja, fosse `null` essa validação não resultaria em `true`, em vez disso seria lançada a exceção `NullPointerException`, pois a chamada ao método `toString()` seria feita em uma variável nula.

Já o segundo é a chamada desnecessária ao método `toString()` para verificar o tamanho do texto atualmente salvo no `StringBuilder`, esse método faz com que seja retornado todo o conteúdo em uma `String` para só depois verificar o tamanho, mas o melhor e mais performático seria executar o método `length()` diretamente no `StringBuilder`, evitando assim que fosse criado uma `String` com o conteúdo atual, e como esse método se encontra dentro de um loop, são criadas várias `Strings` desnecessárias.

E por último o terceiro pequeno "problema" é o corpo da instrução if que redefine o mesmo valor para a variável `s`, que é uma string vazia `""`.

<br />

### 4. Variável Desnecessária
A variável `s` do item anterior é totalmente desnecessária pois o valor que está sendo atribuida a ela pode ser adicionada diretamente ao `StringBuilder`.

E além de essa variável não ser necessária a forma como ela está sendo usada para juntar com o número da nota não é o ideal, pois está sendo feito uma concatenação com o operador `+` dentro do método `append()`, fazendo com que o benefício do método `append()` não seja utilizado, o melhor seria utilizar esse método de forma encadeada da seguinte forma: `cod.append(s).append(c);`

*Antes*
```java
Integer c = iterator.next();
String s = "";
if (cod.toString() == null || cod.toString().length() <= 0)
    s = "";
else if (iterator.hasNext())
    s = ", ";
else
    s = " e ";

cod.append(s + c);
```

*Depois*
```java
Integer c = iterator.next();
if (cod.toString() == null || cod.toString().length() <= 0)
    cod.append("");
else if (iterator.hasNext())
    cod.append(", ");
else
    cod.append(" e ");

cod.append(c);
```

**Obs.: a falta das chaves na instrução `if/else` acima dificulta a leitura e essa forma, por mais que seja válida deve ser evitada.**

<br />

### 5. Outras Melhorias
- Alterado nome de variáveis, constantes e métodos para ajudar no entendimento do código e/ou se adequar ao padrão Java
- Criado novo método para separar a lógica de formatar os códigos, quanto mais específico for os métodos (e classes) melhor será para futuras manutenções e até mesmo para entendimento do código
- Alterado comentário de linha do método público para JavaDocs


<br />
<br />

# Considerações Finais
*Gostaria de agradecer pela oportunidade de participar do processo seletivo e espero que eu tenha conseguido demonstrar minhas caracteristicas e habilidades relacionadas a desenvolvimento de sistemas e que as mesmas tenham despertado um maior interesse da empresa a meu respeito*
