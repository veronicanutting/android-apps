package us.harvard.graphmytravels

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.widget.EditText
import androidx.fragment.app.DialogFragment
import kotlinx.android.synthetic.main.chart_dialog.view.*

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

    lateinit var etChartText: EditText

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialogBuilder = AlertDialog.Builder(requireContext())

        dialogBuilder.setTitle("Chart dialog")
        val dialogView = requireActivity().layoutInflater.inflate(
            R.layout.chart_dialog, null
        )

        etChartText = dialogView.etChartText

        dialogBuilder.setView(dialogView)
        dialogBuilder.setPositiveButton("Ok") { dialog, which -> }
        dialogBuilder.setNegativeButton("Cancel") { dialog, which -> }

        return dialogBuilder.create()
    }

    override fun onResume() {
        super.onResume()

        val positiveButton = (dialog as AlertDialog).getButton(Dialog.BUTTON_POSITIVE)
        positiveButton.setOnClickListener {
            if (etChartText.text.isNotEmpty()) {
                chartHandler.chartCreated(etChartText.text.toString())
                dialog.dismiss()

            } else {
                etChartText.error = "This field can not be empty"
            }
        }
    }

}