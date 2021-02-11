package com.google.appinventor.common.version;

import org.jose4j.jws.AlgorithmIdentifiers;

public final class GitBuildId {
    public static final String ANT_BUILD_DATE = "September 4 2020";
    public static final String GIT_BUILD_FINGERPRINT = "6215d8a3ae6a85a39e60335300deb89c12429a64";
    public static final String GIT_BUILD_VERSION = "1.4D.1-Eagle";

    private GitBuildId() {
    }

    public static String getVersion() {
        String str = GIT_BUILD_VERSION;
        String str2 = str;
        return (str == "" || str2.contains(" ")) ? AlgorithmIdentifiers.NONE : str2;
    }

    public static String getFingerprint() {
        return GIT_BUILD_FINGERPRINT;
    }

    public static String getDate() {
        return ANT_BUILD_DATE;
    }
}
