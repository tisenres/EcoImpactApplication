package com.example.ecoimpactapplication.remote.vo

data class ServerResponse(val item: Item) {
    fun getActivation() = item.virtual35.activation
}

data class Item(
    val virtual35: Virtual
)

data class Virtual(
    val activation: Int
)
