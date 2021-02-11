package org.locationtech.jts.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.LineNumberReader;
import java.io.PrintStream;
import java.io.StringReader;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;

public class StringUtil {
    public static final String NEWLINE = System.getProperty("line.separator");
    private static NumberFormat SIMPLE_ORDINATE_FORMAT;

    public StringUtil() {
    }

    public static String[] split(String s, String str) {
        ArrayList arrayList;
        StringBuilder sb;
        String separator = str;
        int separatorlen = separator.length();
        new ArrayList();
        ArrayList tokenList = arrayList;
        new StringBuilder();
        String tmpString = sb.append("").append(s).toString();
        int indexOf = tmpString.indexOf(separator);
        while (true) {
            int pos = indexOf;
            if (pos < 0) {
                break;
            }
            boolean add = tokenList.add(tmpString.substring(0, pos));
            tmpString = tmpString.substring(pos + separatorlen);
            indexOf = tmpString.indexOf(separator);
        }
        if (tmpString.length() > 0) {
            boolean add2 = tokenList.add(tmpString);
        }
        String[] res = new String[tokenList.size()];
        for (int i = 0; i < res.length; i++) {
            res[i] = (String) tokenList.get(i);
        }
        return res;
    }

    static {
        NumberFormat numberFormat;
        new DecimalFormat("0.#");
        SIMPLE_ORDINATE_FORMAT = numberFormat;
    }

    public static String getStackTrace(Throwable t) {
        ByteArrayOutputStream byteArrayOutputStream;
        PrintStream ps;
        new ByteArrayOutputStream();
        ByteArrayOutputStream os = byteArrayOutputStream;
        new PrintStream(os);
        t.printStackTrace(ps);
        return os.toString();
    }

    public static String getStackTrace(Throwable t, int i) {
        StringReader stringReader;
        LineNumberReader lineNumberReader;
        StringBuilder sb;
        int depth = i;
        String stackTrace = "";
        new StringReader(getStackTrace(t));
        new LineNumberReader(stringReader);
        LineNumberReader lineNumberReader2 = lineNumberReader;
        for (int i2 = 0; i2 < depth; i2++) {
            try {
                new StringBuilder();
                stackTrace = sb.append(stackTrace).append(lineNumberReader2.readLine()).append(NEWLINE).toString();
            } catch (IOException e) {
                IOException iOException = e;
                Assert.shouldNeverReachHere();
            }
        }
        return stackTrace;
    }

    public static String toString(double d) {
        return SIMPLE_ORDINATE_FORMAT.format(d);
    }

    public static String spaces(int n) {
        return chars(' ', n);
    }

    public static String chars(char c, int i) {
        String str;
        char c2 = c;
        int n = i;
        char[] ch = new char[n];
        for (int i2 = 0; i2 < n; i2++) {
            ch[i2] = c2;
        }
        new String(ch);
        return str;
    }
}
