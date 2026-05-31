package com.example.homemates.model

data class Imovel(
    val id: Int,
    val titulo: String,
    val preco: String,
    val detalhes: String,
    val imagens: List<String>, // Agora é uma lista!
    val tempoOnibus: String,
    val ventilado: Boolean,
    val rua: String,
    val numQuartos: Int,
    val incluiAgua: Boolean,
    val incluiEnergia: Boolean,
    val incluiInternet: Boolean
)
//exemplos
val imoveisMock = listOf(
    Imovel(
        id = 1,
        titulo = "Quarto individual - Centro",
        preco = "R$ 400",
        detalhes = "Muito ventilado. A 10 min da parada do intercampi.",
        imagens = listOf(
            "https://images.unsplash.com/photo-1522708323590-d24dbb6b0267?w=500&q=80", // Quarto
            "https://images.unsplash.com/photo-1584622650111-993a426fbf0a?w=500&q=80", // Banheiro
            "https://images.unsplash.com/photo-1556912172-45b7abe8b7e1?w=500&q=80"  // Cozinha/Área
        ),
        tempoOnibus = "5 min",
        ventilado = true,
        rua = "Rua Juscelino Kubitschek",
        numQuartos = 1,
        incluiAgua = true,
        incluiEnergia = false,
        incluiInternet = true
    ),
    Imovel(
        id = 2,
        titulo = "Vaga em República",
        preco = "R$ 300",
        detalhes = "Casa grande. Ônibus passa na esquina.",
        imagens = listOf(
            "https://images.unsplash.com/photo-1505691938895-1758d7feb511?w=500&q=80",
            "https://images.unsplash.com/photo-1600566753190-17f0baa2a6c3?w=500&q=80"
        ),
        tempoOnibus = "1 min",
        ventilado = false,
        rua = "Rua Presidente Vargas",
        numQuartos = 3,
        incluiAgua = true,
        incluiEnergia = true,
        incluiInternet = true
    ),
    Imovel(
        id = 3,
        titulo = "Kitnet Planalto",
        preco = "R$ 500",
        detalhes = "Janela voltada para o nascente. Fica a 15 min a pé da universidade.",
        imagens = listOf(
            "https://images.unsplash.com/photo-1628592102751-ba83b0314276?w=500&q=80",
            "https://images.unsplash.com/photo-1588046135715-c8ccb1e6d421?w=500&q=80"
        ),
        tempoOnibus = "15 min",
        ventilado = true,
        rua = "Av. Plácido Castelo",
        numQuartos = 1,
        incluiAgua = false,
        incluiEnergia = false,
        incluiInternet = false
    )
)