package ru.com.bulat.shoppinglistviews.presentation

import android.app.Application
import ru.com.bulat.shoppinglistviews.di.DaggerAppComponent

class ShopApp : Application() {

    val component by lazy {
        DaggerAppComponent.factory().create(this)
    }

}