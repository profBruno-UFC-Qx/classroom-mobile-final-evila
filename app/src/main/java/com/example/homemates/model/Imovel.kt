package com.example.homemates.model


import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tabela_imoveis")
data class Imovel(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    // O ID do usuário do Firebase que criou este anúncio (Relacionamento)
    val usuarioUid: String,

    // Atributos de Texto
    val titulo: String,
    val endereco: String,
    val contato: String,
    val detalhesOpcionais: String = "",

    val preco: Double,
    val quantidadeQuartos: Int,

    val contasInclusas: Boolean,
    val ehArejado: Boolean,
    val proximoAoTransporte: Boolean, // róximo ao ônibus universitário ou rodoviária

    // Preparação para Galeria e Mapas (Funcionalidades Extras)
    val fotoUri: String? = null,
    val latitude: Double? = null,
    val longitude: Double? = null
)

val imoveisMock = listOf(
    Imovel(
        id = 1,
        usuarioUid = "user123",
        titulo = "Quarto aconchegante perto da UFPA",
        endereco = "Rua dos Universitários, 123",
        contato = "91 99999-9999",
        detalhesOpcionais = "Quarto mobiliado com cama, mesa e guarda-roupa.",
        preco = 650.0,
        quantidadeQuartos = 1,
        contasInclusas = true,
        ehArejado = true,
        proximoAoTransporte = true,
        fotoUri = "https://images.unsplash.com/photo-1522771739844-649f6d15c2a5"
    )
)