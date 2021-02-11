package com.caverock.androidsvg;

public class PreserveAspectRatio {
    public static final PreserveAspectRatio BOTTOM;
    public static final PreserveAspectRatio END;
    public static final PreserveAspectRatio FULLSCREEN;
    public static final PreserveAspectRatio FULLSCREEN_START;
    public static final PreserveAspectRatio LETTERBOX;
    public static final PreserveAspectRatio START;
    public static final PreserveAspectRatio STRETCH;
    public static final PreserveAspectRatio TOP;
    public static final PreserveAspectRatio UNSCALED;
    private Alignment alignment;
    private Scale scale;

    public enum Alignment {
    }

    public enum Scale {
    }

    static {
        PreserveAspectRatio preserveAspectRatio;
        PreserveAspectRatio preserveAspectRatio2;
        PreserveAspectRatio preserveAspectRatio3;
        PreserveAspectRatio preserveAspectRatio4;
        PreserveAspectRatio preserveAspectRatio5;
        PreserveAspectRatio preserveAspectRatio6;
        PreserveAspectRatio preserveAspectRatio7;
        PreserveAspectRatio preserveAspectRatio8;
        PreserveAspectRatio preserveAspectRatio9;
        new PreserveAspectRatio((Alignment) null, (Scale) null);
        UNSCALED = preserveAspectRatio;
        new PreserveAspectRatio(Alignment.None, (Scale) null);
        STRETCH = preserveAspectRatio2;
        new PreserveAspectRatio(Alignment.XMidYMid, Scale.Meet);
        LETTERBOX = preserveAspectRatio3;
        new PreserveAspectRatio(Alignment.XMinYMin, Scale.Meet);
        START = preserveAspectRatio4;
        new PreserveAspectRatio(Alignment.XMaxYMax, Scale.Meet);
        END = preserveAspectRatio5;
        new PreserveAspectRatio(Alignment.XMidYMin, Scale.Meet);
        TOP = preserveAspectRatio6;
        new PreserveAspectRatio(Alignment.XMidYMax, Scale.Meet);
        BOTTOM = preserveAspectRatio7;
        new PreserveAspectRatio(Alignment.XMidYMid, Scale.Slice);
        FULLSCREEN = preserveAspectRatio8;
        new PreserveAspectRatio(Alignment.XMinYMin, Scale.Slice);
        FULLSCREEN_START = preserveAspectRatio9;
    }

    public PreserveAspectRatio(Alignment alignment2, Scale scale2) {
        this.alignment = alignment2;
        this.scale = scale2;
    }

    public Alignment getAlignment() {
        return this.alignment;
    }

    public Scale getScale() {
        return this.scale;
    }

    public boolean equals(Object obj) {
        Object obj2 = obj;
        if (this == obj2) {
            return true;
        }
        if (obj2 == null) {
            return false;
        }
        if (getClass() != obj2.getClass()) {
            return false;
        }
        PreserveAspectRatio other = (PreserveAspectRatio) obj2;
        if (this.alignment != other.alignment) {
            return false;
        }
        if (this.scale != other.scale) {
            return false;
        }
        return true;
    }
}
