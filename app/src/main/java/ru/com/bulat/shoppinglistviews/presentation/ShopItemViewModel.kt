package ru.com.bulat.shoppinglistviews.presentation

import androidx.lifecycle.ViewModel
import ru.com.bulat.shoppinglistviews.data.ShopListRepositoryImpl
import ru.com.bulat.shoppinglistviews.domain.AddShopItemUseCase
import ru.com.bulat.shoppinglistviews.domain.EditShopItemUseCase
import ru.com.bulat.shoppinglistviews.domain.GetShopItemUseCase
import ru.com.bulat.shoppinglistviews.domain.ShopItem

class ShopItemViewModel : ViewModel() {

    private val repository = ShopListRepositoryImpl

    private val getShopItemUseCase = GetShopItemUseCase(repository)
    private val addShopItemUseCase = AddShopItemUseCase(repository)
    private val editShopItemUseCase = EditShopItemUseCase(repository)

    fun getSopItem (shopItemId : Int) {
        val shopItem = getShopItemUseCase.getShopItem(shopItemId)
    }

    fun addShopItem (inputName: String?, inputCount: String?) {
        val name = parseName(inputName)
        val count = parseFloat(inputCount)
        val fieldsValid = validateInput(name, count)
        if (fieldsValid){
            val shopItem = ShopItem(name = name, count = count, enabled = true)
            addShopItemUseCase.addShopItem(shopItem)
        }
    }

    fun editShopItem (inputName: String?, inputCount: String?) {
        val name = parseName(inputName)
        val count = parseFloat(inputCount)
        val fieldsValid = validateInput(name, count)
        if (fieldsValid){
            val shopItem = ShopItem(name = name, count = count, enabled = true)
            editShopItemUseCase.editShopItem(shopItem)
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
            // TODO sow error input name
            result = false
        }
        if (count <= 0f) {
            // TODO sow error input count
            result = false
        }
        return result
    }
}