package vn.loitp.app.activity.database.sqlitemultitable

import android.os.Bundle
import android.widget.TextView
import com.annotation.IsFullScreen
import com.annotation.LogTag
import com.core.base.BaseApplication
import com.core.base.BaseFontActivity
import com.core.utilities.LUIUtil
import kotlinx.android.synthetic.main.activity_sqlite_multi_table.*
import vn.loitp.app.R
import vn.loitp.app.activity.database.sqlitemultitable.helper.DatabaseHelper
import vn.loitp.app.activity.database.sqlitemultitable.model.Note
import vn.loitp.app.activity.database.sqlitemultitable.model.Tag

//https://www.androidhive.info/2013/09/android-sqlite-database-with-multiple-tables/

@LogTag("SqliteMultiTableActivity")
@IsFullScreen(false)
class SqliteMultiTableActivity : BaseFontActivity() {
    private lateinit var databaseHelper: DatabaseHelper

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_sqlite_multi_table
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        test()
    }

    private fun test() {
        databaseHelper = DatabaseHelper(applicationContext)

        // Creating tags
        val tag1 = Tag("Shopping" + System.currentTimeMillis())
        val tag2 = Tag("Important" + System.currentTimeMillis())
        val tag3 = Tag("Watchlist" + System.currentTimeMillis())
        val tag4 = Tag("Androidhive" + System.currentTimeMillis())

        // Inserting tags in db
        val tag1Id = databaseHelper.createTag(tag1)
        val tag2Id = databaseHelper.createTag(tag2)
        val tag3Id = databaseHelper.createTag(tag3)
        val tag4Id = databaseHelper.createTag(tag4)

        showMsg("tag1Id: $tag1Id")
        showMsg("tag2Id: $tag2Id")
        showMsg("tag3Id: $tag3Id")
        showMsg("tag4Id: $tag4Id")

        val tagList = databaseHelper.getTagList()
        showMsg("tagList size: " + tagList.size)
        for (i in tagList.indices) {
            val t = tagList[i]
            showMsg("tagList -> " + i + " -> " + BaseApplication.gson.toJson(t))
        }

        // Creating note
        val note1 = Note("iPhone 5S", 0)
        val note2 = Note("Galaxy Note II", 0)
        val note3 = Note("Whiteboard", 0)

        val note4 = Note("Riddick", 0)
        val note5 = Note("Prisoners", 0)
        val note6 = Note("The Croods", 0)
        val note7 = Note("Insidious: Chapter 2", 0)

        val note8 = Note("Don't forget to call MOM", 0)
        val note9 = Note("Collect money from John", 0)

        val note10 = Note("Post new Article", 0)
        val note11 = Note("Take database backup", 0)

        // Inserting note in db
        // Inserting note under "Shopping" Tag
        val note1Id = databaseHelper.createNote(note1, longArrayOf(tag1Id))
        val note2Id = databaseHelper.createNote(note2, longArrayOf(tag1Id))
        val note3Id = databaseHelper.createNote(note3, longArrayOf(tag1Id))

        // Inserting note under "Important" Tag
        val note8Id = databaseHelper.createNote(note8, longArrayOf(tag2Id))
        val note9Id = databaseHelper.createNote(note9, longArrayOf(tag2Id))

        // Inserting note under "Watchlist" Tag
        val note4Id = databaseHelper.createNote(note4, longArrayOf(tag3Id))
        val note5Id = databaseHelper.createNote(note5, longArrayOf(tag3Id))
        val note6Id = databaseHelper.createNote(note6, longArrayOf(tag3Id))
        val note7Id = databaseHelper.createNote(note7, longArrayOf(tag3Id))

        // Inserting note under "Androidhive" Tag
        val note10Id = databaseHelper.createNote(note10, longArrayOf(tag4Id))
        val note11Id = databaseHelper.createNote(note11, longArrayOf(tag4Id))

        // "Post new Article" - assigning this under "Important" Tag
        // Now this will have - "Androidhive" and "Important" Tags
        databaseHelper.createNoteTag(note10Id, tag2Id)

        val noteCount = databaseHelper.getNoteCount()
        showMsg("getNoteCount: $noteCount")

        val noteList = databaseHelper.getNoteList()
        showMsg("noteList size: " + noteList.size)
        for (i in noteList.indices) {
            val td = noteList[i]
            showMsg(">noteList " + i + " -> " + BaseApplication.gson.toJson(td))
        }

        // Getting note under "Watchlist" tag name
        val tagsWatchList = databaseHelper.getAllNoteByTag(tag3.tagName)
        for (i in tagsWatchList.indices) {
            val td = tagsWatchList[i]
            showMsg(">tagsWatchList " + i + " -> " + BaseApplication.gson.toJson(td))
        }

        // Deleting
        showMsg("Tag Count Before Deleting: " + databaseHelper.getNoteCount())
        databaseHelper.deleteNote(note8Id)
        showMsg("Tag Count After Deleting: " + databaseHelper.getNoteCount())

        // Deleting all note under "Shopping" tag
        showMsg("Tag Count Before Deleting 'Shopping' note: " + databaseHelper.getNoteCount())
        databaseHelper.deleteTag(tag1, true)
        showMsg("Tag Count After Deleting 'Shopping' note: " + databaseHelper.getNoteCount())

        // Updating tag name
        tag3.tagName = "Movies to watch"
        databaseHelper.updateTag(tag3)

        // Don't forget to close database connection
        databaseHelper.closeDB()
    }

    override fun onDestroy() {
        databaseHelper.deleteAllDatabase()
        databaseHelper.closeDB()
        super.onDestroy()
    }

    private fun showMsg(msg: String) {
        logD(msg)
        val tv = TextView(this)
        tv.text = msg
        LUIUtil.setTextSize(textView = tv, size = resources.getDimension(R.dimen.txt_small))
        ll.addView(tv)
    }
}
