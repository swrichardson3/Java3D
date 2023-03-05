// SolidPolygon3D.java
package com.pushingpolygons.javagames.math3D;
import java.awt.Color;

// The SolidPolygon3D class is a Polygon with mColor.
public class SolidPolygon3D extends Polygon3D
{
    private Color mColor = Color.GREEN;

    public SolidPolygon3D()
    {
        super();
    }

    public SolidPolygon3D(Vector4D[] vertices)
    {
        super(vertices);
    }

    public SolidPolygon3D(Vector4D v0, Vector4D v1, Vector4D v2)
    {
        this(new Vector4D[] {v0, v1, v2});
    }

    public SolidPolygon3D(Vector4D v0, Vector4D v1, Vector4D v2, Vector4D v3)
    {
        this(new Vector4D[] {v0, v1, v2, v3});
    }

    public void setTo(Polygon3D polygon)
    {
        super.setTo(polygon);

        if(polygon instanceof SolidPolygon3D)
        {
            mColor = ((SolidPolygon3D)polygon).mColor;
        }
    }

    public Color getColor()
    {
        return mColor;
    }

    public void setColor(Color color)
    {
        this.mColor = color;
    }
}

