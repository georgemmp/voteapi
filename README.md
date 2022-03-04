# Teste softdesign

O challenge foi feito em Java 11 utilizando spring boot 2.6.4, maven, mongodb, webflux e kafka.

### Observação
Não foi possível realizar os testes e publicação na nuvem, com isso o projeto está sendo executado localmente.

### Instalação de dependências
Antes de rodar o projeto instale as dependências através da IDE ou por linha de comando.
```sh
$ mvn install
```

### Swagger
Ao executar o projeto acesse do seu navegador no seguinte endereço <host>/swagger-ui.html, com isso é possível acessar a documentação da API.

### Execução da API
Primeiro foi criado uma rota para cadastrar os associados, facilitando o controle dos votos por pautas.
```sh
POST - <host>/api/v1/associate
{
	"name": "Test",
	"cpf": "73905365006"
}
```
Rota para cadastro de pautas
```sh
POST - <host>/api/v1/guideline
{
	"description": "Nova pauta"
}
```
Rota para abertura de sessão conforme a pauta enformada.
```sh
PATCH - <host>/api/v1/guideline/<id>/session
```
Rota para votação conforme a pauta.
```sh
POST <host>/api/v1/guideline/<id>/vote
{
	"cpf": "24295661090",
	"vote": "Sim"
}
```
O fechamento da sessão está sendo feito através de um scheduler, que verifica o tempo da sessão desde o tempo em que está aberto com o tempo atual, numa diferença padrão de 1 min.
