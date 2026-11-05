# Registro de Testes de Sabotagem — Print-Flow

**Data da execução:** 2026-11-04  
**Responsável:** Sofia Vitória
**Projeto:** Print-Flow  
**Aparelho utilizado:** Xiaomi Redmi 14C  
**Versão do Android:** Android 14 (API 34)  

---

## Tabela Resumo dos Testes

| Teste | Nome da Sabotagem | Arquivo Envolvido | Comportamento Esperado Exibido? | Sabotagem Revertida? |
|---|---|---|---|---|
| S01 | Lista vazia | `HomeScreen.kt` / Room DB | Sim ("Nenhum orçamento cadastrado ainda.") | N/A (Dados) |
| S02 | Sem internet | `DetailsScreen.kt` | Sim ("Sem conexão. Tente novamente", app mantido aberto) | Sim |
| S03 | Carregando visível | `HomeViewModel.kt` | Sim ("Carregando orçamentos...") | Sim |
| S04 | Campo obrigatório vazio | `BudgetScreen.kt` | Sim ("Preencha todos os campos obrigatórios.") | N/A (Validação) |
| S05A | Erro no banco (Room) | `PrintFlowRepository.kt` | Sim ("Não foi possível realizar esta operação...") | Sim |
| S05B | Falha ao abrir WhatsApp | `DetailsScreen.kt` | Sim ("Não foi possível abrir o WhatsApp...") | Sim |

---

## Detalhamento por Sabotagem

### 1. Sabotagem 1: Lista Vazia
- **O que foi feito:** Realizada a limpeza de dados do aplicativo/banco Room para simular a primeira inicialização sem registros.
- **Arquivo alterado:** Nenhum (operação de dados zerados no Room).
- **O que apareceu na tela:** Exibição centralizada do texto `"Nenhum orçamento cadastrado ainda."` e FAB de criação de orçamento ativo.
- **Conformidade com o PRD:** Conforme Seção 9 do PRD (Tratamento de erros / RF01).
- **Sabotagem desfeita:** N/A (bastou cadastrar um novo orçamento para popular a lista).
- **Dispositivo e Android:** Xiaomi Redmi 14C / Android 14.

---

### 2. Sabotagem 2: Sem Internet
- **O que foi feito:** Ativação do Modo Avião no dispositivo e tentativa de envio da proposta via WhatsApp na `DetailsScreen.kt`.
- **Arquivo alterado:** `DetailsScreen.kt` (teste comportamental de hardware/rede).
- **O que apareceu na tela:** O app permaneceu aberto sem fechar (*crash*); exibiu mensagem de indisponibilidade de conexão e permitiu continuar navegando pelas telas offline normalmente.
- **Conformidade com o PRD:** Conforme Seção 9 do PRD e RNF03.
- **Sabotagem desfeita:** Desativação do Modo Avião no dispositivo.
- **Dispositivo e Android:** Xiaomi Redmi 14C / Android 14.

---

### 3. Sabotagem 3: Carregando Visível
- **O que foi feito:** Inserção temporária de `kotlinx.coroutines.delay(2000)` na rotina `carregarDados()` do `HomeViewModel.kt`.
- **Arquivo alterado:** `app/src/main/java/br/edu/ifpe/projeto_final_print_flow/ui/screens/home/HomeViewModel.kt`
- **O que apareceu na tela:** Mensagem `"Carregando orçamentos..."` exibida por 2 segundos antes de exibir o conteúdo principal.
- **Conformidade com o PRD:** Conforme Seção 9 do PRD e padrões de estado UI (`UiState.Carregando`).
- **Sabotagem desfeita:** Sim, o trecho de `delay(2000)` foi removido do código.
- **Dispositivo e Android:** Xiaomi Redmi 14C / Android 14.

---

### 4. Sabotagem 4: Campo Obrigatório Vazio
- **O que foi feito:** Tentativa de salvamento no formulário de Novo Orçamento (`BudgetScreen.kt`) omitindo campos obrigatórios.
- **Arquivo alterado:** `BudgetScreen.kt` e `strings.xml`.
- **O que apareceu na tela:** Mensagem em destaque vermelho `"Preencha todos os campos obrigatórios."` e retenção dos dados preenchidos nos demais campos.
- **Conformidade com o PRD:** Conforme Seção 9 do PRD e RF02.
- **Sabotagem desfeita:** N/A (comportamento nativo de validação mantido e aprimorado no app).
- **Dispositivo e Android:** Xiaomi Redmi 14C / Android 14.

---

### 5. Sabotagem 5: Erros de Operações Externas e Banco
- **O que foi feito:**
  - *5A (Room):* Lançamento de `RuntimeException("Falha forçada no banco de dados Room")` em `PrintFlowRepository.kt` (`salvarOrcamento`).
  - *5B (WhatsApp):* Lançamento de `ActivityNotFoundException("WhatsApp não instalado no dispositivo")` em `DetailsScreen.kt`.
- **Arquivos alterados:** `PrintFlowRepository.kt` e `DetailsScreen.kt`.
- **O que apareceu na tela:**
  - *5A:* Mensagem `"Não foi possível realizar esta operação. Tente novamente."` sem fechar o app e mantendo os dados digitados intactos no formulário.
  - *5B:* Mensagem `"Não foi possível abrir o WhatsApp. Tente novamente."` com dados mantidos na tela sem fechar o app.
- **Conformidade com o PRD:** Conforme Seção 9 do PRD e RNF03.
- **Sabotagem desfeita:** Sim, as exceções temporárias foram removidas do código fonte e a compilação foi validada via Gradle (`assembleDebug`).
- **Dispositivo e Android:** Xiaomi Redmi 14C / Android 14.

---

## 4. Checklist Final de Reversão e Verificação via Git

### Status Atual das Alterações no Código
Todas as alterações temporárias relativas às Sabotagens 3, 5A e 5B foram **100% revertidas** do código fonte Kotlin (`.kt`).

### Verificação Concluída
1. **Removido o `delay(2000)`** do arquivo `HomeViewModel.kt`.
2. **Removido o `throw RuntimeException(...)`** do arquivo `PrintFlowRepository.kt`.
3. **Removido o `throw ActivityNotFoundException(...)`** do arquivo `DetailsScreen.kt`.
4. **Execução do `./gradlew assembleDebug`:** Compilação final concluída com sucesso (`BUILD SUCCESSFUL`).
