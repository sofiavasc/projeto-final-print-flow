
# 🎯 Canvas do Projeto Final — App Android

> **Como usar:** este é o primeiro documento do projeto. Preencha em grupo, em uma única aula, **antes de escrever qualquer linha de código**. Cada bloco tem no máximo 5 linhas — se não couber, o projeto está grande demais.
> Depois de preenchido e validado pelo professor, ele vira a base do [`PRD.md`](PRD.md).

| | |
|---|---|
| **Grupo nº** | |
| **Integrantes (3 a 4)** | Ana Elisa, Bianca Sofia, Larissa Nikolly e Sofia Vitória.|
| **Turma** | 3º ano — Ensino Médio |
| **Repositório** | `(https://github.com/sofiavasc/projeto-final-print-flow/tree/main)` |
| **Data de preenchimento** | _09__/_09__/2026 |
| **Entrega final** | **10/12/2026** |

---

## 🧩 Bloco 1 — Nome e pitch do app

**Nome do app:** PrintFlow

Pitch em uma frase:
> “O Print-Flow ajuda gráficas a realizar orçamentos, enviar propostas aos clientes pelo WhatsApp, registrar pedidos e acompanhar o status dos serviços de forma simples e organizada.”


## 😖 Bloco 2 — Problema

**Qual dor real vocês estão resolvendo? Descrevam uma situação concreta que alguém vive hoje.**
Pequenas gráficas podem ter dificuldade para organizar os orçamentos e pedidos dos clientes, principalmente quando precisam controlar informações como serviço, quantidade, valor e andamento do pedido.

**Como esse problema é resolvido hoje (sem o app)?**
Atualmente, muitos orçamentos e pedidos são controlados por anotações em papel, mensagens ou planilhas. Além disso, depois de realizar um orçamento, o funcionário precisa organizar as informações e enviá-las manualmente ao cliente pelo WhatsApp. 

## 👥 Bloco 3 — Público-alvo

Para quem é o app? Sejam específicos (idade, contexto, com que frequência usariam).

* Perfil principal: donos e funcionários de pequenas gráficas.
* Quando/onde usam: durante o atendimento aos clientes, para realizar orçamentos, enviar propostas, registrar pedidos e acompanhar seus status.
* Uma pessoa real que testaria o app: Joanna Jullyana Eronildes Barbosa (mãe de Ana Elisa).

  O cliente não precisa instalar o aplicativo. O orçamento é realizado pela gráfica e enviado ao cliente pelo WhatsApp.


---

## 💡 Bloco 4 — Solução em uma tela

Descreva o que a **tela principal** mostra e o que o usuário consegue fazer nela.

* A tela principal lista: orçamentos e pedidos recentes, com cliente, serviço, valor e status.
* A ação principal do usuário é: cadastrar um orçamento informando o serviço, quantidade e características da impressão.
* Depois de agir, o usuário vê: o valor calculado e pode enviar o orçamento ao cliente pelo WhatsApp.

Fluxo principal:

Novo orçamento → calcular valor → enviar orçamento pelo WhatsApp → cliente aprova → registrar pedido → atualizar status → pedido finalizado.

---

## ✅ Bloco 5 — Funcionalidades do MVP

Máximo de **4 funcionalidades**. Se tiver mais, corte. Lembre: *qualidade acima de complexidade*.

| # | Funcionalidade | Essencial? | Quem faz |
|---|---|---|---|
| F1 | Criar e calcular orçamentos | Sim | Todos |
| F2 | Enviar o orçamento ao cliente pelo WhatsApp | Sim | Todos |
| F3 | Registrar orçamento aprovado e acompanhar o status do pedido | Sim | Todos |
| F4 | Salvar e consultar orçamentos e pedidos | Sim | Todos |

---

## 🚫 Bloco 6 — Fora do escopo

O que o app **não** vai fazer nesta entrega. Escrever isso aqui protege vocês de perder o prazo.
* ❌ O cliente não precisará baixar ou instalar o aplicativo.
* ❌ O aplicativo não realizará pagamentos.
* ❌ Não haverá chat próprio entre cliente e gráfica.
* ❌ Não haverá entrega ou rastreamento do pedido.
* ❌ Não haverá consulta de CEP.




---

## ⚙️ Bloco 7 — Caminho técnico

Marque **uma** opção (as três valem a mesma nota):

- [ ] **Opção A — Room:** dados salvos no próprio celular (lista de compras, agenda, diário de treino, controle financeiro)
- [ ] **Opção B — Retrofit:** dados vindos de uma API pública (notícias, filmes, feed, clima)
- [X] **Opção C — Desafio:** API + salvar favoritos localmente

**Se escolheu B ou C — qual API?** WhatsApp para envio dos orçamentos e Room para armazenamento local.

**Bibliotecas que o grupo vai usar:**Jetpack Compose, Room e Retrofit, caso seja necessária uma integração com API externa.

**Onde entra o `try/catch`?** Nas operações que podem apresentar erros, como o salvamento e consulta dos dados ou uma possível integração com serviços externos.

- Pode falhar: No salvamento ou consulta dos dados e na abertura do WhatsApp, caso ocorra algum erro ou o aplicativo não esteja disponível.
- O usuário vê a mensagem: “Não foi possível realizar esta operação. Tente novamente.”

---

## 🎨 Bloco 8 — Identidade visual

| Item | Definição do grupo |
|---|---|
| Nome exibido (`strings.xml`) | Print-Flow |
| Cor principal (hex, em `Color.kt`) | `#___#007DCC____#FFB900___#D10056___#B2054C___` |
| Ideia do ícone (512×512) | folha de papel, impressora e símbolo de orçamento. |
| `applicationId` | `br.edu.ifpe.printflow.___` |
| Versão inicial | `1.0` (versionCode `1`) |

---

## 👤 Bloco 9 — Equipe, papéis e riscos

| Integrante | Papel principal | Responsável por |
|---|---|---|
| Ana Elisa | Desenvolvimento | Participar do desenvolvimento e revisão de todas as funcionalidades |
| Larissa Nikolly | Desenvolvimento |  Participar do desenvolvimento e revisão de todas as funcionalidades |
| Sofia Vitória | Design e identidade visual | identidade visual e documentação. |
| Bianca | Documentação, build e entrega | testes, validação, identificação de erros e preparação da entrega. |

> Todos programam. O "papel" define quem **responde** por aquela parte, não quem trabalha sozinho.

**Riscos — o que pode dar errado e o plano B:**

| Risco | Plano B |
|---|---|
| Erros no cálculo do orçamento | Utilizar uma tabela fixa de preços definida pelo grupo |
| Problemas no armazenamento dos dados | Revisar a implementação do Room e testar novamente |
| Problemas no envio pelo WhatsApp | Permitir que o orçamento seja copiado e compartilhado manualmente |
| Erros na geração do APK | Revisar o projeto e gerar uma nova versão para testes |
| Falta de tempo para a entrega | Priorizar as quatro funcionalidades essenciais |

---

## 🤖 Bloco 10 — Acordo de trabalho com IA

A implementação pode ser feita com o Gemini no Android Studio. Vocês orientam, ele digita — e cada integrante precisa saber explicar o que entrou no projeto. Regras completas em docs/USO_DE_IA.md⁠￼.

Três regras que vamos escrever no nosso AGENTS.md:

1. Todo código gerado pela IA deve ser revisado antes de ser aceito.
2. Cada integrante deve entender e conseguir explicar o código implementado.
3. Alterações importantes devem ser revisadas pelo grupo antes do merge.


**Combinados do grupo:**

- [x] Ninguém clica *Accept* no Agent Mode sem ler a mudança inteira.
- [x] Quem aceitou o código escreve o comentário de fronteira do arquivo.
- [x] Antes de cada marco, revisamos juntos: alguém aqui não entende alguma parte?
- [x] Nenhuma chave de API ou senha vai para o prompt.
- Outro combinado nosso:quem implementar uma funcionalidade deverá apresentá-la aos outros integrantes.

**Como vamos garantir que todos entendem tudo**:
* A cada funcionalidade, as integrantes se revezarão no desenvolvimento.
* Quem trabalhou na alteração apresentará o código às demais, explicando seu funcionamento.
* O grupo fará uma revisão conjunta antes de seguir para a próxima etapa.
---

## 🗓️ Bloco 11 — Marcos até 10/12

| Marco | Prazo | Como se comprova no GitHub |
|---|---|---|
| M1 — Canvas preenchido + repositório criado | 16/09 | `CANVAS.md` no `main` |
| M2 — PRD aprovado + telas rascunhadas | 23/09 | `PRD.md` + imagens em `docs/` |
| M3 — Funcionalidade base rodando | 21/10 | tela principal lista dados + 1 ação + `try/catch` |
| M4 — Dados completos (Room/Retrofit) e erros tratados | 11/11 | commits da camada de dados |
| M5 — Identidade visual + `.apk` de release testado | 25/11 | ícone, cores, `.apk` testado por 2 pessoas de fora |
| M6 — `.aab` + material de loja + `README.md` | 02/12 | pasta `loja/` + `README.md` completo |
| **Entrega e apresentação** | **10/12** | tag `v1.0` no repositório |

---

## 🏁 Bloco 12 — Definição de pronto

O grupo só considera o app pronto quando **todas** estas frases forem verdadeiras:

- [ ] O app abre e não fecha sozinho depois de 5 minutos de uso.
- [ ] A tela principal mostra dados reais (não texto de exemplo fixo no código).
- [ ] A ação principal funciona e o resultado aparece na tela.
- [ ] Quando algo falha, aparece uma mensagem clara — o app não quebra.
- [ ] O app tem nome, ícone e cor próprios (nada de ícone padrão do Android).
- [ ] Duas pessoas de fora do grupo instalaram o `.apk` e conseguiram usar sem explicação.
- [ ] O `README.md` explica o que o app faz, com o que foi feito e como gerar o build.
- [ ] O `docs/USO_DE_IA.md` e o `AGENTS.md` estão preenchidos.
- [ ] **Cada integrante consegue abrir o projeto e fazer uma mudança pequena sozinho** — trocar um texto, acrescentar um campo, mudar a ordem da lista.
- [ ] Todo arquivo nosso tem o comentário de fronteira escrito por nós.

---

## ✍️ Validação do professor

| | |
|---|---|
| Data | |
| Situação | ( ) Aprovado ( ) Aprovado com ajustes ( ) Refazer |
| Observações | |
