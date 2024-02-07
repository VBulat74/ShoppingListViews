package ru.com.bulat.shoppinglistviews.domain

interface ShopListRepository {

    fun addShopItem (shopItem: ShopItem)

    fun deleteShopItemUseCase(shopItem: ShopItem)

    fun editShopItem (shopItem: ShopItem)

    fun getShopItem (shopItemId: Int) : ShopItem

    fun getShopList():List<ShopItem>
}