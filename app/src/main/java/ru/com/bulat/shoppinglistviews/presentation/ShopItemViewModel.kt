package ru.com.bulat.shoppinglistviews.presentation

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import ru.com.bulat.shoppinglistviews.data.ShopListRepositoryImpl
import ru.com.bulat.shoppinglistviews.domain.AddShopItemUseCase
import ru.com.bulat.shoppinglistviews.domain.EditShopItemUseCase
import ru.com.bulat.shoppinglistviews.domain.GetShopItemUseCase
import ru.com.bulat.shoppinglistviews.domain.ShopItem

class ShopItemViewModel(application : Application) : AndroidViewModel(application) {

    private val repository = ShopListRepositoryImpl(application)

    private val getShopItemUseCase = GetShopItemUseCase(repository)
    private val addShopItemUseCase = AddShopItemUseCase(repository)
    private val editShopItemUseCase = EditShopItemUseCase(repository)

    val scope = CoroutineScope(Dispatchers.IO)

    private val _errorInputName = MutableLiveData<Boolean>()
    val errorInputName : LiveData<Boolean>
        get() =  _errorInputName

    private val _errorInputCount = MutableLiveData<Boolean>()
    val errorInputCount : LiveData<Boolean>
        get() =  _errorInputCount

    private val _shopItem = MutableLiveData<ShopItem>()
    val shopItem : LiveData<ShopItem>
        get() = _shopItem

    private val _shouldCloseScreen = MutableLiveData<Unit>()
    val shouldCloseScreen : LiveData<Unit>
        get() = _shouldCloseScreen

    fun getSopItem (shopItemId : Int) {
        scope.launch {
            val item = getShopItemUseCase.getShopItem(shopItemId)
            _shopItem.value = item
        }
    }

    fun addShopItem (inputName: String?, inputCount: String?) {
        val name = parseName(inputName)
        val count = parseFloat(inputCount)
        val fieldsValid = validateInput(name, count)
        if (fieldsValid){
            scope.launch{
                val shopItem = ShopItem(name = name, count = count, enabled = true)
                addShopItemUseCase.addShopItem(shopItem)
                finishWork()
            }
        }
    }

    fun editShopItem (inputName: String?, inputCount: String?) {
        val name = parseName(inputName)
        val count = parseFloat(inputCount)
        val fieldsValid = validateInput(name, count)
        if (fieldsValid){
            _shopItem.value?.let {
                scope.launch {
                    val item = it.copy(name = name, count = count)
                    editShopItemUseCase.editShopItem(item)
                    finishWork()
                }
            }
        }
    }
    private fun parseName (inputName:String?):String{
        return inputName?.trim() ?: ""
    }

    private fun parseFloat (inputCount : String?) : Float {
        return try {
            inputCount?.trim()?.toFloat() ?: 0f
        } catch (e : Exception) {
            0f
        }
    }

    private fun validateInput(name: String, count: Float) : Boolean {
        var result = true
        if (name.isBlank()){
            _errorInputName.value = true
            result = false
        }
        if (count <= 0f) {
            _errorInputCount.value = true
            result = false
        }
        return result
    }

    fun resetErrorInputName () {
        _errorInputName.value = false
    }

    fun resetErrorInputCount () {
        _errorInputCount.value = false
    }

    private fun finishWork(){
        _shouldCloseScreen.value = Unit
    }

    override fun onCleared() {
        super.onCleared()
        scope.cancel()
    }
}