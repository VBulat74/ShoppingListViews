package ru.com.bulat.shoppinglistviews.domain

class editShopItemUseCase(
    private val shopListRepository: ShopListRepository
) {

    fun editShopItem (shopItem: ShopItem){
        shopListRepository.editShopItem(shopItem)
    }
}