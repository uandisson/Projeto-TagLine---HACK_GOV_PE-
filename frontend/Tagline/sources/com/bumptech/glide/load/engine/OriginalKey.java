package com.bumptech.glide.load.engine;

import com.bumptech.glide.load.Key;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;

class OriginalKey implements Key {

    /* renamed from: id */
    private final String f281id;
    private final Key signature;

    public OriginalKey(String id, Key signature2) {
        this.f281id = id;
        this.signature = signature2;
    }

    public boolean equals(Object obj) {
        Object o = obj;
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        OriginalKey that = (OriginalKey) o;
        if (!this.f281id.equals(that.f281id)) {
            return false;
        }
        if (!this.signature.equals(that.signature)) {
            return false;
        }
        return true;
    }

    public int hashCode() {
        return (31 * this.f281id.hashCode()) + this.signature.hashCode();
    }

    public void updateDiskCacheKey(MessageDigest messageDigest) throws UnsupportedEncodingException {
        MessageDigest messageDigest2 = messageDigest;
        messageDigest2.update(this.f281id.getBytes("UTF-8"));
        this.signature.updateDiskCacheKey(messageDigest2);
    }
}
