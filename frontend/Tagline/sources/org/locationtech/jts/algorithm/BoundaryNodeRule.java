package org.locationtech.jts.algorithm;

public interface BoundaryNodeRule {
    public static final BoundaryNodeRule ENDPOINT_BOUNDARY_RULE;
    public static final BoundaryNodeRule MOD2_BOUNDARY_RULE;
    public static final BoundaryNodeRule MONOVALENT_ENDPOINT_BOUNDARY_RULE;
    public static final BoundaryNodeRule MULTIVALENT_ENDPOINT_BOUNDARY_RULE;
    public static final BoundaryNodeRule OGC_SFS_BOUNDARY_RULE = MOD2_BOUNDARY_RULE;

    boolean isInBoundary(int i);

    static {
        BoundaryNodeRule boundaryNodeRule;
        BoundaryNodeRule boundaryNodeRule2;
        BoundaryNodeRule boundaryNodeRule3;
        BoundaryNodeRule boundaryNodeRule4;
        new Mod2BoundaryNodeRule();
        MOD2_BOUNDARY_RULE = boundaryNodeRule;
        new EndPointBoundaryNodeRule();
        ENDPOINT_BOUNDARY_RULE = boundaryNodeRule2;
        new MultiValentEndPointBoundaryNodeRule();
        MULTIVALENT_ENDPOINT_BOUNDARY_RULE = boundaryNodeRule3;
        new MonoValentEndPointBoundaryNodeRule();
        MONOVALENT_ENDPOINT_BOUNDARY_RULE = boundaryNodeRule4;
    }

    public static class Mod2BoundaryNodeRule implements BoundaryNodeRule {
        public Mod2BoundaryNodeRule() {
        }

        public boolean isInBoundary(int boundaryCount) {
            return boundaryCount % 2 == 1;
        }
    }

    public static class EndPointBoundaryNodeRule implements BoundaryNodeRule {
        public EndPointBoundaryNodeRule() {
        }

        public boolean isInBoundary(int boundaryCount) {
            return boundaryCount > 0;
        }
    }

    public static class MultiValentEndPointBoundaryNodeRule implements BoundaryNodeRule {
        public MultiValentEndPointBoundaryNodeRule() {
        }

        public boolean isInBoundary(int boundaryCount) {
            return boundaryCount > 1;
        }
    }

    public static class MonoValentEndPointBoundaryNodeRule implements BoundaryNodeRule {
        public MonoValentEndPointBoundaryNodeRule() {
        }

        public boolean isInBoundary(int boundaryCount) {
            return boundaryCount == 1;
        }
    }
}
