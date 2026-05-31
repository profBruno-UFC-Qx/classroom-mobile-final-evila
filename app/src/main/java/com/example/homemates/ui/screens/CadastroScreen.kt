package com.example.homemates.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddPhotoAlternate
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CadastroScreen(navController: NavController) {
    var titulo by remember { mutableStateOf("") }
    var preco by remember { mutableStateOf("") }
    var rua by remember { mutableStateOf("") }
    var quartos by remember { mutableStateOf("") }

    // Estados dos interruptores (Switches)
    var incluiAgua by remember { mutableStateOf(false) }
    var incluiEnergia by remember { mutableStateOf(false) }
    var incluiInternet by remember { mutableStateOf(false) }

    // Lista falsa para simular fotos anexadas
    var quantidadeFotos by remember { mutableStateOf(0) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Novo Anúncio") },
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
                .verticalScroll(rememberScrollState()) // Permite rolar a tela
                .padding(16.dp)
        ) {

            Text("Fotos do Imóvel", fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(8.dp))
            LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                // caixas simulando as fotos já adicionadas
                items(quantidadeFotos) {
                    Box(
                        modifier = Modifier
                            .size(100.dp)
                            .clip(RoundedCornerShape(8.dp))
                            .background(Color.LightGray),
                        contentAlignment = Alignment.Center
                    ) {
                        Text("Foto ${it + 1}", color = Color.DarkGray)
                    }
                }
                // botão de add foto (falso por enquanto)
                item {
                    OutlinedCard(
                        onClick = { quantidadeFotos++ },
                        modifier = Modifier.size(100.dp)
                    ) {
                        Column(
                            modifier = Modifier.fillMaxSize(),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            Icon(Icons.Filled.AddPhotoAlternate, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
                            Text("Adicionar", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.primary)
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))


            OutlinedTextField(
                value = titulo, onValueChange = { titulo = it },
                label = { Text("Título do anúncio (Ex: Kitnet Centro)") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = preco, onValueChange = { preco = it },
                label = { Text("Valor do aluguel (R$)") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                OutlinedTextField(
                    value = rua, onValueChange = { rua = it },
                    label = { Text("Rua/Bairro") },
                    modifier = Modifier.weight(2f)
                )
                OutlinedTextField(
                    value = quartos, onValueChange = { quartos = it },
                    label = { Text("Quartos") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier.weight(1f)
                )
            }

            Spacer(modifier = Modifier.height(24.dp))


            Text("Contas Inclusas no Valor", fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(8.dp))
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                Text("Água")
                Switch(checked = incluiAgua, onCheckedChange = { incluiAgua = it })
            }
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                Text("Energia")
                Switch(checked = incluiEnergia, onCheckedChange = { incluiEnergia = it })
            }
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                Text("Internet")
                Switch(checked = incluiInternet, onCheckedChange = { incluiInternet = it })
            }

            Spacer(modifier = Modifier.height(32.dp))

            Button(
                onClick = { navController.popBackStack() },
                modifier = Modifier.fillMaxWidth().height(50.dp)
            ) {
                Text("Publicar Anúncio")
            }
        }
    }
}