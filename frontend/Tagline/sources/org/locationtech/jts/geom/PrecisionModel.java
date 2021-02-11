package org.locationtech.jts.geom;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class PrecisionModel implements Serializable, Comparable {
    public static final Type FIXED;
    public static final Type FLOATING;
    public static final Type FLOATING_SINGLE;
    public static final double maximumPreciseValue = 9.007199254740992E15d;
    private static final long serialVersionUID = 7777263578777803835L;
    private Type modelType;
    private double scale;

    public static PrecisionModel mostPrecise(PrecisionModel precisionModel, PrecisionModel precisionModel2) {
        PrecisionModel pm1 = precisionModel;
        PrecisionModel pm2 = precisionModel2;
        if (pm1.compareTo(pm2) >= 0) {
            return pm1;
        }
        return pm2;
    }

    public static class Type implements Serializable {
        private static Map nameToTypeMap = null;
        private static final long serialVersionUID = -5528602631731589822L;
        private String name;

        static {
            Map map;
            new HashMap();
            nameToTypeMap = map;
        }

        public Type(String str) {
            String name2 = str;
            this.name = name2;
            Object put = nameToTypeMap.put(name2, this);
        }

        public String toString() {
            return this.name;
        }

        private Object readResolve() {
            return nameToTypeMap.get(this.name);
        }
    }

    static {
        Type type;
        Type type2;
        Type type3;
        new Type("FIXED");
        FIXED = type;
        new Type("FLOATING");
        FLOATING = type2;
        new Type("FLOATING SINGLE");
        FLOATING_SINGLE = type3;
    }

    public PrecisionModel() {
        this.modelType = FLOATING;
    }

    public PrecisionModel(Type type) {
        Type modelType2 = type;
        this.modelType = modelType2;
        if (modelType2 == FIXED) {
            setScale(1.0d);
        }
    }

    public PrecisionModel(double scale2, double d, double d2) {
        double d3 = d;
        double d4 = d2;
        this.modelType = FIXED;
        setScale(scale2);
    }

    public PrecisionModel(double scale2) {
        this.modelType = FIXED;
        setScale(scale2);
    }

    public PrecisionModel(PrecisionModel precisionModel) {
        PrecisionModel pm = precisionModel;
        this.modelType = pm.modelType;
        this.scale = pm.scale;
    }

    public boolean isFloating() {
        return this.modelType == FLOATING || this.modelType == FLOATING_SINGLE;
    }

    public int getMaximumSignificantDigits() {
        int maxSigDigits = 16;
        if (this.modelType == FLOATING) {
            maxSigDigits = 16;
        } else if (this.modelType == FLOATING_SINGLE) {
            maxSigDigits = 6;
        } else if (this.modelType == FIXED) {
            maxSigDigits = 1 + ((int) Math.ceil(Math.log(getScale()) / Math.log(10.0d)));
        }
        return maxSigDigits;
    }

    public double getScale() {
        return this.scale;
    }

    public Type getType() {
        return this.modelType;
    }

    private void setScale(double scale2) {
        double abs = Math.abs(scale2);
        this.scale = abs;
    }

    public double getOffsetX() {
        return 0.0d;
    }

    public double getOffsetY() {
        return 0.0d;
    }

    public void toInternal(Coordinate coordinate, Coordinate coordinate2) {
        Coordinate external = coordinate;
        Coordinate internal = coordinate2;
        if (isFloating()) {
            internal.f412x = external.f412x;
            internal.f413y = external.f413y;
        } else {
            internal.f412x = makePrecise(external.f412x);
            internal.f413y = makePrecise(external.f413y);
        }
        internal.f414z = external.f414z;
    }

    public Coordinate toInternal(Coordinate external) {
        Coordinate coordinate;
        new Coordinate(external);
        Coordinate internal = coordinate;
        makePrecise(internal);
        return internal;
    }

    public Coordinate toExternal(Coordinate internal) {
        Coordinate external;
        new Coordinate(internal);
        return external;
    }

    public void toExternal(Coordinate coordinate, Coordinate coordinate2) {
        Coordinate internal = coordinate;
        Coordinate external = coordinate2;
        external.f412x = internal.f412x;
        external.f413y = internal.f413y;
    }

    public double makePrecise(double d) {
        double val = d;
        if (Double.isNaN(val)) {
            return val;
        }
        if (this.modelType == FLOATING_SINGLE) {
            return (double) ((float) val);
        }
        if (this.modelType == FIXED) {
            return ((double) Math.round(val * this.scale)) / this.scale;
        }
        return val;
    }

    public void makePrecise(Coordinate coordinate) {
        Coordinate coord = coordinate;
        if (this.modelType != FLOATING) {
            coord.f412x = makePrecise(coord.f412x);
            coord.f413y = makePrecise(coord.f413y);
        }
    }

    public String toString() {
        StringBuilder sb;
        String description = "UNKNOWN";
        if (this.modelType == FLOATING) {
            description = "Floating";
        } else if (this.modelType == FLOATING_SINGLE) {
            description = "Floating-Single";
        } else if (this.modelType == FIXED) {
            new StringBuilder();
            description = sb.append("Fixed (Scale=").append(getScale()).append(")").toString();
        }
        return description;
    }

    public boolean equals(Object obj) {
        Object other = obj;
        if (!(other instanceof PrecisionModel)) {
            return false;
        }
        PrecisionModel otherPrecisionModel = (PrecisionModel) other;
        return this.modelType == otherPrecisionModel.modelType && this.scale == otherPrecisionModel.scale;
    }

    public int compareTo(Object o) {
        Integer num;
        Integer num2;
        int sigDigits = getMaximumSignificantDigits();
        int otherSigDigits = ((PrecisionModel) o).getMaximumSignificantDigits();
        new Integer(sigDigits);
        new Integer(otherSigDigits);
        return num.compareTo(num2);
    }
}
