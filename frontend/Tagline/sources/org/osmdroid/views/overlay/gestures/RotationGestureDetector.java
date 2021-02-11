package org.osmdroid.views.overlay.gestures;

import android.view.MotionEvent;

public class RotationGestureDetector {
    private RotationListener mListener;
    protected float mRotation;

    public interface RotationListener {
        void onRotate(float f);
    }

    public RotationGestureDetector(RotationListener listener) {
        this.mListener = listener;
    }

    private static float rotation(MotionEvent motionEvent) {
        MotionEvent event = motionEvent;
        return (float) Math.toDegrees(Math.atan2((double) (event.getY(0) - event.getY(1)), (double) (event.getX(0) - event.getX(1))));
    }

    public void onTouch(MotionEvent motionEvent) {
        MotionEvent e = motionEvent;
        if (e.getPointerCount() == 2) {
            if (e.getActionMasked() == 5) {
                this.mRotation = rotation(e);
            }
            float delta = rotation(e) - this.mRotation;
            this.mRotation += delta;
            this.mListener.onRotate(delta);
        }
    }
}
