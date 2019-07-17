package com.function.expectanim.core.anim3d;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.os.Build;
import android.view.View;

import androidx.annotation.Nullable;

import com.function.expectanim.ViewCalculator;
import com.function.expectanim.core.AnimExpectation;
import com.function.expectanim.core.ExpectAnimManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Christian Ringshofer on 17/02/2017.
 */
public class ExpectAnimCameraDistanceManager extends ExpectAnimManager {

    @Nullable
    private Float mCurrentCameraDistance, mCameraDistance = null;

    public ExpectAnimCameraDistanceManager(List<AnimExpectation> animExpectations, View viewToMove, ViewCalculator viewCalculator) {
        super(animExpectations, viewToMove, viewCalculator);
    }

    @Override
    public void calculate() {
        for (AnimExpectation expectation : animExpectations) {
            if (expectation instanceof CameraDistanceExpectation) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    mCurrentCameraDistance = viewToMove.getCameraDistance();
                }
                final Float cameraDistance = ((CameraDistanceExpectation) expectation).getCalculatedCameraDistance(viewToMove);
                if (cameraDistance != null) mCameraDistance = cameraDistance;
            }
        }
    }

    @Override
    public List<Animator> getAnimators() {
        final List<Animator> animations = new ArrayList<>();
        calculate();
        if (mCameraDistance != null) {
            final ValueAnimator animator = ValueAnimator.ofFloat(mCurrentCameraDistance, mCameraDistance);
            animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator valueAnimator) {
                    viewToMove.setCameraDistance((float) valueAnimator.getAnimatedValue());
                }
            });
            animations.add(animator);
        }
        return animations;
    }

    @Nullable
    public Float getCameraDistance() {
        return mCameraDistance;
    }

}