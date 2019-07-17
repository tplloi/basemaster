package com.core.utilities;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.core.common.Constants;
import com.views.progressloadingview.avloadingindicatorview.AVLoadingIndicatorView;

import java.io.File;

import loitp.core.R;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;

/**
 * Created by www.muathu@gmail.com on 10/7/2017.
 */

public class LImageUtil {
    //for flide
    public static void clear(@NonNull final Context context, @NonNull final View target) {
        Glide.with(context).clear(target);
    }

    public static void load(@NonNull final Context context, final int drawableRes, @NonNull final ImageView imageView) {
        Glide.with(context).load(drawableRes).into(imageView);
    }

    public static void load(@NonNull final Context context, @NonNull final String url, @NonNull final ImageView imageView) {
        Glide.with(context).load(url)
                .transition(withCrossFade())
                .apply(new RequestOptions()
                        //.placeholder(resPlaceHolder)
                        //.fitCenter()
                        //.override(sizeW, sizeH)
                        .override(Target.SIZE_ORIGINAL)
                        //.error(resError)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                )
                .into(imageView);
    }

    public static void load(@NonNull final Context context, @NonNull final String url, @NonNull final ImageView imageView, final RequestListener<Drawable> drawableRequestListener) {
        Glide.with(context).load(url)
                .transition(withCrossFade())
                .apply(new RequestOptions()
                        //.placeholder(resPlaceHolder)
                        //.fitCenter()
                        //.override(sizeW, sizeH)
                        .override(Target.SIZE_ORIGINAL)
                        //.error(resError)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                )
                .listener(drawableRequestListener)
                .into(imageView);
    }

    public static void load(@NonNull final Context context, @NonNull final String url, @NonNull final ImageView imageView, final int resPlaceHolder) {
        //Glide.with(activity).load(url).placeholder(resPlaceHolder).into(imageView);
        Glide.with(context)
                .load(url)
                .transition(withCrossFade())
                .apply(new RequestOptions()
                        .placeholder(resPlaceHolder)
                        //.fitCenter()
                        .override(Target.SIZE_ORIGINAL)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                )
                .into(imageView);
    }

    public static void load(@NonNull final Context context, @NonNull final File imageFile, @NonNull final ImageView imageView) {
        Glide.with(context).load(imageFile).into(imageView);
    }

    public static void load(@NonNull final Context context, @NonNull final Uri uri, @NonNull final ImageView imageView) {
        Glide.with(context).load(uri).into(imageView);
    }

    public static void loadRound(@NonNull final String url, @NonNull final ImageView imageView, final int roundingRadius, final int resPlaceHolder) {
        RequestOptions requestOptions = new RequestOptions();
        requestOptions = requestOptions.transforms(new CenterCrop(), new RoundedCorners(roundingRadius)).placeholder(resPlaceHolder);
        Glide.with(imageView.getContext())
                .load(url)
                .transition(withCrossFade())
                .apply(requestOptions)
                .into(imageView);
    }

    public static void loadCircle(@NonNull final String url, @NonNull final ImageView imageView) {
        Glide.with(imageView.getContext())
                .load(url)
                .transition(withCrossFade())
                .apply(RequestOptions.circleCropTransform())
                .into(imageView);
    }

    public static void loadCircle(@NonNull final String url, @NonNull final ImageView imageView, final int resPlaceHolder, final int resError) {
        Glide.with(imageView.getContext())
                .load(url)
                .transition(withCrossFade())
                .apply(RequestOptions.circleCropTransform().placeholder(resPlaceHolder).error(resError)
                )
                .into(imageView);
    }
    /*public static void load(Activity activity, String url, ImageView imageView, RequestListener<String, GlideDrawable> glideDrawableRequestListener) {
        Glide.with(activity).load(url)
                .listener(glideDrawableRequestListener)
                .into(imageView);
    }*/

    /*public static void loadNoEffect(Activity activity, String url, String oldImage, ImageView imageView) {
        Glide.with(activity).load(url)
                .thumbnail(
                        Glide // this thumbnail request has to have the same RESULT cache key
                        .with(activity) // as the outer request, which usually simply means
                        .load(oldImage) // same size/transformation(e.g. centerCrop)/format(e.g. asBitmap)
                        .centerCrop() // have to be explicit here to match outer load exactly
                )
                .listener(new RequestListener<String, GlideDrawable>() {
                    @Override
                    public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                        if (isFirstResource) {
                            return false; // thumbnail was not shown, do as usual
                        }
                        return new DrawableCrossFadeFactory<Drawable>(customize animation here)
                                .build(false, false) // force crossFade() even if coming from memory cache
                                .animate(resource, (GlideAnimation.ViewAdapter) target);
                    }
                })
                .dontAnimate()
                .dontTransform()
                .into(imageView);
    }*/

    public static void load(@NonNull final Context context, @NonNull final String url, @NonNull final ImageView imageView,
                            final int resPlaceHolder, final int resError, final RequestListener<Drawable> drawableRequestListener) {
        Glide.with(context).load(url)
                .transition(withCrossFade())
                .apply(new RequestOptions()
                        .placeholder(resPlaceHolder)
                        //.fitCenter()
                        //.override(sizeW, sizeH)
                        .override(Target.SIZE_ORIGINAL)
                        .error(resError)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                )
                .listener(drawableRequestListener)
                .into(imageView);
    }

    public static void load(@NonNull final Context context, @NonNull final String url, @NonNull final ImageView imageView,
                            @Nullable final AVLoadingIndicatorView avLoadingIndicatorView, final int resPlaceHolder, final int resError) {
        Glide.with(context).load(url)
                .transition(withCrossFade())
                .apply(new RequestOptions()
                        .placeholder(resPlaceHolder)
                        //.fitCenter()
                        //.override(sizeW, sizeH)
                        .override(Target.SIZE_ORIGINAL)
                        .error(resError)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                )
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        if (avLoadingIndicatorView != null) {
                            //avLoadingIndicatorView.smoothToHide();
                            avLoadingIndicatorView.hide();
                        }
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, com.bumptech.glide.load.DataSource dataSource, boolean isFirstResource) {
                        if (avLoadingIndicatorView != null) {
                            //avLoadingIndicatorView.smoothToHide();
                            avLoadingIndicatorView.hide();
                        }
                        return false;
                    }
                })
                .into(imageView);
    }

    public static void load(@NonNull final Context context, @NonNull final String url, @NonNull final ImageView imageView,
                            @NonNull final ProgressBar progressBar, final int sizeW, final int sizeH) {
        Glide.with(context).load(url)
                .transition(withCrossFade())
                .apply(new RequestOptions()
                        //.placeholder(resPlaceHolder)
                        //.fitCenter()
                        .override(sizeW, sizeH)
                        //.error(resError)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                )
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        LUIUtil.setProgressBarVisibility(progressBar, View.GONE);
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, com.bumptech.glide.load.DataSource dataSource, boolean isFirstResource) {
                        LUIUtil.setProgressBarVisibility(progressBar, View.GONE);
                        return false;
                    }
                })
                .into(imageView);
    }

    public static void load(@NonNull final Context context, @NonNull final String url, @NonNull final ImageView imageView, @NonNull final ProgressBar progressBar) {
        Glide.with(context).load(url)
                .transition(withCrossFade())
                .apply(new RequestOptions()
                        //.placeholder(resPlaceHolder)
                        //.fitCenter()
                        .override(Target.SIZE_ORIGINAL)
                        //.error(resError)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                )
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        LUIUtil.setProgressBarVisibility(progressBar, View.GONE);
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, com.bumptech.glide.load.DataSource dataSource, boolean isFirstResource) {
                        LUIUtil.setProgressBarVisibility(progressBar, View.GONE);
                        return false;
                    }
                })
                .into(imageView);
    }

    public static void load(@NonNull final Context context, @NonNull final String url, @NonNull final ImageView imageView,
                            @Nullable final AVLoadingIndicatorView avLoadingIndicatorView, final int resPlaceHolder) {
        load(context, url, imageView, avLoadingIndicatorView, resPlaceHolder, Color.TRANSPARENT);
    }

    public static void load(@NonNull final Context context, @NonNull final String url, @NonNull final ImageView imageView, @Nullable final AVLoadingIndicatorView avLoadingIndicatorView) {
        load(context, url, imageView, avLoadingIndicatorView, Color.TRANSPARENT, Color.TRANSPARENT);
    }

    public static void load(@NonNull final Context context, @NonNull final String url, @NonNull final ImageView imageView,
                            final int sizeW, final int sizeH) {
        Glide.with(context).load(url)
                .transition(withCrossFade())
                .apply(new RequestOptions()
                        .placeholder(R.drawable.trans)
                        //.fitCenter()
                        .override(sizeW, sizeH)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                ).into(imageView);
    }

    public static void loadNoAmin(@NonNull final Context context, @NonNull final String url, @NonNull final ImageView imageView) {
        loadNoAmin(context, url, imageView, null);
    }

    public static void loadNoAmin(@NonNull final Context context, @NonNull final String url, @NonNull final ImageView imageView,
                                  final RequestListener<Drawable> drawableRequestListener) {
        loadNoAmin(context, url, "", imageView, drawableRequestListener);
    }

    public static void loadNoAmin(@NonNull final Context context, @NonNull final String url, @NonNull final String urlThumbnal, @NonNull final ImageView imageView) {
        loadNoAmin(context, url, urlThumbnal, imageView, null);
    }

    public static void loadNoAmin(@NonNull final Context context, @NonNull final String url, @NonNull final String urlThumbnal, @NonNull final ImageView imageView,
                                  final RequestListener<Drawable> drawableRequestListener) {
        Glide.with(context).load(url)
                //.transition(withCrossFade())
                .thumbnail(Glide.with(context)
                        .load(urlThumbnal)
                        //.asBitmap()
                        //.crossFade()
                        .thumbnail(1f)
                )
                .apply(new RequestOptions()
                        .placeholder(R.drawable.trans)
                        //.fitCenter()
                        //.override(sizeW, sizeH)
                        .override(Target.SIZE_ORIGINAL)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .dontAnimate()
                        .dontTransform()
                )
                .listener(drawableRequestListener)
                .into(imageView);
    }

    //for flick api url_m -> url_b
    public static String getFlickrLink100(@NonNull String linkUrlM) {
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
            return null;
        }
        linkUrlM = linkUrlM.toLowerCase();
        if (linkUrlM.contains(".jpg")) {
            linkUrlM = linkUrlM.replace(".jpg", "_t.jpg");
        } else if (linkUrlM.contains(".png")) {
            linkUrlM = linkUrlM.replace(".png", "_t.png");
        }
        return linkUrlM;
    }

    //for flick api url_m -> url_b
    public static String getFlickrLink640(@NonNull String linkUrlM) {
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
            return null;
        }
        linkUrlM = linkUrlM.toLowerCase();
        if (linkUrlM.contains(".jpg")) {
            linkUrlM = linkUrlM.replace(".jpg", "_z.jpg");
        } else if (linkUrlM.contains(".png")) {
            linkUrlM = linkUrlM.replace(".png", "_z.png");
        }
        return linkUrlM;
    }

    //for flick api url_m -> url_n
    public static String getFlickrLink320(@NonNull String linkUrlM) {
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
            return null;
        }
        linkUrlM = linkUrlM.toLowerCase();
        if (linkUrlM.contains(".jpg")) {
            linkUrlM = linkUrlM.replace(".jpg", "_n.jpg");
        } else if (linkUrlM.contains(".png")) {
            linkUrlM = linkUrlM.replace(".png", "_n.png");
        }
        return linkUrlM;
    }

    //for flick api url_m -> url_b
    public static String getFlickrLink1024(@NonNull String linkUrlM) {
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
            return null;
        }
        linkUrlM = linkUrlM.toLowerCase();
        if (linkUrlM.contains(".jpg")) {
            linkUrlM = linkUrlM.replace(".jpg", "_b.jpg");
        } else if (linkUrlM.contains(".png")) {
            linkUrlM = linkUrlM.replace(".png", "_b.png");
        }
        return linkUrlM;
    }

    public static String getRandomUrlFlickr() {
        final int r = LStoreUtil.getRandomNumber(Constants.INSTANCE.getARR_URL_BKG_FLICKR().length);
        return Constants.INSTANCE.getARR_URL_BKG_FLICKR()[r];
    }

    public static int getRandomMiniDrawable() {
        final int r = LStoreUtil.getRandomNumber(Constants.INSTANCE.getARR_RANDOM_MINI_DRAWABLE().length);
        return Constants.INSTANCE.getARR_RANDOM_MINI_DRAWABLE()[r];
    }
}