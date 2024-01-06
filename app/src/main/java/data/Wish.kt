package data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "wish_table")
data class Wish(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    @ColumnInfo(name = "title")
    val title: String = "",
    @ColumnInfo(name = "description")
    val desc: String = ""
)

object DummyWish{
    val wishList = listOf<Wish>(
        Wish(id = 1, title = "Find a job", desc = "Find a job on student/junior position in hi-tech industry"),
        Wish(id = 2, title = "Increase my grade", desc = "Get a better grades to increase the average"),
        Wish(id = 3, title = "Be happy", desc = "Dont forget to be happy")
    )
}