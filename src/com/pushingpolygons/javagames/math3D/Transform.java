// Transform.java
package com.pushingpolygons.javagames.math3D;

public class Transform
{
    public Matrix4x4 m = new Matrix4x4();

    public Transform()
    {

    }

    public void Identity()
    {
        m = Matrix4x4.Identity();
    }

    public void Translate(float dx, float dy, float dz)
    {
        Matrix4x4 result = new Matrix4x4();
        result = Matrix4x4.Translate(dx, dy, dz);
        result = Matrix4x4.Multiply(m, result);
        Matrix4x4.Copy(m, result);
    }

    public void Scale(float dx, float dy, float dz)
    {
        Matrix4x4 result = new Matrix4x4();
        result = Matrix4x4.Scale(dx, dy, dz);
        result = Matrix4x4.Multiply(m, result);
        Matrix4x4.Copy(m, result);
    }

    public void RotateX(float degrees)
    {
        Matrix4x4 result = new Matrix4x4();
        result = Matrix4x4.RotateX(degrees);
        result = Matrix4x4.Multiply(m, result);
        Matrix4x4.Copy(m, result);
    }

    public void RotateY(float degrees)
    {
        Matrix4x4 result = new Matrix4x4();
        result = Matrix4x4.RotateY(degrees);
        result = Matrix4x4.Multiply(m, result);
        Matrix4x4.Copy(m, result);
    }

    public void RotateZ(float degrees)
    {
        Matrix4x4 result = new Matrix4x4();
        result = Matrix4x4.RotateZ(degrees);
        result = Matrix4x4.Multiply(m, result);
        Matrix4x4.Copy(m, result);
    }

    public void LookAt(Vector4D eye, Vector4D at, Vector4D up)
    {
        Matrix4x4 result = new Matrix4x4();
        result = Matrix4x4.LookAt(eye, at, up);
        result = Matrix4x4.Multiply(m, result);
        Matrix4x4.Copy(m, result);
    }

    public void Frustum(float left, float right, float bottom, float top, float near, float far)
    {
        Matrix4x4 result = new Matrix4x4();
        result = Matrix4x4.Frustum(left, right, bottom, top, near, far);
        result = Matrix4x4.Multiply(m, result);
        Matrix4x4.Copy(m, result);
    }

    public void Perspective(float degrees, float aspectRatio, float near, float far)
    {
        Matrix4x4 result = new Matrix4x4();
        result = Matrix4x4.Perspective(degrees, aspectRatio, near, far);
        result = Matrix4x4.Multiply(m, result);
        Matrix4x4.Copy(m, result);
    }

    public void Viewport(float left, float right, float bottom, float top)
    {
        Matrix4x4 result = new Matrix4x4();
        result = Matrix4x4.Viewport(left, right, bottom, top);
        result = Matrix4x4.Multiply(m, result);
        Matrix4x4.Copy(m, result);
    }
}

