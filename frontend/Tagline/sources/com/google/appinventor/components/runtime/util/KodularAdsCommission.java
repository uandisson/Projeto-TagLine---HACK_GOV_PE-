package com.google.appinventor.components.runtime.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import com.google.appinventor.components.runtime.Component;
import com.google.appinventor.components.runtime.Form;
import java.nio.charset.StandardCharsets;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.security.spec.X509EncodedKeySpec;
import org.jose4j.jwa.AlgorithmConstraints;
import org.jose4j.jws.AlgorithmIdentifiers;
import org.jose4j.jwt.consumer.InvalidJwtException;
import org.jose4j.jwt.consumer.JwtConsumerBuilder;

public class KodularAdsCommission {
    private final SharedPreferences B8WBXPBCF2jGfUDZZU2zV5EYdqbUBu0lAZ0THCEqYyuE8VACR9dY7rDnwBIqh64T;
    private String LOG_TAG = "KodularAdsCommission";
    private final Context context;
    private final KodularBilling hxYOFxFjLpN1maJuWNxUV40nExCGxsxkDPOTgtzMu4zlZCQb3bPlKsXo1SYJg6ME;

    public KodularAdsCommission(Context context2, Form form) {
        StringBuilder sb;
        KodularBilling kodularBilling;
        C1165a aVar;
        Context context3 = context2;
        Form form2 = form;
        this.context = context3;
        this.B8WBXPBCF2jGfUDZZU2zV5EYdqbUBu0lAZ0THCEqYyuE8VACR9dY7rDnwBIqh64T = this.context.getSharedPreferences(getKodularPackageName(), 0);
        SharedPreferences sharedPreferences = this.B8WBXPBCF2jGfUDZZU2zV5EYdqbUBu0lAZ0THCEqYyuE8VACR9dY7rDnwBIqh64T;
        new StringBuilder();
        if (!sharedPreferences.contains(sb.append(getKodularPackageName()).append(".kcv").toString())) {
            new C1165a((byte) 0);
            AsyncTask execute = aVar.execute(new Form[]{form2});
        }
        new KodularBilling(context3, form2);
        this.hxYOFxFjLpN1maJuWNxUV40nExCGxsxkDPOTgtzMu4zlZCQb3bPlKsXo1SYJg6ME = kodularBilling;
    }

    public float getCommision(String str, String str2) {
        boolean z;
        float f;
        StringBuilder sb;
        String str3 = str;
        String str4 = str2;
        float f2 = 0.0f;
        if (this.hxYOFxFjLpN1maJuWNxUV40nExCGxsxkDPOTgtzMu4zlZCQb3bPlKsXo1SYJg6ME.hasCommissionRemoved()) {
            return 0.0f;
        }
        try {
            SharedPreferences sharedPreferences = this.B8WBXPBCF2jGfUDZZU2zV5EYdqbUBu0lAZ0THCEqYyuE8VACR9dY7rDnwBIqh64T;
            new StringBuilder();
            float parseFloat = Float.parseFloat(sharedPreferences.getString(sb.append(getKodularPackageName()).append(".kcv").toString(), "30").trim());
            float f3 = parseFloat;
            f2 = parseFloat / 100.0f;
        } catch (Exception e) {
            int e2 = Log.e(this.LOG_TAG, String.valueOf(e));
        }
        if (this.context.getPackageName().equals(getKodularPackageName()) || this.context.getPackageName().equals(getKodularPackageName().replace("io.kodular", "com.appybuilder")) || this.context.getPackageName().equals(getKodularPackageName().replace("io.kodular", "com.thunkable"))) {
            z = false;
        } else {
            z = true;
        }
        if (z) {
            f2 += 0.04f;
        }
        String str5 = str3;
        boolean z2 = true;
        switch (str5.hashCode()) {
            case -1414265340:
                if (str5.equals("amazon")) {
                    z2 = true;
                    break;
                }
                break;
            case -1249910051:
                if (str5.equals("adcolony")) {
                    z2 = false;
                    break;
                }
                break;
            case 92668925:
                if (str5.equals("admob")) {
                    z2 = true;
                    break;
                }
                break;
            case 111433589:
                if (str5.equals("unity")) {
                    z2 = true;
                    break;
                }
                break;
            case 497130182:
                if (str5.equals("facebook")) {
                    z2 = true;
                    break;
                }
                break;
            case 1179703863:
                if (str5.equals("applovin")) {
                    z2 = true;
                    break;
                }
                break;
            case 1316799103:
                if (str5.equals(Component.COMMISSION_BANNER_ADS_NETWORK_DEFAULT)) {
                    z2 = true;
                    break;
                }
                break;
            case 1560923121:
                if (str5.equals("leadbolt")) {
                    z2 = true;
                    break;
                }
                break;
        }
        switch (z2) {
            case false:
            case true:
                String str6 = str4;
                boolean z3 = true;
                switch (str6.hashCode()) {
                    case 604727084:
                        if (str6.equals("interstitial")) {
                            z3 = false;
                            break;
                        }
                        break;
                }
                switch (z3) {
                    case false:
                        f = f2 + 0.08f;
                        break;
                    default:
                        f = f2 + 0.15f;
                        break;
                }
            case true:
                String str7 = str4;
                boolean z4 = true;
                switch (str7.hashCode()) {
                    case -1396342996:
                        if (str7.equals("banner")) {
                            z4 = false;
                            break;
                        }
                        break;
                    case 112202875:
                        if (str7.equals("video")) {
                            z4 = true;
                            break;
                        }
                        break;
                    case 604727084:
                        if (str7.equals("interstitial")) {
                            z4 = true;
                            break;
                        }
                        break;
                }
                switch (z4) {
                    case false:
                        f = f2 + 0.08f;
                        break;
                    case true:
                        f = f2 + 0.11f;
                        break;
                    case true:
                        f = f2 + 0.14f;
                        break;
                    default:
                        f = f2 + 0.15f;
                        break;
                }
            case true:
                String str8 = str4;
                boolean z5 = true;
                switch (str8.hashCode()) {
                    case -1396342996:
                        if (str8.equals("banner")) {
                            z5 = false;
                            break;
                        }
                        break;
                    case 604727084:
                        if (str8.equals("interstitial")) {
                            z5 = true;
                            break;
                        }
                        break;
                }
                switch (z5) {
                    case false:
                        f = f2 + 0.06f;
                        break;
                    case true:
                        f = f2 + 0.09f;
                        break;
                    default:
                        f = f2 + 0.15f;
                        break;
                }
            case true:
                String str9 = str4;
                boolean z6 = true;
                switch (str9.hashCode()) {
                    case -1396342996:
                        if (str9.equals("banner")) {
                            z6 = false;
                            break;
                        }
                        break;
                    case 112202875:
                        if (str9.equals("video")) {
                            z6 = true;
                            break;
                        }
                        break;
                    case 604727084:
                        if (str9.equals("interstitial")) {
                            z6 = true;
                            break;
                        }
                        break;
                }
                switch (z6) {
                    case false:
                        f = f2 + 0.06f;
                        break;
                    case true:
                        f = f2 + 0.09f;
                        break;
                    case true:
                        f = f2 + 0.12f;
                        break;
                    default:
                        f = f2 + 0.15f;
                        break;
                }
            case true:
                String str10 = str4;
                boolean z7 = true;
                switch (str10.hashCode()) {
                    case 112202875:
                        if (str10.equals("video")) {
                            z7 = true;
                            break;
                        }
                        break;
                    case 604727084:
                        if (str10.equals("interstitial")) {
                            z7 = false;
                            break;
                        }
                        break;
                }
                switch (z7) {
                    case false:
                        f = f2 + 0.09f;
                        break;
                    case true:
                        f = f2 + 0.12f;
                        break;
                    default:
                        f = f2 + 0.15f;
                        break;
                }
            case true:
            case true:
                String str11 = str4;
                boolean z8 = true;
                switch (str11.hashCode()) {
                    case -1396342996:
                        if (str11.equals("banner")) {
                            z8 = false;
                            break;
                        }
                        break;
                    case 604727084:
                        if (str11.equals("interstitial")) {
                            z8 = true;
                            break;
                        }
                        break;
                }
                switch (z8) {
                    case false:
                        f = f2 + 0.05f;
                        break;
                    case true:
                        f = f2 + 0.08f;
                        break;
                    default:
                        f = f2 + 0.15f;
                        break;
                }
            default:
                int hashCode = str4.hashCode();
                f = f2 + 0.2f;
                break;
        }
        int i = Log.i(this.LOG_TAG, "Final Commission: ".concat(String.valueOf(f)));
        return f;
    }

    private String getKodularPackageName() {
        String packageName = this.context.getPackageName();
        try {
            PackageManager packageManager = this.context.getPackageManager();
            return packageManager.resolveActivity(packageManager.getLaunchIntentForPackage(packageName), 65536).activityInfo.name.replaceAll(".Screen1", "");
        } catch (Exception e) {
            return packageName;
        }
    }

    /* renamed from: com.google.appinventor.components.runtime.util.KodularAdsCommission$a */
    static class C1165a extends AsyncTask<Form, Void, String> {
        private String LOG_TAG;
        private Form hR11jdqaRrvBRiBFd4KN6gI7d8MNQVP5Yc7fufDZjGGTeTxaualejjrhiR1Iz2xO;

        private C1165a() {
            this.LOG_TAG = "KodularAdsCommission";
        }

        /* JADX INFO: this call moved to the top of the method (can break code semantics) */
        /* synthetic */ C1165a(byte b) {
            this();
            byte b2 = b;
        }

        /* access modifiers changed from: protected */
        public final /* synthetic */ void onPostExecute(Object obj) {
            StringBuilder sb;
            String str = (String) obj;
            SharedPreferences sharedPreferences = this.hR11jdqaRrvBRiBFd4KN6gI7d8MNQVP5Yc7fufDZjGGTeTxaualejjrhiR1Iz2xO.$context().getSharedPreferences(this.hR11jdqaRrvBRiBFd4KN6gI7d8MNQVP5Yc7fufDZjGGTeTxaualejjrhiR1Iz2xO.getKodularPackageName(), 0);
            if (str != null && sharedPreferences != null) {
                SharedPreferences.Editor edit = sharedPreferences.edit();
                new StringBuilder();
                edit.putString(sb.append(this.hR11jdqaRrvBRiBFd4KN6gI7d8MNQVP5Yc7fufDZjGGTeTxaualejjrhiR1Iz2xO.getKodularPackageName()).append(".kcv").toString(), str).apply();
            }
        }

        /* access modifiers changed from: private */
        /* renamed from: hxYOFxFjLpN1maJuWNxUV40nExCGxsxkDPOTgtzMu4zlZCQb3bPlKsXo1SYJg6ME */
        public String doInBackground(Form... formArr) {
            KeySpec keySpec;
            JwtConsumerBuilder jwtConsumerBuilder;
            String str;
            try {
                this.hR11jdqaRrvBRiBFd4KN6gI7d8MNQVP5Yc7fufDZjGGTeTxaualejjrhiR1Iz2xO = formArr[0];
                Bundle bundle = this.hR11jdqaRrvBRiBFd4KN6gI7d8MNQVP5Yc7fufDZjGGTeTxaualejjrhiR1Iz2xO.$context().getPackageManager().getApplicationInfo(this.hR11jdqaRrvBRiBFd4KN6gI7d8MNQVP5Yc7fufDZjGGTeTxaualejjrhiR1Iz2xO.getPackageName(), 128).metaData;
                Bundle bundle2 = bundle;
                if (bundle == null) {
                    int e = Log.e(this.LOG_TAG, "No bundle!");
                    return null;
                }
                String string = bundle2.getString("RCWTFYP-BYDNKHMWRIT-DNLATT_TK");
                String string2 = bundle2.getString("RCWTFYP-BYDNKHMWRIT-DNLATT_PK");
                if (string == null) {
                    int e2 = Log.e(this.LOG_TAG, "No TK!");
                    return null;
                } else if (string2 == null) {
                    int e3 = Log.e(this.LOG_TAG, "No PK!");
                    return null;
                } else {
                    byte[] decode = Base64.decode(string2, 0);
                    KeyFactory instance = KeyFactory.getInstance("RSA");
                    new X509EncodedKeySpec(decode);
                    PublicKey generatePublic = instance.generatePublic(keySpec);
                    new JwtConsumerBuilder();
                    String[] strArr = {AlgorithmIdentifiers.RSA_USING_SHA256};
                    String str2 = str;
                    new String(Base64.decode(jwtConsumerBuilder.setExpectedIssuer("Junnovate, LLC").setExpectedAudience("Kodular Creator [creator.kodular.io]").setExpectedSubject(this.hR11jdqaRrvBRiBFd4KN6gI7d8MNQVP5Yc7fufDZjGGTeTxaualejjrhiR1Iz2xO.getKodularPackageName()).setVerificationKey(generatePublic).setJwsAlgorithmConstraints(AlgorithmConstraints.ConstraintType.PERMIT, strArr).build().processToClaims(string).getClaimValueAsString("com"), 0), StandardCharsets.UTF_8);
                    return str2;
                }
            } catch (PackageManager.NameNotFoundException e4) {
                int e5 = Log.e(this.LOG_TAG, "Could not find PackageManager");
                return null;
            } catch (InvalidJwtException e6) {
                int e7 = Log.e(this.LOG_TAG, "Signature is not valid!");
                return "100";
            } catch (InvalidKeySpecException e8) {
                int e9 = Log.e(this.LOG_TAG, "Public key is not valid!");
                return "100";
            } catch (NoSuchAlgorithmException e10) {
                int e11 = Log.e(this.LOG_TAG, "Algorithm RSA is not valid");
                return null;
            }
        }
    }
}
