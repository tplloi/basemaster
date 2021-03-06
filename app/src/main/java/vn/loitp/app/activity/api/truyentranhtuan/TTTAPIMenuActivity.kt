package vn.loitp.app.activity.api.truyentranhtuan

import android.content.Intent
import android.os.Bundle
import com.annotation.IsFullScreen
import com.annotation.LogTag
import com.core.base.BaseFontActivity
import com.core.utilities.LActivityUtil
import com.views.setSafeOnClickListener
import kotlinx.android.synthetic.main.activity_api_ttt_menu.*
import vn.loitp.app.R

@LogTag("TTTAPIMenuActivity")
@IsFullScreen(false)
class TTTAPIMenuActivity : BaseFontActivity() {

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_api_ttt_menu
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupViews()
    }

    private fun setupViews() {
        btComicList.setSafeOnClickListener {
            val intent = Intent(this, TTTAPIComicListActivity::class.java)
            startActivity(intent)
            LActivityUtil.tranIn(this)
        }
        btChapList.setSafeOnClickListener {
            val intent = Intent(this, TTTAPIChapListActivity::class.java)
            startActivity(intent)
            LActivityUtil.tranIn(this)
        }
        btPageList.setSafeOnClickListener {
            val intent = Intent(this, TTTAPIPageListActivity::class.java)
            startActivity(intent)
            LActivityUtil.tranIn(this)
        }
        btFavList.setSafeOnClickListener {
            val intent = Intent(this, TTTAPIFavListActivity::class.java)
            startActivity(intent)
            LActivityUtil.tranIn(this)
        }
    }

}
