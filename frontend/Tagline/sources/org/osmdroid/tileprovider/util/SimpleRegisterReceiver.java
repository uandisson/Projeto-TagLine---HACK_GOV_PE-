package org.osmdroid.tileprovider.util;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import org.osmdroid.tileprovider.IRegisterReceiver;

public class SimpleRegisterReceiver implements IRegisterReceiver {
    private Context mContext;

    public SimpleRegisterReceiver(Context pContext) {
        this.mContext = pContext;
    }

    public Intent registerReceiver(BroadcastReceiver aReceiver, IntentFilter aFilter) {
        return this.mContext.registerReceiver(aReceiver, aFilter);
    }

    public void unregisterReceiver(BroadcastReceiver aReceiver) {
        this.mContext.unregisterReceiver(aReceiver);
    }

    public void destroy() {
        this.mContext = null;
    }
}
