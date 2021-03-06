package com.core.utilities

import android.app.Activity
import android.content.ContentUris
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import com.R
import com.core.common.Constants
import com.core.helper.fbcomment.FbCommentActivity
import com.utils.util.AppUtils

/**
 * File created on 11/14/2016.
 *
 * @author loitp
 */
class LSocialUtil {
    companion object {
        private val logTag = LSocialUtil::class.java.simpleName

        fun rateApp(
                activity: Activity,
                packageName: String = AppUtils.appPackageName
        ) {
//            LLog.d(logTag, ">>>rateApp packageName $packageName")
            try {
                activity.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=$packageName")))
                LActivityUtil.tranIn(activity)
            } catch (e: android.content.ActivityNotFoundException) {
                e.printStackTrace()
                activity.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("http://play.google.com/store/apps/details?id=$packageName")))
                LActivityUtil.tranIn(activity)
            }
        }

        //NgonTinh KangKang
        fun moreApp(
                activity: Activity,
                nameOfDeveloper: String = "Toi Yeu Viet Nam"
        ) {
            val uri = "https://play.google.com/store/apps/developer?id=$nameOfDeveloper"
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(uri))
            activity.startActivity(intent)
            LActivityUtil.tranIn(activity)
        }

        fun shareApp(
                activity: Activity
        ) {
            try {
                val intent = Intent(Intent.ACTION_SEND)
                intent.type = "text/plain"
                intent.putExtra(Intent.EXTRA_SUBJECT, activity.getString(R.string.app_name))
                var sAux = "\nỨng dụng này rất bổ ích, thân mời bạn tải về cài đặt để trải nghiệm\n\n"
                sAux = sAux + "https://play.google.com/store/apps/details?id=" + activity.packageName
                intent.putExtra(Intent.EXTRA_TEXT, sAux)
                activity.startActivity(Intent.createChooser(intent, "Vui lòng chọn"))
                LActivityUtil.tranIn(activity)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        fun share(
                activity: Activity,
                msg: String
        ) {
            try {
                val intent = Intent(Intent.ACTION_SEND)
                intent.type = "text/plain"
                intent.putExtra(Intent.EXTRA_SUBJECT, activity.getString(R.string.app_name))
                //String sAux = "\nỨng dụng này rất bổ ích, thân mời bạn tải về cài đặt để trải nghiệm\n\n";
                //sAux = sAux + "https://play.google.com/store/apps/details?id=" + activity.getPackageName();
                intent.putExtra(Intent.EXTRA_TEXT, msg)
                activity.startActivity(Intent.createChooser(intent, "Share via"))
                LActivityUtil.tranIn(activity)
            } catch (e: Exception) {
                e.printStackTrace()
            }

        }

        //like fanpage
        fun likeFacebookFanpage(
                activity: Activity?
        ) {
            activity?.let {
                val facebookIntent = Intent(Intent.ACTION_VIEW)
                val facebookUrl = getFacebookPageURL()
                facebookIntent.data = Uri.parse(facebookUrl)
                it.startActivity(facebookIntent)
                LActivityUtil.tranIn(it)
            }
        }

        /*
        get url fb fanpage
         */
        private fun getFacebookPageURL(): String {
            val facebookUrl = "https://www.facebook.com/hoidammedocsach"
            val facebookPageId = "hoidammedocsach"
            val packageManager = LAppResource.application.packageManager
            return try {
                val versionCode = packageManager.getPackageInfo("com.facebook.katana", 0).versionCode
                if (versionCode >= 3002850) {
                    "fb://facewebmodal/f?href=$facebookUrl"
                } else {
                    "fb://page/$facebookPageId"
                }
            } catch (e: PackageManager.NameNotFoundException) {
                e.printStackTrace()
                facebookUrl
            }

        }

        /*
        chat with fanpage Thugiannao
         */
        fun chatMessenger(
                activity: Activity
        ) {
            val packageManager = activity.packageManager
            var isFBInstalled = false
            try {
                val versionCode = packageManager.getPackageInfo("com.facebook.orca", 0).versionCode
                if (versionCode >= 0) isFBInstalled = true
            } catch (e: PackageManager.NameNotFoundException) {
                e.printStackTrace()
            }

            if (!isFBInstalled) {
                LDialogUtil.showDialog1(
                        context = activity,
                        title = activity.getString(R.string.err),
                        msg = activity.getString(R.string.cannot_find_messenger_app),
                        button1 = activity.getString(R.string.ok)
                )
            } else {
                var uri = Uri.parse("fb-messenger://user/")
                uri = ContentUris.withAppendedId(uri, java.lang.Long.valueOf("947139732073591"))
                val intent = Intent(Intent.ACTION_VIEW, uri)
                try {
                    activity.startActivity(intent)
                    LActivityUtil.tranIn(activity)
                } catch (e: Exception) {
                    e.printStackTrace()
                    LDialogUtil.showDialog1(
                            context = activity,
                            title = activity.getString(R.string.err),
                            msg = activity.getString(R.string.cannot_find_messenger_app),
                            button1 = activity.getString(R.string.ok)
                    )
                }
            }
        }

        /*
         * send email support
         */
        fun sendEmail(
                context: Context?
        ) {
            val emailIntent = Intent(Intent.ACTION_SENDTO)
            emailIntent.data = Uri.parse("mailto: www.muathu@gmail.com")
            context?.startActivity(Intent.createChooser(emailIntent, "Send feedback"))
        }

        fun openBrowserPolicy(
                context: Context
        ) {
            openUrlInBrowser(context = context, url = Constants.URL_POLICY)
        }

        fun openBrowserGirl(
                context: Context
        ) {
            openUrlInBrowser(context = context, url = Constants.URL_GIRL)
        }

        fun openUrlInBrowser(
                context: Context?,
                url: String?
        ) {
            if (context == null || url.isNullOrEmpty()) {
                return
            }
            val webPage = Uri.parse(url)
            val intent = Intent(Intent.ACTION_VIEW, webPage)
            if (intent.resolveActivity(context.packageManager) != null) {
                context.startActivity(intent)
                LActivityUtil.tranIn(context)
            }
        }

//        fun openFacebookComment(
//                context: Context?,
//                url: String?
//        ) {
//            if (context == null || url.isNullOrEmpty()) {
//                return
//            }
//            val intent = Intent(context, FbCommentActivity::class.java)
//            intent.putExtra(Constants.FACEBOOK_COMMENT_URL, url)
//            context.startActivity(intent)
//            LActivityUtil.tranIn(context)
//        }

        fun openFacebookComment(
                context: Context? = null,
                url: String? = null,
                adUnitId: String? = null
        ) {
            if (context == null || url.isNullOrEmpty()) {
                return
            }
            val intent = Intent(context, FbCommentActivity::class.java)
            intent.putExtra(Constants.FACEBOOK_COMMENT_URL, url)
            if (adUnitId.isNullOrEmpty()) {
                LLog.d(logTag, "openFacebookComment adUnitId isNullOrEmpty")
            } else {
                intent.putExtra(Constants.AD_UNIT_ID_BANNER, adUnitId)
            }
            context.startActivity(intent)
            LActivityUtil.tranIn(context)
        }
    }
}
