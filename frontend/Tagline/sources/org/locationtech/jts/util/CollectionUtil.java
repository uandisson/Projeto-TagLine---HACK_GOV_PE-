package org.locationtech.jts.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class CollectionUtil {

    public interface Function {
        Object execute(Object obj);
    }

    public CollectionUtil() {
    }

    public static List transform(Collection coll, Function function) {
        List list;
        Function func = function;
        new ArrayList();
        List result = list;
        for (Object execute : coll) {
            boolean add = result.add(func.execute(execute));
        }
        return result;
    }

    public static void apply(Collection coll, Function function) {
        Function func = function;
        for (Object execute : coll) {
            Object execute2 = func.execute(execute);
        }
    }

    public static List select(Collection collection, Function function) {
        List list;
        Function func = function;
        new ArrayList();
        List result = list;
        for (Object item : collection) {
            if (Boolean.TRUE.equals(func.execute(item))) {
                boolean add = result.add(item);
            }
        }
        return result;
    }
}
