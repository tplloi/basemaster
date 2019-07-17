package vn.loitp.app.activity.customviews.placeholderview.ex.androidadvanceimagegallery;

import android.os.Bundle;
import android.view.View;

import com.core.base.BaseFontActivity;
import com.core.utilities.LLog;
import com.core.utilities.LUIUtil;
import com.views.placeholderview.lib.placeholderview.InfinitePlaceHolderView;

import java.util.ArrayList;
import java.util.List;

import loitp.basemaster.R;

public class AndroidAdvanceImageGalleryActivity extends BaseFontActivity {
    private InfinitePlaceHolderView mGalleryView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mGalleryView = (InfinitePlaceHolderView) findViewById(R.id.galleryView);

        LUIUtil.setPullLikeIOSVertical(mGalleryView);

        setupGallery();
        findViewById(R.id.remove).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mGalleryView.removeAllViews();
            }
        });
    }

    @Override
    protected boolean setFullScreen() {
        return false;
    }

    @Override
    protected String setTag() {
        return getClass().getSimpleName();
    }

    @Override
    protected int setLayoutResourceId() {
        return R.layout.activity_android_advance_image_gallery;
    }

    private void setupGallery() {
        List<Image> imageList = Utils.loadImages(this.getApplicationContext());
        List<Image> newImageList = new ArrayList<>();
        for (int i = 0; i < imageList.size(); i++) {
            newImageList.add(imageList.get(i));
        }
        mGalleryView.addView(new ImageTypeSmallList(this.getApplicationContext(), newImageList));

        for (int i = imageList.size() - 1; i >= 0; i--) {
            mGalleryView.addView(new ImageTypeBig(this.getApplicationContext(), mGalleryView, imageList.get(i).getImageUrl()));
        }

        mGalleryView.setLoadMoreResolver(new LoadMoreView(mGalleryView, newImageList));

        newImageList.addAll(loadMore());
        newImageList.addAll(loadMore());
        newImageList.addAll(loadMore());
        newImageList.addAll(loadMore());
        newImageList.addAll(loadMore());
        newImageList.addAll(loadMore());
    }

    private List<Image> loadMore() {
        List<Image> imageList = Utils.loadImages(this.getApplicationContext());
        List<Image> newImageList = new ArrayList<>();
        for (int i = 0; i < imageList.size(); i++) {
            newImageList.add(imageList.get(i));
        }
        LLog.INSTANCE.d(getTAG(), ">>>loadMore " + newImageList.size());
        return newImageList;
    }
}
