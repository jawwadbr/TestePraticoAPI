# Teste Prático Attornatus

API simples para gerenciar Pessoas.

Esta API está passando por uma reestruturação novamente e ainda não foi finalizada.

### Desafio

O objetivo desse desafio é a criação de uma API REST para gerenciar pessoas.

#### Requisistos

- Criar uma pessoa
- Editar uma pessoa
- Consultar uma pessoa
- Listar pessoas
- Criar endereço para pessoa
- Listar endereços da pessoa
- Poder informar qual endereço é o principal da pessoa

### Tecnologias

- [Java 17](https://docs.oracle.com/en/java/javase/17/)
- [Spring Boot](https://spring.io/projects/spring-boot)
- [Spring Data JPA](https://docs.spring.io/spring-data/data-jpa/docs/current/reference/html/#repositories)
- [Spring Security](https://docs.spring.io/spring-security/reference/index.html)

## Como usar

### Localmente

- Clone o repositório
- Faça o build do projeto:

```shell
./mvnw clean package
```

- Execute o projeto

```shell
java -jar target/ApiPessoa-0.0.1-SNAPSHOT.jar
```

A API vai ficar disponivel em [localhost:8080](http://localhost:8080/api/pessoa).

## Endpoints da API

Para fazer as requisições da API o aplicativo [Postman](https://www.postman.com) foi usado:

### Endpoints Públicas

#### Endpoints de Pessoa

- `GET /api/pessoa`
- `GET /api/pessoa/consulta`

**Parâmetros**

|   Nome |        Requerido         |  Tipo  | Descrição       |
|-------:|:------------------------:|:------:|-----------------|
| `nome` |  opcional caso use `id`  | string | Nome da Pessoa. |
|   `id` | opcional caso use `nome` |  int   | ID da Pessoa    |

Resposta da requisição HTTP GET

Caso usando `nome` como parâmetro e no banco de dados tenha mais de uma pessoa com aquele nome a resposta será assim:

```json
{
  "result": [
    {
      "nome": "João",
      "data_de_nascimento": "09/04/1991",
      "url_de_consulta_endereco": "/api/pessoa/consulta/endereco?id=1"
    },
    {
      "nome": "João",
      "data_de_nascimento": "09/04/1995",
      "url_de_consulta_endereco": "/api/pessoa/consulta/endereco?id=2"
    }
  ]
}
```

Se não, a resposta será assim:

```json
{
  "result": {
    "nome": "João",
    "data_de_nascimento": "09/04/1991",
    "url_de_consulta_endereco": "/api/pessoa/consulta/endereco?id=1"
  }
}
```

- `GET /api/pessoa/consulta/endereco`

**Parâmetros**

|   Nome |        Requerido         |  Tipo  | Descrição       |
|-------:|:------------------------:|:------:|-----------------|
| `nome` |  opcional caso use `id`  | string | Nome da Pessoa. |
|   `id` | opcional caso use `nome` |  int   | ID da Pessoa    |

Resposta da requisição HTTP GET

Caso usando `nome` como parâmetro e no banco de dados tenha mais de uma pessoa com aquele nome a resposta será assim:

```json
{
  "result": [
    {
      "nome": "João",
      "enderecos": [
        {
          "logradouro": "Vila A",
          "cep": 12345678,
          "numero": 230,
          "cidade": "São Paulo",
          "endereco_principal": true
        },
        {
          "logradouro": "Vila B",
          "cep": 12345678,
          "numero": 230,
          "cidade": "São Paulo",
          "endereco_principal": false
        }
      ]
    },
    {
      "nome": "João",
      "enderecos": [
        {
          "logradouro": "Vila B",
          "cep": 12345678,
          "numero": 230,
          "cidade": "São Paulo",
          "endereco_principal": false
        },
        {
          "logradouro": "Vila C",
          "cep": 12345678,
          "numero": 230,
          "cidade": "São Paulo",
          "endereco_principal": true
        }
      ]
    }
  ]
}
```

Se não, a resposta será assim:

```json
{
  "result": {
    "nome": "João",
    "enderecos": [
      {
        "logradouro": "Vila A",
        "cep": 12345678,
        "numero": 230,
        "cidade": "São Paulo",
        "endereco_principal": true
      },
      {
        "logradouro": "Vila B",
        "cep": 12345678,
        "numero": 230,
        "cidade": "São Paulo",
        "endereco_principal": false
      }
    ]
  }
}
```

- `POST /api/pessoa`

Utilizando JSON Body para a requisição para criar uma Pessoa, os endereços são opcionais nesta endpoint.

```json
{
    "nome": "João",
    "data_de_nascimento": "09/04/1991",
    "enderecos": [
        {
            "logradouro": "Vila D'ouro",
            "cep": 12345678,
            "numero": 230,
            "cidade": "São Paulo",
            "endereco_principal": true
        },
        {
            "logradouro": "Vila D'ouro 2",
            "cep": 12345678,
            "numero": 230,
            "cidade": "São Paulo 2",
            "endereco_principal": false
        }
    ]
}
```

Resposta da requisição HTTP POST

```json
{
  "nome": "João",
  "data_de_nascimento": "09/04/1991",
  "url_de_consulta_endereco": "/api/pessoa/consulta/endereco?id=1"
}
```

- `PATCH /api/pessoa/{id}`

Por ser um mapeamento PATCH, não é necessario preencher todos os campos, apenas preencha o que vai ser editado.

```json
{
  "nome": "Não é mais João",
  "data_de_nascimento": "09/04/1991",
  "enderecos": [
    {
      "logradouro": "Apenas 1 endereço",
      "cep": 12345678,
      "numero": 230,
      "cidade": "São Paulo",
      "endereco_principal": true
    }
  ]
}
```
Resposta da requisição HTTP PATCH

```json
{
  "nome": "Não é mais João",
  "data_de_nascimento": "09/04/1991",
  "url_de_consulta_endereco": "/api/pessoa/consulta/endereco?id=1"
}
```

- `DELETE /api/pessoa/{id}`

#### Endpoints de Endereço

- `GET /api/endereco/consulta`

**Parâmetros**

|   Nome |        Requerido         |  Tipo  | Descrição       |
|-------:|:------------------------:|:------:|-----------------|
| `nome` |  opcional caso use `id`  | string | Nome da Pessoa. |
|   `id` | opcional caso use `nome` |  int   | ID da Pessoa    |

Resposta da requisição HTTP GET

Caso usando `nome` como parâmetro e no banco de dados tenha mais de uma pessoa com aquele nome a resposta será assim:
```json
{
  "result": [
    {
      "nome": "João",
      "identificador": 1,
      "enderecos": [
        {
          "logradouro": "Vila D'ouro",
          "cep": 12345678,
          "numero": 230,
          "cidade": "São Paulo",
          "endereco_principal": true,
          "identificador": 1
        }
      ]
    },
    {
      "nome": "João",
      "identificador": 1,
      "enderecos": [
        {
          "logradouro": "Vila D'ouro",
          "cep": 12345678,
          "numero": 230,
          "cidade": "São Paulo",
          "endereco_principal": false,
          "identificador": 2
        }
      ]
    },
    {
      "nome": "João",
      "identificador": 2,
      "enderecos": [
        {
          "logradouro": "Vila D'ouro",
          "cep": 12345678,
          "numero": 230,
          "cidade": "São Paulo",
          "endereco_principal": true,
          "identificador": 3
        }
      ]
    },
    {
      "nome": "João",
      "identificador": 2,
      "enderecos": [
        {
          "logradouro": "Vila D'ouro",
          "cep": 12345678,
          "numero": 230,
          "cidade": "São Paulo",
          "endereco_principal": false,
          "identificador": 4
        }
      ]
    }
  ]
}
```