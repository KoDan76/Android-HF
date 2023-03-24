package hu.bme.aut.android.g36rde_hf

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import hu.bme.aut.android.g36rde_hf.adapter.BooksAdapter
import hu.bme.aut.android.g36rde_hf.data.BookListDatabase
import hu.bme.aut.android.g36rde_hf.data.BookListItem
import hu.bme.aut.android.g36rde_hf.databinding.ActivityMainBinding
import hu.bme.aut.android.g36rde_hf.fragments.NewBookListItemDialogFragment
import hu.bme.aut.android.g36rde_hf.fragments.PieChartFragment
import kotlin.concurrent.thread

class MainActivity : AppCompatActivity(), BooksAdapter.BooksItemClickListener,
    NewBookListItemDialogFragment.NewBookListItemDialogListener {
    private lateinit var binding: ActivityMainBinding
    private lateinit var database: BookListDatabase
    private lateinit var adapter: BooksAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)

        database = BookListDatabase.getDatabase(applicationContext)

        binding.fab.setOnClickListener {
            NewBookListItemDialogFragment().show(
                supportFragmentManager,
                NewBookListItemDialogFragment.TAG
            )
        }

        binding.fabPie.setOnClickListener {
            PieChartFragment().show(
                supportFragmentManager,
                PieChartFragment.TAG
            )
        }
        initRecyclerView()
    }
    private fun initRecyclerView() {
        adapter = BooksAdapter(this)
        binding.rvMain.layoutManager = LinearLayoutManager(this)
        binding.rvMain.adapter = adapter
        loadItemsInBackground()
    }
    private fun loadItemsInBackground() {
        thread {
            val items = database.bookListItemDAO().getAll()
            runOnUiThread {
                adapter.update(items)
            }
        }
    }

    override fun onItemChanged(item: BookListItem) {
        thread {
            database.bookListItemDAO().update(item)
            Log.d("MainActivity", "BookListItem update was successful")
        }
    }

    override fun onItemDetailsClicked(item: BookListItem) {

        //Legkevésbé sem objektum orientált, de az OOP megoldások nem működtek és nullptr exceptionöket dobáltak.
        val auth: String = item.author
        val titl: String = item.name
        val subt: String = item.subtitle
        val desc: String = item.description
        val date: String = item.firstEdition

        val i = Intent(this, DetailsActivity::class.java)
        i.putExtra("date", date)
        i.putExtra("auth", auth)
        i.putExtra("titl", titl)
        i.putExtra("subt", subt)
        i.putExtra("desc", desc)
        startActivity(i)
    }

    override fun onItemDeleted(item: BookListItem) {
        thread {
            database.bookListItemDAO().deleteItem(item )
            Log.d("MainActivity", "BookListItem deletion was successful")
        }
    }

    override fun onBookListItemCreated(newItem: BookListItem) {
        thread {
            val insertId = database.bookListItemDAO().insert(newItem)
            newItem.id = insertId
            runOnUiThread {
                adapter.addItem(newItem)
            }
        }
    }
}