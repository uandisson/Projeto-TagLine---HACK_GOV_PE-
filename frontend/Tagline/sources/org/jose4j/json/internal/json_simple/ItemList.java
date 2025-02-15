package org.jose4j.json.internal.json_simple;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class ItemList {
    List items;

    /* renamed from: sp */
    private String f393sp = ",";

    public ItemList() {
        List list;
        new ArrayList();
        this.items = list;
    }

    public ItemList(String s) {
        List list;
        new ArrayList();
        this.items = list;
        split(s, this.f393sp, this.items);
    }

    public ItemList(String str, String sp) {
        List list;
        String s = str;
        new ArrayList();
        this.items = list;
        this.f393sp = s;
        split(s, sp, this.items);
    }

    public ItemList(String s, String sp, boolean isMultiToken) {
        List list;
        new ArrayList();
        this.items = list;
        split(s, sp, this.items, isMultiToken);
    }

    public List getItems() {
        return this.items;
    }

    public String[] getArray() {
        return (String[]) this.items.toArray();
    }

    public void split(String str, String str2, List list, boolean z) {
        StringTokenizer stringTokenizer;
        String s = str;
        String sp = str2;
        List append = list;
        boolean isMultiToken = z;
        if (s != null && sp != null) {
            if (isMultiToken) {
                new StringTokenizer(s, sp);
                StringTokenizer tokens = stringTokenizer;
                while (tokens.hasMoreTokens()) {
                    boolean add = append.add(tokens.nextToken().trim());
                }
                return;
            }
            split(s, sp, append);
        }
    }

    public void split(String str, String str2, List list) {
        int prevPos;
        String s = str;
        String sp = str2;
        List append = list;
        if (s != null && sp != null) {
            int pos = 0;
            do {
                prevPos = pos;
                int pos2 = s.indexOf(sp, pos);
                if (pos2 == -1) {
                    break;
                }
                boolean add = append.add(s.substring(prevPos, pos2).trim());
                pos = pos2 + sp.length();
            } while (pos != -1);
            boolean add2 = append.add(s.substring(prevPos).trim());
        }
    }

    public void setSP(String sp) {
        String str = sp;
        this.f393sp = str;
    }

    public void add(int i, String str) {
        int i2 = i;
        String item = str;
        if (item != null) {
            this.items.add(i2, item.trim());
        }
    }

    public void add(String str) {
        String item = str;
        if (item != null) {
            boolean add = this.items.add(item.trim());
        }
    }

    public void addAll(ItemList list) {
        boolean addAll = this.items.addAll(list.items);
    }

    public void addAll(String s) {
        split(s, this.f393sp, this.items);
    }

    public void addAll(String s, String sp) {
        split(s, sp, this.items);
    }

    public void addAll(String s, String sp, boolean isMultiToken) {
        split(s, sp, this.items, isMultiToken);
    }

    public String get(int i) {
        return (String) this.items.get(i);
    }

    public int size() {
        return this.items.size();
    }

    public String toString() {
        return toString(this.f393sp);
    }

    public String toString(String str) {
        StringBuilder sb;
        String sp = str;
        new StringBuilder();
        StringBuilder sb2 = sb;
        for (int i = 0; i < this.items.size(); i++) {
            if (i == 0) {
                StringBuilder append = sb2.append(this.items.get(i));
            } else {
                StringBuilder append2 = sb2.append(sp);
                StringBuilder append3 = sb2.append(this.items.get(i));
            }
        }
        return sb2.toString();
    }

    public void clear() {
        this.items.clear();
    }

    public void reset() {
        this.f393sp = ",";
        this.items.clear();
    }
}
