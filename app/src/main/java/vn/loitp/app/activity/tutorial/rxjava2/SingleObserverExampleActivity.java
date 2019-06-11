package vn.loitp.app.activity.tutorial.rxjava2;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.disposables.Disposable;
import loitp.basemaster.R;
import vn.loitp.core.base.BaseFontActivity;
import vn.loitp.core.utilities.LLog;

//https://github.com/amitshekhariitbhu/RxJava2-Android-Samples
public class SingleObserverExampleActivity extends BaseFontActivity {
    Button btn;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        btn = findViewById(R.id.btn);
        textView = findViewById(R.id.textView);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                doSomeWork();
            }
        });
    }

    /*
     * simple example using SingleObserver
     */
    private void doSomeWork() {
        Single.just("Amit").subscribe(getSingleObserver());
    }

    private SingleObserver<String> getSingleObserver() {
        return new SingleObserver<String>() {
            @Override
            public void onSubscribe(Disposable d) {
                LLog.INSTANCE.d(TAG, " onSubscribe : " + d.isDisposed());
            }

            @Override
            public void onSuccess(String value) {
                textView.append(" onNext : value : " + value + "\n");
                LLog.INSTANCE.d(TAG, " onNext value : " + value);
            }

            @Override
            public void onError(Throwable e) {
                textView.append(" onError : " + e.getMessage() + "\n");
                LLog.INSTANCE.d(TAG, " onError : " + e.getMessage());
            }
        };
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
        return R.layout.activity_rxjava2_flowable;
    }
}