package vn.loitp.app.activity.customviews.seekbar

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.annotation.IsFullScreen
import com.annotation.LogTag
import com.core.base.BaseFontActivity
import com.core.utilities.LActivityUtil
import kotlinx.android.synthetic.main.activity_seekbar_menu.*
import vn.loitp.app.R
import vn.loitp.app.activity.customviews.seekbar.boxedverticalseekbar.BoxedVerticalSeekBarActivity
import vn.loitp.app.activity.customviews.seekbar.seekbar.SeekbarActivity
import vn.loitp.app.activity.customviews.seekbar.verticalseekbar.VerticalSeekbarActivity

@LogTag("SeekbarMenuActivity")
@IsFullScreen(false)
class SeekbarMenuActivity : BaseFontActivity(), View.OnClickListener {

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_seekbar_menu
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        btBoxedVerticalSeekbar.setOnClickListener(this)
        btVerticalSeekBar.setOnClickListener(this)
        btSeekBar.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        var intent: Intent? = null
        when (v) {
            btBoxedVerticalSeekbar -> intent = Intent(this, BoxedVerticalSeekBarActivity::class.java)
            btVerticalSeekBar -> intent = Intent(this, VerticalSeekbarActivity::class.java)
            btSeekBar -> intent = Intent(this, SeekbarActivity::class.java)
        }
        intent?.let {
            startActivity(it)
            LActivityUtil.tranIn(this)
        }
    }
}
