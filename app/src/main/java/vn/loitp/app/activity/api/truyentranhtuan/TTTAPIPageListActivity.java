package vn.loitp.app.activity.api.truyentranhtuan;

import android.os.Bundle;
import android.widget.TextView;

import com.core.base.BaseFontActivity;
import com.core.utilities.LUIUtil;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.List;

import vn.loitp.app.R;
import vn.loitp.app.activity.api.truyentranhtuan.helper.pagelist.GetReadImgTask;

public class TTTAPIPageListActivity extends BaseFontActivity {
    private TextView tv;
    private TextView tvTitle;
    private AVLoadingIndicatorView avLoadingIndicatorView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tv = findViewById(R.id.textView);
        tvTitle = findViewById(R.id.tvTitle);
        avLoadingIndicatorView = findViewById(R.id.indicatorView);

        String currentLink = "http://truyentranhtuan.com/one-piece-chuong-69/";
        new GetReadImgTask(currentLink, avLoadingIndicatorView, new GetReadImgTask.Callback() {
            @Override
            public void onSuccess(List<String> imagesListOfOneChap) {
                LUIUtil.INSTANCE.printBeautyJson(imagesListOfOneChap, tv);
                tvTitle.setText("Danh sách page trong chap 69 - size: " + imagesListOfOneChap.size());
            }

            @Override
            public void onError() {
                showShort("onError");
            }
        }).execute();
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
        return R.layout.activity_api_ttt_page_list;
    }
}
