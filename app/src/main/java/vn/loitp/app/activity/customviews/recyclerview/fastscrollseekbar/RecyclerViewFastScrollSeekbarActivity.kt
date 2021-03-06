package vn.loitp.app.activity.customviews.recyclerview.fastscrollseekbar

import abak.tr.com.boxedverticalseekbar.BoxedVertical
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.annotation.IsFullScreen
import com.annotation.LogTag
import com.core.base.BaseFontActivity
import com.core.utilities.LUIUtil
import kotlinx.android.synthetic.main.activity_recycler_view_fast_scroll_seek_bar.*
import vn.loitp.app.R
import vn.loitp.app.activity.customviews.recyclerview.normalrecyclerview.Movie
import vn.loitp.app.activity.customviews.recyclerview.normalrecyclerview.MoviesAdapter
import vn.loitp.app.common.Constants
import java.util.*


@LogTag("loitppRecyclerViewFastScrollSeekbarActivity")
@IsFullScreen(false)
class RecyclerViewFastScrollSeekbarActivity : BaseFontActivity() {

    private val movieList: MutableList<Movie> = ArrayList()
    private var moviesAdapter: MoviesAdapter? = null
    private var tmpPositionSeekBar = 0
    private var tmpPositionRecyclerView = 0
    private var isOnTracking = false

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_recycler_view_fast_scroll_seek_bar
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupViews()
    }

    private fun setupViews() {
        moviesAdapter = MoviesAdapter(moviesList = movieList,
            callback = object : MoviesAdapter.Callback {
                override fun onClick(movie: Movie, position: Int) {
                    showShortInformation("Click " + movie.title)
                }

                override fun onLongClick(movie: Movie, position: Int) {
                }

                override fun onLoadMore() {
                    loadMore()
                }
            })
        val layoutManager = LinearLayoutManager(this)
        rv.layoutManager = layoutManager
        rv.addOnScrollListener(object : RecyclerView.OnScrollListener() {

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                if (!isOnTracking) {
                    val lastElementPosition = layoutManager.findLastVisibleItemPosition()
                    if (tmpPositionRecyclerView != lastElementPosition) {
                        logD("addOnScrollListener lastElementPosition $lastElementPosition")
                        boxedVertical.value = movieList.size - lastElementPosition
                        tmpPositionRecyclerView = lastElementPosition
                    }
                }
            }
        })
        moviesAdapter?.let {
            rv.adapter = it
        }

        boxedVertical.setOnBoxedPointsChangeListener(object : BoxedVertical.OnValuesChangeListener {
            override fun onPointsChanged(boxedPoints: BoxedVertical, value: Int) {
                if (tmpPositionSeekBar != value) {
                    if (isOnTracking) {
                        logD("onPointsChanged $value")
                        rv.scrollToPosition(movieList.size - value)
                        tmpPositionSeekBar = value
                    }
                }
            }

            override fun onStartTrackingTouch(boxedPoints: BoxedVertical) {
                logD("onStartTrackingTouch")
                isOnTracking = true
            }

            override fun onStopTrackingTouch(boxedPoints: BoxedVertical) {
                logD("onStopTrackingTouch")
                isOnTracking = false
            }
        })

        prepareMovieData()
    }

    private fun loadMore() {
        LUIUtil.setDelay(mls = 2000, runnable = {
            val newSize = 5
            for (i in 0 until newSize) {
                val movie = Movie(
                    title = "Add new $i",
                    genre = "Add new $i",
                    year = "Add new: $i",
                    cover = Constants.URL_IMG
                )
                movieList.add(movie)
            }
            moviesAdapter?.notifyDataSetChanged()
            bindSeekbarMax()
            showShortInformation("Finish loadMore")
        })
    }

    private fun prepareMovieData() {
        for (i in 0..49) {
            val movie = Movie(
                title = "Loitp $i",
                genre = "Action & Adventure",
                year = "Year: 2021",
                cover = Constants.URL_IMG
            )
            movieList.add(movie)
        }
        moviesAdapter?.notifyDataSetChanged()
        bindSeekbarMax()
    }

    private fun bindSeekbarMax() {
        boxedVertical.max = movieList.size
    }
}
