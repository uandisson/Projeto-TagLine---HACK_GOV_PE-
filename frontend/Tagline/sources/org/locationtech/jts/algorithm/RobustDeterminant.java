package org.locationtech.jts.algorithm;

import org.locationtech.jts.geom.Coordinate;

public class RobustDeterminant {
    public RobustDeterminant() {
    }

    public static int signOfDet2x2(double d, double d2, double d3, double d4) {
        double y1;
        double x1;
        double x12 = d;
        double y12 = d2;
        double x2 = d3;
        double y2 = d4;
        long count = 0;
        int sign = 1;
        if (x12 == 0.0d || y2 == 0.0d) {
            if (y12 == 0.0d || x2 == 0.0d) {
                return 0;
            }
            if (y12 > 0.0d) {
                if (x2 > 0.0d) {
                    return -1;
                }
                return 1;
            } else if (x2 > 0.0d) {
                return 1;
            } else {
                return -1;
            }
        } else if (y12 != 0.0d && x2 != 0.0d) {
            if (0.0d < y12) {
                if (0.0d < y2) {
                    if (y12 > y2) {
                        sign = -1;
                        double swap = x12;
                        x12 = x2;
                        x2 = swap;
                        double swap2 = y12;
                        y12 = y2;
                        y2 = swap2;
                    }
                } else if (y12 <= (-y2)) {
                    sign = -1;
                    x2 = -x2;
                    y2 = -y2;
                } else {
                    double swap3 = x12;
                    x12 = -x2;
                    x2 = swap3;
                    double swap4 = y12;
                    y12 = -y2;
                    y2 = swap4;
                }
            } else if (0.0d < y2) {
                if ((-y12) <= y2) {
                    sign = -1;
                    x12 = -x12;
                    y12 = -y12;
                } else {
                    double swap5 = -x12;
                    x12 = x2;
                    x2 = swap5;
                    double swap6 = -y12;
                    y12 = y2;
                    y2 = swap6;
                }
            } else if (y12 >= y2) {
                x12 = -x12;
                y12 = -y12;
                x2 = -x2;
                y2 = -y2;
            } else {
                sign = -1;
                double swap7 = -x12;
                x12 = -x2;
                x2 = swap7;
                double swap8 = -y12;
                y12 = -y2;
                y2 = swap8;
            }
            if (0.0d < x12) {
                if (0.0d >= x2) {
                    return sign;
                }
                if (x12 > x2) {
                    return sign;
                }
            } else if (0.0d < x2) {
                return -sign;
            } else {
                if (x12 < x2) {
                    return -sign;
                }
                sign = -sign;
                x12 = -x12;
                x2 = -x2;
            }
            do {
                count++;
                double k = Math.floor(x2 / x1);
                double x22 = x22 - (k * x1);
                double y22 = y22 - (k * y1);
                if (y22 < 0.0d) {
                    return -sign;
                }
                if (y22 > y1) {
                    return sign;
                }
                if (x1 > x22 + x22) {
                    if (y1 < y22 + y22) {
                        return sign;
                    }
                } else if (y1 > y22 + y22) {
                    return -sign;
                } else {
                    x22 = x1 - x22;
                    y22 = y1 - y22;
                    sign = -sign;
                }
                if (y22 == 0.0d) {
                    if (x22 == 0.0d) {
                        return 0;
                    }
                    return -sign;
                } else if (x22 == 0.0d) {
                    return sign;
                } else {
                    double k2 = Math.floor(x1 / x22);
                    x1 -= k2 * x22;
                    y1 -= k2 * y22;
                    if (y1 < 0.0d) {
                        return sign;
                    }
                    if (y1 > y22) {
                        return -sign;
                    }
                    if (x22 > x1 + x1) {
                        if (y22 < y1 + y1) {
                            return -sign;
                        }
                    } else if (y22 > y1 + y1) {
                        return sign;
                    } else {
                        x1 = x22 - x1;
                        y1 = y22 - y1;
                        sign = -sign;
                    }
                    if (y1 == 0.0d) {
                        if (x1 == 0.0d) {
                            return 0;
                        }
                        return sign;
                    }
                }
            } while (x1 != 0.0d);
            return -sign;
        } else if (y2 > 0.0d) {
            if (x12 > 0.0d) {
                return 1;
            }
            return -1;
        } else if (x12 > 0.0d) {
            return -1;
        } else {
            return 1;
        }
    }

    public static int orientationIndex(Coordinate coordinate, Coordinate coordinate2, Coordinate coordinate3) {
        Coordinate p1 = coordinate;
        Coordinate p2 = coordinate2;
        Coordinate q = coordinate3;
        return signOfDet2x2(p2.f412x - p1.f412x, p2.f413y - p1.f413y, q.f412x - p2.f412x, q.f413y - p2.f413y);
    }
}
