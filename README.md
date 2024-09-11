
# API-Rest-with-Java-Spring-Boot-and-MongoDB

Este é um projeto de exemplo que demonstra como criar uma API RESTful usando Java, Spring Boot, e MongoDB, com documentação OpenAPI gerada automaticamente pelo SpringDoc.

## Pré-requisitos

Antes de executar o projeto, certifique-se de ter o seguinte instalado em seu ambiente de desenvolvimento:

- **Java 17** ou superior
- **Maven 3.6.3** ou superior
- **MongoDB** (local ou em um ambiente de nuvem, como o MongoDB Atlas)

## Configurações do Projeto

### MongoDB

Este projeto usa MongoDB como banco de dados. Certifique-se de que o MongoDB está em execução localmente ou configure a URL de conexão no arquivo `application.properties` ou `application.yml`.

#### Configuração do MongoDB no `application.properties`

```properties
spring.data.mongodb.uri=mongodb://localhost:27017/nome-do-banco
```

Se estiver usando o MongoDB Atlas, você pode substituir o valor da URI pelo seu cluster URI.

### Dependências

O projeto utiliza as seguintes dependências principais:

- `spring-boot-starter-web`: Para criar a API RESTful.
- `spring-boot-starter-data-mongodb`: Para integração com MongoDB.
- `spring-boot-starter-test`: Para testes unitários e de integração.
- `springdoc-openapi-ui`: Para gerar a documentação da API via Swagger.
- `rest-assured`: Para realizar testes de integração.

## Como Executar o Projeto

Siga os passos abaixo para rodar a aplicação localmente:

### 1. Clone o repositório

```bash
git clone https://github.com/seu-usuario/seu-repositorio.git
cd seu-repositorio
```

### 2. Compile e rode o projeto com Maven

```bash
mvn clean install
mvn spring-boot:run
```

### 3. Acesse a aplicação

- A API estará disponível em: `http://localhost:8080/`
- A documentação OpenAPI (Swagger UI) estará acessível em: `http://localhost:8080/api/swagger-ui.html`

## Como Executar os Testes

Para rodar os testes, use o seguinte comando:

```bash
mvn test
```

## Tecnologias Utilizadas

- **Java 17**
- **Spring Boot**
- **MongoDB**
- **OpenAPI/Swagger**
- **Rest Assured** (para testes de integração)

## Problemas Comuns

- Certifique-se de que o MongoDB está rodando e que a URL de conexão está correta.
- Verifique se todas as dependências do Maven foram baixadas corretamente.

## Contribuições

Sinta-se à vontade para fazer um fork deste repositório, abrir issues e enviar pull requests.

