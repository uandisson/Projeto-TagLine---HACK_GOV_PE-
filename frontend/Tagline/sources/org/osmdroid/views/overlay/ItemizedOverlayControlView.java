package org.osmdroid.views.overlay;

import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;

public class ItemizedOverlayControlView extends LinearLayout {
    protected ImageButton mCenterToButton;
    protected ItemizedOverlayControlViewListener mLis;
    protected ImageButton mNavToButton;
    protected ImageButton mNextButton;
    protected ImageButton mPreviousButton;

    public interface ItemizedOverlayControlViewListener {
        void onCenter();

        void onNavTo();

        void onNext();

        void onPrevious();
    }

    /* JADX WARNING: Illegal instructions before constructor call */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public ItemizedOverlayControlView(android.content.Context r11, android.util.AttributeSet r12) {
        /*
            r10 = this;
            r0 = r10
            r1 = r11
            r2 = r12
            r3 = r0
            r4 = r1
            r5 = r2
            r3.<init>(r4, r5)
            r3 = r0
            android.widget.ImageButton r4 = new android.widget.ImageButton
            r9 = r4
            r4 = r9
            r5 = r9
            r6 = r1
            r5.<init>(r6)
            r3.mPreviousButton = r4
            r3 = r0
            android.widget.ImageButton r3 = r3.mPreviousButton
            r4 = r1
            android.content.res.Resources r4 = r4.getResources()
            int r5 = org.osmdroid.library.C1262R.C1263drawable.previous
            android.graphics.drawable.Drawable r4 = r4.getDrawable(r5)
            r3.setImageDrawable(r4)
            r3 = r0
            android.widget.ImageButton r4 = new android.widget.ImageButton
            r9 = r4
            r4 = r9
            r5 = r9
            r6 = r1
            r5.<init>(r6)
            r3.mNextButton = r4
            r3 = r0
            android.widget.ImageButton r3 = r3.mNextButton
            r4 = r1
            android.content.res.Resources r4 = r4.getResources()
            int r5 = org.osmdroid.library.C1262R.C1263drawable.next
            android.graphics.drawable.Drawable r4 = r4.getDrawable(r5)
            r3.setImageDrawable(r4)
            r3 = r0
            android.widget.ImageButton r4 = new android.widget.ImageButton
            r9 = r4
            r4 = r9
            r5 = r9
            r6 = r1
            r5.<init>(r6)
            r3.mCenterToButton = r4
            r3 = r0
            android.widget.ImageButton r3 = r3.mCenterToButton
            r4 = r1
            android.content.res.Resources r4 = r4.getResources()
            int r5 = org.osmdroid.library.C1262R.C1263drawable.center
            android.graphics.drawable.Drawable r4 = r4.getDrawable(r5)
            r3.setImageDrawable(r4)
            r3 = r0
            android.widget.ImageButton r4 = new android.widget.ImageButton
            r9 = r4
            r4 = r9
            r5 = r9
            r6 = r1
            r5.<init>(r6)
            r3.mNavToButton = r4
            r3 = r0
            android.widget.ImageButton r3 = r3.mNavToButton
            r4 = r1
            android.content.res.Resources r4 = r4.getResources()
            int r5 = org.osmdroid.library.C1262R.C1263drawable.navto_small
            android.graphics.drawable.Drawable r4 = r4.getDrawable(r5)
            r3.setImageDrawable(r4)
            r3 = r0
            r4 = r0
            android.widget.ImageButton r4 = r4.mPreviousButton
            android.widget.LinearLayout$LayoutParams r5 = new android.widget.LinearLayout$LayoutParams
            r9 = r5
            r5 = r9
            r6 = r9
            r7 = -2
            r8 = -2
            r6.<init>(r7, r8)
            r3.addView(r4, r5)
            r3 = r0
            r4 = r0
            android.widget.ImageButton r4 = r4.mCenterToButton
            android.widget.LinearLayout$LayoutParams r5 = new android.widget.LinearLayout$LayoutParams
            r9 = r5
            r5 = r9
            r6 = r9
            r7 = -2
            r8 = -2
            r6.<init>(r7, r8)
            r3.addView(r4, r5)
            r3 = r0
            r4 = r0
            android.widget.ImageButton r4 = r4.mNavToButton
            android.widget.LinearLayout$LayoutParams r5 = new android.widget.LinearLayout$LayoutParams
            r9 = r5
            r5 = r9
            r6 = r9
            r7 = -2
            r8 = -2
            r6.<init>(r7, r8)
            r3.addView(r4, r5)
            r3 = r0
            r4 = r0
            android.widget.ImageButton r4 = r4.mNextButton
            android.widget.LinearLayout$LayoutParams r5 = new android.widget.LinearLayout$LayoutParams
            r9 = r5
            r5 = r9
            r6 = r9
            r7 = -2
            r8 = -2
            r6.<init>(r7, r8)
            r3.addView(r4, r5)
            r3 = r0
            r3.initViewListeners()
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: org.osmdroid.views.overlay.ItemizedOverlayControlView.<init>(android.content.Context, android.util.AttributeSet):void");
    }

    public void setItemizedOverlayControlViewListener(ItemizedOverlayControlViewListener lis) {
        ItemizedOverlayControlViewListener itemizedOverlayControlViewListener = lis;
        this.mLis = itemizedOverlayControlViewListener;
    }

    public void setNextEnabled(boolean pEnabled) {
        this.mNextButton.setEnabled(pEnabled);
    }

    public void setPreviousEnabled(boolean pEnabled) {
        this.mPreviousButton.setEnabled(pEnabled);
    }

    public void setNavToVisible(int pVisibility) {
        this.mNavToButton.setVisibility(pVisibility);
    }

    private void initViewListeners() {
        View.OnClickListener onClickListener;
        View.OnClickListener onClickListener2;
        View.OnClickListener onClickListener3;
        View.OnClickListener onClickListener4;
        new View.OnClickListener(this) {
            final /* synthetic */ ItemizedOverlayControlView this$0;

            {
                this.this$0 = this$0;
            }

            public void onClick(View view) {
                View view2 = view;
                if (this.this$0.mLis != null) {
                    this.this$0.mLis.onNext();
                }
            }
        };
        this.mNextButton.setOnClickListener(onClickListener);
        new View.OnClickListener(this) {
            final /* synthetic */ ItemizedOverlayControlView this$0;

            {
                this.this$0 = this$0;
            }

            public void onClick(View view) {
                View view2 = view;
                if (this.this$0.mLis != null) {
                    this.this$0.mLis.onPrevious();
                }
            }
        };
        this.mPreviousButton.setOnClickListener(onClickListener2);
        new View.OnClickListener(this) {
            final /* synthetic */ ItemizedOverlayControlView this$0;

            {
                this.this$0 = this$0;
            }

            public void onClick(View view) {
                View view2 = view;
                if (this.this$0.mLis != null) {
                    this.this$0.mLis.onCenter();
                }
            }
        };
        this.mCenterToButton.setOnClickListener(onClickListener3);
        new View.OnClickListener(this) {
            final /* synthetic */ ItemizedOverlayControlView this$0;

            {
                this.this$0 = this$0;
            }

            public void onClick(View view) {
                View view2 = view;
                if (this.this$0.mLis != null) {
                    this.this$0.mLis.onNavTo();
                }
            }
        };
        this.mNavToButton.setOnClickListener(onClickListener4);
    }
}
