// MoreMath.java
package com.pushingpolygons.javagames.util;

public class MoreMath
{
    public MoreMath()
    {

    }

    public static int floor(float f)
    {
        if(f >= 0)
        {
            return((int)f);
        }

        else
        {
            return((int)f - 1);
        }
    }

    public static int ceil(float f)
    {
        if(f > 0)
        {
            return((int)f + 1);
        }

        else
        {
            return((int)f);
        }
    }
}

