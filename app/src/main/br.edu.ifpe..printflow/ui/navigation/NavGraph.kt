package br.edu.ifpe.printflow.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import br.edu.ifpe.printflow.ui.screens.budget.NewBudgetScreen
import br.edu.ifpe.printflow.ui.screens.details.DetailsScreen
import br.edu.ifpe.printflow.ui.screens.home.HomeScreen
import br.edu.ifpe.printflow.ui.screens.login.LoginScreen

@Composable
fun NavGraph() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = NavTarget.Login.route
    ) {
        composable(NavTarget.Login.route) {
            LoginScreen(
                onLoginSuccess = {
                    navController.navigate(NavTarget.Home.route) {
                        popUpTo(NavTarget.Login.route) { inclusive = true }
                    }
                }
            )
        }

        composable(NavTarget.Home.route) {
            HomeScreen(
                onAddBudgetClick = {
                    navController.navigate(NavTarget.NewBudget.route)
                },
                onBudgetClick = { budget ->
                    navController.navigate(NavTarget.Details.createRoute(budget.id))
                }
            )
        }

        composable(NavTarget.NewBudget.route) {
            NewBudgetScreen(
                onBackClick = {
                    navController.popBackStack()
                },
                onCalculateClick = {
                    // Por enquanto volta para a Home após "salvar" (simulação)
                    navController.popBackStack()
                }
            )
        }

        composable(
            route = NavTarget.Details.route,
            arguments = listOf(navArgument("budgetId") { type = NavType.StringType })
        ) { backStackEntry ->
            val budgetId = backStackEntry.arguments?.getString("budgetId")
            DetailsScreen(
                budgetId = budgetId,
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }
    }
}

