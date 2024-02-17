package ru.com.bulat.shoppinglistviews.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.ListAdapter
import ru.com.bulat.shoppinglistviews.R
import ru.com.bulat.shoppinglistviews.databinding.ItemShopDisabledBinding
import ru.com.bulat.shoppinglistviews.databinding.ItemShopEnabledBinding
import ru.com.bulat.shoppinglistviews.domain.ShopItem

class ShopListAdapter : ListAdapter<ShopItem, ShopItemViewHolder> (ShopItemDiffCallback()) {

    var onShopItemLongClickListener: ((ShopItem) -> Unit)? = null
    var onShopItemClickListener: ((ShopItem) -> Unit)? = null

    override

    fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShopItemViewHolder {
        val layout = when (viewType) {
            VIEW_TYPE_ENABLED -> R.layout.item_shop_enabled
            VIEW_TYPE_DISABLED -> R.layout.item_shop_disabled
            else -> {
                throw RuntimeException("Unknown view type $viewType")
            }
        }
        val binding = DataBindingUtil.inflate<ViewDataBinding>(
            LayoutInflater.from(parent.context),
            layout,
            parent,
            false
        )
        return ShopItemViewHolder(binding)
    }

    override fun onBindViewHolder(viewHolder: ShopItemViewHolder, position: Int) {

        val shopItem = getItem(position)

        val binding = viewHolder.binding

        when(binding){
            is ItemShopDisabledBinding -> {
                binding.shopItem = shopItem
            }
            is ItemShopEnabledBinding ->{
                binding.shopItem = shopItem
            }
        }

        binding.root.setOnLongClickListener {
            onShopItemLongClickListener?.invoke(shopItem)
            true
        }

        binding.root.setOnClickListener {
            onShopItemClickListener?.invoke(shopItem)
        }
    }

    override fun getItemViewType(position: Int): Int {
        val item = getItem(position)
        val isEnabled = item.enabled
        return if (isEnabled) VIEW_TYPE_ENABLED else VIEW_TYPE_DISABLED
        //return super.getItemViewType(position)
    }

//    interface OnShopItemLongClickListener {
//
//        fun onShopItemLongClick(shopItem: ShopItem)
//    }

    companion object {
        const val VIEW_TYPE_ENABLED = 101
        const val VIEW_TYPE_DISABLED = 100

        const val MAX_POOL_SIZE = 15
    }
}