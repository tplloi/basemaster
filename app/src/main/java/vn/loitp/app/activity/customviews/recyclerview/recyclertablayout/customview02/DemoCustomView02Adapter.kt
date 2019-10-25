package vn.loitp.app.activity.customviews.recyclerview.recyclertablayout.customview02

import android.content.Context
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import com.views.recyclerview.recyclertablayout.RecyclerTabLayout
import loitp.basemaster.R
import vn.loitp.app.activity.customviews.recyclerview.recyclertablayout.DemoImagePagerAdapter

/**
 * Created by Shinichi Nishimura on 2015/07/22.
 */
class DemoCustomView02Adapter internal constructor(viewPager: ViewPager) : RecyclerTabLayout.Adapter<DemoCustomView02Adapter.ViewHolder>(viewPager) {

    private val mAdapater: DemoImagePagerAdapter = mViewPager.adapter as DemoImagePagerAdapter

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.layout_custom_view02_tab, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val drawable = loadIconWithTint(holder.imageView.context, mAdapater.getImageResourceId(position))

        holder.imageView.setImageDrawable(drawable)
        holder.imageView.isSelected = position == currentIndicatorPosition
    }

    private fun loadIconWithTint(context: Context, @DrawableRes resourceId: Int): Drawable {
        var icon = ContextCompat.getDrawable(context, resourceId)
        val colorStateList = ContextCompat
                .getColorStateList(context, R.color.custom_view02_tint)
        icon = DrawableCompat.wrap(icon!!)
        DrawableCompat.setTintList(icon!!, colorStateList)
        return icon
    }

    override fun getItemCount(): Int {
        return mAdapater.count
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var imageView: ImageView = itemView.findViewById(R.id.image)

        init {
            itemView.setOnClickListener { v -> viewPager.currentItem = adapterPosition }
        }
    }
}
