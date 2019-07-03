package us.harvard.shopproject

import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import androidx.recyclerview.widget.ItemTouchHelper
import kotlinx.android.synthetic.main.activity_scrolling.*
import us.harvard.shopproject.adapter.ShopItemAdapter
import us.harvard.shopproject.data.AppDatabase
import us.harvard.shopproject.data.ShopItem

class ScrollingActivity : AppCompatActivity(),
    ShopItemDialog.ShopItemHandler {

    lateinit var shopItemAdapter: ShopItemAdapter
    companion object {
        public val KEY_SHOP_ITEM_EDIT = "KEY_SHOP_ITEM_EDIT"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scrolling)

        setSupportActionBar(toolbar)

        fabAddShopItem.setOnClickListener {
            ShopItemDialog().show(supportFragmentManager, "Dialog")
        }

        fabDeleteAll.setOnClickListener {
            Thread {
                AppDatabase.getInstance(this@ScrollingActivity).shopItemDao()
                    .deleteAllShopItems()
                runOnUiThread {
                    shopItemAdapter.removeAll()
                }
            }.start()
        }

        initRecyclerView()

    }

    private fun initRecyclerView() {
        Thread {
            var shopItems = AppDatabase.getInstance(this@ScrollingActivity).
                shopItemDao().getAllShopItems()

            runOnUiThread {
                shopItemAdapter = ShopItemAdapter(this, shopItems)
                recycler_view.adapter = shopItemAdapter
            }
        }.start()
    }

    var editIndex : Int = -1

    public fun showEditShopItemDialog(shopItemToEdit: ShopItem, shopItemIndex : Int) {
        editIndex = shopItemIndex

        val editShopItemDialog = ShopItemDialog()

        val bundle = Bundle()
        bundle.putSerializable(KEY_SHOP_ITEM_EDIT, shopItemToEdit)
        editShopItemDialog.arguments = bundle

        editShopItemDialog.show(supportFragmentManager, "EDITDIALOG")
    }

    override fun shopItemCreated(shopItem: ShopItem) {
        Thread{

            var newId : Long = AppDatabase.getInstance(this@ScrollingActivity)
                .shopItemDao().insertShopItem(shopItem)
            shopItem.id = newId

            runOnUiThread {
                shopItemAdapter.addShopItem(shopItem)
            }
        }.start()
    }

    override fun shopItemUpdated(shopItem: ShopItem) {
        Thread {
            AppDatabase.getInstance(this@ScrollingActivity)
                .shopItemDao().updateShopItem(shopItem)

            runOnUiThread{
                shopItemAdapter.updateShopItem(shopItem, editIndex)
            }
        }.start()
    }

}
