// Limite do modulo: entidades persistentes do Print-Flow.
package br.edu.ifpe.projeto_final_print_flow.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "clientes")
data class Cliente(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val nome: String,
    val telefone: String,
    val email: String
)

@Entity(tableName = "servicos")
data class Servico(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val nome: String,
    val descricao: String,
    val precoUnitario: Double,
    val unidade: String
)

@Entity(tableName = "orcamentos")
data class Orcamento(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val cliente: String,
    val servico: String,
    val quantidade: Int,
    val caracteristicas: String,
    val valor: Double,
    val status: String
)

@Entity(tableName = "pedidos")
data class Pedido(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val orcamentoId: Long,
    val data: String,
    val status: String
)

@Entity(tableName = "logins")
data class Login(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val usuario: String,
    val email: String,
    val senha: String
)
