package com.bumptech.glide.load.data;

import com.bumptech.glide.Priority;
import java.io.ByteArrayInputStream;
import java.io.InputStream;

public class ByteArrayFetcher implements DataFetcher<InputStream> {
    private final byte[] bytes;

    /* renamed from: id */
    private final String f278id;

    public ByteArrayFetcher(byte[] bytes2, String id) {
        this.bytes = bytes2;
        this.f278id = id;
    }

    public InputStream loadData(Priority priority) {
        InputStream inputStream;
        Priority priority2 = priority;
        new ByteArrayInputStream(this.bytes);
        return inputStream;
    }

    public void cleanup() {
    }

    public String getId() {
        return this.f278id;
    }

    public void cancel() {
    }
}
