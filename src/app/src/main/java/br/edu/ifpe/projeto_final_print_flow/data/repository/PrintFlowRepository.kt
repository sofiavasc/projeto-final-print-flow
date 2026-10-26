// Limite do modulo: regra de negocio e acesso persistente centralizado.
package br.edu.ifpe.projeto_final_print_flow.data.repository

import android.content.Context
import br.edu.ifpe.projeto_final_print_flow.data.Cliente
import br.edu.ifpe.projeto_final_print_flow.data.Login
import br.edu.ifpe.projeto_final_print_flow.data.Orcamento
import br.edu.ifpe.projeto_final_print_flow.data.Pedido
import br.edu.ifpe.projeto_final_print_flow.data.PrintFlowDatabase
import br.edu.ifpe.projeto_final_print_flow.data.Servico
import kotlinx.coroutines.flow.Flow
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object PrintFlowStatus {
    const val PENDENTE = "Pendente"
    const val APROVADO = "Aprovado"
    const val EM_PRODUCAO = "Em produção"
    const val FINALIZADO = "Finalizado"
}

class PrintFlowRepository(context: Context) {
    private val database = PrintFlowDatabase.getInstance(context.applicationContext)

    fun observarOrcamentos(): Flow<List<Orcamento>> = database.orcamentoDao().observarTodos()
    fun observarOrcamento(id: Long): Flow<Orcamento?> = database.orcamentoDao().observarPorId(id)
    fun observarPedido(orcamentoId: Long): Flow<Pedido?> = database.pedidoDao().observarPorOrcamento(orcamentoId)
    fun buscarOrcamentos(texto: String): Flow<List<Orcamento>> {
        return if (texto.isBlank()) database.orcamentoDao().observarTodos()
        else database.orcamentoDao().buscarPorClienteOuServico(texto)
    }

    suspend fun inicializarLogin(): Result<Unit> {
        return try {
            if (database.loginDao().contar() == 0) {
                database.loginDao().inserir(
                    Login(usuario = "admin", email = "admin@printflow.com", senha = "123456")
                )
            }
            Result.success(Unit)
        } catch (error: Exception) {
            Result.failure(error)
        }
    }

    suspend fun validarLogin(usuario: String, email: String, senha: String): Result<Boolean> {
        return try {
            val login = database.loginDao().validar(usuario, email, senha)
            Result.success(login != null)
        } catch (error: Exception) {
            Result.failure(error)
        }
    }

    suspend fun salvarOrcamento(
        clienteNome: String,
        telefone: String,
        email: String,
        nomeServico: String,
        quantidade: Int,
        caracteristicas: String,
        precoUnitario: Double,
        descricaoServico: String = ""
    ): Result<Long> {
        return try {
            val clienteId = database.clienteDao().inserir(
                Cliente(nome = clienteNome, telefone = telefone, email = email)
            )
            val servicoId = database.servicoDao().inserir(
                Servico(
                    nome = nomeServico,
                    descricao = descricaoServico.ifBlank { caracteristicas },
                    precoUnitario = precoUnitario,
                    unidade = "unidade"
                )
            )
            val valor = precoUnitario * quantidade
            val orcamentoId = database.orcamentoDao().inserir(
                Orcamento(
                    cliente = clienteNome,
                    servico = nomeServico,
                    quantidade = quantidade,
                    caracteristicas = caracteristicas,
                    valor = valor,
                    status = PrintFlowStatus.PENDENTE
                )
            )
            if (clienteId <= 0 || servicoId <= 0 || orcamentoId <= 0) {
                Result.failure(IllegalStateException("Falha ao obter IDs persistidos"))
            } else {
                Result.success(orcamentoId)
            }
        } catch (error: Exception) {
            Result.failure(error)
        }
    }

    suspend fun aprovarOrcamento(orcamento: Orcamento): Result<Unit> {
        return try {
            val pedidoExistente = database.pedidoDao().buscarPorOrcamento(orcamento.id)
            if (pedidoExistente == null) {
                database.orcamentoDao().atualizar(
                    orcamento.copy(status = PrintFlowStatus.APROVADO)
                )
                database.pedidoDao().inserir(
                    Pedido(
                        orcamentoId = orcamento.id,
                        data = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date()),
                        status = PrintFlowStatus.EM_PRODUCAO
                    )
                )
            }
            Result.success(Unit)
        } catch (error: Exception) {
            Result.failure(error)
        }
    }

    suspend fun atualizarStatusPedido(pedido: Pedido, novoStatus: String): Result<Unit> {
        return try {
            database.pedidoDao().atualizar(pedido.copy(status = novoStatus))
            Result.success(Unit)
        } catch (error: Exception) {
            Result.failure(error)
        }
    }

    suspend fun buscarTelefoneDoCliente(nome: String): String? {
        return database.clienteDao().buscarPorNome(nome)?.telefone
    }

    suspend fun finalizarPedido(pedido: Pedido, orcamento: Orcamento): Result<Unit> {
        return try {
            database.pedidoDao().atualizar(pedido.copy(status = PrintFlowStatus.FINALIZADO))
            database.orcamentoDao().atualizar(orcamento.copy(status = PrintFlowStatus.FINALIZADO))
            Result.success(Unit)
        } catch (error: Exception) {
            Result.failure(error)
        }
    }
}
