package com.google.appinventor.components.runtime.util;

import com.google.appinventor.components.runtime.Component;
import com.google.appinventor.components.runtime.Form;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class BulkPermissionRequest {
    private Component B8WBXPBCF2jGfUDZZU2zV5EYdqbUBu0lAZ0THCEqYyuE8VACR9dY7rDnwBIqh64T;
    private String xjswzNK7XIbcXWMkpjjnX2zHmaN4l94b4PGb57QPnGA2cyF8Wf0hPGvkbrRqJbgG;
    private String[] yKzj3OK1ig8r7pqFZ5OXQyoqJiWnRvwjZPZ1kORJGZPQRb8FuKJuM2qAKu5QCSLT;

    public abstract void onGranted();

    protected BulkPermissionRequest(Component component, String str, String... strArr) {
        this.B8WBXPBCF2jGfUDZZU2zV5EYdqbUBu0lAZ0THCEqYyuE8VACR9dY7rDnwBIqh64T = component;
        this.xjswzNK7XIbcXWMkpjjnX2zHmaN4l94b4PGb57QPnGA2cyF8Wf0hPGvkbrRqJbgG = str;
        this.yKzj3OK1ig8r7pqFZ5OXQyoqJiWnRvwjZPZ1kORJGZPQRb8FuKJuM2qAKu5QCSLT = strArr;
    }

    public void onDenied(String[] strArr) {
        String[] strArr2 = strArr;
        Form form = (Form) this.B8WBXPBCF2jGfUDZZU2zV5EYdqbUBu0lAZ0THCEqYyuE8VACR9dY7rDnwBIqh64T.getDispatchDelegate();
        int length = strArr2.length;
        for (int i = 0; i < length; i++) {
            form.dispatchPermissionDeniedEvent(this.B8WBXPBCF2jGfUDZZU2zV5EYdqbUBu0lAZ0THCEqYyuE8VACR9dY7rDnwBIqh64T, this.xjswzNK7XIbcXWMkpjjnX2zHmaN4l94b4PGb57QPnGA2cyF8Wf0hPGvkbrRqJbgG, strArr2[i]);
        }
    }

    public final List<String> getPermissions() {
        List<String> list;
        new ArrayList(this.yKzj3OK1ig8r7pqFZ5OXQyoqJiWnRvwjZPZ1kORJGZPQRb8FuKJuM2qAKu5QCSLT.length);
        List<String> list2 = list;
        List<String> list3 = list2;
        boolean addAll = Collections.addAll(list2, this.yKzj3OK1ig8r7pqFZ5OXQyoqJiWnRvwjZPZ1kORJGZPQRb8FuKJuM2qAKu5QCSLT);
        return list3;
    }
}
