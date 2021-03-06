package vn.loitp.app.activity.customviews.edittext.autosuggest

import android.graphics.Color
import android.os.Bundle
import android.view.inputmethod.EditorInfo
import com.annotation.IsFullScreen
import com.annotation.LogTag
import com.core.base.BaseFontActivity
import com.core.utilities.LScreenUtil
import com.labo.kaji.relativepopupwindow.RelativePopupWindow
import com.views.edittext.autosuggest.LAutoSuggestEditText
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_editext_auto_suggest.*
import vn.loitp.app.R

@LogTag("EditTextAutoSuggestActivity")
@IsFullScreen(false)
class EditTextAutoSuggestActivity : BaseFontActivity() {
    private var disposableSearch: Disposable? = null

    override fun setLayoutResourceId(): Int {
        return R.layout.activity_editext_auto_suggest
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupViews()
    }

    private fun setupViews() {
        aet0.apply {
            this.popupHeight = LScreenUtil.screenHeight / 2
            this.vertPos = RelativePopupWindow.VerticalPosition.BELOW
            this.horizPos = RelativePopupWindow.HorizontalPosition.CENTER
            this.setHintText("1/2 screen")
            this.setHinTextColor(Color.BLUE)
            this.editText.setTextColor(Color.YELLOW)
            this.setColorProgressBar(Color.RED)
            this.setBackgroundResource(R.drawable.bkg_et)
            this.setImeiAction(EditorInfo.IME_ACTION_SEARCH, Runnable {
                showShortInformation("Text ${aet0.editText.text}")
            })
            this.callback = object : LAutoSuggestEditText.Callback {
                override fun onTextChanged(text: String) {
                    fakeCallAPI0(text)
                }
            }
        }

        aet1.apply {
            this.popupWidth = LScreenUtil.screenWidth * 1 / 2
            this.popupHeight = LScreenUtil.screenHeight * 1 / 4
            this.vertPos = RelativePopupWindow.VerticalPosition.ALIGN_BOTTOM
            this.horizPos = RelativePopupWindow.HorizontalPosition.RIGHT
            this.setHintText("3/4 screen")
            this.setHinTextColor(Color.RED)
            this.editText.setTextColor(Color.BLUE)
            this.setColorProgressBar(Color.GREEN)
            this.setBackgroundResource(R.drawable.l_bkg_horizontal)
            this.setImeiAction(EditorInfo.IME_ACTION_DONE) {
                showShortInformation("Text ${aet1.editText.text}")
            }
            this.callback = object : LAutoSuggestEditText.Callback {
                override fun onTextChanged(text: String) {
                    fakeCallAPI1(text)
                }
            }
        }
    }

    private fun fakeCallAPI0(text: String) {
        disposableSearch?.dispose()
        if (text.isEmpty()) {
            return
        }
        disposableSearch = Single.create<ArrayList<String>> {
            try {
                Thread.sleep(2000)
            } catch (e: InterruptedException) {
            }
            val stringList = ArrayList<String>()
            for (i in 0..10) {
                stringList.add("Result $text $i")
            }
            it.onSuccess(stringList)
        }.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnDispose {
                    logD("doOnDispose")
                }
                .subscribe(
                        {
                            aet0.setResultList(it)
                        },
                        {
                            aet0.clearResultList()
                        }
                )
    }

    private fun fakeCallAPI1(text: String) {
        disposableSearch?.dispose()
        if (text.isEmpty()) {
            return
        }
        disposableSearch = Single.create<ArrayList<String>> {
            try {
                Thread.sleep(2000)
            } catch (e: InterruptedException) {
            }
            val stringList = ArrayList<String>()
            for (i in 0..50) {
                stringList.add("Result $text $i")
            }
            it.onSuccess(stringList)
        }.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnDispose {
                    logD("doOnDispose")
                }
                .subscribe(
                        {
                            aet1.setResultList(it)
                        },
                        {
                            aet1.clearResultList()
                        }
                )
    }
}
