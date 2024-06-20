package com.dicoding.InsightCart.data.Api.koneksi

data class CheckoutRequest(
    val orderItems: List<OrderItemRequest>
)

data class OrderItemRequest(
    val namaMenu: String,
    val quantity: Int
)
