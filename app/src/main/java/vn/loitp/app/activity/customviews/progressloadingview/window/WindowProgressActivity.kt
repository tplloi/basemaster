package vn.loitp.app.activity.customviews.progressloadingview.window

import android.os.Bundle
import com.core.base.BaseFontActivity
import kotlinx.android.synthetic.main.activity_window_progress.*
import loitp.basemaster.R

class WindowProgressActivity : BaseFontActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        wp10progressBar.setIndicatorRadius(5)
        showWP7Btn.setOnClickListener { wp7progressBar.showProgressBar() }
        hideWP7Btn.setOnClickListener { wp7progressBar.hideProgressBar() }
        showWP10Btn.setOnClickListener { wp10progressBar.showProgressBar() }
        hideWP10Btn.setOnClickListener { wp10progressBar.hideProgressBar() }
    }

    override fun setFullScreen(): Boolean {
        return false
    }

    override fun setTag(): String? {
        return javaClass.simpleName
    }

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_window_progress
    }
}