Prompt usado para o Boilerplate inicial (commit inicial do projeto Android)
Feito por Sofia Vitória
Data: 23/09/2026

Estou criando um projeto Android chamado Print-Flow para um trabalho acadêmico.
Com base nas especificações abaixo, crie APENAS o boilerplate inicial do projeto, deixando a estrutura pronta para desenvolvimento posterior.
Contexto do projeto
Nome: Print-Flow
Package name:
`br.edu.ifpe.printflow`
O aplicativo será usado por pequenas gráficas para criar orçamentos, enviar propostas pelo WhatsApp, registrar pedidos e acompanhar seus status.
Stack obrigatória
* Kotlin
* Jetpack Compose
* Material 3
* Navigation Compose
* Room Database
* Retrofit 2 + Gson Converter
* Kotlin Coroutines + Flow
* KSP para o Room

Estrutura de pacotes desejada
`br.edu.ifpe.printflow/`
* `data/`
  * `local/`
  * `remote/`
  * `repository/`
* `model/`
* `ui/`
  * `theme/`
  * `navigation/`
  * `features/`

O que deve ser criado

1. Configurar os arquivos Gradle do projeto e do módulo app.
2. Configurar Kotlin, Compose, Material 3, Navigation Compose, Room, Retrofit, Gson, Coroutines e Flow.
3. Configurar o plugin KSP necessário para o Room.
4. Criar a estrutura de diretórios/pacotes indicada acima.
5. Criar `MainActivity.kt`.
6. Criar um tema básico em `ui/theme/`.
7. Criar `ui/navigation/NavGraph.kt`.
8. Criar `ui/navigation/NavTarget.kt`.
9. Criar apenas uma rota inicial chamada `Home`.
10. O `NavHost` deve abrir uma tela inicial simples contendo apenas o texto:
    "Print-Flow"

Restrições IMPORTANTES

NÃO criar neste momento:

* nenhuma entidade Room;
* nenhum DAO;
* nenhum Database;
* nenhuma interface Retrofit;
* nenhum Repository implementado;
* nenhum ViewModel;
* nenhuma regra de negócio;
* nenhuma tela de orçamento;
* nenhuma tela de pedido;
* nenhuma integração real com WhatsApp.

O objetivo é somente criar uma casca inicial COMPILÁVEL do projeto.

Depois de criar os arquivos, explique resumidamente o que foi criado e quais arquivos foram alterados.
Não invente funcionalidades além das solicitadas.

