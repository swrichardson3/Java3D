// Camera.java
package com.pushingpolygons.javagames.math3D;

public class Camera
{
    //private Rectangle bounds;
    public Vector4D eye;
    public Vector4D at;
    public Vector4D up;

    public Camera(Vector4D eye, Vector4D at, Vector4D up)
    {
        this.eye = eye;
        this.at = at;
        this.up = up;
    }
}
