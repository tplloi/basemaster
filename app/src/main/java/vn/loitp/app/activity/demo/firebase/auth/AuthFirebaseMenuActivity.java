package vn.loitp.app.activity.demo.firebase.auth;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.core.base.BaseFontActivity;
import com.core.utilities.LActivityUtil;

import loitp.basemaster.R;

//https://github.com/firebase/quickstart-android
public class AuthFirebaseMenuActivity extends BaseFontActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        findViewById(R.id.bt_gg).setOnClickListener(this);
        findViewById(R.id.bt_fb).setOnClickListener(this);
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
        return R.layout.activity_auth_firebase;
    }

    @Override
    public void onClick(View v) {
        Intent intent = null;
        switch (v.getId()) {
            case R.id.bt_gg:
                intent = new Intent(getActivity(), AuthFirebaseGoogleActivity.class);
                break;
            case R.id.bt_fb:
                intent = new Intent(getActivity(), AuthFirebaseFacebookActivity.class);
                break;
        }
        if (intent != null) {
            startActivity(intent);
            LActivityUtil.INSTANCE.tranIn(getActivity());
        }
    }
}
