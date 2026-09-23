package br.edu.ifpe.printflow.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Servico(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val nome: String,          // ex.: "Impressão A4 colorida", "Impressora 4"
    val descricao: String,
    val precoUnitario: Double, // preço por unidade (folha, cópia, etc.)
    val unidade: String = "un" // ex.: "folha", "cópia", "m²"
)
