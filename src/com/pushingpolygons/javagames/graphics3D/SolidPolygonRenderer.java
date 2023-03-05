// SolidPolygonRenderer.java
package com.pushingpolygons.javagames.graphics3D;

import java.awt.Graphics2D;
import java.awt.Color;
import com.pushingpolygons.javagames.math3D.*;

public class SolidPolygonRenderer extends PolygonRenderer
{
    public SolidPolygonRenderer(Camera camera, int left, int top, int width, int height)
    {
        super(camera, left, top, width, height);
    }

    protected void drawCurrentPolygon(Graphics2D g)
    {
        // Set the color
        if (sourcePolygon instanceof SolidPolygon3D)
        {
            g.setColor(((SolidPolygon3D)sourcePolygon).getColor());
        }

        else
        {
            g.setColor(Color.GREEN);
        }

        // Draw the scans
        int y = scanConverter.getTopBoundary();

        while (y<=scanConverter.getBottomBoundary())
        {
            ScanConverter.Scan scan = scanConverter.getScan(y);

            if (scan.isValid())
            {
                g.drawLine(scan.left, y, scan.right, y);
            }

            y++;
        }
    }
}
