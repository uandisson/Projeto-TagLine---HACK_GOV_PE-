package org.osmdroid.util;

import android.graphics.Rect;
import org.osmdroid.tileprovider.MapTile;

public abstract class TileLooper {
    protected int mTileZoomLevel;
    protected final Rect mTiles;

    public abstract void handleTile(MapTile mapTile, int i, int i2);

    public TileLooper() {
        Rect rect;
        new Rect();
        this.mTiles = rect;
    }

    /* access modifiers changed from: protected */
    public void loop(double d, Rect pViewPort) {
        MapTile tile;
        double pZoomLevel = d;
        Rect PixelXYToTileXY = TileSystem.PixelXYToTileXY(pViewPort, TileSystem.getTileSize(pZoomLevel), this.mTiles);
        this.mTileZoomLevel = TileSystem.getInputTileZoomLevel(pZoomLevel);
        initialiseLoop();
        int mapTileUpperBound = 1 << this.mTileZoomLevel;
        int width = (this.mTiles.right - this.mTiles.left) + 1;
        if (width <= 0) {
            width += mapTileUpperBound;
        }
        int height = (this.mTiles.bottom - this.mTiles.top) + 1;
        if (height <= 0) {
            height += mapTileUpperBound;
        }
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                int x = this.mTiles.left + i;
                int y = this.mTiles.top + j;
                new MapTile(this.mTileZoomLevel, MyMath.mod(x, mapTileUpperBound), MyMath.mod(y, mapTileUpperBound));
                handleTile(tile, x, y);
            }
        }
        finaliseLoop();
    }

    public void initialiseLoop() {
    }

    public void finaliseLoop() {
    }
}
