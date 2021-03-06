package com.core.helper.gallery

import android.Manifest
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.view.View
import com.R
import com.annotation.IsFullScreen
import com.annotation.LogTag
import com.core.base.BaseFontActivity
import com.core.common.Constants
import com.core.helper.gallery.album.GalleryCoreAlbumActivity
import com.core.utilities.*
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import com.restapi.restclient.RestClient
import com.utils.util.AppUtils
import kotlinx.android.synthetic.main.l_activity_flickr_gallery_core_splash.*
import java.util.*

@LogTag("GalleryCoreSplashActivity")
@IsFullScreen(false)
class GalleryCoreSplashActivity : BaseFontActivity() {
    private var adView: AdView? = null
    private var adMobBannerUnitId: String? = null
    private var isShowDialogCheck: Boolean = false

    override fun setLayoutResourceId(): Int {
        return R.layout.l_activity_flickr_gallery_core_splash
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//        setTransparentStatusNavigationBar()
        RestClient.init(getString(R.string.flickr_URL))
        adMobBannerUnitId = intent.getStringExtra(Constants.AD_UNIT_ID_BANNER)
//        logD("admobBannerUnitId $admobBannerUnitId")

        if (adMobBannerUnitId.isNullOrEmpty()) {
            lnAdView.visibility = View.GONE
        } else {
            adView = AdView(this)
            adView?.let {
                it.adSize = AdSize.SMART_BANNER
                it.adUnitId = adMobBannerUnitId
                LUIUtil.createAdBanner(it)
                lnAdView.addView(it)
//                val navigationHeight = DisplayUtil.getNavigationBarHeight(activity)
//                LUIUtil.setMargins(view = it, leftPx = 0, topPx = 0, rightPx = 0, bottomPx = navigationHeight + navigationHeight / 4)
            }
        }

        var urlCoverSplashScreen: String? = intent.getStringExtra(Constants.BKG_SPLASH_SCREEN)
        if (urlCoverSplashScreen.isNullOrEmpty()) {
            urlCoverSplashScreen = Constants.URL_IMG_2
        }
        LImageUtil.load(context = this, any = urlCoverSplashScreen, imageView = ivBkg)
        LUIUtil.setTextShadow(textView = tvCopyright, color = null)
        tvName.text = AppUtils.appName
        LUIUtil.setTextShadow(textView = tvName, color = null)

        LValidateUtil.isValidPackageName()
    }

    private fun goToHome() {
        val removeAlbumList = intent.getStringArrayListExtra(Constants.KEY_REMOVE_ALBUM_FLICKR_LIST)
        LUIUtil.setDelay(mls = 2000, runnable = {
            val intent = Intent(this, GalleryCoreAlbumActivity::class.java)
            intent.putExtra(Constants.AD_UNIT_ID_BANNER, adMobBannerUnitId)
            intent.putStringArrayListExtra(Constants.KEY_REMOVE_ALBUM_FLICKR_LIST, removeAlbumList
                    ?: ArrayList())
            startActivity(intent)
            LActivityUtil.tranIn(this)
            finish()
        })
    }

    override fun onResume() {
        adView?.resume()
        super.onResume()
        if (!isShowDialogCheck) {
            checkPermission()
        }
    }

    public override fun onPause() {
        adView?.pause()
        super.onPause()
    }

    public override fun onDestroy() {
        adView?.destroy()
        super.onDestroy()
    }

    private fun checkPermission() {
        isShowDialogCheck = true

        Dexter.withContext(this)
                .withPermissions(
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.ACCESS_FINE_LOCATION)
                .withListener(object : MultiplePermissionsListener {
                    override fun onPermissionsChecked(report: MultiplePermissionsReport) {
                        // check if all permissions are granted
                        if (report.areAllPermissionsGranted()) {
                            logD("onPermissionsChecked do you work now")
                            goToHome()
                        } else {
                            logD("!areAllPermissionsGranted")
                            showShouldAcceptPermission()
                        }

                        // check for permanent denial of any permission
                        if (report.isAnyPermissionPermanentlyDenied) {
                            logD("onPermissionsChecked permission is denied permenantly, navigate user to app settings")
                            showSettingsDialog()
                        }
                        isShowDialogCheck = true
                    }

                    override fun onPermissionRationaleShouldBeShown(permissions: List<PermissionRequest>, token: PermissionToken) {
                        logD("onPermissionRationaleShouldBeShown")
                        token.continuePermissionRequest()
                    }
                })
                .onSameThread()
                .check()
    }

    private fun showShouldAcceptPermission() {
        val alertDialog = LDialogUtil.showDialog2(
                context = this,
                title = "Need Permissions",
                msg = "This app needs permission to use this feature.",
                button1 = "Okay",
                button2 = "Cancel",
                onClickButton1 = {
                    checkPermission()
                },
                onClickButton2 = {
                    onBackPressed()
                }
        )
        alertDialog.setCancelable(false)
    }

    private fun showSettingsDialog() {
        val alertDialog = LDialogUtil.showDialog2(
                context = this,
                title = "Need Permissions",
                msg = "This app needs permission to use this feature. You can grant them in app settings.",
                button1 = "GOTO SETTINGS",
                button2 = getString(R.string.cancel),
                onClickButton1 = {
                    isShowDialogCheck = false
                    val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                    val uri = Uri.fromParts("package", packageName, null)
                    intent.data = uri
                    startActivityForResult(intent, 101)
                },
                onClickButton2 = {
                    onBackPressed()
                }
        )
        alertDialog.setCancelable(false)
    }
}
