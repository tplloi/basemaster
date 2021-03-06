package vn.loitp.app.activity.database.sqlite

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.Button
import com.annotation.IsFullScreen
import com.annotation.LogTag
import com.core.base.BaseApplication.Companion.gson
import com.core.base.BaseFontActivity
import kotlinx.android.synthetic.main.activity_sqlite.*
import vn.loitp.app.R

@LogTag("SqliteActivity")
@IsFullScreen(false)
class SqliteActivity : BaseFontActivity(), View.OnClickListener {

    private var databaseHandler: DatabaseHandler? = null

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_sqlite
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        databaseHandler = DatabaseHandler(this)
        btAdd.setOnClickListener(this)
        btClearAll.setOnClickListener(this)
        btGetContactWithId.setOnClickListener(this)
        btGetContactListPage1.setOnClickListener(this)
        btAdd100.setOnClickListener(this)
        allContact
    }

    override fun onClick(v: View) {
        when (v) {
            btAdd -> addContact()
            btAdd100 -> add100Contact()
            btClearAll -> clearAllContact()
            btGetContactWithId -> getContactWithId(2)
            btGetContactListPage1 -> getContactListPage(1)
        }
    }

    private val allContact: Unit
        get() {
            val contactList = databaseHandler?.allContacts
            contactList?.forEach {
                addButton(it)
            }
        }

    @SuppressLint("SetTextI18n")
    private fun addButton(contact: Contact) {
        val button = Button(this)
        button.text = contact.id.toString() + " " + contact.name
        button.setOnClickListener {
            updateContact(contact = contact, button = button)
        }
        button.setOnLongClickListener {
            deleteContact(contact, button)
            true
        }
        ll.addView(button)
    }

    @SuppressLint("SetTextI18n")
    private fun addButton() {
        val button = Button(this)
        databaseHandler?.contactsCount?.let { id ->
            val contact = databaseHandler?.getContact(id)
            if (contact != null) {
                button.text = contact.id.toString() + " - " + contact.name
                button.setOnClickListener {
                    updateContact(contact, button)
                }
                button.setOnLongClickListener {
                    deleteContact(contact, button)
                    true
                }
                ll.addView(button)
            }
        }
    }

    private fun addContact() {
        val size = databaseHandler?.contactsCount
        size?.let {
            val contact = Contact()
            contact.name = "name " + (it + 1)
            contact.phoneNumber = "phone: " + (it + 1)
            databaseHandler?.addContact(contact)
            addButton()
        }
    }

    private fun add100Contact() {
        for (i in 0..99) {
            val size = databaseHandler?.contactsCount
            size?.let {
                val contact = Contact()
                contact.name = "name " + (it + 1)
                contact.phoneNumber = "phone: " + (it + 1)
                databaseHandler?.addContact(contact)
                addButton()
            }
        }
    }

    private fun clearAllContact() {
        ll.removeAllViews()
        databaseHandler?.clearAllContact()
        allContact
    }

    private fun getContactWithId(id: Int) {
        val contact = databaseHandler?.getContact(id)
        if (contact == null) {
            showShortInformation("Contact with ID=$id not found", true)
        } else {
            showShortInformation("Found: " + contact.id + " " + contact.name + " " + contact.phoneNumber, true)
        }
    }

    @SuppressLint("SetTextI18n")
    private fun updateContact(contact: Contact, button: Button) {
        contact.name = "Updated " + contact.name
        val result = databaseHandler?.updateContact(contact)
        button.text = contact.id.toString() + " " + contact.name
    }

    private fun deleteContact(contact: Contact, button: Button) {
        val result = databaseHandler?.deleteContact(contact)
        ll.removeView(button)
    }

    private fun getContactListPage(page: Int) {
        val contactList = databaseHandler?.getContactListWithPage(page, 5)
        showDialogMsg("getContactListPage page: $page ${gson.toJson(contactList)}")
    }
}
