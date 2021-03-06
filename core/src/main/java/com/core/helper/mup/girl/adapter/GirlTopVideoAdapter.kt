package com.core.helper.mup.girl.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.BuildConfig
import com.R
import com.annotation.LogTag
import com.core.adapter.BaseAdapter
import com.core.common.Constants
import com.core.helper.mup.girl.model.GirlTopVideo
import com.core.helper.mup.girl.view.ViewGirlTopVideo
import com.core.utilities.LImageUtil
import com.views.setSafeOnClickListener
import kotlinx.android.synthetic.main.view_girl_top_video.view.*
import kotlinx.android.synthetic.main.view_row_girl_top_video.view.*

@LogTag("GirlTopVideoAdapter")
class GirlTopVideoAdapter : BaseAdapter() {

    private val listGirlTopVideo = ArrayList<GirlTopVideo>()
    var onClickRootView: ((GirlTopVideo) -> Unit?)? = null

    fun setListGirlTopUser(listGirlTopVideo: ArrayList<GirlTopVideo>) {
        this.listGirlTopVideo.clear()
        this.listGirlTopVideo.addAll(listGirlTopVideo)
        notifyDataSetChanged()
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind() {
            itemView.layoutHorizontal.removeAllViews()
            listGirlTopVideo.forEach { girlTopVideo ->
                val viewGirlTopVideo = ViewGirlTopVideo(itemView.context)
                val src = if (BuildConfig.DEBUG) {
                    Constants.URL_IMG
                } else {
                    girlTopVideo.cover
                }
                LImageUtil.load(context = viewGirlTopVideo.ivCover.context,
                        any = src,
                        imageView = viewGirlTopVideo.ivCover,
                        resError = R.color.black,
                        resPlaceHolder = R.color.black,
                        drawableRequestListener = null)
//                LUIUtil.setTextShadow(textView = viewGirlTopVideo.tvTitle, color = Color.BLACK)
                viewGirlTopVideo.tvTitle.text = girlTopVideo.title
                viewGirlTopVideo.roundRect.setSafeOnClickListener {
                    onClickRootView?.invoke(girlTopVideo)
                }
                itemView.layoutHorizontal.addView(viewGirlTopVideo)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
            ViewHolder(LayoutInflater.from(parent.context).inflate(
                    R.layout.view_row_girl_top_video, parent,
                    false
            ))

    override fun getItemCount(): Int = if (listGirlTopVideo.isEmpty()) 0 else 1

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ViewHolder) {
            holder.bind()
        }
    }

}
