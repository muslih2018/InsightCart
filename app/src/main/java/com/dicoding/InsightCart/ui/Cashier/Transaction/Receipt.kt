package com.dicoding.InsightCart.ui.Cashier.Transaction


import java.io.Serializable
data class Receipt(
    var quantity: Int = 1,
    var receiptName: String = ""
) : Serializable

