# DimeTracker

[![NPM](https://img.shields.io/npm/l/react)](https://github.com/DeivinsonPereira/GerenciamentoDespesas/blob/main/LICENSE)

Esse projeto foi construído com o objetivo principal de ser um aplicativo web que permita aos usuários controlar e monitorar suas despesas diárias, para poder ajudar os usuários a terem um melhor controle sobre suas finanças, categorizando e analisando suas despesas.

**obs: não será feito o processo de autenticação e login nesse projeto afim de facilitar a observação das funcionalidades**


### Dentre as funcionalidades implementadas estão:
- **Filtro de pesquisa:** <br> **Esse filtro traz as informações de forma paginada.**<br> <br>
  1 - Se o usuário **NÃO INFORMAR** nenhum dado no filtro a pesquisa retorna todas as despesas cadastradas de forma paginada.<br>
  2 - Se o usuário **INFORMAR** apenas a categoria da despesa, retorna todas as despesas relacionadas a essa categoria.<br>
  3 - Se o usuário **INFORMAR** a categoria e um intervalo de datas, retornará todas as despesas relacionadas a categoria escolhida e que estejam dentro da data solicitada. <br>
  4 - Se o usuário **NÃO INFORMAR** a categoria mas, **INFORMAR** a data, retornará todas as despesas de todas as categorias presentes no intervalo informado.<br> <br>
 - **Filtrar Valor Total das Despesas** <br> <br>
  5 - Se o usuário **NÃO INFORMAR** nenhum dado, retornará o valor total das despesas somadas de todas as categorias e em todas as datas.<br>
  6 - Se o usuário **INFORMAR** apenas a categoria da despesa, retorna o valor total das despesas somadas da categoria informada.<br>
  7 - Se o usuário **INFORMAR** a categoria e um intervalo de datas, retorna o valor total das despesas somadas da categoria informada e que estejam dentro da data solicitada.<br>
  8 - Se o usuário **NÃO INFORMAR** a categoria mas, **INFORMAR** a data, retorna o valor total das despesas de todas as categorias presentes no intervalo informado.<br>  

  Também foi criado o método de **INSERIR CATEGORIA E INSERIR DESPESA**, para que o usuário possa inserir as despesas que ele tem no dia a dia e também possa criar a categoria adequada para o tipo de despesa.

  Além disso também foram criados os métodos para **ATUALIZAR DESPESA E ATUALIZAR CATEGORIA** para que o usuário possa corrigir alguma informação que foi digitado errado como: data, nome ou valor. (todas essas informações podem ser alteradas juntas ou individuais).

  Também foram criados os métodos para **DELETAR CATEGORIA E DELETAR DESPESA** para que o usuário possa deletar as despesas em caso de uma informação duplicada, ou uma categoria que não faça sentido. (também foi inserido a lógica para que ao deletar uma categoria, todas as despesas pertencentes a ela sejam deletadas também mantendo assim a obrigação em ter uma categoria cadastrada em cada despesa).

  Também foi implementado o método para buscar as categorias de forma paginada.
  
  Nesse projeto também foi corrigido o problema de N+1 consultas afim de melhorar o desempenho do mesmo.

  ### Abaixo, as fotos mostram o backend em funcionamento

  
## Modelo de Domínio

Foi utilizado esse modelo de domínio para a criação das classes.

![Modelo de Dominio](https://github.com/DeivinsonPereira/GerenciamentoDespesas/blob/main/assets/modelo-dominio-gerenciamento-gastos.png)

## Banco de Dados preenchido

para testes foi utilizado o banco de dados H2 e depois os dados foram homologados no banco de dados Postgresql via Docker.

![banco de dados](https://github.com/DeivinsonPereira/GerenciamentoDespesas/blob/main/assets/Postgres.png)

## Busca de Despesas Paginadas

Essa imagem mostra a execução do teste da api onde captura os clientes e mostra de forma paginada.

![Despesas paginados](https://github.com/DeivinsonPereira/GerenciamentoDespesas/blob/main/assets/Despesas%20paginado.png)

## Busca por Categorias Paginadas

Esse método busca todos os elementos da categoria no banco de dados.

![Categorias Paginadas](https://github.com/DeivinsonPereira/GerenciamentoDespesas/blob/main/assets/Categorias%20paginado.png)


## Inserindo nova despesa

Este método é usado para inserir novas "despesas" dentro do banco de dados já atreladas às suas respectivas categorias.

![Inserir Despesa](https://github.com/DeivinsonPereira/GerenciamentoDespesas/blob/main/assets/Inserir%20Despesa.png)

## Testes Unitários

Imagem mostrando alguns testes Unitários.

![Inserir Despesa](https://github.com/DeivinsonPereira/GerenciamentoDespesas/blob/main/assets/Testes.jpg)

## JaCoCo

Imagem mostrando o Coverage (Cobertura) de código com testes.

![Inserir Despesa](https://github.com/DeivinsonPereira/GerenciamentoDespesas/blob/main/assets/JaCoCoFim.png)

# Tecnologias utilizadas

## Técnologias Backend

- Java 17
- Spring Boot
- Spring Data JPA / Hibernate
- Spring Web
- Maven
- Postgresql
- JUnit
- Mockito
- H2 Database
- Orientação a objetos
- Docker
- Lombok
- JaCoCo

## Técnicas utilizadas

- Padrão camadas
- RESTful api


# Como executar o projeto

## Pré-requisitos
- Docker instalado e aberto
- Git instalado

``` git bash
# clonar repositório
git clone https://github.com/DeivinsonPereira/GerenciamentoDespesas.git

# Abrir Power Shell (se houver erro, acesse como administrador)

# entrar na pasta do projeto (o caminho para onde o projeto foi baixado)
cd clientecrud

# executar o projeto
docker-compose up -d

```
# Autor

Deivinson Robes Pereira

https://www.linkedin.com/in/deivinsonpereira/
