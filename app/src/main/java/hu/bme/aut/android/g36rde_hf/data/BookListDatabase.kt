package hu.bme.aut.android.g36rde_hf.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters


@Database(entities = [BookListItem::class], version = 4 )
@TypeConverters(value = [BookListItem.Category::class])
abstract class BookListDatabase : RoomDatabase() {
    abstract fun bookListItemDAO(): BookListItemDAO

    companion object {
        fun getDatabase(applicationContext: Context): BookListDatabase {
            return Room.databaseBuilder(
                applicationContext,
                BookListDatabase::class.java,
                "books-list"
            ).fallbackToDestructiveMigration().build()
        }
    }
}