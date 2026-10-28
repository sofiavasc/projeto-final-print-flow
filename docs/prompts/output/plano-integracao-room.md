# Plano de Integracao do Banco de Dados com Room

## Objetivo

Integrar o Room ao aplicativo Print-Flow sem alterar o escopo definido no PRD e no plano geral. Ao final, login, cliente, servico, orcamento e pedido devem estar em um banco local persistente, e as telas devem consumir dados reais em vez de dados simulados.

Este plano segue o pacote atual:

`app/src/main/java/br/edu/ifpe/projeto_final_print_flow`

A implementacao deve preservar `minSdk = 24`, `targetSdk = 36`, Compose, Navigation Compose, Room 2.6.1 e a navegacao existente.

## Base atual identificada

- Os modelos Kotlin ja existem em `data/Entities.kt`, mas nao sao entidades Room.
- O banco nao possui DAOs, `RoomDatabase` ou repository.
- As listas e detalhes usam dados simulados internamente.
- A tela de login ainda navega sem validar os dados informados.
- A tela de novo orcamento ainda usa estado local e nao salva.
- `app/build.gradle.kts` declara Room, mas usa `annotationProcessor`. Para Kotlin, o projeto deve usar KSP para gerar o codigo Room.
- As entidades seguem os nomes definidos pelo projeto. O campo existente do servico, `precoUnitario`, e o campo `unidade` devem ser mantidos, pois acompanham `AGENTS.md` e `Entities.kt`.

## Estrutura final de arquivos

A integracao deve ficar organizada assim:

```text
app/src/main/java/br/edu/ifpe/projeto_final_print_flow/data/
├── AppDatabase.kt
├── Entities.kt
├── ClienteDao.kt
├── ServicoDao.kt
├── OrcamentoDao.kt
├── PedidoDao.kt
├── LoginDao.kt
└── repository/
    ├── PrintFlowRepository.kt
    └── PrintFlowRepositoryImpl.kt

app/src/main/java/br/edu/ifpe/projeto_final_print_flow/ui/
└── screens/
    ├── login/
    ├── home/
    ├── budget/
    └── details/
```

Cada arquivo novo ou alterado deve ter um comentario de fronteira escrito pelo grupo, conforme o RNF06.

## Fase 1 — Configuracao do Room

### 1.1 Configurar KSP

Adicionar o plugin KSP na catalogacao de plugins e ativar `ksp` no modulo `app`.

Substituir:

```kotlin
annotationProcessor(libs.androidx.room.compiler)
```

por:

```kotlin
ksp(libs.androidx.room.compiler)
```

Nao adicionar Retrofit nesta etapa. A rede nao sera usada pelo banco.

### 1.2 Adicionar modelos Room

Transformar os cinco data classes de `Entities.kt` em entidades anotadas com `@Entity`.

| Entidade | Tabela | Chave primaria |
|---|---|---|
| `Cliente` | `clientes` | `id`, autogerado |
| `Servico` | `servicos` | `id`, autogerado |
| `Orcamento` | `orcamentos` | `id`, autogerado |
| `Pedido` | `pedidos` | `id`, autogerado |
| `Login` | `logins` | `id`, autogerado |

Regras dos campos:

- `id`: `Long = 0`.
- Cliente: `nome`, `telefone` e `email` obrigatorios.
- Servico: `nome`, `descricao`, `precoUnitario` e `unidade` obrigatorios, conforme o modelo atual.
- Orcamento: `cliente`, `servico`, `quantidade`, `caracteristicas`, `valor` e `status` obrigatorios.
- Pedido: `orcamentoId`, `data` e `status` obrigatorios.
- Login: `usuario`, `email` e `senha` obrigatorios.

A classe `Pedido` deve manter o relacionamento logico com `Orcamento` pelo campo `orcamentoId`. Como pedido/Orcamento deve representar o mesmo fluxo sem duplicar dados, criar o pedido apenas na aprovacao e manter o ID do orcamento salvo nele.

## Fase 2 — DAOs

Criar um DAO para cada entidade.

Operacoes:

- Cliente: inserir, atualizar, excluir, listar e buscar por ID.
- Servico: inserir, atualizar, excluir, listar e buscar por ID.
- Orcamento: inserir, atualizar, listar e buscar por ID.
- Pedido: inserir, atualizar, listar e buscar por ID.
- Login: inserir, atualizar e consultar usuario/email/senha para autenticar.

Os metodos de consulta devem retornar `Flow` ou `LiveData`, permitindo que Compose observe alteracoes sem recarregar manualmente a interface.

Nao remover orcamentos no DAO. Essa operacao nao esta marcada como necessaria pelo PRD.

Nao excluir pedidos no DAO. Essa operacao tambem nao esta marcada como necessaria pelo PRD.

Consultas esperadas para a interface:

```kotlin
fun observarTodos(): Flow<List<Orcamento>>
fun buscarPorId(id: Long): Flow<Orcamento?>
fun buscarPorClienteOuServico(texto: String): Flow<List<Orcamento>>
```

As consultas de busca devem comparar cliente e servico, mantendo o filtro usado pela tela Home.

## Fase 3 — Database e repository

Criar `PrintFlowDatabase`, com as cinco entidades e acesso aos cinco DAOs.

A configuracao deve ser unica por processso, utilizando `Room.databaseBuilder` ou `Room.inMemoryDatabaseBuilder` apenas em testes.

Criar um repository para centralizar a logica de banco e separar as telas de detalhes do Room:

- as telas chamam o repository;
- o repository chama os DAOs;
- operacoes que podem falhar sao executadas dentro de `try/catch` ou retornam `Result<T>`;
- o banco nao deve encerrar a tela nem o aplicativo em caso de erro.

Usar uma instancia sob controle da aplicacao ou provisionada para as telas. Nao criar uma nova instancia do banco em cada composable.

## Fase 4 — Integracao por tela

### Login

- Criar um registro de login persistido ao cadastrar o usuario.
- Para esta versao, validar os tres campos: usuario, email e senha.
- A senha deve continuar mascarada com botao de visibilidade.
- Comparar usuario, email e senha usando o `LoginDao`.
- Entrada incorreta: mostrar erro por recurso de `strings.xml` e permanecer na tela Login.
- Entrada correta: navegar para Home.

Como o projeto nao possui tela de cadastro definida, nao criar um fluxo novo. Para permitir o teste inicial, inserir um usuario somente se o banco estiver vazio por meio do repository ou de um mecanismo de inicializacao controlado. Os dados de inicializacao devem ficar separados da logica visual.

### Home

- Substituir `mockOrcamentos` por uma coleta do Flow do repository.
- Mostrar cliente, servico, valor e status.
- Manter busca por cliente ou servico.
- Se a lista estiver vazia, manter a mensagem definida pelo projeto.
- Abrir o detalhe passando o ID real do orcamento pela navegacao existente.

### Novo Orcamento

- Validar cliente, telefone, email, servico, quantidade, caracteristicas e preco antes de salvar.
- Campos obrigatorios vazios: destacar os campos e usar `strings.xml`.
- Salvar `Cliente` somente quando o preenchimento incluir dados de cliente.
- Calcular o valor:

```text
valor total = preco unitario do servico * quantidade
```

- Persistir o `Servico` cadastrado ou selecionado conforme os dados preenchidos.
- Persistir o `Orcamento` com status inicial `PENDENTE`.
- Apos salvar, navegar para Home ou Detalhe conforme a navegacao atual.
- Em falha de banco, manter todos os campos digitados e mostrar "Nao foi possivel realizar esta operacao. Tente novamente.".

### Detalhes

- Buscar o orcamento real pelo ID recebido da rota.
- Mostrar todos os dados persistidos e o status atual.
- Ao aprovar: atualizar o status do orcamento para `APROVADO` e criar um `Pedido` vinculado ao `orcamentoId` com status inicial definido pelo produto, por exemplo `EM PRODUCAO`.
- Evitar criar pedidos duplicados na aprovacao. Antes de inserir, verificar se ja existe pedido para aquele `orcamentoId`.
- Atualizar o status do pedido a partir do detalhe.
- Os status visiveis devem refletir as mesmas constantes em todas as telas.

### WhatsApp

A integracao acontece depois da persistencia estar funcionando:

- usar o telefone informado no cliente;
- montar uma mensagem com nome do cliente, nome do servico, quantidade, caracteristicas e valor;
- abrir `https://wa.me/<numero>?text=<mensagem>` com `Intent.ACTION_VIEW`;
- tratar WhatsApp ausente ou erro de abertura mantendo a tela e os dados;
- mostrar a falha com mensagem de `strings.xml`.

O modo aviao nao interrompe as operacoes locais de Room. Falha de banco continua sendo tratada separadamente de falha ao abrir WhatsApp.

## Fase 5 — Persistencia e consistencia

- Habilitar migrations a partir da versao inicial se houver alteracao futura de schema.
- Na primeira entrega, usar `fallbackToDestructiveMigration` somente se o grupo concluir que nao existem dados de usuario em ambiente de desenvolvimento.
- Nunca permitir migrations silenciosas que percam dados sem justificativa documentada.
- Consultar o banco apos fechar e abrir o app na verificacao de RNF08.
- Manter a integridade do ID entre a navegacao, o `OrcamentoDao` e a tela Detalhes.

## Fase 6 — Arquitetura de estado recomendada

Para cada tela que consumir banco, usar `ViewModel` e `StateFlow` sempre que isso estiver adequado ao nivel atual da equipe:

```text
Tela Compose -> ViewModel -> Repository -> DAO -> Room
```

O ViewModel deve tratar:

- carregamento;
- sucesso;
- lista vazia;
- erro de banco;
- feedback da operacao atual;
- estado dos campos digitados;
- status aprovado ou atualizado.

Esta separacao reduz duplicacao e permite que Compose observe dados atualizados pelo banco.

## Fase 7 — Testes

### Testes unitarios

Validar:

- calculo de valor do orcamento;
- campos obrigatorios;
- status inicial;
- aprovacao sem duplicar pedido;
- atualizacao de status;
- busca por cliente;
- busca por servico.

### Testes instrumentados de Room

Cobrir:

- insercao e leitura de Cliente;
- insercao e leitura de Servico;
- insercao e leitura de Orcamento;
- atualizacao de Orcamento;
- insercao e leitura de Pedido;
- atualizacao de status de Pedido;
- login valido e invalido;
- persistencia apos fechar e abrir o app;
- banco vazio;
- alteracoes refletidas pelo Flow.

## Criterios de aceite do banco

1. Room abre sem crash e cria o banco na primeira execucao.
2. Lista Home continua vazia com mensagem clara quando nao existem registros.
3. Criar orcamento salva cliente, servico e orcamento localmente.
4. Valor e status aparecem corretamente na lista e nos detalhes.
5. Login valida usuario, email e senha antes da navegacao.
6. Aprovar cria um pedido uma unica vez e vincula ao orcamento correto.
7. Atualizar o status muda a lista e os detalhes.
8. Fechar e reabrir o app preserva login, orcamentos e pedidos.
9. Erro de banco mantem a tela ativa e mostra feedback.
10. Todas as mensagens visiveis novas ficam em `strings.xml`.
11. Todo arquivo criado ou alterado possui comentario de fronteira.
12. Nenhuma funcionalidade fora do escopo foi adicionada.

## Ordem de implementacao recomendada

1. KSP e Room Compiler.
2. Entidades em `Entities.kt`.
3. DAOs.
4. `PrintFlowDatabase`.
5. Repository.
6. Login persistido.
7. Salvamento e listagem de Orcamentos.
8. Detalhes e aprovacao para Pedido.
9. Atualizacao de status.
10. WhatsApp.
11. Testes de Room, persistencia e fluxo completo.
12. Revisao de `strings.xml`, comentarios de fronteira e navegacao.
