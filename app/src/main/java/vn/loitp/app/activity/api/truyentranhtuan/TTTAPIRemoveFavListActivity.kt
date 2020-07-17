package vn.loitp.app.activity.api.truyentranhtuan

import android.os.Bundle
import com.core.base.BaseFontActivity
import com.core.utilities.LUIUtil
import kotlinx.android.synthetic.main.activity_api_ttt_remove_fav_list.*
import vn.loitp.app.R
import vn.loitp.app.activity.api.truyentranhtuan.helper.favlist.RemoveComicFavListTask
import vn.loitp.app.activity.api.truyentranhtuan.model.comic.Comic

class TTTAPIRemoveFavListActivity : BaseFontActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        indicatorView.hide()
        btAddVuongPhongLoi.setOnClickListener {
            val comic = Comic()
            comic.date = "29.07.2014"
            comic.url = "http://truyentranhtuan.com/vuong-phong-loi-i/"
            comic.title = "Vương Phong Lôi I"
            removeComic(comic)
        }
        btAddLayers.setOnClickListener {
            val comic = Comic()
            comic.date = "28.06.2015"
            comic.url = "http://truyentranhtuan.com/layers/"
            comic.title = "Layers"
            removeComic(comic)
        }
        btAddBlackHaze.setOnClickListener {
            val comic = Comic()
            comic.date = "12.03.2017"
            comic.url = "http://truyentranhtuan.com/black-haze/"
            comic.title = "Black Haze"
            removeComic(comic)
        }
    }

    override fun setFullScreen(): Boolean {
        return false
    }

    override fun setTag(): String? {
        return javaClass.simpleName
    }

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_api_ttt_remove_fav_list
    }

    private fun removeComic(comic: Comic) {
        indicatorView.smoothToShow()
        RemoveComicFavListTask(activity, comic, object : RemoveComicFavListTask.Callback {
            override fun onRemoveComicSuccess(mComic: Comic, comicList: List<Comic>) {
                showShort("onRemoveComicSuccess")
                LUIUtil.printBeautyJson(o = comicList, textView = textView)
                indicatorView.smoothToHide()
            }

            override fun onComicIsNotExist(mComic: Comic, comicList: List<Comic>) {
                showShort("onComicIsNotExist")
                LUIUtil.printBeautyJson(o = comicList, textView = textView)
                indicatorView.smoothToHide()
            }

            override fun onRemoveComicError() {
                showShort("onRemoveComicError")
                indicatorView.smoothToHide()
            }
        }).execute()
    }
}