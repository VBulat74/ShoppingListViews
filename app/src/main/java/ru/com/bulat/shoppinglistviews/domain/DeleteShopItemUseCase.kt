package ru.com.bulat.shoppinglistviews.domain

class DeleteShopItemUseCase(
    private val shopListRepository: ShopListRepository
) {

    suspend fun deleteShopItemUseCase(shopItem: ShopItem){
        shopListRepository.deleteShopItemUseCase(shopItem)
    }
}