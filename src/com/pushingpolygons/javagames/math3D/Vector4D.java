// Vector3D.java
package com.pushingpolygons.javagames.math3D;

public class Vector4D
{
    public float x;
    public float y;
    public float z;
    public float w;

    public Vector4D()
    {
        this.x = 0.0f;
        this.y = 0.0f;
        this.z = 0.0f;
        this.w = 1.0f;
    }

    public Vector4D(Vector4D v)
    {
        this.x = v.x;
        this.y = v.y;
        this.z = v.z;
        this.w = v.w;
    }

    public Vector4D(float x, float y, float z)
    {
        this.x = x;
        this.y = y;
        this.z = z;
        this.w = 1.0f;
    }

    // Checks if this Vector3D is equal to the specified Object.
    // They are equal only if the specified Object is a Vector3D
    // and the two Vector3D's x, y, and z coordinates are equal.
    public boolean equals(Object obj)
    {
        Vector4D v = (Vector4D)obj;

        return((this.x == v.x) && (this.y == v.y) && (this.z == v.z) &&(this.w == v.w));
    }

    // Check if this Vector3D is equal to the specified x, y, and z coordinates.
    public boolean equals(float x, float y, float z)
    {
        return((this.x == x) && (this.y == y) && (this.z == z));
    }

    public void setTo(float x, float y, float z)
    {
        this.x = x;
        this.y = y;
        this.z = z;
        this.w = 1.0f;
    }

    public void setTo(Vector4D v)
    {
        this.x = v.x;
        this.y = v.y;
        this.z = v.z;
        this.w = v.w;
    }

    public void add(float x, float y, float z)
    {
        this.x += x;
        this.y += y;
        this.z += z;
    }

    public void add(Vector4D v)
    {
        this.x += v.x;
        this.y += v.y;
        this.z += v.z;
    }

    public void subtract(float x, float y, float z)
    {
        this.x -= x;
        this.y -= y;
        this.z -= z;
    }

    public void subtract(Vector4D v)
    {
        this.x -= v.x;
        this.y -= v.y;
        this.z -= v.z;
    }

    public void multiply(float s)
    {
        this.x *= s;
        this.y *= s;
        this.z *= s;
    }

    public void divide(float s)
    {
        this.x /= s;
        this.y /= s;
        this.z /= s;
    }

    public float length()
    {
        return((float)Math.sqrt(this.x*this.x + this.y*this.y + this.z*this.z));
    }

    public void normalize()
    {
        divide(length());
    }

    public float calculateDotProduct(Vector4D v)
    {
        return(this.x*v.x + this.y*v.y + this.z*v.z);
    }

    public void calculateCrossProduct(Vector4D v1, Vector4D v2)
    {
        float nx = v1.y*v2.z - v1.z*v2.y;
        float ny = v1.z*v2.x - v1.x*v2.z;
        float nz = v1.x*v2.y - v1.y*v2.x;

        this.x = nx;
        this.y = ny;
        this.z = nz;
        this.w = 1.0f;
    }

    public String toString()
    {
        return("(" + this.x + ", " + this.y + ", " + this.z + ")");
    }
}
