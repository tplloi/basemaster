package vn.loitp.app.activity.animation.shadowviewhelper

import android.graphics.Color
import android.os.Bundle
import androidx.core.view.ViewCompat
import com.annotation.IsFullScreen
import com.annotation.LogTag
import com.core.base.BaseFontActivity
import com.core.utilities.LDisplayUtil
import com.views.shadowviewhelper.ShadowProperty
import com.views.shadowviewhelper.ShadowViewDrawable
import kotlinx.android.synthetic.main.activity_animation_shadowview_helper.*
import vn.loitp.app.R

//https://github.com/wangjiegulu/ShadowViewHelper?utm_source=android-arsenal.com&utm_medium=referral&utm_campaign=1884
@LogTag("ShadowViewHelperActivity")
@IsFullScreen(false)
class ShadowViewHelperActivity : BaseFontActivity() {

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_animation_shadowview_helper
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setAllSide()
        setCustomSide()
        setCustomSide2()
    }

    private fun setAllSide() {
        // all side shadow
        val sp = ShadowProperty()
                .setShadowColor(0x77000000)
                .setShadowDy(LDisplayUtil.dip2px(dpValue = 0.5f))
                .setShadowRadius(LDisplayUtil.dip2px(dpValue = 3f))
                .setShadowSide(ShadowProperty.ALL)
        val sd = ShadowViewDrawable(sp, Color.WHITE, 0f, 0f)
        ViewCompat.setBackground(ll0, sd)
        ViewCompat.setLayerType(ll0, ViewCompat.LAYER_TYPE_SOFTWARE, null)
    }

    private fun setCustomSide() {
        // only all sides except top shadow
        val sp = ShadowProperty()
                .setShadowColor(0x77ff0000)
                .setShadowDy(LDisplayUtil.dip2px(dpValue = 0.5f))
                .setShadowRadius(LDisplayUtil.dip2px(dpValue = 3f))
                .setShadowSide(ShadowProperty.LEFT or ShadowProperty.RIGHT or ShadowProperty.BOTTOM)
        val sd = ShadowViewDrawable(sp, Color.TRANSPARENT, 0f, 0f)
        ViewCompat.setBackground(ll1, sd)
        ViewCompat.setLayerType(ll1, ViewCompat.LAYER_TYPE_SOFTWARE, null)
    }

    private fun setCustomSide2() {
        // only all sides except top shadow
        val sp = ShadowProperty()
                .setShadowColor(0x7700ff00)
                .setShadowDy(LDisplayUtil.dip2px(dpValue = 0.5f))
                .setShadowRadius(LDisplayUtil.dip2px(dpValue = 3f))
                .setShadowSide(ShadowProperty.RIGHT or ShadowProperty.BOTTOM)
        val sd = ShadowViewDrawable(sp, Color.TRANSPARENT, 0f, 0f)
        ViewCompat.setBackground(imageView, sd)
        ViewCompat.setLayerType(imageView, ViewCompat.LAYER_TYPE_SOFTWARE, null)
    }

}
