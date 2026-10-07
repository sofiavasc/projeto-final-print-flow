package br.edu.ifpe.printflow.ui.navigation

sealed class NavTarget(val route: String) {
    object Login : NavTarget("login")
    object Home : NavTarget("home")
    object NewBudget : NavTarget("new_budget")
    object Details : NavTarget("details/{budgetId}") {
        fun createRoute(budgetId: String) = "details/$budgetId"
    }
}
