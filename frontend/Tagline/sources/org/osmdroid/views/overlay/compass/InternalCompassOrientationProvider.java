package org.osmdroid.views.overlay.compass;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

public class InternalCompassOrientationProvider implements SensorEventListener, IOrientationProvider {
    private float mAzimuth;
    private IOrientationConsumer mOrientationConsumer;
    private SensorManager mSensorManager;

    public InternalCompassOrientationProvider(Context context) {
        this.mSensorManager = (SensorManager) context.getSystemService("sensor");
    }

    public boolean startOrientationProvider(IOrientationConsumer orientationConsumer) {
        this.mOrientationConsumer = orientationConsumer;
        boolean result = false;
        Sensor sensor = this.mSensorManager.getDefaultSensor(3);
        if (sensor != null) {
            result = this.mSensorManager.registerListener(this, sensor, 2);
        }
        return result;
    }

    public void stopOrientationProvider() {
        this.mOrientationConsumer = null;
        this.mSensorManager.unregisterListener(this);
    }

    public float getLastKnownOrientation() {
        return this.mAzimuth;
    }

    public void destroy() {
        stopOrientationProvider();
        this.mOrientationConsumer = null;
        this.mSensorManager = null;
    }

    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }

    public void onSensorChanged(SensorEvent sensorEvent) {
        SensorEvent event = sensorEvent;
        if (event.sensor.getType() == 3 && event.values != null) {
            this.mAzimuth = event.values[0];
            if (this.mOrientationConsumer != null) {
                this.mOrientationConsumer.onOrientationChanged(this.mAzimuth, this);
            }
        }
    }
}
