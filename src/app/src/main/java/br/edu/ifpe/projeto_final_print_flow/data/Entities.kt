package br.edu.ifpe.projeto_final_print_flow.data

data class Cliente(
    val id: Long = 0,
    val nome: String,
    val telefone: String,
    val email: String
)

data class Servico(
    val id: Long = 0,
    val nome: String,
    val descricao: String,
    val precoUnitario: Double,
    val unidade: String
)

data class Orcamento(
    val id: Long = 0,
    val cliente: String,
    val servico: String,
    val quantidade: Int,
    val caracteristicas: String,
    val valor: Double,
    val status: String
)

data class Pedido(
    val id: Long = 0,
    val orcamentoId: Long,
    val data: String,
    val status: String
)

data class Login(
    val id: Long = 0,
    val usuario: String,
    val email: String,
    val senha: String
)
