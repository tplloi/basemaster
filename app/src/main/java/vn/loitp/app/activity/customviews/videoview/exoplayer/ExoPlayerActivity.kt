package vn.loitp.app.activity.customviews.videoview.exoplayer

import android.content.res.Configuration
import android.os.Bundle
import com.annotation.IsFullScreen
import com.annotation.LogTag
import com.core.base.BaseFontActivity
import com.core.common.Constants
import com.core.utilities.LScreenUtil
import com.views.exo.PlayerManager
import kotlinx.android.synthetic.main.activity_video_exo_player.*
import kotlinx.android.synthetic.main.exo_playback_control_view.*
import vn.loitp.app.R

//custom UI exo_playback_control_view.xml

@LogTag("ExoPlayerActivity")
@IsFullScreen(false)
class ExoPlayerActivity : BaseFontActivity() {
    private var playerManager: PlayerManager? = null
    private var linkPlay = ""

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_video_exo_player
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        linkPlay = intent.getStringExtra(Constants.KEY_VIDEO_LINK_PLAY) ?: ""
        val linkIMAAd = intent.getStringExtra(Constants.KEY_VIDEO_LINK_IMA_AD)

        playerManager = linkIMAAd?.let {
            PlayerManager(context = this, urlIMAAd = it)
        } ?: PlayerManager(this)

        //warning: do not change id exo_fullscreen, exo_fullscreen
        playerManager?.updateSizePlayerView(playerView = playerView, exoFullscreen = exo_fullscreen)
        exo_fullscreen.setOnClickListener {
            playerManager?.toggleFullscreen(this)
        }
    }

    public override fun onResume() {
        super.onResume()
        playerManager?.init(context = this, playerView = playerView, linkPlay = linkPlay)
    }

    public override fun onPause() {
        super.onPause()
        playerManager?.reset()
    }

    public override fun onDestroy() {
        playerManager?.release()
        super.onDestroy()
    }

    override fun onBackPressed() {
        if (LScreenUtil.isLandscape()) {
            playerManager?.toggleFullscreen(this)
        } else {
            super.onBackPressed()
        }
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)

        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            playerManager?.updateSizePlayerView(playerView = playerView, exoFullscreen = exo_fullscreen)
        } else {
            playerManager?.updateSizePlayerView(playerView = playerView, exoFullscreen = exo_fullscreen)
        }
    }
}
