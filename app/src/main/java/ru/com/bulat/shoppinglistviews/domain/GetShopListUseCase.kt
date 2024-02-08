package ru.com.bulat.shoppinglistviews.domain

import androidx.lifecycle.LiveData

class GetShopListUseCase (
    private val shopListRepository: ShopListRepository
) {

    fun getShopList(): LiveData<List<ShopItem>>{
        return shopListRepository.getShopList()
    }
}