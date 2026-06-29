package com.example.homemates.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.homemates.ui.screens.LoginScreen
import com.example.homemates.ui.screens.CadastroUsuarioScreen
import com.example.homemates.ui.screens.CadastroScreen
import com.example.homemates.ui.screens.DetalhesScreen
import com.example.homemates.ui.screens.FavoritosScreen
import com.example.homemates.ui.screens.FeedScreen
import com.example.homemates.ui.screens.MeusAnunciosScreen
import com.example.homemates.viewmodel.ImovelViewModel
import com.google.firebase.auth.FirebaseAuth

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    val auth = FirebaseAuth.getInstance()
    val usuarioAtual = remember { auth.currentUser }
    val rotaInicial = remember { if (usuarioAtual != null) "feed" else "login" }


    val imovelViewModel: ImovelViewModel = viewModel(factory = ImovelViewModel.Factory)

    NavHost(navController = navController, startDestination = rotaInicial) {

        composable("login") { LoginScreen(navController = navController) }

        composable("cadastro_usuario") { CadastroUsuarioScreen(navController = navController) }

        composable("feed") { FeedScreen(navController, imovelViewModel) }

        composable("detalhes") { DetalhesScreen(navController, imovelViewModel) }

        composable("cadastro") { CadastroScreen(navController, imovelViewModel) }

        composable("meus_anuncios") { MeusAnunciosScreen(navController, imovelViewModel) }

        composable("favoritos") { FavoritosScreen(navController, imovelViewModel) }
    }
}