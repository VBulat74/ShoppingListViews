package ru.com.bulat.shoppinglistviews.presentation

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import ru.com.bulat.shoppinglistviews.databinding.FragmentShopItemBinding
import ru.com.bulat.shoppinglistviews.domain.ShopItem
import javax.inject.Inject

class ShopItemFragment() : Fragment() {

    private lateinit var viewModel : ShopItemViewModel
    private lateinit var onEditingFinishListener : OnEditingFinishListener

    private var _binding : FragmentShopItemBinding? = null
    private val binding: FragmentShopItemBinding
        get() = _binding ?: throw RuntimeException("FragmentShopItemBinding == null")

    private var screenMode : String = MODE_UNKNOWN
    private var shopItemId : Int = ShopItem.UNDEFINED_ID

    @Inject
    lateinit var viewModelFactory : ViewModelFactory

    private val component by lazy {
        (requireActivity().application as ShopApp).component
    }

    override fun onAttach(context : Context) {

        component.inject(this)

        super.onAttach(context)
        if (context is OnEditingFinishListener) {
            onEditingFinishListener = context
        } else {
            throw RuntimeException("Activity must implement listener")
        }
    }

    override fun onCreate(savedInstanceState : Bundle?) {
        super.onCreate(savedInstanceState)
        parseParams()
    }

    override fun onCreateView(
        inflater : LayoutInflater,
        container : ViewGroup?,
        savedInstanceState : Bundle?
    ) : View {
        _binding = FragmentShopItemBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view : View, savedInstanceState : Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this, viewModelFactory)[ShopItemViewModel::class.java]
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        addChangeTextListeners()
        launchRightMode()
        observeViewModel()
    }

    private fun observeViewModel() {

        viewModel.shouldCloseScreen.observe(viewLifecycleOwner) {
            onEditingFinishListener.onEditingFinish()
        }
    }

    private fun launchRightMode() {
        when (screenMode) {
            MODE_EDIT -> {
                launchEditMode()
            }

            MODE_ADD -> {
                launchAddMode()
            }
        }
    }

    private fun addChangeTextListeners() {
        binding.etName.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0 : CharSequence?, p1 : Int, p2 : Int, p3 : Int) {}

            override fun onTextChanged(p0 : CharSequence?, p1 : Int, p2 : Int, p3 : Int) {
                viewModel.resetErrorInputName()
            }

            override fun afterTextChanged(p0 : Editable?) {}
        })
        binding.etCount.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0 : CharSequence?, p1 : Int, p2 : Int, p3 : Int) {}

            override fun onTextChanged(p0 : CharSequence?, p1 : Int, p2 : Int, p3 : Int) {
                viewModel.resetErrorInputCount()
            }

            override fun afterTextChanged(p0 : Editable?) {}
        })
    }

    private fun launchEditMode() {
        viewModel.getSopItem(shopItemId)

        binding.saveButton.setOnClickListener {
            viewModel.editShopItem(
                binding.etName.text?.toString(),
                binding.etCount.text?.toString()
            )
        }
    }

    private fun launchAddMode() {
        binding.saveButton.setOnClickListener {
            viewModel.addShopItem(
                binding.etName.text?.toString(),
                binding.etCount.text?.toString()
            )
        }
    }

    private fun parseParams() {

        val args = requireArguments()

        if (! args.containsKey(SCREEN_MODE)) {
            throw RuntimeException("Param screen mode is absent!")
        }

        val mode = args.getString(SCREEN_MODE)

        if (mode != MODE_EDIT && mode != MODE_ADD) {
            throw RuntimeException("Unknown screen mode $mode")
        }

        screenMode = mode

        if (mode == MODE_EDIT) {
            if (! args.containsKey(SHOP_ITEM_ID)) {
                throw RuntimeException("Param ShopItemId is absent!")
            }
            shopItemId = args.getInt(SHOP_ITEM_ID, ShopItem.UNDEFINED_ID)
        }
    }

    interface OnEditingFinishListener {

        fun onEditingFinish()
    }

    companion object {

        private const val SCREEN_MODE = "extra_mode"
        private const val MODE_EDIT = "mode_edit"
        private const val MODE_ADD = "mode_add"
        private const val MODE_UNKNOWN = ""

        private const val SHOP_ITEM_ID = "extra_shop_item_id"

        fun newInstanceAddItem() : ShopItemFragment {
            return ShopItemFragment().apply {
                arguments = Bundle().apply {
                    putString(SCREEN_MODE, MODE_ADD)
                }
            }
        }

        fun newInstanceEditItem(shopItemId : Int) : ShopItemFragment {
            return ShopItemFragment().apply {
                arguments = Bundle().apply {
                    putString(SCREEN_MODE, MODE_EDIT)
                    putInt(SHOP_ITEM_ID, shopItemId)
                }
            }
        }
    }
}