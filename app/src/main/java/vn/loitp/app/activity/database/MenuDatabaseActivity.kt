package vn.loitp.app.activity.database

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.View.OnClickListener

import com.core.base.BaseFontActivity
import com.core.utilities.LActivityUtil

import loitp.basemaster.R
import vn.loitp.app.activity.database.readsqliteasset.ReadSqliteAssetActivity
import vn.loitp.app.activity.database.realm.RealmActivity
import vn.loitp.app.activity.database.sqlite.SqliteActivity

class MenuDatabaseActivity : BaseFontActivity(), OnClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        findViewById<View>(R.id.bt_sqlite).setOnClickListener(this)
        findViewById<View>(R.id.bt_realm).setOnClickListener(this)
        findViewById<View>(R.id.bt_sqlite_asset).setOnClickListener(this)
    }

    override fun setFullScreen(): Boolean {
        return false
    }

    override fun setTag(): String? {
        return javaClass.simpleName
    }

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_menu_database
    }

    override fun onClick(v: View) {
        var intent: Intent? = null
        when (v.id) {
            R.id.bt_sqlite -> intent = Intent(activity, SqliteActivity::class.java)
            R.id.bt_realm -> intent = Intent(activity, RealmActivity::class.java)
            R.id.bt_sqlite_asset -> intent = Intent(activity, ReadSqliteAssetActivity::class.java)
        }
        if (intent != null) {
            startActivity(intent)
            LActivityUtil.tranIn(activity)
        }
    }
}