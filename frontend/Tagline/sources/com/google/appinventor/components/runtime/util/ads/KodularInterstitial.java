package com.google.appinventor.components.runtime.util.ads;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import com.google.appinventor.components.runtime.util.KodularAdsUtil;
import com.unity3d.ads.UnityAds;
import com.unity3d.ads.mediation.IUnityAdsExtendedListener;

public class KodularInterstitial {
    private String G6NGZkO3GzJPBmNJ6LwDoEfdjbnPkejEO5C5VyyDaUNT1yJXqTcFE5lxbNsfgCQk = "unity";
    /* access modifiers changed from: private */
    public boolean TDuNe2Upxoi7WX7QZc6R8eUnFPyyDFw3hP7z3w6U8jMxQwJRQM03zSa9HzWXRv1 = false;
    private Context context;
    private OnAdsSwitcherListener hxYOFxFjLpN1maJuWNxUV40nExCGxsxkDPOTgtzMu4zlZCQb3bPlKsXo1SYJg6ME;

    /* renamed from: hxYOFxFjLpN1maJuWNxUV40nExCGxsxkDPOTgtzMu4zlZCQb3bPlKsXo1SYJg6ME  reason: collision with other field name */
    private C1204a f792hxYOFxFjLpN1maJuWNxUV40nExCGxsxkDPOTgtzMu4zlZCQb3bPlKsXo1SYJg6ME;

    public interface OnAdsSwitcherListener {
        void onAdsClick();

        void onAdsClosed();

        void onAdsError(String str);

        void onAdsReady();
    }

    public KodularInterstitial(Context context2) {
        C1204a aVar;
        int i = Log.i("Kodular Commission Interstitial", "INITIALIZED");
        this.context = context2;
        int hashCode = this.G6NGZkO3GzJPBmNJ6LwDoEfdjbnPkejEO5C5VyyDaUNT1yJXqTcFE5lxbNsfgCQk.hashCode();
        int i2 = Log.i("Kodular Commission Interstitial", "Using Unity Ads");
        new C1204a(this, (byte) 0);
        this.f792hxYOFxFjLpN1maJuWNxUV40nExCGxsxkDPOTgtzMu4zlZCQb3bPlKsXo1SYJg6ME = aVar;
    }

    public void setOnAdsSwitcherListener(OnAdsSwitcherListener onAdsSwitcherListener) {
        OnAdsSwitcherListener onAdsSwitcherListener2 = onAdsSwitcherListener;
        this.hxYOFxFjLpN1maJuWNxUV40nExCGxsxkDPOTgtzMu4zlZCQb3bPlKsXo1SYJg6ME = onAdsSwitcherListener2;
    }

    public void updateNetwork(String str) {
        String str2 = str;
        this.G6NGZkO3GzJPBmNJ6LwDoEfdjbnPkejEO5C5VyyDaUNT1yJXqTcFE5lxbNsfgCQk = str2;
    }

    public void loadAd(boolean z) {
        int hashCode = this.G6NGZkO3GzJPBmNJ6LwDoEfdjbnPkejEO5C5VyyDaUNT1yJXqTcFE5lxbNsfgCQk.hashCode();
        int i = Log.i("Kodular Commission Interstitial", "Loading Unity Ads Interstitial");
        UnityAds.initialize((Activity) this.context, KodularAdsUtil.UNITY_ADS_GAME_ID, z);
        UnityAds.addListener(this.f792hxYOFxFjLpN1maJuWNxUV40nExCGxsxkDPOTgtzMu4zlZCQb3bPlKsXo1SYJg6ME);
        this.TDuNe2Upxoi7WX7QZc6R8eUnFPyyDFw3hP7z3w6U8jMxQwJRQM03zSa9HzWXRv1 = true;
    }

    public boolean showAd() {
        int hashCode = this.G6NGZkO3GzJPBmNJ6LwDoEfdjbnPkejEO5C5VyyDaUNT1yJXqTcFE5lxbNsfgCQk.hashCode();
        int i = Log.i("Kodular Commission Interstitial", "Showing Unity Ads Interstitial");
        if (!UnityAds.isReady()) {
            return false;
        }
        UnityAds.show((Activity) this.context);
        return true;
    }

    /* renamed from: com.google.appinventor.components.runtime.util.ads.KodularInterstitial$a */
    class C1204a implements IUnityAdsExtendedListener {
        private /* synthetic */ KodularInterstitial B8WBXPBCF2jGfUDZZU2zV5EYdqbUBu0lAZ0THCEqYyuE8VACR9dY7rDnwBIqh64T;

        private C1204a(KodularInterstitial kodularInterstitial) {
            this.B8WBXPBCF2jGfUDZZU2zV5EYdqbUBu0lAZ0THCEqYyuE8VACR9dY7rDnwBIqh64T = kodularInterstitial;
        }

        /* JADX INFO: this call moved to the top of the method (can break code semantics) */
        /* synthetic */ C1204a(KodularInterstitial kodularInterstitial, byte b) {
            this(kodularInterstitial);
            byte b2 = b;
        }

        public final void onUnityAdsClick(String str) {
            String str2 = str;
            if (KodularInterstitial.hxYOFxFjLpN1maJuWNxUV40nExCGxsxkDPOTgtzMu4zlZCQb3bPlKsXo1SYJg6ME(this.B8WBXPBCF2jGfUDZZU2zV5EYdqbUBu0lAZ0THCEqYyuE8VACR9dY7rDnwBIqh64T) != null) {
                int i = Log.i("Kodular Commission Interstitial", "Triggering onAdsClick() for Unity Ads");
                KodularInterstitial.hxYOFxFjLpN1maJuWNxUV40nExCGxsxkDPOTgtzMu4zlZCQb3bPlKsXo1SYJg6ME(this.B8WBXPBCF2jGfUDZZU2zV5EYdqbUBu0lAZ0THCEqYyuE8VACR9dY7rDnwBIqh64T).onAdsClick();
            }
        }

        public final void onUnityAdsPlacementStateChanged(String str, UnityAds.PlacementState placementState, UnityAds.PlacementState placementState2) {
        }

        public final void onUnityAdsReady(String str) {
            String str2 = str;
            if (KodularInterstitial.hxYOFxFjLpN1maJuWNxUV40nExCGxsxkDPOTgtzMu4zlZCQb3bPlKsXo1SYJg6ME(this.B8WBXPBCF2jGfUDZZU2zV5EYdqbUBu0lAZ0THCEqYyuE8VACR9dY7rDnwBIqh64T) != null && KodularInterstitial.hxYOFxFjLpN1maJuWNxUV40nExCGxsxkDPOTgtzMu4zlZCQb3bPlKsXo1SYJg6ME(this.B8WBXPBCF2jGfUDZZU2zV5EYdqbUBu0lAZ0THCEqYyuE8VACR9dY7rDnwBIqh64T)) {
                boolean B8WBXPBCF2jGfUDZZU2zV5EYdqbUBu0lAZ0THCEqYyuE8VACR9dY7rDnwBIqh64T2 = this.B8WBXPBCF2jGfUDZZU2zV5EYdqbUBu0lAZ0THCEqYyuE8VACR9dY7rDnwBIqh64T.TDuNe2Upxoi7WX7QZc6R8eUnFPyyDFw3hP7z3w6U8jMxQwJRQM03zSa9HzWXRv1 = false;
                int i = Log.i("Kodular Commission Interstitial", "Triggering onAdsReady() for Unity Ads");
                KodularInterstitial.hxYOFxFjLpN1maJuWNxUV40nExCGxsxkDPOTgtzMu4zlZCQb3bPlKsXo1SYJg6ME(this.B8WBXPBCF2jGfUDZZU2zV5EYdqbUBu0lAZ0THCEqYyuE8VACR9dY7rDnwBIqh64T).onAdsReady();
            }
        }

        public final void onUnityAdsStart(String str) {
        }

        public final void onUnityAdsFinish(String str, UnityAds.FinishState finishState) {
            String str2 = str;
            UnityAds.FinishState finishState2 = finishState;
            if (KodularInterstitial.hxYOFxFjLpN1maJuWNxUV40nExCGxsxkDPOTgtzMu4zlZCQb3bPlKsXo1SYJg6ME(this.B8WBXPBCF2jGfUDZZU2zV5EYdqbUBu0lAZ0THCEqYyuE8VACR9dY7rDnwBIqh64T) != null) {
                int i = Log.i("Kodular Commission Interstitial", "Triggering onAdsClosed() for Unity Ads");
                KodularInterstitial.hxYOFxFjLpN1maJuWNxUV40nExCGxsxkDPOTgtzMu4zlZCQb3bPlKsXo1SYJg6ME(this.B8WBXPBCF2jGfUDZZU2zV5EYdqbUBu0lAZ0THCEqYyuE8VACR9dY7rDnwBIqh64T).onAdsClosed();
            }
        }

        public final void onUnityAdsError(UnityAds.UnityAdsError unityAdsError, String str) {
            UnityAds.UnityAdsError unityAdsError2 = unityAdsError;
            String str2 = str;
            if (KodularInterstitial.hxYOFxFjLpN1maJuWNxUV40nExCGxsxkDPOTgtzMu4zlZCQb3bPlKsXo1SYJg6ME(this.B8WBXPBCF2jGfUDZZU2zV5EYdqbUBu0lAZ0THCEqYyuE8VACR9dY7rDnwBIqh64T) != null) {
                int i = Log.i("Kodular Commission Interstitial", "Triggering onAdsError() for Unity Ads");
                KodularInterstitial.hxYOFxFjLpN1maJuWNxUV40nExCGxsxkDPOTgtzMu4zlZCQb3bPlKsXo1SYJg6ME(this.B8WBXPBCF2jGfUDZZU2zV5EYdqbUBu0lAZ0THCEqYyuE8VACR9dY7rDnwBIqh64T).onAdsError(str2);
            }
        }
    }
}
