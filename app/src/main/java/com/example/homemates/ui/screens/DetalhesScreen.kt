package com.example.homemates.ui.screens

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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.homemates.model.imoveisMock

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetalhesScreen(navController: NavController) {
    val imovel = imoveisMock.first()

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
                items(listOfNotNull(imovel.fotoUri)) { urlImagem ->
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

                Text(text = "Incluso no valor:", fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(8.dp))
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    if (imovel.contasInclusas) AssistChip(onClick = {}, label = { Text("Contas Inclusas") }, leadingIcon = { Icon(Icons.Outlined.WaterDrop, null) })
                }
                if (!imovel.contasInclusas) {
                    Text(text = "Nenhuma conta inclusa no aluguel.", style = MaterialTheme.typography.bodyMedium)
                }

                Spacer(modifier = Modifier.height(24.dp))
                Text(text = "Sobre o local:", fontWeight = FontWeight.Bold)
                Text(text = imovel.detalhesOpcionais)

                Spacer(modifier = Modifier.height(32.dp))
                Button(
                    onClick = { },
                    modifier = Modifier.fillMaxWidth().height(50.dp)
                ) {
                    Text("Entrar em contato")
                }
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}