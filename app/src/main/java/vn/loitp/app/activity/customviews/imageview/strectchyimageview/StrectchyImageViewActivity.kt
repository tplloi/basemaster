package vn.loitp.app.activity.customviews.imageview.strectchyimageview

import android.os.Bundle
import com.core.base.BaseFontActivity
import com.core.utilities.LImageUtil
import kotlinx.android.synthetic.main.activity_imageview_strectchy.*
import vn.loitp.app.R
import vn.loitp.app.common.Constants

class StrectchyImageViewActivity : BaseFontActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        LImageUtil.load(context = activity, url = Constants.URL_IMG_LONG, imageView = lStretchyImageView)
    }

    override fun setFullScreen(): Boolean {
        return false
    }

    override fun setTag(): String? {
        return javaClass.simpleName
    }

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_imageview_strectchy
    }
}
