package br.edu.ifpe.printflow.ui.screens.details

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.edu.ifpe.printflow.R
import br.edu.ifpe.printflow.model.Budget
import br.edu.ifpe.printflow.model.BudgetStatus
import br.edu.ifpe.printflow.ui.components.PrintFlowButton
import br.edu.ifpe.printflow.ui.components.StatusBadge
import br.edu.ifpe.printflow.ui.theme.PrintBlue
import br.edu.ifpe.printflow.ui.theme.PrintPink

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailsScreen(
    budgetId: String?,
    onBackClick: () -> Unit
) {
    // Mock Data Fetching
    val budget = remember(budgetId) {
        // In a real app, this would come from a repository
        Budget(
            id = budgetId ?: "0",
            clientName = "João Silva",
            clientPhone = "81999999999",
            serviceType = "Cartão de Visita",
            quantity = 1000,
            features = "Papel Couché 300g, Verniz Localizado, 4x4",
            value = 150.0,
            status = BudgetStatus.PENDENTE
        )
    }

    var currentStatus by remember { mutableStateOf(budget.status) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = stringResource(id = R.string.details_title), fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Voltar")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = Color.White,
                    navigationIconContentColor = Color.White
                )
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = MaterialTheme.shapes.large,
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f))
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(text = budget.clientName, fontSize = 20.sp, fontWeight = FontWeight.Bold)
                        StatusBadge(status = currentStatus)
                    }
                    Text(text = budget.clientPhone, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    Spacer(modifier = Modifier.height(8.dp))
                    HorizontalDivider()
                    Spacer(modifier = Modifier.height(8.dp))
                    DetailItem(label = "Serviço", value = budget.serviceType)
                    DetailItem(label = "Quantidade", value = budget.quantity.toString())
                    DetailItem(label = "Características", value = budget.features)
                }
            }

            Text(
                text = stringResource(id = R.string.details_total, budget.value),
                fontSize = 24.sp,
                fontWeight = FontWeight.Black,
                color = PrintPink
            )

            Spacer(modifier = Modifier.height(8.dp))

            PrintFlowButton(
                text = stringResource(id = R.string.details_whatsapp),
                onClick = { /* Simular Intent */ },
                containerColor = Color(0xFF25D366) // WhatsApp Green
            )

            if (currentStatus == BudgetStatus.ORCAMENTO || currentStatus == BudgetStatus.PENDENTE) {
                PrintFlowButton(
                    text = stringResource(id = R.string.details_approve),
                    onClick = { currentStatus = BudgetStatus.EM_PRODUCAO },
                    containerColor = PrintBlue
                )
            } else if (currentStatus == BudgetStatus.EM_PRODUCAO) {
                PrintFlowButton(
                    text = "Marcar como Concluído",
                    onClick = { currentStatus = BudgetStatus.CONCLUIDO },
                    containerColor = Color(0xFF4CAF50)
                )
            }

            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}

@Composable
fun DetailItem(label: String, value: String) {
    Column(modifier = Modifier.padding(vertical = 4.dp)) {
        Text(text = label, fontSize = 12.sp, color = MaterialTheme.colorScheme.primary, fontWeight = FontWeight.Bold)
        Text(text = value, fontSize = 16.sp)
    }
}
