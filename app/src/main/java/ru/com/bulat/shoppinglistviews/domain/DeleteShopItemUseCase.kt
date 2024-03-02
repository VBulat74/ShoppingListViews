package ru.com.bulat.shoppinglistviews.domain

import javax.inject.Inject

class DeleteShopItemUseCase @Inject constructor (
    private val shopListRepository: ShopListRepository
) {

    suspend fun deleteShopItemUseCase(shopItem: ShopItem){
        shopListRepository.deleteShopItemUseCase(shopItem)
    }
}