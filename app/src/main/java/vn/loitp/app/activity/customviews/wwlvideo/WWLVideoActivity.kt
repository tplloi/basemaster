package vn.loitp.app.activity.customviews.wwlvideo

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.widget.FrameLayout
import com.annotation.IsFullScreen
import com.annotation.LogTag
import com.core.base.BaseActivity
import com.core.utilities.LAppResource
import com.views.wwlmusic.utils.LWWLMusicUiUtil
import com.views.wwlmusic.utils.LWWLMusicViewHelper
import com.views.wwlvideo.LWWLVideo
import kotlinx.android.synthetic.main.wwl_video_activity.*
import vn.loitp.app.R
import vn.loitp.app.activity.customviews.wwlvideo.detail.WWLVideoMetaInfoFragment
import vn.loitp.app.activity.customviews.wwlvideo.detail.WWLVideoPlayerFragment
import vn.loitp.app.activity.customviews.wwlvideo.detail.WWLVideoUpNextFragment
import vn.loitp.app.activity.customviews.wwlvideo.interfaces.FragmentHost
import vn.loitp.app.activity.customviews.wwlvideo.utils.WWLVideoDataset
import kotlin.math.max
import kotlin.math.min

//https://github.com/vn-ttinc/Youtube-Watch-While-Layout
@LogTag("WWLVideoActivity")
@IsFullScreen(false)
class WWLVideoActivity : BaseActivity(), LWWLVideo.Listener, FragmentHost {
    private var mLastAlpha = 0f
    private var fmrPlayerFragmentContainer: FrameLayout? = null
    private var frmPlayer: WWLVideoPlayerFragment? = null
    private var frmUpNext: WWLVideoUpNextFragment? = null
    private var frmMetaInfo: WWLVideoMetaInfoFragment? = null

    override fun setLayoutResourceId(): Int {
        return R.layout.wwl_video_activity
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        watchWhileLayout.setListener(this)
        frmPlayer = supportFragmentManager.findFragmentById(R.id.frmPlayer) as WWLVideoPlayerFragment?
        frmUpNext = supportFragmentManager.findFragmentById(R.id.frmUpNext) as WWLVideoUpNextFragment?
        frmMetaInfo = supportFragmentManager.findFragmentById(R.id.frmMetaInfo) as WWLVideoMetaInfoFragment?
    }

    override fun WWL_onSliding(offset: Float) {
        val alpha: Float
        if (offset > 2.0f) {
            alpha = mLastAlpha * (3.0f - offset)
        } else {
            alpha = if (offset > 1.0f) {
                0.25f + 0.75f * (2.0f - offset)
            } else {
                1.0f
            }
            if (offset in 0.0f..1.0f) {
                updateStatusBarAlpha(1.0f - offset)
            }
        }
        updatePlayerAlpha(alpha)
        mLastAlpha = alpha
    }

    override fun WWL_onClicked() {
        if (watchWhileLayout.mState == LWWLVideo.STATE_MINIMIZED) {
            watchWhileLayout.maximize(false)
        }
        if (watchWhileLayout.mState == LWWLVideo.STATE_MAXIMIZED) {
            frmPlayer?.toggleControls()
        }
    }

    override fun WWL_onHided() {
        frmPlayer?.stopPlay()
    }

    override fun onDestroy() {
        frmPlayer?.stopPlay()
        super.onDestroy()
    }

    override fun WWL_minimized() {
        mLastAlpha = 0.0f
        frmPlayer?.hideControls()
    }

    override fun WWL_maximized() {
        mLastAlpha = 1.0f
    }

    override fun goToDetail(item: WWLVideoDataset.DatasetItem) {
        if (watchWhileLayout.mState == LWWLVideo.STATE_HIDED) {
            watchWhileLayout.mState = LWWLVideo.STATE_MAXIMIZED
            watchWhileLayout.mIsFullscreen = false
            if (watchWhileLayout.canAnimate()) {
                watchWhileLayout.setAnimatePos(watchWhileLayout.mMaxY)
            }
            watchWhileLayout.reset()
        }
        watchWhileLayout.maximize(false)
        frmPlayer?.startPlay(item)
        frmUpNext?.updateItem(item)
        frmMetaInfo?.updateItem(item)
    }

    override fun onVideoCollapse() {
        LWWLMusicUiUtil.showSystemUI(this)
        watchWhileLayout.exitFullscreenToMinimize()
        frmPlayer?.switchFullscreen(false)
        watchWhileLayout.minimize(false)
    }

    override fun onVideoFullscreen(selected: Boolean) {
        if (selected) {
            LWWLMusicUiUtil.hideSystemUI(this)
            watchWhileLayout.enterFullscreen()
        } else {
            LWWLMusicUiUtil.showSystemUI(this)
            watchWhileLayout.exitFullscreen()
        }
        frmPlayer?.switchFullscreen(selected)
    }

    private fun updateStatusBarAlpha(alpha: Float) {
        if (Build.VERSION.SDK_INT >= 21) {
            val color = LAppResource.getColor(R.color.colorPrimaryDark)
            val color2 = Color.BLACK
            val color3 = LWWLMusicViewHelper.evaluateColorAlpha(max(0.0f, min(1.0f, alpha)), color, color2)
            window.statusBarColor = color3
        }
    }

    private fun updatePlayerAlpha(alpha: Float) {
        fmrPlayerFragmentContainer?.alpha = alpha
    }
}
