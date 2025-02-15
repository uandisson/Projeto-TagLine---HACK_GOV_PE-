package com.google.appinventor.components.runtime.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class JavaStringUtils {
    private static final C1161c B8WBXPBCF2jGfUDZZU2zV5EYdqbUBu0lAZ0THCEqYyuE8VACR9dY7rDnwBIqh64T;
    public static final String LOG_TAG_JOIN_STRINGS = "JavaJoinListOfStrings";
    private static final C1161c hxYOFxFjLpN1maJuWNxUV40nExCGxsxkDPOTgtzMu4zlZCQb3bPlKsXo1SYJg6ME;

    /* renamed from: hxYOFxFjLpN1maJuWNxUV40nExCGxsxkDPOTgtzMu4zlZCQb3bPlKsXo1SYJg6ME  reason: collision with other field name */
    private static final Comparator<C1162d> f773hxYOFxFjLpN1maJuWNxUV40nExCGxsxkDPOTgtzMu4zlZCQb3bPlKsXo1SYJg6ME;
    private static final C1161c wq07duYRO6iFAgWM70EZOSvbCMKs1QznMRJKrct0XuHOBYqCk3XqOKtSBGIpDou;

    public JavaStringUtils() {
    }

    /* renamed from: com.google.appinventor.components.runtime.util.JavaStringUtils$c */
    static class C1161c {
        private C1161c() {
        }

        /* JADX INFO: this call moved to the top of the method (can break code semantics) */
        /* synthetic */ C1161c(byte b) {
            this();
            byte b2 = b;
        }

        public void hxYOFxFjLpN1maJuWNxUV40nExCGxsxkDPOTgtzMu4zlZCQb3bPlKsXo1SYJg6ME(List<String> list, String str) {
        }
    }

    /* renamed from: com.google.appinventor.components.runtime.util.JavaStringUtils$b */
    static class C1159b extends C1161c {
        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        private C1159b() {
            super((byte) 0);
        }

        /* JADX INFO: this call moved to the top of the method (can break code semantics) */
        /* synthetic */ C1159b(byte b) {
            this();
            byte b2 = b;
        }

        public final void hxYOFxFjLpN1maJuWNxUV40nExCGxsxkDPOTgtzMu4zlZCQb3bPlKsXo1SYJg6ME(List<String> list, String str) {
            Comparator comparator;
            String str2 = str;
            new Comparator<String>(this) {
                private /* synthetic */ C1159b hxYOFxFjLpN1maJuWNxUV40nExCGxsxkDPOTgtzMu4zlZCQb3bPlKsXo1SYJg6ME;

                {
                    this.hxYOFxFjLpN1maJuWNxUV40nExCGxsxkDPOTgtzMu4zlZCQb3bPlKsXo1SYJg6ME = r5;
                }

                public final /* synthetic */ int compare(Object obj, Object obj2) {
                    return Integer.compare(((String) obj2).length(), ((String) obj).length());
                }
            };
            Collections.sort(list, comparator);
        }
    }

    /* renamed from: com.google.appinventor.components.runtime.util.JavaStringUtils$a */
    static class C1157a extends C1161c {
        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        private C1157a() {
            super((byte) 0);
        }

        /* JADX INFO: this call moved to the top of the method (can break code semantics) */
        /* synthetic */ C1157a(byte b) {
            this();
            byte b2 = b;
        }

        public final void hxYOFxFjLpN1maJuWNxUV40nExCGxsxkDPOTgtzMu4zlZCQb3bPlKsXo1SYJg6ME(List<String> list, String str) {
            Map map;
            Comparator comparator;
            List<String> list2 = list;
            String str2 = str;
            new HashMap();
            Map map2 = map;
            for (String next : list2) {
                int indexOf = str2.indexOf(next);
                int i = indexOf;
                if (indexOf == -1) {
                    i = str2.length() + map2.size();
                }
                Object put = map2.put(next, Integer.valueOf(i));
            }
            final Map map3 = map2;
            new Comparator<String>(this) {
                private /* synthetic */ C1157a hxYOFxFjLpN1maJuWNxUV40nExCGxsxkDPOTgtzMu4zlZCQb3bPlKsXo1SYJg6ME;

                {
                    this.hxYOFxFjLpN1maJuWNxUV40nExCGxsxkDPOTgtzMu4zlZCQb3bPlKsXo1SYJg6ME = r6;
                }

                public final /* synthetic */ int compare(Object obj, Object obj2) {
                    String str = (String) obj2;
                    String str2 = (String) obj;
                    int intValue = ((Integer) map3.get(str2)).intValue();
                    int intValue2 = ((Integer) map3.get(str)).intValue();
                    if (intValue == intValue2) {
                        return Integer.compare(str.length(), str2.length());
                    }
                    return Integer.compare(intValue, intValue2);
                }
            };
            Collections.sort(list2, comparator);
        }
    }

    /* renamed from: com.google.appinventor.components.runtime.util.JavaStringUtils$d */
    static class C1162d {
        int NR00JNNlW621wVdOuGmKXPtjBpGajdPcd2Ky3026Pmc1Ihub1KfdGuuwHN0dv78V;
        int nItr2rEUwjLh7peU3NgfalZQrx3V2u3REmTnCv6vRXk7VqyYqrNzZhPvrb2eDYTE;
        String yHAbLPerXNK4pCwn5nqbt3OeUjDvQdxh29RmVa0moB4dUtgatbeaGoP5jClPUWFb;

        public C1162d(int i, int i2, String str) {
            this.nItr2rEUwjLh7peU3NgfalZQrx3V2u3REmTnCv6vRXk7VqyYqrNzZhPvrb2eDYTE = i;
            this.NR00JNNlW621wVdOuGmKXPtjBpGajdPcd2Ky3026Pmc1Ihub1KfdGuuwHN0dv78V = i2;
            this.yHAbLPerXNK4pCwn5nqbt3OeUjDvQdxh29RmVa0moB4dUtgatbeaGoP5jClPUWFb = str;
        }
    }

    /* renamed from: com.google.appinventor.components.runtime.util.JavaStringUtils$e */
    static class C1163e implements Comparator<C1162d> {
        private C1163e() {
        }

        /* JADX INFO: this call moved to the top of the method (can break code semantics) */
        /* synthetic */ C1163e(byte b) {
            this();
            byte b2 = b;
        }

        public final /* synthetic */ int compare(Object obj, Object obj2) {
            C1162d dVar = (C1162d) obj2;
            C1162d dVar2 = (C1162d) obj;
            C1162d dVar3 = dVar2;
            if (Math.max(dVar2.nItr2rEUwjLh7peU3NgfalZQrx3V2u3REmTnCv6vRXk7VqyYqrNzZhPvrb2eDYTE, dVar.nItr2rEUwjLh7peU3NgfalZQrx3V2u3REmTnCv6vRXk7VqyYqrNzZhPvrb2eDYTE) < Math.min(dVar3.NR00JNNlW621wVdOuGmKXPtjBpGajdPcd2Ky3026Pmc1Ihub1KfdGuuwHN0dv78V, dVar.NR00JNNlW621wVdOuGmKXPtjBpGajdPcd2Ky3026Pmc1Ihub1KfdGuuwHN0dv78V)) {
                return 0;
            }
            return Integer.compare(dVar.NR00JNNlW621wVdOuGmKXPtjBpGajdPcd2Ky3026Pmc1Ihub1KfdGuuwHN0dv78V, dVar3.NR00JNNlW621wVdOuGmKXPtjBpGajdPcd2Ky3026Pmc1Ihub1KfdGuuwHN0dv78V);
        }
    }

    static {
        C1161c cVar;
        C1161c cVar2;
        C1161c cVar3;
        Comparator<C1162d> comparator;
        new C1161c((byte) 0);
        hxYOFxFjLpN1maJuWNxUV40nExCGxsxkDPOTgtzMu4zlZCQb3bPlKsXo1SYJg6ME = cVar;
        new C1159b((byte) 0);
        B8WBXPBCF2jGfUDZZU2zV5EYdqbUBu0lAZ0THCEqYyuE8VACR9dY7rDnwBIqh64T = cVar2;
        new C1157a((byte) 0);
        wq07duYRO6iFAgWM70EZOSvbCMKs1QznMRJKrct0XuHOBYqCk3XqOKtSBGIpDou = cVar3;
        new C1163e((byte) 0);
        f773hxYOFxFjLpN1maJuWNxUV40nExCGxsxkDPOTgtzMu4zlZCQb3bPlKsXo1SYJg6ME = comparator;
    }

    public static String replaceAllMappingsDictionaryOrder(String str, Map<Object, Object> map) {
        return replaceAllMappings(str, map, hxYOFxFjLpN1maJuWNxUV40nExCGxsxkDPOTgtzMu4zlZCQb3bPlKsXo1SYJg6ME);
    }

    public static String replaceAllMappingsLongestStringOrder(String str, Map<Object, Object> map) {
        return replaceAllMappings(str, map, B8WBXPBCF2jGfUDZZU2zV5EYdqbUBu0lAZ0THCEqYyuE8VACR9dY7rDnwBIqh64T);
    }

    public static String replaceAllMappingsEarliestOccurrenceOrder(String str, Map<Object, Object> map) {
        return replaceAllMappings(str, map, wq07duYRO6iFAgWM70EZOSvbCMKs1QznMRJKrct0XuHOBYqCk3XqOKtSBGIpDou);
    }

    public static String replaceAllMappings(String str, Map<Object, Object> map, C1161c cVar) {
        Map map2;
        List list;
        String str2 = str;
        C1161c cVar2 = cVar;
        new HashMap();
        Map map3 = map2;
        new ArrayList();
        List list2 = list;
        for (Map.Entry next : map.entrySet()) {
            String obj = next.getKey().toString();
            String obj2 = next.getValue().toString();
            if (!map3.containsKey(obj)) {
                boolean add = list2.add(obj);
            }
            Object put = map3.put(obj, obj2);
        }
        cVar2.hxYOFxFjLpN1maJuWNxUV40nExCGxsxkDPOTgtzMu4zlZCQb3bPlKsXo1SYJg6ME(list2, str2);
        return hxYOFxFjLpN1maJuWNxUV40nExCGxsxkDPOTgtzMu4zlZCQb3bPlKsXo1SYJg6ME(str2, map3, list2);
    }

    private static String hxYOFxFjLpN1maJuWNxUV40nExCGxsxkDPOTgtzMu4zlZCQb3bPlKsXo1SYJg6ME(String str, Map<String, String> map, List<String> list) {
        TreeSet treeSet;
        StringBuilder sb;
        Object obj;
        String str2 = str;
        Map<String, String> map2 = map;
        new TreeSet(f773hxYOFxFjLpN1maJuWNxUV40nExCGxsxkDPOTgtzMu4zlZCQb3bPlKsXo1SYJg6ME);
        TreeSet treeSet2 = treeSet;
        for (String next : list) {
            Matcher matcher = Pattern.compile(Pattern.quote(next)).matcher(str2);
            String str3 = map2.get(next);
            while (matcher.find()) {
                new C1162d(matcher.start(), matcher.end(), str3);
                boolean add = treeSet2.add(obj);
            }
        }
        Iterator it = treeSet2.iterator();
        while (it.hasNext()) {
            C1162d dVar = (C1162d) it.next();
            String substring = str2.substring(0, dVar.nItr2rEUwjLh7peU3NgfalZQrx3V2u3REmTnCv6vRXk7VqyYqrNzZhPvrb2eDYTE);
            String str4 = dVar.yHAbLPerXNK4pCwn5nqbt3OeUjDvQdxh29RmVa0moB4dUtgatbeaGoP5jClPUWFb;
            String substring2 = str2.substring(dVar.NR00JNNlW621wVdOuGmKXPtjBpGajdPcd2Ky3026Pmc1Ihub1KfdGuuwHN0dv78V);
            new StringBuilder();
            str2 = sb.append(substring).append(str4).append(substring2).toString();
        }
        return str2;
    }

    public static String joinStrings(List<Object> list, String str) {
        StringBuilder sb;
        String str2 = str;
        new StringBuilder();
        StringBuilder sb2 = sb;
        boolean z = true;
        for (Object next : list) {
            if (z) {
                z = false;
            } else {
                StringBuilder append = sb2.append(str2);
            }
            StringBuilder append2 = sb2.append(next.toString());
        }
        return sb2.toString();
    }
}
