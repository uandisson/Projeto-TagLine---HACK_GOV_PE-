package com.google.appinventor.components.runtime;

import android.util.Log;
import com.google.appinventor.components.annotations.DesignerComponent;
import com.google.appinventor.components.annotations.DesignerProperty;
import com.google.appinventor.components.annotations.PropertyCategory;
import com.google.appinventor.components.annotations.SimpleEvent;
import com.google.appinventor.components.annotations.SimpleFunction;
import com.google.appinventor.components.annotations.SimpleObject;
import com.google.appinventor.components.annotations.SimpleProperty;
import com.google.appinventor.components.annotations.UsesLibraries;
import com.google.appinventor.components.annotations.UsesPermissions;
import com.google.appinventor.components.common.ComponentCategory;
import com.google.appinventor.components.runtime.util.AsynchUtil;
import com.google.appinventor.components.runtime.util.ErrorMessages;
import com.google.appinventor.components.runtime.util.GeoJSONUtil;
import com.google.appinventor.components.runtime.util.GeometryUtil;
import com.google.appinventor.components.runtime.util.JsonUtil;
import com.google.appinventor.components.runtime.util.MapFactory;
import com.google.appinventor.components.runtime.util.YailDictionary;
import com.google.appinventor.components.runtime.util.YailList;
import com.microsoft.appcenter.Constants;
import com.microsoft.appcenter.http.DefaultHttpClient;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import org.json.JSONException;
import org.locationtech.jts.p006io.gml2.GMLConstants;
import org.osmdroid.util.GeoPoint;

@DesignerComponent(category = ComponentCategory.MAPS, description = "Navigation", iconName = "images/navigation.png", nonVisible = true, version = 1)
@UsesLibraries({"osmdroid.jar"})
@SimpleObject
@UsesPermissions(permissionNames = "android.permission.INTERNET")
public class Navigation extends AndroidNonvisibleComponent implements Component {
    public static final String OPEN_ROUTE_SERVICE_URL = "https://api.openrouteservice.org/v2/directions/";
    private String TVKenNjujur1C1Ft9Gj8dhchvJBwuJV9GDuQOmGg2gZVCkxzGoaa0a88A5IZ9COq = "en";
    private GeoPoint hR11jdqaRrvBRiBFd4KN6gI7d8MNQVP5Yc7fufDZjGGTeTxaualejjrhiR1Iz2xO;
    private C0936a hxYOFxFjLpN1maJuWNxUV40nExCGxsxkDPOTgtzMu4zlZCQb3bPlKsXo1SYJg6ME;

    /* renamed from: hxYOFxFjLpN1maJuWNxUV40nExCGxsxkDPOTgtzMu4zlZCQb3bPlKsXo1SYJg6ME  reason: collision with other field name */
    private YailDictionary f721hxYOFxFjLpN1maJuWNxUV40nExCGxsxkDPOTgtzMu4zlZCQb3bPlKsXo1SYJg6ME = YailDictionary.makeDictionary();
    private String rtyU3Uj4Fd2cS2DWhNVfozs9qaFOsy3YcN33Msvg0fbnB6MZpRvgk3PrzB8p4A = "";
    private GeoPoint vSp02fkBXgM8EI0gm0rKWXHQ6wdQINJBQuAtCR15YU8g4XNqVKV8r32SYxkQYxkq;
    private String wfbsnc19ruRPyBpriU11i0zXW81wrBgGRVM2BOD65kRILLKDr3mBxnYSQKLd5kkO = OPEN_ROUTE_SERVICE_URL;

    static /* synthetic */ YailDictionary hxYOFxFjLpN1maJuWNxUV40nExCGxsxkDPOTgtzMu4zlZCQb3bPlKsXo1SYJg6ME(Navigation navigation, YailDictionary yailDictionary) {
        YailDictionary yailDictionary2 = yailDictionary;
        YailDictionary yailDictionary3 = yailDictionary2;
        navigation.f721hxYOFxFjLpN1maJuWNxUV40nExCGxsxkDPOTgtzMu4zlZCQb3bPlKsXo1SYJg6ME = yailDictionary3;
        return yailDictionary2;
    }

    /* JADX INFO: finally extract failed */
    static /* synthetic */ void hxYOFxFjLpN1maJuWNxUV40nExCGxsxkDPOTgtzMu4zlZCQb3bPlKsXo1SYJg6ME(Navigation navigation, GeoPoint geoPoint, GeoPoint geoPoint2, C0936a aVar) throws IOException, JSONException {
        StringBuilder sb;
        URL url;
        StringBuilder sb2;
        BufferedOutputStream bufferedOutputStream;
        BufferedOutputStream bufferedOutputStream2;
        Runnable runnable;
        GeoPoint geoPoint3 = geoPoint2;
        GeoPoint geoPoint4 = geoPoint;
        Navigation navigation2 = navigation;
        new StringBuilder();
        new URL(sb.append(navigation2.wfbsnc19ruRPyBpriU11i0zXW81wrBgGRVM2BOD65kRILLKDr3mBxnYSQKLd5kkO).append(aVar.symWhrqAyHWXMObHLoQEIMlJvqdZFvcp7UyC2VmDxP3CgSs0pkdkxz6qiaDBzrEK).append("/geojson/").toString());
        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
        HttpURLConnection httpURLConnection2 = httpURLConnection;
        httpURLConnection.setDoInput(true);
        httpURLConnection2.setDoOutput(true);
        httpURLConnection2.setRequestProperty(DefaultHttpClient.CONTENT_TYPE_KEY, "application/json; charset=UTF-8");
        httpURLConnection2.setRequestMethod(DefaultHttpClient.METHOD_POST);
        httpURLConnection2.setRequestProperty(Constants.AUTHORIZATION_HEADER, navigation2.rtyU3Uj4Fd2cS2DWhNVfozs9qaFOsy3YcN33Msvg0fbnB6MZpRvgk3PrzB8p4A);
        try {
            StringBuilder sb3 = sb2;
            new StringBuilder("{\"coordinates\": ");
            GeoPoint geoPoint5 = geoPoint4;
            GeoPoint geoPoint6 = geoPoint3;
            GeoPoint geoPoint7 = geoPoint5;
            Double[][] dArr = (Double[][]) Array.newInstance(Double.class, new int[]{2, 2});
            Double[][] dArr2 = dArr;
            dArr[0][0] = Double.valueOf(geoPoint7.getLongitude());
            dArr2[0][1] = Double.valueOf(geoPoint7.getLatitude());
            dArr2[1][0] = Double.valueOf(geoPoint6.getLongitude());
            dArr2[1][1] = Double.valueOf(geoPoint6.getLatitude());
            byte[] bytes = sb3.append(JsonUtil.getJsonRepresentation(dArr2)).append(", \"language\": \"").append(navigation2.TVKenNjujur1C1Ft9Gj8dhchvJBwuJV9GDuQOmGg2gZVCkxzGoaa0a88A5IZ9COq).append("\"}").toString().getBytes("UTF-8");
            httpURLConnection2.setFixedLengthStreamingMode(bytes.length);
            BufferedOutputStream bufferedOutputStream3 = bufferedOutputStream;
            new BufferedOutputStream(httpURLConnection2.getOutputStream());
            bufferedOutputStream2 = bufferedOutputStream3;
            bufferedOutputStream2.write(bytes, 0, bytes.length);
            bufferedOutputStream2.flush();
            bufferedOutputStream2.close();
            if (httpURLConnection2.getResponseCode() != 200) {
                Object[] objArr = new Object[2];
                objArr[0] = Integer.valueOf(httpURLConnection2.getResponseCode());
                Object[] objArr2 = objArr;
                objArr2[1] = httpURLConnection2.getResponseMessage();
                navigation2.form.dispatchErrorOccurredEvent(navigation2, "RequestDirections", ErrorMessages.ERROR_ROUTING_SERVICE_ERROR, objArr2);
                httpURLConnection2.disconnect();
                return;
            }
            String responseContent = getResponseContent(httpURLConnection2);
            int d = Log.d("Navigation", responseContent);
            YailDictionary yailDictionary = (YailDictionary) JsonUtil.getObjectFromJson(responseContent, true);
            YailDictionary yailDictionary2 = yailDictionary;
            YailList yailList = (YailList) yailDictionary.get("features");
            YailList yailList2 = yailList;
            if (yailList.size() > 0) {
                YailDictionary yailDictionary3 = (YailDictionary) yailList2.getObject(0);
                YailDictionary yailDictionary4 = yailDictionary3;
                YailDictionary yailDictionary5 = yailDictionary3;
                String[] strArr = new String[2];
                strArr[0] = "properties";
                String[] strArr2 = strArr;
                strArr2[1] = "summary";
                YailDictionary yailDictionary6 = (YailDictionary) yailDictionary4.getObjectAtKeyPath(Arrays.asList(strArr2));
                double doubleValue = ((Double) yailDictionary6.get("distance")).doubleValue();
                double doubleValue2 = ((Double) yailDictionary6.get("duration")).doubleValue();
                Object[] objArr3 = new Object[6];
                objArr3[0] = "properties";
                Object[] objArr4 = objArr3;
                objArr4[1] = "segments";
                Object[] objArr5 = objArr4;
                objArr5[2] = YailDictionary.ALL;
                Object[] objArr6 = objArr5;
                objArr6[3] = "steps";
                Object[] objArr7 = objArr6;
                objArr7[4] = YailDictionary.ALL;
                Object[] objArr8 = objArr7;
                objArr8[5] = "instruction";
                YailList makeList = YailList.makeList((List) YailDictionary.walkKeyPath(yailDictionary5, Arrays.asList(objArr8)));
                String[] strArr3 = new String[2];
                strArr3[0] = "geometry";
                String[] strArr4 = strArr3;
                strArr4[1] = GMLConstants.GML_COORDINATES;
                final YailDictionary yailDictionary7 = yailDictionary2;
                final YailList yailList3 = makeList;
                final YailList swapCoordinates = GeoJSONUtil.swapCoordinates((YailList) yailDictionary5.getObjectAtKeyPath(Arrays.asList(strArr4)));
                final double d2 = doubleValue;
                final double d3 = doubleValue2;
                new Runnable(navigation2) {
                    private /* synthetic */ Navigation hxYOFxFjLpN1maJuWNxUV40nExCGxsxkDPOTgtzMu4zlZCQb3bPlKsXo1SYJg6ME;

                    {
                        this.hxYOFxFjLpN1maJuWNxUV40nExCGxsxkDPOTgtzMu4zlZCQb3bPlKsXo1SYJg6ME = r14;
                    }

                    public final void run() {
                        YailDictionary hxYOFxFjLpN1maJuWNxUV40nExCGxsxkDPOTgtzMu4zlZCQb3bPlKsXo1SYJg6ME2 = Navigation.hxYOFxFjLpN1maJuWNxUV40nExCGxsxkDPOTgtzMu4zlZCQb3bPlKsXo1SYJg6ME(this.hxYOFxFjLpN1maJuWNxUV40nExCGxsxkDPOTgtzMu4zlZCQb3bPlKsXo1SYJg6ME, yailDictionary7);
                        this.hxYOFxFjLpN1maJuWNxUV40nExCGxsxkDPOTgtzMu4zlZCQb3bPlKsXo1SYJg6ME.GotDirections(yailList3, swapCoordinates, d2, d3);
                    }
                };
                navigation2.form.runOnUiThread(runnable);
            } else {
                navigation2.form.dispatchErrorOccurredEvent(navigation2, "RequestDirections", ErrorMessages.ERROR_NO_ROUTE_FOUND, new Object[0]);
            }
            httpURLConnection2.disconnect();
        } catch (Exception e) {
            Exception exc = e;
            try {
                navigation2.form.dispatchErrorOccurredEvent(navigation2, "RequestDirections", ErrorMessages.ERROR_UNABLE_TO_REQUEST_DIRECTIONS, exc.getMessage());
                exc.printStackTrace();
                httpURLConnection2.disconnect();
            } catch (Throwable th) {
                Throwable th2 = th;
                httpURLConnection2.disconnect();
                throw th2;
            }
        } catch (Throwable th3) {
            Throwable th4 = th3;
            bufferedOutputStream2.close();
            throw th4;
        }
    }

    /* renamed from: com.google.appinventor.components.runtime.Navigation$a */
    enum C0936a {
        ;
        
        /* access modifiers changed from: private */
        public final String symWhrqAyHWXMObHLoQEIMlJvqdZFvcp7UyC2VmDxP3CgSs0pkdkxz6qiaDBzrEK;

        private C0936a(String str) {
            String str2 = r8;
            int i = r9;
            this.symWhrqAyHWXMObHLoQEIMlJvqdZFvcp7UyC2VmDxP3CgSs0pkdkxz6qiaDBzrEK = str;
        }
    }

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public Navigation(ComponentContainer componentContainer) {
        super(componentContainer.$form());
        GeoPoint geoPoint;
        GeoPoint geoPoint2;
        new GeoPoint(0.0d, 0.0d);
        this.vSp02fkBXgM8EI0gm0rKWXHQ6wdQINJBQuAtCR15YU8g4XNqVKV8r32SYxkQYxkq = geoPoint;
        new GeoPoint(0.0d, 0.0d);
        this.hR11jdqaRrvBRiBFd4KN6gI7d8MNQVP5Yc7fufDZjGGTeTxaualejjrhiR1Iz2xO = geoPoint2;
        this.hxYOFxFjLpN1maJuWNxUV40nExCGxsxkDPOTgtzMu4zlZCQb3bPlKsXo1SYJg6ME = C0936a.wq07duYRO6iFAgWM70EZOSvbCMKs1QznMRJKrct0XuHOBYqCk3XqOKtSBGIpDou;
    }

    @SimpleFunction(description = "Request directions from the routing service.")
    public void RequestDirections() {
        Runnable runnable;
        if (this.rtyU3Uj4Fd2cS2DWhNVfozs9qaFOsy3YcN33Msvg0fbnB6MZpRvgk3PrzB8p4A.equals("")) {
            this.form.dispatchErrorOccurredEvent(this, Constants.AUTHORIZATION_HEADER, ErrorMessages.ERROR_INVALID_API_KEY, new Object[0]);
            return;
        }
        GeoPoint geoPoint = this.vSp02fkBXgM8EI0gm0rKWXHQ6wdQINJBQuAtCR15YU8g4XNqVKV8r32SYxkQYxkq;
        GeoPoint geoPoint2 = this.hR11jdqaRrvBRiBFd4KN6gI7d8MNQVP5Yc7fufDZjGGTeTxaualejjrhiR1Iz2xO;
        final GeoPoint geoPoint3 = geoPoint;
        final GeoPoint geoPoint4 = geoPoint2;
        final C0936a aVar = this.hxYOFxFjLpN1maJuWNxUV40nExCGxsxkDPOTgtzMu4zlZCQb3bPlKsXo1SYJg6ME;
        new Runnable(this) {
            private /* synthetic */ Navigation hxYOFxFjLpN1maJuWNxUV40nExCGxsxkDPOTgtzMu4zlZCQb3bPlKsXo1SYJg6ME;

            {
                this.hxYOFxFjLpN1maJuWNxUV40nExCGxsxkDPOTgtzMu4zlZCQb3bPlKsXo1SYJg6ME = r8;
            }

            public final void run() {
                try {
                    Navigation.hxYOFxFjLpN1maJuWNxUV40nExCGxsxkDPOTgtzMu4zlZCQb3bPlKsXo1SYJg6ME(this.hxYOFxFjLpN1maJuWNxUV40nExCGxsxkDPOTgtzMu4zlZCQb3bPlKsXo1SYJg6ME, geoPoint3, geoPoint4, aVar);
                } catch (IOException e) {
                    this.hxYOFxFjLpN1maJuWNxUV40nExCGxsxkDPOTgtzMu4zlZCQb3bPlKsXo1SYJg6ME.form.dispatchErrorOccurredEvent(this.hxYOFxFjLpN1maJuWNxUV40nExCGxsxkDPOTgtzMu4zlZCQb3bPlKsXo1SYJg6ME, "RequestDirections", 0, new Object[0]);
                } catch (JSONException e2) {
                    this.hxYOFxFjLpN1maJuWNxUV40nExCGxsxkDPOTgtzMu4zlZCQb3bPlKsXo1SYJg6ME.form.dispatchErrorOccurredEvent(this.hxYOFxFjLpN1maJuWNxUV40nExCGxsxkDPOTgtzMu4zlZCQb3bPlKsXo1SYJg6ME, "RequestDirections", 0, new Object[0]);
                }
            }
        };
        AsynchUtil.runAsynchronously(runnable);
    }

    @SimpleProperty(userVisible = false)
    public void ServiceURL(String str) {
        String str2 = str;
        this.wfbsnc19ruRPyBpriU11i0zXW81wrBgGRVM2BOD65kRILLKDr3mBxnYSQKLd5kkO = str2;
    }

    @DesignerProperty(editorType = "string")
    @SimpleProperty(description = "API Key for Open Route Service.")
    public void ApiKey(String str) {
        String str2 = str;
        this.rtyU3Uj4Fd2cS2DWhNVfozs9qaFOsy3YcN33Msvg0fbnB6MZpRvgk3PrzB8p4A = str2;
    }

    @DesignerProperty(defaultValue = "0.0", editorType = "latitude")
    @SimpleProperty
    public void StartLatitude(double d) {
        double d2 = d;
        if (GeometryUtil.isValidLatitude(d2)) {
            this.vSp02fkBXgM8EI0gm0rKWXHQ6wdQINJBQuAtCR15YU8g4XNqVKV8r32SYxkQYxkq.setLatitude(d2);
            return;
        }
        getDispatchDelegate().dispatchErrorOccurredEvent(this, "StartLatitude", ErrorMessages.ERROR_INVALID_LATITUDE, Double.valueOf(d2));
    }

    @SimpleProperty(category = PropertyCategory.BEHAVIOR, description = "The latitude of the start location.")
    public double StartLatitude() {
        return this.vSp02fkBXgM8EI0gm0rKWXHQ6wdQINJBQuAtCR15YU8g4XNqVKV8r32SYxkQYxkq.getLatitude();
    }

    @DesignerProperty(defaultValue = "0.0", editorType = "longitude")
    @SimpleProperty
    public void StartLongitude(double d) {
        double d2 = d;
        if (GeometryUtil.isValidLongitude(d2)) {
            this.vSp02fkBXgM8EI0gm0rKWXHQ6wdQINJBQuAtCR15YU8g4XNqVKV8r32SYxkQYxkq.setLongitude(d2);
            return;
        }
        getDispatchDelegate().dispatchErrorOccurredEvent(this, "StartLongitude", ErrorMessages.ERROR_INVALID_LONGITUDE, Double.valueOf(d2));
    }

    @SimpleProperty(category = PropertyCategory.BEHAVIOR, description = "The longitude of the start location.")
    public double StartLongitude() {
        return this.vSp02fkBXgM8EI0gm0rKWXHQ6wdQINJBQuAtCR15YU8g4XNqVKV8r32SYxkQYxkq.getLongitude();
    }

    @SimpleProperty(description = "Set the start location.")
    public void StartLocation(MapFactory.MapFeature mapFeature) {
        GeoPoint centroid = mapFeature.getCentroid();
        double latitude = centroid.getLatitude();
        double longitude = centroid.getLongitude();
        if (!GeometryUtil.isValidLatitude(latitude)) {
            getDispatchDelegate().dispatchErrorOccurredEvent(this, "SetStartLocation", ErrorMessages.ERROR_INVALID_LATITUDE, Double.valueOf(latitude));
        } else if (!GeometryUtil.isValidLongitude(longitude)) {
            getDispatchDelegate().dispatchErrorOccurredEvent(this, "SetStartLocation", ErrorMessages.ERROR_INVALID_LONGITUDE, Double.valueOf(longitude));
        } else {
            this.vSp02fkBXgM8EI0gm0rKWXHQ6wdQINJBQuAtCR15YU8g4XNqVKV8r32SYxkQYxkq.setCoords(latitude, longitude);
        }
    }

    @DesignerProperty(defaultValue = "0.0", editorType = "latitude")
    @SimpleProperty
    public void EndLatitude(double d) {
        double d2 = d;
        if (GeometryUtil.isValidLatitude(d2)) {
            this.hR11jdqaRrvBRiBFd4KN6gI7d8MNQVP5Yc7fufDZjGGTeTxaualejjrhiR1Iz2xO.setLatitude(d2);
            return;
        }
        getDispatchDelegate().dispatchErrorOccurredEvent(this, "EndLatitude", ErrorMessages.ERROR_INVALID_LATITUDE, Double.valueOf(d2));
    }

    @SimpleProperty(category = PropertyCategory.BEHAVIOR, description = "The latitude of the end location.")
    public double EndLatitude() {
        return this.hR11jdqaRrvBRiBFd4KN6gI7d8MNQVP5Yc7fufDZjGGTeTxaualejjrhiR1Iz2xO.getLatitude();
    }

    @DesignerProperty(defaultValue = "0.0", editorType = "longitude")
    @SimpleProperty
    public void EndLongitude(double d) {
        double d2 = d;
        if (GeometryUtil.isValidLongitude(d2)) {
            this.hR11jdqaRrvBRiBFd4KN6gI7d8MNQVP5Yc7fufDZjGGTeTxaualejjrhiR1Iz2xO.setLongitude(d2);
            return;
        }
        getDispatchDelegate().dispatchErrorOccurredEvent(this, "EndLongitude", ErrorMessages.ERROR_INVALID_LONGITUDE, Double.valueOf(d2));
    }

    @SimpleProperty(category = PropertyCategory.BEHAVIOR, description = "The longitude of the end location.")
    public double EndLongitude() {
        return this.hR11jdqaRrvBRiBFd4KN6gI7d8MNQVP5Yc7fufDZjGGTeTxaualejjrhiR1Iz2xO.getLongitude();
    }

    @SimpleProperty(category = PropertyCategory.BEHAVIOR)
    public String TransportationMethod() {
        return this.hxYOFxFjLpN1maJuWNxUV40nExCGxsxkDPOTgtzMu4zlZCQb3bPlKsXo1SYJg6ME.symWhrqAyHWXMObHLoQEIMlJvqdZFvcp7UyC2VmDxP3CgSs0pkdkxz6qiaDBzrEK;
    }

    @DesignerProperty(defaultValue = "foot-walking", editorType = "navigation_method")
    @SimpleProperty(description = "The transportation method used for determining the route.")
    public void TransportationMethod(String str) {
        String str2 = str;
        C0936a[] values = C0936a.values();
        C0936a[] aVarArr = values;
        int length = values.length;
        for (int i = 0; i < length; i++) {
            C0936a aVar = aVarArr[i];
            if (str2.equals(aVar.symWhrqAyHWXMObHLoQEIMlJvqdZFvcp7UyC2VmDxP3CgSs0pkdkxz6qiaDBzrEK)) {
                this.hxYOFxFjLpN1maJuWNxUV40nExCGxsxkDPOTgtzMu4zlZCQb3bPlKsXo1SYJg6ME = aVar;
            }
        }
    }

    @SimpleProperty(description = "Set the end location.")
    public void EndLocation(MapFactory.MapFeature mapFeature) {
        GeoPoint centroid = mapFeature.getCentroid();
        double latitude = centroid.getLatitude();
        double longitude = centroid.getLongitude();
        if (!GeometryUtil.isValidLatitude(latitude)) {
            getDispatchDelegate().dispatchErrorOccurredEvent(this, "SetEndLocation", ErrorMessages.ERROR_INVALID_LATITUDE, Double.valueOf(latitude));
        } else if (!GeometryUtil.isValidLongitude(longitude)) {
            getDispatchDelegate().dispatchErrorOccurredEvent(this, "SetEndLocation", ErrorMessages.ERROR_INVALID_LONGITUDE, Double.valueOf(longitude));
        } else {
            this.hR11jdqaRrvBRiBFd4KN6gI7d8MNQVP5Yc7fufDZjGGTeTxaualejjrhiR1Iz2xO.setCoords(latitude, longitude);
        }
    }

    @DesignerProperty(defaultValue = "en")
    @SimpleProperty(description = "The language to use for textual directions.")
    public void Language(String str) {
        String str2 = str;
        this.TVKenNjujur1C1Ft9Gj8dhchvJBwuJV9GDuQOmGg2gZVCkxzGoaa0a88A5IZ9COq = str2;
    }

    @SimpleProperty
    public String Language() {
        return this.TVKenNjujur1C1Ft9Gj8dhchvJBwuJV9GDuQOmGg2gZVCkxzGoaa0a88A5IZ9COq;
    }

    @SimpleProperty(description = "Content of the last response as a dictionary.")
    public YailDictionary ResponseContent() {
        return this.f721hxYOFxFjLpN1maJuWNxUV40nExCGxsxkDPOTgtzMu4zlZCQb3bPlKsXo1SYJg6ME;
    }

    @SimpleEvent(description = "Event triggered when the Openrouteservice returns the directions.")
    public void GotDirections(YailList yailList, YailList yailList2, double d, double d2) {
        int d3 = Log.d("Navigation", "GotDirections");
        Object[] objArr = new Object[4];
        objArr[0] = yailList;
        Object[] objArr2 = objArr;
        objArr2[1] = yailList2;
        Object[] objArr3 = objArr2;
        objArr3[2] = Double.valueOf(d);
        Object[] objArr4 = objArr3;
        objArr4[3] = Double.valueOf(d2);
        boolean dispatchEvent = EventDispatcher.dispatchEvent(this, "GotDirections", objArr4);
    }

    /* JADX INFO: finally extract failed */
    private static String getResponseContent(HttpURLConnection httpURLConnection) throws IOException {
        InputStreamReader inputStreamReader;
        StringBuilder sb;
        StringBuilder sb2;
        StringBuilder sb3;
        HttpURLConnection httpURLConnection2 = httpURLConnection;
        String contentEncoding = httpURLConnection2.getContentEncoding();
        String str = contentEncoding;
        if (contentEncoding == null) {
            str = "UTF-8";
        }
        int d = Log.d("Navigation", Integer.toString(httpURLConnection2.getResponseCode()));
        new InputStreamReader(httpURLConnection2.getInputStream(), str);
        InputStreamReader inputStreamReader2 = inputStreamReader;
        try {
            int contentLength = httpURLConnection2.getContentLength();
            int i = contentLength;
            if (contentLength != -1) {
                sb2 = sb3;
                new StringBuilder(i);
            } else {
                sb2 = sb;
                new StringBuilder();
            }
            StringBuilder sb4 = sb2;
            char[] cArr = new char[1024];
            while (true) {
                int read = inputStreamReader2.read(cArr);
                int i2 = read;
                if (read != -1) {
                    StringBuilder append = sb4.append(cArr, 0, i2);
                } else {
                    String sb5 = sb4.toString();
                    inputStreamReader2.close();
                    return sb5;
                }
            }
        } catch (Throwable th) {
            Throwable th2 = th;
            inputStreamReader2.close();
            throw th2;
        }
    }
}
