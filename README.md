
# Avaliação Java Back-end

Esta API foi desenvolvida em Java Spring Boot para o teste prático.


## Como Usar

### Passo 1

Baixe o projeto ou clone, importe o projeto Maven em uma IDE e execute como aplicativo a classe '[TestePraticoApplication .java](https://github.com/jawwadbr/TestePraticoAPI/blob/main/src/main/java/com/jawbr/testepratico/ApiPessoaApplication.java)'

É possível também iniciar o projeto navegando até o projeto com o comando:

`cd <project-name>`

Construa o projeto:

`mvn clean install`

Execute com o comando:

`mvn spring-boot:run`

A API vai ficar disponível em `http://localhost:8080/api/**`.

Você vai ver no console algo assim: 
![](https://i.imgur.com/Kv7uDed.png "example")

Se você ver isso, significa que está no caminho certo.

### Passo 2

Caso o projeto esteja rodando sem problemas, é nesse momento que vamos começar utilizar as Endpoints e testar o projeto. E não se preocupe irei dar exemplos para cada uma delas.

#### Todas Endpoints
Começando pelas Endpoint de GET  

- ```"/pessoas"```- Listar pessoas
- ```"/pessoas/{pessoaId}"``` - Consultar Pessoa usando seu Id e também listar endereços da pessoa

Endpoint POST  

- ```"/pessoas"``` - Criar uma Pessoa por RequestBody

Endpoint PUT  

- ```"/pessoas"``` - Editar uma Pessoa
- ```"/endereco/{pessoaId}"``` - Criar um Endereço para uma Pessoa por RequestBody
- ```"/endereco/{pessoaId}/{enderecoId}"``` - Informar qual o Endreço principal da Pessoa

Endpoint DELETE  

- ```"/pessoas/{pessoaId}"``` - Deletar uma Pessoa | Este Endpoint não tinha no teste mas coloquei para testes.

#### Usando as Endpoints

O vídeo de demonstração é para uma versão antiga, mas os JSON ainda podem ser usados.

[Clique na Imagem para abrir o video no youtube. Ou nesse texto mesmo.](https://www.youtube.com/watch?v=sOkYpMelZ18 "link")
[![Test](https://i.imgur.com/vKtjKxd.png)](https://www.youtube.com/watch?v=sOkYpMelZ18 "Test")

Endpoints usadas no vídeo da versão antiga.  

- POST ```http://localhost:8080/api/pessoas  ```
```
{
    "nome": "Bradley Jawwad",
    "dataDeNascimento": "04/09/1998"
}
```
- GET ```http://localhost:8080/api/pessoas  ```
- GET ```http://localhost:8080/api/pessoas/5  ```
- PUT ```http://localhost:8080/api/pessoas  ```
```
{
    "id": 5,
    "nome": "Bradley Jawwad",
    "dataDeNascimento": "20/09/1958"
}
```
- PUT ```http://localhost:8080/api/pessoas/5  ```
```
{
    "logradouro": "Vila D'ouro",
    "cep": 12345678,
    "numero": 230,
    "cidade": "São Paulo",
    "enderecoPrincipal": false
}
```
- PUT ```http://localhost:8080/api/pessoas/5/endereco/6```

