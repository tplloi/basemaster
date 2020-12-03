package com.game.findnumber

import android.os.Bundle
import android.view.View
import com.R
import com.annotation.IsFullScreen
import com.annotation.LogTag
import com.core.base.BaseFontActivity
import com.core.utilities.LAnimationUtil
import com.core.utilities.LScreenUtil
import com.core.utilities.LSocialUtil
import com.core.utilities.LUIUtil
import com.daimajia.androidanimations.library.Techniques
import kotlinx.android.synthetic.main.l_activity_find_number_menu.*

@LogTag("MenuActivity")
@IsFullScreen(true)
//TODO single
//TODO time mode
//TODO 2 players
class MenuActivity : BaseFontActivity() {

    override fun setLayoutResourceId(): Int {
        return R.layout.l_activity_find_number_menu
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        LScreenUtil.toggleFullscreen(activity = this, isFullScreen = true)
        setupViews()
    }

    private fun setupViews() {
        LUIUtil.setDelay(200) {
            ivTitle?.visibility = View.VISIBLE
            LAnimationUtil.play(
                    view = ivTitle,
                    duration = 1000,
                    techniques = Techniques.ZoomInDown
            )
        }
        LUIUtil.setSafeOnClickListenerElastic(
                view = ivBack,
                runnable = {
                    onBackPressed()
                })
        LUIUtil.setSafeOnClickListenerElastic(
                view = ivMore,
                runnable = {
                    LSocialUtil.moreApp(activity = this)
                })
    }
}