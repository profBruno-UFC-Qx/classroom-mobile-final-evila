package com.example.homemates.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.homemates.model.Imovel
import kotlinx.coroutines.flow.Flow

@Dao
interface ImovelDao {

    // O suspend avisa que essa função roda em segundo plano (não trava a tela)
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun inserirImovel(imovel: Imovel)

    @Delete
    suspend fun deletarImovel(imovel: Imovel)

    // O Flow cria um "tubo" de dados. Se um imóvel novo for salvo,
    // a tela atualiza automaticamente sem precisar recarregar!
    @Query("SELECT * FROM tabela_imoveis ORDER BY id DESC")
    fun listarTodosOsImoveis(): Flow<List<Imovel>>

    // (Opcional) Busca apenas os imóveis do usuário logado
    @Query("SELECT * FROM tabela_imoveis WHERE usuarioUid = :uid ORDER BY id DESC")
    fun listarImoveisPorUsuario(uid: String): Flow<List<Imovel>>
}