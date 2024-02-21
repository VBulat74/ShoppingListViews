package ru.com.bulat.shoppinglistviews.presentation

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.com.bulat.shoppinglistviews.data.ShopListRepositoryImpl
import ru.com.bulat.shoppinglistviews.domain.DeleteShopItemUseCase
import ru.com.bulat.shoppinglistviews.domain.EditShopItemUseCase
import ru.com.bulat.shoppinglistviews.domain.GetShopListUseCase
import ru.com.bulat.shoppinglistviews.domain.ShopItem

class MainViewModel (application : Application) : AndroidViewModel(application) {

    private val repository = ShopListRepositoryImpl(application)

    private val getShopListUseCase = GetShopListUseCase(repository)
    private val deleteShopItemUseCase = DeleteShopItemUseCase(repository)
    private val editShopItemUseCase = EditShopItemUseCase(repository)

    val shopList = getShopListUseCase.getShopList()

    fun deleteShopItem (shopItem: ShopItem) {
        viewModelScope.launch {
            deleteShopItemUseCase.deleteShopItemUseCase(shopItem)
        }
    }

    fun changeEnabledState(shopItem: ShopItem){
        val newItem = shopItem.copy(enabled = !shopItem.enabled)
        viewModelScope.launch {
            editShopItemUseCase.editShopItem(newItem)
        }
    }
}