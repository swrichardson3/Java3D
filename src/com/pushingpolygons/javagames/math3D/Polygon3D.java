// Polygon3D.java
package com.pushingpolygons.javagames.math3D;

public class Polygon3D
{
    private static Vector4D mVector1 = new Vector4D();
    private static Vector4D mVector2 = new Vector4D();

    public Vector4D[] mVertices;
    private int mNumVertices;
    private Vector4D mNormal;

    // The default, no-argument constructor creates a blank polygon
    // with no vertices. A polygon that uses this constructor can be
    // used as a "scratch" polygon. A scratch polygon is convenient
    // in case you want to copy a polygon and modify it, preserving
    // the original polygon. Call the setTo() method to set the scratch
    // polygon to the polygon you want to copy and modify.
    public Polygon3D()
    {
        mNumVertices = 0;
        mVertices = new Vector4D[0];
    }

    public Polygon3D(Vector4D v0, Vector4D v1, Vector4D v2)
    {
        this(new Vector4D[] {v0, v1, v2});
    }

    public Polygon3D(Vector4D v0, Vector4D v1, Vector4D v2, Vector4D v3)
    {
        this(new Vector4D[] {v0, v1, v2, v3});
    }

    public Polygon3D(Vector4D[] vertices)
    {
        this.mVertices = vertices;
        this.mNumVertices = mVertices.length;
        calculateNormal();
    }

    public void setTo(Polygon3D polygon)
    {
        this.mNumVertices = polygon.mNumVertices;

        ensureCapacity(mNumVertices);

        for(int i = 0; i < mNumVertices; i++)
        {
            mVertices[i].setTo(polygon.mVertices[i]);
        }
    }

    protected void ensureCapacity(int length)
    {
        if(mVertices.length < length)
        {
            Vector4D[] temp = new Vector4D[length];
            System.arraycopy(mVertices, 0, temp, 0, mVertices.length);

            for(int i = mVertices.length; i < temp.length; i++)
            {
                temp[i] = new Vector4D();
            }

            mVertices = temp;
        }
    }

    public int getNumVertices()
    {
        return(mNumVertices);
    }

    public Vector4D getVertex(int index)
    {
        return(mVertices[index]);
    }

    // Calculates the unit-vector mNormal of this polygon. This
    // method uses the first, second, and third vertices to
    // calculate the mNormal, so if these vertices are collinear,
    // this method will not work. In this case, you can get
    // the mNormal from the bounding rectangle. This method uses
    // static objects in the Polygon3D class for calculations, so
    // this method is not thread-safe across all instances of Polygon3D.
    public Vector4D calculateNormal()
    {
        if (mNormal == null)
        {
            mNormal = new Vector4D();
        }

        mVector1.setTo(mVertices[2]);
        mVector1.subtract(mVertices[1]);
        mVector2.setTo(mVertices[0]);
        mVector2.subtract(mVertices[1]);
        mNormal.calculateCrossProduct(mVector1, mVector2);
        mNormal.normalize();
        return mNormal;
    }

    public Vector4D getNormal()
    {
        return mNormal;
    }

    public void setNormal(Vector4D n)
    {
        if(mNormal == null)
        {
            mNormal = new Vector4D(n);
        }

        else
        {
            mNormal.setTo(n);
        }
    }

    // Tests if this polygon is facing the specified location.
    // This method uses static objects in the Polygon3D class
    // for calculations, so this method is not thread-safe across
    // all instances of Polygon3D.
    public boolean isFacing(Vector4D u)
    {
        mVector1.setTo(u);
        mVector1.subtract(mVertices[0]);
        return(mNormal.calculateDotProduct(mVector1) >= 0);
    }

    // Clips this polygon so that all vertices are in front of the
    // clip plane, clipZ. That is, all vertices have z <= clipZ.
    public boolean clip(float clipZ)
    {
        boolean isCompletelyHidden = true;

        for(int i = 0; i < mNumVertices; i++)
        {
            int next = (i + 1) % mNumVertices;
            Vector4D v1 = mVertices[i];
            Vector4D v2 = mVertices[next];

            if(v1.z < clipZ)
            {
                isCompletelyHidden = false;
            }

            // Ensure that v1.z < v2.z
            if(v1.z > v2.z)
            {
                Vector4D temp = v1;
                v1 = v2;
                v2 = temp;
            }

            if((v1.z < clipZ) && (v2.z > clipZ))
            {
                float scale = (clipZ - v1.z)/(v2.z - v1.z);
                float x = v1.x + scale * (v2.x - v1.x);
                float y = v1.y + scale * (v2.y - v1.y);

                insertVertex(next, x, y, clipZ);
                i++; // Skip the vertex we just created.
            }
        }

        if(isCompletelyHidden)
        {
            return(false);
        }

        // Delete all vertices that have z > clipZ.
        for(int i = mNumVertices - 1; i >= 0; i--)
        {
            if(mVertices[i].z > clipZ)
            {
                deleteVertex(i);
            }
        }

        return(true);
    }

    // Insert a vertex of a polygon at the specified index.
    public void insertVertex(int index, float x, float y, float z)
    {
        ensureCapacity(mVertices.length + 1);
        Vector4D newVertex = mVertices[mVertices.length];
        newVertex.x = x;
        newVertex.y = y;
        newVertex.z = z;
        newVertex.w = 1.0f;

        for(int i = mVertices.length - 1; i > index; i--)
        {
            mVertices[i] = mVertices[i - 1];
        }

        mVertices[index] = newVertex;
        mNumVertices++;
    }

    // Insert a vertex of a polygon at the specified index.
    public void insertVertex(int index, Vector4D vertex)
    {
        Vector4D[] newV = new Vector4D[mNumVertices + 1];
        System.arraycopy(mVertices, 0, newV, 0, index);
        newV[index] = vertex;
        System.arraycopy(mVertices, index, newV, index + 1, mNumVertices - index);
        mVertices = newV;
        mNumVertices++;
    }

    // Delete a vertex of a polygon at the specified index.
    public void deleteVertex(int index)
    {
        Vector4D deleted = mVertices[index];

        for(int i = index; i < mVertices.length - 1; i++)
        {
            mVertices[i] = mVertices[i + 1];
        }

        mVertices[mVertices.length - 1] = deleted;
        mNumVertices--;
    }
}

