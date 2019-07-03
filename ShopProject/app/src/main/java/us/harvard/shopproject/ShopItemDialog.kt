package us.harvard.shopproject

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.Spinner
import androidx.fragment.app.DialogFragment
import kotlinx.android.synthetic.main.shop_item_dialog.view.*
import us.harvard.shopproject.data.ShopItem
import java.lang.RuntimeException

class ShopItemDialog : DialogFragment() {

    interface ShopItemHandler{
        fun shopItemCreated(shopItem: ShopItem)
        fun shopItemUpdated(shopItem: ShopItem)
    }

    lateinit var shopItemHandler: ShopItemHandler

    override fun onAttach(context: Context?) {
        super.onAttach(context)

        if (context is ShopItemHandler){
            shopItemHandler = context
        } else {
            throw RuntimeException(
                "The Activity is not implementing the TodoHandler interface.")
        }
    }

    lateinit var etShopItemName: EditText
    lateinit var cbShopItemPurchased: CheckBox

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialogBuilder = AlertDialog.Builder(requireContext())

        dialogBuilder.setTitle("Shop Item Dialog")
        val dialogView = requireActivity().layoutInflater.inflate(
            R.layout.shop_item_dialog, null
        )

        etShopItemName = dialogView.etShopItemName
        cbShopItemPurchased = dialogView.cbShopItemPurchased

        dialogBuilder.setView(dialogView)

        if (arguments != null) {
            if (arguments!!.containsKey(ScrollingActivity.KEY_SHOP_ITEM_EDIT)) {
                val shopItem = arguments!!.getSerializable(ScrollingActivity.KEY_SHOP_ITEM_EDIT) as ShopItem

                etShopItemName.setText(shopItem.shopItemName)
                cbShopItemPurchased.isChecked = shopItem.shopItemPurchased
                //spinnerCategory.selectedItem =

                dialogBuilder.setTitle("Edit shop item")
            }
        }

        dialogBuilder.setPositiveButton("Add") { dialog, which ->}
        dialogBuilder.setNegativeButton("Cancel") { dialog, which ->}

        return dialogBuilder.create()
    }

    override fun onResume() {
        super.onResume()

        val positiveButton : Button = (dialog as AlertDialog)
            .getButton(Dialog.BUTTON_POSITIVE)
        positiveButton.setOnClickListener{

            if (etShopItemName.text.isNotEmpty()) {

                if (arguments != null && arguments!!.containsKey(ScrollingActivity.KEY_SHOP_ITEM_EDIT)) {

                    val shopItemToEdit : ShopItem = arguments!!.getSerializable(ScrollingActivity.KEY_SHOP_ITEM_EDIT) as ShopItem

                    shopItemToEdit.shopItemPurchased = cbShopItemPurchased.isChecked
                    shopItemToEdit.shopItemName = etShopItemName.text.toString()

                    shopItemHandler.shopItemUpdated(shopItemToEdit)

                } else {
                    shopItemHandler.shopItemCreated(
                        ShopItem(
                            null,
                            cbShopItemPurchased.isChecked,
                            etShopItemName.text.toString())
                    )
                }

                dialog.dismiss()

            } else {
                etShopItemName.error="This field cannot be empty!"
            }
        }
    }

}