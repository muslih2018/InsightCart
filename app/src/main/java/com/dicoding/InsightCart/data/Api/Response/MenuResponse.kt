import com.google.gson.annotations.SerializedName

// Data class untuk item menu
data class MenuResponseItem(
	@SerializedName("id")
	val id: String?,

	@SerializedName("namaMenu")
	val namaMenu: String?,

	@SerializedName("harga")
	val harga: Double?,

	@SerializedName("ingredients")
	val ingredients: List<IngredientsItem>?
)

// Data class untuk bahan menu
data class IngredientsItem(
	@SerializedName("nama")
	val nama: String?,

	@SerializedName("jumlah")
	val jumlah: Double?,

	@SerializedName("satuan")
	val satuan: String?
)

// Data class untuk respons utama menu
data class MenuResponse(
	@SerializedName("menuResponse")
	val menuResponse: List<MenuResponseItem>?
)
