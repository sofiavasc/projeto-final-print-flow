// Limite do modulo: acesso persistente aos pedidos.
package br.edu.ifpe.projeto_final_print_flow.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface PedidoDao {
    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun inserir(pedido: Pedido): Long

    @Update
    suspend fun atualizar(pedido: Pedido)

    @Query("SELECT * FROM pedidos ORDER BY id DESC")
    fun observarTodos(): Flow<List<Pedido>>

    @Query("SELECT * FROM pedidos WHERE orcamentoId = :orcamentoId LIMIT 1")
    fun observarPorOrcamento(orcamentoId: Long): Flow<Pedido?>

    @Query("SELECT * FROM pedidos WHERE orcamentoId = :orcamentoId LIMIT 1")
    suspend fun buscarPorOrcamento(orcamentoId: Long): Pedido?
}
