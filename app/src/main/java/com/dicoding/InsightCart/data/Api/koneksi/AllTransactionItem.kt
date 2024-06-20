package com.dicoding.InsightCart.data.Api.koneksi

import com.google.gson.annotations.SerializedName

data class AllTransactionItem(
    @SerializedName("transaction_date") val transactionDate: String,
    @SerializedName("transaction_time") val transactionTime: String,
    @SerializedName("unit_price") val unitPrice: Double,
    @SerializedName("product_id") val productId: String,
    @SerializedName("quantity") val quantity: Int,
    @SerializedName("line_item_amount") val lineItemAmount: Double,
    @SerializedName("transaction_id") val transactionId: String
)
