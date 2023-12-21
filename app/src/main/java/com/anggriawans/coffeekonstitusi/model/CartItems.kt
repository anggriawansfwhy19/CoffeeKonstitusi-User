package com.anggriawans.coffeekonstitusi.model

data class CartItems(
    val coffeeName: String ?=null,
    val coffeePrice: String ?=null,
    val coffeeDescription: String ?=null,
    val coffeeImage: String ?=null,
    val coffeeQuantity: Int ?=null,
    var coffeeIngredient: String?=null
)
