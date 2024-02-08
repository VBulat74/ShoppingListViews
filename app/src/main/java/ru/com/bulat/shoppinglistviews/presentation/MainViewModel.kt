package ru.com.bulat.shoppinglistviews.presentation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.com.bulat.shoppinglistviews.data.ShopListRepositoryImpl
import ru.com.bulat.shoppinglistviews.domain.DeleteShopItemUseCase
import ru.com.bulat.shoppinglistviews.domain.EditShopItemUseCase
import ru.com.bulat.shoppinglistviews.domain.GetShopListUseCase
import ru.com.bulat.shoppinglistviews.domain.ShopItem

class MainViewModel : ViewModel() {

    private val repository = ShopListRepositoryImpl

    private val getShopListUseCase = GetShopListUseCase(repository)
    private val deleteShopItemUseCase = DeleteShopItemUseCase(repository)
    private val editShopItemUseCase = EditShopItemUseCase(repository)

    val shopList = MutableLiveData<List<ShopItem>>()

    fun getShopList() {
        val list = getShopListUseCase.getShopList()
        shopList.value = list
    }

    fun deleteShopItem (shopItem: ShopItem) {
        deleteShopItemUseCase.deleteShopItemUseCase(shopItem)
        getShopList()
    }

    fun changeEnabledState(shopItem: ShopItem){
        val newItem = shopItem.copy(enabled = !shopItem.enabled)
        editShopItemUseCase.editShopItem(newItem)
        getShopList()
    }
}