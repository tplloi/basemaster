package vn.loitp.app.activity.api.truyentranhtuan.helper.comiclist

import android.app.Activity
import android.os.AsyncTask
import com.core.utilities.LStoreUtil
import com.wang.avi.AVLoadingIndicatorView
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import vn.loitp.app.activity.api.truyentranhtuan.model.comic.Comic
import vn.loitp.app.activity.api.truyentranhtuan.model.comic.Comics
import vn.loitp.app.activity.api.truyentranhtuan.model.comic.TTTComic
import vn.loitp.app.app.LApplication.Companion.gson
import java.io.File
import java.util.*

//TODO convert croutine
class GetComicTask(private val activity: Activity,
                   private val link: String,
                   private val avLoadingIndicatorView: AVLoadingIndicatorView,
                   private val callback: Callback?)
    : AsyncTask<Void, Void, Boolean>() {

    private val TAG = javaClass.simpleName
    private var numberOfParseDataTryAgain = 0
    private var numberOfDoTaskTryAgain = 0
    private val MAX_TRY_AGAIN = 4
    private var getComicSuccess = false
    private var comicList: List<Comic> = ArrayList()

    interface Callback {
        fun onSuccess(comicList: List<Comic>)
        fun onError()
    }

    override fun onPreExecute() {
        avLoadingIndicatorView.smoothToShow()
        super.onPreExecute()
    }

    override fun doInBackground(vararg voids: Void): Boolean {
        comicList = doTask(link)
        if (comicList.isEmpty()) {
            getComicSuccess = false
        } else {
            //restore comic list with img cover url
            var jsonTTTComic: String = LStoreUtil.readTxtFromFolder(activity = activity, folderName = LStoreUtil.FOLDER_TRUYENTRANHTUAN, fileName = LStoreUtil.FILE_NAME_MAIN_COMICS_LIST)
            if (jsonTTTComic.isEmpty()) {
                val tttComic = TTTComic()
                val comics = Comics()
                comics.comic = comicList
                tttComic.comics = comics
                jsonTTTComic = gson.toJson(tttComic)
                LStoreUtil.writeToFile(context = activity, folder = LStoreUtil.FOLDER_TRUYENTRANHTUAN, fileName = LStoreUtil.FILE_NAME_MAIN_COMICS_LIST, body = jsonTTTComic)
                getComicSuccess = true
            } else {
                if (jsonTTTComic.isNotEmpty()) {
                    val tttComic = gson.fromJson(jsonTTTComic, TTTComic::class.java)
                    try {
                        val oldComicList = tttComic.comics?.comic ?: emptyList()
                        if (!oldComicList.isEmpty()) {
                            //restore url img cover
                            //lay tat ca nhung comic da co san img cover url trong oldComicList
                            val savedInfoComicList: MutableList<Comic> = ArrayList()
                            for (comic in oldComicList) {
                                savedInfoComicList.add(comic)
                            }

                            //gan du lieu url img cover cho comic list moi vua tai ve
                            for (i in savedInfoComicList.indices) {
                                for (j in comicList.indices) {
                                    if (savedInfoComicList[i].url == comicList[j].url) {
                                        comicList[j].urlImg = savedInfoComicList[i].urlImg
                                        comicList[j].type = savedInfoComicList[i].type
                                    }
                                }
                            }
                            //end restore url img cover
                        }
                        getComicSuccess = true
                    } catch (e: NullPointerException) {
                        e.printStackTrace()
                        getComicSuccess = false
                    }
                } else {
                    val tttComic = TTTComic()
                    val comics = Comics()
                    comics.comic = comicList
                    tttComic.comics = comics
                    jsonTTTComic = gson.toJson(tttComic)
                    LStoreUtil.writeToFile(context = activity, folder = LStoreUtil.FOLDER_TRUYENTRANHTUAN, fileName = LStoreUtil.FILE_NAME_MAIN_COMICS_LIST, body = jsonTTTComic)
                    getComicSuccess = true
                }
            }
        }

        return false
    }

    override fun onPostExecute(aVoid: Boolean) {
        if (getComicSuccess) {
            callback?.onSuccess(comicList)
        } else {
            callback?.onError()
        }
        avLoadingIndicatorView.smoothToHide()
        super.onPostExecute(aVoid)
    }

    private fun doTask(link: String): List<Comic> {
        var comicList: List<Comic> = ArrayList()
        //luu tru danh sach truyen duoi dang html code
        val state = LStoreUtil.saveHTMLCodeFromURLToSDCard(context = activity, link = link, folderName = LStoreUtil.FOLDER_TRUYENTRANHTUAN, fileName = LStoreUtil.FILE_NAME_MAIN_COMICS_LIST_HTML_CODE)
        //state = true -> luu tru thanh cong
        //state = false -> luu tru that bai
        if (state) {
            comicList = parseData()
        } else {
            numberOfDoTaskTryAgain++
            if (numberOfDoTaskTryAgain > MAX_TRY_AGAIN) {
                return comicList //return list empty
            }
            doTask(link)
        }
        return comicList
    }

    private fun parseData(): List<Comic> {
        val comicList: MutableList<Comic> = ArrayList()
        val document: Document
        try {
            /*parse jsoup from file in asset folder*/
            //is = getAssets().open("comic.txt");
            //document = Jsoup.parse(is, "UTF-8", link);

            /*parse jsoup from url*/
            //document = Jsoup.connect(link).get();

            /*parse jsoup from sdcard*/
            val pathMainComicsListHTMLCode = LStoreUtil.getPathOfFileNameMainComicsListHTMLCode(activity)
            val input = File(pathMainComicsListHTMLCode)
            document = Jsoup.parse(input, "UTF-8", "http://example.com/")
            for (eMangaFocus in document.select("div[class=manga-focus]")) {
                val comic = Comic()
                val eTitle = eMangaFocus.select("span[class=manga]")
                //LLog.d(TAG, "tieu de: " + eTitle.text());//Tieu de: +Anima
                comic.title = eTitle.text()
                val urlComic = eTitle.select("a")
                //LLog.d(TAG, "tmpLink: " + urlComic.attr("href"));//url:
                // http://truyentranhtuan.com/anima/
                comic.url = urlComic.attr("href")
                val eDate = eMangaFocus.select("span[class=current-date]")
                //LLog.d(TAG, "eDate: " + eDate.text());//04.10.2016
                comic.date = eDate.text()
                comicList.add(comic)
            }
        } catch (e: Exception) {
            e.printStackTrace()
            numberOfParseDataTryAgain++
            if (numberOfParseDataTryAgain > MAX_TRY_AGAIN) {
                return comicList //return list empty
            }
            parseData()
        }
        return comicList
    }

}
