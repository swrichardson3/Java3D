// InputManager.java
package com.pushingpolygons.javagames.input;
import java.awt.*;
import java.awt.event.*;
import java.util.List;
import java.util.ArrayList;
import javax.swing.SwingUtilities;

// The InputManger Class manages input of key and mouse events.
// Events are mapped to GameActions.
public class InputManager implements KeyListener, MouseListener,
        MouseMotionListener, MouseWheelListener
{
    // An invisible cursor.
    public static final Cursor INVISIBLE_CURSOR =
            Toolkit.getDefaultToolkit().createCustomCursor(
                    Toolkit.getDefaultToolkit().getImage(""),
                    new Point(0, 0),
                    "invisible");

    // Mouse codes.
    private static final int NUMBER_MOUSE_CODES = 9;
    public static final int MOUSE_MOVE_LEFT = 0;
    public static final int MOUSE_MOVE_RIGHT = 1;
    public static final int MOUSE_MOVE_UP = 2;
    public static final int MOUSE_MOVE_DOWN = 3;
    public static final int MOUSE_WHEEL_UP = 4;
    public static final int MOUSE_WHEEL_DOWN = 5;
    public static final int MOUSE_BUTTON_1 = 6;
    public static final int MOUSE_BUTTON_2 = 7;
    public static final int MOUSE_BUTTON_3 = 8;

    // key codes are defined in java.awt.KeyEvent.
    // most of the codes are less than 600.
    private static final int NUMBER_KEY_CODES = 600;

    private GameAction[] keyActions = new GameAction[NUMBER_KEY_CODES];
    private GameAction[] mouseActions = new GameAction[NUMBER_MOUSE_CODES];

    private Point mouseLocation;
    private Point centerLocation;
    private Component component;
    private Robot robot;
    private boolean isRecentering;

    // creates a new InputManager that listens to
    // input from the specified component.
    public InputManager(Component component)
    {
        this.component = component;
        this.mouseLocation = new Point();
        this.centerLocation = new Point();

        // Register key and mouse listeners.
        component.addKeyListener(this);
        component.addMouseListener(this);
        component.addMouseMotionListener(this);
        component.addMouseWheelListener(this);

        // Allow input of the TAB key and other keys
        // normally used for focus traversal.
        component.setFocusTraversalKeysEnabled(false);
    }

    public void setCursor(Cursor cursor)
    {
        component.setCursor(cursor);
    }

    // Sets whether relative mouse mode is on or not. For relative
    // mouse mode, the mouse is locked in the center of the mScreen,
    // and only the changed in mouse movement is measured. In normal
    // mode, the mouse is free to move about the mScreen.
    public void setRelativeMouseMode(boolean mode)
    {
        if (mode == isRelativeMouseMode())
        {
            return;
        }

        if(mode)
        {
            try
            {
                robot = new Robot();
                recenterMouse();
            }

            catch(AWTException ex)
            {
                // couldn't create robot!
                robot = null;
            }
        }

        else
        {
            robot = null;
        }
    }

    // Returns whether or not relative mouse mode is on.
    public boolean isRelativeMouseMode()
    {
        return(robot != null);
    }

    // Maps a GameAction to a specific key. The key codes are defined
    // in java.awt.KeyEvent. If the key already has a GameAction mapped
    // mapped to it, then the new GameAction overwrites it.
    public void mapToKey(GameAction gameAction, int keyCode)
    {
        keyActions[keyCode] = gameAction;
    }

    // Maps a GameAction to a specific mouse action. The mouse codes are
    // defined here in InputManager. If the mouse action already has
    // a GameAction mapped to it, then the new GameAction overwrites it.
    public void mapToMouse(GameAction gameAction, int mouseCode)
    {
        mouseActions[mouseCode] = gameAction;
    }

    // Clears all mapped keys and mouse actions to this GameAction.
    public void clearMap(GameAction gameAction)
    {
        for(int i = 0; i < keyActions.length; i++)
        {
            if(keyActions[i] == gameAction)
            {
                keyActions[i] = null;
            }
        }

        for(int i = 0; i < mouseActions.length; i++)
        {
            if(mouseActions[i] == gameAction)
            {
                mouseActions[i] = null;
            }
        }

        gameAction.reset();
    }

    // Gets a List of names of the keys and mouse actions mapped
    // to this GameAction. Each entry in the List is a String.
    public List<String> getMaps(GameAction gameCode)
    {
        ArrayList<String> list = new ArrayList<String>();

        for(int i = 0; i <keyActions.length; i++)
        {
            if(keyActions[i] == gameCode)
            {
                list.add(getKeyName(i));
            }
        }

        for(int i = 0; i < mouseActions.length; i++)
        {
            if(mouseActions[i] == gameCode)
            {
                list.add(getMouseName(i));
            }
        }
        return list;
    }

    // Resets all GameActions so they appear like they have
    // not been pressed.
    public void resetAllGameActions()
    {
        for(int i = 0; i < keyActions.length; i++)
        {
            if (keyActions[i] != null)
            {
                keyActions[i].reset();
            }
        }

        for(int i = 0; i < mouseActions.length; i++)
        {
            if (mouseActions[i] != null)
            {
                mouseActions[i].reset();
            }
        }
    }

    // Gets the name of a key code.
    public static String getKeyName(int keyCode)
    {
        return KeyEvent.getKeyText(keyCode);
    }

    // Gets the name of a mouse code.
    public static String getMouseName(int mouseCode)
    {
        switch (mouseCode)
        {
            case MOUSE_MOVE_LEFT:
                return "Mouse Left";
            case MOUSE_MOVE_RIGHT:
                return "Mouse Right";
            case MOUSE_MOVE_UP:
                return "Mouse Up";
            case MOUSE_MOVE_DOWN:
                return "Mouse Down";
            case MOUSE_WHEEL_UP:
                return "Mouse Wheel Up";
            case MOUSE_WHEEL_DOWN:
                return "Mouse Wheel Down";
            case MOUSE_BUTTON_1:
                return "Mouse Button 1";
            case MOUSE_BUTTON_2:
                return "Mouse Button 2";
            case MOUSE_BUTTON_3:
                return "Mouse Button 3";
            default:
                return "Unknown mouse code " + mouseCode;
        }
    }

    // Gets the x position of the mouse.
    public int getMouseX()
    {
        return mouseLocation.x;
    }

    // Gets the y position of the mouse.
    public int getMouseY()
    {
        return mouseLocation.y;
    }

    // Uses the Robot class to try to position the mouse in the
    // center of the mScreen.
    private synchronized void recenterMouse()
    {
        if (robot != null && component.isShowing())
        {
            centerLocation.x = component.getWidth()/2;
            centerLocation.y = component.getHeight()/2;
            SwingUtilities.convertPointToScreen(centerLocation, component);
            isRecentering = true;
            robot.mouseMove(centerLocation.x, centerLocation.y);
        }
    }

    private GameAction getKeyAction(KeyEvent e)
    {
        int keyCode = e.getKeyCode();

        if(keyCode < keyActions.length)
        {
            return keyActions[keyCode];
        }

        else
        {
            return null;
        }
    }

    private GameAction getMouseButtonAction(MouseEvent e)
    {
        int mouseCode = getMouseButtonCode(e);

        if (mouseCode != -1)
        {
            return mouseActions[mouseCode];
        }

        else
        {
            return null;
        }
    }

    // Gets the mouse code for the button specified in this MouseEvent.
    public static int getMouseButtonCode(MouseEvent e)
    {
        switch (e.getButton())
        {
            case MouseEvent.BUTTON1:
                return MOUSE_BUTTON_1;
            case MouseEvent.BUTTON2:
                return MOUSE_BUTTON_2;
            case MouseEvent.BUTTON3:
                return MOUSE_BUTTON_3;
            default:
                return -1;
        }
    }

    // From the KeyListener interface.
    public void keyPressed(KeyEvent e)
    {
        GameAction gameAction = getKeyAction(e);

        if (gameAction != null)
        {
            gameAction.press();
        }
        // Make sure the key is not processed for anything else.
        e.consume();
    }

    // From the KeyListener interface.
    public void keyReleased(KeyEvent e)
    {
        GameAction gameAction = getKeyAction(e);

        if (gameAction != null)
        {
            gameAction.release();
        }

        // Make sure the key is not processed for anything else.
        e.consume();
    }

    // From the KeyListener interface.
    public void keyTyped(KeyEvent e)
    {
        // Make sure the key is not processed for anything else.
        e.consume();
    }

    // From the MouseListener interface.
    public void mousePressed(MouseEvent e)
    {
        GameAction gameAction = getMouseButtonAction(e);

        if (gameAction != null)
        {
            gameAction.press();
        }
    }

    // From the MouseListener interface.
    public void mouseReleased(MouseEvent e)
    {
        GameAction gameAction = getMouseButtonAction(e);

        if (gameAction != null)
        {
            gameAction.release();
        }
    }

    // From the MouseListener interface.
    public void mouseClicked(MouseEvent e)
    {
        // Do nothing.
    }

    // From the MouseListener interface.
    public void mouseEntered(MouseEvent e)
    {
        mouseMoved(e);
    }

    // From the MouseListener interface.
    public void mouseExited(MouseEvent e)
    {
        mouseMoved(e);
    }

    // From the MouseMotionListener interface
    public void mouseDragged(MouseEvent e)
    {
        mouseMoved(e);
    }

    // From the MouseMotionListener interface.
    public synchronized void mouseMoved(MouseEvent e)
    {
        // This event is from re-centering the mouse.Ignore it.
        if (isRecentering && centerLocation.x == e.getX() && centerLocation.y == e.getY())
        {
            isRecentering = false;
        }

        else
        {
            int dx = e.getX() - mouseLocation.x;
            int dy = e.getY() - mouseLocation.y;
            mouseHelper(MOUSE_MOVE_LEFT, MOUSE_MOVE_RIGHT, dx);
            mouseHelper(MOUSE_MOVE_UP, MOUSE_MOVE_DOWN, dy);

            if (isRelativeMouseMode())
            {
                recenterMouse();
            }
        }

        mouseLocation.x = e.getX();
        mouseLocation.y = e.getY();
    }

    // From the MouseWheelListener interface.
    public void mouseWheelMoved(MouseWheelEvent e)
    {
        mouseHelper(MOUSE_WHEEL_UP, MOUSE_WHEEL_DOWN,
                e.getWheelRotation());
    }

    private void mouseHelper(int codeNeg, int codePos, int amount)
    {
        GameAction gameAction;

        if (amount < 0)
        {
            gameAction = mouseActions[codeNeg];
        }

        else
        {
            gameAction = mouseActions[codePos];
        }

        if(gameAction != null)
        {
            gameAction.press(Math.abs(amount));
            gameAction.release();
        }
    }
}

