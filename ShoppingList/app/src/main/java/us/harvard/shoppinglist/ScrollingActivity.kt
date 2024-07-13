package us.harvard.shoppinglist

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.ItemTouchHelper
import kotlinx.android.synthetic.main.activity_scrolling.*
import uk.co.samuelwall.materialtaptargetprompt.MaterialTapTargetPrompt
import us.harvard.shoppinglist.adapter.ItemAdapter
import us.harvard.shoppinglist.data.AppDatabase
import us.harvard.shoppinglist.data.Item
import us.harvard.shoppinglist.touch.ItemReyclerTouchCallback

class ScrollingActivity : AppCompatActivity(), ItemDialog.ItemHandler {

    companion object {
        public val KEY_ITEM_EDIT = "KEY_ITEM_EDIT"
        val NAME_PREF = "NAME_PREF"
        val KEY_STARTED = "KEY_STARTED"
    }

    lateinit var itemAdapter: ItemAdapter

    fun saveWasStarted() {
        var sharedPref = getSharedPreferences(
            NAME_PREF, Context.MODE_PRIVATE)
        var editor = sharedPref.edit()
        editor.putBoolean(KEY_STARTED, true)
        editor.apply()
    }

    fun getWasStarted() :Boolean {
        var sharedPref = getSharedPreferences(NAME_PREF,Context.MODE_PRIVATE)
        return sharedPref.getBoolean(KEY_STARTED, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scrolling)

        setSupportActionBar(toolbar)

        fab.setOnClickListener { view ->
            ItemDialog().show(supportFragmentManager, "Dialog")
        }

        fabDeleteAll.setOnClickListener {
            Thread {
                AppDatabase.getInstance(this@ScrollingActivity).itemDao().deleteAll()
                runOnUiThread {
                    itemAdapter.removeAll()
                }
            }.start()
        }

        initRecyclerView()

        if (!getWasStarted()) {
            MaterialTapTargetPrompt.Builder(this)
                .setTarget(R.id.fab)
                .setPrimaryText("Create item")
                .setSecondaryText("Click here to create a new Item object")
                .show()

            saveWasStarted()
        }
    }

    private fun initRecyclerView() {

        Thread {
            var items = AppDatabase.getInstance(this@ScrollingActivity).itemDao().getAllItems()

            runOnUiThread {
                itemAdapter = ItemAdapter(this, items)
                recyclerShop.adapter = itemAdapter

                val touchCallbackList = ItemReyclerTouchCallback(itemAdapter)
                val itemTouchHelper = ItemTouchHelper(touchCallbackList)
                itemTouchHelper.attachToRecyclerView(recyclerShop)
            }
        }.start()
    }

    var editIndex : Int = -1

     fun showEditItemDialog(itemToEdit: Item, itemIndex : Int) {
        editIndex = itemIndex

        val editItemDialog = ItemDialog()

        val bundle = Bundle()
        bundle.putSerializable(KEY_ITEM_EDIT, itemToEdit)
        editItemDialog.arguments = bundle

        editItemDialog.show(supportFragmentManager, "EDITDIALOG")
    }

    override fun itemCreated(item: Item) {
        Thread{

            var newId : Long = AppDatabase.getInstance(this@ScrollingActivity).
                itemDao().insertItem(item)
            item.id = newId

            runOnUiThread {
                itemAdapter.addItem(item)
            }
        }.start()
    }

    override fun itemUpdated(item: Item) {
        Thread {
            AppDatabase.getInstance(this@ScrollingActivity).itemDao().updateItem(item)

            runOnUiThread{
                itemAdapter.updateItem(item, editIndex)
            }
        }.start()
    }

}
