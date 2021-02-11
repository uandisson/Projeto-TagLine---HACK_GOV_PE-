package org.osmdroid.events;

import android.os.Handler;
import android.util.Log;
import org.osmdroid.api.IMapView;

public class DelayedMapListener implements MapListener {
    protected static final int DEFAULT_DELAY = 100;
    protected CallbackTask callback;
    protected long delay;
    protected Handler handler;
    MapListener wrappedListener;

    public DelayedMapListener(MapListener wrappedListener2, long delay2) {
        Handler handler2;
        this.wrappedListener = wrappedListener2;
        this.delay = delay2;
        new Handler();
        this.handler = handler2;
        this.callback = null;
    }

    /* JADX INFO: this call moved to the top of the method (can break code semantics) */
    public DelayedMapListener(MapListener wrappedListener2) {
        this(wrappedListener2, 100);
    }

    public boolean onScroll(ScrollEvent event) {
        dispatch(event);
        return true;
    }

    public boolean onZoom(ZoomEvent event) {
        dispatch(event);
        return true;
    }

    /* access modifiers changed from: protected */
    public void dispatch(MapEvent mapEvent) {
        CallbackTask callbackTask;
        MapEvent event = mapEvent;
        if (this.callback != null) {
            this.handler.removeCallbacks(this.callback);
        }
        new CallbackTask(this, event);
        this.callback = callbackTask;
        boolean postDelayed = this.handler.postDelayed(this.callback, this.delay);
    }

    private class CallbackTask implements Runnable {
        private final MapEvent event;
        final /* synthetic */ DelayedMapListener this$0;

        public CallbackTask(DelayedMapListener delayedMapListener, MapEvent event2) {
            this.this$0 = delayedMapListener;
            this.event = event2;
        }

        public void run() {
            StringBuilder sb;
            if (this.event instanceof ScrollEvent) {
                boolean onScroll = this.this$0.wrappedListener.onScroll((ScrollEvent) this.event);
            } else if (this.event instanceof ZoomEvent) {
                boolean onZoom = this.this$0.wrappedListener.onZoom((ZoomEvent) this.event);
            } else {
                new StringBuilder();
                int d = Log.d(IMapView.LOGTAG, sb.append("Unknown event received: ").append(this.event).toString());
            }
        }
    }
}
