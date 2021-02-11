package org.locationtech.jts.noding.snapround;

import org.locationtech.jts.geom.Envelope;
import org.locationtech.jts.index.ItemVisitor;
import org.locationtech.jts.index.SpatialIndex;
import org.locationtech.jts.index.chain.MonotoneChain;
import org.locationtech.jts.index.chain.MonotoneChainSelectAction;
import org.locationtech.jts.index.strtree.STRtree;
import org.locationtech.jts.noding.NodedSegmentString;
import org.locationtech.jts.noding.SegmentString;

public class MCIndexPointSnapper {
    private STRtree index;

    public MCIndexPointSnapper(SpatialIndex index2) {
        this.index = (STRtree) index2;
    }

    public boolean snap(HotPixel hotPixel, SegmentString parentEdge, int hotPixelVertexIndex) {
        HotPixelSnapAction hotPixelSnapAction;
        ItemVisitor itemVisitor;
        HotPixel hotPixel2 = hotPixel;
        Envelope pixelEnv = hotPixel2.getSafeEnvelope();
        new HotPixelSnapAction(this, hotPixel2, parentEdge, hotPixelVertexIndex);
        HotPixelSnapAction hotPixelSnapAction2 = hotPixelSnapAction;
        final Envelope envelope = pixelEnv;
        final HotPixelSnapAction hotPixelSnapAction3 = hotPixelSnapAction2;
        new ItemVisitor(this) {
            final /* synthetic */ MCIndexPointSnapper this$0;

            {
                this.this$0 = this$0;
            }

            public void visitItem(Object item) {
                ((MonotoneChain) item).select(envelope, hotPixelSnapAction3);
            }
        };
        this.index.query(pixelEnv, itemVisitor);
        return hotPixelSnapAction2.isNodeAdded();
    }

    public boolean snap(HotPixel hotPixel) {
        return snap(hotPixel, (SegmentString) null, -1);
    }

    public class HotPixelSnapAction extends MonotoneChainSelectAction {
        private HotPixel hotPixel;
        private int hotPixelVertexIndex;
        private boolean isNodeAdded = false;
        private SegmentString parentEdge;
        final /* synthetic */ MCIndexPointSnapper this$0;

        public HotPixelSnapAction(MCIndexPointSnapper this$02, HotPixel hotPixel2, SegmentString parentEdge2, int hotPixelVertexIndex2) {
            this.this$0 = this$02;
            this.hotPixel = hotPixel2;
            this.parentEdge = parentEdge2;
            this.hotPixelVertexIndex = hotPixelVertexIndex2;
        }

        public boolean isNodeAdded() {
            return this.isNodeAdded;
        }

        public void select(MonotoneChain mc, int i) {
            int startIndex = i;
            NodedSegmentString ss = (NodedSegmentString) mc.getContext();
            if (this.parentEdge == null || ss != this.parentEdge || startIndex != this.hotPixelVertexIndex) {
                this.isNodeAdded = this.hotPixel.addSnappedNode(ss, startIndex);
            }
        }
    }
}
