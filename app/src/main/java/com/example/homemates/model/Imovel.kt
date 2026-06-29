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

    // Contas separadas
    val incluiAgua: Boolean = false,
    val incluiEnergia: Boolean = false,
    val incluiInternet: Boolean = false,

    val ehArejado: Boolean,
    val proximoAoTransporte: Boolean,

    val fotoUri: String? = null,
    val latitude: Double? = null,
    val longitude: Double? = null
)

val imoveisMock = listOf(
    Imovel(
        id = 1,
        usuarioUid = "user123",
        titulo = "Quarto aconchegante no Centro",
        endereco = "Rua Principal, 123",
        contato = "88 99999-9999",
        detalhesOpcionais = "Quarto mobiliado com cama, mesa e guarda-roupa.",
        preco = 650.0,
        quantidadeQuartos = 1,
        incluiAgua = true,
        incluiEnergia = false,
        incluiInternet = true,
        ehArejado = true,
        proximoAoTransporte = true,
        fotoUri = "https://images.unsplash.com/photo-1522771739844-649f6d15c2a5"
    )
)