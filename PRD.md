# 📄 PRD — Documento de Requisitos do Produto

> **O que é um PRD?** É o documento que responde **o quê** o app faz e **por quê** — não *como* ele é programado.
> Quem lê o PRD deve conseguir entender o app inteiro sem abrir o código.
>
> **Regra do PRD:** todo requisito precisa ser verificável.

| | |
|---|---|
| **App** | Print-Flow |
| **Grupo** | — *(preencher número do grupo)* |
| **Autores** | Ana Elisa, Bianca Sofia, Larissa Nikolly e Sofia Vitória |
| **Versão do documento** | 1.0 |
| **Última atualização** | 22/09/2026 |
| **Status** | ( ) Rascunho (x) Em revisão ( ) Aprovado |

---

## 1. Visão do produto

**Pitch:** O Print-Flow ajuda gráficas a realizar orçamentos, enviar propostas aos clientes pelo WhatsApp, registrar pedidos e acompanhar o status dos serviços de forma simples e organizada.

**Problema:** Pequenas gráficas têm dificuldade para organizar orçamentos e pedidos dos clientes, principalmente para controlar informações como serviço, quantidade, valor e andamento do pedido. Hoje isso é feito em papel, mensagens soltas ou planilhas, e depois de calcular o orçamento o funcionário ainda precisa organizar manualmente as informações para enviar ao cliente pelo WhatsApp.

**Por que vale a pena fazer isso:** O app centraliza o orçamento, o cálculo do valor, o envio da proposta e o acompanhamento do pedido em um único lugar, reduzindo erros de anotação, retrabalho manual no envio ao cliente e perda de informações sobre o andamento dos pedidos.

---

## 2. Público e cenário de uso

**Usuário-alvo:** donos e funcionários de pequenas gráficas, que usam o app durante o atendimento ao cliente para orçar, enviar propostas e acompanhar pedidos. O cliente final não instala o app — ele recebe o orçamento pronto pelo WhatsApp.

**História de uso:**
> "São 19h, a funcionária da gráfica acabou de atender um cliente que pediu 500 panfletos coloridos. Ela abre o Print-Flow, cadastra o serviço, a quantidade e as características da impressão. Em menos de 30 segundos, o app calcula o valor e abre uma conversa no WhatsApp com a proposta pronta para revisar e enviar ao cliente."

---

## 3. Objetivos e não-objetivos

**Objetivos desta versão (v1.0):**

1. Permitir criar e calcular orçamentos de forma rápida e padronizada.
2. Permitir enviar o orçamento calculado ao cliente pelo WhatsApp diretamente do app.
3. Permitir registrar um orçamento aprovado como pedido e acompanhar seu status até a finalização.

**Não-objetivos (fora do escopo):**

- ❌ O cliente não precisará baixar ou instalar o aplicativo.
- ❌ O aplicativo não realizará pagamentos.
- ❌ Não haverá chat próprio entre cliente e gráfica.
- ❌ Não haverá entrega ou rastreamento do pedido.
- ❌ Não haverá consulta de CEP.

---

## 4. Requisitos funcionais

| ID | História de usuário | Critério de aceite | Prioridade |
|---|---|---|---|
| RF01 | Como funcionário da gráfica, quero ver a lista de orçamentos e pedidos recentes para acompanhar o atendimento. | Ao abrir o app, a tela principal lista orçamentos/pedidos com cliente, serviço, valor e status; se não houver nenhum, aparece "Nenhum orçamento cadastrado ainda." | Must |
| RF02 | Como funcionário, quero criar um orçamento informando serviço, quantidade e características da impressão, para calcular o valor automaticamente. | Ao preencher os campos e confirmar, o app calcula o valor com base na tabela de preços e exibe o orçamento calculado. | Must |
| RF03 | Como funcionário, quero enviar o orçamento calculado ao cliente pelo WhatsApp, para agilizar a comunicação. | Ao tocar em "Enviar pelo WhatsApp" e informar o número do cliente, o app abre uma conversa no WhatsApp com mensagem pré-preenchida com os dados do orçamento. | Must |
| RF04 | Como funcionário, quero transformar um orçamento aprovado em pedido, para iniciar o acompanhamento da produção. | Ao marcar um orçamento como aprovado, ele passa a aparecer na lista de pedidos com status inicial "Em produção". | Must |
| RF05 | Como funcionário, quero atualizar o status de um pedido, para refletir seu andamento. | Ao abrir um pedido, é possível alterar o status entre as opções definidas (ex.: Em produção, Pronto, Entregue) e a mudança é salva. | Must |
| RF06 | Como funcionário, quero consultar orçamentos e pedidos salvos anteriormente, para retomar o atendimento a qualquer momento. | Os dados permanecem salvos localmente (Room) mesmo após fechar e reabrir o app. | Must |
| RF07 | Como funcionário, quero buscar/filtrar orçamentos e pedidos por cliente ou status, para encontrar informações rapidamente. | Ao digitar um nome ou escolher um status, a lista é filtrada em tempo real. | Should |
| RF08 | Como funcionário, quero editar um orçamento antes de enviá-lo, para corrigir eventuais erros. | Ao tocar em "Editar" em um orçamento ainda não aprovado, os campos ficam editáveis e o valor é recalculado ao salvar. | Could |

---

## 5. Requisitos não funcionais

| ID | Requisito | Como será verificado |
|---|---|---|
| RNF01 | O app não pode fechar sozinho durante o uso normal | 5 minutos de uso contínuo sem crash, em 2 celulares diferentes |
| RNF02 | Toda operação que pode falhar está dentro de `try/catch` | Revisão do código: banco (Room), envio ao WhatsApp e entradas do usuário |
| RNF03 | Nenhuma falha mostra tela branca ou fecha o app — sempre há mensagem ao usuário | Testes de falha da seção 9 |
| RNF04 | O app roda a partir do Android 8.0 (API 26) — *confirmar com o grupo* | Instalação em dispositivo real |
| RNF05 | Textos visíveis ficam em `strings.xml`, não escritos direto no código | Revisão do código |
| RNF06 | Todo arquivo do pacote do app tem comentário de fronteira escrito pelo grupo | Revisão do código |
| RNF07 | Qualquer integrante consegue localizar e alterar qualquer parte do app | Teste de mudança ao vivo (rubrica) |
| RNF08 | O envio ao WhatsApp funciona mesmo que o cliente não tenha o app instalado, usando só o número informado | Teste manual enviando para um número real, com e sem WhatsApp instalado |

---

## 6. Telas e navegação

**Mapa de navegação:**

```
[Tela Principal — orçamentos e pedidos]
      │
      ├── toca no "+"      → [Tela de Novo Orçamento]
      ├── toca em um item  → [Tela de Detalhe / Status do pedido]
      └── (estado vazio)   → mensagem "Nenhum orçamento cadastrado ainda."
```

| Tela | O que mostra | Ações disponíveis |
|---|---|---|
| Principal | Lista de orçamentos e pedidos recentes: cliente, serviço, valor e status | Criar novo orçamento, abrir detalhe, buscar/filtrar |
| Novo Orçamento | Formulário com serviço, quantidade, características da impressão e telefone do cliente | Calcular valor, salvar orçamento |
| Detalhe / Status | Dados do orçamento/pedido e valor calculado | Enviar pelo WhatsApp, marcar como aprovado, atualizar status, editar (RF08) |

**Rascunhos das telas:** coloque as imagens em `docs/telas/` e liste os arquivos aqui.

- `docs/telas/01-principal.png`
- `docs/telas/02-novo-orcamento.png`
- `docs/telas/03-detalhe-status.png`

---

## 7. Dados

### Opção C — Room (persistência local)

**Entidade principal:** `Orcamento`

| Campo | Tipo | Obrigatório | Observação |
|---|---|---|---|
| `id` | Long | sim | chave primária, autogerada |
| `nomeCliente` | String | sim | nome do cliente |
| `telefoneCliente` | String | sim | usado para abrir o WhatsApp |
| `servico` | String | sim | ex.: panfleto, banner, cartão |
| `quantidade` | Int | sim | quantidade de itens |
| `caracteristicas` | String | não | detalhes da impressão (cor, tamanho, acabamento) |
| `valor` | Double | sim | valor calculado do orçamento |
| `status` | String | sim | Orçamento, Aprovado, Em produção, Pronto, Entregue |
| `dataCriacao` | Long (timestamp) | sim | usada para ordenar a lista |

**Operações necessárias:** (x) inserir (x) listar (x) atualizar ( ) excluir *(decidir se o grupo permitirá exclusão)*

### Opção C — WhatsApp (integração via Intent, não é uma API REST)

| Item | Definição |
|---|---|
| Integração utilizada | `Intent` do Android para abrir o WhatsApp com número e mensagem pré-preenchidos |
| Documentação | Intents do Android (`android.intent.action.VIEW` com URI `https://wa.me/`) |
| Precisa de chave? | (x) não |
| Observação | Não é uma chamada Retrofit/API — é uma ação do sistema. Retrofit fica reservado para uma eventual integração futura (ex.: tabela de preços remota), ainda não definida pelo grupo. |

---

## 8. Arquitetura e tecnologias

| Item | Escolha |
|---|---|
| Linguagem | Kotlin |
| Interface | (x) Jetpack Compose ( ) XML/Views |
| Persistência | (x) Room |
| Rede | ( ) Retrofit — reservado para uso futuro, se necessário |
| Outras bibliotecas | Jetpack Compose, Room, Retrofit (opcional) |
| `minSdk` / `targetSdk` | 26 / *(definir conforme Android Studio)* |

**Organização de pastas do projeto:**

```
app/src/main/java/br/edu/ifpe/printflow/
├── ui/        # telas (Principal, Novo Orçamento, Detalhe/Status)
├── data/      # Room (entidade Orcamento, DAO, database)
└── MainActivity.kt
```

---

## 9. Tratamento de erros

| Situação de falha | O que o app faz | Mensagem para o usuário |
|---|---|---|
| Sem internet (WhatsApp indisponível) | Mantém a tela e mostra aviso + botão "Tentar de novo" | "Sem conexão. Verifique a internet e tente novamente." |
| WhatsApp não instalado/erro ao abrir | Mantém os dados do orçamento na tela | "Não foi possível abrir o WhatsApp. Tente novamente." |
| Lista vazia (nenhum orçamento/pedido ainda) | Mostra estado vazio | "Nenhum orçamento cadastrado ainda." |
| Campo obrigatório em branco | Impede salvar e destaca o campo | "Preencha todos os campos obrigatórios." |
| Erro ao salvar/consultar no banco (Room) | Não fecha o app, mantém os dados digitados | "Não foi possível realizar esta operação. Tente novamente." |

---

## 10. Identidade visual e publicação

| Item | Definição | Onde fica |
|---|---|---|
| Nome do app | Print-Flow | `strings.xml` |
| Cor principal | `#2563EB` | `Color.kt` |
| Cor secundária | *(a definir pelo grupo)* | `Color.kt` |
| Ícone 512×512 | Folha de papel, impressora e símbolo de orçamento | `loja/icone-512.png` |
| `applicationId` | `br.edu.ifpe.printflow` | `build.gradle.kts` |
| `versionName` / `versionCode` | `1.0` / `1` | `build.gradle.kts` |

**Material da loja** (Etapa 4 do projeto):

| Artefato | Limite | Conteúdo |
|---|---|---|
| Título | 30 caracteres | Print-Flow |
| Descrição curta | 80 caracteres | Orçamentos, pedidos e envio ao cliente pelo WhatsApp — tudo em um só app. |
| Descrição completa | — | *(escrever em `loja/descricao.md`)* |
| Imagem de destaque | 1024×500 | `loja/destaque-1024x500.png` |
| Screenshots | mín. 2 | `loja/screenshots/` |
| Esboço de privacidade | — | `loja/privacidade.md` — dados ficam salvos localmente no celular (Room); o telefone do cliente é usado apenas para abrir o WhatsApp e não é enviado a nenhum servidor. |
| Arquivo `.aab` | — | `loja/app-release.aab` |

---

## 11. Plano de testes

| # | O que testar | Passos | Resultado esperado | OK? |
|---|---|---|---|---|
| T1 | Abrir o app pela primeira vez | Instalar e abrir | Tela principal aparece com estado vazio explicado | |
| T2 | Criar e calcular um orçamento | Tocar em "+", preencher serviço/quantidade/características, confirmar | Valor é calculado e o orçamento aparece na lista | |
| T3 | Enviar orçamento pelo WhatsApp | Abrir um orçamento e tocar em "Enviar pelo WhatsApp" | WhatsApp abre com a conversa e mensagem pré-preenchida | |
| T4 | Falha de rede/banco | Ativar modo avião e repetir T2/T3 | Mensagem clara, app não fecha | |
| T5 | Reabrir o app | Fechar e abrir de novo | Orçamentos e pedidos continuam salvos | |
| T6 | Aprovar orçamento e acompanhar status | Marcar um orçamento como aprovado e alterar o status do pedido | Pedido aparece na lista com status atualizado | |
| T7 | Teste com usuário externo | Pessoa de fora usa sem explicação | Consegue criar e enviar um orçamento sozinha | |

**Testado em:** *(preencher modelo do celular e versão do Android — pelo menos 2 aparelhos)*

---

## 12. Cronograma

| Marco | Prazo | Responsável | Status |
|---|---|---|---|
| M1 — Canvas preenchido + repositório criado | 16/09 | Grupo | ✅ Concluído |
| M2 — PRD aprovado + telas rascunhadas | 23/09 | Grupo (rodízio) | Em andamento |
| M3 — Funcionalidade base rodando (tela principal + criação/cálculo de orçamento) | 21/10 | Grupo (rodízio) | |
| M4 — Dados completos e integração com WhatsApp | 11/11 | Grupo (rodízio) | |
| M5 — Identidade visual + `.apk` de release testado | 25/11 | Grupo (rodízio) | |
| M6 — `.aab` + material de loja + README.md | 02/12 | Grupo (rodízio) | |
| **Entrega e apresentação** | **10/12** | Grupo | |

---

## 13. Riscos

| Risco | Impacto | Plano B |
|---|---|---|
| Erros no cálculo do orçamento | Médio | Utilizar uma tabela fixa de preços definida pelo grupo |
| Problemas no armazenamento dos dados (Room) | Alto | Revisar a implementação do Room e testar novamente |
| Problemas no envio pelo WhatsApp | Médio | Permitir que o orçamento seja copiado e compartilhado manualmente |
| Erros na geração do APK | Alto | Revisar o projeto e gerar uma nova versão para testes |
| Falta de tempo para a entrega | Alto | Priorizar as quatro funcionalidades essenciais (F1–F4) |

---

## 14. Como vamos orientar a implementação com IA

A implementação usa o **Gemini no Android Studio**. Este PRD é o documento que diz à IA o que construir — quanto mais preciso ele estiver, menos a IA inventa. Regras completas em [`docs/USO_DE_IA.md`](docs/USO_DE_IA.md).

**Recursos que vamos usar:** (x) Chat (x) Agent Mode ( ) Explain Code ( ) Ask Gemini no Logcat ( ) Generate Unit Tests ( ) Transform UI

**Regras que colocamos no `AGENTS.md`:**

- Todo código gerado pela IA deve ser revisado antes de ser aceito.
- Cada integrante deve entender e conseguir explicar o código implementado.
- Alterações importantes devem ser revisadas pelo grupo antes do merge.

**Divisão do perímetro explicável** — quem responde por explicar o quê na apresentação (todas participam do desenvolvimento em rodízio; papéis abaixo indicam quem coordena):

| Parte do código | Responsável |
|---|---|
| Telas (`ui/`) | Ana Elisa e Larissa Nikolly |
| Dados (`data/`) | Ana Elisa e Larissa Nikolly |
| Identidade visual e recursos | Bianca Sofia |
| Build e artefatos de loja | Sofia Vitória |

**Decisões que o grupo tomou contra a sugestão da IA** *(preencher ao longo do projeto — isso conta a favor na avaliação)*:

-

---

## 15. Histórico de versões deste documento

| Versão | Data | Autor | O que mudou |
|---|---|---|---|
| 1.0 | 22/09/2026 | Grupo Print-Flow | Versão inicial, preenchida a partir do CANVAS.md |
