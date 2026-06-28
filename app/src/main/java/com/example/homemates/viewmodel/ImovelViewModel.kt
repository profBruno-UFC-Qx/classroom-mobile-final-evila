package com.example.homemates.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.example.homemates.database.AppDatabase
import com.example.homemates.database.ImovelDao
import com.example.homemates.model.Imovel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class ImovelViewModel(private val dao: ImovelDao) : ViewModel() {

    val todosOsImoveis: StateFlow<List<Imovel>> = dao.listarTodosOsImoveis()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )


    fun salvarImovel(imovel: Imovel) {
        viewModelScope.launch {
            dao.inserirImovel(imovel)
        }
    }

    fun deletarImovel(imovel: Imovel) {
        viewModelScope.launch {
            dao.deletarImovel(imovel)
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
                // Pega o contexto do aplicativo
                val application = checkNotNull(extras[APPLICATION_KEY])
                // Abre o banco de dados e pega o DAO
                val dao = AppDatabase.getDatabase(application).imovelDao()
                return ImovelViewModel(dao) as T
            }
        }
    }
}