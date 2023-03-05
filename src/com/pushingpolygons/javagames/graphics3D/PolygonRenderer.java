// PolygonRenderer.java
package com.pushingpolygons.javagames.graphics3D;

import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.Rectangle;
import com.pushingpolygons.javagames.math3D.*;

public abstract class PolygonRenderer
{
    protected Camera camera;
    protected ScanConverter scanConverter;
    protected boolean clearViewEveryFrame;
    protected float aspectRatio;
    protected Transform modelViewTransform;
    protected Transform projectionTransform;
    protected Transform viewPortTransform;
    protected Polygon3D sourcePolygon;
    protected Polygon3D destPolygon;
    private Rectangle bounds;

    public PolygonRenderer(Camera camera, int left, int top, int width, int height)
    {
        this.camera = camera;
        bounds = new Rectangle();
        bounds.x = left;
        bounds.y = top;
        bounds.width = width;
        bounds.height = height;
        this.clearViewEveryFrame = true;
        this.aspectRatio = (float)getWidth()/(float)getHeight();
        destPolygon = new Polygon3D();
        scanConverter = new ScanConverter(getLeftOffset(), getTopOffset(), getWidth(), getHeight());
        modelViewTransform = new Transform();
        projectionTransform = new Transform();
        viewPortTransform = new Transform();
    }

    public int getWidth()
    {
        return(bounds.width);
    }

    public int getHeight()
    {
        return(bounds.height);
    }

    public int getLeftOffset()
    {
        return bounds.x;
    }

    public int getTopOffset()
    {
        return bounds.y;
    }

    public void startFrame(Graphics2D g)
    {
        if(clearViewEveryFrame)
        {
            // Erase background.
            g.setColor(Color.black);
            g.fillRect(getLeftOffset(), getTopOffset(), getWidth(), getHeight());
        }
    }

    public void endFrame(Graphics2D g)
    {
        // Do nothing, for now.
    }

    public boolean draw(Graphics2D g, Polygon3D polygon)
    {
        if(polygon.isFacing(camera.eye))
        {
            float transformedX;
            float transformedY;
            float transformedZ;
            float transformedW;

            sourcePolygon = polygon;
            destPolygon.setTo(polygon);

            modelViewTransform.Identity();
            modelViewTransform.LookAt(camera.eye, camera.at, camera.up);

            for(int i = 0; i < destPolygon.getNumVertices(); i++)
            {
                transformedX = modelViewTransform.m.m11 *destPolygon.mVertices[i].x + modelViewTransform.m.m12 *destPolygon.mVertices[i].y + modelViewTransform.m.m13 *destPolygon.mVertices[i].z + modelViewTransform.m.m14 *destPolygon.mVertices[i].w;
                transformedY = modelViewTransform.m.m21 *destPolygon.mVertices[i].x + modelViewTransform.m.m22 *destPolygon.mVertices[i].y + modelViewTransform.m.m23 *destPolygon.mVertices[i].z + modelViewTransform.m.m24 *destPolygon.mVertices[i].w;
                transformedZ = modelViewTransform.m._31*destPolygon.mVertices[i].x + modelViewTransform.m._32*destPolygon.mVertices[i].y + modelViewTransform.m._33*destPolygon.mVertices[i].z + modelViewTransform.m._34*destPolygon.mVertices[i].w;
                transformedW = modelViewTransform.m._41*destPolygon.mVertices[i].x + modelViewTransform.m._42*destPolygon.mVertices[i].y + modelViewTransform.m._43*destPolygon.mVertices[i].z + modelViewTransform.m._44*destPolygon.mVertices[i].w;

                destPolygon.mVertices[i].x = transformedX;
                destPolygon.mVertices[i].y = transformedY;
                destPolygon.mVertices[i].z = transformedZ;
                destPolygon.mVertices[i].w = transformedW;
            }

            projectionTransform.Identity();
            projectionTransform.Perspective(75.0f, aspectRatio, 1.0f, 1500.0f);

            for(int i = 0; i < destPolygon.getNumVertices(); i++)
            {
                transformedX = projectionTransform.m.m11 *destPolygon.mVertices[i].x + projectionTransform.m.m12 *destPolygon.mVertices[i].y + projectionTransform.m.m13 *destPolygon.mVertices[i].z + projectionTransform.m.m14 *destPolygon.mVertices[i].w;
                transformedY = projectionTransform.m.m21 *destPolygon.mVertices[i].x + projectionTransform.m.m22 *destPolygon.mVertices[i].y + projectionTransform.m.m23 *destPolygon.mVertices[i].z + projectionTransform.m.m24 *destPolygon.mVertices[i].w;
                transformedZ = projectionTransform.m._31*destPolygon.mVertices[i].x + projectionTransform.m._32*destPolygon.mVertices[i].y + projectionTransform.m._33*destPolygon.mVertices[i].z + projectionTransform.m._34*destPolygon.mVertices[i].w;
                transformedW = projectionTransform.m._41*destPolygon.mVertices[i].x + projectionTransform.m._42*destPolygon.mVertices[i].y + projectionTransform.m._43*destPolygon.mVertices[i].z + projectionTransform.m._44*destPolygon.mVertices[i].w;

                destPolygon.mVertices[i].x = transformedX;
                destPolygon.mVertices[i].y = transformedY;
                destPolygon.mVertices[i].z = transformedZ;
                destPolygon.mVertices[i].w = transformedW;
            }

            boolean visible = true;
            //boolean visible = destPolygon.clip(-1);

            if(visible)
            {

                //Calculate the Normalized Device Coordinates
                for(int i = 0; i < destPolygon.getNumVertices(); i++)
                {
                    destPolygon.mVertices[i].x /= destPolygon.mVertices[i].w;
                    destPolygon.mVertices[i].y /= destPolygon.mVertices[i].w;
                    destPolygon.mVertices[i].z /= destPolygon.mVertices[i].w;
                    destPolygon.mVertices[i].w /= destPolygon.mVertices[i].w;
                }

                viewPortTransform.Identity();
                viewPortTransform.Viewport(0.0f, getWidth(), getHeight(), 0.0f);

                for(int i = 0; i < destPolygon.getNumVertices(); i++)
                {
                    transformedX = viewPortTransform.m.m11 *destPolygon.mVertices[i].x + viewPortTransform.m.m12 *destPolygon.mVertices[i].y + viewPortTransform.m.m13 *destPolygon.mVertices[i].z + viewPortTransform.m.m14 *destPolygon.mVertices[i].w;
                    transformedY = viewPortTransform.m.m21 *destPolygon.mVertices[i].x + viewPortTransform.m.m22 *destPolygon.mVertices[i].y + viewPortTransform.m.m23 *destPolygon.mVertices[i].z + viewPortTransform.m.m24 *destPolygon.mVertices[i].w;
                    transformedZ = viewPortTransform.m._31*destPolygon.mVertices[i].x + viewPortTransform.m._32*destPolygon.mVertices[i].y + viewPortTransform.m._33*destPolygon.mVertices[i].z + viewPortTransform.m._34*destPolygon.mVertices[i].w;
                    transformedW = viewPortTransform.m._41*destPolygon.mVertices[i].x + viewPortTransform.m._42*destPolygon.mVertices[i].y + viewPortTransform.m._43*destPolygon.mVertices[i].z + viewPortTransform.m._44*destPolygon.mVertices[i].w;

                    destPolygon.mVertices[i].x = transformedX;
                    destPolygon.mVertices[i].y = transformedY;
                    destPolygon.mVertices[i].z = transformedZ;
                    destPolygon.mVertices[i].w = transformedW;
                }

                System.out.println();

                visible = scanConverter.convert(destPolygon);

                if(visible)
                {
                    drawCurrentPolygon(g);
                    modelViewTransform.Identity();
                    projectionTransform.Identity();
                    viewPortTransform.Identity();
                    return true;
                }
            }
        }

        return false;
    }

    protected abstract void drawCurrentPolygon(Graphics2D g);
}

