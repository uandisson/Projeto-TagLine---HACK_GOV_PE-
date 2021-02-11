package org.osmdroid.tileprovider.modules;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Environment;
import android.util.Log;
import org.osmdroid.api.IMapView;
import org.osmdroid.tileprovider.IRegisterReceiver;

public abstract class MapTileFileStorageProviderBase extends MapTileModuleProviderBase {
    private static boolean mSdCardAvailable = true;
    private MyBroadcastReceiver mBroadcastReceiver;
    private final IRegisterReceiver mRegisterReceiver;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public MapTileFileStorageProviderBase(IRegisterReceiver iRegisterReceiver, int pThreadPoolSize, int pPendingQueueSize) {
        super(pThreadPoolSize, pPendingQueueSize);
        MyBroadcastReceiver myBroadcastReceiver;
        IntentFilter intentFilter;
        IRegisterReceiver pRegisterReceiver = iRegisterReceiver;
        checkSdCard();
        this.mRegisterReceiver = pRegisterReceiver;
        new MyBroadcastReceiver(this, (C15851) null);
        this.mBroadcastReceiver = myBroadcastReceiver;
        new IntentFilter();
        IntentFilter mediaFilter = intentFilter;
        mediaFilter.addAction("android.intent.action.MEDIA_MOUNTED");
        mediaFilter.addAction("android.intent.action.MEDIA_UNMOUNTED");
        mediaFilter.addDataScheme("file");
        Intent registerReceiver = pRegisterReceiver.registerReceiver(this.mBroadcastReceiver, mediaFilter);
    }

    /* access modifiers changed from: private */
    public void checkSdCard() {
        StringBuilder sb;
        String state = Environment.getExternalStorageState();
        new StringBuilder();
        int i = Log.i(IMapView.LOGTAG, sb.append("sdcard state: ").append(state).toString());
        mSdCardAvailable = "mounted".equals(state);
    }

    public static boolean isSdCardAvailable() {
        return mSdCardAvailable;
    }

    public void detach() {
        if (this.mBroadcastReceiver != null) {
            this.mRegisterReceiver.unregisterReceiver(this.mBroadcastReceiver);
            this.mBroadcastReceiver = null;
        }
        super.detach();
    }

    /* access modifiers changed from: protected */
    public void onMediaMounted() {
    }

    /* access modifiers changed from: protected */
    public void onMediaUnmounted() {
    }

    private class MyBroadcastReceiver extends BroadcastReceiver {
        final /* synthetic */ MapTileFileStorageProviderBase this$0;

        private MyBroadcastReceiver(MapTileFileStorageProviderBase mapTileFileStorageProviderBase) {
            this.this$0 = mapTileFileStorageProviderBase;
        }

        /* JADX INFO: this call moved to the top of the method (can break code semantics) */
        /* synthetic */ MyBroadcastReceiver(MapTileFileStorageProviderBase x0, C15851 r7) {
            this(x0);
            C15851 r2 = r7;
        }

        public void onReceive(Context context, Intent aIntent) {
            Context context2 = context;
            String action = aIntent.getAction();
            this.this$0.checkSdCard();
            if ("android.intent.action.MEDIA_MOUNTED".equals(action)) {
                this.this$0.onMediaMounted();
            } else if ("android.intent.action.MEDIA_UNMOUNTED".equals(action)) {
                this.this$0.onMediaUnmounted();
            }
        }
    }
}
