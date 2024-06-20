package com.dicoding.InsightCart.data.Api.koneksi

import com.dicoding.InsightCart.ui.Inventory.Item.Item

data class ReceiptResponse(
    val tanggal: String,
    val waktu: String,
    val idTransaksi: String,
    val items: List<OrderItem>,
    val grandTotal: Double
)
