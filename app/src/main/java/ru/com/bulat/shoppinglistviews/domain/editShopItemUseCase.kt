package ru.com.bulat.shoppinglistviews.domain

class EditShopItemUseCase(
    private val shopListRepository: ShopListRepository
) {

    fun editShopItem (shopItem: ShopItem){
        shopListRepository.editShopItem(shopItem)
    }
}