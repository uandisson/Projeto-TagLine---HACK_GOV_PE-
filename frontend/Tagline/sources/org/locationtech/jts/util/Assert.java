package org.locationtech.jts.util;

public class Assert {
    public Assert() {
    }

    public static void isTrue(boolean assertion) {
        isTrue(assertion, (String) null);
    }

    public static void isTrue(boolean assertion, String str) {
        Throwable th;
        Throwable th2;
        String message = str;
        if (assertion) {
            return;
        }
        if (message == null) {
            Throwable th3 = th2;
            new AssertionFailedException();
            throw th3;
        }
        Throwable th4 = th;
        new AssertionFailedException(message);
        throw th4;
    }

    public static void equals(Object expectedValue, Object actualValue) {
        equals(expectedValue, actualValue, (String) null);
    }

    public static void equals(Object obj, Object obj2, String str) {
        StringBuilder sb;
        String str2;
        StringBuilder sb2;
        Object expectedValue = obj;
        Object actualValue = obj2;
        String message = str;
        if (!actualValue.equals(expectedValue)) {
            AssertionFailedException assertionFailedException = r8;
            new StringBuilder();
            StringBuilder append = sb.append("Expected ").append(expectedValue).append(" but encountered ").append(actualValue);
            if (message != null) {
                new StringBuilder();
                str2 = sb2.append(": ").append(message).toString();
            } else {
                str2 = "";
            }
            AssertionFailedException assertionFailedException2 = new AssertionFailedException(append.append(str2).toString());
            throw assertionFailedException;
        }
    }

    public static void shouldNeverReachHere() {
        shouldNeverReachHere((String) null);
    }

    public static void shouldNeverReachHere(String str) {
        StringBuilder sb;
        String str2;
        StringBuilder sb2;
        String message = str;
        AssertionFailedException assertionFailedException = r6;
        new StringBuilder();
        StringBuilder append = sb.append("Should never reach here");
        if (message != null) {
            new StringBuilder();
            str2 = sb2.append(": ").append(message).toString();
        } else {
            str2 = "";
        }
        AssertionFailedException assertionFailedException2 = new AssertionFailedException(append.append(str2).toString());
        throw assertionFailedException;
    }
}
