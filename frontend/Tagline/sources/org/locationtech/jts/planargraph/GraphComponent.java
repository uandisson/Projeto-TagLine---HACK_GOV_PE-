package org.locationtech.jts.planargraph;

import java.util.Iterator;

public abstract class GraphComponent {
    private Object data;
    protected boolean isMarked = false;
    protected boolean isVisited = false;

    public abstract boolean isRemoved();

    public static void setVisited(Iterator it, boolean z) {
        Iterator i = it;
        boolean visited = z;
        while (i.hasNext()) {
            ((GraphComponent) i.next()).setVisited(visited);
        }
    }

    public static void setMarked(Iterator it, boolean z) {
        Iterator i = it;
        boolean marked = z;
        while (i.hasNext()) {
            ((GraphComponent) i.next()).setMarked(marked);
        }
    }

    public static GraphComponent getComponentWithVisitedState(Iterator it, boolean z) {
        Iterator i = it;
        boolean visitedState = z;
        while (i.hasNext()) {
            GraphComponent comp = (GraphComponent) i.next();
            if (comp.isVisited() == visitedState) {
                return comp;
            }
        }
        return null;
    }

    public GraphComponent() {
    }

    public boolean isVisited() {
        return this.isVisited;
    }

    public void setVisited(boolean isVisited2) {
        boolean z = isVisited2;
        this.isVisited = z;
    }

    public boolean isMarked() {
        return this.isMarked;
    }

    public void setMarked(boolean isMarked2) {
        boolean z = isMarked2;
        this.isMarked = z;
    }

    public void setContext(Object data2) {
        Object obj = data2;
        this.data = obj;
    }

    public Object getContext() {
        return this.data;
    }

    public void setData(Object data2) {
        Object obj = data2;
        this.data = obj;
    }

    public Object getData() {
        return this.data;
    }
}
