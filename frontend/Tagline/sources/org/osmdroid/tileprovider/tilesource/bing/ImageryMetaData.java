package org.osmdroid.tileprovider.tilesource.bing;

import org.json.JSONArray;
import org.json.JSONObject;

public class ImageryMetaData {
    private static final String AUTH_RESULT_CODE = "authenticationResultCode";
    private static final String AUTH_RESULT_CODE_VALID = "ValidCredentials";
    private static final String ESTIMATED_TOTAL = "estimatedTotal";
    private static final String RESOURCE = "resources";
    private static final String RESOURCE_SETS = "resourceSets";
    private static final String STATUS_CODE = "statusCode";

    public ImageryMetaData() {
    }

    public static ImageryMetaDataResource getInstanceFromJSON(String str) throws Exception {
        JSONObject jSONObject;
        Throwable th;
        Throwable th2;
        Throwable th3;
        StringBuilder sb;
        Throwable th4;
        StringBuilder sb2;
        Throwable th5;
        String a_jsonContent = str;
        if (a_jsonContent == null) {
            Throwable th6 = th5;
            new Exception("JSON to parse is null");
            throw th6;
        }
        new JSONObject(a_jsonContent);
        JSONObject jsonResult = jSONObject;
        int statusCode = jsonResult.getInt(STATUS_CODE);
        if (statusCode != 200) {
            Throwable th7 = th4;
            new StringBuilder();
            new Exception(sb2.append("Status code = ").append(statusCode).toString());
            throw th7;
        } else if (AUTH_RESULT_CODE_VALID.compareToIgnoreCase(jsonResult.getString(AUTH_RESULT_CODE)) != 0) {
            Throwable th8 = th3;
            new StringBuilder();
            new Exception(sb.append("authentication result code = ").append(jsonResult.getString(AUTH_RESULT_CODE)).toString());
            throw th8;
        } else {
            JSONArray resultsSet = jsonResult.getJSONArray(RESOURCE_SETS);
            if (resultsSet == null || resultsSet.length() < 1) {
                Throwable th9 = th;
                new Exception("No results set found in json response");
                throw th9;
            } else if (resultsSet.getJSONObject(0).getInt(ESTIMATED_TOTAL) > 0) {
                return ImageryMetaDataResource.getInstanceFromJSON(resultsSet.getJSONObject(0).getJSONArray(RESOURCE).getJSONObject(0), jsonResult);
            } else {
                Throwable th10 = th2;
                new Exception("No resource found in json response");
                throw th10;
            }
        }
    }
}
