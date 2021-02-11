package org.locationtech.jts.util;

public class AssertionFailedException extends RuntimeException {
    public AssertionFailedException() {
    }

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public AssertionFailedException(String message) {
        super(message);
    }
}
