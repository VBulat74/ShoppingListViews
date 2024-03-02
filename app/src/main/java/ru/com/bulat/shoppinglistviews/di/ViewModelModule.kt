package ru.com.bulat.shoppinglistviews.di

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import ru.com.bulat.shoppinglistviews.presentation.MainViewModel
import ru.com.bulat.shoppinglistviews.presentation.ShopItemViewModel

@Module
interface ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel::class)
    fun bindMainViewModel (viewModel : MainViewModel) : ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ShopItemViewModel::class)
    fun bindShopItemViewModel (viewModel : ShopItemViewModel) : ViewModel
}