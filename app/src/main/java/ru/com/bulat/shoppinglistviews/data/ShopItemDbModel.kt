package ru.com.bulat.shoppinglistviews.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity (tableName = "shop_items")
data class ShopItemDbModel (
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val name : String,
    val count: Float,
    val enabled: Boolean,

)