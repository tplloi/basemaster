package com.core.helper.gallery.albumonly

import alirezat775.lib.downloader.core.OnDownloadListener
import android.Manifest
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.Settings
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.R
import com.annotation.LogTag
import com.core.base.BaseApplication
import com.core.base.BaseFragment
import com.core.common.Constants
import com.core.helper.gallery.photos.PhotosDataCore
import com.core.utilities.*
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import com.restapi.flickr.FlickrConst
import com.restapi.flickr.model.photosetgetphotos.Photo
import com.restapi.flickr.service.FlickrService
import com.restapi.restclient.RestClient
import com.views.setSafeOnClickListener
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import jp.wasabeef.recyclerview.adapters.SlideInBottomAnimationAdapter
import kotlinx.android.synthetic.main.l_frm_flickr_gallery_core_photos_only.*
import java.io.File

@LogTag("GalleryCorePhotosOnlyFrm")
class GalleryCorePhotosOnlyFrm(
        val onTop: ((Unit) -> Unit)? = null,
        val onBottom: ((Unit) -> Unit)? = null,
        val onScrolled: ((isScrollDown: Boolean) -> Unit)? = null
) : BaseFragment() {

    companion object {
        private const val PER_PAGE_SIZE = 100
        const val IS_SHOW_TITLE = "IS_SHOW_TITLE"
    }

    private var currentPage = 0
    private var totalPage = 1
    private var isLoading: Boolean = false
    private var photosOnlyAdapter: PhotosOnlyAdapter? = null
    private var photosetID: String? = null
    private var photosSize: Int = 0
    private var isShowDialogCheck: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        RestClient.init(getString(R.string.flickr_URL))
    }

    override fun setLayoutResourceId(): Int {
        return R.layout.l_frm_flickr_gallery_core_photos_only
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val bundle = arguments ?: return
        PhotosDataCore.instance.clearData()
        photosetID = bundle.getString(Constants.SK_PHOTOSET_ID)
        if (photosetID.isNullOrEmpty()) {
            handleException(Exception(getString(R.string.err_unknow)))
            return
        }
//        logD("photosetID $photosetID")
        photosSize = bundle.getInt(Constants.SK_PHOTOSET_SIZE, Constants.NOT_FOUND)
//        logD("photosSize $photosSize")

        val isShowTitle = bundle.getBoolean(IS_SHOW_TITLE)
        if (isShowTitle) {
            tvTitle.visibility = View.VISIBLE
        } else {
            tvTitle.visibility = View.GONE
        }

        recyclerView.layoutManager = LinearLayoutManager(activity)
        activity?.let { a ->
            photosOnlyAdapter = PhotosOnlyAdapter(
                    callback = object : PhotosOnlyAdapter.Callback {
                        override fun onClick(photo: Photo, pos: Int) {
                        }

                        override fun onLongClick(photo: Photo, pos: Int) {
                        }

                        override fun onClickDownload(photo: Photo, pos: Int) {
                            save(url = photo.urlO)
                        }

                        override fun onClickShare(photo: Photo, pos: Int) {
                            LSocialUtil.share(activity = a, msg = photo.urlO)
                        }

                        override fun onClickReport(photo: Photo, pos: Int) {
                            LSocialUtil.sendEmail(context = a)
                        }

                        override fun onClickCmt(photo: Photo, pos: Int) {
                            LSocialUtil.openFacebookComment(context = a, url = photo.urlO)
                        }
                    })
        }
        photosOnlyAdapter?.let {
//            val animAdapter = AlphaInAnimationAdapter(it)
//            val animAdapter = ScaleInAnimationAdapter(it)
            val animAdapter = SlideInBottomAnimationAdapter(it)
//            val animAdapter = SlideInLeftAnimationAdapter(it)
//            val animAdapter = SlideInRightAnimationAdapter(it)

            animAdapter.setDuration(1000)
//            animAdapter.setInterpolator(OvershootInterpolator())
            animAdapter.setFirstOnly(true)
            recyclerView.adapter = animAdapter
        }

        //LUIUtil.setPullLikeIOSVertical(recyclerView)

//        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
//            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
//                super.onScrollStateChanged(recyclerView, newState)
//                if (!recyclerView.canScrollVertically(1)) {
//                    if (!isLoading) {
//                        photosetsGetPhotos(photosetID)
//                    }
//                }
//            }
//        })

        LUIUtil.setScrollChange(
                recyclerView = recyclerView,
                onTop = {
                    onTop?.invoke(Unit)
                },
                onBottom = {
                    if (!isLoading) {
                        photosetsGetPhotos(photosetID)
                    }
                    onBottom?.invoke(Unit)
                },
                onScrolled = {
                    onScrolled?.invoke(it)
                }
        )

        btPage.setSafeOnClickListener {
            showListPage()
        }
    }

    private fun showListPage() {
        val size = totalPage
        val arr = arrayOfNulls<String>(size)
        for (i in 0 until size) {
            arr[i] = "Page " + (totalPage - i)
        }
        activity?.let {
            LDialogUtil.showDialogList(
                    context = it,
                    title = "Select page",
                    arr = arr,
                    onClick = { position ->
                        currentPage = totalPage - position
                        logD("showDialogList onClick position $position, -> currentPage: $currentPage")
                        PhotosDataCore.instance.clearData()
                        updateAllViews()
                        photosetsGetPhotos(photosetID)
                    })
        }
    }

    private fun goToHome() {
        if (photosSize == Constants.NOT_FOUND) {
            getPhotosets()
        } else {
            init()
        }
    }

    private fun init() {
        logD("init photosSize $photosSize")

        totalPage = if (photosSize % PER_PAGE_SIZE == 0) {
            photosSize / PER_PAGE_SIZE
        } else {
            photosSize / PER_PAGE_SIZE + 1
        }

        currentPage = totalPage

        photosetsGetPhotos(photosetID)
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
            logD("photosetID isNullOrEmpty -> return")
            return
        }
        if (isLoading) {
            logD("photosetsGetList isLoading true -> return")
            return
        }
        logD("is calling photosetsGetPhotos $currentPage/$totalPage")
        isLoading = true
        indicatorView.smoothToShow()
        val service = RestClient.createService(FlickrService::class.java)
        val method = FlickrConst.METHOD_PHOTOSETS_GETPHOTOS
        val apiKey = FlickrConst.API_KEY
        val userID = FlickrConst.USER_KEY
        if (currentPage <= 0) {
            logD("currentPage <= 0 -> return")
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
                perPage = PER_PAGE_SIZE,
                page = currentPage,
                format = format,
                noJsonCallback = noJsonCallBack)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ wrapperPhotosetGetPhotos ->
                    logD("photosetsGetPhotos onSuccess " + BaseApplication.gson.toJson(wrapperPhotosetGetPhotos))

                    val s = wrapperPhotosetGetPhotos?.photoset?.title + " (" + currentPage + "/" + totalPage + ")"
                    tvTitle.text = s
                    wrapperPhotosetGetPhotos?.photoset?.photo?.let {
                        it.shuffle()
                        PhotosDataCore.instance.addPhoto(it)
                    }
                    updateAllViews()

                    indicatorView.smoothToHide()
                    btPage.visibility = View.VISIBLE
                    isLoading = false
                    currentPage--
                }, { e ->
                    handleException(e)
                    indicatorView.smoothToHide()
                    isLoading = true
                }))
    }

    private fun updateAllViews() {
        photosOnlyAdapter?.notifyDataSetChanged()
    }

    override fun onResume() {
        super.onResume()
//        logD("onResume")
        if (!isShowDialogCheck) {
            checkPermission()
        }
    }

    private fun checkPermission() {
        isShowDialogCheck = true
        Dexter.withContext(context)
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
        activity?.let { a ->
            val alertDialog = LDialogUtil.showDialog2(
                    context = a,
                    title = "Need Permissions",
                    msg = "This app needs permission to use this feature.",
                    button1 = "Okay",
                    button2 = "Cancel",
                    onClickButton1 = {
                        checkPermission()
                    },
                    onClickButton2 = {
                        a.onBackPressed()
                    }
            )
            alertDialog.setCancelable(false)
        }
    }

    private fun showSettingsDialog() {
        activity?.let { a ->
            val alertDialog = LDialogUtil.showDialog2(
                    context = a,
                    title = "Need Permissions",
                    msg = "This app needs permission to use this feature. You can grant them in app settings.",
                    button1 = "GOTO SETTINGS",
                    button2 = getString(R.string.cancel),
                    onClickButton1 = {
                        isShowDialogCheck = false
                        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                        val uri = Uri.fromParts("package", a.packageName, null)
                        intent.data = uri
                        startActivityForResult(intent, 101)
                    },
                    onClickButton2 = {
                        activity?.onBackPressed()
                    }
            )
            alertDialog.setCancelable(false)
        }
    }

    private fun save(url: String) {
        val downloader = LStoreUtil.getDownloader(
                folderName = Environment.DIRECTORY_PICTURES + "/" + LAppResource.getString(R.string.app_name),
                url = url,
                onDownloadListener = object : OnDownloadListener {
                    override fun onCancel() {
                    }

                    override fun onCompleted(file: File?) {
                        file?.let {
                            showLongInformation("Saved in ${it.path}")
                            LStoreUtil.sendBroadcastMediaScan(it)
                        }
                    }

                    override fun onFailure(reason: String?) {
                        showLongError("Download failed $reason")
                    }

                    override fun onPause() {
                    }

                    override fun onProgressUpdate(percent: Int, downloadedSize: Int, totalSize: Int) {
                    }

                    override fun onResume() {
                    }

                    override fun onStart() {
                    }

                }
        )
        downloader?.download()
    }
}
