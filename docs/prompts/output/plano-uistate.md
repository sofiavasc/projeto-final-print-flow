
Local do arquivo e pacote correto
Consultar a estrutura atual de UI no projeto:
ui
HomeScreen.kt
ui/components/Components.kt
ui/navigation/NavGraph.kt
Verificar o padrão de organização dos pacotes do app.
Como o requisito é apenas criar um estado de interface, sem ViewModel nem tela, a opção mais simples e alinhada ao projeto é manter o arquivo em um pacote de UI geral, com base em br.edu.ifpe.projeto_final_print_flow.ui, sem criar estrutura adicional fora do necessário.
A decisão final do local será confirmada pela leitura dos arquivos de UI já existentes e pela regra de manter a organização mais direta.
Verificação da versão do Kotlin
Consultar o arquivo:
libs.versions.toml
Identificar a versão do Kotlin declarada no projeto.
O projeto já indica kotlin = "2.2.10", e essa informação deve ser confirmada antes da criação do arquivo para garantir compatibilidade com data object e sealed interface.
Entidade da lista no estado Sucesso
Consultar:
Entities.kt
HomeScreen.kt
Confirmar qual entidade representa a lista exibida na tela principal do app.
O estado Sucesso deve usar a entidade Orcamento, porque:
o PRD define a lista principal de orçamentos/pedidos na tela principal;
a tela HomeScreen.kt trabalha diretamente com Orcamento;
a entidade está definida em Entities.kt como data class Orcamento(...).
A lista do estado Sucesso será, então, List<Orcamento>.
Planejamento da estrutura do UiState
Consultar a regra do PRD e os requisitos de UI:
PRD.md
AGENTS.md
Definir a sealed interface UiState com os quatro estados exigidos:
Carregando como data object
Vazio como data object
Erro como data class com mensagem: String
Sucesso como data class com uma lista da entidade identificada
Manter a estrutura mínima e alinhada ao escopo solicitado, sem estados extras e sem implementação de ViewModel.
Não criar lógica adicional, nem telas, nem fluxos.
Comentário de fronteira e sequência final
Consultar:
AGENTS.md
a convenção visual dos arquivos já existentes no projeto
Incluir o comentário de fronteira exigido no topo do arquivo, conforme a regra do grupo.
Sequência de execução:
revisar AGENTS.md;
revisar PRD.md;
confirmar o pacote e a localização no diretório de UI;
confirmar a versão do Kotlin em libs.versions.toml;
confirmar a entidade Orcamento em Entities.kt e na tela principal;
preparar a definição da sealed interface UiState com os quatro estados solicitados;
incluir o comentário de fronteira;
aguardar aprovação do grupo antes de qualquer alteração no projeto.