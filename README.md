# Stock System

Um sistema de gerenciamento de estoque para a Marley Store, desenvolvido com Spring Boot.

## Visão Geral

Este aplicativo é um sistema de gerenciamento de estoque projetado para a Marley Store. Ele fornece funcionalidades para:
- Autenticação e autorização de usuários
- Gerenciamento de usuários (criação, atualização, exclusão)
- Controle de acesso baseado em funções (RBAC)

## Estrutura do Projeto

O projeto segue uma arquitetura padrão Spring Boot com:
- **Controllers**: Manipulam requisições HTTP
- **Services**: Contêm a lógica de negócios
- **Repositories**: Interface com o banco de dados
- **Models**: Definem entidades de dados
- **DTOs**: Objetos de Transferência de Dados para requisições/respostas
- **Security**: Autenticação e autorização baseada em JWT

## Tecnologias Utilizadas

- **Java 21**: Linguagem de programação principal
- **Spring Boot 3.4.3**: Framework de aplicação
- **Spring Security**: Autenticação e autorização
- **Spring Data JPA**: Acesso ao banco de dados e ORM
- **JWT (Java-JWT 4.4.0)**: Autenticação baseada em tokens
- **PostgreSQL**: Banco de dados
- **Lombok**: Reduz código boilerplate
- **Maven**: Ferramenta de build e gerenciamento de dependências

## Funcionalidades Principais

### 1. Gerenciamento de Usuários
- Registro de usuários
- Autenticação de usuários (login)
- Atualizações de perfil de usuário
- Exclusão de usuários

### 2. Implementação de Segurança
- Autenticação baseada em JWT
- Autorização baseada em funções
- Gerenciamento de sessão stateless
- Criptografia de senhas com BCrypt

### 3. Controle de Acesso Baseado em Funções
- Suporte para múltiplos perfis de usuário (ADMIN, SHOPKEEPER)
- Segurança de endpoints baseada em perfis

## Implementação de Segurança

- **Autenticação JWT**: Implementa autenticação baseada em tokens usando a biblioteca Auth0 Java-JWT
- **Criptografia de Senha**: Usa BCrypt para armazenamento seguro de senhas
- **Sessões Stateless**: Nenhum estado de sessão mantido no servidor
- **Endpoints Protegidos**: Controle de acesso configurável para diferentes endpoints da API
- **Validação de Token**: Valida tokens JWT para cada solicitação protegida

## Endpoints da API

### Gerenciamento de Usuários
- `POST /v1/user/create`: Criar um novo usuário
- `GET /v1/user/get`: Obter informações do usuário atual
- `PATCH /v1/user/update`: Atualizar informações do usuário
- `DELETE /v1/user/delete`: Excluir um usuário
- `POST /v1/user/login`: Autenticar e obter token JWT
- `GET /v1/user/test`: Endpoint de teste para autenticação

## Configuração

Configuração de banco de dados em `application.properties`:
```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/marley-store
spring.datasource.username=admin
spring.datasource.password=admin
spring.datasource.driver-class-name=org.postgresql.Driver

spring.jpa.database-platform=org.hibernate.dialect.PostgreSQLDialect
spring.jpa.hibernate.ddl-auto=update

spring.application.name=stock-system
```

## Desenvolvimento

O projeto usa ferramentas de build padrão do Maven:
- `./mvnw clean install`: Compilar o projeto
- `./mvnw spring-boot:run`: Executar a aplicação

## Notas de Segurança

- A chave secreta JWT está hardcoded na classe `JwtTokenService` (deve ser movida para a configuração)
- Os tokens expiram após 4 horas (configurável em `JwtTokenService`)
- Endpoints públicos não requerem autenticação: `/v1/user/login`, `/v1/user/create`
- Todos os outros endpoints requerem autenticação com token JWT válido