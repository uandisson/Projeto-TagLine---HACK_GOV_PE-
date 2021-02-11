package android.support.transition;

import android.support.p000v4.util.C1617ArrayMap;
import android.support.p000v4.util.C1622LongSparseArray;
import android.util.SparseArray;
import android.view.View;

class TransitionValuesMaps {
    final SparseArray<View> mIdValues;
    final C1622LongSparseArray<View> mItemIdValues;
    final C1617ArrayMap<String, View> mNameValues;
    final C1617ArrayMap<View, TransitionValues> mViewValues;

    TransitionValuesMaps() {
        C1617ArrayMap<View, TransitionValues> arrayMap;
        SparseArray<View> sparseArray;
        C1622LongSparseArray<View> longSparseArray;
        C1617ArrayMap<String, View> arrayMap2;
        new C1617ArrayMap<>();
        this.mViewValues = arrayMap;
        new SparseArray<>();
        this.mIdValues = sparseArray;
        new C1622LongSparseArray<>();
        this.mItemIdValues = longSparseArray;
        new C1617ArrayMap<>();
        this.mNameValues = arrayMap2;
    }
}
