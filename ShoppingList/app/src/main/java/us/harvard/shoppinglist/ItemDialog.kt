package us.harvard.shoppinglist

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.widget.*
import androidx.fragment.app.DialogFragment
import kotlinx.android.synthetic.main.item_dialog.view.*
import us.harvard.shoppinglist.data.Item
import java.lang.RuntimeException
import java.util.*


class ItemDialog : DialogFragment() {

    interface ItemHandler{
        fun itemCreated(item: Item)
        fun itemUpdated(item: Item)
    }

    lateinit var itemHandler: ItemHandler

    override fun onAttach(context: Context?) {
        super.onAttach(context)

        if (context is ItemHandler){
            itemHandler = context
        } else {
            throw RuntimeException(
                "The Activity is not implementing the ItemHandler interface.")
        }
    }

    lateinit var etItemText: EditText
    lateinit var cbItemDone: CheckBox
    lateinit var spinnerCategory: Spinner

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialogBuilder = AlertDialog.Builder(requireContext())

        dialogBuilder.setTitle("Item dialog")
        val dialogView = requireActivity().layoutInflater.inflate(
            R.layout.item_dialog, null
        )

        etItemText = dialogView.etItemText
        cbItemDone = dialogView.cbItemDone
        spinnerCategory = dialogView.spinnerCategory

        val categoriesAdapter = ArrayAdapter.createFromResource(
            activity as Context,
            R.array.categories_array,
            android.R.layout.simple_spinner_item)
        spinnerCategory.adapter = categoriesAdapter

        dialogBuilder.setView(dialogView)


        // IF WE ARE IN EDIT MODE
        if (arguments != null) {
            if (arguments!!.containsKey(ScrollingActivity.KEY_ITEM_EDIT)) {
                val todoItem = arguments!!.getSerializable(ScrollingActivity.KEY_ITEM_EDIT) as Item

                etItemText.setText(todoItem.itemText)
                cbItemDone.isChecked = todoItem.done
                //spinnerCategory.selectedItem =

                dialogBuilder.setTitle("Edit item")
            }
        }

        dialogBuilder.setPositiveButton("Ok") {
                dialog, which ->

        }
        dialogBuilder.setNegativeButton("Cancel") {
                dialog, which ->
        }


        return dialogBuilder.create()
    }

    override fun onResume() {
        super.onResume()

        val positiveButton : Button = (dialog as AlertDialog).getButton(Dialog.BUTTON_POSITIVE)
        positiveButton.setOnClickListener{

            if (etItemText.text.isNotEmpty()) {

                if (arguments != null && arguments!!.containsKey(ScrollingActivity.KEY_ITEM_EDIT)) {

                    val itemToEdit : Item = arguments!!.getSerializable(ScrollingActivity.KEY_ITEM_EDIT) as Item

                    itemToEdit.itemText = etItemText.text.toString()
                    itemToEdit.done = cbItemDone.isChecked

                    itemHandler.itemUpdated(itemToEdit)

                } else {
                    itemHandler.itemCreated(
                        Item(
                            null,
                            cbItemDone.isChecked,
                            etItemText.text.toString())
                    )
                }

                dialog.dismiss()

            } else {
                etItemText.error="This field cannot be empty!"
            }


        }
    }

}