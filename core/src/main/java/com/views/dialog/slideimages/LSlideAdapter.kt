package com.views.dialog.slideimages

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.viewpager.widget.PagerAdapter
import com.R
import com.core.utilities.LImageUtil
import com.core.utilities.LScreenUtil
import kotlinx.android.synthetic.main.l_frm_image_slide.view.*

class LSlideAdapter(private val mContext: Context, private val stringList: List<String>?,
                    private val isShowIconClose: Boolean,
                    private val callback: Callback?) : PagerAdapter() {
//    private val screenW: Int = LScreenUtil.screenWidth

    interface Callback {
        fun onClickClose()
    }

    override fun instantiateItem(collection: ViewGroup, position: Int): Any {
        val inflater = LayoutInflater.from(mContext)
        val layout = inflater.inflate(R.layout.l_frm_image_slide, collection, false) as ViewGroup

        layout.ivClose.visibility = if (isShowIconClose) View.VISIBLE else View.INVISIBLE
//        val sizeW = screenW
//        imageView.layoutParams.width = sizeW
//        imageView.requestLayout()
        val url = stringList?.get(position)
//        LImageUtil.load(context = mContext, any = url, imageView = iv, resPlaceHolder = screenW, resError = screenW * 9 / 16)
        LImageUtil.load(context = mContext, any = url, imageView = layout.imageView)

        layout.ivClose.setOnClickListener {
            callback?.onClickClose()
        }
        layout.setOnClickListener {
            callback?.onClickClose()
        }
        collection.addView(layout)
        return layout
    }

    override fun getCount(): Int {
        return stringList?.size ?: 0
    }

    override fun isViewFromObject(arg0: View, arg1: Any): Boolean {
        return arg0 === arg1
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        //super.destroyItem(container, position, object);
    }
}
