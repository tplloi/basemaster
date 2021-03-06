package vn.loitp.app.activity.demo.ebookwithrealm.realm

import android.app.Activity
import android.app.Application
import androidx.fragment.app.Fragment
import io.realm.Realm
import io.realm.RealmResults
import vn.loitp.app.activity.demo.ebookwithrealm.model.Book

class RealmController private constructor() {
    val realm: Realm = Realm.getDefaultInstance()

    //Refresh the realm istance
    fun refresh() {
        realm.refresh()
    }

    //clear all objects from Book.class
    fun clearAll() {
        realm.beginTransaction()
        realm.clear(Book::class.java)
        realm.commitTransaction()
    }

    //find all objects in the Book.class
    val books: RealmResults<Book>
        get() = realm.where(Book::class.java).findAll()

    //query a single item with the given id
    fun getBook(id: String?): Book {
        return realm.where(Book::class.java).equalTo("id", id).findFirst()
    }

    //check if Book.class is empty
    fun hasBooks(): Boolean {
        return !realm.allObjects(Book::class.java).isEmpty()
    }

    //query example
    fun queryedBooks(): RealmResults<Book> {
        return realm.where(Book::class.java)
                .contains("author", "Author 0")
                .or()
                .contains("title", "Realm")
                .findAll()
    }

    companion object {
        @JvmStatic
        var instance: RealmController? = null
            private set

        fun with(fragment: Fragment): RealmController? {
            if (instance == null) {
                instance = RealmController()
            }
            return instance
        }

        fun with(activity: Activity): RealmController? {
            if (instance == null) {
                instance = RealmController()
            }
            return instance
        }

        fun with(application: Application): RealmController? {
            if (instance == null) {
                instance = RealmController()
            }
            return instance
        }
    }

}
