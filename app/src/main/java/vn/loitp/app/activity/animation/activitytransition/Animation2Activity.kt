package vn.loitp.app.activity.animation.activitytransition

import android.os.Bundle
import android.view.View
import android.view.View.OnClickListener

import loitp.basemaster.R
import vn.loitp.core.base.BaseFontActivity

class Animation2Activity : BaseFontActivity(), OnClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        isShowAdWhenExit = false
        findViewById<View>(R.id.tv_exit).setOnClickListener(this)
    }

    override fun setFullScreen(): Boolean {
        return false
    }

    override fun setTag(): String {
        return javaClass.simpleName
    }

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_animation_2
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.tv_exit -> onBackPressed()
        }
    }
}