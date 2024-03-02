package ru.com.bulat.shoppinglistviews.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.com.bulat.shoppinglistviews.data.ShopListRepositoryImpl
import ru.com.bulat.shoppinglistviews.domain.DeleteShopItemUseCase
import ru.com.bulat.shoppinglistviews.domain.EditShopItemUseCase
import ru.com.bulat.shoppinglistviews.domain.GetShopListUseCase
import ru.com.bulat.shoppinglistviews.domain.ShopItem
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val getShopListUseCase : GetShopListUseCase,
    private val deleteShopItemUseCase : DeleteShopItemUseCase,
    private val editShopItemUseCase : EditShopItemUseCase,
) : ViewModel() {

    val shopList = getShopListUseCase.getShopList()

    fun deleteShopItem(shopItem : ShopItem) {
        viewModelScope.launch {
            deleteShopItemUseCase.deleteShopItemUseCase(shopItem)
        }
    }

    fun changeEnabledState(shopItem : ShopItem) {
        val newItem = shopItem.copy(enabled = ! shopItem.enabled)
        viewModelScope.launch {
            editShopItemUseCase.editShopItem(newItem)
        }
    }
}