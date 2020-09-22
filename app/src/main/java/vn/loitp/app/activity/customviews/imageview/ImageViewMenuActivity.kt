package vn.loitp.app.activity.customviews.imageview

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.View.OnClickListener
import com.annotation.IsFullScreen
import com.annotation.LayoutId
import com.annotation.LogTag
import com.core.base.BaseFontActivity
import com.core.utilities.LActivityUtil
import kotlinx.android.synthetic.main.activity_imageview_menu.*
import vn.loitp.app.R
import vn.loitp.app.activity.customviews.imageview.bigimageview.BigImageViewActivity
import vn.loitp.app.activity.customviews.imageview.bigimageview.BigImageViewWithScrollViewActivity
import vn.loitp.app.activity.customviews.imageview.blurimageview.BlurImageViewActivity
import vn.loitp.app.activity.customviews.imageview.circleimageview.CircleImageViewActivity
import vn.loitp.app.activity.customviews.imageview.continuousscrollableimageview.ContinuousScrollableImageViewActivity
import vn.loitp.app.activity.customviews.imageview.fidgetspinnerimageview.FidgetSpinnerImageViewActivity
import vn.loitp.app.activity.customviews.imageview.kenburnview.KenburnViewActivity
import vn.loitp.app.activity.customviews.imageview.panoramaimageview.PanoramaImageViewActivity
import vn.loitp.app.activity.customviews.imageview.pinchtozoom.PinchToZoomActivity
import vn.loitp.app.activity.customviews.imageview.pinchtozoom.PinchToZoomViewPagerActivity
import vn.loitp.app.activity.customviews.imageview.scrollparallaximageview.ScrollParallaxImageViewActivity
import vn.loitp.app.activity.customviews.imageview.strectchyimageview.StrectchyImageViewActivity
import vn.loitp.app.activity.customviews.imageview.touchimageview.TouchImageViewActivity
import vn.loitp.app.activity.customviews.imageview.zoomimageview.ZoomImageViewActivity

@LayoutId(R.layout.activity_imageview_menu)
@LogTag("ImageViewMenuActivity")
@IsFullScreen(false)
class ImageViewMenuActivity : BaseFontActivity(), OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        btBlurImageView.setOnClickListener(this)
        btCirleImageView.setOnClickListener(this)
        btStretchyImageView.setOnClickListener(this)
        btTouchImageView.setOnClickListener(this)
        btZoomImageView.setOnClickListener(this)
        btFidgetSpinner.setOnClickListener(this)
        btContinuousScrollableImageView.setOnClickListener(this)
        btScrollParallaxImageView.setOnClickListener(this)
        btPanoramaImageView.setOnClickListener(this)
        btBigImageView.setOnClickListener(this)
        btBigImageViewWithScrollView.setOnClickListener(this)
        btPinchToZoomWithViewPager.setOnClickListener(this)
        btPinchToZoom.setOnClickListener(this)
        btKenburnView.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        var intent: Intent? = null
        when (v) {
            btBlurImageView -> intent = Intent(this, BlurImageViewActivity::class.java)
            btCirleImageView -> intent = Intent(this, CircleImageViewActivity::class.java)
            btStretchyImageView -> intent = Intent(this, StrectchyImageViewActivity::class.java)
            btTouchImageView -> intent = Intent(this, TouchImageViewActivity::class.java)
            btZoomImageView -> intent = Intent(this, ZoomImageViewActivity::class.java)
            btFidgetSpinner -> intent = Intent(this, FidgetSpinnerImageViewActivity::class.java)
            btContinuousScrollableImageView -> intent = Intent(this, ContinuousScrollableImageViewActivity::class.java)
            btScrollParallaxImageView -> intent = Intent(this, ScrollParallaxImageViewActivity::class.java)
            btPanoramaImageView -> intent = Intent(this, PanoramaImageViewActivity::class.java)
            btBigImageView -> intent = Intent(this, BigImageViewActivity::class.java)
            btBigImageViewWithScrollView -> intent = Intent(this, BigImageViewWithScrollViewActivity::class.java)
            btPinchToZoom -> intent = Intent(this, PinchToZoomActivity::class.java)
            btPinchToZoomWithViewPager -> intent = Intent(this, PinchToZoomViewPagerActivity::class.java)
            btKenburnView -> intent = Intent(this, KenburnViewActivity::class.java)
        }
        intent?.let { i ->
            startActivity(i)
            LActivityUtil.tranIn(this)
        }
    }
}
