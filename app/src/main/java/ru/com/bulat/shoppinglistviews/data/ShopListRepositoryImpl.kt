package ru.com.bulat.shoppinglistviews.data

import ru.com.bulat.shoppinglistviews.domain.ShopItem
import ru.com.bulat.shoppinglistviews.domain.ShopListRepository

object ShopListRepositoryImpl : ShopListRepository {

    private val shopList = mutableListOf<ShopItem>()
    private var autoIncrementID = 0

    init {
        for (i in 0 until 10) {
            val item = ShopItem(
                name = "Name $i",
                count = i.toFloat(),
                enabled = true,
            )
            addShopItem(item)
        }
    }

    override fun addShopItem(shopItem: ShopItem) {
        if (shopItem.id == ShopItem.UNDEFINED_ID) {
            shopItem.id = autoIncrementID++
        }

        shopList.add(shopItem)
    }

    override fun deleteShopItemUseCase(shopItem: ShopItem) {
        shopList.remove(shopItem)
    }

    override fun editShopItem(shopItem: ShopItem) {
        val oldItem = getShopItem(shopItem.id)
        shopList.remove(oldItem)
        addShopItem(shopItem)
    }

    override fun getShopItem(shopItemId: Int): ShopItem {
        return shopList.find {
            it.id == shopItemId
        } ?: throw RuntimeException("Element with $shopItemId not found")
    }

    override fun getShopList(): List<ShopItem> {
        return shopList.toList()
    }
}