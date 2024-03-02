package ru.com.bulat.shoppinglistviews.di

import android.app.Application
import dagger.Binds
import dagger.Module
import dagger.Provides
import ru.com.bulat.shoppinglistviews.data.AppDataBase
import ru.com.bulat.shoppinglistviews.data.ShopListDao
import ru.com.bulat.shoppinglistviews.data.ShopListRepositoryImpl
import ru.com.bulat.shoppinglistviews.domain.ShopListRepository

@Module
interface DataModule {

    @AppScope
    @Binds
    fun bindShopListRepository(impl: ShopListRepositoryImpl) : ShopListRepository

    companion object{

        @AppScope
        @Provides
        fun provideShopListDao(
            application : Application
        ) : ShopListDao {
            return AppDataBase.getInstance(application).shopListDao()
        }
    }
}