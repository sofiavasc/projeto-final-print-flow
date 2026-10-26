package br.edu.ifpe.projeto_final_print_flow.ui.screens.budget

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
import androidx.compose.ui.unit.dp
import br.edu.ifpe.projeto_final_print_flow.R
import br.edu.ifpe.projeto_final_print_flow.ui.components.PrintFlowButton
import br.edu.ifpe.projeto_final_print_flow.ui.components.PrintFlowTextField
import br.edu.ifpe.projeto_final_print_flow.data.repository.PrintFlowRepository
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
                label = stringResource(id = R.string.client_phone_label)
            )
            PrintFlowTextField(
                value = email,
                onValueChange = { email = it },
                label = stringResource(id = R.string.client_email_label)
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
                label = stringResource(id = R.string.unit_price_label)
            )
            PrintFlowTextField(
                value = quantidade,
                onValueChange = { quantidade = it },
                label = stringResource(id = R.string.quantity_label)
            )
            PrintFlowTextField(
                value = caracteristicas,
                onValueChange = { caracteristicas = it },
                label = stringResource(id = R.string.characteristics_label),
                modifier = Modifier.height(120.dp)
            )

            errorMessage?.let {
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = it, color = MaterialTheme.colorScheme.error)
            }
            Spacer(modifier = Modifier.height(24.dp))

            PrintFlowButton(
                text = stringResource(id = R.string.calculate_save_button),
                onClick = {
                    val quantidadeInt = quantidade.toIntOrNull()
                    val preco = precoUnitario.replace(',', '.').toDoubleOrNull()
                    if (cliente.isBlank() || telefone.isBlank() || email.isBlank() ||
                        servico.isBlank() || quantidadeInt == null || quantidadeInt <= 0 ||
                        preco == null || preco <= 0.0 || caracteristicas.isBlank()
                    ) {
                        errorMessage = context.getString(R.string.error_fields_required)
                    } else {
                        scope.launch {
                            val result = repository.salvarOrcamento(
                                clienteNome = cliente,
                                telefone = telefone,
                                email = email,
                                nomeServico = servico,
                                quantidade = quantidadeInt,
                                caracteristicas = caracteristicas,
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
