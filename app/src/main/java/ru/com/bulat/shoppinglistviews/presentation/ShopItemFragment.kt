package ru.com.bulat.shoppinglistviews.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import ru.com.bulat.shoppinglistviews.R

class ShopItemFragment : Fragment() {
    override fun onCreateView(
        inflater : LayoutInflater,
        container : ViewGroup?,
        savedInstanceState : Bundle?
    ) : View? {
        return layoutInflater.inflate(R.layout.fragment_shop_item, container, false)
    }
}