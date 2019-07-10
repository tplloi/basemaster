package vn.loitp.app.activity.customviews.button.shinebutton;

import android.os.Bundle;
import android.view.View;

import com.core.base.BaseFontActivity;
import com.utils.util.ToastUtils;

import loitp.basemaster.R;
import vn.loitp.views.button.shinebutton.LShineView;

public class ShineButtonActivity extends BaseFontActivity {
    private LShineView bt0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bt0 = (LShineView) findViewById(R.id.bt_0);
        bt0.setImage(R.mipmap.ic_launcher);
        bt0.setSize(100, 80);
        bt0.setOnClick(new LShineView.Callback() {
            @Override
            public void onClick(View view) {
                ToastUtils.showShort("onClick");
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
        return R.layout.activity_button_shine;
    }
}
