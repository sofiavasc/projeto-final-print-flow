package br.edu.ifpe.printflow.ui.screens.home

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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.edu.ifpe.printflow.R
import br.edu.ifpe.printflow.model.Budget
import br.edu.ifpe.printflow.model.BudgetStatus
import br.edu.ifpe.printflow.ui.components.BudgetCard
import br.edu.ifpe.printflow.ui.components.PrintFlowTextField

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    onAddBudgetClick: () -> Unit,
    onBudgetClick: (Budget) -> Unit
) {
    var searchQuery by remember { mutableStateOf("") }
    
    // Mock Data
    val budgets = listOf(
        Budget("1", "João Silva", "81999999999", "Cartão de Visita", 1000, "Papel Couché 300g", 150.0, BudgetStatus.PENDENTE),
        Budget("2", "Maria Oliveira", "81988888888", "Panfletos", 5000, "Papel Offset 90g", 350.0, BudgetStatus.EM_PRODUCAO),
        Budget("3", "Gráfica Rápida", "81977777777", "Banner", 1, "Lona com Ilhós", 80.0, BudgetStatus.CONCLUIDO),
        Budget("4", "José Santos", "81966666666", "Adesivos", 100, "Vinil Transparente", 120.0, BudgetStatus.ORCAMENTO)
    )

    val filteredBudgets = budgets.filter { 
        it.clientName.contains(searchQuery, ignoreCase = true) || 
        it.serviceType.contains(searchQuery, ignoreCase = true)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { 
                    Text(
                        text = stringResource(id = R.string.home_title),
                        fontWeight = FontWeight.Bold
                    ) 
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = Color.White
                )
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = onAddBudgetClick,
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = Color.White
            ) {
                Icon(Icons.Default.Add, contentDescription = "Novo Orçamento")
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ) {
            PrintFlowTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                label = stringResource(id = R.string.home_search_hint),
                trailingIcon = { Icon(Icons.Default.Search, contentDescription = null) }
            )

            Spacer(modifier = Modifier.height(16.dp))

            if (filteredBudgets.isEmpty()) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(text = stringResource(id = R.string.home_empty_list))
                }
            } else {
                LazyColumn(modifier = Modifier.fillMaxSize()) {
                    items(filteredBudgets) { budget ->
                        BudgetCard(budget = budget, onClick = { onBudgetClick(budget) })
                    }
                }
            }
        }
    }
}
