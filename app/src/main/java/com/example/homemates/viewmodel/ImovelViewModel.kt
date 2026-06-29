package com.example.homemates.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.example.homemates.database.AppDatabase
import com.example.homemates.database.ImovelDao
import com.example.homemates.model.Imovel
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class ImovelViewModel(private val dao: ImovelDao) : ViewModel() {

    // Instância do banco de dados em nuvem
    private val firestore = FirebaseFirestore.getInstance()

    // =========================================================================
    // 1. O FLUXO LOCAL (Room) -> Usaremos para a tela "Meus Anúncios"
    // =========================================================================
    val todosOsImoveis: StateFlow<List<Imovel>> = dao.listarTodosOsImoveis()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    // =========================================================================
    // 2. O FLUXO DA NUVEM (Firestore) -> Usaremos para o "Feed" Principal
    // =========================================================================
    private val _imoveisDaNuvem = MutableStateFlow<List<Imovel>>(emptyList())
    val imoveisDaNuvem: StateFlow<List<Imovel>> = _imoveisDaNuvem.asStateFlow()

    init {
        // Assim que o ViewModel nasce, ele começa a escutar a nuvem
        buscarImoveisDaNuvem()
    }
    var imovelSelecionado: Imovel? = null
        private set

    fun selecionarImovel(imovel: Imovel) {
        imovelSelecionado = imovel
    }
    private fun buscarImoveisDaNuvem() {
        // O addSnapshotListener fica "ouvindo" o banco. Se outro usuário
        // postar algo no celular dele, o seu Feed atualiza na mesma hora!
        firestore.collection("imoveis")
            .addSnapshotListener { snapshot, error ->
                if (error != null) {
                    Log.e("Firestore", "Erro ao buscar imóveis da nuvem", error)
                    return@addSnapshotListener
                }

                if (snapshot != null) {
                    // Mapeamento manual do documento NoSQL para a nossa Entidade Kotlin
                    val listaDeImoveis = snapshot.documents.map { doc ->
                        Imovel(
                            id = 0, // Ignoramos o ID do Room para o Feed da nuvem
                            usuarioUid = doc.getString("usuarioUid") ?: "",
                            titulo = doc.getString("titulo") ?: "",
                            endereco = doc.getString("endereco") ?: "",
                            contato = doc.getString("contato") ?: "",
                            detalhesOpcionais = doc.getString("detalhesOpcionais") ?: "",
                            preco = doc.getDouble("preco") ?: 0.0,
                            quantidadeQuartos = doc.getLong("quantidadeQuartos")?.toInt() ?: 0,
                            contasInclusas = doc.getBoolean("contasInclusas") ?: false,
                            ehArejado = doc.getBoolean("ehArejado") ?: false,
                            proximoAoTransporte = doc.getBoolean("proximoAoTransporte") ?: false,
                            fotoUri = doc.getString("fotoUri")
                        )
                    }
                    _imoveisDaNuvem.value = listaDeImoveis
                }
            }
    }

    // =========================================================================
    // 3. O SALVAMENTO ESPELHADO
    // =========================================================================
    fun salvarImovel(imovel: Imovel) {
        viewModelScope.launch {
            // Passo A: Salva fisicamente no celular (Room)
            dao.inserirImovel(imovel)

            // Passo B: Envia uma cópia para a nuvem (Firestore)
            firestore.collection("imoveis")
                .add(imovel)
                .addOnSuccessListener {
                    Log.d("Firestore", "Imóvel salvo na nuvem com sucesso!")
                }
                .addOnFailureListener { e ->
                    Log.e("Firestore", "Erro ao salvar na nuvem", e)
                }
        }
    }

    fun deletarImovel(imovel: Imovel) {
        viewModelScope.launch {
            // 1. Apaga do banco local (Room) do celular
            dao.deletarImovel(imovel)

            // 2. Procura o anúncio exato na nuvem (Firestore) e apaga também
            firestore.collection("imoveis")
                .whereEqualTo("usuarioUid", imovel.usuarioUid)
                .whereEqualTo("titulo", imovel.titulo)
                .whereEqualTo("preco", imovel.preco)
                .get()
                .addOnSuccessListener { querySnapshot ->
                    // O Firestore retorna uma lista com os resultados da busca
                    for (document in querySnapshot) {
                        // Para cada documento encontrado igual ao seu, mandamos deletar
                        document.reference.delete()
                            .addOnSuccessListener {
                                Log.d("Firestore", "Anúncio deletado da nuvem com sucesso!")
                            }
                    }
                }
                .addOnFailureListener { e ->
                    Log.e("Firestore", "Erro ao tentar deletar da nuvem", e)
                }
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
                val application = checkNotNull(extras[APPLICATION_KEY])
                val dao = AppDatabase.getDatabase(application).imovelDao()
                return ImovelViewModel(dao) as T
            }
        }
    }
}