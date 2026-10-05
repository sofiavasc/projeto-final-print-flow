package br.edu.ifpe.printflow

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import br.edu.ifpe.printflow.ui.navigation.NavGraph
import br.edu.ifpe.printflow.ui.theme.PrintFlowTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PrintFlowTheme {
                NavGraph()
            }
        }
    }
}
