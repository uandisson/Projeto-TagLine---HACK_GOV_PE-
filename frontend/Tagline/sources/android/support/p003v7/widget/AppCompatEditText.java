package android.support.p003v7.widget;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;
import android.support.annotation.RestrictTo;
import android.support.p000v4.view.TintableBackgroundView;
import android.support.p000v4.widget.TextViewCompat;
import android.support.p003v7.appcompat.C0425R;
import android.text.Editable;
import android.util.AttributeSet;
import android.view.ActionMode;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputConnection;
import android.widget.EditText;

/* renamed from: android.support.v7.widget.AppCompatEditText */
public class AppCompatEditText extends EditText implements TintableBackgroundView {
    private final AppCompatBackgroundHelper mBackgroundTintHelper;
    private final AppCompatTextHelper mTextHelper;

    /* JADX INFO: this call moved to the top of the method (can break code semantics) */
    public AppCompatEditText(Context context) {
        this(context, (AttributeSet) null);
    }

    /* JADX INFO: this call moved to the top of the method (can break code semantics) */
    public AppCompatEditText(Context context, AttributeSet attrs) {
        this(context, attrs, C0425R.attr.editTextStyle);
    }

    /* JADX WARNING: Illegal instructions before constructor call */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public AppCompatEditText(android.content.Context r10, android.util.AttributeSet r11, int r12) {
        /*
            r9 = this;
            r0 = r9
            r1 = r10
            r2 = r11
            r3 = r12
            r4 = r0
            r5 = r1
            android.content.Context r5 = android.support.p003v7.widget.TintContextWrapper.wrap(r5)
            r6 = r2
            r7 = r3
            r4.<init>(r5, r6, r7)
            r4 = r0
            android.support.v7.widget.AppCompatBackgroundHelper r5 = new android.support.v7.widget.AppCompatBackgroundHelper
            r8 = r5
            r5 = r8
            r6 = r8
            r7 = r0
            r6.<init>(r7)
            r4.mBackgroundTintHelper = r5
            r4 = r0
            android.support.v7.widget.AppCompatBackgroundHelper r4 = r4.mBackgroundTintHelper
            r5 = r2
            r6 = r3
            r4.loadFromAttributes(r5, r6)
            r4 = r0
            android.support.v7.widget.AppCompatTextHelper r5 = new android.support.v7.widget.AppCompatTextHelper
            r8 = r5
            r5 = r8
            r6 = r8
            r7 = r0
            r6.<init>(r7)
            r4.mTextHelper = r5
            r4 = r0
            android.support.v7.widget.AppCompatTextHelper r4 = r4.mTextHelper
            r5 = r2
            r6 = r3
            r4.loadFromAttributes(r5, r6)
            r4 = r0
            android.support.v7.widget.AppCompatTextHelper r4 = r4.mTextHelper
            r4.applyCompoundDrawablesTints()
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: android.support.p003v7.widget.AppCompatEditText.<init>(android.content.Context, android.util.AttributeSet, int):void");
    }

    @Nullable
    public Editable getText() {
        if (Build.VERSION.SDK_INT >= 28) {
            return super.getText();
        }
        return super.getEditableText();
    }

    public void setBackgroundResource(@DrawableRes int i) {
        int resId = i;
        super.setBackgroundResource(resId);
        if (this.mBackgroundTintHelper != null) {
            this.mBackgroundTintHelper.onSetBackgroundResource(resId);
        }
    }

    public void setBackgroundDrawable(Drawable drawable) {
        Drawable background = drawable;
        super.setBackgroundDrawable(background);
        if (this.mBackgroundTintHelper != null) {
            this.mBackgroundTintHelper.onSetBackgroundDrawable(background);
        }
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public void setSupportBackgroundTintList(@Nullable ColorStateList colorStateList) {
        ColorStateList tint = colorStateList;
        if (this.mBackgroundTintHelper != null) {
            this.mBackgroundTintHelper.setSupportBackgroundTintList(tint);
        }
    }

    @Nullable
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public ColorStateList getSupportBackgroundTintList() {
        return this.mBackgroundTintHelper != null ? this.mBackgroundTintHelper.getSupportBackgroundTintList() : null;
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public void setSupportBackgroundTintMode(@Nullable PorterDuff.Mode mode) {
        PorterDuff.Mode tintMode = mode;
        if (this.mBackgroundTintHelper != null) {
            this.mBackgroundTintHelper.setSupportBackgroundTintMode(tintMode);
        }
    }

    @Nullable
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public PorterDuff.Mode getSupportBackgroundTintMode() {
        return this.mBackgroundTintHelper != null ? this.mBackgroundTintHelper.getSupportBackgroundTintMode() : null;
    }

    /* access modifiers changed from: protected */
    public void drawableStateChanged() {
        super.drawableStateChanged();
        if (this.mBackgroundTintHelper != null) {
            this.mBackgroundTintHelper.applySupportBackgroundTint();
        }
        if (this.mTextHelper != null) {
            this.mTextHelper.applyCompoundDrawablesTints();
        }
    }

    public void setTextAppearance(Context context, int i) {
        Context context2 = context;
        int resId = i;
        super.setTextAppearance(context2, resId);
        if (this.mTextHelper != null) {
            this.mTextHelper.onSetTextAppearance(context2, resId);
        }
    }

    public InputConnection onCreateInputConnection(EditorInfo editorInfo) {
        EditorInfo outAttrs = editorInfo;
        return AppCompatHintHelper.onCreateInputConnection(super.onCreateInputConnection(outAttrs), outAttrs, this);
    }

    public void setCustomSelectionActionModeCallback(ActionMode.Callback actionModeCallback) {
        super.setCustomSelectionActionModeCallback(TextViewCompat.wrapCustomSelectionActionModeCallback(this, actionModeCallback));
    }
}
