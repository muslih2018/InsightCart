package com.dicoding.InsightCart.data.Api.Response

import com.google.gson.annotations.SerializedName

data class CheckoutResponse(
    @field:SerializedName("grandTotal")
    val grandTotal: Double? = null,

    @field:SerializedName("idTransaksi")
    val idTransaksi: String? = null,

    @field:SerializedName("waktu")
    val waktu: String? = null,

    @field:SerializedName("tanggal")
    val tanggal: String? = null,

    @field:SerializedName("items")
    val items: List<ItemsItem?>? = null
)

data class ItemsItem(

    @field:SerializedName("quantity")
    val quantity: Int? = null,

    @field:SerializedName("harga")
    val harga: Double? = null,

    @field:SerializedName("namaMenu")
    val namaMenu: String? = null
)
