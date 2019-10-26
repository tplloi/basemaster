package vn.loitp.app.activity.database.sharedprefsencryption

import android.os.Bundle
import com.core.base.BaseFontActivity
import com.core.utilities.LEncryptionSharedPrefsUtil
import kotlinx.android.synthetic.main.activity_shared_prefs.*
import loitp.basemaster.R
import vn.loitp.app.activity.pattern.mvp.User

class EnctyptionSharedPrefsActivity : BaseFontActivity() {

    private val KEY_STRING = "KEY_STRING"
    private val KEY_STRING_WITH_DEFAULT_VALUE = "KEY_STRING_WITH_DEFAULT_VALUE"
    private val KEY_BOOLEAN = "KEY_BOOLEAN"
    private val KEY_FLOAT = "KEY_FLOAT"
    private val KEY_INT = "KEY_INT"
    private val KEY_LONG = "KEY_LONG"
    private val KEY_OBJECT = "KEY_OBJECT"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        btPutString.setOnClickListener {
            LEncryptionSharedPrefsUtil.instance.put(KEY_STRING, "This is a string!!! " + System.currentTimeMillis())
        }
        btGetString.setOnClickListener {
            val value = LEncryptionSharedPrefsUtil.instance.getString(KEY_STRING)
            showLong(value)
        }

        /*btPutStringWithDefaultValue.setOnClickListener {
            LEncryptionSharedPrefsUtil.instance.putString(KEY_STRING_WITH_DEFAULT_VALUE, "This is a string!!! " + System.currentTimeMillis())
        }
        btGetStringWithDefaultValue.setOnClickListener {
            val value = LEncryptionSharedPrefsUtil.instance.getString(KEY_STRING_WITH_DEFAULT_VALUE, "Default value is Loitppp ahihi")
            showLong(value)
        }*/

        btPutBoolean.setOnClickListener {
            LEncryptionSharedPrefsUtil.instance.put(KEY_BOOLEAN, true)
        }
        btGetBoolean.setOnClickListener {
            val value = LEncryptionSharedPrefsUtil.instance.getBoolean(KEY_BOOLEAN)
            showLong("Value: $value")
        }

        btPutFloat.setOnClickListener {
            LEncryptionSharedPrefsUtil.instance.put(KEY_FLOAT, System.currentTimeMillis().toFloat())
        }
        btGetFloat.setOnClickListener {
            val value = LEncryptionSharedPrefsUtil.instance.getFloat(KEY_FLOAT)
            showLong("Value: $value")
        }

        btPutInt.setOnClickListener {
            LEncryptionSharedPrefsUtil.instance.put(KEY_INT, System.currentTimeMillis().toInt())
        }
        btGetInt.setOnClickListener {
            val value = LEncryptionSharedPrefsUtil.instance.getInt(KEY_INT)
            showLong("Value: $value")
        }

        btPutLong.setOnClickListener {
            LEncryptionSharedPrefsUtil.instance.put(KEY_LONG, System.currentTimeMillis())
        }
        btGetLong.setOnClickListener {
            val value = LEncryptionSharedPrefsUtil.instance.getLong(KEY_LONG)
            showLong("Value: $value")
        }

        btPutObject.setOnClickListener {
            val user = User()
            user.email = "Email ${System.currentTimeMillis()}"
            user.fullName = "Name ${System.currentTimeMillis()}"
            LEncryptionSharedPrefsUtil.instance.put(KEY_OBJECT, user)
        }
        btGetObject.setOnClickListener {
            val value = LEncryptionSharedPrefsUtil.instance.getObject(KEY_OBJECT, User::class.java)
            showLong("Value: $value")
        }
    }

    override fun setFullScreen(): Boolean {
        return false
    }

    override fun setTag(): String? {
        return javaClass.simpleName
    }

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_shared_prefs_encryption
    }
}
