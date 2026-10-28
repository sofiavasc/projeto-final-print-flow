// Limite da tela: detalhes persistidos, aprovacao, status e WhatsApp.
package br.edu.ifpe.projeto_final_print_flow.ui.screens.details

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import br.edu.ifpe.projeto_final_print_flow.R
import br.edu.ifpe.projeto_final_print_flow.data.repository.PrintFlowRepository
import br.edu.ifpe.projeto_final_print_flow.data.repository.PrintFlowStatus
import br.edu.ifpe.projeto_final_print_flow.ui.components.PrintFlowButton
import br.edu.ifpe.projeto_final_print_flow.ui.components.StatusBadge
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailsScreen(id: Long, onBackClick: () -> Unit) {
    val context = LocalContext.current
    val repository = remember { PrintFlowRepository(context) }
    val scope = rememberCoroutineScope()
    val orcamento by repository.observarOrcamento(id).collectAsState(initial = null)
    val pedido by repository.observarPedido(id).collectAsState(initial = null)
    var errorMessage by remember { mutableStateOf<String?>(null) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.budget_details_title)) },
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
        val atual = orcamento
        if (atual == null) {
            Box(modifier = Modifier.fillMaxSize().padding(padding), contentAlignment = androidx.compose.ui.Alignment.Center) {
                Text(stringResource(R.string.empty_list_message))
            }
        } else {
            Column(
                modifier = Modifier.fillMaxSize().padding(padding).padding(16.dp)
                    .verticalScroll(rememberScrollState())
            ) {
                DetailItem(stringResource(R.string.client_name_label), atual.cliente)
                DetailItem(stringResource(R.string.service_label), atual.servico)
                DetailItem(stringResource(R.string.quantity_label), atual.quantidade.toString())
                DetailItem(stringResource(R.string.characteristics_label), atual.caracteristicas)
                Spacer(modifier = Modifier.height(16.dp))
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    Text("Total:", style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold)
                    Text("R$ ${String.format("%.2f", atual.valor)}", style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
                }
                Spacer(modifier = Modifier.height(8.dp))
                Row(verticalAlignment = androidx.compose.ui.Alignment.CenterVertically) {
                    Text("Status: ", fontWeight = FontWeight.Bold)
                    StatusBadge(pedido?.status ?: atual.status)
                }
                errorMessage?.let {
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(it, color = MaterialTheme.colorScheme.error)
                }
                Spacer(modifier = Modifier.height(24.dp))
                PrintFlowButton(
                    text = stringResource(R.string.send_whatsapp_button),
                    containerColor = Color(0xFF25D366),
                    onClick = {
                        scope.launch {
                            val telefone = repository.buscarTelefoneDoCliente(atual.cliente)
                                ?.filter { it.isDigit() }
                            if (telefone.isNullOrBlank()) {
                                errorMessage = context.getString(R.string.error_whatsapp)
                            } else {
                                val mensagem = Uri.encode(
                                    "Olá ${atual.cliente}, segue a proposta: ${atual.servico}, " +
                                        "quantidade ${atual.quantidade}, características: ${atual.caracteristicas}, " +
                                        "valor R$ ${String.format("%.2f", atual.valor)}."
                                )
                                val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://wa.me/$telefone?text=$mensagem"))
                                try { context.startActivity(intent) }
                                catch (_: ActivityNotFoundException) {
                                    errorMessage = context.getString(R.string.error_whatsapp)
                                }
                            }
                        }
                    }
                )
                if (atual.status == PrintFlowStatus.PENDENTE) {
                    PrintFlowButton(
                        text = stringResource(R.string.approve_button),
                        onClick = {
                            scope.launch {
                                val result = repository.aprovarOrcamento(atual)
                                if (result.isFailure) errorMessage = context.getString(R.string.error_db)
                            }
                        }
                    )
                } else if (pedido?.status == PrintFlowStatus.EM_PRODUCAO) {
                    PrintFlowButton(
                        text = stringResource(R.string.update_status_button),
                        onClick = {
                            scope.launch {
                                val result = repository.finalizarPedido(pedido!!, atual)
                                if (result.isFailure) errorMessage = context.getString(R.string.error_db)
                            }
                        }
                    )
                }
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
