package com.core.loitp.gallery.member;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.util.Pair;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.core.base.BaseFontActivity;
import com.core.common.Constants;
import com.core.loitp.gallery.photos.PhotosDataCore;
import com.core.utilities.LDialogUtil;
import com.core.utilities.LLog;
import com.core.utilities.LUIUtil;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.gson.Gson;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.restapi.flickr.FlickrConst;
import com.restapi.flickr.model.photosetgetlist.Photoset;
import com.restapi.flickr.model.photosetgetphotos.Photo;
import com.restapi.flickr.service.FlickrService;
import com.restapi.restclient.RestClient;
import com.views.layout.floatdraglayout.DisplayUtil;
import com.views.progressloadingview.avloadingindicatorview.AVLoadingIndicatorView;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import loitp.core.R;

public class GalleryMemberActivity extends BaseFontActivity {
    private AVLoadingIndicatorView avLoadingIndicatorView;
    private TextView tvTitle;

    private int currentPage = 0;
    private int totalPage = 1;
    private final int PER_PAGE_SIZE = 50;

    private boolean isLoading;
    private MemberAdapter memberAdapter;
    private AdView adView;

    private String photosetID;
    private int photosSize;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setShowAdWhenExit(false);
        RestClient.init(getString(R.string.flickr_URL));
        setTransparentStatusNavigationBar();
        PhotosDataCore.getInstance().clearData();

        final int resBkgRootView = getIntent().getIntExtra(Constants.getBKG_ROOT_VIEW(), R.color.colorPrimary);
        getRootView().setBackgroundResource(resBkgRootView);

        final String adUnitId = getIntent().getStringExtra(Constants.getAD_UNIT_ID_BANNER());
        LLog.d(getTAG(), "adUnitId " + adUnitId);
        final LinearLayout lnAdview = findViewById(R.id.ln_adview);
        if (adUnitId == null || adUnitId.isEmpty()) {
            lnAdview.setVisibility(View.GONE);
        } else {
            adView = new AdView(getActivity());
            adView.setAdSize(AdSize.SMART_BANNER);
            adView.setAdUnitId(adUnitId);
            LUIUtil.createAdBanner(adView);
            lnAdview.addView(adView);
            final int navigationHeight = DisplayUtil.getNavigationBarHeight(getActivity());
            LUIUtil.setMargins(lnAdview, 0, 0, 0, navigationHeight + navigationHeight / 4);
        }

        tvTitle = findViewById(R.id.tv_title);
        LUIUtil.setTextShadow(tvTitle, Color.WHITE);
        avLoadingIndicatorView = findViewById(R.id.av);

        photosetID = Constants.INSTANCE.getFLICKR_ID_MEMBERS();
        if (photosetID.isEmpty()) {
            handleException(new Exception(getString(R.string.err_unknow)));
            return;
        }
        LLog.d(getTAG(), "photosetID " + photosetID);
        photosSize = getIntent().getIntExtra(Constants.getSK_PHOTOSET_SIZE(), Constants.getNOT_FOUND());
        LLog.d(getTAG(), "photosSize " + photosSize);

        final RecyclerView recyclerView = findViewById(R.id.recyclerView);

        /*SlideInRightAnimator animator = new SlideInRightAnimator(new OvershootInterpolator(1f));
        animator.setAddDuration(1000);
        recyclerView.setItemAnimator(animator);*/

        final int numCount = 2;

        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), numCount));
        recyclerView.setHasFixedSize(true);
        memberAdapter = new MemberAdapter(getActivity(), numCount, new MemberAdapter.Callback() {
            @Override
            public void onClick(Photo photo, int pos, ImageView imageView, TextView textView) {
                final Intent intent = new Intent(getActivity(), GalleryMemberDetailActivity.class);
                intent.putExtra(GalleryMemberDetailActivity.PHOTO, photo);
                ActivityOptionsCompat activityOptions = ActivityOptionsCompat.makeSceneTransitionAnimation(
                        getActivity(),
                        new Pair<>(imageView, GalleryMemberDetailActivity.IV),
                        new Pair<>(textView, GalleryMemberDetailActivity.TV));
                ActivityCompat.startActivity(getActivity(), intent, activityOptions.toBundle());
            }

            @Override
            public void onLongClick(Photo photo, int pos) {
                ////do nothing
                //LSocialUtil.share(activity, photo.getUrlO());
            }
        });
        recyclerView.setAdapter(memberAdapter);
        /*ScaleInAnimationAdapter scaleAdapter = new ScaleInAnimationAdapter(memberAdapter);
        scaleAdapter.setDuration(1000);
        scaleAdapter.setInterpolator(new OvershootInterpolator());
        scaleAdapter.setFirstOnly(true);
        recyclerView.setAdapter(scaleAdapter);*/

        LUIUtil.setPullLikeIOSVertical(recyclerView);

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (!recyclerView.canScrollVertically(1)) {
                    if (!isLoading) {
                        LLog.d(getTAG(), "last item");
                        photosetsGetPhotos(photosetID);
                    }
                }
            }
        });
    }

    private void goToHome() {
        if (photosSize == Constants.getNOT_FOUND()) {
            photosetsGetList();
        } else {
            init();
        }
    }

    @Override
    protected boolean setFullScreen() {
        return false;
    }

    @Override
    protected String setTag() {
        return "TAG" + getClass().getSimpleName();
    }

    @Override
    protected int setLayoutResourceId() {
        return R.layout.activity_gallery_core_photos_only;
    }

    private void init() {
        LLog.d(getTAG(), "init photosSize " + photosSize);

        if (photosSize % PER_PAGE_SIZE == 0) {
            totalPage = photosSize / PER_PAGE_SIZE;
        } else {
            totalPage = photosSize / PER_PAGE_SIZE + 1;
        }

        currentPage = totalPage;
        //LLog.d(TAG, "total page " + totalPage);
        //LLog.d(TAG, "currentPage " + currentPage);

        photosetsGetPhotos(photosetID);
    }

    private void photosetsGetList() {
        avLoadingIndicatorView.smoothToShow();
        final FlickrService service = RestClient.createService(FlickrService.class);
        final String method = FlickrConst.METHOD_PHOTOSETS_GETLIST;
        final String apiKey = FlickrConst.API_KEY;
        final String userID = FlickrConst.USER_KEY;
        final int page = 1;
        final int perPage = 500;
        //String primaryPhotoExtras = FlickrConst.PRIMARY_PHOTO_EXTRAS_0;
        final String primaryPhotoExtras = "";
        final String format = FlickrConst.FORMAT;
        final int nojsoncallback = FlickrConst.NO_JSON_CALLBACK;

        getCompositeDisposable().add(service.photosetsGetList(method, apiKey, userID, page, perPage, primaryPhotoExtras, format, nojsoncallback)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(wrapperPhotosetGetlist -> {
                    LLog.d(getTAG(), "photosetsGetList onSuccess " + new Gson().toJson(wrapperPhotosetGetlist));
                    for (final Photoset photoset : wrapperPhotosetGetlist.getPhotosets().getPhotoset()) {
                        if (photoset.getId().equals(photosetID)) {
                            photosSize = Integer.parseInt(photoset.getPhotos());
                            init();
                            return;
                        }
                    }
                }, e -> {
                    LLog.e(getTAG(), "photosetsGetList onFail " + e.toString());
                    handleException(e);
                    avLoadingIndicatorView.smoothToHide();
                }));
    }

    private void photosetsGetPhotos(@NonNull final String photosetID) {
        if (isLoading) {
            LLog.d(getTAG(), "photosetsGetList isLoading true -> return");
            return;
        }
        LLog.d(getTAG(), "is calling photosetsGetPhotos " + currentPage + "/" + totalPage);
        isLoading = true;
        avLoadingIndicatorView.smoothToShow();
        final FlickrService service = RestClient.createService(FlickrService.class);
        final String method = FlickrConst.METHOD_PHOTOSETS_GETPHOTOS;
        final String apiKey = FlickrConst.API_KEY;
        final String userID = FlickrConst.USER_KEY;
        if (currentPage <= 0) {
            LLog.d(getTAG(), "currentPage <= 0 -> return");
            currentPage = 0;
            avLoadingIndicatorView.smoothToHide();
            return;
        }
        final String primaryPhotoExtras = FlickrConst.PRIMARY_PHOTO_EXTRAS_1;
        final String format = FlickrConst.FORMAT;
        final int nojsoncallback = FlickrConst.NO_JSON_CALLBACK;

        getCompositeDisposable().add(service.photosetsGetPhotos(method, apiKey, photosetID, userID, primaryPhotoExtras, PER_PAGE_SIZE, currentPage, format, nojsoncallback)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(wrapperPhotosetGetPhotos -> {
                    LLog.d(getTAG(), "photosetsGetPhotos " + currentPage + "/" + totalPage);

                    final String s = wrapperPhotosetGetPhotos.getPhotoset().getTitle() + " (" + currentPage + "/" + totalPage + ")";
                    tvTitle.setText(s);
                    final List<Photo> photoList = wrapperPhotosetGetPhotos.getPhotoset().getPhoto();
                    PhotosDataCore.getInstance().addPhoto(photoList);
                    updateAllViews();

                    avLoadingIndicatorView.smoothToHide();
                    isLoading = false;
                    currentPage--;
                }, e -> {
                    LLog.e(getTAG(), "photosetsGetPhotos onFail " + e.toString());
                    handleException(e);
                    avLoadingIndicatorView.smoothToHide();
                    isLoading = true;
                }));
    }

    private void updateAllViews() {
        if (memberAdapter != null) {
            memberAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onPause() {
        if (adView != null) {
            adView.pause();
        }
        super.onPause();
    }

    @Override
    public void onResume() {
        if (adView != null) {
            adView.resume();
        }
        super.onResume();
        if (!isShowDialogCheck) {
            checkPermission();
        }
    }

    @Override
    public void onDestroy() {
        if (adView != null) {
            adView.destroy();
        }
        super.onDestroy();
    }

    private boolean isShowDialogCheck;

    private void checkPermission() {
        isShowDialogCheck = true;
        Dexter.withActivity(this)
                .withPermissions(
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.ACCESS_FINE_LOCATION)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        // check if all permissions are granted
                        if (report.areAllPermissionsGranted()) {
                            LLog.d(getTAG(), "onPermissionsChecked do you work now");
                            goToHome();
                        } else {
                            LLog.d(getTAG(), "!areAllPermissionsGranted");
                            showShouldAcceptPermission();
                        }

                        // check for permanent denial of any permission
                        if (report.isAnyPermissionPermanentlyDenied()) {
                            LLog.d(getTAG(), "onPermissionsChecked permission is denied permenantly, navigate user to app settings");
                            showSettingsDialog();
                        }
                        isShowDialogCheck = true;
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                        LLog.d(getTAG(), "onPermissionRationaleShouldBeShown");
                        token.continuePermissionRequest();
                    }
                })
                .onSameThread()
                .check();
    }

    private void showShouldAcceptPermission() {
        final AlertDialog alertDialog = LDialogUtil.INSTANCE.showDialog2(getActivity(), "Need Permissions", "This app needs permission to use this feature.", "Okay", "Cancel", new LDialogUtil.Callback2() {
            @Override
            public void onClick1() {
                checkPermission();
            }

            @Override
            public void onClick2() {
                onBackPressed();
            }
        });
        alertDialog.setCancelable(false);
    }

    private void showSettingsDialog() {
        final AlertDialog alertDialog = LDialogUtil.INSTANCE.showDialog2(getActivity(), "Need Permissions", "This app needs permission to use this feature. You can grant them in app settings.", "GOTO SETTINGS", "Cancel", new LDialogUtil.Callback2() {
            @Override
            public void onClick1() {
                isShowDialogCheck = false;
                final Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                final Uri uri = Uri.fromParts("package", getPackageName(), null);
                intent.setData(uri);
                startActivityForResult(intent, 101);
            }

            @Override
            public void onClick2() {
                onBackPressed();
            }
        });
        alertDialog.setCancelable(false);
    }
}