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
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.homemates.model.Imovel
import com.example.homemates.viewmodel.ImovelViewModel
import com.google.firebase.auth.FirebaseAuth

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CadastroScreen(
    navController: NavController,
    imovelViewModel: ImovelViewModel = viewModel(factory = ImovelViewModel.Factory)
) {
    var titulo by remember { mutableStateOf("") }
    var preco by remember { mutableStateOf("") }
    var rua by remember { mutableStateOf("") }
    var quartos by remember { mutableStateOf("") }
    var contato by remember { mutableStateOf("") } // Novo campo para o WhatsApp/Telefone

    var incluiAgua by remember { mutableStateOf(false) }
    var incluiEnergia by remember { mutableStateOf(false) }
    var incluiInternet by remember { mutableStateOf(false) }

    // Novas flags do banco de dados
    var ehArejado by remember { mutableStateOf(false) }
    var proximoTransporte by remember { mutableStateOf(false) }

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
                .verticalScroll(rememberScrollState())
                .padding(16.dp)
        ) {

            Text("Fotos do Imóvel", fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(8.dp))
            LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
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
            OutlinedTextField(
                value = contato, onValueChange = { contato = it },
                label = { Text("Contato (WhatsApp/Email)") },
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

            Text("Características e Contas", fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(8.dp))

            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                Text("Água Inclusa")
                Switch(checked = incluiAgua, onCheckedChange = { incluiAgua = it })
            }
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                Text("Energia Inclusa")
                Switch(checked = incluiEnergia, onCheckedChange = { incluiEnergia = it })
            }
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                Text("Internet Inclusa")
                Switch(checked = incluiInternet, onCheckedChange = { incluiInternet = it })
            }
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                Text("Lugar Arejado")
                Switch(checked = ehArejado, onCheckedChange = { ehArejado = it })
            }
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                Text("Próximo a ônibus/rodoviária")
                Switch(checked = proximoTransporte, onCheckedChange = { proximoTransporte = it })
            }

            Spacer(modifier = Modifier.height(32.dp))

            Button(
                onClick = {
                    val usuarioId = FirebaseAuth.getInstance().currentUser?.uid ?: "usuario_desconhecido"

                    val temContasInclusas = incluiAgua || incluiEnergia || incluiInternet

                    val novoImovel = Imovel(
                        usuarioUid = usuarioId,
                        titulo = titulo,
                        preco = preco.toDoubleOrNull() ?: 0.0, // Tratamento seguro caso digitem texto no lugar de número
                        endereco = rua,
                        quantidadeQuartos = quartos.toIntOrNull() ?: 0,
                        contato = contato,
                        contasInclusas = temContasInclusas,
                        ehArejado = ehArejado,
                        proximoAoTransporte = proximoTransporte
                    )

                    imovelViewModel.salvarImovel(novoImovel)
                    navController.popBackStack()
                },
                modifier = Modifier.fillMaxWidth().height(50.dp)
            ) {
                Text("Publicar Anúncio")
            }
        }
    }
}