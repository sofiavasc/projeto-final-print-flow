package br.edu.ifpe.projeto_final_print_flow.ui.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.edu.ifpe.projeto_final_print_flow.data.repository.PrintFlowRepository
import br.edu.ifpe.projeto_final_print_flow.ui.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class HomeViewModel(
    private val repository: PrintFlowRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<UiState>(UiState.Carregando)
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()

    init {
        carregarDados()
    }

    fun carregarDados() {
        viewModelScope.launch {
            _uiState.value = UiState.Carregando

            try {
                val orcamentos = repository.observarOrcamentos().first()

                _uiState.value = if (orcamentos.isEmpty()) {
                    UiState.Vazio
                } else {
                    UiState.Sucesso(orcamentos)
                }
            } catch (error: Exception) {
                _uiState.value = UiState.Erro(
                    error.message ?: "Não foi possível carregar os orçamentos. Tente novamente."
                )
            }
        }
    }

    fun tentarNovamente() {
        carregarDados()
    }
}
