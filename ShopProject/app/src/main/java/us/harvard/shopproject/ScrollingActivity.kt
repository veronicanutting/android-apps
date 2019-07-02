package us.harvard.shopproject

import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_scrolling.*
import us.harvard.shopproject.adapter.ShopItemAdapter
import us.harvard.shopproject.data.ShopItem

class ScrollingActivity : AppCompatActivity() {

    lateinit var shopItemAdapter: ShopItemAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scrolling)

        setSupportActionBar(toolbar)

        fabAddShopItem.setOnClickListener {
            ShopItemDialog().show(supportFragmentManager, "Dialog")
        }

        fabDeleteAll.setOnClickListener { view ->
            Snackbar.make(view, "Will delete all items",
                Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }

        initRecyclerView()

    }

    private fun initRecyclerView() {

        var shopItems = listOf<ShopItem>(
            ShopItem(true, "1 banana"),
            ShopItem(false, "fish heads"),
            ShopItem(false, "chicken feet"),
            ShopItem(false, "frogs' legs"),
            ShopItem(true, "ANCHOVIES"),
            ShopItem(false, "pack of strawberries"),
            ShopItem(true, "Pellegrino"),
            ShopItem(true, "meringues"),
            ShopItem(false, "pack of raspberries"),
            ShopItem(false, "pack of blackberries"),
            ShopItem(true, "4 apples"))

        shopItemAdapter = ShopItemAdapter(this, shopItems)
        recycler_view.adapter = shopItemAdapter

    }

}
