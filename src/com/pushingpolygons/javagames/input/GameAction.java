// GameAction.java
package com.pushingpolygons.javagames.input;

public class GameAction
{
    public static final int NORMAL = 0;
    public static final int DETECT_INITIAL_PRESS_ONLY = 1;
    private static final int STATE_RELEASED = 0;
    private static final int STATE_PRESSED = 1;
    private static final int STATE_WAITING_FOR_RELEASE = 2;

    private String name;
    private int behavior;
    private int amount;
    private int state;

    public GameAction(String name)
    {
        this.name = name;
    }

    public GameAction(String name, int behavior)
    {
        this.name = name;
        this.behavior = behavior;
        reset();
    }

    public String getName()
    {
        return name;
    }

    public void reset()
    {
        state = STATE_RELEASED;
        amount = 0;
    }

    public synchronized void tap()
    {
        press();
        release();
    }

    // Signals that the key was pressed.
    public synchronized void press()
    {
        press(1);
    }

    // Signals that the key was pressed a specific number
    // of times, or that the mouse moved a specified distance.
    public synchronized void press(int amount)
    {
        if(state != STATE_WAITING_FOR_RELEASE)
        {
            this.amount += amount;
            state = STATE_PRESSED;
        }
    }

    // Signals that the key was released.
    public synchronized void release()
    {
        state = STATE_RELEASED;
    }

    // Returns whether the key was pressed or not since last checked.
    public synchronized boolean isPressed()
    {
        return(getAmount() != 0);
    }

    // For keys, this is the number of times the key was pressed since it
    // was last checked. For mouse movement, this is the distance moved.
    public synchronized int getAmount()
    {
        int retVal = amount;

        if (retVal != 0)
        {
            if (state == STATE_RELEASED)
            {
                amount = 0;
            }

            else if (behavior == DETECT_INITIAL_PRESS_ONLY)
            {
                state = STATE_WAITING_FOR_RELEASE;
                amount = 0;
            }
        }

        return retVal;
    }
}


