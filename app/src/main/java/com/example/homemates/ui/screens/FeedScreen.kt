package com.example.homemates.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.outlined.Air
import androidx.compose.material.icons.outlined.DirectionsBus
import androidx.compose.material.icons.outlined.List
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.homemates.viewmodel.ImovelViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FeedScreen(
    navController: NavController,
    imovelViewModel: ImovelViewModel = viewModel(factory = ImovelViewModel.Factory)
) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    val imoveis by imovelViewModel.imoveisDaNuvem.collectAsState()
    val imoveisLocais by imovelViewModel.todosOsImoveis.collectAsState()

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet(modifier = Modifier.width(300.dp)) {
                Spacer(Modifier.height(24.dp))
                Text(
                    "HomeMates",
                    modifier = Modifier.padding(horizontal = 16.dp),
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )
                Spacer(Modifier.height(16.dp))
                HorizontalDivider()
                Spacer(Modifier.height(8.dp))

                NavigationDrawerItem(
                    icon = { Icon(Icons.Outlined.Person, contentDescription = "Perfil") },
                    label = { Text("Meu Perfil") },
                    selected = false,
                    onClick = { scope.launch { drawerState.close() } },
                    modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
                )
                NavigationDrawerItem(
                    icon = { Icon(Icons.Outlined.List, contentDescription = "Anúncios") },
                    label = { Text("Meus Anúncios") },
                    selected = false,
                    onClick = { navController.navigate("meus_anuncios") },
                    modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
                )

                // NOVO: Link para a tela de Favoritos
                NavigationDrawerItem(
                    icon = { Icon(Icons.Outlined.FavoriteBorder, contentDescription = "Favoritos") },
                    label = { Text("Meus Favoritos") },
                    selected = false,
                    onClick = { navController.navigate("favoritos") },
                    modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
                )
            }
        }
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("HomeMates", fontWeight = FontWeight.Bold) },
                    navigationIcon = {
                        IconButton(onClick = { scope.launch { drawerState.open() } }) {
                            Icon(Icons.Filled.Menu, contentDescription = "Abrir Menu")
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
                )
            },
            floatingActionButton = {
                FloatingActionButton(onClick = {
                    imovelViewModel.limparSelecao()
                    navController.navigate("cadastro")
                }) {
                    Icon(Icons.Filled.Add, contentDescription = "Adicionar Anúncio")
                }
            }
        ) { padding ->

            if (imoveis.isEmpty()) {
                Box(
                    modifier = Modifier.fillMaxSize().padding(padding),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Nenhum imóvel cadastrado. Seja o primeiro!",
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            } else {
                LazyColumn(
                    modifier = Modifier.padding(padding).fillMaxSize(),
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    items(imoveis) { imovel ->

                        val imovelSalvoNoRoom = imoveisLocais.find {
                            it.titulo == imovel.titulo && it.usuarioUid == imovel.usuarioUid
                        }
                        val isFavorito = imovelSalvoNoRoom != null

                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    imovelViewModel.selecionarImovel(imovel)
                                    navController.navigate("detalhes")
                                },
                            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                        ) {
                            Column {
                                AsyncImage(
                                    model = imovel.fotoUri ?: "https://via.placeholder.com/400x200.png?text=Sem+Foto",
                                    contentDescription = "Foto do imóvel",
                                    modifier = Modifier.fillMaxWidth().height(180.dp),
                                    contentScale = ContentScale.Crop
                                )

                                Column(modifier = Modifier.padding(16.dp)) {
                                    // NOVO LAYOUT: Preço embaixo do título
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Column(modifier = Modifier.weight(1f)) {
                                            Text(
                                                text = imovel.titulo,
                                                fontWeight = FontWeight.Bold,
                                                style = MaterialTheme.typography.titleMedium
                                            )
                                            Spacer(modifier = Modifier.height(4.dp))
                                            Text(
                                                text = "R$ ${imovel.preco}",
                                                color = MaterialTheme.colorScheme.primary,
                                                fontWeight = FontWeight.Bold
                                            )
                                        }

                                        IconButton(onClick = {
                                            if (isFavorito && imovelSalvoNoRoom != null) {
                                                imovelViewModel.removerFavoritoLocal(imovelSalvoNoRoom)
                                            } else {
                                                imovelViewModel.favoritarImovelLocal(imovel)
                                            }
                                        }) {
                                            Icon(
                                                imageVector = if (isFavorito) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
                                                contentDescription = "Favoritar",
                                                tint = if (isFavorito) Color.Red else Color.Gray
                                            )
                                        }
                                    }

                                    Spacer(modifier = Modifier.height(12.dp))

                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        Icon(Icons.Outlined.DirectionsBus, contentDescription = "Ônibus", modifier = Modifier.size(20.dp), tint = MaterialTheme.colorScheme.secondary)
                                        Spacer(modifier = Modifier.width(4.dp))
                                        Text(text = if (imovel.proximoAoTransporte) "Perto do transporte" else "Longe do transporte", style = MaterialTheme.typography.bodyMedium)

                                        Spacer(modifier = Modifier.width(16.dp))

                                        if (imovel.ehArejado) {
                                            Icon(Icons.Outlined.Air, contentDescription = "Ventilado", modifier = Modifier.size(20.dp), tint = MaterialTheme.colorScheme.secondary)
                                            Spacer(modifier = Modifier.width(4.dp))
                                            Text(text = "Bem ventilado", style = MaterialTheme.typography.bodyMedium)
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}