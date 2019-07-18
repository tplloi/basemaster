package vn.loitp.app.activity.customviews.placeholderview.ex.androidinfinitelistwithloadmore;

import android.os.Bundle;
import android.view.View;

import com.core.base.BaseFontActivity;
import com.core.utilities.LLog;
import com.core.utilities.LUIUtil;
import com.views.placeholderview.lib.placeholderview.InfinitePlaceHolderView;

import java.util.List;

import loitp.basemaster.R;

public class AndroidInfiniteListWithLoadMoreActivity extends BaseFontActivity {
    private InfinitePlaceHolderView mLoadMoreView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLoadMoreView = (InfinitePlaceHolderView) findViewById(R.id.loadMoreView);

        LUIUtil.INSTANCE.setPullLikeIOSVertical(mLoadMoreView);

        setupView();
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
        return R.layout.activity_android_infinite_with_load_more;
    }

    private void setupView() {
        final List<InfiniteFeedInfo> feedList = Utils.loadInfiniteFeeds(this.getApplicationContext());
        LLog.INSTANCE.d("DEBUG", "LoadMoreView.LOAD_VIEW_SET_COUNT " + LoadMoreView.LOAD_VIEW_SET_COUNT);
        for (int i = 0; i < LoadMoreView.LOAD_VIEW_SET_COUNT; i++) {
            mLoadMoreView.addView(new ItemView(this.getApplicationContext(), feedList.get(i)));
        }
        mLoadMoreView.setLoadMoreResolver(new LoadMoreView(mLoadMoreView, feedList));
        findViewById(R.id.clear).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                feedList.clear();
                mLoadMoreView.removeAllViews();
            }
        });
    }
}
