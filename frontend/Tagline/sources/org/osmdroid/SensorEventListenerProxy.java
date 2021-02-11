package org.osmdroid;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

public class SensorEventListenerProxy implements SensorEventListener {
    private SensorEventListener mListener = null;
    private final SensorManager mSensorManager;

    public SensorEventListenerProxy(SensorManager pSensorManager) {
        this.mSensorManager = pSensorManager;
    }

    public boolean startListening(SensorEventListener sensorEventListener, int pSensorType, int i) {
        SensorEventListener pListener = sensorEventListener;
        int pRate = i;
        Sensor sensor = this.mSensorManager.getDefaultSensor(pSensorType);
        if (sensor == null) {
            return false;
        }
        this.mListener = pListener;
        return this.mSensorManager.registerListener(this, sensor, pRate);
    }

    public void stopListening() {
        this.mListener = null;
        this.mSensorManager.unregisterListener(this);
    }

    public void onAccuracyChanged(Sensor sensor, int i) {
        Sensor pSensor = sensor;
        int pAccuracy = i;
        if (this.mListener != null) {
            this.mListener.onAccuracyChanged(pSensor, pAccuracy);
        }
    }

    public void onSensorChanged(SensorEvent sensorEvent) {
        SensorEvent pEvent = sensorEvent;
        if (this.mListener != null) {
            this.mListener.onSensorChanged(pEvent);
        }
    }
}
