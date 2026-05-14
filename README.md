# Boxes API

**Boxes API** é o backend de um gerenciador de dados sensíveis e cofre pessoal (vault). Ele permite aos usuários organizar informações de forma segura através da criação de "Boxes" (pastas/categorias) e inserção de "Items" (segredos, credenciais, notas) dentro delas.

O sistema foi arquitetado com a premissa de **Criptografia Ponta-a-Ponta (E2EE)**. Informações sensíveis (como os detalhes de um *Item*) são criptografadas no lado do cliente antes de serem enviadas à API, garantindo que o servidor armazene apenas os dados cifrados e não tenha acesso ao conteúdo em texto plano.

## 🚀 Tecnologias

O projeto é desenvolvido no ecossistema Spring, utilizando as seguintes tecnologias:

- **Java 21**
- **Spring Boot 3.5** (Web, Security, Data JPA, Validation)
- **PostgreSQL** (Banco de Dados Relacional)
- **Flyway** (Migrações e versionamento de banco de dados)
- **Docker Compose** (Infraestrutura local)
- **MapStruct** (Mapeamento de Entidade/DTO)
- **Lombok** (Redução de Boilerplate)
- **JWT (Auth0)** (Autenticação via Token)

## 📦 Modelo de Domínio

- **User**: Conta de usuário protegida por e-mail e senha. Armazena um `encryptionSalt` usado pelo cliente para derivar a chave-mestra e criptografar os dados.
- **Box**: Funciona como uma pasta organizacional. Pertence a um usuário e possui atributos visuais como rótulo, descrição e cor. Suporta hierarquia (uma Box pode conter outras).
- **Item**: Representa o dado sensível armazenado. Vinculado a uma Box, possui nome, descrição e armazena o `iv` (Initialization Vector) empregado na criptografia daquele item.

## ⚙️ Pré-requisitos

Para rodar a aplicação localmente, você precisará ter instalado em sua máquina:

- **Java 21** (JDK)
- **Docker** e **Docker Compose** (para o banco de dados)

## 🏃 Como Executar

1. **Clone o repositório:**
   ```bash
   git clone git@github.com:johnnysn/boxes-api.git
   cd boxes-api
   ```

2. **Suba o Banco de Dados localmente:**
   A aplicação requer o banco de dados para rodar. Utilize o Docker Compose para subir o container do PostgreSQL:
   ```bash
   docker compose up -d
   ```
   *O banco estará acessível localmente na porta 5431 com usuário e senha "postgres".*

3. **Execute a aplicação via Gradle:**
   Você pode usar o Wrapper do Gradle incluído no projeto para iniciar o servidor Spring Boot:
   ```bash
   ./gradlew bootRun
   ```

4. **Testando a API:**
   A API rodará por padrão em `http://localhost:8080`.
   Você pode encontrar exemplos de requisições de autenticação na pasta `/requests`.

## 📄 Licença

Este projeto está sob a licença **MIT**. Sinta-se livre para usar, modificar e distribuir conforme detalhado pela licença.
