# Sistema de Gerenciamento de jornadas de trabalho

Este projeto é um sistema de gestão de jornadas de trabalho desenvolvido em Java utilizando o framework Spring Boot e Gradle como ferramenta de build. O sistema permite gerenciar as jornadas de trabalho dos funcionários, incluindo o registro de pontos, cálculo de horas trabalhadas, horas extras e horas restantes.


## 🛠 Tecnologias Utilizadas

### Backend
- **Spring Boot (v3.4.0)**: Framework para criação de aplicações Java.
- **Spring Security (v6.1.4)**: Configuração de autenticação e autorização.
- **Spring Data JPA**: Manipulação de dados com JPA/Hibernate.
- **PostgreSQL (v42.3.1)**: Banco de dados relacional.
- **SpringDoc OpenAPI (v2.7.0)**: Documentação da API com Swagger.
- **ModelMapper (v3.0.0)**: Mapeamento de objetos DTO e entidades.
- **JSON Web Tokens (JWT) (v0.11.5)**: Gerenciamento de tokens para autenticação.
- **Lombok (v1.18.24)**: Redução de boilerplate no código.

### Testes
- **JUnit Platform (v1.8.2)**: Execução de testes unitários e de integração.
- **Spring Security Test**: Testes relacionados à segurança.
- **Spring Boot Starter Test**: Suporte para testes no Spring.

### Desenvolvimento
- **Spring Boot DevTools**: Ferramenta para facilitar o desenvolvimento.

## 🚀 Funcionalidades
## Funcionalidades

- **Registro de pontos de entrada e saída dos funcionários**.
- **Cálculo de horas trabalhadas**, horas extras e horas restantes.
- **Listagem de todas as jornadas de um funcionário**.
- **Resumo detalhado de uma jornada específica**.
- **Resumo total e médio de todas as jornadas de um funcionário**.
- **Cadastro de funcionários**: Adição de novos funcionários no sistema.
- **Consulta de dados**: Busca detalhada de informações dos funcionários.
- **Atualização de informações**: Alteração de dados já cadastrados.
- **Exclusão de registros**: Remoção de funcionários do sistema.
- **Segurança**: Implementação robusta com autenticação via JWT.

## ⚙️ Configuração

1. Configure o banco de dados no arquivo `application.properties`:
   ```properties
   spring.datasource.url=jdbc:mysql://localhost:3306/nome_do_banco
   spring.datasource.username=usuario
   spring.datasource.password=senha
   spring.jpa.hibernate.ddl-auto=update
## 📚 Uso das APIs

### Buscar ou Criar Jornada
- **Endpoint**: `GET /jornadas/{id}/{data}`  
- **Parâmetros**:  
  - `id`: ID do funcionário  
  - `data`: Data da jornada (formato ISO)

#### Exemplo de Requisição:
```http
GET /jornadas/1234/2024-12-20

#### Exemplo de Resposta:
```json
{
  "data": "2024-12-20",
  "pontos": [
    {
      "id": 29,
      "dataHora": "2024-12-20T08:00:00",
      "tipo": "ENTRADA"
    },
    {
      "id": 30,
      "dataHora": "2024-12-20T09:00:00",
      "tipo": "SAIDA"
    }
  ],
  "jornadaCompleta": false,
  "horasRestantes": "PT5H",
  "horasExtras": "PT0S"
}
