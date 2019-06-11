package vn.loitp.app.activity.customviews.bottomnavigationbar

import android.content.Intent
import android.os.Bundle
import android.view.View

import loitp.basemaster.R
import vn.loitp.app.activity.customviews.bottomnavigationbar.bottombar.BottomBarActivity
import vn.loitp.core.base.BaseFontActivity
import vn.loitp.core.utilities.LActivityUtil

class BottomNavigationMenuActivity : BaseFontActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        findViewById<View>(R.id.bt_bottom_bar).setOnClickListener {
            val intent = Intent(activity, BottomBarActivity::class.java)
            startActivity(intent)
            LActivityUtil.tranIn(activity)
        }
    }

    override fun setFullScreen(): Boolean {
        return false
    }

    override fun setTag(): String {
        return javaClass.simpleName
    }

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_menu_bottom_navigation_bar
    }
}