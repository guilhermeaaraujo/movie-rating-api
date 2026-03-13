# 💰 Movie Rating API

API REST de **Crítica de filmes** desenvolvida em **Java com Spring Boot**.

Este foi meu primeiro projeto utilizando um banco de dados não relacional (**MongoDB**), representando um avanço significativo no meu aprendizado.

---

## 🚀 Tecnologias Utilizadas

- Java 17+
- Spring Boot
- Banco de dados MongoDB
- Maven
- Spring Security + JWT
- Swagger (OpenAPI)
- JUnit + Mockito para testes unitários.

---

## 🧱 Arquitetura do Projeto

O projeto segue uma **arquitetura em camadas**, com responsabilidades bem definidas:

- **Controller** → Camada de entrada (API REST)
- **Service** → Regras de negócio (Business Rules)
- **Repository** → Persistência de dados
- **Entity (Domain)** → Modelo de domínio
- **Data Tranfer Objects (DTO's)** → Objetos para transferência de dados.

## 📊 Modelo de Domínio (Resumo)

### Entidades principais:
- **User** → Representa o usuário do sistema
- **Movie** - Representa os filmes do banco de dados
- **Review* - Representa as críticas dos usuários

## 🔒 Autenticação e Autorização

- Implementada utilizando Spring Security + JWT com controle baseado em roles.
- ROLE_ADMIN: Representa os administradores com todas as permissões de CRUD ao banco de dados.
- ROLE_USER: Representa os usuários padrão da aplicação, tendo certas permissões sobre suas próprias contas e transações.

## 📌 Funcionalidades Principais

Endpoints que permitem:

- CRUD de Usuários pelo ADMIN
- Registro e Login de Usuários
- CRUD de Filmes
- Criação e deleção de Críticas (respeitando regras de negócio)

## 📘 Documentação da API (Swagger)

Documentação interativa disponível em:

http://localhost:8080/swagger-ui/index.html

Através dela você pode testar os endpoints diretamente.

### Pré-requisitos
- Java 17+
- Maven
- MongoDB

## 👨‍💻 Autor

**Guilherme Araújo**

Projeto desenvolvido com foco em aprendizado e arquitetura backend.

🔗 GitHub: https://github.com/guilhermeaaraujo
