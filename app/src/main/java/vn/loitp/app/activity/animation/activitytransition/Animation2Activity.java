package vn.loitp.app.activity.animation.activitytransition;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

import loitp.basemaster.R;
import vn.loitp.core.base.BaseFontActivity;

public class Animation2Activity extends BaseFontActivity implements OnClickListener {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        isShowAdWhenExit = false;
        findViewById(R.id.tv_exit).setOnClickListener(this);
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
        return R.layout.activity_animation_2;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_exit:
                onBackPressed();
                break;
        }
    }
}
