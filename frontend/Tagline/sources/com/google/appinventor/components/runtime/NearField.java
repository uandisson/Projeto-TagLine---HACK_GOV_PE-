package com.google.appinventor.components.runtime;

import android.app.Activity;
import android.content.Intent;
import android.nfc.NfcAdapter;
import android.os.Build;
import android.util.Log;
import com.google.appinventor.components.annotations.DesignerComponent;
import com.google.appinventor.components.annotations.DesignerProperty;
import com.google.appinventor.components.annotations.PropertyCategory;
import com.google.appinventor.components.annotations.SimpleEvent;
import com.google.appinventor.components.annotations.SimpleObject;
import com.google.appinventor.components.annotations.SimpleProperty;
import com.google.appinventor.components.annotations.UsesPermissions;
import com.google.appinventor.components.common.ComponentCategory;
import com.google.appinventor.components.runtime.util.Ev3Constants;
import com.google.appinventor.components.runtime.util.GingerbreadUtil;

@SimpleObject
@DesignerComponent(category = ComponentCategory.SENSORS, description = "<p>Non-visible component to provide NFC capabilities.  For now this component supports the reading and writing of text tags only (if supported by the device)</p><p>In order to read and write text tags, the component must have its <code>ReadMode</code> property set to True or False respectively.</p><p><strong>Note:</strong> This component will only work on Screen1 of any App Inventor app.</p>", iconName = "images/nearfield.png", nonVisible = true, version = 1)
@UsesPermissions(permissionNames = "android.permission.NFC")
public class NearField extends AndroidNonvisibleComponent implements Deleteable, OnNewIntentListener, OnPauseListener, OnResumeListener, OnStopListener {
    private boolean LqPkE8nw2txhYsROtdyVVn8eaaz2Snf5qPRDJl9Xd4fWH3FTDxRDNp0V8h7hgt8B = true;
    private int Ta6bjdQXoFeEb44hWQ7kTTTXw2rT1LHw6UX7lms7WU7H0AkpETH9D9EhELUfywQi;
    private Activity activity;
    private NfcAdapter hxYOFxFjLpN1maJuWNxUV40nExCGxsxkDPOTgtzMu4zlZCQb3bPlKsXo1SYJg6ME;
    private String nrk4qDbuonwjAUi7HPDytbtabfk6KLkf1OM4Z1o9O6dUiFPBfNVpTU1RRHKJKimM = "";
    private String q8iygSjgpTZ52A5K8GXhnvUs2CX4iXd4uIVtwvououVquIzlzfVOfWIR3CjSHG4 = "";
    protected int requestCode;

    /* JADX WARNING: Illegal instructions before constructor call */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public NearField(com.google.appinventor.components.runtime.ComponentContainer r6) {
        /*
            r5 = this;
            r0 = r5
            r1 = r6
            r2 = r0
            r3 = r1
            com.google.appinventor.components.runtime.Form r3 = r3.$form()
            r2.<init>(r3)
            r2 = r0
            r3 = 1
            r2.LqPkE8nw2txhYsROtdyVVn8eaaz2Snf5qPRDJl9Xd4fWH3FTDxRDNp0V8h7hgt8B = r3
            r2 = r0
            java.lang.String r3 = ""
            r2.q8iygSjgpTZ52A5K8GXhnvUs2CX4iXd4uIVtwvououVquIzlzfVOfWIR3CjSHG4 = r3
            r2 = r0
            java.lang.String r3 = ""
            r2.nrk4qDbuonwjAUi7HPDytbtabfk6KLkf1OM4Z1o9O6dUiFPBfNVpTU1RRHKJKimM = r3
            r2 = r0
            r3 = r1
            android.app.Activity r3 = r3.$context()
            r2.activity = r3
            r2 = r0
            r3 = 1
            r2.Ta6bjdQXoFeEb44hWQ7kTTTXw2rT1LHw6UX7lms7WU7H0AkpETH9D9EhELUfywQi = r3
            r2 = r0
            int r3 = android.os.Build.VERSION.SDK_INT
            r4 = 9
            if (r3 < r4) goto L_0x0057
            r3 = r0
            android.app.Activity r3 = r3.activity
            android.nfc.NfcAdapter r3 = com.google.appinventor.components.runtime.util.GingerbreadUtil.newNfcAdapter(r3)
        L_0x0035:
            r2.hxYOFxFjLpN1maJuWNxUV40nExCGxsxkDPOTgtzMu4zlZCQb3bPlKsXo1SYJg6ME = r3
            r2 = r0
            com.google.appinventor.components.runtime.Form r2 = r2.form
            r3 = r0
            r2.registerForOnResume(r3)
            r2 = r0
            com.google.appinventor.components.runtime.Form r2 = r2.form
            r3 = r0
            r2.registerForOnNewIntent(r3)
            r2 = r0
            com.google.appinventor.components.runtime.Form r2 = r2.form
            r3 = r0
            r2.registerForOnPause(r3)
            java.lang.String r2 = "nearfield"
            java.lang.String r3 = "Nearfield component created"
            int r2 = android.util.Log.d(r2, r3)
            return
        L_0x0057:
            r3 = 0
            goto L_0x0035
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.appinventor.components.runtime.NearField.<init>(com.google.appinventor.components.runtime.ComponentContainer):void");
    }

    @SimpleEvent
    public void TagRead(String str, String str2) {
        String str3 = str2;
        int d = Log.d("nearfield", "Tag read: got message ".concat(String.valueOf(str3)));
        this.q8iygSjgpTZ52A5K8GXhnvUs2CX4iXd4uIVtwvououVquIzlzfVOfWIR3CjSHG4 = str3;
        Object[] objArr = new Object[2];
        objArr[0] = str;
        Object[] objArr2 = objArr;
        objArr2[1] = str3;
        boolean dispatchEvent = EventDispatcher.dispatchEvent(this, "TagRead", objArr2);
    }

    @SimpleEvent(description = "Event to detect when a tag was written.")
    public void TagWritten() {
        StringBuilder sb;
        new StringBuilder("Tag written: ");
        int d = Log.d("nearfield", sb.append(this.nrk4qDbuonwjAUi7HPDytbtabfk6KLkf1OM4Z1o9O6dUiFPBfNVpTU1RRHKJKimM).toString());
        boolean dispatchEvent = EventDispatcher.dispatchEvent(this, "TagWritten", new Object[0]);
    }

    @SimpleProperty(category = PropertyCategory.BEHAVIOR)
    public String LastMessage() {
        int d = Log.d("nearfield", "String message method stared");
        return this.q8iygSjgpTZ52A5K8GXhnvUs2CX4iXd4uIVtwvououVquIzlzfVOfWIR3CjSHG4;
    }

    @DesignerProperty(defaultValue = "True", editorType = "boolean")
    @SimpleProperty(category = PropertyCategory.BEHAVIOR)
    public boolean ReadMode() {
        int d = Log.d("nearfield", "boolean method stared");
        return this.LqPkE8nw2txhYsROtdyVVn8eaaz2Snf5qPRDJl9Xd4fWH3FTDxRDNp0V8h7hgt8B;
    }

    @SimpleProperty(category = PropertyCategory.BEHAVIOR)
    public String TextToWrite() {
        int d = Log.d("nearfield", "String message method stared");
        return this.nrk4qDbuonwjAUi7HPDytbtabfk6KLkf1OM4Z1o9O6dUiFPBfNVpTU1RRHKJKimM;
    }

    @SimpleProperty(category = PropertyCategory.BEHAVIOR)
    public int WriteType() {
        return this.Ta6bjdQXoFeEb44hWQ7kTTTXw2rT1LHw6UX7lms7WU7H0AkpETH9D9EhELUfywQi;
    }

    @SimpleProperty(category = PropertyCategory.BEHAVIOR)
    public void ReadMode(boolean z) {
        boolean z2 = z;
        int d = Log.d("nearfield", "Read mode set to".concat(String.valueOf(z2)));
        this.LqPkE8nw2txhYsROtdyVVn8eaaz2Snf5qPRDJl9Xd4fWH3FTDxRDNp0V8h7hgt8B = z2;
        if (!this.LqPkE8nw2txhYsROtdyVVn8eaaz2Snf5qPRDJl9Xd4fWH3FTDxRDNp0V8h7hgt8B && Build.VERSION.SDK_INT >= 9) {
            GingerbreadUtil.enableNFCWriteMode(this.activity, this.hxYOFxFjLpN1maJuWNxUV40nExCGxsxkDPOTgtzMu4zlZCQb3bPlKsXo1SYJg6ME, this.nrk4qDbuonwjAUi7HPDytbtabfk6KLkf1OM4Z1o9O6dUiFPBfNVpTU1RRHKJKimM);
        }
    }

    @SimpleProperty(category = PropertyCategory.BEHAVIOR)
    public void TextToWrite(String str) {
        String str2 = str;
        int d = Log.d("nearfield", "Text to write set to".concat(String.valueOf(str2)));
        this.nrk4qDbuonwjAUi7HPDytbtabfk6KLkf1OM4Z1o9O6dUiFPBfNVpTU1RRHKJKimM = str2;
        if (!this.LqPkE8nw2txhYsROtdyVVn8eaaz2Snf5qPRDJl9Xd4fWH3FTDxRDNp0V8h7hgt8B && this.Ta6bjdQXoFeEb44hWQ7kTTTXw2rT1LHw6UX7lms7WU7H0AkpETH9D9EhELUfywQi == 1 && Build.VERSION.SDK_INT >= 9) {
            GingerbreadUtil.enableNFCWriteMode(this.activity, this.hxYOFxFjLpN1maJuWNxUV40nExCGxsxkDPOTgtzMu4zlZCQb3bPlKsXo1SYJg6ME, this.nrk4qDbuonwjAUi7HPDytbtabfk6KLkf1OM4Z1o9O6dUiFPBfNVpTU1RRHKJKimM);
        }
    }

    public void onNewIntent(Intent intent) {
        Intent intent2 = intent;
        int d = Log.d("nearfield", "Nearfield on onNewIntent.  Intent is: ".concat(String.valueOf(intent2)));
        Intent intent3 = intent2;
        int d2 = Log.d("nearfield", "resolve intent. Intent is: ".concat(String.valueOf(intent3)));
        if (Build.VERSION.SDK_INT >= 9) {
            GingerbreadUtil.resolveNFCIntent(intent3, this);
        }
    }

    public void onResume() {
        if (this.activity != null) {
            int d = Log.d("nearfield", "Nearfield on onResume.  Intent is: ".concat(String.valueOf(this.activity.getIntent())));
        }
    }

    public void onPause() {
        int d = Log.d("nearfield", "OnPause method started.");
        if (this.hxYOFxFjLpN1maJuWNxUV40nExCGxsxkDPOTgtzMu4zlZCQb3bPlKsXo1SYJg6ME != null && this.activity != null) {
            GingerbreadUtil.disableNFCAdapter(this.activity, this.hxYOFxFjLpN1maJuWNxUV40nExCGxsxkDPOTgtzMu4zlZCQb3bPlKsXo1SYJg6ME);
        }
    }

    public void onDelete() {
    }

    public void onStop() {
    }

    public static String toHexString(byte[] bArr) {
        StringBuilder sb;
        byte[] bArr2 = bArr;
        new StringBuilder();
        StringBuilder sb2 = sb;
        int length = bArr2.length;
        for (int i = 0; i < length; i++) {
            StringBuilder append = sb2.append(String.format("%02x", new Object[]{Integer.valueOf(bArr2[i] & Ev3Constants.Opcode.TST)}));
        }
        return sb2.toString().toUpperCase();
    }
}
