package org.locationtech.jts;

public class JTSVersion {
    public static final JTSVersion CURRENT_VERSION;
    public static final int MAJOR = 1;
    public static final int MINOR = 14;
    public static final int PATCH = 0;
    private static final String releaseInfo = "";

    static {
        JTSVersion jTSVersion;
        new JTSVersion();
        CURRENT_VERSION = jTSVersion;
    }

    public static void main(String[] strArr) {
        String[] strArr2 = strArr;
        System.out.println(CURRENT_VERSION);
    }

    private JTSVersion() {
    }

    public int getMajor() {
        return 1;
    }

    public int getMinor() {
        return 14;
    }

    public int getPatch() {
        return 0;
    }

    public String toString() {
        StringBuilder sb;
        String ver = "1.14.0";
        if ("" == 0 || "".length() <= 0) {
            return ver;
        }
        new StringBuilder();
        return sb.append(ver).append(" ").append("").toString();
    }
}
