// Limite do modulo: acesso persistente aos servicos.
package br.edu.ifpe.projeto_final_print_flow.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface ServicoDao {
    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun inserir(servico: Servico): Long

    @Update
    suspend fun atualizar(servico: Servico)

    @Delete
    suspend fun excluir(servico: Servico)

    @Query("SELECT * FROM servicos ORDER BY nome ASC")
    fun observarTodos(): Flow<List<Servico>>

    @Query("SELECT * FROM servicos WHERE id = :id")
    suspend fun buscarPorId(id: Long): Servico?
}
