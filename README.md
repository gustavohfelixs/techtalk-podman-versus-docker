Tech Talk - Podman vs Docker
===========================

**Pré-requisitos**
- JAVA 8
- Maven 3.3+
- IDE de sua preferência

*****
## Introdução
**Podman** é um administrador de containeres Linux feito para substituir o **Docker**.
Na sua grande parte eles são idênticos, porém tem algumas diferenças interessantes a 
serem citadas. Como por exemplo sua execução, compatibilidade com certos tipos de arquivos
e outros fatores que veremos mais a frente.

**Containeres?**

"_Containeres são processos com subprocessos rodando em um sistema operacional, simulando outro
sistema operacional mas de forma isolada, isso funciona através dos namespaces, caracteristicas Linux
que foram exploradas pelo Docker_" [¹](https://docs.docker.com/get-started/) - Docker

### Projeto
Aqui trabalharemos com um projeto **Spring Batch** que vai ler um arquivo csv (separado por vírgulas, conhecido como _excel_)
e vamos persistir as informações em uma base de dados, porém estamos em um cenário onde no meu computador para exemplificar não 
tenho MySql instalado. Então vamos utilizar o Podman para rodar um docker-compose e subir um Banco de dados novinho em folha.

*Este é um projeto Básico de Spring Batch - Bons estudos! Para uma intodrução ao assunto recomendo esta [vídeo-aula] (PROJETO DE REFERÊNCIA NO LINK)(https://www.youtube.com/watch?v=6iDzOi2YWxA) e o [livro](https://github.com/gustavohfelixs/Ebook-The-Definitive-guide-to-spring-batch-modern-finite-batch-processing)**


#### Como utilizar o projeto?
1. Baixe o arquivo para seu computador como zip
2. extraia o arquivo zip
3. Import para sua IDE como projeto Maven

## Instale as dependências
`mvn clean install`

### Clean and Build
`mvn clean package`

### Executar projeto
`mvn spring-boot:run` <br>Ou<br> `java -jar ./target/App-0.0.1-SNAPSHOT.jar` <br><br> Você também pode rodar de a classe **AppApplication** em sua IDE que está no caminho `br.com.gfelix.app.AppApplication`

Entendendo o Projeto
====================

Pacotes (não é um padrão):
* **config** - Responsável por guardar as configurações
spring, suas classes recebem ``@Configuration`` como anotação. É aqui que vamos
e que vamos configurar nossos Jobs e steps 
* **entity** - Guarda as classes principais do negócio, as entidades que vão se tornar tabelas
no nosso banco de Dados, tem uma classe java normal com seus atributos e os Gettes, Setters e construtores.
  (Optei por usar de annotations Lombok para gerar os Getters, Setters e construtores: `@Getters @Setters @AllArgsConstructor @NoArgsConstructor`)
* **repository** - Guarda o repositório
* **step** - Guarda classes que implementam nossos steps configurados no package `config`

### Funcionalidade

Input tem um arquivo csv chamado `transacoes.csv` que contém  os seguintes itens: 
* **idTrans** - `"7efcs8r920asd02381sc9s931232900sd234123 - string"`
* **cnpjRemetente** `12345678000100 - string`
* **cnpjDestinatario** `12345678900101 - string`
* **tipoConta** `corrente/ poupanca - string`
* **valor** `3000 - Integer`
* **dataCriacao** `22:14:59:12-04-23 - string`

_**curiosidade**: O arquivo csv foi feito pela IA chatgpt, uma dica para quem quer t
trabalhar com diferentes tipos de dados e não quer desprender tempo criando um arquivo._

A aplicação lê esses dados coluna a coluna, processa e joga tudo em um banco de dados.

## Hands on
Rode a aplicação com os comandos demonstrados anteriormente em  `Instale as Dependências` e digite no seu navegador http://localhost:8096/h2-console para acessar 
a base de dados.  

![img_1.png](img_1.png)

No campo JDBC Url digite `jdbc:h2:mem:mydb` e clique `connect`.

![img_3.png](img_3.png)

Clique em TRANSACAO e em run para rodar o comando que inspeciona a base de dados.

![img_4.png](img_4.png)

Não temos nada gravado em nossa base (ainda).

Para gravar os dados na base de dados foi criado o end-point `"/run"` - acesse em http://localhost:8096/run

![img_5.png](img_5.png)

É possível ver isso no terminal de nossa IDE, significa que deu certo. Vamos checkar a base de dados! Após executar o mesmo comando `run` no navegador com o banco de dados é possível ver que os nossos dados foram para a base: 

![img_6.png](img_6.png)

Deu certo! 

Foi meu primeiro app usando o Spring Batch, bons estudos e até mais!
