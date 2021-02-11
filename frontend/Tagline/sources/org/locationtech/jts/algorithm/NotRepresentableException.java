package org.locationtech.jts.algorithm;

public class NotRepresentableException extends Exception {
    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public NotRepresentableException() {
        super("Projective point not representable on the Cartesian plane.");
    }
}
