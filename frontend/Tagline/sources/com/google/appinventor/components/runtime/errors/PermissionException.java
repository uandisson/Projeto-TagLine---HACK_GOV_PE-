package com.google.appinventor.components.runtime.errors;

public class PermissionException extends RuntimeException {
    private final String Sh2mKYKwuehs0F1mmv0TRntZIP9rdF7C9GwSzNCd1X4nGlljhrD3h4lWlup0CGLh;

    public PermissionException(String str) {
        this.Sh2mKYKwuehs0F1mmv0TRntZIP9rdF7C9GwSzNCd1X4nGlljhrD3h4lWlup0CGLh = str;
    }

    public String getPermissionNeeded() {
        return this.Sh2mKYKwuehs0F1mmv0TRntZIP9rdF7C9GwSzNCd1X4nGlljhrD3h4lWlup0CGLh;
    }

    public String getMessage() {
        StringBuilder sb;
        new StringBuilder("Unable to complete the operation because the user denied permission: ");
        return sb.append(this.Sh2mKYKwuehs0F1mmv0TRntZIP9rdF7C9GwSzNCd1X4nGlljhrD3h4lWlup0CGLh).toString();
    }
}
