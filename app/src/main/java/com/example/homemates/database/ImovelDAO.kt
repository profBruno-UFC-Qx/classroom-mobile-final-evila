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

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun inserirImovel(imovel: Imovel)

    @Delete
    suspend fun deletarImovel(imovel: Imovel)

    @Query("SELECT * FROM tabela_imoveis ORDER BY id DESC")
    fun listarTodosOsImoveis(): Flow<List<Imovel>>


    @Query("SELECT * FROM tabela_imoveis WHERE usuarioUid = :uid ORDER BY id DESC")
    fun listarImoveisPorUsuario(uid: String): Flow<List<Imovel>>
}