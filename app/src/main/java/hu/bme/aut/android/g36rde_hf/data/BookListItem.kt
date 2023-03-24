package hu.bme.aut.android.g36rde_hf.data

import android.graphics.pdf.PdfDocument
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import java.io.Serializable
import java.time.LocalDate


@Entity(tableName = "booklistitem")
data class BookListItem (
    @ColumnInfo(name = "id") @PrimaryKey(autoGenerate = true) var id: Long? = null,
    @ColumnInfo(name = "name") var name: String,
    @ColumnInfo(name = "Author") var author: String,
    @ColumnInfo(name = "description") var description: String,
    @ColumnInfo(name = "category") var category: Category,
    @ColumnInfo(name = "page_count") var pageCount: Int,
    @ColumnInfo(name = "current_page") var currentPage: Int,
    @ColumnInfo(name = "is_bought") var isBought: Boolean,
    @ColumnInfo(name = "is_read") var isRead: Boolean,
    @ColumnInfo(name = "is_favorite") var isFavorite: Boolean,
    @ColumnInfo(name = "subtitle") var subtitle : String,
    @ColumnInfo(name = "first_edition") var firstEdition: String


    ) :Serializable {
    enum class Category {
        SCIFI, FANTASY, LITERARY, HISTROCIAL, SLEFHELP, ENTERTAINMENT, MANUAL, COOKING, DOCU, ROMANTIC;
        companion object {
            @JvmStatic
            @TypeConverter
            fun getByOrdinal(ordinal: Int): Category? {
                var ret: Category? = null
                for (cat in values()) {
                    if (cat.ordinal == ordinal) {
                        ret = cat
                        break
                    }
                }
                return ret
            }

            @JvmStatic
            @TypeConverter
            fun toInt(category: Category): Int {
                return category.ordinal
            }
        }
    }

}
