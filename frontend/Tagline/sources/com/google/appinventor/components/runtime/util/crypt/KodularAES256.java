package com.google.appinventor.components.runtime.util.crypt;

import android.content.Context;
import android.util.Base64;
import android.util.Log;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.KeySpec;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import org.jose4j.keys.AesKey;

public class KodularAES256 {
    private static byte[] B8WBXPBCF2jGfUDZZU2zV5EYdqbUBu0lAZ0THCEqYyuE8VACR9dY7rDnwBIqh64T = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
    private static String Czk3JxQndp8OXzdhVJbUdjsQzir0LjcvTO4sxxzJFetSb3s5Ea1z9Q4DZEvMPAr1 = "AES/CBC/PKCS5Padding";
    private static String OOU2L6ZPvPBvVsNChdspsFDAiRPppRoUY09Zs89r9dCXQgf8whit7Vv4m0wrV8PG = "PBKDF2WithHmacSHA256";
    private static int QMAkvNZ7PS9svrCQagoyYbfcp0VhAnlVqmqxnYH8cmskKuYxqiwVnZvGO7Q279iL = 256;
    private static int S8H3eYzvg6VJJXGfS9KsaceOQzr9BosWjP9J0PNzfqDjshHgeOMsb1PZpwnxucV5 = 65536;
    private static String SZ5aRRQoo4RABtl0KnlkQDhNcppg3ZUNkmsRmBa9EW4UdQenZXJCu8cuK0SgtVYc = AesKey.ALGORITHM;

    public KodularAES256() {
    }

    public static String encode(String str, String str2, Context context) {
        StringBuilder sb;
        AlgorithmParameterSpec algorithmParameterSpec;
        KeySpec keySpec;
        Key key;
        String str3 = str;
        String str4 = str2;
        try {
            byte[] bytes = CryptoPref.getSalt(context).getBytes("UTF-8");
            new IvParameterSpec(B8WBXPBCF2jGfUDZZU2zV5EYdqbUBu0lAZ0THCEqYyuE8VACR9dY7rDnwBIqh64T);
            AlgorithmParameterSpec algorithmParameterSpec2 = algorithmParameterSpec;
            SecretKeyFactory instance = SecretKeyFactory.getInstance(OOU2L6ZPvPBvVsNChdspsFDAiRPppRoUY09Zs89r9dCXQgf8whit7Vv4m0wrV8PG);
            new PBEKeySpec(str4.toCharArray(), bytes, S8H3eYzvg6VJJXGfS9KsaceOQzr9BosWjP9J0PNzfqDjshHgeOMsb1PZpwnxucV5, QMAkvNZ7PS9svrCQagoyYbfcp0VhAnlVqmqxnYH8cmskKuYxqiwVnZvGO7Q279iL);
            new SecretKeySpec(instance.generateSecret(keySpec).getEncoded(), SZ5aRRQoo4RABtl0KnlkQDhNcppg3ZUNkmsRmBa9EW4UdQenZXJCu8cuK0SgtVYc);
            Key key2 = key;
            Cipher instance2 = Cipher.getInstance(Czk3JxQndp8OXzdhVJbUdjsQzir0LjcvTO4sxxzJFetSb3s5Ea1z9Q4DZEvMPAr1);
            instance2.init(1, key2, algorithmParameterSpec2);
            return Base64.encodeToString(instance2.doFinal(str3.getBytes("UTF-8")), 0);
        } catch (Exception e) {
            new StringBuilder("Error while encrypting: ");
            int d = Log.d("KodularAES256", sb.append(e.toString()).toString());
            return "";
        }
    }

    public static String decode(String str, String str2, Context context) {
        StringBuilder sb;
        AlgorithmParameterSpec algorithmParameterSpec;
        KeySpec keySpec;
        Key key;
        String str3;
        String str4 = str;
        String str5 = str2;
        String salt = CryptoPref.getSalt(context);
        try {
            new IvParameterSpec(B8WBXPBCF2jGfUDZZU2zV5EYdqbUBu0lAZ0THCEqYyuE8VACR9dY7rDnwBIqh64T);
            AlgorithmParameterSpec algorithmParameterSpec2 = algorithmParameterSpec;
            SecretKeyFactory instance = SecretKeyFactory.getInstance(OOU2L6ZPvPBvVsNChdspsFDAiRPppRoUY09Zs89r9dCXQgf8whit7Vv4m0wrV8PG);
            new PBEKeySpec(str5.toCharArray(), salt.getBytes(), S8H3eYzvg6VJJXGfS9KsaceOQzr9BosWjP9J0PNzfqDjshHgeOMsb1PZpwnxucV5, QMAkvNZ7PS9svrCQagoyYbfcp0VhAnlVqmqxnYH8cmskKuYxqiwVnZvGO7Q279iL);
            new SecretKeySpec(instance.generateSecret(keySpec).getEncoded(), SZ5aRRQoo4RABtl0KnlkQDhNcppg3ZUNkmsRmBa9EW4UdQenZXJCu8cuK0SgtVYc);
            Key key2 = key;
            Cipher instance2 = Cipher.getInstance(Czk3JxQndp8OXzdhVJbUdjsQzir0LjcvTO4sxxzJFetSb3s5Ea1z9Q4DZEvMPAr1);
            instance2.init(2, key2, algorithmParameterSpec2);
            String str6 = str3;
            new String(instance2.doFinal(Base64.decode(str4.getBytes(), 0)));
            return str6;
        } catch (Exception e) {
            new StringBuilder("Error while decrypting: ");
            int d = Log.d("KodularAES256", sb.append(e.toString()).toString());
            return "";
        }
    }

    public static String generateKey() {
        StringBuilder sb;
        try {
            KeyGenerator instance = KeyGenerator.getInstance(AesKey.ALGORITHM);
            instance.init(256);
            return Base64.encodeToString(instance.generateKey().getEncoded(), 0);
        } catch (NoSuchAlgorithmException e) {
            new StringBuilder("generateKey: ");
            int d = Log.d("KodularAES256", sb.append(e.getMessage()).toString());
            return "";
        }
    }
}
