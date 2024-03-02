package ru.com.bulat.shoppinglistviews.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import ru.com.bulat.shoppinglistviews.domain.ShopItem
import ru.com.bulat.shoppinglistviews.domain.ShopListRepository
import javax.inject.Inject

class ShopListRepositoryImpl @Inject constructor(
    private val shopListDao : ShopListDao,
    private val mapper : ShopListMapper,
) : ShopListRepository {

    override suspend fun addShopItem(shopItem : ShopItem) {
        shopListDao.addShopItem(mapper.mapEntityToDbModel(shopItem))
    }

    override suspend fun deleteShopItemUseCase(shopItem : ShopItem) {
        shopListDao.deleteShopItem(shopItem.id)
    }

    override suspend fun editShopItem(shopItem : ShopItem) {
        shopListDao.addShopItem(mapper.mapEntityToDbModel(shopItem))
    }

    override suspend fun getShopItem(shopItemId : Int) : ShopItem {
        val dbModel = shopListDao.getShopItem(shopItemId)
        return mapper.mapDbModelToEntity(dbModel)
    }

//    override fun getShopList(): LiveData<List<ShopItem>> = MediatorLiveData<List<ShopItem>>().apply {
//        addSource(shopListDao.getShopList()){
//            value = mapper.mapListDbModelToListEntity(it)
//        }
//    }

    override fun getShopList() : LiveData<List<ShopItem>> = shopListDao.getShopList().map {
        mapper.mapListDbModelToListEntity(it)
    }
}