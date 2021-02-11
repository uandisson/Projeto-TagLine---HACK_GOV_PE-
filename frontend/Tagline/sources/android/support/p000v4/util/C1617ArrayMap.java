package android.support.p000v4.util;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import java.util.Collection;
import java.util.Map;
import java.util.Set;

/* renamed from: android.support.v4.util.ArrayMap  reason: case insensitive filesystem */
public class C1617ArrayMap<K, V> extends C1625SimpleArrayMap<K, V> implements Map<K, V> {
    @Nullable
    C1624MapCollections<K, V> mCollections;

    public C1617ArrayMap() {
    }

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public C1617ArrayMap(int capacity) {
        super(capacity);
    }

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public C1617ArrayMap(C1625SimpleArrayMap map) {
        super(map);
    }

    private C1624MapCollections<K, V> getCollection() {
        C1624MapCollections<K, V> mapCollections;
        if (this.mCollections == null) {
            new C1624MapCollections<K, V>(this) {
                final /* synthetic */ C1617ArrayMap this$0;

                {
                    this.this$0 = this$0;
                }

                /* access modifiers changed from: protected */
                public int colGetSize() {
                    return this.this$0.mSize;
                }

                /* access modifiers changed from: protected */
                public Object colGetEntry(int index, int offset) {
                    return this.this$0.mArray[(index << 1) + offset];
                }

                /* access modifiers changed from: protected */
                public int colIndexOfKey(Object key) {
                    return this.this$0.indexOfKey(key);
                }

                /* access modifiers changed from: protected */
                public int colIndexOfValue(Object value) {
                    return this.this$0.indexOfValue(value);
                }

                /* access modifiers changed from: protected */
                public Map<K, V> colGetMap() {
                    return this.this$0;
                }

                /* access modifiers changed from: protected */
                public void colPut(K key, V value) {
                    Object put = this.this$0.put(key, value);
                }

                /* access modifiers changed from: protected */
                public V colSetValue(int index, V value) {
                    return this.this$0.setValueAt(index, value);
                }

                /* access modifiers changed from: protected */
                public void colRemoveAt(int index) {
                    Object removeAt = this.this$0.removeAt(index);
                }

                /* access modifiers changed from: protected */
                public void colClear() {
                    this.this$0.clear();
                }
            };
            this.mCollections = mapCollections;
        }
        return this.mCollections;
    }

    public boolean containsAll(@NonNull Collection<?> collection) {
        return C1624MapCollections.containsAllHelper(this, collection);
    }

    public void putAll(Map<? extends K, ? extends V> map) {
        Map<? extends K, ? extends V> map2 = map;
        ensureCapacity(this.mSize + map2.size());
        for (Map.Entry<? extends K, ? extends V> entry : map2.entrySet()) {
            Object put = put(entry.getKey(), entry.getValue());
        }
    }

    public boolean removeAll(@NonNull Collection<?> collection) {
        return C1624MapCollections.removeAllHelper(this, collection);
    }

    public boolean retainAll(@NonNull Collection<?> collection) {
        return C1624MapCollections.retainAllHelper(this, collection);
    }

    public Set<Map.Entry<K, V>> entrySet() {
        return getCollection().getEntrySet();
    }

    public Set<K> keySet() {
        return getCollection().getKeySet();
    }

    public Collection<V> values() {
        return getCollection().getValues();
    }
}
