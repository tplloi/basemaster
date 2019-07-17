package vn.loitp.app.activity.function.gesto;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.core.base.BaseFontActivity;
import com.function.gesto.OnGestureListener;

import loitp.basemaster.R;

public class GestoActivity extends BaseFontActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ImageView iv = (ImageView) findViewById(R.id.iv);
        TextView tv = (TextView) findViewById(R.id.tv);
        iv.setOnTouchListener(new OnGestureListener(this) {
                                  @Override
                                  public void onSwipeRight() {
                                      super.onSwipeRight();
                                      tv.setText("onSwipeRight");
                                  }

                                  @Override
                                  public void onSwipeLeft() {
                                      super.onSwipeLeft();
                                      tv.setText("onSwipeLeft");
                                  }

                                  @Override
                                  public void onSwipeTop() {
                                      super.onSwipeTop();
                                      tv.setText("onSwipeTop");
                                  }

                                  @Override
                                  public void onSwipeBottom() {
                                      super.onSwipeBottom();
                                      tv.setText("onSwipeBottom");
                                  }

                                  @Override
                                  public void onClick() {
                                      super.onClick();
                                      tv.setText("onClick");
                                  }

                                  @Override
                                  public void onDoubleClick() {
                                      super.onDoubleClick();
                                      tv.setText("onDoubleClick");
                                  }

                                  @Override
                                  public void onLongClick() {
                                      super.onLongClick();
                                      tv.setText("onLongClick");
                                  }
                              }

        );
        //To remove gesture listener
        //view.setOnTouchListener(null);
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
        return R.layout.activity_gesto;
    }
}
