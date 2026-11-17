# Instituto Mão Amiga – Projeto N2

O projeto consiste em um aplicativo desenvolvido com Expo e React Native, integrado a uma API construída com Spring Boot, localizada na pasta `backend/`.

As informações referentes aos pontos de coleta e às doações são armazenadas no servidor. Já os pontos marcados como favoritos são salvos localmente no dispositivo, utilizando o AsyncStorage.

## Como iniciar o projeto

Para executar a aplicação, é necessário ter o Node.js, npm e Java 25 instalados, conforme a versão configurada no arquivo `backend/pom.xml`.

Execute os seguintes comandos:

```bash
npm install
cp .env.example .env
npm run backend
npx expo start
```

No arquivo `.env`, configure o endereço da API de acordo com o ambiente utilizado:

- **Navegador/iOS:** `http://localhost:8080/api/v1`
- **Emulador Android:** `http://10.0.2.2:8080/api/v1`
- **Celular físico:** `http://IP_DO_COMPUTADOR:8080/api/v1`

A API estará disponível na porta `8080`.

Para utilizar o aplicativo em um celular físico, é necessário que o computador e o aparelho estejam conectados à mesma rede Wi-Fi. Também é importante verificar se a porta `8080` está liberada para comunicação.

A alteração do endereço da API deve ser realizada exclusivamente pela variável `EXPO_PUBLIC_API_URL`, dentro do arquivo `.env`.

## Endpoints da API

A documentação e os testes dos serviços podem ser acessados pelo Swagger:

`http://localhost:8080/swagger-ui/index.html`

### Gerenciamento dos pontos de coleta

A API disponibiliza as seguintes operações para administrar os pontos:

- `GET /api/v1/pontos/filtro?filtro=&page=0&size=10` – Consultar os pontos com possibilidade de pesquisa e paginação.
- `GET /api/v1/pontos/{id}` – Consultar um ponto específico pelo seu identificador.
- `POST /api/v1/pontos` – Registrar um novo ponto.
- `PUT /api/v1/pontos/{id}` – Atualizar os dados de um ponto existente.
- `DELETE /api/v1/pontos/{id}` – Remover um ponto cadastrado.

Para registrar um ponto, devem ser informados os campos `nome`, `endereco`, `bairro`, `horario` e `tipo`. O campo `tipo` aceita duas opções: `coleta` ou `distribuição`.

O sistema permite pesquisar os pontos utilizando o nome ou o bairro. Os resultados são organizados em duas partes: `content`, que apresenta os registros encontrados, e `page`, que contém os dados de paginação.

### Gerenciamento das doações

Para trabalhar com as doações, estão disponíveis os seguintes endpoints:

- `GET /api/v1/doacoes/filtro?filtro=&page=0&size=10` – Listar doações com suporte à pesquisa e paginação.
- `GET /api/v1/doacoes/{id}` – Consultar uma doação específica.
- `POST /api/v1/doacoes` – Cadastrar uma nova doação.
- `PUT /api/v1/doacoes/{id}` – Modificar uma doação registrada.
- `DELETE /api/v1/doacoes/{id}` – Excluir uma doação.

O cadastro de uma doação exige os campos `tipoItem`, `quantidade` e `pontoDestino`. A quantidade precisa ser maior que zero, enquanto o ponto de destino deve ser informado pelo nome do ponto cadastrado.

A data de criação, representada pelo campo `criadoEm`, é gerada automaticamente pela API e permanece inalterada mesmo quando uma doação é editada.

Os registros utilizam identificadores no formato UUID. Para armazenar os dados, o projeto utiliza o banco H2, que mantém as informações em arquivo no caminho `backend/data/maoamiga`.

## Exemplos de consultas e filtros

É possível realizar diferentes consultas para visualizar os pontos cadastrados:

```bash
/api/v1/pontos/filtro?filtro=&page=0&size=10

/api/v1/pontos/filtro?filtro=Bueno&page=0&size=10

/api/v1/pontos/filtro?filtro=&page=1&size=10
```

Essas requisições permitem visualizar a primeira página sem pesquisa, procurar pontos localizados em determinado bairro e acessar a página seguinte dos resultados.

As respostas seguem uma estrutura padronizada, contendo os registros em `content` e as informações de paginação em `page`.

Exemplo de retorno:

```json
{
  "content": [
    {
      "id": "uuid-do-ponto",
      "nome": "Instituto Recomeçar",
      "endereco": "Rua 1, 100",
      "bairro": "Setor Bueno",
      "horario": "Seg a Sex, 8h às 17h",
      "tipo": "coleta"
    }
  ],
  "page": {
    "size": 10,
    "number": 0,
    "totalElements": 25,
    "totalPages": 3
  }
}
```

A pesquisa por bairro não diferencia letras maiúsculas de minúsculas, facilitando a localização dos registros.

Também é possível personalizar a ordem de exibição. Por exemplo:

```bash
/api/v1/pontos/filtro?filtro=&page=0&size=10&sort=bairro,asc
```

Nesse caso, os pontos serão organizados em ordem alfabética de acordo com o bairro.

### Consultas de doações

As doações possuem um sistema de pesquisa semelhante:

```bash
/api/v1/doacoes/filtro?filtro=&page=0&size=10

/api/v1/doacoes/filtro?filtro=roupa&page=0&size=10

/api/v1/doacoes/filtro?filtro=&page=1&size=10
```

Por padrão, os registros são apresentados considerando a data de criação, começando pelas doações mais recentes.

O Swagger também pode ser utilizado para verificar o comportamento da API em diferentes situações, incluindo:

- **Erro 400:** ocorre quando algum campo obrigatório não é preenchido corretamente ou quando a quantidade informada é inválida.
- **Erro 404:** acontece quando uma operação tenta acessar um registro que não existe.

## Registros de evidências no Swagger

Durante a entrega do projeto, devem ser incluídas capturas de tela que comprovem o funcionamento dos endpoints. Os testes precisam ser realizados pela interface Swagger, disponível em `http://localhost:8080/swagger-ui/index.html`.

As evidências necessárias são:

**Pontos de coleta:**
- Consulta geral (`GET`).
- Busca de um ponto pelo ID (`GET`).
- Cadastro de um novo ponto (`POST`).
- Atualização de um ponto (`PUT`).
- Exclusão de um ponto (`DELETE`).
- Demonstração de uma resposta de erro `400`.
- Demonstração de uma resposta de erro `404`.

**Pesquisa e paginação dos pontos:**
- Consulta sem utilizar filtros.
- Pesquisa utilizando o nome de um bairro.
- Consulta da segunda página de resultados.

**Doações:**
- Consulta utilizando filtros (`GET`).
- Busca de uma doação pelo identificador (`GET`).
- Cadastro de uma doação (`POST`).
- Edição de uma doação (`PUT`).
- Exclusão de uma doação (`DELETE`).
- Teste de validação com retorno `400`.
- Teste de recurso inexistente com retorno `404`.

As capturas devem apresentar claramente o endereço da requisição, os parâmetros utilizados e o conteúdo retornado pela API.

## Etapas para apresentação do aplicativo

A demonstração do projeto deve seguir uma sequência que permita visualizar as principais funcionalidades implementadas:

1. **Pesquisa de pontos:** realizar uma busca utilizando o nome ou o bairro de um ponto de coleta e acessar sua página de detalhes.

2. **Cadastro e gerenciamento:** demonstrar como adicionar um novo ponto, alterar suas informações e realizar sua exclusão.

3. **Sistema de favoritos:** selecionar um ponto como favorito, acessar a seção **Meus pontos** e comprovar que a informação continua salva mesmo após sair e retornar ao aplicativo.

4. **Controle de doações:** registrar uma nova doação, realizar alterações nos dados cadastrados e, por fim, excluí-la por meio do histórico.

5. **Testes da API:** utilizar o Swagger para apresentar uma situação de erro `400`, relacionada à validação dos dados, e outra de erro `404`, utilizando um UUID que não corresponda a nenhum registro existente.

Essas etapas permitem demonstrar a integração entre o aplicativo React Native e o backend Spring Boot, além do funcionamento das operações de cadastro, consulta, atualização, exclusão, pesquisa, paginação e persistência dos dados.
