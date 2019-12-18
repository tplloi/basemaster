package vn.loitp.app.activity.demo.architecturecomponent.room.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = Word.TABLE_WORD)
class Word {
    companion object {
        const val DB_NAME_WORD = "word_database"
        const val TABLE_WORD = "word_table"
        const val COL_ID = "id"
        const val COL_WORD = "word"
    }

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = COL_ID)
    var id: Long = 0

    @ColumnInfo(name = COL_WORD)
    var word: String? = null
}