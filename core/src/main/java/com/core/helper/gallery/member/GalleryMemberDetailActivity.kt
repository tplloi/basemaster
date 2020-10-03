package com.core.helper.gallery.member

import android.os.Bundle
import android.view.View
import com.R
import com.annotation.IsFullScreen
import com.annotation.LogTag
import com.core.base.BaseFontActivity
import com.core.utilities.LActivityUtil
import com.core.utilities.LImageUtil
import com.core.utilities.LUIUtil
import com.restapi.flickr.model.photosetgetphotos.Photo
import com.views.layout.swipeback.SwipeBackLayout
import kotlinx.android.synthetic.main.l_activity_flickr_member_detail.*

@LogTag("GalleryMemberDetailActivity")
@IsFullScreen(false)
class GalleryMemberDetailActivity : BaseFontActivity() {

    companion object {
        const val PHOTO = "PHOTO"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.l_activity_flickr_member_detail)

        LUIUtil.setTextShadow(textView = tvTitle)
        val photo = intent.getSerializableExtra(PHOTO) as Photo
        loadItem(photo = photo)

        LImageUtil.setImageViewZoom(iv = imageView)

        swipeBackLayout.setSwipeBackListener(object : SwipeBackLayout.OnSwipeBackListener {
            override fun onViewPositionChanged(mView: View, swipeBackFraction: Float, SWIPE_BACK_FACTOR: Float) {
            }

            override fun onViewSwipeFinished(mView: View, isEnd: Boolean) {
                if (isEnd) {
                    finish()
                    LActivityUtil.transActivityNoAnimation(this@GalleryMemberDetailActivity)
                }
            }
        })
    }

    private fun loadItem(photo: Photo) {
        tvTitle.text = photo.title
        LImageUtil.load(context = this, url = photo.urlO, imageView = imageView)
        LImageUtil.load(context = this, url = photo.urlS, imageView = imageViewBlur)
    }
}
