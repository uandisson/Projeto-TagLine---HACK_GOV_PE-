package org.locationtech.jts.util;

import java.util.HashMap;
import java.util.Map;

public class ObjectCounter {
    private Map counts;

    public ObjectCounter() {
        Map map;
        new HashMap();
        this.counts = map;
    }

    public void add(Object obj) {
        Object obj2;
        Object o = obj;
        Counter counter = (Counter) this.counts.get(o);
        if (counter == null) {
            new Counter(1);
            Object put = this.counts.put(o, obj2);
            return;
        }
        counter.increment();
    }

    public int count(Object o) {
        Counter counter = (Counter) this.counts.get(o);
        if (counter == null) {
            return 0;
        }
        return counter.count();
    }

    private static class Counter {
        int count = 0;

        public Counter() {
        }

        public Counter(int count2) {
            this.count = count2;
        }

        public int count() {
            return this.count;
        }

        public void increment() {
            this.count++;
        }
    }
}
