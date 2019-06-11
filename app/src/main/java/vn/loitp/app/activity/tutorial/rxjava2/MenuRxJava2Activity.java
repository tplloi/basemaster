package vn.loitp.app.activity.tutorial.rxjava2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import loitp.basemaster.R;
import vn.loitp.core.base.BaseFontActivity;
import vn.loitp.core.utilities.LActivityUtil;

//https://github.com/amitshekhariitbhu/RxJava2-Android-Samples
public class MenuRxJava2Activity extends BaseFontActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        findViewById(R.id.bt_0).setOnClickListener(this);
        findViewById(R.id.bt_1).setOnClickListener(this);
        findViewById(R.id.bt_2).setOnClickListener(this);
        findViewById(R.id.bt_3).setOnClickListener(this);
        findViewById(R.id.bt_4).setOnClickListener(this);
        findViewById(R.id.bt_5).setOnClickListener(this);
        findViewById(R.id.bt_00).setOnClickListener(this);
        findViewById(R.id.bt_test_rx).setOnClickListener(this);
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
        return R.layout.activity_menu_rx_java2;
    }

    @Override
    public void onClick(View v) {
        Intent intent = null;
        switch (v.getId()) {
            case R.id.bt_0:
                intent = new Intent(activity, DisposableExampleActivity.class);
                break;
            case R.id.bt_1:
                intent = new Intent(activity, FlowableExampleActivity.class);
                break;
            case R.id.bt_2:
                intent = new Intent(activity, IntervalExampleActivity.class);
                break;
            case R.id.bt_3:
                intent = new Intent(activity, SingleObserverExampleActivity.class);
                break;
            case R.id.bt_4:
                intent = new Intent(activity, CompletableObserverExampleActivity.class);
                break;
            case R.id.bt_5:
                intent = new Intent(activity, MapExampleActivity.class);
                break;
            case R.id.bt_00:
                intent = new Intent(activity, AsyncTaskRxActivity.class);
                break;
            case R.id.bt_test_rx:
                intent = new Intent(activity, TestRxActivity.class);
                break;
        }
        if (intent != null) {
            startActivity(intent);
            LActivityUtil.tranIn(activity);
        }
    }
}