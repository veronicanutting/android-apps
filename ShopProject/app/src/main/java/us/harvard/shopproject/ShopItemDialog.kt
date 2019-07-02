package us.harvard.shopproject

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.widget.Button
import androidx.fragment.app.DialogFragment
import com.google.android.material.snackbar.Snackbar

class ShopItemDialog : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialogBuilder = AlertDialog.Builder(requireContext())

        dialogBuilder.setTitle("Shop Item Dialog")
        val dialogView = requireActivity().layoutInflater.inflate(
            R.layout.shop_item_dialog, null
        )
        dialogBuilder.setView(dialogView)

        dialogBuilder.setPositiveButton("Add") {
                dialog, which ->
        }

        dialogBuilder.setNegativeButton("Cancel") {
                dialog, which ->
        }

        return dialogBuilder.create()
    }

    override fun onResume() {
        super.onResume()

        val positiveButton : Button = (dialog as AlertDialog)
            .getButton(Dialog.BUTTON_POSITIVE)
        positiveButton.setOnClickListener{ view ->
            Snackbar.make(view, "Fake added some item", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }
    }

}