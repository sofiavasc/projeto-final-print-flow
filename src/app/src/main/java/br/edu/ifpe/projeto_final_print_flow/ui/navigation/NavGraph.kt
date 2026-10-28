package br.edu.ifpe.projeto_final_print_flow.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import br.edu.ifpe.projeto_final_print_flow.ui.screens.login.LoginScreen
import br.edu.ifpe.projeto_final_print_flow.ui.screens.home.HomeScreen
import br.edu.ifpe.projeto_final_print_flow.ui.screens.budget.BudgetScreen
import br.edu.ifpe.projeto_final_print_flow.ui.screens.details.DetailsScreen

sealed class Screen(val route: String) {
    object Login : Screen("login")
    object Home : Screen("home")
    object NewBudget : Screen("new_budget")
    object Details : Screen("details/{id}") {
        fun createRoute(id: Long) = "details/$id"
    }
}

@Composable
fun SetupNavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Screen.Login.route
    ) {
        composable(Screen.Login.route) {
            LoginScreen(
                onLoginClick = {
                    navController.navigate(Screen.Home.route) {
                        popUpTo(Screen.Login.route) { inclusive = true }
                    }
                }
            )
        }
        composable(Screen.Home.route) {
            HomeScreen(
                onAddClick = { navController.navigate(Screen.NewBudget.route) },
                onItemClick = { id -> navController.navigate(Screen.Details.createRoute(id)) }
            )
        }
        composable(Screen.NewBudget.route) {
            BudgetScreen(
                onBackClick = { navController.popBackStack() },
                onSaveClick = { navController.navigate(Screen.Home.route) }
            )
        }
        composable(Screen.Details.route) { backStackEntry ->
            val id = backStackEntry.arguments?.getString("id")?.toLongOrNull() ?: 0L
            DetailsScreen(
                id = id,
                onBackClick = { navController.popBackStack() }
            )
        }
    }
}
