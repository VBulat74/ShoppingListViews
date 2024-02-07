package ru.com.bulat.shoppinglistviews.domain

class GetShopListUseCase (
    private val shopListRepository: ShopListRepository
) {

    fun getShopList():List<ShopItem>{
        return shopListRepository.getShopList()
    }
}