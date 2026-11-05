# Plano de Teste de Sabotagem e Documentação — Print-Flow

**Integrante:** Sofia Vitória 
**Projeto:** Print-Flow (Gerenciamento de Orçamentos e Pedidos para Gráficas)  
**Tecnologias:** Kotlin, Jetpack Compose, Room (Persistência Local), Intent Android (`android.intent.action.VIEW` para WhatsApp)  
**Pacote Base:** `br.edu.ifpe.projeto_final_print_flow`  
**Objetivo:** Guia passo a passo para execução, teste de resiliência e registro documental das **cinco sabotagens** solicitadas, garantindo que o aplicativo trate falhas graciosamente conforme a **Seção 9 do PRD** sem fechamentos inesperados (*crashes*), e garantindo a reversão total de alterações temporárias no código.

---

## 1. Mapeamento da Arquitetura do Projeto Aberto

Para executar as sabotagens com precisão, foi mapeada a estrutura de arquivos e responsabilidades do codebase:

| Componente / Camada | Arquivo do Projeto | Responsabilidade no Teste de Sabotagem |
|---|---|---|
| **Modelo de Estado UI** | `ui/UiState.kt` | Interface selada (`sealed interface UiState`) com os estados `Carregando`, `Vazio`, `Erro(mensagem)` e `Sucesso(orcamentos)`. |
| **Tela Principal** | `ui/screens/home/HomeScreen.kt` | Renderização da lista de orçamentos e do estado vazio (`home_empty_state_message`) ou carregando (`home_loading_message`). |
| **ViewModel Principal** | `ui/screens/home/HomeViewModel.kt` | Gerenciamento de fluxo do estado `UiState`. Local de inserção do `delay(2000)` temporário. |
| **Tela Novo Orçamento** | `ui/screens/budget/BudgetScreen.kt` | Validação de formulário de campos obrigatórios (`error_fields_required`) e captura de erros ao salvar. |
| **Tela de Detalhes** | `ui/screens/details/DetailsScreen.kt` | Integração de envio via WhatsApp com Intent e tratamento de `ActivityNotFoundException` (`error_whatsapp`). |
| **Repositório Central** | `data/repository/PrintFlowRepository.kt` | Comunicação com o Room Database. Local para injeção temporária de exceções de banco de dados (`error_db`). |
| **Banco de Dados (Room)** | `data/PrintFlowDatabase.kt` & DAOs | Armazenamento local. Alvo de limpeza de dados para provocar lista vazia e simulação de falha de persistência. |
| **Recursos de Texto** | `res/values/strings.xml` | Central de mensagens do sistema especificadas no PRD. |

---

## 2. Passo a Passo de Execução das 5 Sabotagens

---

### 💣 Sabotagem 1: Lista Vazia

- **Objetivo:** Verificar se o aplicativo exibe o estado UI limpo e amigável quando não há nenhum orçamento ou pedido registrado no banco local Room.
- **Como provocar:**
  1. No emulador ou dispositivo físico, acesse: **Configurações do Android > Aplicativos > Print-Flow > Armazenamento e cache > Limpar armazenamento (Limpar dados)**.
  2. Alternativamente, desinstale e reinstale o aplicativo no dispositivo.
  3. Abra o aplicativo Print-Flow e efetue login com a credencial padrão (`admin` / `123456`) ou cadastrada.
- **O que conferir na tela:**
  - A tela principal (`HomeScreen.kt`) deve reconhecer o estado `UiState.Vazio`.
  - A mensagem **"Nenhum orçamento cadastrado ainda."** (`R.string.home_empty_state_message`) deve ser apresentada centralizada na tela.
  - O botão flutuante de adicionar novo orçamento (`+`) deve permanecer visível e funcional.
- **Conformidade com o PRD:** Atende ao item "Lista vazia" da Seção 9 e ao RF01.
- **Alteração no código:** Nenhuma alteração de código é necessária (apenas ação no ambiente de execução/dados).

---

### 💣 Sabotagem 2: Sem Internet (Tentativa de Envio ao WhatsApp)

- **Objetivo:** Garantir que a perda de conexão de rede não trave nem feche o app ao tentar abrir o serviço externo do WhatsApp, mantendo o restante das operações offline funcionando normalmente.
- **Como provocar:**
  1. Com pelo menos um orçamento cadastrado no app, abra os detalhes desse orçamento na `DetailsScreen.kt`.
  2. Deslize a barra de notificações do dispositivo/emulador e **ative o Modo Avião** (desconectando Wi-Fi e dados móveis).
  3. Toque no botão **"Enviar pelo WhatsApp"**.
- **O que conferir na tela:**
  - O aplicativo **mantém a tela ativa e não fecha** (sem crash).
  - Como o WhatsApp utiliza uma `Intent` do sistema (`android.intent.action.VIEW`), o sistema operacional ou o app exibe o aviso **"Sem conexão. Verifique a internet e tente novamente."** (`R.string.error_connectivity`) ou a tela do WhatsApp notifica a ausência de rede.
  - Ao retornar ao Print-Flow, todos os dados do orçamento permanecem intactos na tela.
  - Navegue pelo restante do app (criação de orçamento, consulta de histórico): tudo continua funcionando normalmente pois os dados são mantidos offline pelo Room.
- **Conformidade com o PRD:** Atende ao item "Sem internet (WhatsApp indisponível)" da Seção 9 e ao RNF03.
- **Alteração no código:** Nenhuma alteração de código necessária (teste comportamental de hardware/rede).

---

### 💣 Sabotagem 3: Carregando Visível

- **Objetivo:** Validar a exibição do indicador/mensagem de carregamento (`UiState.Carregando`) na inicialização da lista, simulando um pequeno tempo de processamento.
- **Como provocar:**
  1. Abra o arquivo `app/src/main/java/br/edu/ifpe/projeto_final_print_flow/ui/screens/home/HomeViewModel.kt`.
  2. Localize o método `carregarDados()`.
  3. Insira temporariamente a linha `kotlinx.coroutines.delay(2000)` imediatamente após definir `_uiState.value = UiState.Carregando`.

  *Trecho de alteração temporária em `HomeViewModel.kt`:*
  ```kotlin
  fun carregarDados() {
      viewModelScope.launch {
          _uiState.value = UiState.Carregando

          // === ALTERAÇÃO TEMPORÁRIA DA SABOTAGEM 3 ===
          kotlinx.coroutines.delay(2000)
          // ============================================

          try {
              val orcamentos = repository.observarOrcamentos().first()
              // ...
  ```

  4. Execute o app e abra a Tela Principal.
- **O que conferir na tela:**
  - Durante os 2 segundos de atraso, a tela deve exibir o estado de carregamento com o texto **"Carregando orçamentos..."** (`R.string.home_loading_message`).
  - Após os 2 segundos, a tela deve transicionar suavemente para `UiState.Sucesso` (com os cards da lista) ou `UiState.Vazio`.
- **Reversão imediata:** Remova a linha `kotlinx.coroutines.delay(2000)` do `HomeViewModel.kt`.

---

### 💣 Sabotagem 4: Campo Obrigatório Vazio

- **Objetivo:** Confirmar que o envio do formulário de novo orçamento com campos em branco é bloqueado antes da persistência, destacando a mensagem de erro apropriada.
- **Como provocar:**
  1. Na tela principal, toque no botão `+` para abrir a tela **Novo Orçamento** (`BudgetScreen.kt`).
  2. Deixe um ou todos os campos obrigatórios em branco (ex.: deixe o campo **Nome do Cliente**, **Telefone** ou **Serviço** vazio).
  3. Toque no botão **"Calcular e Salvar"**.
- **O que conferir na tela:**
  - O salvamento é imediatamente **impedido** (nenhum dado incorreto é enviado ao banco).
  - É exibida em destaque (cor de erro) a mensagem: **"Preencha todos os campos obrigatórios."** (`R.string.error_fields_required`).
  - O foco e o conteúdo digitado nos demais campos não são perdidos.
- **Conformidade com o PRD:** Atende ao item "Campo obrigatório em branco" da Seção 9 e ao RF02.
- **Alteração no código:** Nenhuma alteração no código necessária (teste de validação de formulário).

---

### 💣 Sabotagem 5: Erro de Servidor / Operações Externas e Persistência

Como o app **não utiliza API REST/Retrofit**, as falhas de ambiente externo e persistência são divididas em dois cenários temporários:

#### Cenário 5A: Erro no Banco de Dados Local (Room)

- **Objetivo:** Garantir que exceções inesperadas ao ler ou gravar no banco de dados não fechem o app e preservem os dados digitados pelo usuário.
- **Como provocar:**
  1. Abra o arquivo `app/src/main/java/br/edu/ifpe/projeto_final_print_flow/data/repository/PrintFlowRepository.kt`.
  2. No método `salvarOrcamento(...)`, insira uma exceção forçada temporária na primeira linha do corpo da função.

  *Trecho de alteração temporária em `PrintFlowRepository.kt`:*
  ```kotlin
  suspend fun salvarOrcamento(
      clienteNome: String,
      telefone: String,
      email: String,
      nomeServico: String,
      quantidade: Int,
      caracteristicas: String,
      precoUnitario: Double,
      descricaoServico: String = ""
  ): Result<Long> {
      // === ALTERAÇÃO TEMPORÁRIA DA SABOTAGEM 5A ===
      throw RuntimeException("Falha forçada no banco de dados Room")
      // ============================================

      return try {
          // lógica normal de inserção...
  ```

  3. Abra o app, vá em Novo Orçamento, preencha todos os campos corretamente e clique em **"Calcular e Salvar"**.
- **O que conferir na tela:**
  - O aplicativo **não fecha/crash** (a exceção é capturada com segurança).
  - Aparece a mensagem de erro na tela: **"Não foi possível realizar esta operação. Tente novamente."** (`R.string.error_db`).
  - Os dados digitados nos campos permanecem intactos, permitindo que o usuário tente salvar novamente sem ter que reescrever tudo.
- **Reversão imediata:** Remova a linha `throw RuntimeException(...)` do `PrintFlowRepository.kt`.

---

#### Cenário 5B: Falha ao Abrir o WhatsApp (Intent Indisponível)

- **Objetivo:** Simular o cenário onde o dispositivo não possui o aplicativo do WhatsApp instalado ou a URI da Intent falha.
- **Como provocar:**
  1. Abra o arquivo `app/src/main/java/br/edu/ifpe/projeto_final_print_flow/ui/screens/details/DetailsScreen.kt`.
  2. No manipulador do botão de envio ao WhatsApp (`PrintFlowButton`), simule uma `ActivityNotFoundException` temporária antes de invocar `context.startActivity(intent)`.

  *Trecho de alteração temporária em `DetailsScreen.kt`:*
  ```kotlin
  // No clique do botão Enviar pelo WhatsApp:
  onClick = {
      scope.launch {
          val telefone = repository.buscarTelefoneDoCliente(atual.cliente)?.filter { it.isDigit() }
          
          // === ALTERAÇÃO TEMPORÁRIA DA SABOTAGEM 5B ===
          throw ActivityNotFoundException("WhatsApp não instalado no dispositivo")
          // ============================================

          if (telefone.isNullOrBlank()) {
              errorMessage = context.getString(R.string.error_whatsapp)
          } else {
              // ...
  ```

  3. Abra os detalhes de um orçamento e clique no botão **"Enviar pelo WhatsApp"**.
- **O que conferir na tela:**
  - O aplicativo trata a falha sem fechar.
  - Aparece na tela o aviso: **"Não foi possível abrir o WhatsApp. Tente novamente."** (`R.string.error_whatsapp`).
  - Os detalhes do orçamento continuam sendo exibidos normalmente.
- **Reversão imediata:** Remova a injeção da exceção `throw ActivityNotFoundException(...)` em `DetailsScreen.kt`.

---

## 3. Modelo Padrão de Registro para `docs/sabotagem.md`

Abaixo está a estrutura pronta que deverá ser preenchida e gravada em `docs/sabotagem.md` durante e após a execução dos testes.

```markdown
# Registro de Testes de Sabotagem — Print-Flow

**Data da execução:** YYYY-MM-DD  
**Responsável:** Integrante 4  
**Aparelho utilizado:** [Ex.: Motorola Moto G84 / Emulador Pixel 7]  
**Versão do Android:** [Ex.: Android 14 / API 34]  

---

## Tabela Resumo dos Testes

| Teste | Nome da Sabotagem | Arquivo Envolvido | Comportamento Esperado Exibido? | Sabotagem Revertida? |
|---|---|---|---|---|
| S01 | Lista vazia | `HomeScreen.kt` / Room DB | Sim ("Nenhum orçamento cadastrado ainda.") | N/A (Dados) |
| S02 | Sem internet | `DetailsScreen.kt` | Sim ("Sem conexão...", app mantido aberto) | Sim |
| S03 | Carregando visível | `HomeViewModel.kt` | Sim ("Carregando orçamentos...") | Sim |
| S04 | Campo obrigatório vazio | `BudgetScreen.kt` | Sim ("Preencha todos os campos obrigatórios.") | N/A (Validação) |
| S05A | Erro no banco (Room) | `PrintFlowRepository.kt` | Sim ("Não foi possível realizar esta operação...") | Sim |
| S05B | Falha ao abrir WhatsApp | `DetailsScreen.kt` | Sim ("Não foi possível abrir o WhatsApp...") | Sim |

---

## Detalhamento por Sabotagem

### 1. Sabotagem 1: Lista Vazia
- **O que foi feito:** Realizada a limpeza de dados do aplicativo no dispositivo para simular a primeira inicialização sem registros no Room.
- **Arquivo alterado:** Nenhum (operação de banco de dados zerado).
- **O que apareceu na tela:** Exibição centralizada do texto `"Nenhum orçamento cadastrado ainda."` e FAB de criação de orçamento ativo.
- **Conformidade com o PRD:** Conforme Seção 9 do PRD (Tratamento de erros).
- **Sabotagem desfeita:** N/A (bastou cadastrar um novo orçamento para popular a lista).
- **Dispositivo e Android:** [Preencher modelo do aparelho e versão do Android].

---

### 2. Sabotagem 2: Sem Internet
- **O que foi feito:** Ativação do Modo Avião no dispositivo e tentativa de envio da proposta via WhatsApp.
- **Arquivo alterado:** `DetailsScreen.kt` (teste comportamental de hardware).
- **O que apareceu na tela:** O app permaneceu aberto sem falhar; exibiu mensagem de indisponibilidade de conexão e permitiu continuar navegando pelas telas offline normalmente.
- **Conformidade com o PRD:** Conforme Seção 9 do PRD.
- **Sabotagem desfeita:** Desativação do Modo Avião no dispositivo.
- **Dispositivo e Android:** [Preencher modelo do aparelho e versão do Android].

---

### 3. Sabotagem 3: Carregando Visível
- **O que foi feito:** Inserção temporária de `delay(2000)` na rotina `carregarDados()` do `HomeViewModel`.
- **Arquivo alterado:** `app/src/main/java/br/edu/ifpe/projeto_final_print_flow/ui/screens/home/HomeViewModel.kt`
- **O que apareceu na tela:** Mensagem `"Carregando orçamentos..."` exibida por 2 segundos antes de exibir o conteúdo principal.
- **Conformidade com o PRD:** Conforme Seção 9 do PRD e padrões de estado UI.
- **Sabotagem desfeita:** Sim, trecho de `delay` removido e verificado via `git diff`.
- **Dispositivo e Android:** [Preencher modelo do aparelho e versão do Android].

---

### 4. Sabotagem 4: Campo Obrigatório Vazio
- **O que foi feito:** Tentativa de salvamento no formulário de Novo Orçamento omitindo campos obrigatórios.
- **Arquivo alterado:** `BudgetScreen.kt` (teste de validação de formulário).
- **O que apareceu na tela:** Mensagem em destaque vermelho `"Preencha todos os campos obrigatórios."` e retenção dos dados preenchidos.
- **Conformidade com o PRD:** Conforme Seção 9 do PRD.
- **Sabotagem desfeita:** N/A (comportamento nativo de validação mantido no app).
- **Dispositivo e Android:** [Preencher modelo do aparelho e versão do Android].

---

### 5. Sabotagem 5: Erros de Operações Externas e Banco
- **O que foi feito:**
  - *5A (Room):* Lançamento forçado de `RuntimeException` em `PrintFlowRepository.kt` (`salvarOrcamento`).
  - *5B (WhatsApp):* Lançamento forçado de `ActivityNotFoundException` em `DetailsScreen.kt`.
- **Arquivos alterados:** `PrintFlowRepository.kt` e `DetailsScreen.kt`.
- **O que apareceu na tela:**
  - *5A:* Mensagem `"Não foi possível realizar esta operação. Tente novamente."` sem crash e sem perda de dados do formulário.
  - *5B:* Mensagem `"Não foi possível abrir o WhatsApp. Tente novamente."` com dados mantidos na tela.
- **Conformidade com o PRD:** Conforme Seção 9 do PRD e RNF03.
- **Sabotagem desfeita:** Sim, exceções forçadas removidas de ambos os arquivos.
- **Dispositivo e Android:** [Preencher modelo do aparelho e versão do Android].
```

---

## 6. Checklist Final de Reversão e Verificação via Git

Antes de finalizar as atividades e efetuar qualquer commit no repositório, execute rigorosamente o checklist abaixo utilizando o terminal do Git ou do Android Studio:

### Checklist de Reversão de Código

- [ ] **Reversão da Sabotagem 3:** Verificar se o `delay(2000)` foi removido de `HomeViewModel.kt`.
- [ ] **Reversão da Sabotagem 5A:** Verificar se o `throw RuntimeException(...)` foi removido de `PrintFlowRepository.kt`.
- [ ] **Reversão da Sabotagem 5B:** Verificar se o `throw ActivityNotFoundException(...)` foi removido de `DetailsScreen.kt`.
- [ ] **Estado dos Dados:** Cadastrar um orçamento de teste para garantir que a base do Room voltou ao funcionamento normal.

---

### Comandos Git Obrigatórios para Validação

1. **Verificar estado dos arquivos no projeto:**
   ```bash
   git status
   ```
   *Resultado esperado:* NENHUM arquivo fonte de código Kotlin (`.kt`) na pasta `src/app/` deve figurar como modificado. Apenas o arquivo de documentação `docs/sabotagem.md` deve constar para commit.

2. **Inspecionar modificações pendentes linha a linha:**
   ```bash
   git diff
   ```
   *Resultado esperado:* O comando não deve retornar nenhuma diferença nos arquivos Kotlin do app. Se alguma alteração de código temporária aparecer na saída do `git diff`, desfaça-a imediatamente.

3. **Desfazer alterações temporárias residuais (se houver):**
   ```bash
   git checkout -- app/src/main/java/br/edu/ifpe/projeto_final_print_flow/
   # ou
   git restore app/src/main/java/br/edu/ifpe/projeto_final_print_flow/
   ```

4. **Confirmar que o projeto compila perfeitamente sem erros:**
   ```bash
   ./gradlew assembleDebug
   ```

Com estas etapas concluídas e validadas via Git, a Integrante 4 garante a entrega de um teste de sabotagem completo, auditável, 100% aderente ao PRD e livre de efeitos colaterais no código fonte do projeto Print-Flow.
