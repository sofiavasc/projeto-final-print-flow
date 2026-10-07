package br.edu.ifpe.printflow.ui.screens.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.edu.ifpe.printflow.R
import br.edu.ifpe.printflow.ui.components.PrintFlowButton
import br.edu.ifpe.printflow.ui.components.PrintFlowTextField

@Composable
fun LoginScreen(onLoginSuccess: () -> Unit) {
    var user by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Logo Placeholder
        Text(
            text = stringResource(id = R.string.app_name),
            fontSize = 32.sp,
            fontWeight = FontWeight.Black,
            color = MaterialTheme.colorScheme.primary
        )
        
        Spacer(modifier = Modifier.height(8.dp))
        
        Text(
            text = stringResource(id = R.string.login_title),
            fontSize = 16.sp,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        Spacer(modifier = Modifier.height(32.dp))

        PrintFlowTextField(
            value = user,
            onValueChange = { user = it },
            label = stringResource(id = R.string.login_user)
        )

        Spacer(modifier = Modifier.height(16.dp))

        PrintFlowTextField(
            value = email,
            onValueChange = { email = it },
            label = stringResource(id = R.string.login_email)
        )

        Spacer(modifier = Modifier.height(16.dp))

        PrintFlowTextField(
            value = password,
            onValueChange = { password = it },
            label = stringResource(id = R.string.login_password),
            visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            trailingIcon = {
                IconButton(onClick = { passwordVisible = !passwordVisible }) {
                    val icon = if (passwordVisible) "Hide" else "Show" // Replace with actual icons later
                    Text(text = icon, fontSize = 10.sp)
                }
            }
        )

        Spacer(modifier = Modifier.height(32.dp))

        PrintFlowButton(
            text = stringResource(id = R.string.login_button),
            onClick = onLoginSuccess
        )
    }
}
