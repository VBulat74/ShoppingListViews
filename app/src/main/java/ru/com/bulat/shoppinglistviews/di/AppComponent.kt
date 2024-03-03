package ru.com.bulat.shoppinglistviews.di

import android.app.Application
import dagger.BindsInstance
import dagger.Component
import ru.com.bulat.shoppinglistviews.data.ShopListProvider
import ru.com.bulat.shoppinglistviews.presentation.MainActivity
import ru.com.bulat.shoppinglistviews.presentation.ShopItemFragment

@AppScope
@Component(
    modules = [
        DataModule::class,
        ViewModelModule::class,
    ]
)
interface AppComponent {

    fun inject (activity : MainActivity)

    fun inject (activity: ShopItemFragment)

    fun inject (provider : ShopListProvider)

    @Component.Factory
    interface Factory {

        fun create(
            @BindsInstance application : Application,
        ) : AppComponent
    }
}