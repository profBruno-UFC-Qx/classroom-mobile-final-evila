package com.example.homemates.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.homemates.viewmodel.ImovelViewModel
import com.google.firebase.auth.FirebaseAuth

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MeusAnunciosScreen(
    navController: NavController,
    imovelViewModel: ImovelViewModel = viewModel(factory = ImovelViewModel.Factory)
) {
    // 1. Coletamos a lista do Room (Persistência Local)
    val todosOsImoveisLocais by imovelViewModel.todosOsImoveis.collectAsState()

    // 2. Filtramos para mostrar apenas os imóveis salvos por quem está logado agora
    val usuarioAtualUid = FirebaseAuth.getInstance().currentUser?.uid
    val meusImoveis = todosOsImoveisLocais.filter { it.usuarioUid == usuarioAtualUid }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Meus Anúncios", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Voltar")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
            )
        }
    ) { padding ->
        if (meusImoveis.isEmpty()) {
            Box(
                modifier = Modifier.fillMaxSize().padding(padding),
                contentAlignment = Alignment.Center
            ) {
                Text("Você ainda não possui anúncios salvos neste aparelho.", color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .padding(padding)
                    .fillMaxSize(),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(meusImoveis) { imovel ->
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                    ) {
                        Column {
                            AsyncImage(
                                model = imovel.fotoUri ?: "https://via.placeholder.com/400x200.png?text=Sem+Foto",
                                contentDescription = "Foto do imóvel",
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(140.dp),
                                contentScale = ContentScale.Crop
                            )
                            Column(modifier = Modifier.padding(16.dp)) {
                                Text(text = imovel.titulo, fontWeight = FontWeight.Bold, style = MaterialTheme.typography.titleMedium)
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(text = "R$ ${imovel.preco}", color = MaterialTheme.colorScheme.primary, fontWeight = FontWeight.Bold)

                                Spacer(modifier = Modifier.height(12.dp))

                                // 3. O Botão de Excluir (Delete Local)
                                OutlinedButton(
                                    onClick = {
                                        imovelViewModel.deletarImovel(imovel)
                                    },
                                    modifier = Modifier.fillMaxWidth(),
                                    colors = ButtonDefaults.outlinedButtonColors(contentColor = MaterialTheme.colorScheme.error)
                                ) {
                                    Icon(Icons.Filled.Delete, contentDescription = "Excluir")
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Text("Excluir Anúncio")
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}