package com.example.homemates.viewmodel

import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth

class AuthViewModel : ViewModel() {
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()


    fun cadastrarUsuario(email: String, senha: String, onSucesso: () -> Unit, onErro: (String) -> Unit) {
        if (email.isBlank() || senha.isBlank()) {
            onErro("Preencha todos os campos")
            return
        }
        auth.createUserWithEmailAndPassword(email, senha)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    onSucesso()
                } else {
                    onErro(task.exception?.message ?: "Erro ao cadastrar")
                }
            }
    }


    fun fazerLogin(email: String, senha: String, onSucesso: () -> Unit, onErro: (String) -> Unit) {
        if (email.isBlank() || senha.isBlank()) {
            onErro("Preencha todos os campos")
            return
        }
        auth.signInWithEmailAndPassword(email, senha)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    onSucesso()
                } else {
                    onErro(task.exception?.message ?: "Erro ao fazer login")
                }
            }
    }
}