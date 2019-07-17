package vn.loitp.app.activity.demo.texttospeech;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import com.core.base.BaseFontActivity;
import com.core.utilities.LTextToSpeechUtil;

import loitp.basemaster.R;

public class TextToSpeechActivity extends BaseFontActivity implements OnClickListener {
    private EditText etType;
    private Button btSpeak;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LTextToSpeechUtil.Companion.getInstance().setupTTS(this);
        findViewById(R.id.bt_i_love_you).setOnClickListener(this);
        findViewById(R.id.bt_i_you_love_me).setOnClickListener(this);

        etType = (EditText) findViewById(R.id.et_type);
        btSpeak = (Button) findViewById(R.id.bt_speak);
        btSpeak.setOnClickListener(this);

        etType.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                //do nothing
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (etType.getText().toString().isEmpty()) {
                    btSpeak.setVisibility(View.GONE);
                } else {
                    btSpeak.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                //do nothing
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
        return R.layout.activity_text_to_speech;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_i_love_you:
                LTextToSpeechUtil.Companion.getInstance().speakOut("I love you");
                break;
            case R.id.bt_i_you_love_me:
                LTextToSpeechUtil.Companion.getInstance().speakOut("You love me");
                break;
            case R.id.bt_speak:
                LTextToSpeechUtil.Companion.getInstance().speakOut(etType.getText().toString());
                break;
        }
    }

    @Override
    protected void onDestroy() {
        LTextToSpeechUtil.Companion.getInstance().destroy();
        super.onDestroy();
    }
}
