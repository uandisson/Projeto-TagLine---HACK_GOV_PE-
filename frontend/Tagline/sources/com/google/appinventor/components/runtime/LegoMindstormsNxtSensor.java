package com.google.appinventor.components.runtime;

import com.google.appinventor.components.annotations.PropertyCategory;
import com.google.appinventor.components.annotations.SimpleObject;
import com.google.appinventor.components.annotations.SimpleProperty;
import com.google.appinventor.components.runtime.util.ErrorMessages;

@SimpleObject
public abstract class LegoMindstormsNxtSensor extends LegoMindstormsNxtBase {
    protected int port;
    private String tOvM9eIUWszKzvISo4zKh7g8MyRaLHuNLQ5NdigAhriBDKCBmghUBEUlM2ZIvV1J;

    public abstract void SensorPort(String str);

    /* access modifiers changed from: protected */
    public abstract void initializeSensor(String str);

    /* renamed from: com.google.appinventor.components.runtime.LegoMindstormsNxtSensor$a */
    static class C0825a<T> {
        final boolean e1IHYfRNckEvpOWbFvMtuN7w9PEpZtVYShhIlzbQR8mwSxiOizA6OYtX7vMfGCUT;
        final T qPeHJnCLP0dAOwDPeFIn82vcSIsCMh6KFFn3o4kyIe0RhQKOQXDeyY2LFwPu2GbE;

        C0825a(boolean z, T t) {
            this.e1IHYfRNckEvpOWbFvMtuN7w9PEpZtVYShhIlzbQR8mwSxiOizA6OYtX7vMfGCUT = z;
            this.qPeHJnCLP0dAOwDPeFIn82vcSIsCMh6KFFn3o4kyIe0RhQKOQXDeyY2LFwPu2GbE = t;
        }
    }

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    protected LegoMindstormsNxtSensor(ComponentContainer componentContainer, String str) {
        super(componentContainer, str);
    }

    @SimpleProperty(category = PropertyCategory.BEHAVIOR, description = "The sensor port that the sensor is connected to.", userVisible = false)
    public String SensorPort() {
        return this.tOvM9eIUWszKzvISo4zKh7g8MyRaLHuNLQ5NdigAhriBDKCBmghUBEUlM2ZIvV1J;
    }

    /* access modifiers changed from: protected */
    public final void setSensorPort(String str) {
        String str2 = str;
        String str3 = "SensorPort";
        try {
            int convertSensorPortLetterToNumber = convertSensorPortLetterToNumber(str2);
            this.tOvM9eIUWszKzvISo4zKh7g8MyRaLHuNLQ5NdigAhriBDKCBmghUBEUlM2ZIvV1J = str2;
            this.port = convertSensorPortLetterToNumber;
            if (this.bluetooth != null && this.bluetooth.IsConnected()) {
                initializeSensor(str3);
            }
        } catch (IllegalArgumentException e) {
            this.form.dispatchErrorOccurredEvent(this, str3, ErrorMessages.ERROR_NXT_INVALID_SENSOR_PORT, str2);
        }
    }

    public void afterConnect(BluetoothConnectionBase bluetoothConnectionBase) {
        BluetoothConnectionBase bluetoothConnectionBase2 = bluetoothConnectionBase;
        initializeSensor("Connect");
    }
}
