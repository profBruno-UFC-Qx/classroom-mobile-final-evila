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
import com.example.homemates.model.Imovel
import com.example.homemates.viewmodel.ImovelViewModel
import com.google.firebase.auth.FirebaseAuth

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CadastroScreen(
    navController: NavController,
    imovelViewModel: ImovelViewModel
) {
    // Verifica se tem algum imóvel na memória para ser editado
    val imovelEdicao = imovelViewModel.imovelSelecionado
    val ehEdicao = imovelEdicao != null

    // Se for edição, já preenche os campos. Se não, fica vazio.
    var titulo by remember { mutableStateOf(imovelEdicao?.titulo ?: "") }
    var preco by remember { mutableStateOf(if (ehEdicao) imovelEdicao!!.preco.toString() else "") }
    var rua by remember { mutableStateOf(imovelEdicao?.endereco ?: "") }
    var quartos by remember { mutableStateOf(if (ehEdicao) imovelEdicao!!.quantidadeQuartos.toString() else "") }
    var contato by remember { mutableStateOf(imovelEdicao?.contato ?: "") }
    var detalhesOpcionais by remember { mutableStateOf(imovelEdicao?.detalhesOpcionais ?: "") }

    var incluiAgua by remember { mutableStateOf(imovelEdicao?.incluiAgua ?: false) }
    var incluiEnergia by remember { mutableStateOf(imovelEdicao?.incluiEnergia ?: false) }
    var incluiInternet by remember { mutableStateOf(imovelEdicao?.incluiInternet ?: false) }
    var ehArejado by remember { mutableStateOf(imovelEdicao?.ehArejado ?: false) }
    var proximoTransporte by remember { mutableStateOf(imovelEdicao?.proximoAoTransporte ?: false) }

    var quantidadeFotos by remember { mutableStateOf(0) }

    Scaffold(
        topBar = {
            TopAppBar(
                // Muda o título da tela dependendo do modo
                title = { Text(if (ehEdicao) "Editar Anúncio" else "Novo Anúncio") },
                navigationIcon = {
                    IconButton(onClick = {
                        imovelViewModel.limparSelecao() // Limpa se o usuário desistir e voltar
                        navController.popBackStack()
                    }) {
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
                    OutlinedCard(onClick = { quantidadeFotos++ }, modifier = Modifier.size(100.dp)) {
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
                label = { Text("Título do anúncio") },
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
            OutlinedTextField(
                value = detalhesOpcionais, onValueChange = { detalhesOpcionais = it },
                label = { Text("Detalhes Adicionais (Opcional)") },
                modifier = Modifier.fillMaxWidth(),
                minLines = 3
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
                    val novoImovel = Imovel(
                        usuarioUid = usuarioId,
                        titulo = titulo,
                        preco = preco.toDoubleOrNull() ?: 0.0,
                        endereco = rua,
                        quantidadeQuartos = quartos.toIntOrNull() ?: 0,
                        contato = contato,
                        detalhesOpcionais = detalhesOpcionais,
                        incluiAgua = incluiAgua,
                        incluiEnergia = incluiEnergia,
                        incluiInternet = incluiInternet,
                        ehArejado = ehArejado,
                        proximoAoTransporte = proximoTransporte
                    )

                    // LÓGICA DO UPDATE: Se estiver editando, chama atualizar. Se não, salvar.
                    if (ehEdicao && imovelEdicao != null) {
                        imovelViewModel.atualizarImovel(imovelEdicao, novoImovel)
                    } else {
                        imovelViewModel.salvarImovel(novoImovel)
                    }

                    imovelViewModel.limparSelecao() // Limpa a memória
                    navController.navigate("feed") { // Força a volta para o feed para limpar a pilha
                        popUpTo("feed") { inclusive = true }
                    }
                },
                modifier = Modifier.fillMaxWidth().height(50.dp)
            ) {
                Text(if (ehEdicao) "Salvar Alterações" else "Publicar Anúncio")
            }
        }
    }
}