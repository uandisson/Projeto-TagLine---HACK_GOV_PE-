package org.locationtech.jts.operation.distance;

import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.index.strtree.ItemBoundable;
import org.locationtech.jts.index.strtree.ItemDistance;
import org.locationtech.jts.index.strtree.STRtree;

public class IndexedFacetDistance {
    private STRtree cachedTree;

    public static double distance(Geometry g1, Geometry g2) {
        IndexedFacetDistance dist;
        new IndexedFacetDistance(g1);
        return dist.getDistance(g2);
    }

    public IndexedFacetDistance(Geometry g1) {
        this.cachedTree = FacetSequenceTreeBuilder.build(g1);
    }

    public double getDistance(Geometry g) {
        ItemDistance itemDistance;
        STRtree tree2 = FacetSequenceTreeBuilder.build(g);
        new FacetSequenceDistance((C15691) null);
        return facetDistance(this.cachedTree.nearestNeighbour(tree2, itemDistance));
    }

    private static double facetDistance(Object[] objArr) {
        Object[] obj = objArr;
        return ((FacetSequence) obj[0]).distance((FacetSequence) obj[1]);
    }

    private static class FacetSequenceDistance implements ItemDistance {
        private FacetSequenceDistance() {
        }

        /* JADX INFO: this call moved to the top of the method (can break code semantics) */
        /* synthetic */ FacetSequenceDistance(C15691 r4) {
            this();
            C15691 r1 = r4;
        }

        public double distance(ItemBoundable item1, ItemBoundable item2) {
            return ((FacetSequence) item1.getItem()).distance((FacetSequence) item2.getItem());
        }
    }
}
