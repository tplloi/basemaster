package com.core.utilities

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.net.Uri
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.exifinterface.media.ExifInterface
import com.R
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target
import com.core.common.Constants
import com.utils.util.FileUtils
import com.views.imageview.pinchtozoom.ImageMatrixTouchHandler
import com.views.progressloadingview.avl.LAVLoadingIndicatorView
import java.io.File
import java.util.*
import kotlin.math.min

/**
 * Created by www.muathu@gmail.com on 17/7/2019.
 */

object LImageUtil {
    private val TAG = javaClass.simpleName

    val randomUrlFlickr: String
        get() {
            val r = LStoreUtil.getRandomNumber(Constants.ARR_URL_BKG_FLICKR.size)
            return Constants.ARR_URL_BKG_FLICKR[r]
        }

    //for flide
    fun clear(context: Context, target: View) {
        Glide.with(context).clear(target)
    }

    fun load(context: Context, drawableRes: Int, imageView: ImageView) {
        Glide.with(context).load(drawableRes).into(imageView)
    }

    fun load(context: Context, url: String, imageView: ImageView) {
        Glide.with(context).load(url)
                .transition(withCrossFade())
                .apply(RequestOptions()
                        //.override(Target.SIZE_ORIGINAL)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                )
                .into(imageView)
    }

    fun load(context: Context, url: String, imageView: ImageView, drawableRequestListener: RequestListener<Drawable>) {
        Glide.with(context).load(url)
                .transition(withCrossFade())
                .apply(RequestOptions()
                        //.override(Target.SIZE_ORIGINAL)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                )
                .listener(drawableRequestListener)
                .into(imageView)
    }

    fun load(context: Context, url: String, imageView: ImageView, resPlaceHolder: Int) {
        Glide.with(context)
                .load(url)
                .transition(withCrossFade())
                .apply(RequestOptions()
                        .placeholder(resPlaceHolder)
                        //.override(Target.SIZE_ORIGINAL)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                )
                .into(imageView)
    }

    fun load(context: Context, imageFile: File, imageView: ImageView) {
        Glide.with(context).load(imageFile).into(imageView)
    }

    fun load(context: Context, uri: Uri, imageView: ImageView) {
        Glide.with(context).load(uri).into(imageView)
    }

    fun loadRound(url: String, imageView: ImageView, roundingRadius: Int, resPlaceHolder: Int) {
        var requestOptions = RequestOptions()
        requestOptions = requestOptions.transforms(CenterCrop(), RoundedCorners(roundingRadius)).placeholder(resPlaceHolder)
        Glide.with(imageView.context)
                .load(url)
                .transition(withCrossFade())
                .apply(requestOptions)
                .into(imageView)
    }

    fun loadCircle(url: String, imageView: ImageView) {
        Glide.with(imageView.context)
                .load(url)
                .transition(withCrossFade())
                .apply(RequestOptions.circleCropTransform())
                .into(imageView)
    }

    fun loadCircle(url: String, imageView: ImageView, resPlaceHolder: Int, resError: Int) {
        Glide.with(imageView.context)
                .load(url)
                .transition(withCrossFade())
                .apply(RequestOptions.circleCropTransform().placeholder(resPlaceHolder).error(resError)
                )
                .into(imageView)
    }

    fun loadCircleImageResources(res: Int, imageView: ImageView) {
        Glide.with(imageView.context)
                .load(res)
                .transition(withCrossFade())
                .apply(RequestOptions.circleCropTransform().placeholder(R.color.transparent).error(R.color.colorPrimary)
                )
                .into(imageView)
    }

    fun load(context: Context, url: String, imageView: ImageView,
             resPlaceHolder: Int, resError: Int, drawableRequestListener: RequestListener<Drawable>) {
        Glide.with(context).load(url)
                .transition(withCrossFade())
                .apply(RequestOptions()
                        .placeholder(resPlaceHolder)
                        //.override(Target.SIZE_ORIGINAL)
                        .error(resError)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                )
                .listener(drawableRequestListener)
                .into(imageView)
    }

    @SuppressLint("ResourceType")
    @JvmOverloads
    fun load(context: Context, url: String, imageView: ImageView,
             LAVLoadingIndicatorView: LAVLoadingIndicatorView?, resPlaceHolder:
             Int = Color.TRANSPARENT, resError: Int = Color.TRANSPARENT) {
        Glide.with(context).load(url)
                .transition(withCrossFade())
                .apply(RequestOptions()
                        .placeholder(resPlaceHolder)
                        //.override(Target.SIZE_ORIGINAL)
                        .error(resError)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                )
                .listener(object : RequestListener<Drawable> {
                    override fun onLoadFailed(e: GlideException?, model: Any, target: Target<Drawable>, isFirstResource: Boolean): Boolean {
                        LAVLoadingIndicatorView?.hide()
                        return false
                    }

                    override fun onResourceReady(resource: Drawable, model: Any, target: Target<Drawable>, dataSource: com.bumptech.glide.load.DataSource, isFirstResource: Boolean): Boolean {
                        LAVLoadingIndicatorView?.hide()
                        return false
                    }
                })
                .into(imageView)
    }

    fun load(context: Context, url: String, imageView: ImageView,
             progressBar: ProgressBar, sizeW: Int, sizeH: Int) {
        Glide.with(context).load(url)
                .transition(withCrossFade())
                .apply(RequestOptions()
                        .override(sizeW, sizeH)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                )
                .listener(object : RequestListener<Drawable> {
                    override fun onLoadFailed(e: GlideException?, model: Any, target: Target<Drawable>, isFirstResource: Boolean): Boolean {
                        LUIUtil.setProgressBarVisibility(progressBar, View.GONE)
                        return false
                    }

                    override fun onResourceReady(resource: Drawable, model: Any, target: Target<Drawable>, dataSource: com.bumptech.glide.load.DataSource, isFirstResource: Boolean): Boolean {
                        LUIUtil.setProgressBarVisibility(progressBar, View.GONE)
                        return false
                    }
                })
                .into(imageView)
    }

    fun load(context: Context, url: String, imageView: ImageView, progressBar: ProgressBar) {
        Glide.with(context).load(url)
                .transition(withCrossFade())
                .apply(RequestOptions()
                        //.override(Target.SIZE_ORIGINAL)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                )
                .listener(object : RequestListener<Drawable> {
                    override fun onLoadFailed(e: GlideException?, model: Any, target: Target<Drawable>, isFirstResource: Boolean): Boolean {
                        LUIUtil.setProgressBarVisibility(progressBar, View.GONE)
                        return false
                    }

                    override fun onResourceReady(resource: Drawable, model: Any, target: Target<Drawable>, dataSource: com.bumptech.glide.load.DataSource, isFirstResource: Boolean): Boolean {
                        LUIUtil.setProgressBarVisibility(progressBar, View.GONE)
                        return false
                    }
                })
                .into(imageView)
    }

    fun load(context: Context, url: String, imageView: ImageView, sizeW: Int, sizeH: Int) {
        Glide.with(context).load(url)
                .transition(withCrossFade())
                .apply(RequestOptions()
                        .placeholder(R.drawable.trans)
                        .override(sizeW, sizeH)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                ).into(imageView)
    }

    @JvmOverloads
    fun loadNoAmin(context: Context, url: String, imageView: ImageView,
                   drawableRequestListener: RequestListener<Drawable>? = null) {
        loadNoAmin(context, url, "", imageView, drawableRequestListener)
    }

    @JvmOverloads
    fun loadNoAmin(context: Context, url: String, urlThumbnal: String, imageView: ImageView,
                   drawableRequestListener: RequestListener<Drawable>? = null) {
        Glide.with(context).load(url)
                .thumbnail(Glide.with(context)
                        .load(urlThumbnal)
                        .thumbnail(1f)
                )
                .apply(RequestOptions()
                        .placeholder(R.drawable.trans)
                        //.override(Target.SIZE_ORIGINAL)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .dontAnimate()
                        .dontTransform()
                )
                .listener(drawableRequestListener)
                .into(imageView)
    }

    //for flick api url_m -> url_b
    fun getFlickrLink100(urlM: String): String? {
        var linkUrlM = urlM
        /*
        s	small square 75x75
        q	large square 150x150
        t	thumbnail, 100 on longest side
        m	small, 240 on longest side
        n	small, 320 on longest side
                -	medium, 500 on longest side
        z	medium 640, 640 on longest side
        c	medium 800, 800 on longest side†
        b	large, 1024 on longest side*
                h	large 1600, 1600 on longest side†
        k	large 2048, 2048 on longest side†
        o	original image, either a jpg, gif or png, depending on source format
        */


        if (linkUrlM.isEmpty()) {
            return null
        }
        linkUrlM = linkUrlM.toLowerCase(Locale.getDefault())
        if (linkUrlM.contains(".jpg")) {
            linkUrlM = linkUrlM.replace(".jpg", "_t.jpg")
        } else if (linkUrlM.contains(".png")) {
            linkUrlM = linkUrlM.replace(".png", "_t.png")
        }
        return linkUrlM
    }

    //for flick api url_m -> url_b
    fun getFlickrLink640(urlM: String): String? {
        var linkUrlM = urlM
        /*
        s	small square 75x75
        q	large square 150x150
        t	thumbnail, 100 on longest side
        m	small, 240 on longest side
        n	small, 320 on longest side
                -	medium, 500 on longest side
        z	medium 640, 640 on longest side
        c	medium 800, 800 on longest side†
        b	large, 1024 on longest side*
                h	large 1600, 1600 on longest side†
        k	large 2048, 2048 on longest side†
        o	original image, either a jpg, gif or png, depending on source format
        */


        if (linkUrlM.isEmpty()) {
            return null
        }
        linkUrlM = linkUrlM.toLowerCase(Locale.getDefault())
        if (linkUrlM.contains(".jpg")) {
            linkUrlM = linkUrlM.replace(".jpg", "_z.jpg")
        } else if (linkUrlM.contains(".png")) {
            linkUrlM = linkUrlM.replace(".png", "_z.png")
        }
        return linkUrlM
    }

    //for flick api url_m -> url_n
    fun getFlickrLink320(urlM: String): String? {
        var linkUrlM = urlM
        /*
        s	small square 75x75
        q	large square 150x150
        t	thumbnail, 100 on longest side
        m	small, 240 on longest side
        n	small, 320 on longest side
                -	medium, 500 on longest side
        z	medium 640, 640 on longest side
        c	medium 800, 800 on longest side†
        b	large, 1024 on longest side*
                h	large 1600, 1600 on longest side†
        k	large 2048, 2048 on longest side†
        o	original image, either a jpg, gif or png, depending on source format
        */


        if (linkUrlM.isEmpty()) {
            return null
        }
        linkUrlM = linkUrlM.toLowerCase(Locale.getDefault())
        if (linkUrlM.contains(".jpg")) {
            linkUrlM = linkUrlM.replace(".jpg", "_n.jpg")
        } else if (linkUrlM.contains(".png")) {
            linkUrlM = linkUrlM.replace(".png", "_n.png")
        }
        return linkUrlM
    }

    //for flick api url_m -> url_b
    fun getFlickrLink1024(urlM: String): String? {
        var linkUrlM = urlM
        /*
        s	small square 75x75
        q	large square 150x150
        t	thumbnail, 100 on longest side
        m	small, 240 on longest side
        n	small, 320 on longest side
                -	medium, 500 on longest side
        z	medium 640, 640 on longest side
        c	medium 800, 800 on longest side†
        b	large, 1024 on longest side*
                h	large 1600, 1600 on longest side†
        k	large 2048, 2048 on longest side†
        o	original image, either a jpg, gif or png, depending on source format
        */


        if (linkUrlM.isEmpty()) {
            return null
        }
        linkUrlM = linkUrlM.toLowerCase(Locale.getDefault())
        if (linkUrlM.contains(".jpg")) {
            linkUrlM = linkUrlM.replace(".jpg", "_b.jpg")
        } else if (linkUrlM.contains(".png")) {
            linkUrlM = linkUrlM.replace(".png", "_b.png")
        }
        return linkUrlM
    }

    @SuppressLint("ClickableViewAccessibility")
    fun setImageViewZoom(iv: ImageView?) {
        iv?.setOnTouchListener(ImageMatrixTouchHandler(iv.context))
    }

    fun resizeImage(context: Context?, file: File?, scaleTo: Int = 1024, folderPath: String?): File? {
        if (context == null || file == null || folderPath.isNullOrEmpty()) {
            return null
        }
        if (!file.exists()) {
            return null
        }
        try {
            val srcFilePath = file.path
            val destFilePath = LStoreUtil.getFolderPath(context, folderPath) + "/" + file.name
            val resultCopy = FileUtils.copyFile(srcFilePath, destFilePath)
            //LLog.d(TAG, "resizeImage srcFilePath: $srcFilePath")
            //LLog.d(TAG, "resizeImage destFilePath: $destFilePath")
            //LLog.d(TAG, "resizeImage -> resultCopy: $resultCopy")

            val copiedFile = File(destFilePath)
            if (!copiedFile.exists()) {
                return null
            }

            val ei = ExifInterface(copiedFile.path)
            val o = ei.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL)

            val bmOptions = BitmapFactory.Options()
            bmOptions.inJustDecodeBounds = true
            BitmapFactory.decodeFile(copiedFile.absolutePath, bmOptions)

            val photoW = bmOptions.outWidth
            val photoH = bmOptions.outHeight

            // Determine how much to scale down the image
            val scaleFactor = min(a = photoW / scaleTo, b = photoH / scaleTo)

            bmOptions.inJustDecodeBounds = false
            bmOptions.inSampleSize = scaleFactor

            val resized = BitmapFactory.decodeFile(copiedFile.absolutePath, bmOptions)
                    ?: return null
            copiedFile.outputStream().use {
                resized.compress(Bitmap.CompressFormat.JPEG, 75, it)

                val ei2 = ExifInterface(copiedFile.path)
                ei2.setAttribute(ExifInterface.TAG_ORIENTATION, o.toString())
                ei2.saveAttributes()

                resized.recycle()
            }
            return copiedFile
        } catch (e: Exception) {
            e.printStackTrace()
            //LLog.e(TAG, "resizeImage $e")
            return null
        }
    }
}
