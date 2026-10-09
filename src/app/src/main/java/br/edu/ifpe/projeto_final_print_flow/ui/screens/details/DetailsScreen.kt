package br.edu.ifpe.projeto_final_print_flow.ui.screens.details

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import br.edu.ifpe.projeto_final_print_flow.R
import br.edu.ifpe.projeto_final_print_flow.data.Orcamento
import br.edu.ifpe.projeto_final_print_flow.ui.components.PrintFlowButton
import br.edu.ifpe.projeto_final_print_flow.ui.components.StatusBadge

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailsScreen(
    id: Long,
    onBackClick: () -> Unit
) {
    // Mock data for the specific ID
    val orcamento = remember(id) {
        Orcamento(
            id = id,
            cliente = "João Silva",
            servico = "Impressão A4",
            quantidade = 100,
            caracteristicas = "Papel Couché 150g, Frente e Verso, Colorido",
            valor = 50.0,
            status = "Pendente"
        )
    }

    var currentStatus by remember { mutableStateOf(orcamento.status) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(id = R.string.budget_details_title)) },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = null)
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
                .verticalScroll(rememberScrollState())
        ) {
            DetailItem(label = stringResource(id = R.string.client_name_label), value = orcamento.cliente)
            DetailItem(label = stringResource(id = R.string.service_label), value = orcamento.servico)
            DetailItem(label = stringResource(id = R.string.quantity_label), value = orcamento.quantidade.toString())
            DetailItem(label = stringResource(id = R.string.characteristics_label), value = orcamento.caracteristicas)
            
            Spacer(modifier = Modifier.height(16.dp))
            
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Total:",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "R$ ${String.format("%.2f", orcamento.valor)}",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )
            }

            Spacer(modifier = Modifier.height(8.dp))
            Row(verticalAlignment = androidx.compose.ui.Alignment.CenterVertically) {
                Text(text = "Status: ", fontWeight = FontWeight.Bold)
                StatusBadge(status = currentStatus)
            }

            Spacer(modifier = Modifier.height(24.dp))

            PrintFlowButton(
                text = stringResource(id = R.string.send_whatsapp_button),
                onClick = { /* Intent simulation */ },
                containerColor = Color(0xFF25D366) // WhatsApp Green
            )

            if (currentStatus.lowercase() == "pendente") {
                PrintFlowButton(
                    text = stringResource(id = R.string.approve_button),
                    onClick = { currentStatus = "Aprovado" }
                )
            } else if (currentStatus.lowercase() == "aprovado") {
                PrintFlowButton(
                    text = stringResource(id = R.string.update_status_button),
                    onClick = { currentStatus = "Finalizado" }
                )
            }
        }
    }
}

@Composable
fun DetailItem(label: String, value: String) {
    Column(modifier = Modifier.padding(vertical = 8.dp)) {
        Text(text = label, style = MaterialTheme.typography.labelLarge, color = Color.Gray)
        Text(text = value, style = MaterialTheme.typography.bodyLarge)
    }
}
