// Matrix4x4.java
package com.pushingpolygons.javagames.math3D;

public class Matrix4x4
{
    public float m11, m12, m13, m14;
    public float m21, m22, m23, m24;
    public float _31, _32, _33, _34;
    public float _41, _42, _43, _44;

    private static boolean flag1 = true;
    private static boolean flag2 = true;
    private static boolean flag3 = true;

    private static float W_r = 1.0f;
    private static float W_l = -1.0f;
    private static float W_t = 1.0f;
    private static float W_b = -1.0f;

    private static final float RADIANS_TO_DEGREES = 57.2958f;
    private static final float DEGREES_TO_RADIANS = 0.0174532925f;

    public Matrix4x4()
    {

    }

    public Matrix4x4(float IN_11, float IN_12, float IN_13, float IN_14,
                     float IN_21, float IN_22, float IN_23, float IN_24,
                     float IN_31, float IN_32, float IN_33, float IN_34,
                     float IN_41, float IN_42, float IN_43, float IN_44)
    {
        this.m11 = IN_11; this.m12 = IN_12; this.m13 = IN_13; this.m14 = IN_14;
        this.m21 = IN_21; this.m22 = IN_22; this.m23 = IN_23; this.m24 = IN_24;
        this._31 = IN_31; this._32 = IN_32; this._33 = IN_33; this._34 = IN_34;
        this._41 = IN_41; this._42 = IN_42; this._43 = IN_43; this._44 = IN_44;
    }

    public static Matrix4x4 Identity()
    {
        Matrix4x4 m = new Matrix4x4();

        m.m11 = 1.0f; m.m12 = 0.0f; m.m13 = 0.0f; m.m14 = 0.0f;
        m.m21 = 0.0f; m.m22 = 1.0f; m.m23 = 0.0f; m.m24 = 0.0f;
        m._31 = 0.0f; m._32 = 0.0f; m._33 = 1.0f; m._34 = 0.0f;
        m._41 = 0.0f; m._42 = 0.0f; m._43 = 0.0f; m._44 = 1.0f;

        return(m);
    }

    public static Matrix4x4 Translate(float dx, float dy, float dz)
    {
        Matrix4x4 m = new Matrix4x4();

        m.m11 = 1.0f; m.m12 = 0.0f; m.m13 = 0.0f; m.m14 = dx;
        m.m21 = 0.0f; m.m22 = 1.0f; m.m23 = 0.0f; m.m24 = dy;
        m._31 = 0.0f; m._32 = 0.0f; m._33 = 1.0f; m._34 = dz;
        m._41 = 0.0f; m._42 = 0.0f; m._43 = 0.0f; m._44 = 1.0f;

        return(m);
    }

    public static Matrix4x4 Scale(float dx, float dy, float dz)
    {
        Matrix4x4 m = new Matrix4x4();

        m.m11 = dx;   m.m12 = 0.0f; m.m13 = 0.0f; m.m14 = 1.0f;
        m.m21 = 0.0f; m.m22 = dy;   m.m23 = 0.0f; m.m24 = 1.0f;
        m._31 = 0.0f; m._32 = 0.0f; m._33 = dz;   m._34 = 1.0f;
        m._41 = 0.0f; m._42 = 0.0f; m._43 = 0.0f; m._44 = 1.0f;

        return(m);
    }

    public static Matrix4x4 RotateX(float degrees)
    {
        Matrix4x4 m = new Matrix4x4();

        float radians = degrees * DEGREES_TO_RADIANS;
        float fcos = (float)Math.cos(radians);
        float fsin = (float)Math.sin(radians);

        m.m11 = 1.0f; m.m12 = 0.0f; m.m13 = 0.0f;  m.m14 = 0.0f;
        m.m21 = 0.0f; m.m22 = fcos; m.m23 = -fsin; m.m24 = 0.0f;
        m._31 = 0.0f; m._32 = fsin; m._33 = fcos;  m._34 = 0.0f;
        m._41 = 0.0f; m._42 = 0.0f; m._43 = 0.0f;  m._44 = 1.0f;

        return(m);
    }

    public static Matrix4x4 RotateY(float degrees)
    {
        Matrix4x4 m = new Matrix4x4();

        float radians = degrees * DEGREES_TO_RADIANS;
        float fcos = (float)Math.cos(radians);
        float fsin = (float)Math.sin(radians);

        m.m11 = fcos;  m.m12 = 0.0f; m.m13 = fsin; m.m14 = 0.0f;
        m.m21 = 0.0f;  m.m22 = 1.0f; m.m23 = 0.0f; m.m24 = 0.0f;
        m._31 = -fsin; m._32 = 0.0f; m._33 = fcos; m._34 = 0.0f;
        m._41 = 0.0f;  m._42 = 0.0f; m._43 = 0.0f; m._44 = 1.0f;

        return(m);
    }

    public static Matrix4x4 RotateZ(float degrees)
    {
        Matrix4x4 m = new Matrix4x4();

        float radians = degrees * DEGREES_TO_RADIANS;
        float fcos = (float)Math.cos(radians);
        float fsin = (float)Math.sin(radians);

        m.m11 = fcos; m.m12 = -fsin; m.m13 = 0.0f; m.m14 = 0.0f;
        m.m21 = fsin; m.m22 = fcos;  m.m23 = 0.0f; m.m24 = 0.0f;
        m._31 = 0.0f; m._32 = 0.0f;  m._33 = 1.0f; m._34 = 0.0f;
        m._41 = 0.0f; m._42 = 0.0f;  m._43 = 0.0f; m._44 = 1.0f;

        return(m);
    }

    public static Matrix4x4 Multiply(Matrix4x4 m1, Matrix4x4 m2)
    {
        Matrix4x4 m = new Matrix4x4();

        m.m11 = m1.m11 *m2.m11 + m1.m12 *m2.m21 + m1.m13 *m2._31 + m1.m14 *m2._41;
        m.m12 = m1.m11 *m2.m12 + m1.m12 *m2.m22 + m1.m13 *m2._32 + m1.m14 *m2._42;
        m.m13 = m1.m11 *m2.m13 + m1.m12 *m2.m23 + m1.m13 *m2._33 + m1.m14 *m2._43;
        m.m14 = m1.m11 *m2.m14 + m1.m12 *m2.m24 + m1.m13 *m2._34 + m1.m14 *m2._44;

        m.m21 = m1.m21 *m2.m11 + m1.m22 *m2.m21 + m1.m23 *m2._31 + m1.m24 *m2._41;
        m.m22 = m1.m21 *m2.m12 + m1.m22 *m2.m22 + m1.m23 *m2._32 + m1.m24 *m2._42;
        m.m23 = m1.m21 *m2.m13 + m1.m22 *m2.m23 + m1.m23 *m2._33 + m1.m24 *m2._43;
        m.m24 = m1.m21 *m2.m14 + m1.m22 *m2.m24 + m1.m23 *m2._34 + m1.m24 *m2._44;

        m._31 = m1._31*m2.m11 + m1._32*m2.m21 + m1._33*m2._31 + m1._34*m2._41;
        m._32 = m1._31*m2.m12 + m1._32*m2.m22 + m1._33*m2._32 + m1._34*m2._42;
        m._33 = m1._31*m2.m13 + m1._32*m2.m23 + m1._33*m2._33 + m1._34*m2._43;
        m._34 = m1._31*m2.m14 + m1._32*m2.m24 + m1._33*m2._34 + m1._34*m2._44;

        m._41 = m1._41*m2.m11 + m1._42*m2.m21 + m1._43*m2._31 + m1._44*m2._41;
        m._42 = m1._41*m2.m12 + m1._42*m2.m22 + m1._43*m2._32 + m1._44*m2._42;
        m._43 = m1._41*m2.m13 + m1._42*m2.m23 + m1._43*m2._33 + m1._44*m2._43;
        m._44 = m1._41*m2.m14 + m1._42*m2.m24 + m1._43*m2._34 + m1._44*m2._44;

        return(m);
    }

    public static Matrix4x4 LookAt(Vector4D eye, Vector4D at, Vector4D up)
    {
        Matrix4x4 m = new Matrix4x4();
        Vector4D n = new Vector4D();
        Vector4D u = new Vector4D();
        Vector4D v = new Vector4D();

        n.setTo(eye);
        n.subtract(at);
        n.normalize();

        u.calculateCrossProduct(up, n);
        u.normalize();

        v.calculateCrossProduct(n, u);
        v.normalize();

        m.m11 = u.x;  m.m12 = u.y;  m.m13 = u.z;  m.m14 = -eye.calculateDotProduct(u);
        m.m21 = v.x;  m.m22 = v.y;  m.m23 = v.z;  m.m24 = -eye.calculateDotProduct(v);
        m._31 = n.x;  m._32 = n.y;  m._33 = n.z;  m._34 = -eye.calculateDotProduct(n);
        m._41 = 0.0f; m._42 = 0.0f; m._43 = 0.0f; m._44 = 1.0f;

        if(flag1)
        {
            System.out.println("View Matrix Begin");
            System.out.printf("%f %f %f %f\n", m.m11, m.m12, m.m13, m.m14);
            System.out.printf("%f %f %f %f\n", m.m21, m.m22, m.m23, m.m24);
            System.out.printf("%f %f %f %f\n", m._31, m._32, m._33, m._34);
            System.out.printf("%f %f %f %f\n", m._41, m._42, m._43, m._44);
            System.out.println("View Matrix End");
            flag1 = false;
        }

        return(m);
    }

    public static Matrix4x4 Ortho(float left, float right, float bottom, float top, float near, float far)
    {
        Matrix4x4 m = new Matrix4x4();

        float a = 2.0f/(right - left);
        float b = -(right + left)/(right - left);
        float c = 2.0f/(top - bottom);
        float d = -(top + bottom)/(top - bottom);
        float e = -2.0f/(far - near);
        float f = -(far + near)/(far - near);

        m.m11 = a;    m.m12 = 0.0f; m.m13 = 0.0f; m.m14 = b;
        m.m21 = 0.0f; m.m22 = c;    m.m23 = 0.0f; m.m24 = d;
        m._31 = 0.0f; m._32 = 0.0f; m._33 = e;    m._34 = f;
        m._41 = 0.0f; m._42 = 0.0f; m._43 = 0.0f; m._44 = 1.0f;

        return(m);
    }

    public static Matrix4x4 Frustum(float left, float right, float bottom, float top, float near, float far)
    {
        Matrix4x4 m = new Matrix4x4();

        float a = 2.0f * near/(right - left);
        float b = (right + left)/(right - left);
        float c = 2.0f * near/(top - bottom);
        float d = (top + bottom)/(top - bottom);
        float e = -(far + near)/(far - near);
        float f = -2.0f * near * far/(far - near);

        m.m11 = a;    m.m12 = 0.0f; m.m13 = b;     m.m14 = 0.0f;
        m.m21 = 0.0f; m.m22 = c;    m.m23 = d;     m.m24 = 0.0f;
        m._31 = 0.0f; m._32 = 0.0f; m._33 = e;     m._34 = f;
        m._41 = 0.0f; m._42 = 0.0f; m._43 = -1.0f; m._44 = 0.0f;

        if(flag2)
        {
            System.out.println("Perspective Matrix Begin");
            System.out.printf("%f %f %f %f\n", m.m11, m.m12, m.m13, m.m14);
            System.out.printf("%f %f %f %f\n", m.m21, m.m22, m.m23, m.m24);
            System.out.printf("%f %f %f %f\n", m._31, m._32, m._33, m._34);
            System.out.printf("%f %f %f %f\n", m._41, m._42, m._43, m._44);
            System.out.println("Perspective Matrix End");
            flag2 = false;
        }

        return(m);
    }

    public static Matrix4x4 Perspective(float degrees, float aspectRatio, float near, float far)
    {
        float radians = degrees * 3.14159f/180.0f;

        float top = near * (float)Math.tan(radians/2);
        float bottom = -near * (float)Math.tan(radians/2);
        float right = top * aspectRatio;
        float left = bottom * aspectRatio;

        return(Frustum(left, right, bottom, top, near, far));
    }

    public static Matrix4x4 Viewport(float left, float right, float bottom, float top)
    {
        Matrix4x4 m = new Matrix4x4();

        float V_r = right;
        float V_l = left;
        float V_t = top;
        float V_b = bottom;

        float A = (V_r - V_l)/(W_r - W_l);
        float B = (V_t - V_b)/(W_t - W_b);
        float C = V_l - A * W_l;
        float D = V_b - B * W_b;

        m.m11 = A;    m.m12 = 0.0f; m.m13 = 0.0f; m.m14 = C;
        m.m21 = 0.0f; m.m22 = B;    m.m23 = 0.0f; m.m24 = D;
        m._31 = 0.0f; m._32 = 0.0f; m._33 = 1.0f; m._34 = 0.0f;
        m._41 = 0.0f; m._42 = 0.0f; m._43 = 0.0f; m._44 = 1.0f;

        if(flag3)
        {
            System.out.println("Viewport Matrix Begin");
            System.out.printf("%f %f %f %f\n", m.m11, m.m12, m.m13, m.m14);
            System.out.printf("%f %f %f %f\n", m.m21, m.m22, m.m23, m.m24);
            System.out.printf("%f %f %f %f\n", m._31, m._32, m._33, m._34);
            System.out.printf("%f %f %f %f\n", m._41, m._42, m._43, m._44);
            System.out.println("Viewport Matrix End");
            flag3 = false;
        }

        return(m);
    }

    public static void Copy(Matrix4x4 dist, Matrix4x4 src)
    {
        dist.m11 = src.m11;
        dist.m12 = src.m12;
        dist.m13 = src.m13;
        dist.m14 = src.m14;

        dist.m21 = src.m21;
        dist.m22 = src.m22;
        dist.m23 = src.m23;
        dist.m24 = src.m24;

        dist._31 = src._31;
        dist._32 = src._32;
        dist._33 = src._33;
        dist._34 = src._34;

        dist._41 = src._41;
        dist._42 = src._42;
        dist._43 = src._43;
        dist._44 = src._44;
    }
}

