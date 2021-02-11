package org.osmdroid.views.overlay.infowindow;

import android.content.Context;
import android.text.Html;
import android.util.Log;
import android.widget.TextView;
import org.osmdroid.api.IMapView;
import org.osmdroid.views.overlay.OverlayWithIW;

public class BasicInfoWindow extends InfoWindow {
    public static final int UNDEFINED_RES_ID = 0;
    static int mDescriptionId = 0;
    static int mImageId = 0;
    static int mSubDescriptionId = 0;
    static int mTitleId = 0;

    private static void setResIds(Context context) {
        StringBuilder sb;
        Context context2 = context;
        String packageName = context2.getPackageName();
        mTitleId = context2.getResources().getIdentifier("id/bubble_title", (String) null, packageName);
        mDescriptionId = context2.getResources().getIdentifier("id/bubble_description", (String) null, packageName);
        mSubDescriptionId = context2.getResources().getIdentifier("id/bubble_subdescription", (String) null, packageName);
        mImageId = context2.getResources().getIdentifier("id/bubble_image", (String) null, packageName);
        if (mTitleId == 0 || mDescriptionId == 0 || mSubDescriptionId == 0 || mImageId == 0) {
            new StringBuilder();
            int e = Log.e(IMapView.LOGTAG, sb.append("BasicInfoWindow: unable to get res ids in ").append(packageName).toString());
        }
    }

    /* JADX WARNING: Illegal instructions before constructor call */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public BasicInfoWindow(int r9, org.osmdroid.views.MapView r10) {
        /*
            r8 = this;
            r0 = r8
            r1 = r9
            r2 = r10
            r3 = r0
            r4 = r1
            r5 = r2
            r3.<init>(r4, r5)
            int r3 = mTitleId
            if (r3 != 0) goto L_0x0015
            r3 = r2
            android.content.Context r3 = r3.getContext()
            setResIds(r3)
        L_0x0015:
            r3 = r0
            android.view.View r3 = r3.mView
            org.osmdroid.views.overlay.infowindow.BasicInfoWindow$1 r4 = new org.osmdroid.views.overlay.infowindow.BasicInfoWindow$1
            r7 = r4
            r4 = r7
            r5 = r7
            r6 = r0
            r5.<init>(r6)
            r3.setOnTouchListener(r4)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: org.osmdroid.views.overlay.infowindow.BasicInfoWindow.<init>(int, org.osmdroid.views.MapView):void");
    }

    public void onOpen(Object item) {
        OverlayWithIW overlay = (OverlayWithIW) item;
        String title = overlay.getTitle();
        if (title == null) {
            title = "";
        }
        if (this.mView == null) {
            int w = Log.w(IMapView.LOGTAG, "Error trapped, BasicInfoWindow.open, mView is null!");
            return;
        }
        TextView temp = (TextView) this.mView.findViewById(mTitleId);
        if (temp != null) {
            temp.setText(title);
        }
        String snippet = overlay.getSnippet();
        if (snippet == null) {
            snippet = "";
        }
        ((TextView) this.mView.findViewById(mDescriptionId)).setText(Html.fromHtml(snippet));
        TextView subDescText = (TextView) this.mView.findViewById(mSubDescriptionId);
        String subDesc = overlay.getSubDescription();
        if (subDesc == null || "".equals(subDesc)) {
            subDescText.setVisibility(8);
            return;
        }
        subDescText.setText(Html.fromHtml(subDesc));
        subDescText.setVisibility(0);
    }

    public void onClose() {
    }
}
