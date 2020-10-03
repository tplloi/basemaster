package com.core.helper.gallery.member

import android.Manifest
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.R
import com.annotation.LogTag
import com.core.base.BaseFontActivity
import com.core.common.Constants
import com.core.helper.gallery.photos.PhotosDataCore
import com.core.utilities.LActivityUtil
import com.core.utilities.LDialogUtil
import com.core.utilities.LUIUtil
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView
import com.interfaces.Callback2
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import com.restapi.flickr.FlickrConst
import com.restapi.flickr.model.photosetgetphotos.Photo
import com.restapi.flickr.service.FlickrService
import com.restapi.restclient.RestClient
import com.views.layout.swipeback.SwipeBackLayout
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.l_activity_flickr_gallery_core_photos_only.*

@LogTag("GalleryMemberActivity")
class GalleryMemberActivity : BaseFontActivity() {
    private var currentPage = 0
    private var totalPage = 1

    private var isLoading: Boolean = false
    private var memberAdapter: MemberAdapter? = null
    private var adView: AdView? = null

    private var photosetID: String? = null
    private var photosSize: Int = 0

    private var isShowDialogCheck: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.l_activity_flickr_gallery_core_photos_only)

        RestClient.init(getString(R.string.flickr_URL))
        PhotosDataCore.instance.clearData()

        val resBkgRootView = intent.getIntExtra(Constants.BKG_ROOT_VIEW, R.color.colorPrimary)
        rootView.setBackgroundResource(resBkgRootView)

        val adUnitId = intent.getStringExtra(Constants.AD_UNIT_ID_BANNER)
//        logD("adUnitId $adUnitId")
        if (adUnitId.isNullOrEmpty()) {
            lnAdView.visibility = View.GONE
        } else {
            adView = AdView(this)
            adView?.let {
                it.adSize = AdSize.SMART_BANNER
                it.adUnitId = adUnitId
                LUIUtil.createAdBanner(adView = it)
                lnAdView.addView(it)
                //val navigationHeight = DisplayUtil.getNavigationBarHeight(activity)
                //LUIUtil.setMargins(view = lnAdView, leftPx = 0, topPx = 0, rightPx = 0, bottomPx = navigationHeight + navigationHeight / 4)
            }
        }

        LUIUtil.setTextShadow(textView = tvTitle, color = Color.BLACK)

        photosetID = Constants.FLICKR_ID_MEMBERS
        if (photosetID?.isEmpty() == true) {
            handleException(Exception(getString(R.string.err_unknow)))
            return
        }
        photosSize = intent.getIntExtra(Constants.SK_PHOTOSET_SIZE, Constants.NOT_FOUND)

        recyclerView.layoutManager = GridLayoutManager(this, 2)
        recyclerView.setHasFixedSize(true)
        memberAdapter = MemberAdapter(context = this,
                callback = object : MemberAdapter.Callback {
                    override fun onClick(photo: Photo, pos: Int, imageView: ImageView, textView: TextView) {
                        val intent = Intent(this@GalleryMemberActivity, GalleryMemberDetailActivity::class.java)
                        intent.putExtra(GalleryMemberDetailActivity.PHOTO, photo)
                        startActivity(intent)
                        LActivityUtil.tranIn(this@GalleryMemberActivity)
                    }

                    override fun onLongClick(photo: Photo, pos: Int) {
                    }
                })
        recyclerView.adapter = memberAdapter
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (!recyclerView.canScrollVertically(1)) {
                    if (!isLoading) {
                        photosetsGetPhotos(photosetID = photosetID)
                    }
                }
            }
        })

        swipeBackLayout.setSwipeBackListener(object : SwipeBackLayout.OnSwipeBackListener {
            override fun onViewPositionChanged(mView: View, swipeBackFraction: Float, SWIPE_BACK_FACTOR: Float) {
            }

            override fun onViewSwipeFinished(mView: View, isEnd: Boolean) {
                if (isEnd) {
                    finish()
                    LActivityUtil.transActivityNoAnimation(this@GalleryMemberActivity)
                }
            }
        })
    }

    private fun goToHome() {
        if (photosSize == Constants.NOT_FOUND) {
            getPhotosets()
        } else {
            init()
        }
    }

    private fun init() {
        totalPage = if (photosSize % Constants.PER_PAGE_SIZE == 0) {
            photosSize / Constants.PER_PAGE_SIZE
        } else {
            photosSize / Constants.PER_PAGE_SIZE + 1
        }

        currentPage = totalPage

        photosetsGetPhotos(photosetID = photosetID)
    }

    private fun getPhotosets() {
        indicatorView.smoothToShow()
        val service = RestClient.createService(FlickrService::class.java)
        val method = FlickrConst.METHOD_PHOTOSETS_GETLIST
        val apiKey = FlickrConst.API_KEY
        val userID = FlickrConst.USER_KEY
        val page = 1
        val perPage = 500
        //String primaryPhotoExtras = FlickrConst.PRIMARY_PHOTO_EXTRAS_0;
        val primaryPhotoExtras = ""
        val format = FlickrConst.FORMAT
        val noJsonCallBack = FlickrConst.NO_JSON_CALLBACK

        compositeDisposable.add(service.getListPhotoset(method, apiKey, userID, page, perPage, primaryPhotoExtras, format, noJsonCallBack)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ wrapperPhotosetGetlist ->
                    wrapperPhotosetGetlist?.photosets?.photoset?.let { list ->
                        for (photoset in list) {
                            if (photoset.id == photosetID) {
                                photosSize = Integer.parseInt(photoset.photos ?: "0")
                                init()
                                return@subscribe
                            }
                        }
                    }
                }, { e ->
                    e.printStackTrace()
                    handleException(e)
                    indicatorView.smoothToHide()
                }))
    }

    private fun photosetsGetPhotos(photosetID: String?) {
        if (photosetID.isNullOrEmpty()) {
//            logD("photosetID isNullOrEmpty -> return")
            return
        }
        if (isLoading) {
//            logD("photosetsGetList isLoading true -> return")
            return
        }
//        logD("is calling photosetsGetPhotos $currentPage/$totalPage")
        isLoading = true
        indicatorView.smoothToShow()
        val service = RestClient.createService(FlickrService::class.java)
        val method = FlickrConst.METHOD_PHOTOSETS_GETPHOTOS
        val apiKey = FlickrConst.API_KEY
        val userID = FlickrConst.USER_KEY
        if (currentPage <= 0) {
//            logD("currentPage <= 0 -> return")
            currentPage = 0
            indicatorView.smoothToHide()
            return
        }
        val primaryPhotoExtras = FlickrConst.PRIMARY_PHOTO_EXTRAS_1
        val format = FlickrConst.FORMAT
        val noJsonCallBack = FlickrConst.NO_JSON_CALLBACK

        compositeDisposable.add(service.getPhotosetPhotos(method = method,
                apiKey = apiKey,
                photosetId = photosetID,
                userId = userID,
                extras = primaryPhotoExtras,
                perPage = Constants.PER_PAGE_SIZE,
                page = currentPage,
                format = format,
                noJsonCallback = noJsonCallBack)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ wrapperPhotosetGetPhotos ->
//                    logD("photosetsGetPhotos $currentPage/$totalPage")

                    val s = wrapperPhotosetGetPhotos?.photoset?.title + " (" + currentPage + "/" + totalPage + ")"
                    tvTitle.text = s
                    wrapperPhotosetGetPhotos?.photoset?.photo?.let {
                        PhotosDataCore.instance.addPhoto(it)
                    }
                    updateAllViews()

                    indicatorView.smoothToHide()
                    isLoading = false
                    currentPage--
                }, { e ->
                    e.printStackTrace()
                    handleException(e)
                    indicatorView.smoothToHide()
                    isLoading = true
                }))
    }

    private fun updateAllViews() {
        memberAdapter?.notifyDataSetChanged()
    }

    public override fun onPause() {
        adView?.pause()
        super.onPause()
    }

    public override fun onResume() {
        adView?.resume()
        super.onResume()
        if (!isShowDialogCheck) {
            checkPermission()
        }
    }

    public override fun onDestroy() {
        adView?.destroy()
        super.onDestroy()
    }

    private fun checkPermission() {
        isShowDialogCheck = true
        Dexter.withActivity(this)
                .withPermissions(
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.ACCESS_FINE_LOCATION)
                .withListener(object : MultiplePermissionsListener {
                    override fun onPermissionsChecked(report: MultiplePermissionsReport) {
                        // check if all permissions are granted
                        if (report.areAllPermissionsGranted()) {
//                            logD("onPermissionsChecked do you work now")
                            goToHome()
                        } else {
//                            logD("!areAllPermissionsGranted")
                            showShouldAcceptPermission()
                        }

                        // check for permanent denial of any permission
                        if (report.isAnyPermissionPermanentlyDenied) {
//                            logD("onPermissionsChecked permission is denied permenantly, navigate user to app settings")
                            showSettingsDialog()
                        }
                        isShowDialogCheck = true
                    }

                    override fun onPermissionRationaleShouldBeShown(permissions: List<PermissionRequest>, token: PermissionToken) {
//                        logD("onPermissionRationaleShouldBeShown")
                        token.continuePermissionRequest()
                    }
                })
                .onSameThread()
                .check()
    }

    private fun showShouldAcceptPermission() {
        val alertDialog = LDialogUtil.showDialog2(context = this,
                title = "Need Permissions",
                msg = "This app needs permission to use this feature.",
                button1 = "Okay",
                button2 = "Cancel",
                callback2 = object : Callback2 {
                    override fun onClick1() {
                        checkPermission()
                    }

                    override fun onClick2() {
                        onBackPressed()
                    }
                })
        alertDialog.setCancelable(false)
    }

    private fun showSettingsDialog() {
        val alertDialog = LDialogUtil.showDialog2(context = this,
                title = "Need Permissions",
                msg = "This app needs permission to use this feature. You can grant them in app settings.",
                button1 = "GOTO SETTINGS",
                button2 = "Cancel",
                callback2 = object : Callback2 {
                    override fun onClick1() {
                        isShowDialogCheck = false
                        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                        val uri = Uri.fromParts("package", packageName, null)
                        intent.data = uri
                        startActivityForResult(intent, 101)
                    }

                    override fun onClick2() {
                        onBackPressed()
                    }
                })
        alertDialog.setCancelable(false)
    }
}
