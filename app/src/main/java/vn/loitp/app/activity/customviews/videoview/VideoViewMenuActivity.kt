package vn.loitp.app.activity.customviews.videoview

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.core.base.BaseFontActivity
import com.core.common.Constants.KEY_VIDEO_LINK_IMA_AD
import com.core.common.Constants.KEY_VIDEO_LINK_PLAY
import com.core.utilities.LActivityUtil.tranIn
import kotlinx.android.synthetic.main.activity_video_menu.*
import vn.loitp.app.R
import vn.loitp.app.activity.customviews.videoview.exoplayer.ExoPlayerActivity
import vn.loitp.app.activity.customviews.videoview.exoplayer.ExoPlayerActivity2
import vn.loitp.app.activity.customviews.videoview.exoplayer.ExoPlayerActivity3
import vn.loitp.app.activity.customviews.videoview.youtube.YoutubeActivity

class VideoViewMenuActivity : BaseFontActivity(), View.OnClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        btExoPlayer2.setOnClickListener(this)
        btExoPlayer2IMA.setOnClickListener(this)
        bt2.setOnClickListener(this)
        bt3.setOnClickListener(this)
        btYoutube.setOnClickListener(this)
    }

    override fun setFullScreen(): Boolean {
        return false
    }

    override fun setTag(): String? {
        return javaClass.simpleName
    }

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_video_menu
    }

    override fun onClick(v: View) {
        var intent: Intent? = null
        when (v) {
            btExoPlayer2 -> {
                intent = Intent(activity, ExoPlayerActivity::class.java)
                intent.putExtra(KEY_VIDEO_LINK_PLAY, "https://bitmovin-a.akamaihd.net/content/MI201109210084_1/mpds/f08e80da-bf1d-4e3d-8899-f0f6155f6efa.mpd")
            }
            btExoPlayer2IMA -> {
                intent = Intent(activity, ExoPlayerActivity::class.java)
                intent.putExtra(KEY_VIDEO_LINK_PLAY, "https://bitmovin-a.akamaihd.net/content/MI201109210084_1/mpds/f08e80da-bf1d-4e3d-8899-f0f6155f6efa.mpd")
                intent.putExtra(KEY_VIDEO_LINK_IMA_AD, getString(R.string.ad_tag_url))
            }
            bt2 -> intent = Intent(activity, ExoPlayerActivity2::class.java)
            bt3 -> intent = Intent(activity, ExoPlayerActivity3::class.java)
            btYoutube -> intent = Intent(activity, YoutubeActivity::class.java)
        }
        intent?.let {
            startActivity(it)
            tranIn(activity)
        }
    }
}