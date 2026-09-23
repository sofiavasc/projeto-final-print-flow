import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Pedido(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    val orcamentoId: Int,

    val data: String,

    val status: String
)
