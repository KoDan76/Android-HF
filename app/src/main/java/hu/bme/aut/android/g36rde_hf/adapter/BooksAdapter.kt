package hu.bme.aut.android.g36rde_hf.adapter

import android.annotation.SuppressLint
import android.content.Intent
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.DrawableRes
import androidx.recyclerview.widget.RecyclerView
import hu.bme.aut.android.g36rde_hf.R
import hu.bme.aut.android.g36rde_hf.data.BookListItem
import hu.bme.aut.android.g36rde_hf.databinding.ItemBooksListBinding
import kotlin.math.roundToInt

class BooksAdapter(private val listener: BooksItemClickListener) :
    RecyclerView.Adapter<BooksAdapter.BooksViewHolder>() {

    private val items = mutableListOf<BookListItem>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = BooksViewHolder(
        ItemBooksListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun onBindViewHolder(holder: BooksViewHolder, position: Int) {
        val bookItem = items[position]

        holder.binding.ivIcon.setImageResource(getImageResource(bookItem.category))
        holder.binding.cbIsBought.isChecked = bookItem.isBought
        holder.binding.cbIsRead.isChecked = bookItem.isRead
        holder.binding.isFavourite.isChecked = bookItem.isFavorite
        holder.binding.tvAuthor.text = bookItem.author
        holder.binding.tvName.text = bookItem.name
        holder.binding.tvCategory.text = bookItem.category.name
        holder.binding.tvPrice.text =" ${bookItem.currentPage} / ${bookItem.pageCount} Pages"

        holder.binding.ibRemove.setOnClickListener { buttonView ->
            removeItem(bookItem, position)
            listener.onItemDeleted(bookItem)
        }

        holder.binding.etOnPage.setOnKeyListener { v, keyCode, event ->
            if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
                (keyCode == KeyEvent.KEYCODE_ENTER )
            ) {
                bookItem.currentPage = holder.binding.etOnPage.text.toString().toInt()
                listener.onItemChanged(bookItem)
                return@setOnKeyListener true
            }
            return@setOnKeyListener false
        }

        holder.binding.cbIsBought.setOnCheckedChangeListener { buttonView, isChecked ->
            bookItem.isBought = isChecked
            listener.onItemChanged(bookItem)
        }
        holder.binding.cbIsRead.setOnCheckedChangeListener { buttonView, isChecked ->
            bookItem.isRead = isChecked
            listener.onItemChanged(bookItem)
        }
        holder.binding.isFavourite.setOnCheckedChangeListener { buttonView, isChecked ->
            bookItem.isFavorite = isChecked
            listener.onItemChanged(bookItem)
        }
        holder.binding.btDescription.setOnClickListener {
            listener.onItemDetailsClicked(bookItem)
        }
    }

    @DrawableRes()
    private fun getImageResource(category: BookListItem.Category): Int {
        return R.drawable.open_book
    }


    fun removeItem(item: BookListItem, position: Int){
        items.remove(item)
        notifyItemRemoved(position)
    }

    fun addItem(item: BookListItem) {
        items.add(item)
        notifyItemInserted(items.size - 1)
    }

    fun update(bookItems: List<BookListItem>) {
        items.clear()
        items.addAll(bookItems)
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = items.size

    interface BooksItemClickListener {
        fun onItemChanged(item: BookListItem)
        fun onItemDetailsClicked(item: BookListItem)
        fun onItemDeleted(item: BookListItem)
    }

    inner class BooksViewHolder(val binding: ItemBooksListBinding) : RecyclerView.ViewHolder(binding.root)
}