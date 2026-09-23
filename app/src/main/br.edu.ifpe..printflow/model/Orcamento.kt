package br.edu.ifpe.printflow.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "orcamentos")
data class Orcamento(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    val cliente: String,
    val servico: String,
    val quantidade: Int,
    val caracteristicas: String,
    val valor: Double,
    val status: String
)
