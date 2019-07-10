package us.harvard.graphmytravels

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Spinner
import androidx.fragment.app.DialogFragment
import kotlinx.android.synthetic.main.chart_dialog.view.*
import kotlinx.android.synthetic.main.country_dialog.view.*

class ChartDialog : DialogFragment() {

    interface ChartHandler {
        fun chartCreated(chart: String)
    }

    lateinit var chartHandler: ChartHandler

    override fun onAttach(context: Context?) {
        super.onAttach(context)

        if (context is ChartHandler) {
            chartHandler = context
        } else {
            throw RuntimeException(
                "The Activity is not implementing the ChartHandler interface."
            )
        }
    }

    lateinit var spinnerCharts: Spinner

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialogBuilder = AlertDialog.Builder(requireContext())

        dialogBuilder.setTitle("Chart dialog")
        val dialogView = requireActivity().layoutInflater.inflate(
            R.layout.chart_dialog, null
        )

        spinnerCharts = dialogView.spinnerCharts

        val countriesAdapter = ArrayAdapter.createFromResource(
            activity as Context,
            R.array.chart_categories,
            android.R.layout.simple_spinner_item)
        spinnerCharts.adapter = countriesAdapter

        dialogBuilder.setView(dialogView)
        dialogBuilder.setPositiveButton("Ok") { dialog, which -> }
        dialogBuilder.setNegativeButton("Cancel") { dialog, which -> }

        return dialogBuilder.create()
    }

    override fun onResume() {
        super.onResume()

        val positiveButton = (dialog as AlertDialog).getButton(Dialog.BUTTON_POSITIVE)
        positiveButton.setOnClickListener {
            chartHandler.chartCreated(spinnerCharts.selectedItem.toString())
            dialog.dismiss()
        }
    }

}