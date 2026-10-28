// Limite do modulo: acesso persistente aos clientes.
package br.edu.ifpe.projeto_final_print_flow.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface ClienteDao {
    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun inserir(cliente: Cliente): Long

    @Update
    suspend fun atualizar(cliente: Cliente)

    @Delete
    suspend fun excluir(cliente: Cliente)

    @Query("SELECT * FROM clientes ORDER BY nome ASC")
    fun observarTodos(): Flow<List<Cliente>>

    @Query("SELECT * FROM clientes WHERE id = :id")
    suspend fun buscarPorId(id: Long): Cliente?

    @Query("SELECT * FROM clientes WHERE nome = :nome LIMIT 1")
    suspend fun buscarPorNome(nome: String): Cliente?
}
