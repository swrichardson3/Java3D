// ScanConverter.java
package com.pushingpolygons.javagames.graphics3D;

import com.pushingpolygons.javagames.math3D.*;
import com.pushingpolygons.javagames.util.MoreMath;
import java.awt.Rectangle;

// The ScanConverter class converts a projected polygon
// into a series of horizontal scans for drawing.
public class ScanConverter
{
    // private static final int SCALE_BITS = 16;
    // private static final int SCALE = 1 << SCALE_BITS;
    // private static final int SCALE_MASK = SCALE - 1;

    protected Scan[] scans;
    protected int top;
    protected int bottom;
    private Rectangle bounds;

    public ScanConverter(int left, int top, int width, int height)
    {
        bounds = new Rectangle();
        bounds.x = left;
        bounds.y = top;
        bounds.width = width;
        bounds.height = height;
    }

    public int getLeftOffset()
    {
        return bounds.x;
    }

    public int getTopOffset()
    {
        return bounds.y;
    }

    public int getWidth()
    {
        return(bounds.width);
    }

    public int getHeight()
    {
        return(bounds.height);
    }

    public int getTopBoundary()
    {
        return(top);
    }

    public int getBottomBoundary()
    {
        return(bottom);
    }

    public Scan getScan(int y)
    {
        return(scans[y]);
    }

    // Ensure this ScanConverter has the capacity to
    // scan-convert a polygon to the ViewWindow.
    protected void ensureCapacity()
    {
        int height = getTopOffset() + getHeight();

        if((scans == null) || (scans.length != height))
        {
            scans = new Scan[height];

            for(int i = 0; i < height; i++)
            {
                scans[i] = new Scan();
            }

            top = 0;
            bottom = height - 1;
        }
    }

    private void clearCurrentScan()
    {
        for(int i = top; i <= bottom; i++)
        {
            scans[i].clear();
        }

        top = Integer.MAX_VALUE;
        bottom = Integer.MIN_VALUE;
    }

    // Scan-converts a projected polygon. Returns true
    // if the polygon is visible in the view window.
    public boolean convert(Polygon3D polygon)
    {
        ensureCapacity();
        clearCurrentScan();

        int minX = getLeftOffset();
        int maxX = getLeftOffset() + getWidth() - 1;
        int minY = getTopOffset();
        int maxY = getTopOffset() + getHeight() - 1;

        int numVertices = polygon.getNumVertices();

        for(int i = 0; i < numVertices; i++)
        {
            Vector4D v1 = polygon.getVertex(i);
            Vector4D v2;

            if(i == numVertices - 1)
            {
                v2 = polygon.getVertex(0);
            }

            else
            {
                v2 = polygon.getVertex(i + 1);
            }

            // We want to ensure that v1.y < v2.y
            if(v1.y > v2.y)
            {
                Vector4D temp = v1;
                v1 = v2;
                v2 = temp;
            }

            float dy = v2.y - v1.y;

            // Ignore Horizontal Lines.
            if(dy == 0)
            {
                continue;
            }

            int startY = Math.max(MoreMath.ceil(v1.y), minY);
            int endY = Math.min(MoreMath.ceil(v2.y) - 1, maxY);
            top = Math.min(top, startY);
            bottom = Math.max(bottom, endY);
            float dx = v2.x - v1.x;

            // Vertical line.
            if (dx == 0)
            {
                int x = MoreMath.ceil(v1.x);

                // Ensure that x is within view bounds.
                x = Math.min(maxX+1, Math.max(x, minX));

                for (int y = startY; y <= endY; y++)
                {
                    scans[y].setBoundary(x);
                }
            }

            else
            {
                float slope = dx/dy;

                for(int y = startY; y <= endY; y++)
                {
                    int x = MoreMath.ceil(v1.x + slope * (y - v1.y));

                    // Ensure that x is within view bounds.
                    x = Math.min(maxX + 1,  Math.max(x, minX));
                    scans[y].setBoundary(x);
                }
            }
        }

        // Check if visible (any valid scans)
        for (int i = top; i <= bottom; i++)
        {
            if (scans[i].isValid())
            {
                return true;
            }
        }

        return false;
    }

    public static class Scan
    {
        public int left;
        public int right;

        public Scan()
        {

        }

        public void setBoundary(int x)
        {
            if(x < left)
            {
                left = x;
            }

            if(x - 1 > right)
            {
                right = x - 1;
            }
        }

        public void clear()
        {
            left = Integer.MAX_VALUE;
            right = Integer.MIN_VALUE;
        }

        public boolean isValid()
        {
            return(left <= right);
        }

        public void setTo(int left, int right)
        {
            this.left = left;
            this.right = right;
        }

        public boolean equals(int left, int right)
        {
            return((this.left == left) && (this.right == right));
        }
    }
}

