package com.example.homemates.ui.screens

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.homemates.viewmodel.ImovelViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetalhesScreen(
    navController: NavController,
    imovelViewModel: ImovelViewModel // Recebe o ViewModel central
) {
    // 1. Puxa da memória o imóvel que foi clicado no Feed
    val imovel = imovelViewModel.imovelSelecionado

    // Se houver algum bug e chegar nulo, volta para a tela anterior por segurança
    if (imovel == null) {
        LaunchedEffect(Unit) {
            navController.popBackStack()
        }
        return
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Detalhes") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Voltar")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
            )
        }

    ) { padding ->

        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {

            LazyRow(
                contentPadding = PaddingValues(16.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                // Se não tiver foto, mostramos uma genérica via Coil
                val urlImagem = imovel.fotoUri ?: "https://via.placeholder.com/400x200.png?text=Sem+Foto"

                item {
                    AsyncImage(
                        model = urlImagem,
                        contentDescription = "Foto do local",
                        modifier = Modifier
                            .size(width = 280.dp, height = 200.dp)
                            .clip(RoundedCornerShape(12.dp)),
                        contentScale = ContentScale.Crop
                    )
                }
            }

            Column(modifier = Modifier.padding(horizontal = 16.dp)) {
                Text(text = imovel.titulo, style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold)
                Text(text = "R$ ${imovel.preco}", style = MaterialTheme.typography.titleLarge, color = MaterialTheme.colorScheme.primary, fontWeight = FontWeight.Bold)

                Spacer(modifier = Modifier.height(16.dp))

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Outlined.LocationOn, contentDescription = null, tint = MaterialTheme.colorScheme.secondary)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(text = imovel.endereco)
                }
                Spacer(modifier = Modifier.height(8.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Outlined.Bed, contentDescription = null, tint = MaterialTheme.colorScheme.secondary)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(text = "${imovel.quantidadeQuartos} quarto(s)")
                }

                Spacer(modifier = Modifier.height(24.dp))

                Text(text = "Comodidades e Contas:", fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .horizontalScroll(rememberScrollState()), // Permite arrastar os chips para o lado
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    if (imovel.incluiAgua) AssistChip(onClick = {}, label = { Text("Água") }, leadingIcon = { Icon(Icons.Outlined.WaterDrop, null) })
                    if (imovel.incluiEnergia) AssistChip(onClick = {}, label = { Text("Energia") }, leadingIcon = { Icon(Icons.Outlined.Bolt, null) })
                    if (imovel.incluiInternet) AssistChip(onClick = {}, label = { Text("Internet") }, leadingIcon = { Icon(Icons.Outlined.Wifi, null) })
                    if (imovel.ehArejado) AssistChip(onClick = {}, label = { Text("Arejado") }, leadingIcon = { Icon(Icons.Outlined.Air, null) })
                    if (imovel.proximoAoTransporte) AssistChip(onClick = {}, label = { Text("Transporte") }, leadingIcon = { Icon(Icons.Outlined.DirectionsBus, null) })
                }

                // Caso o usuário não tenha marcado nenhuma opção
                if (!imovel.incluiAgua && !imovel.incluiEnergia && !imovel.incluiInternet && !imovel.ehArejado && !imovel.proximoAoTransporte) {
                    Text(text = "Nenhuma comodidade adicional especificada.", style = MaterialTheme.typography.bodyMedium)
                }

                Spacer(modifier = Modifier.height(24.dp))

                if (imovel.detalhesOpcionais.isNotBlank()) {
                    Text(text = "Sobre o local:", fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(text = imovel.detalhesOpcionais, style = MaterialTheme.typography.bodyMedium)
                    Spacer(modifier = Modifier.height(32.dp))
                }

                Button(
                    onClick = {
                        // Futuramente conectaremos isso a uma Intent de WhatsApp usando o imovel.contato
                    },
                    modifier = Modifier.fillMaxWidth().height(50.dp)
                ) {
                    Text("Entrar em contato (${imovel.contato})")
                }
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}