package org.metalev.multitouch.controller;

import android.util.Log;
import android.view.MotionEvent;
import java.lang.reflect.Method;

public class MultiTouchController<T> {
    private static int ACTION_POINTER_INDEX_SHIFT = 0;
    private static int ACTION_POINTER_UP = 0;
    public static final boolean DEBUG = false;
    private static final long EVENT_SETTLE_TIME_INTERVAL = 20;
    private static final float MAX_MULTITOUCH_DIM_JUMP_SIZE = 40.0f;
    private static final float MAX_MULTITOUCH_POS_JUMP_SIZE = 30.0f;
    public static final int MAX_TOUCH_POINTS = 20;
    private static final float MIN_MULTITOUCH_SEPARATION = 30.0f;
    private static final int MODE_DRAG = 1;
    private static final int MODE_NOTHING = 0;
    private static final int MODE_PINCH = 2;
    private static Method m_getHistoricalPressure;
    private static Method m_getHistoricalX;
    private static Method m_getHistoricalY;
    private static Method m_getPointerCount;
    private static Method m_getPointerId;
    private static Method m_getPressure;
    private static Method m_getX;
    private static Method m_getY;
    public static final boolean multiTouchSupported;
    private static final int[] pointerIds = new int[20];
    private static final float[] pressureVals = new float[20];
    private static final float[] xVals = new float[20];
    private static final float[] yVals = new float[20];
    private boolean handleSingleTouchEvents;
    private PointInfo mCurrPt;
    private float mCurrPtAng;
    private float mCurrPtDiam;
    private float mCurrPtHeight;
    private float mCurrPtWidth;
    private float mCurrPtX;
    private float mCurrPtY;
    private PositionAndScale mCurrXform;
    private int mMode;
    private PointInfo mPrevPt;
    private long mSettleEndTime;
    private long mSettleStartTime;
    MultiTouchObjectCanvas<T> objectCanvas;
    private T selectedObject;
    private float startAngleMinusPinchAngle;
    private float startPosX;
    private float startPosY;
    private float startScaleOverPinchDiam;
    private float startScaleXOverPinchWidth;
    private float startScaleYOverPinchHeight;

    public interface MultiTouchObjectCanvas<T> {
        T getDraggableObjectAtPoint(PointInfo pointInfo);

        void getPositionAndScale(T t, PositionAndScale positionAndScale);

        void selectObject(T t, PointInfo pointInfo);

        boolean setPositionAndScale(T t, PositionAndScale positionAndScale, PointInfo pointInfo);
    }

    private void extractCurrPtInfo() {
        this.mCurrPtX = this.mCurrPt.getX();
        this.mCurrPtY = this.mCurrPt.getY();
        this.mCurrPtDiam = Math.max(21.3f, !this.mCurrXform.updateScale ? 0.0f : this.mCurrPt.getMultiTouchDiameter());
        this.mCurrPtWidth = Math.max(30.0f, !this.mCurrXform.updateScaleXY ? 0.0f : this.mCurrPt.getMultiTouchWidth());
        this.mCurrPtHeight = Math.max(30.0f, !this.mCurrXform.updateScaleXY ? 0.0f : this.mCurrPt.getMultiTouchHeight());
        this.mCurrPtAng = !this.mCurrXform.updateAngle ? 0.0f : this.mCurrPt.getMultiTouchAngle();
    }

    /* JADX INFO: this call moved to the top of the method (can break code semantics) */
    public MultiTouchController(MultiTouchObjectCanvas<T> objectCanvas2) {
        this(objectCanvas2, true);
    }

    public MultiTouchController(MultiTouchObjectCanvas<T> objectCanvas2, boolean handleSingleTouchEvents2) {
        PositionAndScale positionAndScale;
        PointInfo pointInfo;
        PointInfo pointInfo2;
        this.selectedObject = null;
        new PositionAndScale();
        this.mCurrXform = positionAndScale;
        this.mMode = 0;
        new PointInfo();
        this.mCurrPt = pointInfo;
        new PointInfo();
        this.mPrevPt = pointInfo2;
        this.handleSingleTouchEvents = handleSingleTouchEvents2;
        this.objectCanvas = objectCanvas2;
    }

    /* access modifiers changed from: protected */
    public void setHandleSingleTouchEvents(boolean handleSingleTouchEvents2) {
        boolean z = handleSingleTouchEvents2;
        this.handleSingleTouchEvents = z;
    }

    /* access modifiers changed from: protected */
    public boolean getHandleSingleTouchEvents() {
        return this.handleSingleTouchEvents;
    }

    static {
        ACTION_POINTER_UP = 6;
        ACTION_POINTER_INDEX_SHIFT = 8;
        boolean succeeded = false;
        try {
            m_getPointerCount = MotionEvent.class.getMethod("getPointerCount", new Class[0]);
            m_getPointerId = MotionEvent.class.getMethod("getPointerId", new Class[]{Integer.TYPE});
            m_getPressure = MotionEvent.class.getMethod("getPressure", new Class[]{Integer.TYPE});
            Class[] clsArr = new Class[2];
            clsArr[0] = Integer.TYPE;
            Class[] clsArr2 = clsArr;
            clsArr2[1] = Integer.TYPE;
            m_getHistoricalX = MotionEvent.class.getMethod("getHistoricalX", clsArr2);
            Class[] clsArr3 = new Class[2];
            clsArr3[0] = Integer.TYPE;
            Class[] clsArr4 = clsArr3;
            clsArr4[1] = Integer.TYPE;
            m_getHistoricalY = MotionEvent.class.getMethod("getHistoricalY", clsArr4);
            Class[] clsArr5 = new Class[2];
            clsArr5[0] = Integer.TYPE;
            Class[] clsArr6 = clsArr5;
            clsArr6[1] = Integer.TYPE;
            m_getHistoricalPressure = MotionEvent.class.getMethod("getHistoricalPressure", clsArr6);
            m_getX = MotionEvent.class.getMethod("getX", new Class[]{Integer.TYPE});
            m_getY = MotionEvent.class.getMethod("getY", new Class[]{Integer.TYPE});
            succeeded = true;
        } catch (Exception e) {
            int e2 = Log.e("MultiTouchController", "static initializer failed", e);
        }
        multiTouchSupported = succeeded;
        if (multiTouchSupported) {
            try {
                ACTION_POINTER_UP = MotionEvent.class.getField("ACTION_POINTER_UP").getInt((Object) null);
                ACTION_POINTER_INDEX_SHIFT = MotionEvent.class.getField("ACTION_POINTER_INDEX_SHIFT").getInt((Object) null);
            } catch (Exception e3) {
                Exception exc = e3;
            }
        }
    }

    public boolean onTouchEvent(MotionEvent motionEvent) {
        float pressure;
        Object invoke;
        Object invoke2;
        Object invoke3;
        MotionEvent event = motionEvent;
        try {
            int pointerCount = multiTouchSupported ? ((Integer) m_getPointerCount.invoke(event, new Object[0])).intValue() : 1;
            if (this.mMode == 0 && !this.handleSingleTouchEvents && pointerCount == 1) {
                return false;
            }
            int action = event.getAction();
            int histLen = event.getHistorySize() / pointerCount;
            int histIdx = 0;
            while (histIdx <= histLen) {
                boolean processingHist = histIdx < histLen;
                if (!multiTouchSupported || pointerCount == 1) {
                    xVals[0] = processingHist ? event.getHistoricalX(histIdx) : event.getX();
                    yVals[0] = processingHist ? event.getHistoricalY(histIdx) : event.getY();
                    float[] fArr = pressureVals;
                    if (processingHist) {
                        pressure = event.getHistoricalPressure(histIdx);
                    } else {
                        pressure = event.getPressure();
                    }
                    fArr[0] = pressure;
                } else {
                    int numPointers = Math.min(pointerCount, 20);
                    for (int ptrIdx = 0; ptrIdx < numPointers; ptrIdx++) {
                        pointerIds[ptrIdx] = ((Integer) m_getPointerId.invoke(event, new Object[]{Integer.valueOf(ptrIdx)})).intValue();
                        float[] fArr2 = xVals;
                        int i = ptrIdx;
                        if (processingHist) {
                            Object[] objArr = new Object[2];
                            objArr[0] = Integer.valueOf(ptrIdx);
                            Object[] objArr2 = objArr;
                            objArr2[1] = Integer.valueOf(histIdx);
                            invoke = m_getHistoricalX.invoke(event, objArr2);
                        } else {
                            invoke = m_getX.invoke(event, new Object[]{Integer.valueOf(ptrIdx)});
                        }
                        fArr2[i] = ((Float) invoke).floatValue();
                        float[] fArr3 = yVals;
                        int i2 = ptrIdx;
                        if (processingHist) {
                            Object[] objArr3 = new Object[2];
                            objArr3[0] = Integer.valueOf(ptrIdx);
                            Object[] objArr4 = objArr3;
                            objArr4[1] = Integer.valueOf(histIdx);
                            invoke2 = m_getHistoricalY.invoke(event, objArr4);
                        } else {
                            invoke2 = m_getY.invoke(event, new Object[]{Integer.valueOf(ptrIdx)});
                        }
                        fArr3[i2] = ((Float) invoke2).floatValue();
                        float[] fArr4 = pressureVals;
                        int i3 = ptrIdx;
                        if (processingHist) {
                            Object[] objArr5 = new Object[2];
                            objArr5[0] = Integer.valueOf(ptrIdx);
                            Object[] objArr6 = objArr5;
                            objArr6[1] = Integer.valueOf(histIdx);
                            invoke3 = m_getHistoricalPressure.invoke(event, objArr6);
                        } else {
                            invoke3 = m_getPressure.invoke(event, new Object[]{Integer.valueOf(ptrIdx)});
                        }
                        fArr4[i3] = ((Float) invoke3).floatValue();
                    }
                }
                decodeTouchEvent(pointerCount, xVals, yVals, pressureVals, pointerIds, processingHist ? 2 : action, processingHist ? true : (action == 1 || (action & ((1 << ACTION_POINTER_INDEX_SHIFT) + -1)) == ACTION_POINTER_UP || action == 3) ? false : true, processingHist ? event.getHistoricalEventTime(histIdx) : event.getEventTime());
                histIdx++;
            }
            return true;
        } catch (Exception e) {
            int e2 = Log.e("MultiTouchController", "onTouchEvent() failed", e);
            return false;
        }
    }

    private void decodeTouchEvent(int pointerCount, float[] x, float[] y, float[] pressure, int[] pointerIds2, int action, boolean down, long eventTime) {
        PointInfo tmp = this.mPrevPt;
        this.mPrevPt = this.mCurrPt;
        this.mCurrPt = tmp;
        this.mCurrPt.set(pointerCount, x, y, pressure, pointerIds2, action, down, eventTime);
        multiTouchController();
    }

    private void anchorAtThisPositionAndScale() {
        if (this.selectedObject != null) {
            this.objectCanvas.getPositionAndScale(this.selectedObject, this.mCurrXform);
            float currScaleInv = 1.0f / (!this.mCurrXform.updateScale ? 1.0f : this.mCurrXform.scale == 0.0f ? 1.0f : this.mCurrXform.scale);
            extractCurrPtInfo();
            this.startPosX = (this.mCurrPtX - this.mCurrXform.xOff) * currScaleInv;
            this.startPosY = (this.mCurrPtY - this.mCurrXform.yOff) * currScaleInv;
            this.startScaleOverPinchDiam = this.mCurrXform.scale / this.mCurrPtDiam;
            this.startScaleXOverPinchWidth = this.mCurrXform.scaleX / this.mCurrPtWidth;
            this.startScaleYOverPinchHeight = this.mCurrXform.scaleY / this.mCurrPtHeight;
            this.startAngleMinusPinchAngle = this.mCurrXform.angle - this.mCurrPtAng;
        }
    }

    private void performDragOrPinch() {
        if (this.selectedObject != null) {
            float currScale = !this.mCurrXform.updateScale ? 1.0f : this.mCurrXform.scale == 0.0f ? 1.0f : this.mCurrXform.scale;
            extractCurrPtInfo();
            this.mCurrXform.set(this.mCurrPtX - (this.startPosX * currScale), this.mCurrPtY - (this.startPosY * currScale), this.startScaleOverPinchDiam * this.mCurrPtDiam, this.startScaleXOverPinchWidth * this.mCurrPtWidth, this.startScaleYOverPinchHeight * this.mCurrPtHeight, this.startAngleMinusPinchAngle + this.mCurrPtAng);
            if (!this.objectCanvas.setPositionAndScale(this.selectedObject, this.mCurrXform, this.mCurrPt)) {
            }
        }
    }

    public boolean isPinching() {
        return this.mMode == 2;
    }

    private void multiTouchController() {
        switch (this.mMode) {
            case 0:
                if (this.mCurrPt.isDown()) {
                    this.selectedObject = this.objectCanvas.getDraggableObjectAtPoint(this.mCurrPt);
                    if (this.selectedObject != null) {
                        this.mMode = 1;
                        this.objectCanvas.selectObject(this.selectedObject, this.mCurrPt);
                        anchorAtThisPositionAndScale();
                        long eventTime = this.mCurrPt.getEventTime();
                        this.mSettleEndTime = eventTime;
                        this.mSettleStartTime = eventTime;
                        return;
                    }
                    return;
                }
                return;
            case 1:
                if (!this.mCurrPt.isDown()) {
                    this.mMode = 0;
                    MultiTouchObjectCanvas<T> multiTouchObjectCanvas = this.objectCanvas;
                    this.selectedObject = null;
                    multiTouchObjectCanvas.selectObject(null, this.mCurrPt);
                    return;
                } else if (this.mCurrPt.isMultiTouch()) {
                    this.mMode = 2;
                    anchorAtThisPositionAndScale();
                    this.mSettleStartTime = this.mCurrPt.getEventTime();
                    this.mSettleEndTime = this.mSettleStartTime + EVENT_SETTLE_TIME_INTERVAL;
                    return;
                } else if (this.mCurrPt.getEventTime() < this.mSettleEndTime) {
                    anchorAtThisPositionAndScale();
                    return;
                } else {
                    performDragOrPinch();
                    return;
                }
            case 2:
                if (!this.mCurrPt.isMultiTouch() || !this.mCurrPt.isDown()) {
                    if (!this.mCurrPt.isDown()) {
                        this.mMode = 0;
                        MultiTouchObjectCanvas<T> multiTouchObjectCanvas2 = this.objectCanvas;
                        this.selectedObject = null;
                        multiTouchObjectCanvas2.selectObject(null, this.mCurrPt);
                        return;
                    }
                    this.mMode = 1;
                    anchorAtThisPositionAndScale();
                    this.mSettleStartTime = this.mCurrPt.getEventTime();
                    this.mSettleEndTime = this.mSettleStartTime + EVENT_SETTLE_TIME_INTERVAL;
                    return;
                } else if (Math.abs(this.mCurrPt.getX() - this.mPrevPt.getX()) > 30.0f || Math.abs(this.mCurrPt.getY() - this.mPrevPt.getY()) > 30.0f || Math.abs(this.mCurrPt.getMultiTouchWidth() - this.mPrevPt.getMultiTouchWidth()) * 0.5f > MAX_MULTITOUCH_DIM_JUMP_SIZE || Math.abs(this.mCurrPt.getMultiTouchHeight() - this.mPrevPt.getMultiTouchHeight()) * 0.5f > MAX_MULTITOUCH_DIM_JUMP_SIZE) {
                    anchorAtThisPositionAndScale();
                    this.mSettleStartTime = this.mCurrPt.getEventTime();
                    this.mSettleEndTime = this.mSettleStartTime + EVENT_SETTLE_TIME_INTERVAL;
                    return;
                } else if (this.mCurrPt.eventTime < this.mSettleEndTime) {
                    anchorAtThisPositionAndScale();
                    return;
                } else {
                    performDragOrPinch();
                    return;
                }
            default:
                return;
        }
    }

    public int getMode() {
        return this.mMode;
    }

    public static class PointInfo {
        private int action;
        private float angle;
        private boolean angleIsCalculated;
        private float diameter;
        private boolean diameterIsCalculated;
        private float diameterSq;
        private boolean diameterSqIsCalculated;

        /* renamed from: dx */
        private float f520dx;

        /* renamed from: dy */
        private float f521dy;
        /* access modifiers changed from: private */
        public long eventTime;
        private boolean isDown;
        private boolean isMultiTouch;
        private int numPoints;
        private int[] pointerIds = new int[20];
        private float pressureMid;
        private float[] pressures = new float[20];
        private float xMid;

        /* renamed from: xs */
        private float[] f522xs = new float[20];
        private float yMid;

        /* renamed from: ys */
        private float[] f523ys = new float[20];

        public PointInfo() {
        }

        /* access modifiers changed from: private */
        public void set(int i, float[] fArr, float[] fArr2, float[] fArr3, int[] iArr, int action2, boolean z, long eventTime2) {
            int numPoints2 = i;
            float[] x = fArr;
            float[] y = fArr2;
            float[] pressure = fArr3;
            int[] pointerIds2 = iArr;
            boolean isDown2 = z;
            this.eventTime = eventTime2;
            this.action = action2;
            this.numPoints = numPoints2;
            for (int i2 = 0; i2 < numPoints2; i2++) {
                this.f522xs[i2] = x[i2];
                this.f523ys[i2] = y[i2];
                this.pressures[i2] = pressure[i2];
                this.pointerIds[i2] = pointerIds2[i2];
            }
            this.isDown = isDown2;
            this.isMultiTouch = numPoints2 >= 2;
            if (this.isMultiTouch) {
                this.xMid = (x[0] + x[1]) * 0.5f;
                this.yMid = (y[0] + y[1]) * 0.5f;
                this.pressureMid = (pressure[0] + pressure[1]) * 0.5f;
                this.f520dx = Math.abs(x[1] - x[0]);
                this.f521dy = Math.abs(y[1] - y[0]);
            } else {
                this.xMid = x[0];
                this.yMid = y[0];
                this.pressureMid = pressure[0];
                this.f521dy = 0.0f;
                this.f520dx = 0.0f;
            }
            this.angleIsCalculated = false;
            this.diameterIsCalculated = false;
            this.diameterSqIsCalculated = false;
        }

        public void set(PointInfo pointInfo) {
            PointInfo other = pointInfo;
            this.numPoints = other.numPoints;
            for (int i = 0; i < this.numPoints; i++) {
                this.f522xs[i] = other.f522xs[i];
                this.f523ys[i] = other.f523ys[i];
                this.pressures[i] = other.pressures[i];
                this.pointerIds[i] = other.pointerIds[i];
            }
            this.xMid = other.xMid;
            this.yMid = other.yMid;
            this.pressureMid = other.pressureMid;
            this.f520dx = other.f520dx;
            this.f521dy = other.f521dy;
            this.diameter = other.diameter;
            this.diameterSq = other.diameterSq;
            this.angle = other.angle;
            this.isDown = other.isDown;
            this.action = other.action;
            this.isMultiTouch = other.isMultiTouch;
            this.diameterIsCalculated = other.diameterIsCalculated;
            this.diameterSqIsCalculated = other.diameterSqIsCalculated;
            this.angleIsCalculated = other.angleIsCalculated;
            this.eventTime = other.eventTime;
        }

        public boolean isMultiTouch() {
            return this.isMultiTouch;
        }

        public float getMultiTouchWidth() {
            return this.isMultiTouch ? this.f520dx : 0.0f;
        }

        public float getMultiTouchHeight() {
            return this.isMultiTouch ? this.f521dy : 0.0f;
        }

        private int julery_isqrt(int i) {
            int i2;
            int val = i;
            int g = 0;
            int b = 32768;
            int bshft = 15;
            do {
                int i3 = bshft;
                bshft--;
                int i4 = ((g << 1) + b) << i3;
                int temp = i4;
                if (val >= i4) {
                    g += b;
                    val -= temp;
                }
                i2 = b >> 1;
                b = i2;
            } while (i2 > 0);
            return g;
        }

        public float getMultiTouchDiameterSq() {
            if (!this.diameterSqIsCalculated) {
                this.diameterSq = this.isMultiTouch ? (this.f520dx * this.f520dx) + (this.f521dy * this.f521dy) : 0.0f;
                this.diameterSqIsCalculated = true;
            }
            return this.diameterSq;
        }

        public float getMultiTouchDiameter() {
            if (!this.diameterIsCalculated) {
                if (!this.isMultiTouch) {
                    this.diameter = 0.0f;
                } else {
                    float diamSq = getMultiTouchDiameterSq();
                    this.diameter = diamSq == 0.0f ? 0.0f : ((float) julery_isqrt((int) (256.0f * diamSq))) / 16.0f;
                    if (this.diameter < this.f520dx) {
                        this.diameter = this.f520dx;
                    }
                    if (this.diameter < this.f521dy) {
                        this.diameter = this.f521dy;
                    }
                }
                this.diameterIsCalculated = true;
            }
            return this.diameter;
        }

        public float getMultiTouchAngle() {
            if (!this.angleIsCalculated) {
                if (!this.isMultiTouch) {
                    this.angle = 0.0f;
                } else {
                    this.angle = (float) Math.atan2((double) (this.f523ys[1] - this.f523ys[0]), (double) (this.f522xs[1] - this.f522xs[0]));
                }
                this.angleIsCalculated = true;
            }
            return this.angle;
        }

        public int getNumTouchPoints() {
            return this.numPoints;
        }

        public float getX() {
            return this.xMid;
        }

        public float[] getXs() {
            return this.f522xs;
        }

        public float getY() {
            return this.yMid;
        }

        public float[] getYs() {
            return this.f523ys;
        }

        public int[] getPointerIds() {
            return this.pointerIds;
        }

        public float getPressure() {
            return this.pressureMid;
        }

        public float[] getPressures() {
            return this.pressures;
        }

        public boolean isDown() {
            return this.isDown;
        }

        public int getAction() {
            return this.action;
        }

        public long getEventTime() {
            return this.eventTime;
        }
    }

    public static class PositionAndScale {
        /* access modifiers changed from: private */
        public float angle;
        /* access modifiers changed from: private */
        public float scale;
        /* access modifiers changed from: private */
        public float scaleX;
        /* access modifiers changed from: private */
        public float scaleY;
        /* access modifiers changed from: private */
        public boolean updateAngle;
        /* access modifiers changed from: private */
        public boolean updateScale;
        /* access modifiers changed from: private */
        public boolean updateScaleXY;
        /* access modifiers changed from: private */
        public float xOff;
        /* access modifiers changed from: private */
        public float yOff;

        public PositionAndScale() {
        }

        public void set(float xOff2, float yOff2, boolean updateScale2, float f, boolean z, float f2, float f3, boolean z2, float f4) {
            float scale2 = f;
            boolean updateScaleXY2 = z;
            float scaleX2 = f2;
            float scaleY2 = f3;
            boolean updateAngle2 = z2;
            float angle2 = f4;
            this.xOff = xOff2;
            this.yOff = yOff2;
            this.updateScale = updateScale2;
            this.scale = scale2 == 0.0f ? 1.0f : scale2;
            this.updateScaleXY = updateScaleXY2;
            this.scaleX = scaleX2 == 0.0f ? 1.0f : scaleX2;
            this.scaleY = scaleY2 == 0.0f ? 1.0f : scaleY2;
            this.updateAngle = updateAngle2;
            this.angle = angle2;
        }

        /* access modifiers changed from: protected */
        public void set(float xOff2, float yOff2, float f, float f2, float f3, float f4) {
            float scale2 = f;
            float scaleX2 = f2;
            float scaleY2 = f3;
            float angle2 = f4;
            this.xOff = xOff2;
            this.yOff = yOff2;
            this.scale = scale2 == 0.0f ? 1.0f : scale2;
            this.scaleX = scaleX2 == 0.0f ? 1.0f : scaleX2;
            this.scaleY = scaleY2 == 0.0f ? 1.0f : scaleY2;
            this.angle = angle2;
        }

        public float getXOff() {
            return this.xOff;
        }

        public float getYOff() {
            return this.yOff;
        }

        public float getScale() {
            return !this.updateScale ? 1.0f : this.scale;
        }

        public float getScaleX() {
            return !this.updateScaleXY ? 1.0f : this.scaleX;
        }

        public float getScaleY() {
            return !this.updateScaleXY ? 1.0f : this.scaleY;
        }

        public float getAngle() {
            return !this.updateAngle ? 0.0f : this.angle;
        }
    }
}
