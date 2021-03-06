package vn.loitp.app.activity.function.fullscreen

import android.os.Bundle
import android.view.View
import com.annotation.IsFullScreen
import com.annotation.LogTag
import com.core.base.BaseFontActivity
import com.core.utilities.LScreenUtil
import com.views.dialog.imersivedialog.ImmersiveDialogFragment
import kotlinx.android.synthetic.main.activity_func_fullscreen.*
import vn.loitp.app.R

@LogTag("FullScreenActivity")
@IsFullScreen(false)
class FullScreenActivity : BaseFontActivity(), View.OnClickListener {

    private var isFullScreen: Boolean = false

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_func_fullscreen
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        btToggleFullScreen.setOnClickListener(this)
        btShowDialog.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when (v) {
            btToggleFullScreen -> {
                isFullScreen = !isFullScreen
                LScreenUtil.toggleFullscreen(this, isFullScreen)
            }
            btShowDialog -> showDialog()
        }
    }

    private fun showDialog() {
        ImmersiveDialogFragment().showImmersive(this)
    }
}
