package br.edu.ifpe.printflow.ui.screens.budget

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
import br.edu.ifpe.printflow.R
import br.edu.ifpe.printflow.ui.components.PrintFlowButton
import br.edu.ifpe.printflow.ui.components.PrintFlowTextField

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewBudgetScreen(
    onBackClick: () -> Unit,
    onCalculateClick: () -> Unit
) {
    var clientName by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var serviceType by remember { mutableStateOf("") }
    var quantity by remember { mutableStateOf("") }
    var features by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = stringResource(id = R.string.budget_new_title), fontWeight = FontWeight.Bold) },
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
            PrintFlowTextField(
                value = clientName,
                onValueChange = { clientName = it },
                label = stringResource(id = R.string.budget_client_name)
            )

            PrintFlowTextField(
                value = phone,
                onValueChange = { phone = it },
                label = stringResource(id = R.string.budget_phone)
            )

            PrintFlowTextField(
                value = serviceType,
                onValueChange = { serviceType = it },
                label = stringResource(id = R.string.budget_service)
            )

            PrintFlowTextField(
                value = quantity,
                onValueChange = { quantity = it },
                label = stringResource(id = R.string.budget_quantity)
            )

            OutlinedTextField(
                value = features,
                onValueChange = { features = it },
                label = { Text(stringResource(id = R.string.budget_features)) },
                modifier = Modifier.fillMaxWidth().height(120.dp),
                shape = MaterialTheme.shapes.medium
            )

            Spacer(modifier = Modifier.height(16.dp))

            PrintFlowButton(
                text = stringResource(id = R.string.budget_calculate),
                onClick = onCalculateClick
            )
        }
    }
}
