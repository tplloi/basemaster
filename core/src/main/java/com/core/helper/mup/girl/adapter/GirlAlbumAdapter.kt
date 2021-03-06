package com.core.helper.mup.girl.adapter

import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.BuildConfig
import com.R
import com.annotation.LogTag
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.core.adapter.BaseAdapter
import com.core.common.Constants
import com.core.helper.mup.girl.model.GirlPage
import com.core.utilities.LDateUtil
import com.core.utilities.LImageUtil
import com.views.setSafeOnClickListener
import kotlinx.android.synthetic.main.view_row_girl_album.view.*

@LogTag("GirlAlbumAdapter")
class GirlAlbumAdapter : BaseAdapter() {

    private var listGirlPage = ArrayList<GirlPage>()
    var onClickRootListener: ((GirlPage, Int) -> Unit)? = null
    var onClickLikeListener: ((GirlPage, Int) -> Unit)? = null

    fun setData(listGirlPage: List<GirlPage>, isSwipeToRefresh: Boolean) {
        if (isSwipeToRefresh) {
            this.listGirlPage.clear()
        }
        this.listGirlPage.addAll(listGirlPage)
        notifyDataSetChanged()
    }

    fun getData(): ArrayList<GirlPage> {
        return listGirlPage
    }

    fun updateData(listGirlPage: List<GirlPage>) {
        this.listGirlPage.forEach {
            it.isFavorites = false
        }
        listGirlPage.forEach {
            updateData(it)
        }
        notifyDataSetChanged()
    }

    private fun updateData(girlPage: GirlPage) {
        val findGirlPage = this.listGirlPage.find {
            it.id == girlPage.id
        }
        findGirlPage?.let {
            it.isFavorites = girlPage.isFavorites
        }
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(girlPage: GirlPage) {
            itemView.tvTitle.text = girlPage.title
            itemView.tvCreatedDate.text = LDateUtil.convertFormatDate(strDate = girlPage.createdDate, fromFormat = "yyyy-MM-dd'T'HH:mm:ss", toFormat = "HH:mm:ss dd/MM/yyyy")
//            LUIUtil.setTextShadow(textView = itemView.tvCreatedDate, color = Color.BLACK)
//            LUIUtil.setTextShadow(textView = itemView.tvTitle, color = Color.BLACK)
            val src = if (BuildConfig.DEBUG) {
                Constants.URL_IMG
            } else {
                girlPage.src
            }
            LImageUtil.load(context = itemView.imageView.context,
                    any = src,
                    imageView = itemView.imageView,
                    resPlaceHolder = R.color.black,
                    resError = R.color.black,
                    drawableRequestListener = object : RequestListener<Drawable> {
                        override fun onLoadFailed(e: GlideException?, model: Any, target: Target<Drawable?>, isFirstResource: Boolean): Boolean {
                            return false
                        }

                        override fun onResourceReady(resource: Drawable?, model: Any, target: Target<Drawable?>, dataSource: DataSource, isFirstResource: Boolean): Boolean {
                            return false
                        }
                    })
            itemView.imageView.setAspectRatio(16f / 9f)
            itemView.roundRect.setSafeOnClickListener {
                onClickRootListener?.invoke(girlPage, bindingAdapterPosition)
            }
            itemView.btLike.isChecked = girlPage.isFavorites
            itemView.btLike.setSafeOnClickListener {
                onClickLikeListener?.invoke(girlPage, bindingAdapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
            ViewHolder(LayoutInflater.from(parent.context).inflate(
                    R.layout.view_row_girl_album, parent,
                    false
            ))

    override fun getItemCount(): Int = listGirlPage.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ViewHolder) {
            holder.bind(girlPage = listGirlPage[position])
        }
    }

}
