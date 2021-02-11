package org.locationtech.jts.math;

import java.io.Serializable;
import org.locationtech.jts.geom.Dimension;

/* renamed from: org.locationtech.jts.math.DD */
public final class C1564DD implements Serializable, Comparable, Cloneable {

    /* renamed from: E */
    public static final C1564DD f451E;
    public static final double EPS = 1.23259516440783E-32d;
    private static final int MAX_PRINT_DIGITS = 32;
    public static final C1564DD NaN;
    private static final C1564DD ONE = valueOf(1.0d);

    /* renamed from: PI */
    public static final C1564DD f452PI;
    public static final C1564DD PI_2;
    private static final String SCI_NOT_EXPONENT_CHAR = "E";
    private static final String SCI_NOT_ZERO = "0.0E0";
    private static final double SPLIT = 1.34217729E8d;
    private static final C1564DD TEN = valueOf(10.0d);
    public static final C1564DD TWO_PI;

    /* renamed from: hi */
    private double f453hi;

    /* renamed from: lo */
    private double f454lo;

    static {
        C1564DD dd;
        C1564DD dd2;
        C1564DD dd3;
        C1564DD dd4;
        C1564DD dd5;
        new C1564DD(3.141592653589793d, 1.2246467991473532E-16d);
        f452PI = dd;
        new C1564DD(6.283185307179586d, 2.4492935982947064E-16d);
        TWO_PI = dd2;
        new C1564DD(1.5707963267948966d, 6.123233995736766E-17d);
        PI_2 = dd3;
        new C1564DD(2.718281828459045d, 1.4456468917292502E-16d);
        f451E = dd4;
        new C1564DD(Double.NaN, Double.NaN);
        NaN = dd5;
    }

    private static C1564DD createNaN() {
        C1564DD dd;
        C1564DD dd2 = dd;
        new C1564DD(Double.NaN, Double.NaN);
        return dd2;
    }

    public static C1564DD valueOf(String str) throws NumberFormatException {
        return parse(str);
    }

    public static C1564DD valueOf(double x) {
        C1564DD dd;
        new C1564DD(x);
        return dd;
    }

    public C1564DD() {
        this.f453hi = 0.0d;
        this.f454lo = 0.0d;
        init(0.0d);
    }

    public C1564DD(double x) {
        this.f453hi = 0.0d;
        this.f454lo = 0.0d;
        init(x);
    }

    public C1564DD(double hi, double lo) {
        this.f453hi = 0.0d;
        this.f454lo = 0.0d;
        init(hi, lo);
    }

    public C1564DD(C1564DD dd) {
        this.f453hi = 0.0d;
        this.f454lo = 0.0d;
        init(dd);
    }

    /* JADX INFO: this call moved to the top of the method (can break code semantics) */
    public C1564DD(String str) throws NumberFormatException {
        this(parse(str));
    }

    public static C1564DD copy(C1564DD dd) {
        C1564DD dd2;
        new C1564DD(dd);
        return dd2;
    }

    public Object clone() {
        try {
            return super.clone();
        } catch (CloneNotSupportedException e) {
            CloneNotSupportedException cloneNotSupportedException = e;
            return null;
        }
    }

    private final void init(double x) {
        this.f453hi = x;
        this.f454lo = 0.0d;
    }

    private final void init(double hi, double lo) {
        this.f453hi = hi;
        this.f454lo = lo;
    }

    private final void init(C1564DD dd) {
        C1564DD dd2 = dd;
        this.f453hi = dd2.f453hi;
        this.f454lo = dd2.f454lo;
    }

    public C1564DD setValue(C1564DD value) {
        init(value);
        return this;
    }

    public C1564DD setValue(double value) {
        init(value);
        return this;
    }

    public final C1564DD add(C1564DD y) {
        return copy(this).selfAdd(y);
    }

    public final C1564DD add(double y) {
        return copy(this).selfAdd(y);
    }

    public final C1564DD selfAdd(C1564DD dd) {
        C1564DD y = dd;
        return selfAdd(y.f453hi, y.f454lo);
    }

    public final C1564DD selfAdd(double d) {
        double y = d;
        double S = this.f453hi + y;
        double e = S - this.f453hi;
        double d2 = y - e;
        double f = d2 + (this.f453hi - (S - e)) + this.f454lo;
        double H = S + f;
        double h = f + (S - H);
        this.f453hi = H + h;
        this.f454lo = h + (H - this.f453hi);
        return this;
    }

    private final C1564DD selfAdd(double d, double d2) {
        double yhi = d;
        double ylo = d2;
        double S = this.f453hi + yhi;
        double T = this.f454lo + ylo;
        double e = S - this.f453hi;
        double f = T - this.f454lo;
        double d3 = yhi - e;
        double s = d3 + (this.f453hi - (S - e));
        double d4 = ylo - f;
        double e2 = s + T;
        double H = S + e2;
        double e3 = d4 + (this.f454lo - (T - f)) + e2 + (S - H);
        double zhi = H + e3;
        this.f453hi = zhi;
        this.f454lo = e3 + (H - zhi);
        return this;
    }

    public final C1564DD subtract(C1564DD y) {
        return add(y.negate());
    }

    public final C1564DD subtract(double y) {
        return add(-y);
    }

    public final C1564DD selfSubtract(C1564DD dd) {
        C1564DD y = dd;
        if (!isNaN()) {
            return selfAdd(-y.f453hi, -y.f454lo);
        }
        return this;
    }

    public final C1564DD selfSubtract(double d) {
        double y = d;
        if (!isNaN()) {
            return selfAdd(-y, 0.0d);
        }
        return this;
    }

    public final C1564DD negate() {
        C1564DD dd;
        if (isNaN()) {
            return this;
        }
        new C1564DD(-this.f453hi, -this.f454lo);
        return dd;
    }

    public final C1564DD multiply(C1564DD dd) {
        C1564DD y = dd;
        if (y.isNaN()) {
            return createNaN();
        }
        return copy(this).selfMultiply(y);
    }

    public final C1564DD multiply(double d) {
        double y = d;
        if (Double.isNaN(y)) {
            return createNaN();
        }
        return copy(this).selfMultiply(y, 0.0d);
    }

    public final C1564DD selfMultiply(C1564DD dd) {
        C1564DD y = dd;
        return selfMultiply(y.f453hi, y.f454lo);
    }

    public final C1564DD selfMultiply(double y) {
        return selfMultiply(y, 0.0d);
    }

    private final C1564DD selfMultiply(double d, double ylo) {
        double yhi = d;
        double C = SPLIT * this.f453hi;
        double hx = C - this.f453hi;
        double c = SPLIT * yhi;
        double hx2 = C - hx;
        double tx = this.f453hi - hx2;
        double C2 = this.f453hi * yhi;
        double hy = c - (c - yhi);
        double ty = yhi - hy;
        double c2 = ((hx2 * hy) - C2) + (hx2 * ty) + (tx * hy) + (tx * ty) + (this.f453hi * ylo) + (this.f454lo * yhi);
        double zhi = C2 + c2;
        this.f453hi = zhi;
        this.f454lo = c2 + (C2 - zhi);
        return this;
    }

    public final C1564DD divide(C1564DD dd) {
        C1564DD dd2;
        C1564DD y = dd;
        double C = this.f453hi / y.f453hi;
        double c = SPLIT * C;
        double u = SPLIT * y.f453hi;
        double hc = c - (c - C);
        double tc = C - hc;
        double hy = u - y.f453hi;
        double U = C * y.f453hi;
        double hy2 = u - hy;
        double ty = y.f453hi - hy2;
        double c2 = ((((this.f453hi - U) - (((((hc * hy2) - U) + (hc * ty)) + (tc * hy2)) + (tc * ty))) + this.f454lo) - (C * y.f454lo)) / y.f453hi;
        double u2 = C + c2;
        new C1564DD(u2, (C - u2) + c2);
        return dd2;
    }

    public final C1564DD divide(double d) {
        double y = d;
        if (Double.isNaN(y)) {
            return createNaN();
        }
        return copy(this).selfDivide(y, 0.0d);
    }

    public final C1564DD selfDivide(C1564DD dd) {
        C1564DD y = dd;
        return selfDivide(y.f453hi, y.f454lo);
    }

    public final C1564DD selfDivide(double y) {
        return selfDivide(y, 0.0d);
    }

    private final C1564DD selfDivide(double d, double ylo) {
        double yhi = d;
        double C = this.f453hi / yhi;
        double c = SPLIT * C;
        double u = SPLIT * yhi;
        double hc = c - (c - C);
        double tc = C - hc;
        double U = C * yhi;
        double hy = u - (u - yhi);
        double ty = yhi - hy;
        double c2 = ((((this.f453hi - U) - (((((hc * hy) - U) + (hc * ty)) + (tc * hy)) + (tc * ty))) + this.f454lo) - (C * ylo)) / yhi;
        double u2 = C + c2;
        this.f453hi = u2;
        this.f454lo = (C - u2) + c2;
        return this;
    }

    public final C1564DD reciprocal() {
        C1564DD dd;
        double C = 1.0d / this.f453hi;
        double c = SPLIT * C;
        double u = SPLIT * this.f453hi;
        double hc = c - (c - C);
        double tc = C - hc;
        double hy = u - this.f453hi;
        double U = C * this.f453hi;
        double hy2 = u - hy;
        double ty = this.f453hi - hy2;
        double c2 = (((1.0d - U) - (((((hc * hy2) - U) + (hc * ty)) + (tc * hy2)) + (tc * ty))) - (C * this.f454lo)) / this.f453hi;
        double zhi = C + c2;
        new C1564DD(zhi, (C - zhi) + c2);
        return dd;
    }

    public C1564DD floor() {
        C1564DD dd;
        if (isNaN()) {
            return NaN;
        }
        double fhi = Math.floor(this.f453hi);
        double flo = 0.0d;
        if (fhi == this.f453hi) {
            flo = Math.floor(this.f454lo);
        }
        new C1564DD(fhi, flo);
        return dd;
    }

    public C1564DD ceil() {
        C1564DD dd;
        if (isNaN()) {
            return NaN;
        }
        double fhi = Math.ceil(this.f453hi);
        double flo = 0.0d;
        if (fhi == this.f453hi) {
            flo = Math.ceil(this.f454lo);
        }
        new C1564DD(fhi, flo);
        return dd;
    }

    public int signum() {
        if (this.f453hi > 0.0d) {
            return 1;
        }
        if (this.f453hi < 0.0d) {
            return -1;
        }
        if (this.f454lo > 0.0d) {
            return 1;
        }
        if (this.f454lo < 0.0d) {
            return -1;
        }
        return 0;
    }

    public C1564DD rint() {
        if (!isNaN()) {
            return add(0.5d).floor();
        }
        return this;
    }

    public C1564DD trunc() {
        if (isNaN()) {
            return NaN;
        }
        if (isPositive()) {
            return floor();
        }
        return ceil();
    }

    public C1564DD abs() {
        C1564DD dd;
        if (isNaN()) {
            return NaN;
        }
        if (isNegative()) {
            return negate();
        }
        new C1564DD(this);
        return dd;
    }

    public C1564DD sqr() {
        return multiply(this);
    }

    public C1564DD selfSqr() {
        return selfMultiply(this);
    }

    public static C1564DD sqr(double d) {
        double x = d;
        return valueOf(x).selfMultiply(x);
    }

    public C1564DD sqrt() {
        if (isZero()) {
            return valueOf(0.0d);
        }
        if (isNegative()) {
            return NaN;
        }
        double x = 1.0d / Math.sqrt(this.f453hi);
        C1564DD axdd = valueOf(this.f453hi * x);
        return axdd.add(subtract(axdd.sqr()).f453hi * x * 0.5d);
    }

    public static C1564DD sqrt(double x) {
        return valueOf(x).sqrt();
    }

    public C1564DD pow(int i) {
        C1564DD dd;
        int exp = i;
        if (((double) exp) == 0.0d) {
            return valueOf(1.0d);
        }
        new C1564DD(this);
        C1564DD r = dd;
        C1564DD s = valueOf(1.0d);
        int n = Math.abs(exp);
        if (n > 1) {
            while (n > 0) {
                if (n % 2 == 1) {
                    C1564DD selfMultiply = s.selfMultiply(r);
                }
                n /= 2;
                if (n > 0) {
                    r = r.sqr();
                }
            }
        } else {
            s = r;
        }
        if (exp < 0) {
            return s.reciprocal();
        }
        return s;
    }

    public C1564DD min(C1564DD dd) {
        C1564DD x = dd;
        if (!mo24316le(x)) {
            return x;
        }
        return this;
    }

    public C1564DD max(C1564DD dd) {
        C1564DD x = dd;
        if (!mo24309ge(x)) {
            return x;
        }
        return this;
    }

    public double doubleValue() {
        return this.f453hi + this.f454lo;
    }

    public int intValue() {
        return (int) this.f453hi;
    }

    public boolean isZero() {
        return this.f453hi == 0.0d && this.f454lo == 0.0d;
    }

    public boolean isNegative() {
        return this.f453hi < 0.0d || (this.f453hi == 0.0d && this.f454lo < 0.0d);
    }

    public boolean isPositive() {
        return this.f453hi > 0.0d || (this.f453hi == 0.0d && this.f454lo > 0.0d);
    }

    public boolean isNaN() {
        return Double.isNaN(this.f453hi);
    }

    public boolean equals(C1564DD dd) {
        C1564DD y = dd;
        return this.f453hi == y.f453hi && this.f454lo == y.f454lo;
    }

    /* renamed from: gt */
    public boolean mo24310gt(C1564DD dd) {
        C1564DD y = dd;
        return this.f453hi > y.f453hi || (this.f453hi == y.f453hi && this.f454lo > y.f454lo);
    }

    /* renamed from: ge */
    public boolean mo24309ge(C1564DD dd) {
        C1564DD y = dd;
        return this.f453hi > y.f453hi || (this.f453hi == y.f453hi && this.f454lo >= y.f454lo);
    }

    /* renamed from: lt */
    public boolean mo24317lt(C1564DD dd) {
        C1564DD y = dd;
        return this.f453hi < y.f453hi || (this.f453hi == y.f453hi && this.f454lo < y.f454lo);
    }

    /* renamed from: le */
    public boolean mo24316le(C1564DD dd) {
        C1564DD y = dd;
        return this.f453hi < y.f453hi || (this.f453hi == y.f453hi && this.f454lo <= y.f454lo);
    }

    public int compareTo(Object o) {
        C1564DD other = (C1564DD) o;
        if (this.f453hi < other.f453hi) {
            return -1;
        }
        if (this.f453hi > other.f453hi) {
            return 1;
        }
        if (this.f454lo < other.f454lo) {
            return -1;
        }
        if (this.f454lo > other.f454lo) {
            return 1;
        }
        return 0;
    }

    public String dump() {
        StringBuilder sb;
        new StringBuilder();
        return sb.append("DD<").append(this.f453hi).append(", ").append(this.f454lo).append(">").toString();
    }

    public String toString() {
        int mag = magnitude(this.f453hi);
        if (mag < -3 || mag > 20) {
            return toSciNotation();
        }
        return toStandardNotation();
    }

    public String toStandardNotation() {
        StringBuilder sb;
        StringBuilder sb2;
        StringBuilder sb3;
        StringBuilder sb4;
        String specialStr = getSpecialNumberString();
        if (specialStr != null) {
            return specialStr;
        }
        int[] magnitude = new int[1];
        String sigDigits = extractSignificantDigits(true, magnitude);
        int decimalPointPos = magnitude[0] + 1;
        String num = sigDigits;
        if (sigDigits.charAt(0) == '.') {
            new StringBuilder();
            num = sb4.append("0").append(sigDigits).toString();
        } else if (decimalPointPos < 0) {
            new StringBuilder();
            num = sb2.append("0.").append(stringOfChar(Dimension.SYM_P, -decimalPointPos)).append(sigDigits).toString();
        } else if (sigDigits.indexOf(46) == -1) {
            String zeroes = stringOfChar(Dimension.SYM_P, decimalPointPos - sigDigits.length());
            new StringBuilder();
            num = sb.append(sigDigits).append(zeroes).append(".0").toString();
        }
        if (!isNegative()) {
            return num;
        }
        new StringBuilder();
        return sb3.append("-").append(num).toString();
    }

    public String toSciNotation() {
        StringBuilder sb;
        StringBuilder sb2;
        StringBuilder sb3;
        StringBuilder sb4;
        Throwable th;
        StringBuilder sb5;
        if (isZero()) {
            return SCI_NOT_ZERO;
        }
        String specialStr = getSpecialNumberString();
        if (specialStr != null) {
            return specialStr;
        }
        int[] magnitude = new int[1];
        String digits = extractSignificantDigits(false, magnitude);
        new StringBuilder();
        String expStr = sb.append(SCI_NOT_EXPONENT_CHAR).append(magnitude[0]).toString();
        if (digits.charAt(0) == '0') {
            Throwable th2 = th;
            new StringBuilder();
            new IllegalStateException(sb5.append("Found leading zero: ").append(digits).toString());
            throw th2;
        }
        String trailingDigits = "";
        if (digits.length() > 1) {
            trailingDigits = digits.substring(1);
        }
        new StringBuilder();
        String digitsWithDecimal = sb2.append(digits.charAt(0)).append(".").append(trailingDigits).toString();
        if (isNegative()) {
            new StringBuilder();
            return sb4.append("-").append(digitsWithDecimal).append(expStr).toString();
        }
        new StringBuilder();
        return sb3.append(digitsWithDecimal).append(expStr).toString();
    }

    private String extractSignificantDigits(boolean z, int[] iArr) {
        StringBuffer stringBuffer;
        char digitChar;
        boolean insertDecimalPoint = z;
        int[] magnitude = iArr;
        C1564DD y = abs();
        int mag = magnitude(y.f453hi);
        C1564DD y2 = y.divide(TEN.pow(mag));
        if (y2.mo24310gt(TEN)) {
            y2 = y2.divide(TEN);
            mag++;
        } else if (y2.mo24317lt(ONE)) {
            y2 = y2.multiply(TEN);
            mag--;
        }
        int decimalPointPos = mag + 1;
        new StringBuffer();
        StringBuffer buf = stringBuffer;
        for (int i = 0; i <= 31; i++) {
            if (insertDecimalPoint && i == decimalPointPos) {
                StringBuffer append = buf.append('.');
            }
            int digit = (int) y2.f453hi;
            if (digit < 0 || digit > 9) {
            }
            if (digit < 0) {
                break;
            }
            boolean rebiasBy10 = false;
            if (digit > 9) {
                rebiasBy10 = true;
                digitChar = '9';
            } else {
                digitChar = (char) (48 + digit);
            }
            StringBuffer append2 = buf.append(digitChar);
            y2 = y2.subtract(valueOf((double) digit)).multiply(TEN);
            if (rebiasBy10) {
                C1564DD selfAdd = y2.selfAdd(TEN);
            }
            boolean continueExtractingDigits = true;
            int remMag = magnitude(y2.f453hi);
            if (remMag < 0 && Math.abs(remMag) >= 31 - i) {
                continueExtractingDigits = false;
            }
            if (!continueExtractingDigits) {
                break;
            }
        }
        magnitude[0] = mag;
        return buf.toString();
    }

    private static String stringOfChar(char c, int i) {
        StringBuffer stringBuffer;
        char ch = c;
        int len = i;
        new StringBuffer();
        StringBuffer buf = stringBuffer;
        for (int i2 = 0; i2 < len; i2++) {
            StringBuffer append = buf.append(ch);
        }
        return buf.toString();
    }

    private String getSpecialNumberString() {
        if (isZero()) {
            return "0.0";
        }
        if (isNaN()) {
            return "NaN ";
        }
        return null;
    }

    private static int magnitude(double x) {
        double xAbs = Math.abs(x);
        int xMag = (int) Math.floor(Math.log(xAbs) / Math.log(10.0d));
        if (Math.pow(10.0d, (double) xMag) * 10.0d <= xAbs) {
            xMag++;
        }
        return xMag;
    }

    public static C1564DD parse(String str) throws NumberFormatException {
        C1564DD dd;
        Throwable th;
        StringBuilder sb;
        Throwable th2;
        StringBuilder sb2;
        char signCh;
        String str2 = str;
        int i = 0;
        int strlen = str2.length();
        while (Character.isWhitespace(str2.charAt(i))) {
            i++;
        }
        boolean isNegative = false;
        if (i < strlen && ((signCh = str2.charAt(i)) == '-' || signCh == '+')) {
            i++;
            if (signCh == '-') {
                isNegative = true;
            }
        }
        new C1564DD();
        C1564DD val = dd;
        int numDigits = 0;
        int numBeforeDec = 0;
        int exp = 0;
        while (true) {
            if (i >= strlen) {
                break;
            }
            char ch = str2.charAt(i);
            i++;
            if (Character.isDigit(ch)) {
                C1564DD selfMultiply = val.selfMultiply(TEN);
                C1564DD selfAdd = val.selfAdd((double) (ch - '0'));
                numDigits++;
            } else if (ch == '.') {
                numBeforeDec = numDigits;
            } else if (ch == 'e' || ch == 'E') {
                String expStr = str2.substring(i);
                try {
                    exp = Integer.parseInt(expStr);
                } catch (NumberFormatException e) {
                    NumberFormatException numberFormatException = e;
                    Throwable th3 = th;
                    new StringBuilder();
                    new NumberFormatException(sb.append("Invalid exponent ").append(expStr).append(" in string ").append(str2).toString());
                    throw th3;
                }
            } else {
                Throwable th4 = th2;
                new StringBuilder();
                new NumberFormatException(sb2.append("Unexpected character '").append(ch).append("' at position ").append(i).append(" in string ").append(str2).toString());
                throw th4;
            }
        }
        C1564DD val2 = val;
        int numDecPlaces = (numDigits - numBeforeDec) - exp;
        if (numDecPlaces == 0) {
            val2 = val;
        } else if (numDecPlaces > 0) {
            val2 = val.divide(TEN.pow(numDecPlaces));
        } else if (numDecPlaces < 0) {
            val2 = val.multiply(TEN.pow(-numDecPlaces));
        }
        if (isNegative) {
            return val2.negate();
        }
        return val2;
    }
}
