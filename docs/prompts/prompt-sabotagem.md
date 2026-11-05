
## Contexto

Sou a **integrante 4** do **grupo 4**, que desenvolve o app **Print-Flow**.

- **Tecnologias:** Kotlin, Jetpack Compose e Room (opção C)
- **Pacote:** `br.edu.ifpe.printflow` (pastas `ui/` e `data/`)
- **Dados:** salvos localmente no Room
- **Envio ao cliente:** feito por uma **Intent** do Android (`android.intent.action.VIEW` com URI `https://wa.me/`), que abre o WhatsApp com número e mensagem preenchidos
- **Atenção:** o app **não usa API REST nem Retrofit**

---

## Minha responsabilidade: teste de sabotagem e documentação

Preciso de um **plano de planejamento, passo a passo e em ordem de execução**, para realizar estas atividades:

- Criar o arquivo `docs/sabotagem.md`
- Organizar nele o registro das **cinco sabotagens**
- Anotar o que apareceu na tela em cada teste
- Conferir se todas as sabotagens temporárias foram desfeitas no código

---

## As cinco sabotagens (adaptadas ao app)

### Sabotagem 1: Lista vazia

- **Como provocar:** apagar todos os orçamentos e pedidos ou reinstalar o aplicativo
- **Conferir:** a tela principal mostra o estado vazio com a mensagem **"Nenhum orçamento cadastrado ainda."**

### Sabotagem 2: Sem internet

- **Como provocar:** ativar o **modo avião** e tentar enviar um orçamento pelo WhatsApp
- **Conferir:**
  ° o app mantém a tela e **não fecha**
  ° aparece o aviso **"Sem conexão. Verifique a internet e tente novamente."** com o botão **"Tentar de novo"**
- **Lembrete:** o restante do app deve continuar funcionando offline, pois usa o Room

### Sabotagem 3: Carregando visível

- **Como provocar:** conferir se existe um indicador de carregamento ao abrir a lista ou ao calcular o orçamento. Como o Room responde rápido demais, usar um **`delay(2000)` temporário** no ViewModel ou no repositório
- **Conferir:** o indicador aparece durante o atraso
- **Atenção:** se o app ainda não tiver esse indicador implementado, me avise

### Sabotagem 4: Campo obrigatório vazio

- **Como provocar:** tentar salvar um novo orçamento sem preencher os campos
- **Conferir:**
  ° o salvamento é impedido
  ° o campo é destacado
  ° aparece **"Preencha todos os campos obrigatórios."**

### Sabotagem 5: Erro de servidor

No nosso caso **não envolve URL de API**, e sim falhas nas duas operações externas do app, provocadas de forma **temporária**:

**Erro no banco (Room)**

- **Como provocar:** forçar uma exceção em um método do DAO ou do repositório
- **Conferir:** aparece **"Não foi possível realizar esta operação. Tente novamente."**, o app não fecha e os dados digitados não se perdem

**Falha ao abrir o WhatsApp**

- **Como provocar:** lançar `ActivityNotFoundException` ou usar uma URI inválida
- **Conferir:** aparece **"Não foi possível abrir o WhatsApp. Tente novamente."** e os dados do orçamento continuam na tela

---

## O que preciso que o plano traga

- **Análise do projeto aberto:** identificar em quais telas, ViewModels, repositórios e DAOs cada sabotagem deve ser aplicada
- **Alterações temporárias:** dizer exatamente quais **arquivos e trechos** vou alterar, para eu reverter cada mudança sem dúvida
- **Modelo de registro para o `docs/sabotagem.md`**, com uma seção por sabotagem contendo:
  ° o que foi feito
  ° o arquivo alterado
  ° o que apareceu na tela (mensagem, estado vazio, indicador, destaque do campo etc.)
  ° se o comportamento foi o esperado, conforme a seção de **tratamento de erros do PRD**
  ° se a sabotagem foi desfeita
  ° o aparelho e a versão do Android usados no teste
- **Checklist final de reversão**
- **Uso do Git:** como usar `git diff` e `git status` para garantir que nenhuma alteração temporária (`delay(2000)`, exceção forçada ou URI inválida) tenha ficado no código antes do commit

---

## Regra importante

**Não altere nenhum arquivo ainda.** Quero apenas o **plano detalhado**, para eu executar e conferir cada etapa.
