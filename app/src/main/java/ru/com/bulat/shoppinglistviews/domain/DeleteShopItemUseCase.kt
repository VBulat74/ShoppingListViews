package ru.com.bulat.shoppinglistviews.domain

class DeleteShopItemUseCase(
    private val shopListRepository: ShopListRepository
) {

    fun deleteShopItemUseCase(shopItem: ShopItem){
        shopListRepository.deleteShopItemUseCase(shopItem)
    }
}