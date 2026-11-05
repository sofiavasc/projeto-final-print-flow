// Limite da tela: listar e buscar orcamentos persistidos.
package br.edu.ifpe.projeto_final_print_flow.ui.screens.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import br.edu.ifpe.projeto_final_print_flow.R
import br.edu.ifpe.projeto_final_print_flow.data.Orcamento
import br.edu.ifpe.projeto_final_print_flow.data.repository.PrintFlowRepository
import br.edu.ifpe.projeto_final_print_flow.ui.UiState
import br.edu.ifpe.projeto_final_print_flow.ui.components.StatusBadge

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    onAddClick: () -> Unit,
    onItemClick: (Long) -> Unit,
    viewModel: HomeViewModel = viewModel(
        factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                val context = LocalContext.current
                return HomeViewModel(PrintFlowRepository(context.applicationContext)) as T
            }
        }
    )
) {
    val uiState by viewModel.uiState.collectAsState(initial = UiState.Carregando)
    var searchQuery by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(id = R.string.home_title)) },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = Color.White
                )
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = onAddClick,
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = Color.White
            ) {
                Icon(Icons.Filled.Add, contentDescription = stringResource(id = R.string.new_budget_fab))
            }
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ) {
            when (uiState) {
                is UiState.Carregando -> {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Text(text = stringResource(id = R.string.home_loading_message))
                    }
                }

                is UiState.Vazio -> {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Text(text = stringResource(id = R.string.home_empty_state_message))
                    }
                }

                is UiState.Erro -> {
                    val mensagem = (uiState as UiState.Erro).mensagem
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(text = mensagem)
                        Spacer(modifier = Modifier.height(16.dp))
                        Button(onClick = { viewModel.tentarNovamente() }) {
                            Text(stringResource(id = R.string.home_retry_button))
                        }
                    }
                }

                is UiState.Sucesso -> {
                    val orcamentos = (uiState as UiState.Sucesso).orcamentos
                    val resultados = orcamentos.filter {
                        searchQuery.isBlank() ||
                            it.cliente.contains(searchQuery, ignoreCase = true) ||
                            it.servico.contains(searchQuery, ignoreCase = true)
                    }

                    Column(modifier = Modifier.fillMaxSize()) {
                        OutlinedTextField(
                            value = searchQuery,
                            onValueChange = { searchQuery = it },
                            modifier = Modifier.fillMaxWidth(),
                            placeholder = { Text(stringResource(id = R.string.search_hint)) },
                            leadingIcon = { Icon(Icons.Filled.Search, contentDescription = null) },
                            shape = MaterialTheme.shapes.medium
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        if (resultados.isEmpty()) {
                            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                                Text(text = stringResource(id = R.string.home_empty_state_message))
                            }
                        } else {
                            LazyColumn(
                                verticalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                items(resultados) { orcamento ->
                                    BudgetCard(orcamento = orcamento, onClick = { onItemClick(orcamento.id) })
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun BudgetCard(orcamento: Orcamento, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(text = orcamento.cliente, fontWeight = FontWeight.Bold, style = MaterialTheme.typography.titleMedium)
                Text(text = orcamento.servico, style = MaterialTheme.typography.bodyMedium)
                Text(
                    text = "R$ ${String.format("%.2f", orcamento.valor)}",
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.Bold
                )
            }
            StatusBadge(status = orcamento.status)
        }
    }
}
