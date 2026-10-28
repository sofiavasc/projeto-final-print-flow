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

    @Query("SELECT * FROM logins WHERE (LOWER(TRIM(usuario)) = LOWER(TRIM(:usuario)) OR LOWER(TRIM(email)) = LOWER(TRIM(:usuario))) AND LOWER(TRIM(email)) = LOWER(TRIM(:email)) AND senha = :senha LIMIT 1")
    suspend fun validar(usuario: String, email: String, senha: String): Login?

    @Query("SELECT * FROM logins WHERE LOWER(TRIM(usuario)) = LOWER(TRIM(:usuario)) OR LOWER(TRIM(email)) = LOWER(TRIM(:email)) LIMIT 1")
    suspend fun buscarPorUsuarioOuEmail(usuario: String, email: String): Login?

    @Query("SELECT * FROM logins WHERE LOWER(TRIM(usuario)) = LOWER(TRIM(:usuario)) LIMIT 1")
    suspend fun buscarPorUsuario(usuario: String): Login?

    @Query("SELECT * FROM logins")
    suspend fun listarTodos(): List<Login>
}
