package org.jose4j.jwe;

public class ContentEncryptionParts {
    private byte[] authenticationTag;
    private byte[] ciphertext;

    /* renamed from: iv */
    private byte[] f395iv;

    public ContentEncryptionParts(byte[] iv, byte[] ciphertext2, byte[] authenticationTag2) {
        this.f395iv = iv;
        this.ciphertext = ciphertext2;
        this.authenticationTag = authenticationTag2;
    }

    public byte[] getIv() {
        return this.f395iv;
    }

    public byte[] getCiphertext() {
        return this.ciphertext;
    }

    public byte[] getAuthenticationTag() {
        return this.authenticationTag;
    }
}
