package vn.loitp.app.activity.customviews.layout.dragueur;

import android.os.Bundle;
import android.widget.TextView;

import loitp.basemaster.R;
import vn.loitp.core.base.BaseFontActivity;
import vn.loitp.core.utilities.LLog;
import vn.loitp.views.layout.dragueur.Direction;
import vn.loitp.views.layout.dragueur.DraggableView;

//https://github.com/Meetic/Dragueur?utm_source=android-arsenal.com&utm_medium=referral&utm_campaign=3534
public class DragueurActivity extends BaseFontActivity {
    private DraggableView draggableView;
    private TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        draggableView = (DraggableView) findViewById(R.id.dragueur);
        tv = (TextView) findViewById(R.id.tv);
        draggableView.setRotationEnabled(true);
        //draggableView.setAnimating(true);
        draggableView.setRotationValue(10f);
        //draggableView.setDraggable(true);
        draggableView.setDragListener(new DraggableView.DraggableViewListener() {
            @Override
            public void onDrag(DraggableView draggableView, float percentX, float percentY) {
                setText("draggableView: " + percentX + " - " + percentY);
                LLog.INSTANCE.d(TAG, "onDrag " + percentX + " x " + percentY);
            }

            @Override
            public void onDraggedStarted(DraggableView draggableView, Direction direction) {
                setText("onDraggedStarted");
                LLog.INSTANCE.d(TAG, "onDraggedStarted " + direction.name());
            }

            @Override
            public void onDraggedEnded(DraggableView draggableView, Direction direction) {
                setText("onDraggedEnded");
                LLog.INSTANCE.d(TAG, "onDraggedEnded " + direction.name());
            }

            @Override
            public void onDragCancelled(DraggableView draggableView) {
                setText("onDragCancelled");
            }
        });
        /*draggableView.setViewAnimator(new ViewAnimator() {
            @Override
            public boolean animateExit(@NonNull DraggableView draggableView, Direction direction, int duration) {
                return false;
            }

            @Override
            public boolean animateToOrigin(@NonNull DraggableView draggableView, int duration) {
                return false;
            }

            @Override
            public void update(@NonNull DraggableView draggableView, float percentX, float percentY) {
                //do nothing
            }
        });*/
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
        return R.layout.activity_dragueur;
    }

    private void setText(String s) {
        tv.setText("Dragueur can move any view with one finger ;)\n" + s);
    }
}
