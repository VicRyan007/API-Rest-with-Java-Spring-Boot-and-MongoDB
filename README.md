# API-Rest-with-Java-Spring-Boot-and-MongoDB

Este é um projeto que demonstra como criar uma API RESTful usando Java, Spring Boot, e MongoDB, com documentação OpenAPI gerada automaticamente pelo SpringDoc. Além disso, foi adicionado um frontend em React com TypeScript para consumir esta API.

## Pré-requisitos

Antes de executar o projeto, certifique-se de ter o seguinte instalado em seu ambiente de desenvolvimento:

- **Java 17** ou superior
- **Maven 3.6.3** ou superior
- **MongoDB** (local ou em um ambiente de nuvem, como o MongoDB Atlas)
- **Node.js** e **npm** (ou Yarn) para o frontend

## Configurações do Projeto

### Backend (Spring Boot)

#### MongoDB

Este projeto usa MongoDB como banco de dados. Certifique-se de que o MongoDB está em execução localmente ou configure a URL de conexão no arquivo `application.properties` ou `application.yml`.

##### Configuração do MongoDB no `src/main/resources/application.properties`

```properties
spring.data.mongodb.uri=mongodb://localhost:27017/project_ifpe
server.port=8080
server.servlet.context-path=/api
logging.level.org.springframework.data.mongodb.core.MongoTemplate=DEBUG
logging.level.org.springframework.web=TRACE
logging.level.com.fasterxml.jackson=TRACE
springdoc.api-docs.path=/api-docs
springdoc.swagger-ui.path=/swagger-ui.html
springdoc.swagger-ui.operationsSorter=method
```

Se estiver usando o MongoDB Atlas, você pode substituir o valor da URI pelo seu cluster URI.

#### Configuração CORS

Para permitir que o frontend (rodando em `http://localhost:3000`) se comunique com o backend, uma configuração CORS foi adicionada. Esta configuração está em `src/main/java/com/ifpe/project/config/CorsConfig.java`.

### Frontend (React)

O frontend é uma aplicação React desenvolvida com TypeScript e Material-UI. Ele se comunica com a API RESTful do backend.

## Como Executar o Projeto

Siga os passos abaixo para rodar a aplicação localmente:

### 1. Clone o repositório

```bash
git clone https://github.com/seu-usuario/seu-repositorio.git
cd seu-repositorio
```

### 2. Configure e inicie o Backend (Spring Boot)

Na pasta raiz do projeto (`API-Rest-with-Java-Spring-Boot-and-MongoDB`):

```bash
mvn clean install
mvn spring-boot:run
```

- A API estará disponível em: `http://localhost:8080/api`
- A documentação OpenAPI (Swagger UI) estará acessível em: `http://localhost:8080/api/swagger-ui.html`

### 3. Configure e inicie o Frontend (React)

Abra um **novo terminal** e navegue para a pasta `frontend`:

```bash
cd frontend
```

Instale as dependências:

```bash
npm install
# ou yarn install
```

Inicie o servidor de desenvolvimento do React:

```bash
npm start
# ou yarn start
```

- O frontend estará disponível em: `http://localhost:3000`

## Como Executar os Testes

### Backend

Para rodar os testes do backend, use o seguinte comando na pasta raiz do projeto:

```bash
mvn test
```

### Frontend

Para rodar os testes do frontend, use o seguinte comando na pasta `frontend`:

```bash
npm test
# ou yarn test
```

## Tecnologias Utilizadas

-   **Java 17**
-   **Spring Boot**
-   **MongoDB**
-   **OpenAPI/Swagger** (com SpringDoc)
-   **Rest Assured** (para testes de integração do backend)
-   **React.js**
-   **TypeScript**
-   **Material-UI**
-   **Axios**
-   **React Router DOM**

## Problemas Comuns

-   Certifique-se de que o **MongoDB** está rodando e que a URL de conexão está correta no `application.properties`.
-   **Erros de CORS**: Se o frontend não conseguir se comunicar com o backend, verifique se o backend Spring Boot foi reiniciado após a adição da configuração `CorsConfig.java`.
-   Verifique se todas as dependências do Maven (backend) e do npm/yarn (frontend) foram baixadas corretamente.
-   Certifique-se de que **tanto o backend quanto o frontend estão rodando** simultaneamente em seus respectivos terminais.

## Contribuições

Sinta-se à vontade para fazer um fork deste repositório, abrir issues e enviar pull requests.

