package ru.com.bulat.shoppinglistviews.presentation

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
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

    private val scope = CoroutineScope(Dispatchers.IO)

    val shopList = getShopListUseCase.getShopList()

    fun deleteShopItem (shopItem: ShopItem) {
        scope.launch {
            deleteShopItemUseCase.deleteShopItemUseCase(shopItem)
        }
    }

    fun changeEnabledState(shopItem: ShopItem){
        val newItem = shopItem.copy(enabled = !shopItem.enabled)
        scope.launch {
            editShopItemUseCase.editShopItem(newItem)
        }
    }

    override fun onCleared() {
        super.onCleared()
        scope.cancel()
    }
}