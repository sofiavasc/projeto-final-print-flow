// Limite do pacote: estados de interface do Print-Flow.
package br.edu.ifpe.projeto_final_print_flow.ui

import br.edu.ifpe.projeto_final_print_flow.data.Orcamento

sealed interface UiState {
    data object Carregando : UiState

    data object Vazio : UiState

    data class Erro(
        val mensagem: String
    ) : UiState

    data class Sucesso(
        val orcamentos: List<Orcamento>
    ) : UiState
}
