package com.example.homemates.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tabela_imoveis")
data class Imovel(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val usuarioUid: String,
    val titulo: String,
    val endereco: String,
    val contato: String,
    val detalhesOpcionais: String = "",
    val preco: Double,
    val quantidadeQuartos: Int,


    val incluiAgua: Boolean = false,
    val incluiEnergia: Boolean = false,
    val incluiInternet: Boolean = false,

    val ehArejado: Boolean,
    val proximoAoTransporte: Boolean,

    val fotoUri: String? = null,
    val latitude: Double? = null,
    val longitude: Double? = null
)
