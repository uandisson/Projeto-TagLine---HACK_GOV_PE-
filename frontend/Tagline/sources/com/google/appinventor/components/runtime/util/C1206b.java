package com.google.appinventor.components.runtime.util;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Picture;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.PictureDrawable;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.p000v4.view.ViewCompat;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.animation.Animation;
import android.widget.RelativeLayout;
import com.caverock.androidsvg.SVG;
import com.caverock.androidsvg.SVGParseException;
import com.google.appinventor.components.runtime.Component;
import com.google.appinventor.components.runtime.Form;
import com.google.appinventor.components.runtime.LocationSensor;
import com.google.appinventor.components.runtime.util.MapFactory;
import com.google.appinventor.components.runtime.view.ZoomControlView;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.osmdroid.api.IGeoPoint;
import org.osmdroid.config.Configuration;
import org.osmdroid.events.MapListener;
import org.osmdroid.events.ScrollEvent;
import org.osmdroid.events.ZoomEvent;
import org.osmdroid.tileprovider.constants.OpenStreetMapTileProviderConstants;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.BoundingBox;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.Marker;
import org.osmdroid.views.overlay.Overlay;
import org.osmdroid.views.overlay.OverlayWithIW;
import org.osmdroid.views.overlay.OverlayWithIWVisitor;
import org.osmdroid.views.overlay.Polygon;
import org.osmdroid.views.overlay.Polyline;
import org.osmdroid.views.overlay.ScaleBarOverlay;
import org.osmdroid.views.overlay.compass.CompassOverlay;
import org.osmdroid.views.overlay.compass.IOrientationProvider;
import org.osmdroid.views.overlay.compass.InternalCompassOrientationProvider;
import org.osmdroid.views.overlay.gestures.RotationGestureOverlay;
import org.osmdroid.views.overlay.infowindow.OverlayInfoWindow;
import org.osmdroid.views.overlay.mylocation.IMyLocationConsumer;
import org.osmdroid.views.overlay.mylocation.IMyLocationProvider;
import org.osmdroid.views.overlay.mylocation.MyLocationNewOverlay;

/* renamed from: com.google.appinventor.components.runtime.util.b */
class C1206b implements MapFactory.MapController, MapListener {
    /* access modifiers changed from: private */
    public static final String TAG = C1206b.class.getSimpleName();
    private static final float[] mBLKdPlRolYfdtud5Z8giGWyhURWoCw9yHsOv08fUnIBAM4MuKAWkHVD8nEhBmIT = {Float.NaN, 0.0f, 0.5f, 1.0f};
    private static final float[] sSQuIBUVrx5rcdxHqHgqC6bPLuuDnxnBF7e7BJeOxmxr54l6ArzFZvC3SGBTuUdt = {Float.NaN, 0.0f, 1.0f, 0.5f};
    private Set<MapFactory.MapFeature> Co1rSUoyBoOJ9n0NOgrpxvTYmRPklnETeodUBiQlSBK151C8PCv1Q1Pl4SUa1qxB;
    private float M2XSrcNVsTj86KbWYhtzmFwqCl4FRN4hJC3YQ2jC5nTG9V14APZgqJsIs4HMKSeu;
    private boolean XkTExsgDjzL7UIaxQorrmSTr7jZbDxCmXSVfTpg7zAbwFxeOpcI2x1hAyx12QFiS;
    private boolean ZBOPOXf20XZKrN6ycbQhEDPC2OsW2QCGfMHApJMYjAVGCpHVQNyA3eb1TXx8tY2I;
    private final Form form;
    private RelativeLayout hxYOFxFjLpN1maJuWNxUV40nExCGxsxkDPOTgtzMu4zlZCQb3bPlKsXo1SYJg6ME;

    /* renamed from: hxYOFxFjLpN1maJuWNxUV40nExCGxsxkDPOTgtzMu4zlZCQb3bPlKsXo1SYJg6ME  reason: collision with other field name */
    private SVG f794hxYOFxFjLpN1maJuWNxUV40nExCGxsxkDPOTgtzMu4zlZCQb3bPlKsXo1SYJg6ME;

    /* renamed from: hxYOFxFjLpN1maJuWNxUV40nExCGxsxkDPOTgtzMu4zlZCQb3bPlKsXo1SYJg6ME  reason: collision with other field name */
    private MapFactory.MapType f795hxYOFxFjLpN1maJuWNxUV40nExCGxsxkDPOTgtzMu4zlZCQb3bPlKsXo1SYJg6ME;

    /* renamed from: hxYOFxFjLpN1maJuWNxUV40nExCGxsxkDPOTgtzMu4zlZCQb3bPlKsXo1SYJg6ME  reason: collision with other field name */
    private final C1225a f796hxYOFxFjLpN1maJuWNxUV40nExCGxsxkDPOTgtzMu4zlZCQb3bPlKsXo1SYJg6ME;

    /* renamed from: hxYOFxFjLpN1maJuWNxUV40nExCGxsxkDPOTgtzMu4zlZCQb3bPlKsXo1SYJg6ME  reason: collision with other field name */
    private C1230e f797hxYOFxFjLpN1maJuWNxUV40nExCGxsxkDPOTgtzMu4zlZCQb3bPlKsXo1SYJg6ME;

    /* renamed from: hxYOFxFjLpN1maJuWNxUV40nExCGxsxkDPOTgtzMu4zlZCQb3bPlKsXo1SYJg6ME  reason: collision with other field name */
    private ZoomControlView f798hxYOFxFjLpN1maJuWNxUV40nExCGxsxkDPOTgtzMu4zlZCQb3bPlKsXo1SYJg6ME;

    /* renamed from: hxYOFxFjLpN1maJuWNxUV40nExCGxsxkDPOTgtzMu4zlZCQb3bPlKsXo1SYJg6ME  reason: collision with other field name */
    private MapView f799hxYOFxFjLpN1maJuWNxUV40nExCGxsxkDPOTgtzMu4zlZCQb3bPlKsXo1SYJg6ME;

    /* renamed from: hxYOFxFjLpN1maJuWNxUV40nExCGxsxkDPOTgtzMu4zlZCQb3bPlKsXo1SYJg6ME  reason: collision with other field name */
    private ScaleBarOverlay f800hxYOFxFjLpN1maJuWNxUV40nExCGxsxkDPOTgtzMu4zlZCQb3bPlKsXo1SYJg6ME;

    /* renamed from: hxYOFxFjLpN1maJuWNxUV40nExCGxsxkDPOTgtzMu4zlZCQb3bPlKsXo1SYJg6ME  reason: collision with other field name */
    private CompassOverlay f801hxYOFxFjLpN1maJuWNxUV40nExCGxsxkDPOTgtzMu4zlZCQb3bPlKsXo1SYJg6ME = null;

    /* renamed from: hxYOFxFjLpN1maJuWNxUV40nExCGxsxkDPOTgtzMu4zlZCQb3bPlKsXo1SYJg6ME  reason: collision with other field name */
    private RotationGestureOverlay f802hxYOFxFjLpN1maJuWNxUV40nExCGxsxkDPOTgtzMu4zlZCQb3bPlKsXo1SYJg6ME = null;

    /* renamed from: hxYOFxFjLpN1maJuWNxUV40nExCGxsxkDPOTgtzMu4zlZCQb3bPlKsXo1SYJg6ME  reason: collision with other field name */
    private OverlayInfoWindow f803hxYOFxFjLpN1maJuWNxUV40nExCGxsxkDPOTgtzMu4zlZCQb3bPlKsXo1SYJg6ME;

    /* renamed from: hxYOFxFjLpN1maJuWNxUV40nExCGxsxkDPOTgtzMu4zlZCQb3bPlKsXo1SYJg6ME  reason: collision with other field name */
    private final MyLocationNewOverlay f804hxYOFxFjLpN1maJuWNxUV40nExCGxsxkDPOTgtzMu4zlZCQb3bPlKsXo1SYJg6ME;
    private Map<MapFactory.MapFeature, OverlayWithIW> moH2L3ThNq0Qhevz3oD0wpnUoXefdMi3gJ5JHfEbjrtgsvRvpM1e34BZQYI4s5i0;
    private Set<MapFactory.MapFeatureCollection> qPeHJnCLP0dAOwDPeFIn82vcSIsCMh6KFFn3o4kyIe0RhQKOQXDeyY2LFwPu2GbE;
    private Set<MapFactory.MapEventListener> vwEpIRqEf6xdtwTR9dehwBO7JUhyLV6iEzEK2WqfPN10eUMQDPn3AUmqAFfsnr6R;
    /* access modifiers changed from: private */
    public boolean wfWoJD9s05zGciiq11jj7TXqW1AmJIE9BV68DWBBkLZSv9NayRoTG3XJb9WJMlyD;
    private boolean zoomEnabled;

    static /* synthetic */ Drawable hxYOFxFjLpN1maJuWNxUV40nExCGxsxkDPOTgtzMu4zlZCQb3bPlKsXo1SYJg6ME(C1206b bVar, MapFactory.MapMarker mapMarker) {
        C1206b bVar2 = bVar;
        return bVar2.hxYOFxFjLpN1maJuWNxUV40nExCGxsxkDPOTgtzMu4zlZCQb3bPlKsXo1SYJg6ME(mapMarker, bVar2.f794hxYOFxFjLpN1maJuWNxUV40nExCGxsxkDPOTgtzMu4zlZCQb3bPlKsXo1SYJg6ME);
    }

    /* renamed from: com.google.appinventor.components.runtime.util.b$a */
    static class C1225a implements LocationSensor.LocationSensorListener, IMyLocationProvider {
        private boolean enabled;
        private Location hxYOFxFjLpN1maJuWNxUV40nExCGxsxkDPOTgtzMu4zlZCQb3bPlKsXo1SYJg6ME;

        /* renamed from: hxYOFxFjLpN1maJuWNxUV40nExCGxsxkDPOTgtzMu4zlZCQb3bPlKsXo1SYJg6ME  reason: collision with other field name */
        private IMyLocationConsumer f815hxYOFxFjLpN1maJuWNxUV40nExCGxsxkDPOTgtzMu4zlZCQb3bPlKsXo1SYJg6ME;
        private LocationSensor vSp02fkBXgM8EI0gm0rKWXHQ6wdQINJBQuAtCR15YU8g4XNqVKV8r32SYxkQYxkq;

        private C1225a() {
            this.enabled = false;
        }

        /* JADX INFO: this call moved to the top of the method (can break code semantics) */
        /* synthetic */ C1225a(byte b) {
            this();
            byte b2 = b;
        }

        public final void setSource(LocationSensor locationSensor) {
            LocationSensor locationSensor2 = locationSensor;
            if (this.vSp02fkBXgM8EI0gm0rKWXHQ6wdQINJBQuAtCR15YU8g4XNqVKV8r32SYxkQYxkq != locationSensor2) {
                if (this.vSp02fkBXgM8EI0gm0rKWXHQ6wdQINJBQuAtCR15YU8g4XNqVKV8r32SYxkQYxkq != null) {
                    this.vSp02fkBXgM8EI0gm0rKWXHQ6wdQINJBQuAtCR15YU8g4XNqVKV8r32SYxkQYxkq.Enabled(false);
                }
                this.vSp02fkBXgM8EI0gm0rKWXHQ6wdQINJBQuAtCR15YU8g4XNqVKV8r32SYxkQYxkq = locationSensor2;
                if (this.vSp02fkBXgM8EI0gm0rKWXHQ6wdQINJBQuAtCR15YU8g4XNqVKV8r32SYxkQYxkq != null) {
                    this.vSp02fkBXgM8EI0gm0rKWXHQ6wdQINJBQuAtCR15YU8g4XNqVKV8r32SYxkQYxkq.Enabled(this.enabled);
                }
            }
        }

        public final void onTimeIntervalChanged(int i) {
        }

        public final void onDistanceIntervalChanged(int i) {
        }

        public final void onLocationChanged(Location location) {
            Location location2 = location;
            this.hxYOFxFjLpN1maJuWNxUV40nExCGxsxkDPOTgtzMu4zlZCQb3bPlKsXo1SYJg6ME = location2;
            if (this.f815hxYOFxFjLpN1maJuWNxUV40nExCGxsxkDPOTgtzMu4zlZCQb3bPlKsXo1SYJg6ME != null) {
                this.f815hxYOFxFjLpN1maJuWNxUV40nExCGxsxkDPOTgtzMu4zlZCQb3bPlKsXo1SYJg6ME.onLocationChanged(location2, this);
            }
        }

        public final void onStatusChanged(String str, int i, Bundle bundle) {
        }

        public final void onProviderEnabled(String str) {
        }

        public final void onProviderDisabled(String str) {
        }

        public final boolean startLocationProvider(IMyLocationConsumer iMyLocationConsumer) {
            this.f815hxYOFxFjLpN1maJuWNxUV40nExCGxsxkDPOTgtzMu4zlZCQb3bPlKsXo1SYJg6ME = iMyLocationConsumer;
            if (this.vSp02fkBXgM8EI0gm0rKWXHQ6wdQINJBQuAtCR15YU8g4XNqVKV8r32SYxkQYxkq != null) {
                this.vSp02fkBXgM8EI0gm0rKWXHQ6wdQINJBQuAtCR15YU8g4XNqVKV8r32SYxkQYxkq.Enabled(true);
                this.enabled = true;
            }
            return this.enabled;
        }

        public final void stopLocationProvider() {
            if (this.vSp02fkBXgM8EI0gm0rKWXHQ6wdQINJBQuAtCR15YU8g4XNqVKV8r32SYxkQYxkq != null) {
                this.vSp02fkBXgM8EI0gm0rKWXHQ6wdQINJBQuAtCR15YU8g4XNqVKV8r32SYxkQYxkq.Enabled(false);
            }
            this.enabled = false;
        }

        public final Location getLastKnownLocation() {
            return this.hxYOFxFjLpN1maJuWNxUV40nExCGxsxkDPOTgtzMu4zlZCQb3bPlKsXo1SYJg6ME;
        }

        public final void destroy() {
            this.f815hxYOFxFjLpN1maJuWNxUV40nExCGxsxkDPOTgtzMu4zlZCQb3bPlKsXo1SYJg6ME = null;
        }
    }

    /* renamed from: com.google.appinventor.components.runtime.util.b$e */
    class C1230e extends Overlay {
        private /* synthetic */ C1206b hxYOFxFjLpN1maJuWNxUV40nExCGxsxkDPOTgtzMu4zlZCQb3bPlKsXo1SYJg6ME;
        /* access modifiers changed from: private */
        public boolean muMCPHZoR5LDcgNaOoyYPcH1tjqU9LHoHV7Yzgex9Dj2uE6xbcPRtlEMsWbJpsbN;

        private C1230e(C1206b bVar) {
            this.hxYOFxFjLpN1maJuWNxUV40nExCGxsxkDPOTgtzMu4zlZCQb3bPlKsXo1SYJg6ME = bVar;
            this.muMCPHZoR5LDcgNaOoyYPcH1tjqU9LHoHV7Yzgex9Dj2uE6xbcPRtlEMsWbJpsbN = true;
        }

        /* JADX INFO: this call moved to the top of the method (can break code semantics) */
        /* synthetic */ C1230e(C1206b bVar, byte b) {
            this(bVar);
            byte b2 = b;
        }

        static /* synthetic */ boolean hxYOFxFjLpN1maJuWNxUV40nExCGxsxkDPOTgtzMu4zlZCQb3bPlKsXo1SYJg6ME(C1230e eVar, boolean z) {
            boolean z2 = z;
            boolean z3 = z2;
            eVar.muMCPHZoR5LDcgNaOoyYPcH1tjqU9LHoHV7Yzgex9Dj2uE6xbcPRtlEMsWbJpsbN = z3;
            return z2;
        }

        public final void draw(Canvas canvas, MapView mapView, boolean z) {
        }

        public final boolean onFling(MotionEvent motionEvent, MotionEvent motionEvent2, float f, float f2, MapView mapView) {
            MotionEvent motionEvent3 = motionEvent;
            MotionEvent motionEvent4 = motionEvent2;
            float f3 = f;
            float f4 = f2;
            MapView mapView2 = mapView;
            return !this.muMCPHZoR5LDcgNaOoyYPcH1tjqU9LHoHV7Yzgex9Dj2uE6xbcPRtlEMsWbJpsbN;
        }

        public final boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent2, float f, float f2, MapView mapView) {
            MotionEvent motionEvent3 = motionEvent;
            MotionEvent motionEvent4 = motionEvent2;
            float f3 = f;
            float f4 = f2;
            MapView mapView2 = mapView;
            return !this.muMCPHZoR5LDcgNaOoyYPcH1tjqU9LHoHV7Yzgex9Dj2uE6xbcPRtlEMsWbJpsbN;
        }

        public final boolean onLongPress(MotionEvent motionEvent, MapView mapView) {
            MotionEvent motionEvent2 = motionEvent;
            IGeoPoint fromPixels = mapView.getProjection().fromPixels((int) motionEvent2.getX(), (int) motionEvent2.getY());
            double latitude = fromPixels.getLatitude();
            double longitude = fromPixels.getLongitude();
            for (MapFactory.MapEventListener onLongPress : C1206b.hxYOFxFjLpN1maJuWNxUV40nExCGxsxkDPOTgtzMu4zlZCQb3bPlKsXo1SYJg6ME(this.hxYOFxFjLpN1maJuWNxUV40nExCGxsxkDPOTgtzMu4zlZCQb3bPlKsXo1SYJg6ME)) {
                onLongPress.onLongPress(latitude, longitude);
            }
            return false;
        }
    }

    /* renamed from: com.google.appinventor.components.runtime.util.b$c */
    class C1227c extends Handler {
        final /* synthetic */ C1206b hxYOFxFjLpN1maJuWNxUV40nExCGxsxkDPOTgtzMu4zlZCQb3bPlKsXo1SYJg6ME;

        private C1227c(C1206b bVar) {
            this.hxYOFxFjLpN1maJuWNxUV40nExCGxsxkDPOTgtzMu4zlZCQb3bPlKsXo1SYJg6ME = bVar;
        }

        /* JADX INFO: this call moved to the top of the method (can break code semantics) */
        /* synthetic */ C1227c(C1206b bVar, byte b) {
            this(bVar);
            byte b2 = b;
        }

        public final void handleMessage(Message message) {
            Runnable runnable;
            switch (message.what) {
                case 0:
                    if (!C1206b.hxYOFxFjLpN1maJuWNxUV40nExCGxsxkDPOTgtzMu4zlZCQb3bPlKsXo1SYJg6ME(this.hxYOFxFjLpN1maJuWNxUV40nExCGxsxkDPOTgtzMu4zlZCQb3bPlKsXo1SYJg6ME) && C1206b.hxYOFxFjLpN1maJuWNxUV40nExCGxsxkDPOTgtzMu4zlZCQb3bPlKsXo1SYJg6ME(this.hxYOFxFjLpN1maJuWNxUV40nExCGxsxkDPOTgtzMu4zlZCQb3bPlKsXo1SYJg6ME).canDispatchEvent((Component) null, "MapReady")) {
                        boolean B8WBXPBCF2jGfUDZZU2zV5EYdqbUBu0lAZ0THCEqYyuE8VACR9dY7rDnwBIqh64T = this.hxYOFxFjLpN1maJuWNxUV40nExCGxsxkDPOTgtzMu4zlZCQb3bPlKsXo1SYJg6ME.wfWoJD9s05zGciiq11jj7TXqW1AmJIE9BV68DWBBkLZSv9NayRoTG3XJb9WJMlyD = true;
                        new Runnable(this) {
                            private /* synthetic */ C1227c hxYOFxFjLpN1maJuWNxUV40nExCGxsxkDPOTgtzMu4zlZCQb3bPlKsXo1SYJg6ME;

                            {
                                this.hxYOFxFjLpN1maJuWNxUV40nExCGxsxkDPOTgtzMu4zlZCQb3bPlKsXo1SYJg6ME = r5;
                            }

                            public final void run() {
                                for (MapFactory.MapEventListener onReady : C1206b.hxYOFxFjLpN1maJuWNxUV40nExCGxsxkDPOTgtzMu4zlZCQb3bPlKsXo1SYJg6ME(this.hxYOFxFjLpN1maJuWNxUV40nExCGxsxkDPOTgtzMu4zlZCQb3bPlKsXo1SYJg6ME.hxYOFxFjLpN1maJuWNxUV40nExCGxsxkDPOTgtzMu4zlZCQb3bPlKsXo1SYJg6ME)) {
                                    onReady.onReady(this.hxYOFxFjLpN1maJuWNxUV40nExCGxsxkDPOTgtzMu4zlZCQb3bPlKsXo1SYJg6ME.hxYOFxFjLpN1maJuWNxUV40nExCGxsxkDPOTgtzMu4zlZCQb3bPlKsXo1SYJg6ME);
                                }
                            }
                        };
                        C1206b.hxYOFxFjLpN1maJuWNxUV40nExCGxsxkDPOTgtzMu4zlZCQb3bPlKsXo1SYJg6ME(this.hxYOFxFjLpN1maJuWNxUV40nExCGxsxkDPOTgtzMu4zlZCQb3bPlKsXo1SYJg6ME).runOnUiThread(runnable);
                    }
                    C1206b.hxYOFxFjLpN1maJuWNxUV40nExCGxsxkDPOTgtzMu4zlZCQb3bPlKsXo1SYJg6ME(this.hxYOFxFjLpN1maJuWNxUV40nExCGxsxkDPOTgtzMu4zlZCQb3bPlKsXo1SYJg6ME).invalidate();
                    return;
                default:
                    return;
            }
        }
    }

    /* renamed from: com.google.appinventor.components.runtime.util.b$b */
    class C1226b extends MapView {
        private /* synthetic */ C1206b hxYOFxFjLpN1maJuWNxUV40nExCGxsxkDPOTgtzMu4zlZCQb3bPlKsXo1SYJg6ME;

        /* JADX WARNING: Illegal instructions before constructor call */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public C1226b(com.google.appinventor.components.runtime.util.C1206b r12, android.content.Context r13) {
            /*
                r11 = this;
                r0 = r11
                r1 = r12
                r2 = r13
                r3 = r0
                r4 = r1
                r3.hxYOFxFjLpN1maJuWNxUV40nExCGxsxkDPOTgtzMu4zlZCQb3bPlKsXo1SYJg6ME = r4
                r3 = r0
                r4 = r2
                r5 = 0
                com.google.appinventor.components.runtime.util.b$c r6 = new com.google.appinventor.components.runtime.util.b$c
                r10 = r6
                r6 = r10
                r7 = r10
                r8 = r1
                r9 = 0
                r7.<init>(r8, r9)
                r3.<init>(r4, r5, r6)
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.appinventor.components.runtime.util.C1206b.C1226b.<init>(com.google.appinventor.components.runtime.util.b, android.content.Context):void");
        }

        /* access modifiers changed from: protected */
        public final void onSizeChanged(int i, int i2, int i3, int i4) {
            int i5 = i;
            int i6 = i2;
            int i7 = i3;
            int i8 = i4;
            scrollTo(getScrollX() + ((i7 - i5) / 2), getScrollY() + ((i8 - i6) / 2));
            super.onSizeChanged(i5, i6, i7, i8);
        }

        public final void onDetach() {
        }
    }

    C1206b(Form form2) {
        Set<MapFactory.MapEventListener> set;
        Map<MapFactory.MapFeature, OverlayWithIW> map;
        Set<MapFactory.MapFeatureCollection> set2;
        Set<MapFactory.MapFeature> set3;
        File file;
        C1230e eVar;
        MapView mapView;
        C1225a aVar;
        OverlayInfoWindow overlayInfoWindow;
        MapView.OnTapListener onTapListener;
        ZoomControlView zoomControlView;
        MyLocationNewOverlay myLocationNewOverlay;
        ScaleBarOverlay scaleBarOverlay;
        RelativeLayout relativeLayout;
        ViewGroup.LayoutParams layoutParams;
        File file2;
        Form form3 = form2;
        new HashSet();
        this.vwEpIRqEf6xdtwTR9dehwBO7JUhyLV6iEzEK2WqfPN10eUMQDPn3AUmqAFfsnr6R = set;
        new HashMap();
        this.moH2L3ThNq0Qhevz3oD0wpnUoXefdMi3gJ5JHfEbjrtgsvRvpM1e34BZQYI4s5i0 = map;
        this.f794hxYOFxFjLpN1maJuWNxUV40nExCGxsxkDPOTgtzMu4zlZCQb3bPlKsXo1SYJg6ME = null;
        this.f797hxYOFxFjLpN1maJuWNxUV40nExCGxsxkDPOTgtzMu4zlZCQb3bPlKsXo1SYJg6ME = null;
        this.f803hxYOFxFjLpN1maJuWNxUV40nExCGxsxkDPOTgtzMu4zlZCQb3bPlKsXo1SYJg6ME = null;
        this.wfWoJD9s05zGciiq11jj7TXqW1AmJIE9BV68DWBBkLZSv9NayRoTG3XJb9WJMlyD = false;
        this.f798hxYOFxFjLpN1maJuWNxUV40nExCGxsxkDPOTgtzMu4zlZCQb3bPlKsXo1SYJg6ME = null;
        this.M2XSrcNVsTj86KbWYhtzmFwqCl4FRN4hJC3YQ2jC5nTG9V14APZgqJsIs4HMKSeu = Float.NaN;
        new HashSet();
        this.qPeHJnCLP0dAOwDPeFIn82vcSIsCMh6KFFn3o4kyIe0RhQKOQXDeyY2LFwPu2GbE = set2;
        new HashSet();
        this.Co1rSUoyBoOJ9n0NOgrpxvTYmRPklnETeodUBiQlSBK151C8PCv1Q1Pl4SUa1qxB = set3;
        OpenStreetMapTileProviderConstants.setUserAgentValue(form3.getApplication().getPackageName());
        new File(form3.getCacheDir(), "osmdroid");
        File file3 = file;
        File file4 = file3;
        if (file3.exists() || file4.mkdirs()) {
            Configuration.getInstance().setOsmdroidBasePath(file4);
            new File(file4, "tiles");
            File file5 = file2;
            File file6 = file5;
            if (file5.exists() || file6.mkdirs()) {
                Configuration.getInstance().setOsmdroidTileCache(file6);
                this.ZBOPOXf20XZKrN6ycbQhEDPC2OsW2QCGfMHApJMYjAVGCpHVQNyA3eb1TXx8tY2I = true;
            }
        }
        this.form = form3;
        new C1230e(this, (byte) 0);
        this.f797hxYOFxFjLpN1maJuWNxUV40nExCGxsxkDPOTgtzMu4zlZCQb3bPlKsXo1SYJg6ME = eVar;
        new C1226b(this, form3.getApplicationContext());
        this.f799hxYOFxFjLpN1maJuWNxUV40nExCGxsxkDPOTgtzMu4zlZCQb3bPlKsXo1SYJg6ME = mapView;
        new C1225a((byte) 0);
        this.f796hxYOFxFjLpN1maJuWNxUV40nExCGxsxkDPOTgtzMu4zlZCQb3bPlKsXo1SYJg6ME = aVar;
        new OverlayInfoWindow(this.f799hxYOFxFjLpN1maJuWNxUV40nExCGxsxkDPOTgtzMu4zlZCQb3bPlKsXo1SYJg6ME);
        this.f803hxYOFxFjLpN1maJuWNxUV40nExCGxsxkDPOTgtzMu4zlZCQb3bPlKsXo1SYJg6ME = overlayInfoWindow;
        this.f799hxYOFxFjLpN1maJuWNxUV40nExCGxsxkDPOTgtzMu4zlZCQb3bPlKsXo1SYJg6ME.setTilesScaledToDpi(true);
        this.f799hxYOFxFjLpN1maJuWNxUV40nExCGxsxkDPOTgtzMu4zlZCQb3bPlKsXo1SYJg6ME.setMapListener(this);
        boolean add = this.f799hxYOFxFjLpN1maJuWNxUV40nExCGxsxkDPOTgtzMu4zlZCQb3bPlKsXo1SYJg6ME.getOverlayManager().add(this.f797hxYOFxFjLpN1maJuWNxUV40nExCGxsxkDPOTgtzMu4zlZCQb3bPlKsXo1SYJg6ME);
        new MapView.OnTapListener(this) {
            private /* synthetic */ C1206b hxYOFxFjLpN1maJuWNxUV40nExCGxsxkDPOTgtzMu4zlZCQb3bPlKsXo1SYJg6ME;

            {
                this.hxYOFxFjLpN1maJuWNxUV40nExCGxsxkDPOTgtzMu4zlZCQb3bPlKsXo1SYJg6ME = r5;
            }

            public final void onSingleTap(MapView mapView, double d, double d2) {
                MapView mapView2 = mapView;
                double d3 = d;
                double d4 = d2;
                for (MapFactory.MapEventListener onSingleTap : C1206b.hxYOFxFjLpN1maJuWNxUV40nExCGxsxkDPOTgtzMu4zlZCQb3bPlKsXo1SYJg6ME(this.hxYOFxFjLpN1maJuWNxUV40nExCGxsxkDPOTgtzMu4zlZCQb3bPlKsXo1SYJg6ME)) {
                    onSingleTap.onSingleTap(d3, d4);
                }
            }

            public final void onDoubleTap(MapView mapView, double d, double d2) {
                MapView mapView2 = mapView;
                double d3 = d;
                double d4 = d2;
                for (MapFactory.MapEventListener onDoubleTap : C1206b.hxYOFxFjLpN1maJuWNxUV40nExCGxsxkDPOTgtzMu4zlZCQb3bPlKsXo1SYJg6ME(this.hxYOFxFjLpN1maJuWNxUV40nExCGxsxkDPOTgtzMu4zlZCQb3bPlKsXo1SYJg6ME)) {
                    onDoubleTap.onDoubleTap(d3, d4);
                }
            }
        };
        this.f799hxYOFxFjLpN1maJuWNxUV40nExCGxsxkDPOTgtzMu4zlZCQb3bPlKsXo1SYJg6ME.addOnTapListener(onTapListener);
        new ZoomControlView(this.f799hxYOFxFjLpN1maJuWNxUV40nExCGxsxkDPOTgtzMu4zlZCQb3bPlKsXo1SYJg6ME);
        this.f798hxYOFxFjLpN1maJuWNxUV40nExCGxsxkDPOTgtzMu4zlZCQb3bPlKsXo1SYJg6ME = zoomControlView;
        new MyLocationNewOverlay(this.f796hxYOFxFjLpN1maJuWNxUV40nExCGxsxkDPOTgtzMu4zlZCQb3bPlKsXo1SYJg6ME, this.f799hxYOFxFjLpN1maJuWNxUV40nExCGxsxkDPOTgtzMu4zlZCQb3bPlKsXo1SYJg6ME);
        this.f804hxYOFxFjLpN1maJuWNxUV40nExCGxsxkDPOTgtzMu4zlZCQb3bPlKsXo1SYJg6ME = myLocationNewOverlay;
        new ScaleBarOverlay(this.f799hxYOFxFjLpN1maJuWNxUV40nExCGxsxkDPOTgtzMu4zlZCQb3bPlKsXo1SYJg6ME);
        this.f800hxYOFxFjLpN1maJuWNxUV40nExCGxsxkDPOTgtzMu4zlZCQb3bPlKsXo1SYJg6ME = scaleBarOverlay;
        this.f800hxYOFxFjLpN1maJuWNxUV40nExCGxsxkDPOTgtzMu4zlZCQb3bPlKsXo1SYJg6ME.setAlignBottom(true);
        this.f800hxYOFxFjLpN1maJuWNxUV40nExCGxsxkDPOTgtzMu4zlZCQb3bPlKsXo1SYJg6ME.setAlignRight(true);
        this.f800hxYOFxFjLpN1maJuWNxUV40nExCGxsxkDPOTgtzMu4zlZCQb3bPlKsXo1SYJg6ME.disableScaleBar();
        boolean add2 = this.f799hxYOFxFjLpN1maJuWNxUV40nExCGxsxkDPOTgtzMu4zlZCQb3bPlKsXo1SYJg6ME.getOverlayManager().add(this.f800hxYOFxFjLpN1maJuWNxUV40nExCGxsxkDPOTgtzMu4zlZCQb3bPlKsXo1SYJg6ME);
        new RelativeLayout(form3);
        this.hxYOFxFjLpN1maJuWNxUV40nExCGxsxkDPOTgtzMu4zlZCQb3bPlKsXo1SYJg6ME = relativeLayout;
        this.hxYOFxFjLpN1maJuWNxUV40nExCGxsxkDPOTgtzMu4zlZCQb3bPlKsXo1SYJg6ME.setClipChildren(true);
        new RelativeLayout.LayoutParams(-1, -1);
        this.hxYOFxFjLpN1maJuWNxUV40nExCGxsxkDPOTgtzMu4zlZCQb3bPlKsXo1SYJg6ME.addView(this.f799hxYOFxFjLpN1maJuWNxUV40nExCGxsxkDPOTgtzMu4zlZCQb3bPlKsXo1SYJg6ME, layoutParams);
        this.hxYOFxFjLpN1maJuWNxUV40nExCGxsxkDPOTgtzMu4zlZCQb3bPlKsXo1SYJg6ME.addView(this.f798hxYOFxFjLpN1maJuWNxUV40nExCGxsxkDPOTgtzMu4zlZCQb3bPlKsXo1SYJg6ME);
        this.f798hxYOFxFjLpN1maJuWNxUV40nExCGxsxkDPOTgtzMu4zlZCQb3bPlKsXo1SYJg6ME.setVisibility(8);
    }

    public View getView() {
        return this.hxYOFxFjLpN1maJuWNxUV40nExCGxsxkDPOTgtzMu4zlZCQb3bPlKsXo1SYJg6ME;
    }

    public double getLatitude() {
        return this.f799hxYOFxFjLpN1maJuWNxUV40nExCGxsxkDPOTgtzMu4zlZCQb3bPlKsXo1SYJg6ME.getMapCenter().getLatitude();
    }

    public double getLongitude() {
        return this.f799hxYOFxFjLpN1maJuWNxUV40nExCGxsxkDPOTgtzMu4zlZCQb3bPlKsXo1SYJg6ME.getMapCenter().getLongitude();
    }

    public void setCenter(double d, double d2) {
        IGeoPoint iGeoPoint;
        new GeoPoint(d, d2);
        this.f799hxYOFxFjLpN1maJuWNxUV40nExCGxsxkDPOTgtzMu4zlZCQb3bPlKsXo1SYJg6ME.getController().setCenter(iGeoPoint);
    }

    public void setZoom(int i) {
        double zoom = this.f799hxYOFxFjLpN1maJuWNxUV40nExCGxsxkDPOTgtzMu4zlZCQb3bPlKsXo1SYJg6ME.getController().setZoom((double) i);
        this.f798hxYOFxFjLpN1maJuWNxUV40nExCGxsxkDPOTgtzMu4zlZCQb3bPlKsXo1SYJg6ME.updateButtons();
    }

    public int getZoom() {
        return (int) this.f799hxYOFxFjLpN1maJuWNxUV40nExCGxsxkDPOTgtzMu4zlZCQb3bPlKsXo1SYJg6ME.getZoomLevel(true);
    }

    public void setZoomEnabled(boolean z) {
        boolean z2 = z;
        this.zoomEnabled = z2;
        this.f799hxYOFxFjLpN1maJuWNxUV40nExCGxsxkDPOTgtzMu4zlZCQb3bPlKsXo1SYJg6ME.setMultiTouchControls(z2);
    }

    public boolean isZoomEnabled() {
        return this.zoomEnabled;
    }

    public void setMapType(MapFactory.MapType mapType) {
        MapFactory.MapType mapType2 = mapType;
        switch (mapType2) {
            case ROADS:
                this.f795hxYOFxFjLpN1maJuWNxUV40nExCGxsxkDPOTgtzMu4zlZCQb3bPlKsXo1SYJg6ME = mapType2;
                this.f799hxYOFxFjLpN1maJuWNxUV40nExCGxsxkDPOTgtzMu4zlZCQb3bPlKsXo1SYJg6ME.setTileSource(TileSourceFactory.MAPNIK);
                return;
            case AERIAL:
                this.f795hxYOFxFjLpN1maJuWNxUV40nExCGxsxkDPOTgtzMu4zlZCQb3bPlKsXo1SYJg6ME = mapType2;
                this.f799hxYOFxFjLpN1maJuWNxUV40nExCGxsxkDPOTgtzMu4zlZCQb3bPlKsXo1SYJg6ME.setTileSource(TileSourceFactory.USGS_SAT);
                return;
            case TERRAIN:
                this.f795hxYOFxFjLpN1maJuWNxUV40nExCGxsxkDPOTgtzMu4zlZCQb3bPlKsXo1SYJg6ME = mapType2;
                this.f799hxYOFxFjLpN1maJuWNxUV40nExCGxsxkDPOTgtzMu4zlZCQb3bPlKsXo1SYJg6ME.setTileSource(TileSourceFactory.USGS_TOPO);
                return;
            default:
                return;
        }
    }

    public MapFactory.MapType getMapType() {
        return this.f795hxYOFxFjLpN1maJuWNxUV40nExCGxsxkDPOTgtzMu4zlZCQb3bPlKsXo1SYJg6ME;
    }

    public void setCompassEnabled(boolean z) {
        IOrientationProvider iOrientationProvider;
        CompassOverlay compassOverlay;
        ViewTreeObserver.OnPreDrawListener onPreDrawListener;
        boolean z2 = z;
        if (z2 && this.f801hxYOFxFjLpN1maJuWNxUV40nExCGxsxkDPOTgtzMu4zlZCQb3bPlKsXo1SYJg6ME == null) {
            new CompassOverlay(this.f799hxYOFxFjLpN1maJuWNxUV40nExCGxsxkDPOTgtzMu4zlZCQb3bPlKsXo1SYJg6ME.getContext(), this.f799hxYOFxFjLpN1maJuWNxUV40nExCGxsxkDPOTgtzMu4zlZCQb3bPlKsXo1SYJg6ME);
            this.f801hxYOFxFjLpN1maJuWNxUV40nExCGxsxkDPOTgtzMu4zlZCQb3bPlKsXo1SYJg6ME = compassOverlay;
            new ViewTreeObserver.OnPreDrawListener(this) {
                private /* synthetic */ C1206b hxYOFxFjLpN1maJuWNxUV40nExCGxsxkDPOTgtzMu4zlZCQb3bPlKsXo1SYJg6ME;

                {
                    this.hxYOFxFjLpN1maJuWNxUV40nExCGxsxkDPOTgtzMu4zlZCQb3bPlKsXo1SYJg6ME = r5;
                }

                public final boolean onPreDraw() {
                    C1206b.hxYOFxFjLpN1maJuWNxUV40nExCGxsxkDPOTgtzMu4zlZCQb3bPlKsXo1SYJg6ME(this.hxYOFxFjLpN1maJuWNxUV40nExCGxsxkDPOTgtzMu4zlZCQb3bPlKsXo1SYJg6ME).setCompassCenter((((float) C1206b.hxYOFxFjLpN1maJuWNxUV40nExCGxsxkDPOTgtzMu4zlZCQb3bPlKsXo1SYJg6ME(this.hxYOFxFjLpN1maJuWNxUV40nExCGxsxkDPOTgtzMu4zlZCQb3bPlKsXo1SYJg6ME).getMeasuredWidth()) / C1206b.hxYOFxFjLpN1maJuWNxUV40nExCGxsxkDPOTgtzMu4zlZCQb3bPlKsXo1SYJg6ME(this.hxYOFxFjLpN1maJuWNxUV40nExCGxsxkDPOTgtzMu4zlZCQb3bPlKsXo1SYJg6ME).getContext().getResources().getDisplayMetrics().density) - 35.0f, 35.0f);
                    return true;
                }
            };
            this.f799hxYOFxFjLpN1maJuWNxUV40nExCGxsxkDPOTgtzMu4zlZCQb3bPlKsXo1SYJg6ME.getViewTreeObserver().addOnPreDrawListener(onPreDrawListener);
            boolean add = this.f799hxYOFxFjLpN1maJuWNxUV40nExCGxsxkDPOTgtzMu4zlZCQb3bPlKsXo1SYJg6ME.getOverlayManager().add(this.f801hxYOFxFjLpN1maJuWNxUV40nExCGxsxkDPOTgtzMu4zlZCQb3bPlKsXo1SYJg6ME);
        }
        if (this.f801hxYOFxFjLpN1maJuWNxUV40nExCGxsxkDPOTgtzMu4zlZCQb3bPlKsXo1SYJg6ME == null) {
            return;
        }
        if (z2) {
            if (this.f801hxYOFxFjLpN1maJuWNxUV40nExCGxsxkDPOTgtzMu4zlZCQb3bPlKsXo1SYJg6ME.getOrientationProvider() != null) {
                boolean enableCompass = this.f801hxYOFxFjLpN1maJuWNxUV40nExCGxsxkDPOTgtzMu4zlZCQb3bPlKsXo1SYJg6ME.enableCompass();
            } else {
                new InternalCompassOrientationProvider(this.f799hxYOFxFjLpN1maJuWNxUV40nExCGxsxkDPOTgtzMu4zlZCQb3bPlKsXo1SYJg6ME.getContext());
                boolean enableCompass2 = this.f801hxYOFxFjLpN1maJuWNxUV40nExCGxsxkDPOTgtzMu4zlZCQb3bPlKsXo1SYJg6ME.enableCompass(iOrientationProvider);
            }
            this.f801hxYOFxFjLpN1maJuWNxUV40nExCGxsxkDPOTgtzMu4zlZCQb3bPlKsXo1SYJg6ME.onOrientationChanged(this.M2XSrcNVsTj86KbWYhtzmFwqCl4FRN4hJC3YQ2jC5nTG9V14APZgqJsIs4HMKSeu, (IOrientationProvider) null);
            return;
        }
        this.M2XSrcNVsTj86KbWYhtzmFwqCl4FRN4hJC3YQ2jC5nTG9V14APZgqJsIs4HMKSeu = this.f801hxYOFxFjLpN1maJuWNxUV40nExCGxsxkDPOTgtzMu4zlZCQb3bPlKsXo1SYJg6ME.getOrientation();
        this.f801hxYOFxFjLpN1maJuWNxUV40nExCGxsxkDPOTgtzMu4zlZCQb3bPlKsXo1SYJg6ME.disableCompass();
    }

    public boolean isCompassEnabled() {
        return this.f801hxYOFxFjLpN1maJuWNxUV40nExCGxsxkDPOTgtzMu4zlZCQb3bPlKsXo1SYJg6ME != null && this.f801hxYOFxFjLpN1maJuWNxUV40nExCGxsxkDPOTgtzMu4zlZCQb3bPlKsXo1SYJg6ME.isCompassEnabled();
    }

    public void setZoomControlEnabled(boolean z) {
        boolean z2 = z;
        if (this.XkTExsgDjzL7UIaxQorrmSTr7jZbDxCmXSVfTpg7zAbwFxeOpcI2x1hAyx12QFiS != z2) {
            this.f798hxYOFxFjLpN1maJuWNxUV40nExCGxsxkDPOTgtzMu4zlZCQb3bPlKsXo1SYJg6ME.setVisibility(z2 ? 0 : 8);
            this.XkTExsgDjzL7UIaxQorrmSTr7jZbDxCmXSVfTpg7zAbwFxeOpcI2x1hAyx12QFiS = z2;
            this.hxYOFxFjLpN1maJuWNxUV40nExCGxsxkDPOTgtzMu4zlZCQb3bPlKsXo1SYJg6ME.invalidate();
        }
    }

    public boolean isZoomControlEnabled() {
        return this.XkTExsgDjzL7UIaxQorrmSTr7jZbDxCmXSVfTpg7zAbwFxeOpcI2x1hAyx12QFiS;
    }

    public void setShowUserEnabled(boolean z) {
        boolean z2 = z;
        this.f804hxYOFxFjLpN1maJuWNxUV40nExCGxsxkDPOTgtzMu4zlZCQb3bPlKsXo1SYJg6ME.setEnabled(z2);
        if (z2) {
            boolean enableMyLocation = this.f804hxYOFxFjLpN1maJuWNxUV40nExCGxsxkDPOTgtzMu4zlZCQb3bPlKsXo1SYJg6ME.enableMyLocation();
            boolean add = this.f799hxYOFxFjLpN1maJuWNxUV40nExCGxsxkDPOTgtzMu4zlZCQb3bPlKsXo1SYJg6ME.getOverlayManager().add(this.f804hxYOFxFjLpN1maJuWNxUV40nExCGxsxkDPOTgtzMu4zlZCQb3bPlKsXo1SYJg6ME);
            return;
        }
        this.f804hxYOFxFjLpN1maJuWNxUV40nExCGxsxkDPOTgtzMu4zlZCQb3bPlKsXo1SYJg6ME.disableMyLocation();
        boolean remove = this.f799hxYOFxFjLpN1maJuWNxUV40nExCGxsxkDPOTgtzMu4zlZCQb3bPlKsXo1SYJg6ME.getOverlayManager().remove(this.f804hxYOFxFjLpN1maJuWNxUV40nExCGxsxkDPOTgtzMu4zlZCQb3bPlKsXo1SYJg6ME);
    }

    public boolean isShowUserEnabled() {
        return this.f804hxYOFxFjLpN1maJuWNxUV40nExCGxsxkDPOTgtzMu4zlZCQb3bPlKsXo1SYJg6ME.isEnabled();
    }

    public void setRotationEnabled(boolean z) {
        RotationGestureOverlay rotationGestureOverlay;
        boolean z2 = z;
        if (z2 && this.f802hxYOFxFjLpN1maJuWNxUV40nExCGxsxkDPOTgtzMu4zlZCQb3bPlKsXo1SYJg6ME == null) {
            new RotationGestureOverlay(this.f799hxYOFxFjLpN1maJuWNxUV40nExCGxsxkDPOTgtzMu4zlZCQb3bPlKsXo1SYJg6ME);
            this.f802hxYOFxFjLpN1maJuWNxUV40nExCGxsxkDPOTgtzMu4zlZCQb3bPlKsXo1SYJg6ME = rotationGestureOverlay;
        }
        if (this.f802hxYOFxFjLpN1maJuWNxUV40nExCGxsxkDPOTgtzMu4zlZCQb3bPlKsXo1SYJg6ME != null) {
            this.f802hxYOFxFjLpN1maJuWNxUV40nExCGxsxkDPOTgtzMu4zlZCQb3bPlKsXo1SYJg6ME.setEnabled(z2);
            if (z2) {
                boolean add = this.f799hxYOFxFjLpN1maJuWNxUV40nExCGxsxkDPOTgtzMu4zlZCQb3bPlKsXo1SYJg6ME.getOverlayManager().add(this.f802hxYOFxFjLpN1maJuWNxUV40nExCGxsxkDPOTgtzMu4zlZCQb3bPlKsXo1SYJg6ME);
            } else {
                boolean remove = this.f799hxYOFxFjLpN1maJuWNxUV40nExCGxsxkDPOTgtzMu4zlZCQb3bPlKsXo1SYJg6ME.getOverlayManager().remove(this.f802hxYOFxFjLpN1maJuWNxUV40nExCGxsxkDPOTgtzMu4zlZCQb3bPlKsXo1SYJg6ME);
            }
        }
    }

    public boolean isRotationEnabled() {
        return this.f802hxYOFxFjLpN1maJuWNxUV40nExCGxsxkDPOTgtzMu4zlZCQb3bPlKsXo1SYJg6ME != null && this.f802hxYOFxFjLpN1maJuWNxUV40nExCGxsxkDPOTgtzMu4zlZCQb3bPlKsXo1SYJg6ME.isEnabled();
    }

    public void setPanEnabled(boolean z) {
        boolean hxYOFxFjLpN1maJuWNxUV40nExCGxsxkDPOTgtzMu4zlZCQb3bPlKsXo1SYJg6ME2 = C1230e.hxYOFxFjLpN1maJuWNxUV40nExCGxsxkDPOTgtzMu4zlZCQb3bPlKsXo1SYJg6ME(this.f797hxYOFxFjLpN1maJuWNxUV40nExCGxsxkDPOTgtzMu4zlZCQb3bPlKsXo1SYJg6ME, z);
    }

    public boolean isPanEnabled() {
        return this.f797hxYOFxFjLpN1maJuWNxUV40nExCGxsxkDPOTgtzMu4zlZCQb3bPlKsXo1SYJg6ME.muMCPHZoR5LDcgNaOoyYPcH1tjqU9LHoHV7Yzgex9Dj2uE6xbcPRtlEMsWbJpsbN;
    }

    public void panTo(double d, double d2, int i, double d3) {
        IGeoPoint iGeoPoint;
        double d4 = d3;
        new GeoPoint(d, d2);
        this.f799hxYOFxFjLpN1maJuWNxUV40nExCGxsxkDPOTgtzMu4zlZCQb3bPlKsXo1SYJg6ME.getController().animateTo(iGeoPoint);
        if (this.f799hxYOFxFjLpN1maJuWNxUV40nExCGxsxkDPOTgtzMu4zlZCQb3bPlKsXo1SYJg6ME.getController().zoomTo((double) i)) {
            Animation animation = this.f799hxYOFxFjLpN1maJuWNxUV40nExCGxsxkDPOTgtzMu4zlZCQb3bPlKsXo1SYJg6ME.getAnimation();
            Animation animation2 = animation;
            if (animation != null) {
                animation2.setDuration((long) (d4 * 1000.0d));
            }
        }
    }

    public void addEventListener(MapFactory.MapEventListener mapEventListener) {
        MapFactory.MapEventListener mapEventListener2 = mapEventListener;
        boolean add = this.vwEpIRqEf6xdtwTR9dehwBO7JUhyLV6iEzEK2WqfPN10eUMQDPn3AUmqAFfsnr6R.add(mapEventListener2);
        if ((this.wfWoJD9s05zGciiq11jj7TXqW1AmJIE9BV68DWBBkLZSv9NayRoTG3XJb9WJMlyD || ViewCompat.isAttachedToWindow(this.f799hxYOFxFjLpN1maJuWNxUV40nExCGxsxkDPOTgtzMu4zlZCQb3bPlKsXo1SYJg6ME)) && this.form.canDispatchEvent((Component) null, "MapReady")) {
            this.wfWoJD9s05zGciiq11jj7TXqW1AmJIE9BV68DWBBkLZSv9NayRoTG3XJb9WJMlyD = true;
            mapEventListener2.onReady(this);
        }
    }

    public void addFeature(MapFactory.MapMarker mapMarker) {
        AsyncCallbackPair asyncCallbackPair;
        Marker marker;
        GeoPoint geoPoint;
        AsyncCallbackPair asyncCallbackPair2;
        MapFactory.MapMarker mapMarker2 = mapMarker;
        final MapFactory.MapMarker mapMarker3 = mapMarker2;
        new AsyncCallbackPair<Marker>(this) {

            /* renamed from: hxYOFxFjLpN1maJuWNxUV40nExCGxsxkDPOTgtzMu4zlZCQb3bPlKsXo1SYJg6ME  reason: collision with other field name */
            final /* synthetic */ C1206b f813hxYOFxFjLpN1maJuWNxUV40nExCGxsxkDPOTgtzMu4zlZCQb3bPlKsXo1SYJg6ME;

            {
                this.f813hxYOFxFjLpN1maJuWNxUV40nExCGxsxkDPOTgtzMu4zlZCQb3bPlKsXo1SYJg6ME = r6;
            }

            public final /* synthetic */ void onSuccess(Object obj) {
                Marker.OnMarkerClickListener onMarkerClickListener;
                Marker.OnMarkerDragListener onMarkerDragListener;
                Marker marker = (Marker) obj;
                new Marker.OnMarkerClickListener(this) {
                    private /* synthetic */ C12218 hxYOFxFjLpN1maJuWNxUV40nExCGxsxkDPOTgtzMu4zlZCQb3bPlKsXo1SYJg6ME;

                    {
                        this.hxYOFxFjLpN1maJuWNxUV40nExCGxsxkDPOTgtzMu4zlZCQb3bPlKsXo1SYJg6ME = r5;
                    }

                    public final boolean onMarkerClick(Marker marker, MapView mapView) {
                        Marker marker2 = marker;
                        MapView mapView2 = mapView;
                        for (MapFactory.MapEventListener onFeatureClick : C1206b.hxYOFxFjLpN1maJuWNxUV40nExCGxsxkDPOTgtzMu4zlZCQb3bPlKsXo1SYJg6ME(this.hxYOFxFjLpN1maJuWNxUV40nExCGxsxkDPOTgtzMu4zlZCQb3bPlKsXo1SYJg6ME.f813hxYOFxFjLpN1maJuWNxUV40nExCGxsxkDPOTgtzMu4zlZCQb3bPlKsXo1SYJg6ME)) {
                            onFeatureClick.onFeatureClick(mapMarker3);
                        }
                        if (mapMarker3.EnableInfobox()) {
                            marker2.showInfoWindow();
                        }
                        return false;
                    }

                    public final boolean onMarkerLongPress(Marker marker, MapView mapView) {
                        Marker marker2 = marker;
                        MapView mapView2 = mapView;
                        for (MapFactory.MapEventListener onFeatureLongPress : C1206b.hxYOFxFjLpN1maJuWNxUV40nExCGxsxkDPOTgtzMu4zlZCQb3bPlKsXo1SYJg6ME(this.hxYOFxFjLpN1maJuWNxUV40nExCGxsxkDPOTgtzMu4zlZCQb3bPlKsXo1SYJg6ME.f813hxYOFxFjLpN1maJuWNxUV40nExCGxsxkDPOTgtzMu4zlZCQb3bPlKsXo1SYJg6ME)) {
                            onFeatureLongPress.onFeatureLongPress(mapMarker3);
                        }
                        return false;
                    }
                };
                marker.setOnMarkerClickListener(onMarkerClickListener);
                new Marker.OnMarkerDragListener(this) {
                    private /* synthetic */ C12218 hxYOFxFjLpN1maJuWNxUV40nExCGxsxkDPOTgtzMu4zlZCQb3bPlKsXo1SYJg6ME;

                    {
                        this.hxYOFxFjLpN1maJuWNxUV40nExCGxsxkDPOTgtzMu4zlZCQb3bPlKsXo1SYJg6ME = r5;
                    }

                    public final void onMarkerDrag(Marker marker) {
                        Marker marker2 = marker;
                        for (MapFactory.MapEventListener onFeatureDrag : C1206b.hxYOFxFjLpN1maJuWNxUV40nExCGxsxkDPOTgtzMu4zlZCQb3bPlKsXo1SYJg6ME(this.hxYOFxFjLpN1maJuWNxUV40nExCGxsxkDPOTgtzMu4zlZCQb3bPlKsXo1SYJg6ME.f813hxYOFxFjLpN1maJuWNxUV40nExCGxsxkDPOTgtzMu4zlZCQb3bPlKsXo1SYJg6ME)) {
                            onFeatureDrag.onFeatureDrag(mapMarker3);
                        }
                    }

                    public final void onMarkerDragEnd(Marker marker) {
                        GeoPoint position = marker.getPosition();
                        mapMarker3.updateLocation(position.getLatitude(), position.getLongitude());
                        for (MapFactory.MapEventListener onFeatureStopDrag : C1206b.hxYOFxFjLpN1maJuWNxUV40nExCGxsxkDPOTgtzMu4zlZCQb3bPlKsXo1SYJg6ME(this.hxYOFxFjLpN1maJuWNxUV40nExCGxsxkDPOTgtzMu4zlZCQb3bPlKsXo1SYJg6ME.f813hxYOFxFjLpN1maJuWNxUV40nExCGxsxkDPOTgtzMu4zlZCQb3bPlKsXo1SYJg6ME)) {
                            onFeatureStopDrag.onFeatureStopDrag(mapMarker3);
                        }
                    }

                    public final void onMarkerDragStart(Marker marker) {
                        Marker marker2 = marker;
                        for (MapFactory.MapEventListener onFeatureStartDrag : C1206b.hxYOFxFjLpN1maJuWNxUV40nExCGxsxkDPOTgtzMu4zlZCQb3bPlKsXo1SYJg6ME(this.hxYOFxFjLpN1maJuWNxUV40nExCGxsxkDPOTgtzMu4zlZCQb3bPlKsXo1SYJg6ME.f813hxYOFxFjLpN1maJuWNxUV40nExCGxsxkDPOTgtzMu4zlZCQb3bPlKsXo1SYJg6ME)) {
                            onFeatureStartDrag.onFeatureStartDrag(mapMarker3);
                        }
                    }
                };
                marker.setOnMarkerDragListener(onMarkerDragListener);
                if (mapMarker3.Visible()) {
                    this.f813hxYOFxFjLpN1maJuWNxUV40nExCGxsxkDPOTgtzMu4zlZCQb3bPlKsXo1SYJg6ME.hxYOFxFjLpN1maJuWNxUV40nExCGxsxkDPOTgtzMu4zlZCQb3bPlKsXo1SYJg6ME((OverlayWithIW) marker);
                } else {
                    this.f813hxYOFxFjLpN1maJuWNxUV40nExCGxsxkDPOTgtzMu4zlZCQb3bPlKsXo1SYJg6ME.B8WBXPBCF2jGfUDZZU2zV5EYdqbUBu0lAZ0THCEqYyuE8VACR9dY7rDnwBIqh64T((OverlayWithIW) marker);
                }
            }

            public final void onFailure(String str) {
                int e = Log.e(C1206b.TAG, "Unable to create marker: ".concat(String.valueOf(str)));
            }
        };
        MapFactory.MapMarker mapMarker4 = mapMarker2;
        new Marker(this.f799hxYOFxFjLpN1maJuWNxUV40nExCGxsxkDPOTgtzMu4zlZCQb3bPlKsXo1SYJg6ME);
        Marker marker2 = marker;
        OverlayWithIW put = this.moH2L3ThNq0Qhevz3oD0wpnUoXefdMi3gJ5JHfEbjrtgsvRvpM1e34BZQYI4s5i0.put(mapMarker4, marker2);
        marker2.setDraggable(mapMarker4.Draggable());
        marker2.setTitle(mapMarker4.Title());
        marker2.setSnippet(mapMarker4.Description());
        new GeoPoint(mapMarker4.Latitude(), mapMarker4.Longitude());
        marker2.setPosition(geoPoint);
        marker2.setAnchor(0.5f, 1.0f);
        final Marker marker3 = marker2;
        new AsyncCallbackFacade<Drawable, Marker>(this, asyncCallbackPair) {
            private /* synthetic */ C1206b hxYOFxFjLpN1maJuWNxUV40nExCGxsxkDPOTgtzMu4zlZCQb3bPlKsXo1SYJg6ME;

            {
                this.hxYOFxFjLpN1maJuWNxUV40nExCGxsxkDPOTgtzMu4zlZCQb3bPlKsXo1SYJg6ME = r7;
            }

            public final /* synthetic */ void onSuccess(Object obj) {
                marker3.setIcon((Drawable) obj);
                this.callback.onSuccess(marker3);
            }

            public final void onFailure(String str) {
                this.callback.onFailure(str);
            }
        };
        hxYOFxFjLpN1maJuWNxUV40nExCGxsxkDPOTgtzMu4zlZCQb3bPlKsXo1SYJg6ME(mapMarker4, (AsyncCallbackPair<Drawable>) asyncCallbackPair2);
    }

    public void addFeature(MapFactory.MapLineString mapLineString) {
        Polyline polyline;
        Polyline.OnClickListener onClickListener;
        Polyline.OnDragListener onDragListener;
        MapFactory.MapLineString mapLineString2 = mapLineString;
        MapFactory.MapLineString mapLineString3 = mapLineString2;
        new Polyline();
        Polyline polyline2 = polyline;
        Polyline polyline3 = polyline2;
        polyline2.setDraggable(mapLineString3.Draggable());
        polyline3.setTitle(mapLineString3.Title());
        polyline3.setSnippet(mapLineString3.Description());
        polyline3.setPoints(mapLineString3.getPoints());
        polyline3.setColor(mapLineString3.StrokeColor());
        polyline3.setWidth((float) mapLineString3.StrokeWidth());
        polyline3.setInfoWindow(this.f803hxYOFxFjLpN1maJuWNxUV40nExCGxsxkDPOTgtzMu4zlZCQb3bPlKsXo1SYJg6ME);
        Polyline polyline4 = polyline3;
        OverlayWithIW put = this.moH2L3ThNq0Qhevz3oD0wpnUoXefdMi3gJ5JHfEbjrtgsvRvpM1e34BZQYI4s5i0.put(mapLineString2, polyline4);
        final MapFactory.MapLineString mapLineString4 = mapLineString2;
        new Polyline.OnClickListener(this) {

            /* renamed from: hxYOFxFjLpN1maJuWNxUV40nExCGxsxkDPOTgtzMu4zlZCQb3bPlKsXo1SYJg6ME  reason: collision with other field name */
            private /* synthetic */ C1206b f814hxYOFxFjLpN1maJuWNxUV40nExCGxsxkDPOTgtzMu4zlZCQb3bPlKsXo1SYJg6ME;

            {
                this.f814hxYOFxFjLpN1maJuWNxUV40nExCGxsxkDPOTgtzMu4zlZCQb3bPlKsXo1SYJg6ME = r6;
            }

            public final boolean onClick(Polyline polyline, MapView mapView, GeoPoint geoPoint) {
                Polyline polyline2 = polyline;
                MapView mapView2 = mapView;
                GeoPoint geoPoint2 = geoPoint;
                for (MapFactory.MapEventListener onFeatureClick : C1206b.hxYOFxFjLpN1maJuWNxUV40nExCGxsxkDPOTgtzMu4zlZCQb3bPlKsXo1SYJg6ME(this.f814hxYOFxFjLpN1maJuWNxUV40nExCGxsxkDPOTgtzMu4zlZCQb3bPlKsXo1SYJg6ME)) {
                    onFeatureClick.onFeatureClick(mapLineString4);
                }
                if (mapLineString4.EnableInfobox()) {
                    polyline2.showInfoWindow(geoPoint2);
                }
                return true;
            }

            public final boolean onLongClick(Polyline polyline, MapView mapView, GeoPoint geoPoint) {
                Polyline polyline2 = polyline;
                MapView mapView2 = mapView;
                GeoPoint geoPoint2 = geoPoint;
                for (MapFactory.MapEventListener onFeatureLongPress : C1206b.hxYOFxFjLpN1maJuWNxUV40nExCGxsxkDPOTgtzMu4zlZCQb3bPlKsXo1SYJg6ME(this.f814hxYOFxFjLpN1maJuWNxUV40nExCGxsxkDPOTgtzMu4zlZCQb3bPlKsXo1SYJg6ME)) {
                    onFeatureLongPress.onFeatureLongPress(mapLineString4);
                }
                return true;
            }
        };
        polyline4.setOnClickListener(onClickListener);
        final MapFactory.MapLineString mapLineString5 = mapLineString2;
        new Polyline.OnDragListener(this) {

            /* renamed from: hxYOFxFjLpN1maJuWNxUV40nExCGxsxkDPOTgtzMu4zlZCQb3bPlKsXo1SYJg6ME  reason: collision with other field name */
            private /* synthetic */ C1206b f805hxYOFxFjLpN1maJuWNxUV40nExCGxsxkDPOTgtzMu4zlZCQb3bPlKsXo1SYJg6ME;

            {
                this.f805hxYOFxFjLpN1maJuWNxUV40nExCGxsxkDPOTgtzMu4zlZCQb3bPlKsXo1SYJg6ME = r6;
            }

            public final void onDragStart(Polyline polyline) {
                Polyline polyline2 = polyline;
                for (MapFactory.MapEventListener onFeatureStartDrag : C1206b.hxYOFxFjLpN1maJuWNxUV40nExCGxsxkDPOTgtzMu4zlZCQb3bPlKsXo1SYJg6ME(this.f805hxYOFxFjLpN1maJuWNxUV40nExCGxsxkDPOTgtzMu4zlZCQb3bPlKsXo1SYJg6ME)) {
                    onFeatureStartDrag.onFeatureStartDrag(mapLineString5);
                }
            }

            public final void onDrag(Polyline polyline) {
                Polyline polyline2 = polyline;
                for (MapFactory.MapEventListener onFeatureDrag : C1206b.hxYOFxFjLpN1maJuWNxUV40nExCGxsxkDPOTgtzMu4zlZCQb3bPlKsXo1SYJg6ME(this.f805hxYOFxFjLpN1maJuWNxUV40nExCGxsxkDPOTgtzMu4zlZCQb3bPlKsXo1SYJg6ME)) {
                    onFeatureDrag.onFeatureDrag(mapLineString5);
                }
            }

            public final void onDragEnd(Polyline polyline) {
                mapLineString5.updatePoints(polyline.getPoints());
                for (MapFactory.MapEventListener onFeatureStopDrag : C1206b.hxYOFxFjLpN1maJuWNxUV40nExCGxsxkDPOTgtzMu4zlZCQb3bPlKsXo1SYJg6ME(this.f805hxYOFxFjLpN1maJuWNxUV40nExCGxsxkDPOTgtzMu4zlZCQb3bPlKsXo1SYJg6ME)) {
                    onFeatureStopDrag.onFeatureStopDrag(mapLineString5);
                }
            }
        };
        polyline4.setOnDragListener(onDragListener);
        if (mapLineString2.Visible()) {
            hxYOFxFjLpN1maJuWNxUV40nExCGxsxkDPOTgtzMu4zlZCQb3bPlKsXo1SYJg6ME((OverlayWithIW) polyline4);
        } else {
            B8WBXPBCF2jGfUDZZU2zV5EYdqbUBu0lAZ0THCEqYyuE8VACR9dY7rDnwBIqh64T((OverlayWithIW) polyline4);
        }
    }

    private void hxYOFxFjLpN1maJuWNxUV40nExCGxsxkDPOTgtzMu4zlZCQb3bPlKsXo1SYJg6ME(MapFactory.MapFeature mapFeature, Polygon polygon) {
        Polygon.OnClickListener onClickListener;
        Polygon.OnDragListener onDragListener;
        MapFactory.MapFeature mapFeature2 = mapFeature;
        Polygon polygon2 = polygon;
        OverlayWithIW put = this.moH2L3ThNq0Qhevz3oD0wpnUoXefdMi3gJ5JHfEbjrtgsvRvpM1e34BZQYI4s5i0.put(mapFeature2, polygon2);
        final MapFactory.MapFeature mapFeature3 = mapFeature2;
        new Polygon.OnClickListener(this) {
            private /* synthetic */ C1206b hxYOFxFjLpN1maJuWNxUV40nExCGxsxkDPOTgtzMu4zlZCQb3bPlKsXo1SYJg6ME;

            {
                this.hxYOFxFjLpN1maJuWNxUV40nExCGxsxkDPOTgtzMu4zlZCQb3bPlKsXo1SYJg6ME = r6;
            }

            public final boolean onLongClick(Polygon polygon, MapView mapView, GeoPoint geoPoint) {
                Polygon polygon2 = polygon;
                MapView mapView2 = mapView;
                GeoPoint geoPoint2 = geoPoint;
                for (MapFactory.MapEventListener onFeatureLongPress : C1206b.hxYOFxFjLpN1maJuWNxUV40nExCGxsxkDPOTgtzMu4zlZCQb3bPlKsXo1SYJg6ME(this.hxYOFxFjLpN1maJuWNxUV40nExCGxsxkDPOTgtzMu4zlZCQb3bPlKsXo1SYJg6ME)) {
                    onFeatureLongPress.onFeatureLongPress(mapFeature3);
                }
                return true;
            }

            public final boolean onClick(Polygon polygon, MapView mapView, GeoPoint geoPoint) {
                Polygon polygon2 = polygon;
                MapView mapView2 = mapView;
                GeoPoint geoPoint2 = geoPoint;
                for (MapFactory.MapEventListener onFeatureClick : C1206b.hxYOFxFjLpN1maJuWNxUV40nExCGxsxkDPOTgtzMu4zlZCQb3bPlKsXo1SYJg6ME(this.hxYOFxFjLpN1maJuWNxUV40nExCGxsxkDPOTgtzMu4zlZCQb3bPlKsXo1SYJg6ME)) {
                    onFeatureClick.onFeatureClick(mapFeature3);
                }
                if (mapFeature3.EnableInfobox()) {
                    polygon2.showInfoWindow(geoPoint2);
                }
                return true;
            }
        };
        polygon2.setOnClickListener(onClickListener);
        final MapFactory.MapFeature mapFeature4 = mapFeature2;
        new Polygon.OnDragListener(this) {
            private /* synthetic */ C1206b hxYOFxFjLpN1maJuWNxUV40nExCGxsxkDPOTgtzMu4zlZCQb3bPlKsXo1SYJg6ME;

            {
                this.hxYOFxFjLpN1maJuWNxUV40nExCGxsxkDPOTgtzMu4zlZCQb3bPlKsXo1SYJg6ME = r6;
            }

            public final void onDragStart(Polygon polygon) {
                Polygon polygon2 = polygon;
                for (MapFactory.MapEventListener onFeatureStartDrag : C1206b.hxYOFxFjLpN1maJuWNxUV40nExCGxsxkDPOTgtzMu4zlZCQb3bPlKsXo1SYJg6ME(this.hxYOFxFjLpN1maJuWNxUV40nExCGxsxkDPOTgtzMu4zlZCQb3bPlKsXo1SYJg6ME)) {
                    onFeatureStartDrag.onFeatureStartDrag(mapFeature4);
                }
            }

            public final void onDrag(Polygon polygon) {
                Polygon polygon2 = polygon;
                for (MapFactory.MapEventListener onFeatureDrag : C1206b.hxYOFxFjLpN1maJuWNxUV40nExCGxsxkDPOTgtzMu4zlZCQb3bPlKsXo1SYJg6ME(this.hxYOFxFjLpN1maJuWNxUV40nExCGxsxkDPOTgtzMu4zlZCQb3bPlKsXo1SYJg6ME)) {
                    onFeatureDrag.onFeatureDrag(mapFeature4);
                }
            }

            public final void onDragEnd(Polygon polygon) {
                List list;
                Polygon polygon2 = polygon;
                if (mapFeature4 instanceof MapFactory.MapCircle) {
                    double d = 0.0d;
                    double d2 = 0.0d;
                    int size = polygon2.getPoints().size();
                    for (GeoPoint next : polygon2.getPoints()) {
                        d += next.getLatitude();
                        d2 += next.getLongitude();
                    }
                    if (size > 0) {
                        ((MapFactory.MapCircle) mapFeature4).updateCenter(d / ((double) size), d2 / ((double) size));
                    } else {
                        ((MapFactory.MapCircle) mapFeature4).updateCenter(0.0d, 0.0d);
                    }
                } else {
                    if (mapFeature4 instanceof MapFactory.MapRectangle) {
                        double d3 = -90.0d;
                        double d4 = -180.0d;
                        double d5 = 180.0d;
                        double d6 = 90.0d;
                        for (GeoPoint next2 : polygon2.getPoints()) {
                            double latitude = next2.getLatitude();
                            double longitude = next2.getLongitude();
                            d3 = Math.max(d3, latitude);
                            d6 = Math.min(d6, latitude);
                            d4 = Math.max(d4, longitude);
                            d5 = Math.min(d5, longitude);
                        }
                        ((MapFactory.MapRectangle) mapFeature4).updateBounds(d3, d5, d6, d4);
                    } else {
                        MapFactory.MapPolygon mapPolygon = (MapFactory.MapPolygon) mapFeature4;
                        new ArrayList();
                        List list2 = list;
                        for (Polygon points : ((C1229d) polygon2).DmQGLROFyZ9Eo0RSsJcpZNxJZjgcsPDfYPi3awNwmyyErT71sGU5mvgG4PDW3yL) {
                            boolean add = list2.add(points.getPoints());
                        }
                        mapPolygon.updatePoints(list2);
                        ((MapFactory.MapPolygon) mapFeature4).updateHolePoints(((C1229d) polygon2).hxYOFxFjLpN1maJuWNxUV40nExCGxsxkDPOTgtzMu4zlZCQb3bPlKsXo1SYJg6ME());
                    }
                }
                for (MapFactory.MapEventListener onFeatureStopDrag : C1206b.hxYOFxFjLpN1maJuWNxUV40nExCGxsxkDPOTgtzMu4zlZCQb3bPlKsXo1SYJg6ME(this.hxYOFxFjLpN1maJuWNxUV40nExCGxsxkDPOTgtzMu4zlZCQb3bPlKsXo1SYJg6ME)) {
                    onFeatureStopDrag.onFeatureStopDrag(mapFeature4);
                }
            }
        };
        polygon2.setOnDragListener(onDragListener);
        if (mapFeature2.Visible()) {
            hxYOFxFjLpN1maJuWNxUV40nExCGxsxkDPOTgtzMu4zlZCQb3bPlKsXo1SYJg6ME((OverlayWithIW) polygon2);
        } else {
            B8WBXPBCF2jGfUDZZU2zV5EYdqbUBu0lAZ0THCEqYyuE8VACR9dY7rDnwBIqh64T((OverlayWithIW) polygon2);
        }
    }

    public void addFeature(MapFactory.MapPolygon mapPolygon) {
        C1229d dVar;
        MapFactory.MapPolygon mapPolygon2 = mapPolygon;
        MapFactory.MapPolygon mapPolygon3 = mapPolygon2;
        MapFactory.MapPolygon mapPolygon4 = mapPolygon2;
        new C1229d();
        C1229d dVar2 = dVar;
        hxYOFxFjLpN1maJuWNxUV40nExCGxsxkDPOTgtzMu4zlZCQb3bPlKsXo1SYJg6ME((Polygon) dVar2, (MapFactory.MapFeature) mapPolygon4);
        dVar2.B8WBXPBCF2jGfUDZZU2zV5EYdqbUBu0lAZ0THCEqYyuE8VACR9dY7rDnwBIqh64T(mapPolygon4.getPoints());
        dVar2.wq07duYRO6iFAgWM70EZOSvbCMKs1QznMRJKrct0XuHOBYqCk3XqOKtSBGIpDou(mapPolygon4.getHolePoints());
        hxYOFxFjLpN1maJuWNxUV40nExCGxsxkDPOTgtzMu4zlZCQb3bPlKsXo1SYJg6ME((MapFactory.MapFeature) mapPolygon3, (Polygon) dVar2);
    }

    public void addFeature(MapFactory.MapCircle mapCircle) {
        Polygon polygon;
        GeoPoint geoPoint;
        MapFactory.MapCircle mapCircle2 = mapCircle;
        MapFactory.MapCircle mapCircle3 = mapCircle2;
        MapFactory.MapCircle mapCircle4 = mapCircle2;
        new Polygon();
        Polygon polygon2 = polygon;
        hxYOFxFjLpN1maJuWNxUV40nExCGxsxkDPOTgtzMu4zlZCQb3bPlKsXo1SYJg6ME(polygon2, (MapFactory.MapFeature) mapCircle4);
        new GeoPoint(mapCircle4.Latitude(), mapCircle4.Longitude());
        polygon2.setPoints(Polygon.pointsAsCircle(geoPoint, mapCircle4.Radius()));
        hxYOFxFjLpN1maJuWNxUV40nExCGxsxkDPOTgtzMu4zlZCQb3bPlKsXo1SYJg6ME((MapFactory.MapFeature) mapCircle3, polygon2);
    }

    public void addFeature(MapFactory.MapRectangle mapRectangle) {
        BoundingBox boundingBox;
        Polygon polygon;
        List list;
        MapFactory.MapRectangle mapRectangle2 = mapRectangle;
        MapFactory.MapRectangle mapRectangle3 = mapRectangle2;
        MapFactory.MapRectangle mapRectangle4 = mapRectangle2;
        new BoundingBox(mapRectangle4.NorthLatitude(), mapRectangle4.EastLongitude(), mapRectangle4.SouthLatitude(), mapRectangle4.WestLongitude());
        new Polygon();
        Polygon polygon2 = polygon;
        hxYOFxFjLpN1maJuWNxUV40nExCGxsxkDPOTgtzMu4zlZCQb3bPlKsXo1SYJg6ME(polygon2, (MapFactory.MapFeature) mapRectangle4);
        new ArrayList(Polygon.pointsAsRect(boundingBox));
        polygon2.setPoints(list);
        hxYOFxFjLpN1maJuWNxUV40nExCGxsxkDPOTgtzMu4zlZCQb3bPlKsXo1SYJg6ME((MapFactory.MapFeature) mapRectangle3, polygon2);
    }

    public void removeFeature(MapFactory.MapFeature mapFeature) {
        MapFactory.MapFeature mapFeature2 = mapFeature;
        boolean remove = this.f799hxYOFxFjLpN1maJuWNxUV40nExCGxsxkDPOTgtzMu4zlZCQb3bPlKsXo1SYJg6ME.getOverlayManager().remove(this.moH2L3ThNq0Qhevz3oD0wpnUoXefdMi3gJ5JHfEbjrtgsvRvpM1e34BZQYI4s5i0.get(mapFeature2));
        OverlayWithIW remove2 = this.moH2L3ThNq0Qhevz3oD0wpnUoXefdMi3gJ5JHfEbjrtgsvRvpM1e34BZQYI4s5i0.remove(mapFeature2);
    }

    public void updateFeaturePosition(MapFactory.MapMarker mapMarker) {
        GeoPoint geoPoint;
        MapFactory.MapMarker mapMarker2 = mapMarker;
        Marker marker = (Marker) this.moH2L3ThNq0Qhevz3oD0wpnUoXefdMi3gJ5JHfEbjrtgsvRvpM1e34BZQYI4s5i0.get(mapMarker2);
        Marker marker2 = marker;
        if (marker != null) {
            marker2.setAnchor(sSQuIBUVrx5rcdxHqHgqC6bPLuuDnxnBF7e7BJeOxmxr54l6ArzFZvC3SGBTuUdt[mapMarker2.AnchorHorizontal()], mBLKdPlRolYfdtud5Z8giGWyhURWoCw9yHsOv08fUnIBAM4MuKAWkHVD8nEhBmIT[mapMarker2.AnchorVertical()]);
            new GeoPoint(mapMarker2.Latitude(), mapMarker2.Longitude());
            marker2.setPosition(geoPoint);
            this.f799hxYOFxFjLpN1maJuWNxUV40nExCGxsxkDPOTgtzMu4zlZCQb3bPlKsXo1SYJg6ME.invalidate();
        }
    }

    public void updateFeaturePosition(MapFactory.MapLineString mapLineString) {
        MapFactory.MapLineString mapLineString2 = mapLineString;
        Polyline polyline = (Polyline) this.moH2L3ThNq0Qhevz3oD0wpnUoXefdMi3gJ5JHfEbjrtgsvRvpM1e34BZQYI4s5i0.get(mapLineString2);
        Polyline polyline2 = polyline;
        if (polyline != null) {
            polyline2.setPoints(mapLineString2.getPoints());
            this.f799hxYOFxFjLpN1maJuWNxUV40nExCGxsxkDPOTgtzMu4zlZCQb3bPlKsXo1SYJg6ME.invalidate();
        }
    }

    public void updateFeaturePosition(MapFactory.MapPolygon mapPolygon) {
        MapFactory.MapPolygon mapPolygon2 = mapPolygon;
        C1229d dVar = (C1229d) this.moH2L3ThNq0Qhevz3oD0wpnUoXefdMi3gJ5JHfEbjrtgsvRvpM1e34BZQYI4s5i0.get(mapPolygon2);
        C1229d dVar2 = dVar;
        if (dVar != null) {
            dVar2.B8WBXPBCF2jGfUDZZU2zV5EYdqbUBu0lAZ0THCEqYyuE8VACR9dY7rDnwBIqh64T(mapPolygon2.getPoints());
            this.f799hxYOFxFjLpN1maJuWNxUV40nExCGxsxkDPOTgtzMu4zlZCQb3bPlKsXo1SYJg6ME.invalidate();
        }
    }

    public void updateFeatureHoles(MapFactory.MapPolygon mapPolygon) {
        MapFactory.MapPolygon mapPolygon2 = mapPolygon;
        C1229d dVar = (C1229d) this.moH2L3ThNq0Qhevz3oD0wpnUoXefdMi3gJ5JHfEbjrtgsvRvpM1e34BZQYI4s5i0.get(mapPolygon2);
        C1229d dVar2 = dVar;
        if (dVar != null) {
            dVar2.wq07duYRO6iFAgWM70EZOSvbCMKs1QznMRJKrct0XuHOBYqCk3XqOKtSBGIpDou(mapPolygon2.getHolePoints());
            this.f799hxYOFxFjLpN1maJuWNxUV40nExCGxsxkDPOTgtzMu4zlZCQb3bPlKsXo1SYJg6ME.invalidate();
        }
    }

    public void updateFeaturePosition(MapFactory.MapCircle mapCircle) {
        GeoPoint geoPoint;
        MapFactory.MapCircle mapCircle2 = mapCircle;
        new GeoPoint(mapCircle2.Latitude(), mapCircle2.Longitude());
        GeoPoint geoPoint2 = geoPoint;
        Polygon polygon = (Polygon) this.moH2L3ThNq0Qhevz3oD0wpnUoXefdMi3gJ5JHfEbjrtgsvRvpM1e34BZQYI4s5i0.get(mapCircle2);
        Polygon polygon2 = polygon;
        if (polygon != null) {
            polygon2.setPoints(Polygon.pointsAsCircle(geoPoint2, mapCircle2.Radius()));
            this.f799hxYOFxFjLpN1maJuWNxUV40nExCGxsxkDPOTgtzMu4zlZCQb3bPlKsXo1SYJg6ME.invalidate();
        }
    }

    public void updateFeaturePosition(MapFactory.MapRectangle mapRectangle) {
        BoundingBox boundingBox;
        MapFactory.MapRectangle mapRectangle2 = mapRectangle;
        Polygon polygon = (Polygon) this.moH2L3ThNq0Qhevz3oD0wpnUoXefdMi3gJ5JHfEbjrtgsvRvpM1e34BZQYI4s5i0.get(mapRectangle2);
        Polygon polygon2 = polygon;
        if (polygon != null) {
            new BoundingBox(mapRectangle2.NorthLatitude(), mapRectangle2.EastLongitude(), mapRectangle2.SouthLatitude(), mapRectangle2.WestLongitude());
            polygon2.setPoints(Polygon.pointsAsRect(boundingBox));
            this.f799hxYOFxFjLpN1maJuWNxUV40nExCGxsxkDPOTgtzMu4zlZCQb3bPlKsXo1SYJg6ME.invalidate();
        }
    }

    public void updateFeatureFill(MapFactory.HasFill hasFill) {
        OverlayWithIWVisitor overlayWithIWVisitor;
        MapFactory.HasFill hasFill2 = hasFill;
        OverlayWithIW overlayWithIW = this.moH2L3ThNq0Qhevz3oD0wpnUoXefdMi3gJ5JHfEbjrtgsvRvpM1e34BZQYI4s5i0.get(hasFill2);
        OverlayWithIW overlayWithIW2 = overlayWithIW;
        if (overlayWithIW != null) {
            final MapFactory.HasFill hasFill3 = hasFill2;
            new OverlayWithIWVisitor(this) {

                /* renamed from: hxYOFxFjLpN1maJuWNxUV40nExCGxsxkDPOTgtzMu4zlZCQb3bPlKsXo1SYJg6ME  reason: collision with other field name */
                final /* synthetic */ C1206b f806hxYOFxFjLpN1maJuWNxUV40nExCGxsxkDPOTgtzMu4zlZCQb3bPlKsXo1SYJg6ME;

                {
                    this.f806hxYOFxFjLpN1maJuWNxUV40nExCGxsxkDPOTgtzMu4zlZCQb3bPlKsXo1SYJg6ME = r6;
                }

                public final void visit(Marker marker) {
                    AsyncCallbackPair asyncCallbackPair;
                    final Marker marker2 = marker;
                    new AsyncCallbackPair<Drawable>(this) {
                        private /* synthetic */ C121113 hxYOFxFjLpN1maJuWNxUV40nExCGxsxkDPOTgtzMu4zlZCQb3bPlKsXo1SYJg6ME;

                        {
                            this.hxYOFxFjLpN1maJuWNxUV40nExCGxsxkDPOTgtzMu4zlZCQb3bPlKsXo1SYJg6ME = r6;
                        }

                        public final /* synthetic */ void onSuccess(Object obj) {
                            marker2.setIcon((Drawable) obj);
                            C1206b.hxYOFxFjLpN1maJuWNxUV40nExCGxsxkDPOTgtzMu4zlZCQb3bPlKsXo1SYJg6ME(this.hxYOFxFjLpN1maJuWNxUV40nExCGxsxkDPOTgtzMu4zlZCQb3bPlKsXo1SYJg6ME.f806hxYOFxFjLpN1maJuWNxUV40nExCGxsxkDPOTgtzMu4zlZCQb3bPlKsXo1SYJg6ME).invalidate();
                        }

                        public final void onFailure(String str) {
                            int e = Log.e(C1206b.TAG, "Unable to update fill color for marker: ".concat(String.valueOf(str)));
                        }
                    };
                    this.f806hxYOFxFjLpN1maJuWNxUV40nExCGxsxkDPOTgtzMu4zlZCQb3bPlKsXo1SYJg6ME.hxYOFxFjLpN1maJuWNxUV40nExCGxsxkDPOTgtzMu4zlZCQb3bPlKsXo1SYJg6ME((MapFactory.MapMarker) hasFill3, (AsyncCallbackPair<Drawable>) asyncCallbackPair);
                }

                public final void visit(Polyline polyline) {
                }

                public final void visit(Polygon polygon) {
                    polygon.setFillColor(hasFill3.FillColor());
                    C1206b.hxYOFxFjLpN1maJuWNxUV40nExCGxsxkDPOTgtzMu4zlZCQb3bPlKsXo1SYJg6ME(this.f806hxYOFxFjLpN1maJuWNxUV40nExCGxsxkDPOTgtzMu4zlZCQb3bPlKsXo1SYJg6ME).invalidate();
                }
            };
            overlayWithIW2.accept(overlayWithIWVisitor);
        }
    }

    public void updateFeatureStroke(MapFactory.HasStroke hasStroke) {
        OverlayWithIWVisitor overlayWithIWVisitor;
        MapFactory.HasStroke hasStroke2 = hasStroke;
        OverlayWithIW overlayWithIW = this.moH2L3ThNq0Qhevz3oD0wpnUoXefdMi3gJ5JHfEbjrtgsvRvpM1e34BZQYI4s5i0.get(hasStroke2);
        OverlayWithIW overlayWithIW2 = overlayWithIW;
        if (overlayWithIW != null) {
            final MapFactory.HasStroke hasStroke3 = hasStroke2;
            new OverlayWithIWVisitor(this) {

                /* renamed from: hxYOFxFjLpN1maJuWNxUV40nExCGxsxkDPOTgtzMu4zlZCQb3bPlKsXo1SYJg6ME  reason: collision with other field name */
                final /* synthetic */ C1206b f808hxYOFxFjLpN1maJuWNxUV40nExCGxsxkDPOTgtzMu4zlZCQb3bPlKsXo1SYJg6ME;

                {
                    this.f808hxYOFxFjLpN1maJuWNxUV40nExCGxsxkDPOTgtzMu4zlZCQb3bPlKsXo1SYJg6ME = r6;
                }

                public final void visit(Marker marker) {
                    AsyncCallbackPair asyncCallbackPair;
                    final Marker marker2 = marker;
                    new AsyncCallbackPair<Drawable>(this) {
                        private /* synthetic */ C121314 hxYOFxFjLpN1maJuWNxUV40nExCGxsxkDPOTgtzMu4zlZCQb3bPlKsXo1SYJg6ME;

                        {
                            this.hxYOFxFjLpN1maJuWNxUV40nExCGxsxkDPOTgtzMu4zlZCQb3bPlKsXo1SYJg6ME = r6;
                        }

                        public final /* synthetic */ void onSuccess(Object obj) {
                            marker2.setIcon((Drawable) obj);
                            C1206b.hxYOFxFjLpN1maJuWNxUV40nExCGxsxkDPOTgtzMu4zlZCQb3bPlKsXo1SYJg6ME(this.hxYOFxFjLpN1maJuWNxUV40nExCGxsxkDPOTgtzMu4zlZCQb3bPlKsXo1SYJg6ME.f808hxYOFxFjLpN1maJuWNxUV40nExCGxsxkDPOTgtzMu4zlZCQb3bPlKsXo1SYJg6ME).invalidate();
                        }

                        public final void onFailure(String str) {
                            int e = Log.e(C1206b.TAG, "Unable to update stroke color for marker: ".concat(String.valueOf(str)));
                        }
                    };
                    this.f808hxYOFxFjLpN1maJuWNxUV40nExCGxsxkDPOTgtzMu4zlZCQb3bPlKsXo1SYJg6ME.hxYOFxFjLpN1maJuWNxUV40nExCGxsxkDPOTgtzMu4zlZCQb3bPlKsXo1SYJg6ME((MapFactory.MapMarker) hasStroke3, (AsyncCallbackPair<Drawable>) asyncCallbackPair);
                }

                public final void visit(Polyline polyline) {
                    DisplayMetrics displayMetrics;
                    Polyline polyline2 = polyline;
                    new DisplayMetrics();
                    DisplayMetrics displayMetrics2 = displayMetrics;
                    C1206b.hxYOFxFjLpN1maJuWNxUV40nExCGxsxkDPOTgtzMu4zlZCQb3bPlKsXo1SYJg6ME(this.f808hxYOFxFjLpN1maJuWNxUV40nExCGxsxkDPOTgtzMu4zlZCQb3bPlKsXo1SYJg6ME).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics2);
                    polyline2.setColor(hasStroke3.StrokeColor());
                    polyline2.setWidth(((float) hasStroke3.StrokeWidth()) * displayMetrics2.density);
                    C1206b.hxYOFxFjLpN1maJuWNxUV40nExCGxsxkDPOTgtzMu4zlZCQb3bPlKsXo1SYJg6ME(this.f808hxYOFxFjLpN1maJuWNxUV40nExCGxsxkDPOTgtzMu4zlZCQb3bPlKsXo1SYJg6ME).invalidate();
                }

                public final void visit(Polygon polygon) {
                    DisplayMetrics displayMetrics;
                    Polygon polygon2 = polygon;
                    new DisplayMetrics();
                    DisplayMetrics displayMetrics2 = displayMetrics;
                    C1206b.hxYOFxFjLpN1maJuWNxUV40nExCGxsxkDPOTgtzMu4zlZCQb3bPlKsXo1SYJg6ME(this.f808hxYOFxFjLpN1maJuWNxUV40nExCGxsxkDPOTgtzMu4zlZCQb3bPlKsXo1SYJg6ME).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics2);
                    polygon2.setStrokeColor(hasStroke3.StrokeColor());
                    polygon2.setStrokeWidth(((float) hasStroke3.StrokeWidth()) * displayMetrics2.density);
                    C1206b.hxYOFxFjLpN1maJuWNxUV40nExCGxsxkDPOTgtzMu4zlZCQb3bPlKsXo1SYJg6ME(this.f808hxYOFxFjLpN1maJuWNxUV40nExCGxsxkDPOTgtzMu4zlZCQb3bPlKsXo1SYJg6ME).invalidate();
                }
            };
            overlayWithIW2.accept(overlayWithIWVisitor);
        }
    }

    public void updateFeatureText(MapFactory.MapFeature mapFeature) {
        MapFactory.MapFeature mapFeature2 = mapFeature;
        OverlayWithIW overlayWithIW = this.moH2L3ThNq0Qhevz3oD0wpnUoXefdMi3gJ5JHfEbjrtgsvRvpM1e34BZQYI4s5i0.get(mapFeature2);
        OverlayWithIW overlayWithIW2 = overlayWithIW;
        if (overlayWithIW != null) {
            overlayWithIW2.setTitle(mapFeature2.Title());
            overlayWithIW2.setSnippet(mapFeature2.Description());
        }
    }

    public void updateFeatureDraggable(MapFactory.MapFeature mapFeature) {
        MapFactory.MapFeature mapFeature2 = mapFeature;
        OverlayWithIW overlayWithIW = this.moH2L3ThNq0Qhevz3oD0wpnUoXefdMi3gJ5JHfEbjrtgsvRvpM1e34BZQYI4s5i0.get(mapFeature2);
        OverlayWithIW overlayWithIW2 = overlayWithIW;
        if (overlayWithIW != null) {
            overlayWithIW2.setDraggable(mapFeature2.Draggable());
        }
    }

    public void updateFeatureImage(MapFactory.MapMarker mapMarker) {
        AsyncCallbackPair asyncCallbackPair;
        MapFactory.MapMarker mapMarker2 = mapMarker;
        Marker marker = (Marker) this.moH2L3ThNq0Qhevz3oD0wpnUoXefdMi3gJ5JHfEbjrtgsvRvpM1e34BZQYI4s5i0.get(mapMarker2);
        Marker marker2 = marker;
        if (marker != null) {
            final Marker marker3 = marker2;
            new AsyncCallbackPair<Drawable>(this) {
                private /* synthetic */ C1206b hxYOFxFjLpN1maJuWNxUV40nExCGxsxkDPOTgtzMu4zlZCQb3bPlKsXo1SYJg6ME;

                {
                    this.hxYOFxFjLpN1maJuWNxUV40nExCGxsxkDPOTgtzMu4zlZCQb3bPlKsXo1SYJg6ME = r6;
                }

                public final /* synthetic */ void onSuccess(Object obj) {
                    marker3.setIcon((Drawable) obj);
                    C1206b.hxYOFxFjLpN1maJuWNxUV40nExCGxsxkDPOTgtzMu4zlZCQb3bPlKsXo1SYJg6ME(this.hxYOFxFjLpN1maJuWNxUV40nExCGxsxkDPOTgtzMu4zlZCQb3bPlKsXo1SYJg6ME).invalidate();
                }

                public final void onFailure(String str) {
                    int e = Log.e(C1206b.TAG, "Unable to update feature image: ".concat(String.valueOf(str)));
                }
            };
            hxYOFxFjLpN1maJuWNxUV40nExCGxsxkDPOTgtzMu4zlZCQb3bPlKsXo1SYJg6ME(mapMarker2, (AsyncCallbackPair<Drawable>) asyncCallbackPair);
        }
    }

    public void updateFeatureSize(MapFactory.MapMarker mapMarker) {
        AsyncCallbackPair asyncCallbackPair;
        MapFactory.MapMarker mapMarker2 = mapMarker;
        Marker marker = (Marker) this.moH2L3ThNq0Qhevz3oD0wpnUoXefdMi3gJ5JHfEbjrtgsvRvpM1e34BZQYI4s5i0.get(mapMarker2);
        Marker marker2 = marker;
        if (marker != null) {
            final Marker marker3 = marker2;
            new AsyncCallbackPair<Drawable>(this) {
                private /* synthetic */ C1206b hxYOFxFjLpN1maJuWNxUV40nExCGxsxkDPOTgtzMu4zlZCQb3bPlKsXo1SYJg6ME;

                {
                    this.hxYOFxFjLpN1maJuWNxUV40nExCGxsxkDPOTgtzMu4zlZCQb3bPlKsXo1SYJg6ME = r6;
                }

                public final /* synthetic */ void onSuccess(Object obj) {
                    marker3.setIcon((Drawable) obj);
                    C1206b.hxYOFxFjLpN1maJuWNxUV40nExCGxsxkDPOTgtzMu4zlZCQb3bPlKsXo1SYJg6ME(this.hxYOFxFjLpN1maJuWNxUV40nExCGxsxkDPOTgtzMu4zlZCQb3bPlKsXo1SYJg6ME).invalidate();
                }

                public final void onFailure(String str) {
                    String str2 = str;
                    int wtf = Log.wtf(C1206b.TAG, "Cannot find default marker");
                }
            };
            hxYOFxFjLpN1maJuWNxUV40nExCGxsxkDPOTgtzMu4zlZCQb3bPlKsXo1SYJg6ME(mapMarker2, (AsyncCallbackPair<Drawable>) asyncCallbackPair);
        }
    }

    /* access modifiers changed from: private */
    public void hxYOFxFjLpN1maJuWNxUV40nExCGxsxkDPOTgtzMu4zlZCQb3bPlKsXo1SYJg6ME(MapFactory.MapMarker mapMarker, AsyncCallbackPair<Drawable> asyncCallbackPair) {
        MapFactory.MapMarker mapMarker2 = mapMarker;
        AsyncCallbackPair<Drawable> asyncCallbackPair2 = asyncCallbackPair;
        String ImageAsset = mapMarker2.ImageAsset();
        String str = ImageAsset;
        if (ImageAsset == null || str.length() == 0 || str.endsWith(".svg")) {
            B8WBXPBCF2jGfUDZZU2zV5EYdqbUBu0lAZ0THCEqYyuE8VACR9dY7rDnwBIqh64T(mapMarker2, asyncCallbackPair2);
        } else {
            wq07duYRO6iFAgWM70EZOSvbCMKs1QznMRJKrct0XuHOBYqCk3XqOKtSBGIpDou(mapMarker2, asyncCallbackPair2);
        }
    }

    private void B8WBXPBCF2jGfUDZZU2zV5EYdqbUBu0lAZ0THCEqYyuE8VACR9dY7rDnwBIqh64T(MapFactory.MapMarker mapMarker, AsyncCallbackPair<Drawable> asyncCallbackPair) {
        StringBuilder sb;
        Throwable th;
        MapFactory.MapMarker mapMarker2 = mapMarker;
        AsyncCallbackPair<Drawable> asyncCallbackPair2 = asyncCallbackPair;
        SVG svg = null;
        if (this.f794hxYOFxFjLpN1maJuWNxUV40nExCGxsxkDPOTgtzMu4zlZCQb3bPlKsXo1SYJg6ME == null) {
            try {
                this.f794hxYOFxFjLpN1maJuWNxUV40nExCGxsxkDPOTgtzMu4zlZCQb3bPlKsXo1SYJg6ME = SVG.getFromAsset(this.f799hxYOFxFjLpN1maJuWNxUV40nExCGxsxkDPOTgtzMu4zlZCQb3bPlKsXo1SYJg6ME.getContext().getAssets(), "marker.svg");
            } catch (SVGParseException e) {
                int e2 = Log.e(TAG, "Invalid SVG in Marker asset", e);
            } catch (IOException e3) {
                int e4 = Log.e(TAG, "Unable to read Marker asset", e3);
            }
            if (this.f794hxYOFxFjLpN1maJuWNxUV40nExCGxsxkDPOTgtzMu4zlZCQb3bPlKsXo1SYJg6ME == null || this.f794hxYOFxFjLpN1maJuWNxUV40nExCGxsxkDPOTgtzMu4zlZCQb3bPlKsXo1SYJg6ME.getRootElement() == null) {
                Throwable th2 = th;
                new IllegalStateException("Unable to load SVG from assets");
                throw th2;
            }
        }
        String ImageAsset = mapMarker2.ImageAsset();
        String str = ImageAsset;
        if (!(ImageAsset == null || str.length() == 0)) {
            try {
                svg = SVG.getFromAsset(this.f799hxYOFxFjLpN1maJuWNxUV40nExCGxsxkDPOTgtzMu4zlZCQb3bPlKsXo1SYJg6ME.getContext().getAssets(), str);
            } catch (SVGParseException e5) {
                int e6 = Log.e(TAG, "Invalid SVG in Marker asset", e5);
            } catch (IOException e7) {
                int e8 = Log.e(TAG, "Unable to read Marker asset", e7);
            }
            if (svg == null) {
                InputStream inputStream = null;
                try {
                    InputStream openMedia = MediaUtil.openMedia(this.form, str);
                    inputStream = openMedia;
                    svg = SVG.getFromInputStream(openMedia);
                    IOUtils.closeQuietly(TAG, inputStream);
                } catch (SVGParseException e9) {
                    int e10 = Log.e(TAG, "Invalid SVG in Marker asset", e9);
                    IOUtils.closeQuietly(TAG, inputStream);
                } catch (IOException e11) {
                    int e12 = Log.e(TAG, "Unable to read Marker asset", e11);
                    IOUtils.closeQuietly(TAG, inputStream);
                } catch (Throwable th3) {
                    Throwable th4 = th3;
                    IOUtils.closeQuietly(TAG, inputStream);
                    throw th4;
                }
            }
        }
        if (svg == null) {
            svg = this.f794hxYOFxFjLpN1maJuWNxUV40nExCGxsxkDPOTgtzMu4zlZCQb3bPlKsXo1SYJg6ME;
        }
        try {
            asyncCallbackPair2.onSuccess(hxYOFxFjLpN1maJuWNxUV40nExCGxsxkDPOTgtzMu4zlZCQb3bPlKsXo1SYJg6ME(mapMarker2, svg));
        } catch (Exception e13) {
            new StringBuilder();
            asyncCallbackPair2.onFailure(sb.append(e13.getMessage()).toString());
        }
    }

    private void wq07duYRO6iFAgWM70EZOSvbCMKs1QznMRJKrct0XuHOBYqCk3XqOKtSBGIpDou(MapFactory.MapMarker mapMarker, AsyncCallbackPair<Drawable> asyncCallbackPair) {
        AsyncCallbackPair asyncCallbackPair2;
        MapFactory.MapMarker mapMarker2 = mapMarker;
        final AsyncCallbackPair<Drawable> asyncCallbackPair3 = asyncCallbackPair;
        final MapFactory.MapMarker mapMarker3 = mapMarker2;
        new AsyncCallbackPair<BitmapDrawable>(this) {

            /* renamed from: hxYOFxFjLpN1maJuWNxUV40nExCGxsxkDPOTgtzMu4zlZCQb3bPlKsXo1SYJg6ME  reason: collision with other field name */
            private /* synthetic */ C1206b f812hxYOFxFjLpN1maJuWNxUV40nExCGxsxkDPOTgtzMu4zlZCQb3bPlKsXo1SYJg6ME;

            {
                this.f812hxYOFxFjLpN1maJuWNxUV40nExCGxsxkDPOTgtzMu4zlZCQb3bPlKsXo1SYJg6ME = r7;
            }

            public final /* synthetic */ void onSuccess(Object obj) {
                BitmapDrawable bitmapDrawable = (BitmapDrawable) obj;
                bitmapDrawable.setAlpha(Math.round(mapMarker3.FillOpacity() * 255.0f));
                asyncCallbackPair3.onSuccess(bitmapDrawable);
            }

            public final void onFailure(String str) {
                String str2 = str;
                asyncCallbackPair3.onSuccess(C1206b.hxYOFxFjLpN1maJuWNxUV40nExCGxsxkDPOTgtzMu4zlZCQb3bPlKsXo1SYJg6ME(this.f812hxYOFxFjLpN1maJuWNxUV40nExCGxsxkDPOTgtzMu4zlZCQb3bPlKsXo1SYJg6ME, mapMarker3));
            }
        };
        MediaUtil.getBitmapDrawableAsync(this.form, mapMarker2.ImageAsset(), asyncCallbackPair2);
    }

    private static float hxYOFxFjLpN1maJuWNxUV40nExCGxsxkDPOTgtzMu4zlZCQb3bPlKsXo1SYJg6ME(SVG.Svg svg) {
        SVG.Svg svg2 = svg;
        if (svg2.width != null) {
            return svg2.width.floatValue();
        }
        if (svg2.viewBox != null) {
            return svg2.viewBox.width;
        }
        return 30.0f;
    }

    private static float B8WBXPBCF2jGfUDZZU2zV5EYdqbUBu0lAZ0THCEqYyuE8VACR9dY7rDnwBIqh64T(SVG.Svg svg) {
        SVG.Svg svg2 = svg;
        if (svg2.height != null) {
            return svg2.height.floatValue();
        }
        if (svg2.viewBox != null) {
            return svg2.viewBox.height;
        }
        return 50.0f;
    }

    private Drawable hxYOFxFjLpN1maJuWNxUV40nExCGxsxkDPOTgtzMu4zlZCQb3bPlKsXo1SYJg6ME(MapFactory.MapMarker mapMarker, SVG svg) {
        float Width;
        Paint paint;
        Paint paint2;
        SVG.Length length;
        Picture picture;
        Drawable drawable;
        SVG.SvgPaint svgPaint;
        SVG.SvgPaint svgPaint2;
        SVG.SvgPaint svgPaint3;
        SVG.SvgPaint svgPaint4;
        MapFactory.MapMarker mapMarker2 = mapMarker;
        SVG svg2 = svg;
        SVG.Svg rootElement = svg2.getRootElement();
        float f = this.f799hxYOFxFjLpN1maJuWNxUV40nExCGxsxkDPOTgtzMu4zlZCQb3bPlKsXo1SYJg6ME.getContext().getResources().getDisplayMetrics().density;
        float B8WBXPBCF2jGfUDZZU2zV5EYdqbUBu0lAZ0THCEqYyuE8VACR9dY7rDnwBIqh64T = mapMarker2.Height() <= 0 ? B8WBXPBCF2jGfUDZZU2zV5EYdqbUBu0lAZ0THCEqYyuE8VACR9dY7rDnwBIqh64T(rootElement) : (float) mapMarker2.Height();
        if (mapMarker2.Width() <= 0) {
            Width = hxYOFxFjLpN1maJuWNxUV40nExCGxsxkDPOTgtzMu4zlZCQb3bPlKsXo1SYJg6ME(rootElement);
        } else {
            Width = (float) mapMarker2.Width();
        }
        float f2 = Width;
        float B8WBXPBCF2jGfUDZZU2zV5EYdqbUBu0lAZ0THCEqYyuE8VACR9dY7rDnwBIqh64T2 = B8WBXPBCF2jGfUDZZU2zV5EYdqbUBu0lAZ0THCEqYyuE8VACR9dY7rDnwBIqh64T / B8WBXPBCF2jGfUDZZU2zV5EYdqbUBu0lAZ0THCEqYyuE8VACR9dY7rDnwBIqh64T(rootElement);
        float hxYOFxFjLpN1maJuWNxUV40nExCGxsxkDPOTgtzMu4zlZCQb3bPlKsXo1SYJg6ME2 = f2 / hxYOFxFjLpN1maJuWNxUV40nExCGxsxkDPOTgtzMu4zlZCQb3bPlKsXo1SYJg6ME(rootElement);
        float f3 = B8WBXPBCF2jGfUDZZU2zV5EYdqbUBu0lAZ0THCEqYyuE8VACR9dY7rDnwBIqh64T2;
        float f4 = f3 * f3;
        float f5 = hxYOFxFjLpN1maJuWNxUV40nExCGxsxkDPOTgtzMu4zlZCQb3bPlKsXo1SYJg6ME2;
        float sqrt = (float) Math.sqrt((double) (f4 + (f5 * f5)));
        new Paint();
        Paint paint3 = paint;
        new Paint();
        Paint paint4 = paint2;
        PaintUtil.changePaint(paint3, mapMarker2.FillColor());
        PaintUtil.changePaint(paint4, mapMarker2.StrokeColor());
        new SVG.Length(((float) mapMarker2.StrokeWidth()) / sqrt);
        SVG.Length length2 = length;
        for (SVG.SvgObject next : rootElement.getChildren()) {
            SVG.SvgObject svgObject = next;
            if (next instanceof SVG.SvgConditionalElement) {
                SVG.SvgConditionalElement svgConditionalElement = (SVG.SvgConditionalElement) svgObject;
                SVG.SvgConditionalElement svgConditionalElement2 = svgConditionalElement;
                SVG.Style style = svgConditionalElement.baseStyle;
                new SVG.Colour(paint3.getColor());
                style.fill = svgPaint;
                svgConditionalElement2.baseStyle.fillOpacity = Float.valueOf(((float) paint3.getAlpha()) / 255.0f);
                SVG.Style style2 = svgConditionalElement2.baseStyle;
                new SVG.Colour(paint4.getColor());
                style2.stroke = svgPaint2;
                svgConditionalElement2.baseStyle.strokeOpacity = Float.valueOf(((float) paint4.getAlpha()) / 255.0f);
                svgConditionalElement2.baseStyle.strokeWidth = length2;
                svgConditionalElement2.baseStyle.specifiedFlags = 61;
                if (svgConditionalElement2.style != null) {
                    if ((svgConditionalElement2.style.specifiedFlags & 1) == 0) {
                        SVG.Style style3 = svgConditionalElement2.style;
                        new SVG.Colour(paint3.getColor());
                        style3.fill = svgPaint4;
                        svgConditionalElement2.style.specifiedFlags |= 1;
                    }
                    if ((svgConditionalElement2.style.specifiedFlags & 4) == 0) {
                        svgConditionalElement2.style.fillOpacity = Float.valueOf(((float) paint3.getAlpha()) / 255.0f);
                        svgConditionalElement2.style.specifiedFlags |= 4;
                    }
                    if ((svgConditionalElement2.style.specifiedFlags & 8) == 0) {
                        SVG.Style style4 = svgConditionalElement2.style;
                        new SVG.Colour(paint4.getColor());
                        style4.stroke = svgPaint3;
                        svgConditionalElement2.style.specifiedFlags |= 8;
                    }
                    if ((svgConditionalElement2.style.specifiedFlags & 16) == 0) {
                        svgConditionalElement2.style.strokeOpacity = Float.valueOf(((float) paint4.getAlpha()) / 255.0f);
                        svgConditionalElement2.style.specifiedFlags |= 16;
                    }
                    if ((svgConditionalElement2.style.specifiedFlags & 32) == 0) {
                        svgConditionalElement2.style.strokeWidth = length2;
                        svgConditionalElement2.style.specifiedFlags |= 32;
                    }
                }
            }
        }
        Picture renderToPicture = svg2.renderToPicture();
        new Picture();
        Picture picture2 = picture;
        Picture picture3 = picture2;
        Canvas beginRecording = picture2.beginRecording((int) ((f2 + (2.0f * ((float) mapMarker2.StrokeWidth()))) * f), (int) ((B8WBXPBCF2jGfUDZZU2zV5EYdqbUBu0lAZ0THCEqYyuE8VACR9dY7rDnwBIqh64T + (2.0f * ((float) mapMarker2.StrokeWidth()))) * f));
        Canvas canvas = beginRecording;
        beginRecording.scale(f * hxYOFxFjLpN1maJuWNxUV40nExCGxsxkDPOTgtzMu4zlZCQb3bPlKsXo1SYJg6ME2, f * B8WBXPBCF2jGfUDZZU2zV5EYdqbUBu0lAZ0THCEqYyuE8VACR9dY7rDnwBIqh64T2);
        canvas.translate(length2.floatValue(), length2.floatValue());
        renderToPicture.draw(canvas);
        picture3.endRecording();
        new PictureDrawable(picture3);
        return drawable;
    }

    private void hxYOFxFjLpN1maJuWNxUV40nExCGxsxkDPOTgtzMu4zlZCQb3bPlKsXo1SYJg6ME(Polygon polygon, MapFactory.MapFeature mapFeature) {
        Polygon polygon2 = polygon;
        MapFactory.MapFeature mapFeature2 = mapFeature;
        polygon2.setDraggable(mapFeature2.Draggable());
        polygon2.setTitle(mapFeature2.Title());
        polygon2.setSnippet(mapFeature2.Description());
        polygon2.setStrokeColor(((MapFactory.HasStroke) mapFeature2).StrokeColor());
        polygon2.setStrokeWidth((float) ((MapFactory.HasStroke) mapFeature2).StrokeWidth());
        polygon2.setFillColor(((MapFactory.HasFill) mapFeature2).FillColor());
        polygon2.setInfoWindow(this.f803hxYOFxFjLpN1maJuWNxUV40nExCGxsxkDPOTgtzMu4zlZCQb3bPlKsXo1SYJg6ME);
    }

    public void showFeature(MapFactory.MapFeature mapFeature) {
        MapFactory.MapFeature mapFeature2 = mapFeature;
        if (!this.Co1rSUoyBoOJ9n0NOgrpxvTYmRPklnETeodUBiQlSBK151C8PCv1Q1Pl4SUa1qxB.contains(mapFeature2)) {
            hxYOFxFjLpN1maJuWNxUV40nExCGxsxkDPOTgtzMu4zlZCQb3bPlKsXo1SYJg6ME(this.moH2L3ThNq0Qhevz3oD0wpnUoXefdMi3gJ5JHfEbjrtgsvRvpM1e34BZQYI4s5i0.get(mapFeature2));
        }
    }

    /* access modifiers changed from: protected */
    public final void hxYOFxFjLpN1maJuWNxUV40nExCGxsxkDPOTgtzMu4zlZCQb3bPlKsXo1SYJg6ME(OverlayWithIW overlayWithIW) {
        boolean add = this.f799hxYOFxFjLpN1maJuWNxUV40nExCGxsxkDPOTgtzMu4zlZCQb3bPlKsXo1SYJg6ME.getOverlayManager().add(overlayWithIW);
        this.f799hxYOFxFjLpN1maJuWNxUV40nExCGxsxkDPOTgtzMu4zlZCQb3bPlKsXo1SYJg6ME.invalidate();
    }

    public void hideFeature(MapFactory.MapFeature mapFeature) {
        B8WBXPBCF2jGfUDZZU2zV5EYdqbUBu0lAZ0THCEqYyuE8VACR9dY7rDnwBIqh64T(this.moH2L3ThNq0Qhevz3oD0wpnUoXefdMi3gJ5JHfEbjrtgsvRvpM1e34BZQYI4s5i0.get(mapFeature));
    }

    /* access modifiers changed from: protected */
    public final void B8WBXPBCF2jGfUDZZU2zV5EYdqbUBu0lAZ0THCEqYyuE8VACR9dY7rDnwBIqh64T(OverlayWithIW overlayWithIW) {
        boolean remove = this.f799hxYOFxFjLpN1maJuWNxUV40nExCGxsxkDPOTgtzMu4zlZCQb3bPlKsXo1SYJg6ME.getOverlayManager().remove(overlayWithIW);
        this.f799hxYOFxFjLpN1maJuWNxUV40nExCGxsxkDPOTgtzMu4zlZCQb3bPlKsXo1SYJg6ME.invalidate();
    }

    public boolean isFeatureVisible(MapFactory.MapFeature mapFeature) {
        OverlayWithIW overlayWithIW = this.moH2L3ThNq0Qhevz3oD0wpnUoXefdMi3gJ5JHfEbjrtgsvRvpM1e34BZQYI4s5i0.get(mapFeature);
        return overlayWithIW != null && this.f799hxYOFxFjLpN1maJuWNxUV40nExCGxsxkDPOTgtzMu4zlZCQb3bPlKsXo1SYJg6ME.getOverlayManager().contains(overlayWithIW);
    }

    public boolean isFeatureCollectionVisible(MapFactory.MapFeatureCollection mapFeatureCollection) {
        return !this.qPeHJnCLP0dAOwDPeFIn82vcSIsCMh6KFFn3o4kyIe0RhQKOQXDeyY2LFwPu2GbE.contains(mapFeatureCollection);
    }

    public void setFeatureCollectionVisible(MapFactory.MapFeatureCollection mapFeatureCollection, boolean z) {
        MapFactory.MapFeatureCollection mapFeatureCollection2 = mapFeatureCollection;
        boolean z2 = z;
        if (!z2 && this.qPeHJnCLP0dAOwDPeFIn82vcSIsCMh6KFFn3o4kyIe0RhQKOQXDeyY2LFwPu2GbE.contains(mapFeatureCollection2)) {
            return;
        }
        if (z2 && !this.qPeHJnCLP0dAOwDPeFIn82vcSIsCMh6KFFn3o4kyIe0RhQKOQXDeyY2LFwPu2GbE.contains(mapFeatureCollection2)) {
            return;
        }
        if (z2) {
            boolean remove = this.qPeHJnCLP0dAOwDPeFIn82vcSIsCMh6KFFn3o4kyIe0RhQKOQXDeyY2LFwPu2GbE.remove(mapFeatureCollection2);
            for (MapFactory.MapFeature next : mapFeatureCollection2) {
                boolean remove2 = this.Co1rSUoyBoOJ9n0NOgrpxvTYmRPklnETeodUBiQlSBK151C8PCv1Q1Pl4SUa1qxB.remove(next);
                if (next.Visible()) {
                    showFeature(next);
                }
            }
            return;
        }
        boolean add = this.qPeHJnCLP0dAOwDPeFIn82vcSIsCMh6KFFn3o4kyIe0RhQKOQXDeyY2LFwPu2GbE.add(mapFeatureCollection2);
        for (MapFactory.MapFeature next2 : mapFeatureCollection2) {
            boolean add2 = this.Co1rSUoyBoOJ9n0NOgrpxvTYmRPklnETeodUBiQlSBK151C8PCv1Q1Pl4SUa1qxB.add(next2);
            hideFeature(next2);
        }
    }

    public void showInfobox(MapFactory.MapFeature mapFeature) {
        this.moH2L3ThNq0Qhevz3oD0wpnUoXefdMi3gJ5JHfEbjrtgsvRvpM1e34BZQYI4s5i0.get(mapFeature).showInfoWindow();
    }

    public void hideInfobox(MapFactory.MapFeature mapFeature) {
        this.moH2L3ThNq0Qhevz3oD0wpnUoXefdMi3gJ5JHfEbjrtgsvRvpM1e34BZQYI4s5i0.get(mapFeature).closeInfoWindow();
    }

    public boolean isInfoboxVisible(MapFactory.MapFeature mapFeature) {
        OverlayWithIW overlayWithIW = this.moH2L3ThNq0Qhevz3oD0wpnUoXefdMi3gJ5JHfEbjrtgsvRvpM1e34BZQYI4s5i0.get(mapFeature);
        return overlayWithIW != null && overlayWithIW.isInfoWindowOpen();
    }

    public BoundingBox getBoundingBox() {
        return this.f799hxYOFxFjLpN1maJuWNxUV40nExCGxsxkDPOTgtzMu4zlZCQb3bPlKsXo1SYJg6ME.getBoundingBox();
    }

    public void setBoundingBox(BoundingBox boundingBox) {
        BoundingBox boundingBox2 = boundingBox;
        this.f799hxYOFxFjLpN1maJuWNxUV40nExCGxsxkDPOTgtzMu4zlZCQb3bPlKsXo1SYJg6ME.getController().setCenter(boundingBox2.getCenter());
        this.f799hxYOFxFjLpN1maJuWNxUV40nExCGxsxkDPOTgtzMu4zlZCQb3bPlKsXo1SYJg6ME.getController().zoomToSpan(boundingBox2.getLatitudeSpan(), boundingBox2.getLongitudeSpan());
    }

    public boolean onScroll(ScrollEvent scrollEvent) {
        ScrollEvent scrollEvent2 = scrollEvent;
        for (MapFactory.MapEventListener onBoundsChanged : this.vwEpIRqEf6xdtwTR9dehwBO7JUhyLV6iEzEK2WqfPN10eUMQDPn3AUmqAFfsnr6R) {
            onBoundsChanged.onBoundsChanged();
        }
        return true;
    }

    public boolean onZoom(ZoomEvent zoomEvent) {
        ZoomEvent zoomEvent2 = zoomEvent;
        this.f798hxYOFxFjLpN1maJuWNxUV40nExCGxsxkDPOTgtzMu4zlZCQb3bPlKsXo1SYJg6ME.updateButtons();
        for (MapFactory.MapEventListener onZoom : this.vwEpIRqEf6xdtwTR9dehwBO7JUhyLV6iEzEK2WqfPN10eUMQDPn3AUmqAFfsnr6R) {
            onZoom.onZoom();
        }
        return true;
    }

    public LocationSensor.LocationSensorListener getLocationListener() {
        return this.f796hxYOFxFjLpN1maJuWNxUV40nExCGxsxkDPOTgtzMu4zlZCQb3bPlKsXo1SYJg6ME;
    }

    public int getOverlayCount() {
        System.err.println(this.f799hxYOFxFjLpN1maJuWNxUV40nExCGxsxkDPOTgtzMu4zlZCQb3bPlKsXo1SYJg6ME.getOverlays());
        return this.f799hxYOFxFjLpN1maJuWNxUV40nExCGxsxkDPOTgtzMu4zlZCQb3bPlKsXo1SYJg6ME.getOverlays().size();
    }

    public void setRotation(float f) {
        this.f799hxYOFxFjLpN1maJuWNxUV40nExCGxsxkDPOTgtzMu4zlZCQb3bPlKsXo1SYJg6ME.setMapOrientation(f);
    }

    public float getRotation() {
        return this.f799hxYOFxFjLpN1maJuWNxUV40nExCGxsxkDPOTgtzMu4zlZCQb3bPlKsXo1SYJg6ME.getMapOrientation();
    }

    public void setScaleVisible(boolean z) {
        this.f800hxYOFxFjLpN1maJuWNxUV40nExCGxsxkDPOTgtzMu4zlZCQb3bPlKsXo1SYJg6ME.setEnabled(z);
        this.f799hxYOFxFjLpN1maJuWNxUV40nExCGxsxkDPOTgtzMu4zlZCQb3bPlKsXo1SYJg6ME.invalidate();
    }

    public boolean isScaleVisible() {
        return this.f800hxYOFxFjLpN1maJuWNxUV40nExCGxsxkDPOTgtzMu4zlZCQb3bPlKsXo1SYJg6ME.isEnabled();
    }

    public void setScaleUnits(MapFactory.MapScaleUnits mapScaleUnits) {
        Throwable th;
        MapFactory.MapScaleUnits mapScaleUnits2 = mapScaleUnits;
        switch (mapScaleUnits2) {
            case METRIC:
                this.f800hxYOFxFjLpN1maJuWNxUV40nExCGxsxkDPOTgtzMu4zlZCQb3bPlKsXo1SYJg6ME.setUnitsOfMeasure(ScaleBarOverlay.UnitsOfMeasure.metric);
                break;
            case IMPERIAL:
                this.f800hxYOFxFjLpN1maJuWNxUV40nExCGxsxkDPOTgtzMu4zlZCQb3bPlKsXo1SYJg6ME.setUnitsOfMeasure(ScaleBarOverlay.UnitsOfMeasure.imperial);
                break;
            default:
                Throwable th2 = th;
                new IllegalArgumentException("Unallowable unit system: ".concat(String.valueOf(mapScaleUnits2)));
                throw th2;
        }
        this.f799hxYOFxFjLpN1maJuWNxUV40nExCGxsxkDPOTgtzMu4zlZCQb3bPlKsXo1SYJg6ME.invalidate();
    }

    public MapFactory.MapScaleUnits getScaleUnits() {
        Throwable th;
        switch (this.f800hxYOFxFjLpN1maJuWNxUV40nExCGxsxkDPOTgtzMu4zlZCQb3bPlKsXo1SYJg6ME.getUnitsOfMeasure()) {
            case imperial:
                return MapFactory.MapScaleUnits.IMPERIAL;
            case metric:
                return MapFactory.MapScaleUnits.METRIC;
            default:
                Throwable th2 = th;
                new IllegalStateException("Somehow we have an unallowed unit system");
                throw th2;
        }
    }

    /* renamed from: com.google.appinventor.components.runtime.util.b$d */
    static class C1229d extends Polygon {
        List<Polygon> DmQGLROFyZ9Eo0RSsJcpZNxJZjgcsPDfYPi3awNwmyyErT71sGU5mvgG4PDW3yL;
        private boolean NqI9fRlBp15rsxXeNmRjOrUuQwDxdaDY0xSQj1B7T50sMWnnasnmhTtbykVZmafW;
        private Polygon.OnClickListener hxYOFxFjLpN1maJuWNxUV40nExCGxsxkDPOTgtzMu4zlZCQb3bPlKsXo1SYJg6ME;

        /* renamed from: hxYOFxFjLpN1maJuWNxUV40nExCGxsxkDPOTgtzMu4zlZCQb3bPlKsXo1SYJg6ME  reason: collision with other field name */
        private Polygon.OnDragListener f816hxYOFxFjLpN1maJuWNxUV40nExCGxsxkDPOTgtzMu4zlZCQb3bPlKsXo1SYJg6ME;

        C1229d() {
            List<Polygon> list;
            new ArrayList();
            this.DmQGLROFyZ9Eo0RSsJcpZNxJZjgcsPDfYPi3awNwmyyErT71sGU5mvgG4PDW3yL = list;
        }

        public final void showInfoWindow() {
            if (this.DmQGLROFyZ9Eo0RSsJcpZNxJZjgcsPDfYPi3awNwmyyErT71sGU5mvgG4PDW3yL.size() > 0) {
                this.DmQGLROFyZ9Eo0RSsJcpZNxJZjgcsPDfYPi3awNwmyyErT71sGU5mvgG4PDW3yL.get(0).showInfoWindow();
            }
        }

        public final void draw(Canvas canvas, MapView mapView, boolean z) {
            Canvas canvas2 = canvas;
            MapView mapView2 = mapView;
            boolean z2 = z;
            for (Polygon draw : this.DmQGLROFyZ9Eo0RSsJcpZNxJZjgcsPDfYPi3awNwmyyErT71sGU5mvgG4PDW3yL) {
                draw.draw(canvas2, mapView2, z2);
            }
        }

        public final void B8WBXPBCF2jGfUDZZU2zV5EYdqbUBu0lAZ0THCEqYyuE8VACR9dY7rDnwBIqh64T(List<List<GeoPoint>> list) {
            Polygon polygon;
            Iterator<Polygon> it = this.DmQGLROFyZ9Eo0RSsJcpZNxJZjgcsPDfYPi3awNwmyyErT71sGU5mvgG4PDW3yL.iterator();
            Iterator<List<GeoPoint>> it2 = list.iterator();
            while (it.hasNext() && it2.hasNext()) {
                it.next().setPoints(it2.next());
            }
            while (it.hasNext()) {
                Polygon next = it.next();
                it.remove();
            }
            while (it2.hasNext()) {
                new Polygon();
                Polygon polygon2 = polygon;
                Polygon polygon3 = polygon2;
                polygon2.setPoints(it2.next());
                polygon3.setStrokeColor(getStrokeColor());
                polygon3.setFillColor(getFillColor());
                polygon3.setStrokeWidth(getStrokeWidth());
                polygon3.setInfoWindow(getInfoWindow());
                polygon3.setDraggable(this.NqI9fRlBp15rsxXeNmRjOrUuQwDxdaDY0xSQj1B7T50sMWnnasnmhTtbykVZmafW);
                polygon3.setOnClickListener(this.hxYOFxFjLpN1maJuWNxUV40nExCGxsxkDPOTgtzMu4zlZCQb3bPlKsXo1SYJg6ME);
                polygon3.setOnDragListener(this.f816hxYOFxFjLpN1maJuWNxUV40nExCGxsxkDPOTgtzMu4zlZCQb3bPlKsXo1SYJg6ME);
                boolean add = this.DmQGLROFyZ9Eo0RSsJcpZNxJZjgcsPDfYPi3awNwmyyErT71sGU5mvgG4PDW3yL.add(polygon3);
            }
        }

        public final List<List<List<GeoPoint>>> hxYOFxFjLpN1maJuWNxUV40nExCGxsxkDPOTgtzMu4zlZCQb3bPlKsXo1SYJg6ME() {
            List<List<List<GeoPoint>>> list;
            new ArrayList();
            List<List<List<GeoPoint>>> list2 = list;
            for (Polygon holes : this.DmQGLROFyZ9Eo0RSsJcpZNxJZjgcsPDfYPi3awNwmyyErT71sGU5mvgG4PDW3yL) {
                boolean add = list2.add(holes.getHoles());
            }
            return list2;
        }

        public final void wq07duYRO6iFAgWM70EZOSvbCMKs1QznMRJKrct0XuHOBYqCk3XqOKtSBGIpDou(List<List<List<GeoPoint>>> list) {
            Throwable th;
            List<List<List<GeoPoint>>> list2 = list;
            if (list2 == null || list2.isEmpty()) {
                for (Polygon holes : this.DmQGLROFyZ9Eo0RSsJcpZNxJZjgcsPDfYPi3awNwmyyErT71sGU5mvgG4PDW3yL) {
                    holes.setHoles(Collections.emptyList());
                }
            } else if (list2.size() != this.DmQGLROFyZ9Eo0RSsJcpZNxJZjgcsPDfYPi3awNwmyyErT71sGU5mvgG4PDW3yL.size()) {
                Throwable th2 = th;
                new IllegalArgumentException("Holes and points are not of the same arity.");
                throw th2;
            } else {
                Iterator<Polygon> it = this.DmQGLROFyZ9Eo0RSsJcpZNxJZjgcsPDfYPi3awNwmyyErT71sGU5mvgG4PDW3yL.iterator();
                Iterator<List<List<GeoPoint>>> it2 = list2.iterator();
                while (it.hasNext() && it2.hasNext()) {
                    it.next().setHoles(it2.next());
                }
            }
        }

        public final void setDraggable(boolean z) {
            boolean z2 = z;
            super.setDraggable(z2);
            this.NqI9fRlBp15rsxXeNmRjOrUuQwDxdaDY0xSQj1B7T50sMWnnasnmhTtbykVZmafW = z2;
            for (Polygon draggable : this.DmQGLROFyZ9Eo0RSsJcpZNxJZjgcsPDfYPi3awNwmyyErT71sGU5mvgG4PDW3yL) {
                draggable.setDraggable(z2);
            }
        }

        public final void setOnClickListener(Polygon.OnClickListener onClickListener) {
            Polygon.OnClickListener onClickListener2 = onClickListener;
            super.setOnClickListener(onClickListener2);
            this.hxYOFxFjLpN1maJuWNxUV40nExCGxsxkDPOTgtzMu4zlZCQb3bPlKsXo1SYJg6ME = onClickListener2;
            for (Polygon onClickListener3 : this.DmQGLROFyZ9Eo0RSsJcpZNxJZjgcsPDfYPi3awNwmyyErT71sGU5mvgG4PDW3yL) {
                onClickListener3.setOnClickListener(onClickListener2);
            }
        }

        public final void setOnDragListener(Polygon.OnDragListener onDragListener) {
            Polygon.OnDragListener onDragListener2 = onDragListener;
            super.setOnDragListener(onDragListener2);
            this.f816hxYOFxFjLpN1maJuWNxUV40nExCGxsxkDPOTgtzMu4zlZCQb3bPlKsXo1SYJg6ME = onDragListener2;
            for (Polygon onDragListener3 : this.DmQGLROFyZ9Eo0RSsJcpZNxJZjgcsPDfYPi3awNwmyyErT71sGU5mvgG4PDW3yL) {
                onDragListener3.setOnDragListener(onDragListener2);
            }
        }

        public final void setStrokeWidth(float f) {
            float f2 = f;
            super.setStrokeWidth(f2);
            for (Polygon strokeWidth : this.DmQGLROFyZ9Eo0RSsJcpZNxJZjgcsPDfYPi3awNwmyyErT71sGU5mvgG4PDW3yL) {
                strokeWidth.setStrokeWidth(f2);
            }
        }

        public final void setStrokeColor(int i) {
            int i2 = i;
            super.setStrokeColor(i2);
            for (Polygon strokeColor : this.DmQGLROFyZ9Eo0RSsJcpZNxJZjgcsPDfYPi3awNwmyyErT71sGU5mvgG4PDW3yL) {
                strokeColor.setStrokeColor(i2);
            }
        }

        public final void setFillColor(int i) {
            int i2 = i;
            super.setFillColor(i2);
            for (Polygon fillColor : this.DmQGLROFyZ9Eo0RSsJcpZNxJZjgcsPDfYPi3awNwmyyErT71sGU5mvgG4PDW3yL) {
                fillColor.setFillColor(i2);
            }
        }

        public final void setTitle(String str) {
            String str2 = str;
            super.setTitle(str2);
            for (Polygon title : this.DmQGLROFyZ9Eo0RSsJcpZNxJZjgcsPDfYPi3awNwmyyErT71sGU5mvgG4PDW3yL) {
                title.setTitle(str2);
            }
        }

        public final void setSnippet(String str) {
            String str2 = str;
            super.setSnippet(str2);
            for (Polygon snippet : this.DmQGLROFyZ9Eo0RSsJcpZNxJZjgcsPDfYPi3awNwmyyErT71sGU5mvgG4PDW3yL) {
                snippet.setSnippet(str2);
            }
        }

        public final boolean onSingleTapConfirmed(MotionEvent motionEvent, MapView mapView) {
            MotionEvent motionEvent2 = motionEvent;
            MapView mapView2 = mapView;
            for (Polygon onSingleTapConfirmed : this.DmQGLROFyZ9Eo0RSsJcpZNxJZjgcsPDfYPi3awNwmyyErT71sGU5mvgG4PDW3yL) {
                if (onSingleTapConfirmed.onSingleTapConfirmed(motionEvent2, mapView2)) {
                    return true;
                }
            }
            return false;
        }

        public final boolean contains(MotionEvent motionEvent) {
            MotionEvent motionEvent2 = motionEvent;
            for (Polygon contains : this.DmQGLROFyZ9Eo0RSsJcpZNxJZjgcsPDfYPi3awNwmyyErT71sGU5mvgG4PDW3yL) {
                if (contains.contains(motionEvent2)) {
                    return true;
                }
            }
            return false;
        }

        public final boolean onLongPress(MotionEvent motionEvent, MapView mapView) {
            MotionEvent motionEvent2 = motionEvent;
            MapView mapView2 = mapView;
            boolean contains = contains(motionEvent2);
            boolean z = contains;
            if (contains) {
                if (this.mDraggable) {
                    this.mIsDragged = true;
                    closeInfoWindow();
                    this.mDragStartPoint = motionEvent2;
                    if (this.mOnDragListener != null) {
                        this.mOnDragListener.onDragStart(this);
                    }
                    moveToEventPosition(motionEvent2, this.mDragStartPoint, mapView2);
                } else if (this.mOnClickListener != null) {
                    MapView mapView3 = mapView2;
                    boolean onLongClick = this.mOnClickListener.onLongClick(this, mapView3, (GeoPoint) mapView3.getProjection().fromPixels((int) motionEvent2.getX(), (int) motionEvent2.getY()));
                }
            }
            return z;
        }

        public final void moveToEventPosition(MotionEvent motionEvent, MotionEvent motionEvent2, MapView mapView) {
            MotionEvent motionEvent3 = motionEvent;
            MotionEvent motionEvent4 = motionEvent2;
            MapView mapView2 = mapView;
            for (Polygon moveToEventPosition : this.DmQGLROFyZ9Eo0RSsJcpZNxJZjgcsPDfYPi3awNwmyyErT71sGU5mvgG4PDW3yL) {
                moveToEventPosition.moveToEventPosition(motionEvent3, motionEvent4, mapView2);
            }
        }

        public final void finishMove(MotionEvent motionEvent, MotionEvent motionEvent2, MapView mapView) {
            MotionEvent motionEvent3 = motionEvent;
            MotionEvent motionEvent4 = motionEvent2;
            MapView mapView2 = mapView;
            for (Polygon finishMove : this.DmQGLROFyZ9Eo0RSsJcpZNxJZjgcsPDfYPi3awNwmyyErT71sGU5mvgG4PDW3yL) {
                finishMove.finishMove(motionEvent3, motionEvent4, mapView2);
            }
        }

        public final boolean onTouchEvent(MotionEvent motionEvent, MapView mapView) {
            MotionEvent motionEvent2 = motionEvent;
            MapView mapView2 = mapView;
            if (this.mDraggable && this.mIsDragged) {
                if (motionEvent2.getAction() == 1) {
                    this.mIsDragged = false;
                    finishMove(this.mDragStartPoint, motionEvent2, mapView2);
                    if (this.mOnDragListener != null) {
                        this.mOnDragListener.onDragEnd(this);
                    }
                    return true;
                } else if (motionEvent2.getAction() == 2) {
                    moveToEventPosition(motionEvent2, this.mDragStartPoint, mapView2);
                    if (this.mOnDragListener != null) {
                        this.mOnDragListener.onDrag(this);
                    }
                    return true;
                }
            }
            return false;
        }
    }
}
