// Limite da tela: criacao e calculo de orcamentos.
package br.edu.ifpe.projeto_final_print_flow.ui.screens.budget

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import br.edu.ifpe.projeto_final_print_flow.R
import br.edu.ifpe.projeto_final_print_flow.data.repository.PrintFlowRepository
import br.edu.ifpe.projeto_final_print_flow.ui.components.PrintFlowButton
import br.edu.ifpe.projeto_final_print_flow.ui.components.PrintFlowTextField
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BudgetScreen(
    onBackClick: () -> Unit,
    onSaveClick: () -> Unit
) {
    val context = LocalContext.current
    val repository = remember { PrintFlowRepository(context) }
    val scope = rememberCoroutineScope()
    var cliente by remember { mutableStateOf("") }
    var telefone by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var servico by remember { mutableStateOf("") }
    var precoUnitario by remember { mutableStateOf("") }
    var quantidade by remember { mutableStateOf("") }
    var caracteristicas by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(id = R.string.new_budget_fab)) },
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
            PrintFlowTextField(
                value = cliente,
                onValueChange = { cliente = it },
                label = stringResource(id = R.string.client_name_label)
            )
            PrintFlowTextField(
                value = telefone,
                onValueChange = { telefone = it },
                label = stringResource(id = R.string.client_phone_label),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone)
            )
            PrintFlowTextField(
                value = email,
                onValueChange = { email = it },
                label = stringResource(id = R.string.client_email_label),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
            )
            
            Spacer(modifier = Modifier.height(8.dp))
            HorizontalDivider()
            Spacer(modifier = Modifier.height(8.dp))

            PrintFlowTextField(
                value = servico,
                onValueChange = { servico = it },
                label = stringResource(id = R.string.service_label)
            )
            PrintFlowTextField(
                value = precoUnitario,
                onValueChange = { precoUnitario = it },
                label = stringResource(id = R.string.unit_price_label),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal)
            )
            PrintFlowTextField(
                value = quantidade,
                onValueChange = { quantidade = it },
                label = stringResource(id = R.string.quantity_label),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )
            PrintFlowTextField(
                value = caracteristicas,
                onValueChange = { caracteristicas = it },
                label = stringResource(id = R.string.characteristics_label)
            )

            errorMessage?.let {
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = it, color = MaterialTheme.colorScheme.error)
            }
            Spacer(modifier = Modifier.height(24.dp))

            PrintFlowButton(
                text = stringResource(id = R.string.calculate_save_button),
                onClick = {
                    errorMessage = null
                    val clienteNome = cliente.trim()
                    val fone = telefone.trim()
                    val emailCliente = email.trim()
                    val nomeServico = servico.trim()
                    val caracteristicasTexto = caracteristicas.trim().ifBlank { nomeServico }

                    val quantidadeInt = quantidade.trim().toIntOrNull()
                    val precoClean = precoUnitario.trim()
                        .replace("R$", "", ignoreCase = true)
                        .replace(" ", "")
                        .replace(',', '.')
                    val preco = precoClean.toDoubleOrNull()

                    if (clienteNome.isBlank() || fone.isBlank() || nomeServico.isBlank()) {
                        errorMessage = context.getString(R.string.error_fields_required)
                    } else if (quantidadeInt == null || quantidadeInt <= 0) {
                        errorMessage = context.getString(R.string.error_invalid_quantity)
                    } else if (preco == null || preco <= 0.0) {
                        errorMessage = context.getString(R.string.error_invalid_price)
                    } else {
                        scope.launch {
                            val result = repository.salvarOrcamento(
                                clienteNome = clienteNome,
                                telefone = fone,
                                email = emailCliente,
                                nomeServico = nomeServico,
                                quantidade = quantidadeInt,
                                caracteristicas = caracteristicasTexto,
                                precoUnitario = preco
                            )
                            if (result.isSuccess) onSaveClick()
                            else errorMessage = context.getString(R.string.error_db)
                        }
                    }
                }
            )
        }
    }
}
