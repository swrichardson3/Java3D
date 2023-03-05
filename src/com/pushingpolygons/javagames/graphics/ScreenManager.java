// ScreenManager.java
package com.pushingpolygons.javagames.graphics;
import java.awt.DisplayMode;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import javax.swing.JFrame;

public class ScreenManager
{
    private GraphicsDevice device;

    public ScreenManager()
    {
        GraphicsEnvironment environment = GraphicsEnvironment.getLocalGraphicsEnvironment();
        device = environment.getDefaultScreenDevice();
    }

    // Returns the first compatible display mode in a list of display modes.
    // If no display modes are compatible, then it returns null.
    public DisplayMode findCompatibleDisplayMode(DisplayMode displayModes[])
    {
        DisplayMode goodDisplayModes[] = device.getDisplayModes();

        for(int i = 0; i < displayModes.length; i++)
        {
            for(int j = 0; j < goodDisplayModes.length; j++)
            {
                if(displayModesMatch(displayModes[i], goodDisplayModes[j]))
                {
                    return(displayModes[i]);
                }
            }
        }

        return null;
    }

    // Determines if two display modes match. Two display modes match if they have
    // the same resolution, bit depth, and refresh rate. The bit depth is ignored if
    // one of the display modes has a bit depth of DisplayMode.BIT_DEPTH_MULTI. Likewise,
    // the refresh rate is ignored if one of the display modes has a refresh rate of
    // DisplayMode.REFRESH_RATE_UNKNOWN.
    public boolean displayModesMatch(DisplayMode displayMode1, DisplayMode displayMode2)
    {
        if (displayMode1.getWidth() != displayMode2.getWidth() ||
                displayMode1.getHeight() != displayMode2.getHeight())
        {
            return false;
        }

        if (displayMode1.getBitDepth() != DisplayMode.BIT_DEPTH_MULTI &&
                displayMode2.getBitDepth() != DisplayMode.BIT_DEPTH_MULTI &&
                displayMode1.getBitDepth() != displayMode2.getBitDepth())
        {
            return false;
        }

        if (displayMode1.getRefreshRate() != DisplayMode.REFRESH_RATE_UNKNOWN &&
                displayMode2.getRefreshRate() != DisplayMode.REFRESH_RATE_UNKNOWN &&
                displayMode1.getRefreshRate() != displayMode2.getRefreshRate())
        {
            return false;
        }

        return true;
    }

    // Returns a list of compatible display modes for
    // the default device on the system.
    public DisplayMode[] getCompatibleDisplayModes()
    {
        return device.getDisplayModes();
    }

    // Returns the current display mode.
    public DisplayMode getCurrentDisplayMode()
    {
        return device.getDisplayMode();
    }

    // Enters full mScreen mode and changes the display mode. If the specified
    // display mode is null or not compatible with this device, or if the
    // display mode cannot be changed on this system, the current display
    // mode is used. The display uses a BufferStrategy with 2 buffers.
    public void setFullScreen(DisplayMode displayMode)
    {
        JFrame frame = new JFrame();
        frame.setUndecorated(true);
        frame.setIgnoreRepaint(true);
        frame.setResizable(false);

        device.setFullScreenWindow(frame);

        if((displayMode != null) && (device.isDisplayChangeSupported()))
        {
            try
            {
                device.setDisplayMode(displayMode);
            }

            catch(IllegalArgumentException ex)
            {

            }
        }

        frame.createBufferStrategy(2);
    }

    // Gets the graphics context for the display. The ScreenManager uses
    // double buffering, so applications must call update() to show any
    // graphics drawn. The application must dispose of the graphics object.
    public Graphics2D getGraphics()
    {
        Window window = device.getFullScreenWindow();

        if (window != null)
        {
            BufferStrategy strategy = window.getBufferStrategy();
            return (Graphics2D)strategy.getDrawGraphics();
        }

        else
        {
            return null;
        }
    }

    // Updates the display.
    public void update()
    {
        Window window = device.getFullScreenWindow();

        if (window != null)
        {
            BufferStrategy strategy = window.getBufferStrategy();

            if (!strategy.contentsLost())
            {
                strategy.show();
            }
        }

        // Sync the display on some systems.
        // On Linux, this fixes event queue problems.
        Toolkit.getDefaultToolkit().sync();
    }

    // Returns the window currently used in full mScreen mode.
    // Returns null if the device is not in full mScreen mode.
    public Window getFullScreenWindow()
    {
        return(device.getFullScreenWindow());
    }

    //Returns the width of the window currently used in full
    //mScreen mode. Returns 0 if the device is not in full
    //mScreen mode.
    public int getWidth()
    {
        Window window = device.getFullScreenWindow();

        if (window != null)
        {
            return window.getWidth();
        }

        else
        {
            return 0;
        }
    }

    //Returns the height of the window currently used in full
    //mScreen mode. Returns 0 if the device is not in full
    //mScreen mode.
    public int getHeight()
    {
        Window window = device.getFullScreenWindow();

        if (window != null)
        {
            return window.getHeight();
        }

        else
        {
            return 0;
        }
    }

    // Restore the mScreen's display mode.
    public void restoreScreen()
    {
        Window window = device.getFullScreenWindow();

        if(window != null)
        {
            window.dispose();
        }

        device.setFullScreenWindow(null);
    }

    // Creates an image compatible with the current display.
    public BufferedImage createCompatibleImage(int w, int h, int transparency)
    {
        Window window = device.getFullScreenWindow();

        if (window != null)
        {
            GraphicsConfiguration gc = window.getGraphicsConfiguration();
            return gc.createCompatibleImage(w, h, transparency);
        }

        return null;
    }
}
