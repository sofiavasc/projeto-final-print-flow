// Limite da tela: entrada de credenciais e acesso ao fluxo principal.
package br.edu.ifpe.projeto_final_print_flow.ui.screens.login

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.edu.ifpe.projeto_final_print_flow.R
import br.edu.ifpe.projeto_final_print_flow.data.repository.PrintFlowRepository
import br.edu.ifpe.projeto_final_print_flow.ui.components.PrintFlowButton
import br.edu.ifpe.projeto_final_print_flow.ui.components.PrintFlowTextField
import kotlinx.coroutines.launch

@Composable
fun LoginScreen(onLoginClick: () -> Unit) {
    val context = LocalContext.current
    val repository = remember { PrintFlowRepository(context) }
    val scope = rememberCoroutineScope()
    var usuario by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var senha by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    var isRegisterMode by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf<String?>(null) }
    var successMessage by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(Unit) { repository.inicializarLogin() }

    Column(
        modifier = Modifier.fillMaxSize().padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Print-Flow",
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.padding(bottom = 24.dp)
        )
        Text(
            text = if (isRegisterMode) stringResource(id = R.string.register_title) else stringResource(id = R.string.login_title),
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier.padding(bottom = 16.dp)
        )
        PrintFlowTextField(
            value = usuario,
            onValueChange = { usuario = it },
            label = stringResource(id = R.string.user_label)
        )
        PrintFlowTextField(
            value = email,
            onValueChange = { email = it },
            label = stringResource(id = R.string.login_email_label)
        )
        PrintFlowTextField(
            value = senha,
            onValueChange = { senha = it },
            label = stringResource(id = R.string.password_label),
            visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            trailingIcon = {
                val image = if (passwordVisible) Icons.Filled.Visibility else Icons.Filled.VisibilityOff
                IconButton(onClick = { passwordVisible = !passwordVisible }) {
                    Icon(imageVector = image, contentDescription = null)
                }
            }
        )
        errorMessage?.let {
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = it, color = MaterialTheme.colorScheme.error)
        }
        successMessage?.let {
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = it, color = MaterialTheme.colorScheme.primary)
        }
        Spacer(modifier = Modifier.height(20.dp))
        PrintFlowButton(
            text = if (isRegisterMode) stringResource(id = R.string.register_button) else stringResource(id = R.string.login_button),
            onClick = {
                errorMessage = null
                successMessage = null
                if (usuario.isBlank() || email.isBlank() || senha.isBlank()) {
                    errorMessage = context.getString(R.string.error_fields_required)
                } else if (isRegisterMode) {
                    scope.launch {
                        val result = repository.cadastrarLogin(usuario, email, senha)
                        if (result.isSuccess) {
                            successMessage = context.getString(R.string.success_register)
                            isRegisterMode = false
                        } else {
                            val exception = result.exceptionOrNull()
                            errorMessage = exception?.message ?: context.getString(R.string.error_user_exists)
                        }
                    }
                } else {
                    scope.launch {
                        val result = repository.validarLogin(usuario, email, senha)
                        if (result.isSuccess && result.getOrDefault(false)) {
                            onLoginClick()
                        } else {
                            errorMessage = context.getString(R.string.error_login_invalid)
                        }
                    }
                }
            }
        )
        Spacer(modifier = Modifier.height(12.dp))
        TextButton(
            onClick = {
                isRegisterMode = !isRegisterMode
                errorMessage = null
                successMessage = null
            }
        ) {
            Text(
                text = if (isRegisterMode) stringResource(id = R.string.switch_to_login) else stringResource(id = R.string.switch_to_register)
            )
        }
    }
}
