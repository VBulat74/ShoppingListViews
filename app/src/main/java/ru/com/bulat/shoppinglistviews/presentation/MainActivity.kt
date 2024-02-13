package ru.com.bulat.shoppinglistviews.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentContainerView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import ru.com.bulat.shoppinglistviews.R

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel : MainViewModel
    private lateinit var shopListAdapter : ShopListAdapter
    private var shopItemContainer : FragmentContainerView? = null

    override fun onCreate(savedInstanceState : Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        shopItemContainer = findViewById(R.id.shop_item_container)

        setupRecyclerView()
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        viewModel.shopList.observe(this) { shopItems ->
            shopListAdapter.submitList(shopItems)
        }

        val buttonAddItem = findViewById<FloatingActionButton>(R.id.button_add_shop_item)
        buttonAddItem.setOnClickListener {
            if (isOnePaneMode()) {
                val intent = ShopItemActivity.newIntentAddItem(this)
                startActivity(intent)
            } else {
                launchFragment(ShopItemFragment.newInstanceAddItem())
            }

        }
    }

    private fun isOnePaneMode() : Boolean {
        return shopItemContainer == null
    }

    private fun launchFragment(fragment : Fragment) {
        supportFragmentManager.popBackStack()
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.shop_item_container, fragment)
            .addToBackStack(null)
            .commit()
    }

    private fun setupRecyclerView() {
        val rvShopList = findViewById<RecyclerView>(R.id.rv_shop_list)
        with(rvShopList) {
            shopListAdapter = ShopListAdapter()
            adapter = shopListAdapter
            recycledViewPool.setMaxRecycledViews(
                ShopListAdapter.VIEW_TYPE_ENABLED,
                ShopListAdapter.MAX_POOL_SIZE
            )
            recycledViewPool.setMaxRecycledViews(
                ShopListAdapter.VIEW_TYPE_DISABLED,
                ShopListAdapter.MAX_POOL_SIZE
            )
        }
        setupLongClickLitener()
        setupClickListener()
        setupSwipeListener(rvShopList)
    }

    private fun setupSwipeListener(rvShopList : RecyclerView) {
        val callBack = object : ItemTouchHelper.SimpleCallback(
            0,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        ) {
            override fun onMove(
                recyclerView : RecyclerView,
                viewHolder : RecyclerView.ViewHolder,
                target : RecyclerView.ViewHolder
            ) : Boolean {
                return false
            }

            override fun onSwiped(viewHolder : RecyclerView.ViewHolder, direction : Int) {
                val item = shopListAdapter.currentList[viewHolder.adapterPosition]
                viewModel.deleteShopItem(item)
            }
        }
        val itemTouchHelper = ItemTouchHelper(callBack)
        itemTouchHelper.attachToRecyclerView(rvShopList)
    }

    private fun setupClickListener() {
        shopListAdapter.onShopItemClickListener = { shopItem ->
            if (isOnePaneMode()) {
                val intent = ShopItemActivity.newIntentEditItem(this, shopItem.id)
                startActivity(intent)
            } else {
                launchFragment(ShopItemFragment.newInstanceEditItem(shopItem.id))
            }
        }
    }

    private fun setupLongClickLitener() {
        shopListAdapter.onShopItemLongClickListener = { shopItem ->
            viewModel.changeEnabledState(shopItem)
        }
    }
}