// Limite do modulo: configuracao unica do banco Room.
package br.edu.ifpe.projeto_final_print_flow.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [Cliente::class, Servico::class, Orcamento::class, Pedido::class, Login::class],
    version = 1,
    exportSchema = false
)
abstract class PrintFlowDatabase : RoomDatabase() {
    abstract fun clienteDao(): ClienteDao
    abstract fun servicoDao(): ServicoDao
    abstract fun orcamentoDao(): OrcamentoDao
    abstract fun pedidoDao(): PedidoDao
    abstract fun loginDao(): LoginDao

    companion object {
        @Volatile
        private var instance: PrintFlowDatabase? = null

        fun getInstance(context: Context): PrintFlowDatabase {
            return instance ?: synchronized(this) {
                instance ?: Room.databaseBuilder(
                    context.applicationContext,
                    PrintFlowDatabase::class.java,
                    "print_flow_database"
                ).build().also { instance = it }
            }
        }
    }
}
