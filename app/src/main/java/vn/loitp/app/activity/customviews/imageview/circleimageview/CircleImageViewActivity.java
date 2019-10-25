package vn.loitp.app.activity.customviews.imageview.circleimageview;

import android.os.Bundle;
import android.widget.ImageView;

import com.core.base.BaseFontActivity;
import com.core.utilities.LImageUtil;

import loitp.basemaster.R;
import vn.loitp.app.common.Constants;

public class CircleImageViewActivity extends BaseFontActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ImageView iv = findViewById(R.id.iv);
        ImageView iv1 = findViewById(R.id.iv1);
        ImageView iv2 = findViewById(R.id.iv2);
        int resPlaceHolder = R.color.Light_Cyan;
        LImageUtil.INSTANCE.loadRound("https://kenh14cdn.com/2019/2/25/2-1551076391040835580731.jpg", iv, 45, resPlaceHolder);
        LImageUtil.INSTANCE.loadCircle("https://kenh14cdn.com/2019/2/25/2-1551076391040835580731.jpg", iv1);
        LImageUtil.INSTANCE.loadCircle(Constants.INSTANCE.getURL_IMG_LARGE(), iv2, R.color.Red, R.drawable.l_error_404);
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
        return R.layout.activity_circle_imageview;
    }
}
