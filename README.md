
# Avaliação Java Back-end

Esta API foi desenvolvida em Java Spring Boot para o teste prático.



## Como Usar

### Passo 1

Baixe o projeto ou clone, importe o projeto Maven em uma IDE e execute como aplicativo a classe '[TestePraticoApplication .java](https://github.com/jawwadbr/TestePraticoAPI/blob/main/src/main/java/com/jawbr/testepratico/TestePraticoApplication.java)'

Você vai ver no console algo assim: 
![](https://i.imgur.com/Kv7uDed.png "example")

Se você ver isso, significa que está no caminho certo.

Obs: Caso queira usar o Endpoint do banco de dados H2 "/h2-console" não esqueça de verificar no console a url jdbc. Pois nas mais novas versões não é possível modificar a url pelas properties.

### Passo 2

Caso o projeto esteja rodando sem problemas, é nesse momento que vamos começar utilizar as Endpoints e testar o projeto. E não se preocupe irei dar exemplos para cada uma delas.

#### Todas Endpoints
Começando pelas Endpoint de GET  

- ```"/api/pessoas"```- Listar pessoas
- ```"/api/pessoas/{pessoaId}"``` - Consultar Pessoa usando seu Id e também listar endereços da pessoa
- ```"/api/pessoas/nome/{nomePessoa}"``` - Consultar Pessoa usando seu Nome completo. Obs: Endpoint é sensível a maiúsculas e minúsculas

Endpoint PUT  

- ```"/api/pessoas"``` - Editar uma Pessoa
- ```"/api/pessoas/{pessoaId}"``` -  Criar Endereço para uma Pessoa
- ```"/api/pessoas/{pessoaId}/endereco/{enderecoId}"``` - Informar qual Endereço principal da Pessoa

Endpoint POST  

- ```"/api/pessoas"``` - Criar uma Pessoa

Endpoint DELETE  

- ```"/api/pessoas/{pessoaId}"``` - Deletar uma Pessoa | Este Endpoint não tinha no teste mas coloquei para testes.

#### Usando as Endpoints

Veja os vídeos de demonstração para cada uso de Endpoint.

[Clique na Imagem para abrir o video no youtube. Ou nesse texto mesmo.](https://www.youtube.com/watch?v=sOkYpMelZ18 "link")
[![Test](https://i.imgur.com/vKtjKxd.png)](https://www.youtube.com/watch?v=sOkYpMelZ18 "Test")

Endpoints usadas no vídeo.  

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

Atenção: Não esqueça de usar a opção Body > Raw > JSON ao criar algo na API.
