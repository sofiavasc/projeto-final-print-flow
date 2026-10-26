# Plano de Atividades do Projeto Print-Flow

## Introdução

Este plano organiza o desenvolvimento do aplicativo Print-Flow, com foco em entregas incrementais, qualidade de código e alinhamento com o PRD do projeto. O objetivo é transformar a base atual do app em uma solução funcional para pequenas gráficas, cobrindo criação e registro de orçamentos, envio pelo WhatsApp, acompanhamento de pedidos e atualização de status.

A estrutura atual do projeto já apresenta telas em Jetpack Compose, navegação e identidade visual definidas. Ainda falta consolidar a base de dados com Room, regras de negócio, validações, integração com WhatsApp e revisão final do fluxo completo.

---

## Escopo e objetivos

### Objetivo do produto
O Print-Flow deve permitir que uma gráfica:
- cadastre orçamentos;
- calcule valores;
- envie propostas por WhatsApp;
- registre pedidos aprovados;
- acompanhe o andamento de cada pedido.

### Público-alvo
- donos de pequenas gráficas;
- funcionários de atendimento;
- usuários que precisam registrar pedidos e acompanhar orçamentos diretamente pelo celular.

### Escopo principal
- autenticação com usuário, e-mail e senha;
- lista de orçamentos e pedidos;
- criação de novo orçamento;
- cálculo automático de valor;
- detalhamento de orçamento;
- envio de proposta por WhatsApp;
- aprovação e atualização de status;
- persistência local com Room;
- manutenção de dados após fechar/reabrir o app;
- tratamento de erros e telas vazias.

### Metas de entrega
- app funcional em Android;
- dados persistidos localmente;
- fluxo completo de criação, envio e acompanhamento;
- interface consistente com o protótipo visual;
- validações e uso de `strings.xml`;
- app estável, sem fechamento inesperado.

---

## Estrutura das atividades

### 1. Setup e arquitetura inicial
- revisar a estrutura atual do projeto;
- padronizar nomes e organização de pacotes;
- alinhar modelos com o PRD;
- ajustar regras de navegação e strings;
- preparar base para Room e lógica de negócio.

### 2. Fluxo de navegação
- conectar telas de login, home, orçamento e detalhes;
- validar passagem de parâmetros;
- garantir retorno adequado entre telas;
- manter consistência do estado da navegação.

### 3. Implementação das telas principais
- Login;
- Home;
- Novo Orçamento;
- Detalhes do Orçamento;
- Pedido/Status.

### 4. Persistência com Room
- entidades e DAOs;
- database local;
- repository;
- leitura e gravação de dados persistentes.

### 5. Regras de negócio e validações
- campos obrigatórios;
- cálculo do orçamento;
- atualização de status;
- aprovação de orçamento;
- tratamento de estados vazios e erro.

### 6. Whatsapp e comunicação externa
- montar mensagem automática;
- usar Intent para abrir o WhatsApp;
- tratar ausência do app;
- preservar dados em caso de falha.

### 7. Testes e revisão final
- testar fluxo completo;
- verificar persistência;
- revisar tratamento de erros;
- confirmar consistência da interface e da navegação.

---

## Planejamento por área

### Autenticação / Login
- campos de usuário, email e senha;
- esconder senha durante a digitação;
- validar dados antes do acesso;
- impedir acesso com dados inválidos;
- manter tela ativa com mensagem do erro.

### Cadastro e listagem de orçamentos
- coletar dados do cliente e do serviço;
- registrar orçamento com status inicial;
- exibir lista por cliente/serviço;
- permitir busca por texto;
- mostrar mensagem e estado vazio quando não houver registros.

### Criação do novo orçamento
- coletar informações do cliente, serviço, quantidade e características;
- validar dados obrigatórios;
- calcular total;
- salvar no banco local;
- voltar para a tela principal.

### Detalhes do orçamento
- exibir dados completos;
- mostrar valor total;
- mostrar status atual;
- permitir aprovação;
- permitir envio pelo WhatsApp.

### Atualização de status
- inicializar status do orçamento;
- permitir alterações conforme o fluxo do pedido;
- manter o status consistente entre orçamento e pedido;
- atualizar a visualização em lista e detalhes.

### Integração com WhatsApp
- montar mensagem com os dados do orçamento;
- abrir conversa diretamente no WhatsApp;
- tratar falhas caso o app não esteja instalado;
- manter a experiência estável sem encerrar o app.

### Navegação
- login → home;
- home → novo orçamento;
- home → detalhes;
- detalhes → voltar;
- novo orçamento → salvar → home.

### Persistência com Room
- salvar orçamentos e pedidos;
- consultar dados salvos;
- manter registros após reinicialização do app;
- garantir consistência de dados.

### Validação de dados e mensagens de erro
- campos vazios;
- banco sem registros;
- falha ao abrir WhatsApp;
- falha ao salvar/consultar dados;
- ações fora do fluxo esperado.

### Testes e revisão final
- validar login;
- validar criação e listagem;
- validar WhatsApp;
- validar aprovação e status;
- validar persistência ao reabrir app.

---

## Detalhamento por tela

### 1. Tela de Login
**Objetivo:** permitir acesso ao sistema após validação dos dados.

**Elementos necessários:**
- nome do app;
- campo de usuário/email;
- campo de senha com visibilidade toggle;
- botão de entrar;
- mensagens de erro.

**Ações do usuário:**
- digitar usuário, email e senha;
- alternar visibilidade da senha;
- tocar em Entrar.

**Navegação:**
- login válido → Home;
- login inválido → permanece na tela com mensagem.

**Status esperado:**
- estrutura funcional e validada;
- sem fechamento do app em erro.

**Requisitos e regras de negócio:**
- usuário deve informar usuário, email e senha;
- senha deve ficar mascarada;
- dados incorretos exibem erro sem encerrar a tela.

**Referência visual:**
- `docs/telas/tela1.png`

---

### 2. Tela Principal (Home)
**Objetivo:** centralizar a visão geral de orçamentos e pedidos.

**Elementos necessários:**
- TopAppBar;
- campo de busca;
- lista de cards;
- FAB para adicionar novo orçamento;
- badge de status;
- mensagem de lista vazia.

**Ações do usuário:**
- buscar por cliente ou serviço;
- abrir item da lista;
- criar novo orçamento.

**Navegação:**
- clique em FAB → Novo Orçamento;
- clique em item → Detalhes do Orçamento/Pedido.

**Status esperado:**
- funcional com dados reais;
- filtros e buscas funcionando;
- estado vazio devidamente tratado.

**Requisitos e regras de negócio:**
- mostrar cliente, serviço, valor e status;
- dados devem ser persistidos e atualizados;
- lista deve refletir estado correto do banco.

**Referência visual:**
- `docs/telas/tela2.png`
- `docs/telas/tela3.png`

---

### 3. Tela de Novo Orçamento
**Objetivo:** coletar informações do orçamento para cálculo e registro.

**Elementos necessários:**
- dados do cliente;
- telefone e e-mail;
- nome do serviço;
- quantidade;
- características do pedido;
- botão de salvar/calcular.

**Ações do usuário:**
- preencher todos os campos;
- salvar o orçamento;
- confirmar cadastro.

**Navegação:**
- salvar → Home ou Detalhes;
- voltar → Home.

**Status esperado:**
- formulário validado;
- dados persistidos corretamente.

**Requisitos e regras de negócio:**
- campos obrigatórios devem ser preenchidos;
- orçamento deve salvar com valor calculado;
- erros devem ser exibidos sem fechar a tela.

**Referência visual:**
- `docs/telas/tela4.png`

---

### 4. Tela de Detalhes do Orçamento
**Objetivo:** mostrar o orçamento completo e disponibilizar ações principais.

**Elementos necessários:**
- nome do cliente;
- serviço;
- quantidade;
- características;
- valor total;
- status;
- botão de WhatsApp;
- botão de aprovação/atualização.

**Ações do usuário:**
- visualizar dados do orçamento;
- abrir WhatsApp;
- aprovar orçamento;
- atualizar status.

**Navegação:**
- botão voltar → Home;
- aprovação → pedido/fluxo de status;
- WhatsApp → ação externa.

**Status esperado:**
- dados e status visíveis;
- ações funcionando conforme a regra do fluxo.

**Requisitos e regras de negócio:**
- orçamento aprovado pode virar pedido;
- WhatsApp abre com mensagem pré-preenchida;
- falha ao abrir o WhatsApp não encerra o app.

**Referência visual:**
- `docs/telas/tela5.png`
- `docs/telas/tela6.png`

---

### 5. Tela de Pedido / Status
**Objetivo:** acompanhar o andamento do pedido pós-aprovação.

**Elementos necessários:**
- dados do pedido;
- status atual;
- observações visuais;
- botão de alteração do status;
- histórico ou resumo.

**Ações do usuário:**
- consultar status;
- atualizar andamento;
- confirmar conclusão do pedido.

**Navegação:**
- retorno para a tela de detalhes ou home.

**Status esperado:**
- fluxo de acompanhamento funcional e consistente.

**Requisitos e regras de negócio:**
- status deve refletir o andamento real do trabalho;
- pedido deve estar vinculado ao orçamento;
- status deve ser atualizado em todas as telas relevantes.

**Referência visual:**
- `docs/telas/tela7.png`

---

## Cronograma sugerido

### Fase 1 — Setup inicial
**Objetivo:** preparar a base do app para continuar a implementação com segurança.

**Tarefas:**
- revisar arquitetura;
- ajustar organização de pacotes;
- confirmar entidades e regras do PRD;
- ajustar navegação inicial;
- revisar strings e identidade visual.

**Entregáveis:**
- estrutura consistente;
- models e navegação prontos;
- regras gerais definidas.

**Critérios de aceite:**
- todo o time entende a base do projeto;
- estrutura está alinhada ao PRD;
- app compila sem erros básicos.

**Dependências:**
- PRD;
- AGENTS;
- estrutura atual do app.

---

### Fase 2 — Navegação e telas principais
**Objetivo:** entregar a base da interface com fluxo completo e navegável.

**Tarefas:**
- login;
- home;
- novo orçamento;
- detalhes;
- estados vazios e erro;
- transições entre telas.

**Entregáveis:**
- fluxo funcional entre telas;
- telas visuais consistentes;
- navegação pronta para integração com dados reais.

**Critérios de aceite:**
- todo o fluxo principal funciona sem erros de navegação;
- retorno e parâmetros funcionam corretamente;
- telas seguem os protótipos e a documentação.

**Dependências:**
- Fase 1.

---

### Fase 3 — Persistência e regras de negócio
**Objetivo:** implementar dados reais e salvar informações do app.

**Tarefas:**
- Room entities;
- DAOs;
- database;
- repository;
- regras de cálculo e status;
- listagem e consulta de dados.

**Entregáveis:**
- dados persistidos;
- orçamentos salvos e recuperados;
- rules de negócio funcionando.

**Critérios de aceite:**
- dados continuam salvos após reabrir app;
- operações de salvamento e consulta funcionam;
- erro de banco é tratado sem encerrar o app.

**Dependências:**
- Fase 2.

---

### Fase 4 — WhatsApp e integração
**Objetivo:** habilitar envio do orçamento para o cliente.

**Tarefas:**
- montar mensagem do orçamento;
- abrir WhatsApp por Intent;
- tratar ausência do app;
- manter dados na tela em caso de falha.

**Entregáveis:**
- botão de WhatsApp funcionando;
- mensagem automática preenchida;
- alertas de erro corretos.

**Critérios de aceite:**
- app abre o WhatsApp com proposta pronta;
- falha é tratada sem fechar o app;
- o usuário entende o que aconteceu.

**Dependências:**
- Fase 3.

---

### Fase 5 — Testes, ajustes e entrega
**Objetivo:** validar o produto final com foco em estabilidade e usabilidade.

**Tarefas:**
- testes do fluxo completo;
- validação de campos obrigatórios;
- testes de persistência;
- testes de WhatsApp;
- revisão das mensagens e textos;
- refinamento visual e de usabilidade.

**Entregáveis:**
- versão funcional estável;
- checklist de QA final;
- app pronto para revisão final.

**Critérios de aceite:**
- não há falhas graves no fluxo principal;
- as regras do PRD estão sendo atendidas;
- app funciona em ambiente real e emulador.

**Dependências:**
- Fases anteriores.

---

## Dependências entre atividades

- a base técnica depende dos requisitos do PRD;
- a navegação depende da estrutura e das telas;
- o Room depende dos modelos e do fluxo principal;
- o WhatsApp depende da tela de detalhes e do orçamento;
- os testes dependem de todas as fases anteriores;
- a revisão final depende do alinhamento entre UI, regras de negócio e persistência.

---

## Critérios de aceite do projeto

- app abre corretamente;
- login valida dados antes de liberar acesso;
- orçamento pode ser criado e salvo;
- lista exibe itens e busca corretamente;
- detalhes mostram informações completas;
- WhatsApp abre com mensagem pré-preenchida;
- dados persistem ao fechar e reabrir;
- tela vazia e estados de erro estão tratados;
- status atualiza corretamente;
- app não fecha sozinho em cenários normais.

---

## Riscos e pontos de atenção

- ambiguidade entre orçamento e pedido no fluxo de status;
- risco de duplicação de lógica em componentes e telas;
- necessidade de padronizar nomes e textos em `strings.xml`;
- necessidade de manter consistência entre dados da UI e dados do banco;
- problema em ambiente sem WhatsApp instalado;
- campos obrigatórios e feedback de erro precisam ser claros;
- navegação sem parâmetro ou rota inconsistente pode quebrar fluxo;
- refatorações sem planejamento podem gerar retrabalho.

---

## Checklist de pronto para desenvolvimento

- requisitos alinhados com o PRD;
- fluxo principal definido;
- telas fundamentais revisadas;
- navegação validada;
- padrões de código definidos;
- persistência com Room planejada;
- regras de negócio revisadas;
- integração com WhatsApp prevista;
- critérios de teste definidos;
- objetivo final e entregáveis claros.

---

## Conclusão

O projeto Print-Flow possui uma base boa em Jetpack Compose e em navegação, mas ainda precisa consolidar as camadas de persistência, regras de negócio e integração com WhatsApp. O plano acima organiza o desenvolvimento em etapas lógicas e priorizadas, reduzindo risco de retrabalho e garantindo uma entrega mais estável.

A ordem recomendada é:
1. setup e arquitetura;
2. navegação e telas;
3. persistência;
4. WhatsApp e status;
5. testes e revisão final.

Essa sequência permite evoluir o app com clareza, preservando a consistência do PRD e do padrão do projeto.