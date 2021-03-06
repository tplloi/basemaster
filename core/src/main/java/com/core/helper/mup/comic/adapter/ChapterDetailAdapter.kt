package com.core.helper.mup.comic.adapter

import android.graphics.Color
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.R
import com.annotation.LogTag
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.core.adapter.BaseAdapter
import com.core.helper.mup.comic.model.ChapterComicsDetail
import com.core.helper.mup.comic.model.ChapterDetail
import com.core.utilities.LImageUtil
import com.core.utilities.LUIUtil
import com.views.setSafeOnClickListener
import kotlinx.android.synthetic.main.view_row_comic_chapter_detail.view.*

@LogTag("ChapterDetailAdapter")
class ChapterDetailAdapter : BaseAdapter() {

    private var chapterDetail: ChapterDetail? = null
    private val listData = ArrayList<ChapterComicsDetail>()
//    private val placeHolder = if (LUIUtil.isDarkTheme()) {
//        R.drawable.place_holder_white
//    } else {
//        R.drawable.place_holder_black
//    }

    fun setData(chapterDetail: ChapterDetail) {
        this.chapterDetail = chapterDetail

        this.listData.clear()
        this.chapterDetail?.chapterComicsDetails?.let {
            this.listData.addAll(it)
        }

        notifyDataSetChanged()
    }

    fun getChapterDetail(): ChapterDetail? {
        return chapterDetail
    }

    var onClickRoot: ((ChapterComicsDetail) -> Unit)? = null

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(chapterComicsDetail: ChapterComicsDetail) {
            val imgSrc = chapterComicsDetail.imageSrc
//            if (Constants.IS_DEBUG) {
//                imgSrc = "http://truyentranhtuan.com/manga2/detective-conan/1055/img-00001.jpg"
//            }
            logD("$bindingAdapterPosition -> imgSrc $imgSrc, ${chapterComicsDetail.noOrder}")
            itemView.indicatorView.smoothToShow()
            itemView.tvPage.text = "${chapterComicsDetail.noOrder}"
//            LImageUtil.setImageViewZoom(iv = itemView.ivChapterDetail)

            LImageUtil.loadHighQuality(
                    any = imgSrc,
                    imageView = itemView.ivChapterDetail,
//                    resPlaceHolder = placeHolder,
                    resError = R.drawable.place_holder_error404,
                    drawableRequestListener = object : RequestListener<Drawable> {
                        override fun onLoadFailed(e: GlideException?, model: Any, target: Target<Drawable?>, isFirstResource: Boolean): Boolean {
                            itemView.indicatorView?.smoothToHide()
                            return false
                        }

                        override fun onResourceReady(resource: Drawable?, model: Any, target: Target<Drawable?>, dataSource: DataSource, isFirstResource: Boolean): Boolean {
                            itemView.indicatorView?.smoothToHide()
//                            LImageUtil.setZoomFitWidthScreen(touchImageView = itemView.ivChapterDetail)
                            return false
                        }
                    }
            )

            LUIUtil.setTextShadow(textView = itemView.tvPage, color = Color.BLACK)
            itemView.ivChapterDetail.setSafeOnClickListener {
                onClickRoot?.invoke(chapterComicsDetail)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
            ViewHolder(LayoutInflater.from(parent.context).inflate(
                    R.layout.view_row_comic_chapter_detail, parent,
                    false
            ))

    override fun getItemCount(): Int = listData.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ViewHolder) {
            holder.bind(chapterComicsDetail = listData[position])
        }
    }

}
