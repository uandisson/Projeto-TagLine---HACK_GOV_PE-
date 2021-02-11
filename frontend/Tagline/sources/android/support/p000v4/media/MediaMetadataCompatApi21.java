package android.support.p000v4.media;

import android.graphics.Bitmap;
import android.media.MediaMetadata;
import android.media.Rating;
import android.os.Parcel;
import android.support.annotation.RequiresApi;
import java.util.Set;

@RequiresApi(21)
/* renamed from: android.support.v4.media.MediaMetadataCompatApi21 */
class MediaMetadataCompatApi21 {
    public static Set<String> keySet(Object metadataObj) {
        return ((MediaMetadata) metadataObj).keySet();
    }

    public static Bitmap getBitmap(Object metadataObj, String key) {
        return ((MediaMetadata) metadataObj).getBitmap(key);
    }

    public static long getLong(Object metadataObj, String key) {
        return ((MediaMetadata) metadataObj).getLong(key);
    }

    public static Object getRating(Object metadataObj, String key) {
        return ((MediaMetadata) metadataObj).getRating(key);
    }

    public static CharSequence getText(Object metadataObj, String key) {
        return ((MediaMetadata) metadataObj).getText(key);
    }

    public static void writeToParcel(Object metadataObj, Parcel dest, int flags) {
        ((MediaMetadata) metadataObj).writeToParcel(dest, flags);
    }

    public static Object createFromParcel(Parcel in) {
        return MediaMetadata.CREATOR.createFromParcel(in);
    }

    /* renamed from: android.support.v4.media.MediaMetadataCompatApi21$Builder */
    public static class Builder {
        public static Object newInstance() {
            Object obj;
            Object obj2 = obj;
            new MediaMetadata.Builder();
            return obj2;
        }

        public static void putBitmap(Object builderObj, String key, Bitmap value) {
            MediaMetadata.Builder putBitmap = ((MediaMetadata.Builder) builderObj).putBitmap(key, value);
        }

        public static void putLong(Object builderObj, String key, long value) {
            MediaMetadata.Builder putLong = ((MediaMetadata.Builder) builderObj).putLong(key, value);
        }

        public static void putRating(Object builderObj, String key, Object ratingObj) {
            MediaMetadata.Builder putRating = ((MediaMetadata.Builder) builderObj).putRating(key, (Rating) ratingObj);
        }

        public static void putText(Object builderObj, String key, CharSequence value) {
            MediaMetadata.Builder putText = ((MediaMetadata.Builder) builderObj).putText(key, value);
        }

        public static void putString(Object builderObj, String key, String value) {
            MediaMetadata.Builder putString = ((MediaMetadata.Builder) builderObj).putString(key, value);
        }

        public static Object build(Object builderObj) {
            return ((MediaMetadata.Builder) builderObj).build();
        }

        private Builder() {
        }
    }

    private MediaMetadataCompatApi21() {
    }
}
