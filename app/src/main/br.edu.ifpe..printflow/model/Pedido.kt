@Entity
data class Pedido(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    val orcamentoId: Int,

    val data: String,

    val status: String
)
