package ru.com.bulat.shoppinglistviews.domain

class AddShopItemUseCase(
    private val shopListRepository: ShopListRepository
) {

    fun addShopItem (shopItem: ShopItem) {
        shopListRepository.addShopItem(shopItem)
    }
}