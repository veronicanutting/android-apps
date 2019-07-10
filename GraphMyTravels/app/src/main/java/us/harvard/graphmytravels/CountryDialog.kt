package us.harvard.graphmytravels

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Spinner
import androidx.fragment.app.DialogFragment
import kotlinx.android.synthetic.main.country_dialog.view.*


class CountryDialog : DialogFragment() {

    interface CountryHandler {
        fun countryCreated(country: String)
    }

    lateinit var countryHandler: CountryHandler

    override fun onAttach(context: Context?) {
        super.onAttach(context)

        if (context is CountryHandler) {
            countryHandler = context
        } else {
            throw RuntimeException(
                "The Activity is not implementing the CountryHandler interface."
            )
        }
    }

    lateinit var spinnerCountries: Spinner

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialogBuilder = AlertDialog.Builder(requireContext())

        dialogBuilder.setTitle("Country dialog")
        val dialogView = requireActivity().layoutInflater.inflate(
            R.layout.country_dialog, null
        )

        spinnerCountries = dialogView.spinnerCountries

        val countriesAdapter = ArrayAdapter.createFromResource(
            activity as Context,
            R.array.all_countries,
            android.R.layout.simple_spinner_item)
        spinnerCountries.adapter = countriesAdapter

        dialogBuilder.setView(dialogView)
        dialogBuilder.setPositiveButton("Ok") { dialog, which -> }
        dialogBuilder.setNegativeButton("Cancel") { dialog, which -> }

        return dialogBuilder.create()
    }

    override fun onResume() {
        super.onResume()

        val positiveButton = (dialog as AlertDialog).getButton(Dialog.BUTTON_POSITIVE)
        positiveButton.setOnClickListener {
            countryHandler.countryCreated(spinnerCountries.selectedItem.toString())
            dialog.dismiss()
        }
    }


}