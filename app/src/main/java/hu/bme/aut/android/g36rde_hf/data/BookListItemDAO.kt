package hu.bme.aut.android.g36rde_hf.data

import androidx.room.*

@Dao
interface BookListItemDAO {
    @Query("SELECT * FROM booklistitem")
    fun getAll(): List<BookListItem>

    @Insert
    fun insert(bookListItems: BookListItem): Long

    @Update
    fun update(bookListItem: BookListItem)

    @Delete
    fun deleteItem(bookListItem: BookListItem)
}