package vn.loitp.app.activity.customviews.recyclerview.footer2

import android.os.Bundle
import android.view.MenuItem
import android.view.animation.OvershootInterpolator
import androidx.recyclerview.widget.*
import com.annotation.IsFullScreen
import com.annotation.LogTag
import com.core.base.BaseFontActivity
import com.core.utilities.LPopupMenu
import com.core.utilities.LUIUtil
import com.views.recyclerview.itemdecoration.StickyFooterItemDecoration
import com.views.setSafeOnClickListener
import jp.wasabeef.recyclerview.adapters.*
import kotlinx.android.synthetic.main.activity_recycler_view_footer_2.*
import vn.loitp.app.R
import vn.loitp.app.activity.customviews.recyclerview.normalrecyclerview.Movie
import vn.loitp.app.activity.customviews.recyclerview.normalrecyclerviewwithsingletondata.DummyData.Companion.instance
import vn.loitp.app.common.Constants

@LogTag("RecyclerViewFooter2Activity")
@IsFullScreen(false)
class RecyclerViewFooter2Activity : BaseFontActivity() {

    private var footer2Adapter: Footer2Adapter? = null

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_recycler_view_footer_2
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        footer2Adapter = Footer2Adapter(moviesList = instance.movieList,
                callback = object : Footer2Adapter.Callback {
                    override fun onClick(movie: Movie, position: Int) {
                        showShortInformation("Click " + movie.title)
                    }

                    override fun onLongClick(movie: Movie, position: Int) {
                        val isRemoved = instance.movieList.remove(movie)
                        if (isRemoved) {
                            footer2Adapter?.let {
                                it.notifyItemRemoved(position)
                                it.notifyItemRangeChanged(position, instance.movieList.size)
                            }
                        }
                    }

                    override fun onLoadMore() {
                    }
                })
        val mLayoutManager: RecyclerView.LayoutManager = LinearLayoutManager(this)
        rv.layoutManager = mLayoutManager
//        rv.adapter = footer2Adapter
        footer2Adapter?.let {
            val animAdapter = AlphaInAnimationAdapter(it)
//            val animAdapter = ScaleInAnimationAdapter(it)
//            val animAdapter = SlideInBottomAnimationAdapter(it)
//            val animAdapter = SlideInLeftAnimationAdapter(it)
//            val animAdapter = SlideInRightAnimationAdapter(it)

            animAdapter.setDuration(1000)
            animAdapter.setInterpolator(OvershootInterpolator())
            animAdapter.setFirstOnly(true)
            rv.adapter = animAdapter
        }

        rv.addItemDecoration(StickyFooterItemDecoration())

        prepareMovieData()

        btSetting.setSafeOnClickListener {
            LPopupMenu.show(
                    activity = this,
                    showOnView = it,
                    menuRes = R.menu.menu_recycler_view,
                    callback = { menuItem ->
                        tvType.text = menuItem.title.toString()
                        when (menuItem.itemId) {
                            R.id.menuLinearVertical -> {
                                val lmVertical: RecyclerView.LayoutManager = LinearLayoutManager(this@RecyclerViewFooter2Activity)
                                rv.layoutManager = lmVertical
                            }
                            R.id.menuLinearHorizontal -> {
                                val lmHorizontal: RecyclerView.LayoutManager = LinearLayoutManager(this@RecyclerViewFooter2Activity, LinearLayoutManager.HORIZONTAL, false)
                                rv.layoutManager = lmHorizontal
                            }
                            R.id.menuGridLayoutManager2 -> rv.layoutManager = GridLayoutManager(this@RecyclerViewFooter2Activity, 2)
                            R.id.menuGridLayoutManager3 -> rv.layoutManager = GridLayoutManager(this@RecyclerViewFooter2Activity, 3)
                            R.id.menuStaggeredGridLayoutManager2 -> rv.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
                            R.id.menuStaggeredGridLayoutManager4 -> rv.layoutManager = StaggeredGridLayoutManager(4, StaggeredGridLayoutManager.HORIZONTAL)
                        }
                    }
            )
        }

        btAddMore.setSafeOnClickListener {
            loadMore()
        }
    }

    private fun loadMore() {
        indicatorView.smoothToShow()
        LUIUtil.setDelay(mls = 2000, runnable = Runnable {
            val newSize = 5
            for (i in 0 until newSize) {
                val movie = Movie(title = "Add new $i", genre = "Add new $i", year = "Add new: $i", cover = Constants.URL_IMG)
                instance.movieList.add(movie)
            }
            indicatorView?.smoothToHide()
            footer2Adapter?.notifyDataSetChanged()
            showShortInformation("Finish loadMore")
        })
    }

    private fun prepareMovieData() {
        if (instance.movieList.isEmpty()) {
            for (i in 0..3) {
                val movie = Movie(title = "Loitp $i", genre = "Action & Adventure $i", year = "Year: $i", cover = Constants.URL_IMG)
                instance.movieList.add(movie)
            }
        }
        footer2Adapter?.notifyDataSetChanged()
        indicatorView.smoothToHide()
    }
}
