// Limite do modulo: acesso persistente aos orcamentos.
package br.edu.ifpe.projeto_final_print_flow.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface OrcamentoDao {
    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun inserir(orcamento: Orcamento): Long

    @Update
    suspend fun atualizar(orcamento: Orcamento)

    @Query("SELECT * FROM orcamentos ORDER BY id DESC")
    fun observarTodos(): Flow<List<Orcamento>>

    @Query("SELECT * FROM orcamentos WHERE id = :id")
    fun observarPorId(id: Long): Flow<Orcamento?>

    @Query("SELECT * FROM orcamentos WHERE LOWER(cliente) LIKE '%' || LOWER(:texto) || '%' OR LOWER(servico) LIKE '%' || LOWER(:texto) || '%' ORDER BY id DESC")
    fun buscarPorClienteOuServico(texto: String): Flow<List<Orcamento>>
}
