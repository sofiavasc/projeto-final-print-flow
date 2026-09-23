package br.edu.ifpe.printflow.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Cliente(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val nome: String,
    val telefone: String,
    val email: String
)

@Entity
data class Servico(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val nome: String,           
    val descricao: String,
    val precoUnitario: Double,  
    val unidade: String = "un" 
)
