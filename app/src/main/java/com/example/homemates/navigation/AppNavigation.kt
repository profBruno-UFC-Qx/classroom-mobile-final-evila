package com.example.homemates.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

import com.example.homemates.ui.screens.CadastroScreen
import com.example.homemates.ui.screens.DetalhesScreen
import com.example.homemates.ui.screens.FeedScreen

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "feed") {
        composable("feed") { FeedScreen(navController) }
        composable("detalhes") { DetalhesScreen(navController) }
        composable("cadastro") { CadastroScreen(navController) }
    }
}