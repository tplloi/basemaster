package vn.loitp.app.activity.customviews.recyclerview.fastscroll.adapter

import android.annotation.SuppressLint
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.view.updatePaddingRelative
import androidx.recyclerview.widget.RecyclerView
import vn.loitp.app.R
import vn.loitp.app.activity.customviews.recyclerview.fastscroll.db.ListItem

class SampleAdapter(
        private val listItem: List<ListItem>
) : RecyclerView.Adapter<SampleAdapter.ViewHolder>() {

    companion object {
        const val VIEWTYPE_HEADER = 0
        const val VIEWTYPE_DATA = 1
    }

    private val containsHeaders: Boolean = listItem.any { it is ListItem.HeaderItem }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        return ViewHolder(LayoutInflater.from(parent.context).inflate(
                when (viewType) {
                    VIEWTYPE_HEADER -> R.layout.layout_fast_scroll_header_item
                    VIEWTYPE_DATA -> R.layout.layout_fast_scroll_data_item
                    else -> throw IllegalArgumentException()
                },
                parent,
                false
        ))
    }

    override fun getItemCount() = listItem.count()

    override fun getItemViewType(position: Int): Int {
        return when (listItem[position]) {
            is ListItem.HeaderItem -> VIEWTYPE_HEADER
            is ListItem.DataItem -> VIEWTYPE_DATA
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(listItem[position])
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val titleView = itemView as TextView

        @SuppressLint("UseCompatTextViewDrawableApis")
        fun bind(listItem: ListItem) {
            when (listItem) {
                is ListItem.HeaderItem -> {
                    titleView.text = listItem.title
                    titleView.setCompoundDrawablesRelativeWithIntrinsicBounds(listItem.iconRes, 0, 0, 0)
                    val iconColor = titleView.textColors

                    if (Build.VERSION.SDK_INT >= 23) {
                        titleView.compoundDrawableTintList = iconColor
                    } else {
                        titleView.compoundDrawablesRelative
                                .filterNotNull()
                                .forEach {
                                    it.setTintList(iconColor)
                                }
                    }
                }
                is ListItem.DataItem -> {
                    titleView.text = listItem.title
                    if (containsHeaders) {
                        titleView.updatePaddingRelative(start = titleView.context.resources.getDimensionPixelSize(
                                R.dimen.list_with_headers_start_padding
                        ))
                    }
                }
            }
        }

    }

}
