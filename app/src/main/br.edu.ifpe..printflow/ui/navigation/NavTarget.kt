package br.edu.ifpe.printflow.ui.navigation

sealed class NavTarget(val route: String) {
    object Home : NavTarget("home")
}
