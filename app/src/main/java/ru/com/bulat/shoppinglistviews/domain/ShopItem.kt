package ru.com.bulat.shoppinglistviews.domain

data class ShopItem(
    val id: Int,
    val name : String,
    val count: Float,
    val enabled: Boolean,
)
