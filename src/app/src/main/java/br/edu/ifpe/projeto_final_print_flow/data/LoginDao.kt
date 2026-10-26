// Limite do modulo: acesso persistente aos dados de login.
package br.edu.ifpe.projeto_final_print_flow.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface LoginDao {
    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun inserir(login: Login): Long

    @Update
    suspend fun atualizar(login: Login)

    @Query("SELECT COUNT(*) FROM logins")
    suspend fun contar(): Int

    @Query("SELECT * FROM logins WHERE usuario = :usuario AND email = :email AND senha = :senha LIMIT 1")
    suspend fun validar(usuario: String, email: String, senha: String): Login?
}
