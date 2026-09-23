# 📄 PRD — Documento de Requisitos do Produto

> **O que é um PRD?** É o documento que responde **o quê** o app faz e **por quê** — não *como* ele é programado.
> Quem lê o PRD deve conseguir entender o app inteiro sem abrir o código.
>
> **Regra do PRD:** todo requisito precisa ser verificável.

| | |
|---|---|
| **App** | Print-Flow |
| **Grupo** | 4 |
| **Autores** | Ana Elisa, Bianca Sofia, Larissa Nikolly e Sofia Vitória |
| **Versão do documento** | 1.0 |
| **Última atualização** | 22/09/2026 |
| **Status** | ( ) Rascunho (x) Em revisão ( ) Aprovado |

---

## 1. Visão do produto

**Pitch:** O Print-Flow ajuda gráficas a realizar orçamentos, enviar propostas aos clientes pelo WhatsApp, registrar pedidos e acompanhar o status dos serviços de forma simples e organizada.

**Problema:** Pequenas gráficas têm dificuldade para organizar orçamentos e pedidos dos clientes, principalmente para controlar informações como serviço, quantidade, valor e andamento do pedido. Atualmente, essas informações podem ser controladas por anotações em papel, mensagens ou planilhas, além de o envio do orçamento pelo WhatsApp precisar ser feito manualmente.
**Por que vale a pena fazer isso:** O Print-Flow centraliza os orçamentos e pedidos em um único aplicativo, facilitando o cálculo, o registro das informações, o envio da proposta ao cliente e o acompanhamento do serviço.

---

## 2. Público e cenário de uso

**Usuário-alvo:** Donos e funcionários de pequenas gráficas que precisam realizar orçamentos, registrar pedidos e acompanhar serviços durante o atendimento aos clientes.

**História de uso:**
> "Durante o atendimento, o funcionário de uma gráfica recebe um pedido de impressão. Ele abre o Print-Flow, cadastra o cliente e as informações do serviço, informa a quantidade e calcula o orçamento. Depois, revisa o valor e envia a proposta pelo WhatsApp. Quando o cliente aprova, o funcionário registra o orçamento como pedido e acompanha seu status até a finalização."

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
| RF01 | Como funcionário da gráfica, quero criar um orçamento para calcular o valor de um serviço de impressão. | Ao abrir o app, são exibidos os orçamentos e pedidos salvos, mostrando cliente, serviço, valor e status. Se não houver registros, aparece uma mensagem informando que não existem orçamentos ou pedidos cadastrados. | Must |
| RF02 | Como funcionário, quero criar um orçamento informando serviço, quantidade e características da impressão, para calcular o valor automaticamente. | Ao preencher os dados obrigatórios do orçamento e confirmar, o aplicativo calcula o valor e salva o orçamento.  | Must |
| RF03 | Como funcionário da gráfica, quero enviar o orçamento pelo WhatsApp para apresentar a proposta ao cliente. | Após o cálculo, o aplicativo permite abrir o WhatsApp com o número informado e uma mensagem preenchida com os dados do orçamento. | Must |
| RF04 | Como funcionário, quero transformar um orçamento aprovado em pedido, para iniciar o acompanhamento da produção. | Ao marcar um orçamento como aprovado, o aplicativo permite registrá-lo como pedido e atribuir um status inicial. | Must |
| RF05 | Como funcionário, quero atualizar o status de um pedido, para refletir seu andamento. | O usuário consegue alterar o status do pedido e visualizar o status atualizado na lista e nos detalhes. | Must |

---

## 5. Requisitos não funcionais

| ID | Requisito | Como será verificado |
|---|---|---|
| RNF01 | O app não pode fechar sozinho durante o uso normal | 5 minutos de uso contínuo sem crash, em 2 celulares diferentes |
| RNF02 | Operações que podem apresentar erros devem possuir tratamento de exceção. | Revisão do código nas operações de banco, rede e entradas do usuário. |
| RNF03 | Falhas não podem fechar o aplicativo ou deixar a tela sem resposta | Testes de falha da seção 9 |
| RNF04 | O app roda a partir do Android definido no projeto | Instalação em dispositivo real |
| RNF05 | Textos visíveis ficam em `strings.xml`, não escritos direto no código | Revisão do código |
| RNF06 | Todo arquivo do pacote do app tem comentário de fronteira escrito pelo grupo | Revisão do código |
| RNF07 | Qualquer integrante consegue localizar e alterar qualquer parte do app | Teste de mudança ao vivo (rubrica) |
| RNF08 | Os dados cadastrados devem permanecer disponíveis após fechar e abrir novamente o aplicativo. | Fechar e reabrir o app e verificar os dados salvos. |

---

## 6. Telas e navegação

**Mapa de navegação:**

```
[Tela Principal]
      │
      ├── toca em "+" → [Novo Orçamento]
      │                       │
      │                       └── calcular → [Detalhes do Orçamento]
      │                                             │
      │                                             ├── enviar → WhatsApp
      │                                             │
      │                                             └── aprovado → [Pedido]
      │
      └── toca em um item → [Detalhes do Orçamento/Pedido]

```

| Tela | O que mostra | Ações disponíveis |
|---|---|---|
| Principal | Lista de orçamentos e pedidos recentes: cliente, serviço, valor e status | Criar novo orçamento, abrir detalhe, buscar/filtrar |
| Novo Orçamento | Formulário com serviço, quantidade, características da impressão e telefone do cliente | Calcular valor, salvar orçamento |
| Detalhe / Status | Dados do orçamento/pedido e valor calculado | Enviar pelo WhatsApp, marcar como aprovado, atualizar status, editar (RF08) |

**Rascunhos das telas:** coloque as imagens em `docs/telas/` e liste os arquivos aqui.


---

## 7. Dados

### Opção C — Room (persistência local)

**Entidades:**

### Entidade Cliente

| Campo | Tipo | Obrigatório | Observação |
|---|---|---|---|
| `id` | Long | sim | chave primária, autogerada |
| `nome` | String | sim | nome do cliente |
| `telefone` | String | sim | usado para abrir o WhatsApp |
| `email` | String | sim | e-mail do cliente |

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
| Interface | (x) Jetpack Compose |
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
| Cor principal | `#___#007DCC____#FFB900___#D10056___#B2054C___` | `Color.kt` |
| Ícone 512×512 | impressora | `appicone.png` |
| `applicationId` | `br.edu.ifpe.printflow` | `build.gradle.kts` |
| `versionName` / `versionCode` | `1.0` / `1` | `build.gradle.kts` |

**Material da loja** (Etapa 4 do projeto):

| Artefato | Limite | Conteúdo |
|---|---|---|
| Título | 30 caracteres | Print-Flow |
| Descrição curta | 80 caracteres | Orçamentos, pedidos e envio ao cliente pelo WhatsApp — tudo em um só app. |
| Descrição completa |  | O Print-Flow é um aplicativo para pequenas gráficas que facilita a criação e o gerenciamento de orçamentos e pedidos. O aplicativo permite cadastrar as informações do serviço, calcular o valor do orçamento, enviar a proposta ao cliente pelo WhatsApp e acompanhar o status do pedido até sua finalização. |
| Imagem de destaque | 1024×500 | arte de uma impressora |
| Screenshots | mín. 2 | Capturas da Tela Principal, Novo Orçamento e Detalhes do Orçamento/Pedido. |
| Esboço de privacidade | — | dados ficam salvos localmente no celular (Room); o telefone do cliente é usado apenas para abrir o WhatsApp. |
| Arquivo `.aab` | — | Arquivo final do aplicativo para publicação, gerado após os testes e a revisão da versão final. |

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

**Decisões que o grupo tomou contra a sugestão da IA** :

* Manter o cliente fora do aplicativo, utilizando o WhatsApp para receber o orçamento.
* Utilizar armazenamento local com Room para os dados do aplicativo.
* Priorizar as quatro funcionalidades definidas no Canvas, evitando recursos fora do escopo.
* Não incluir pagamentos, chat próprio, entrega/rastreamento ou consulta de CEP.

-

---

## 15. Histórico de versões deste documento

| Versão | Data | Autor | O que mudou |
|---|---|---|---|
| 1.0 | 22/09/2026 | Grupo Print-Flow | Versão inicial, preenchida a partir do CANVAS.md |
| 1.1 | 22/09/2026 | Sofia Vitória | Mudança da identidade visual (tópico 10) |

