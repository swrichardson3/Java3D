// GameCore.java
package com.pushingpolygons.javagames.test;
import java.awt.*;
import javax.swing.ImageIcon;
import com.pushingpolygons.javagames.graphics.ScreenManager;

public abstract class GameCore
{
    protected static final int FONT_SIZE = 24;
    private static final DisplayMode mDISPLAYMODES[] = {new DisplayMode(2560, 1440, 32, 0)};
    protected ScreenManager mScreen;
    private boolean mIsRunning;

    public GameCore()
    {

    }

    public void run()
    {
        try
        {
            init();
            gameLoop();
        }

        finally
        {
            if(mScreen != null)
            {
                mScreen.restoreScreen();
            }

            lazilyExit();
        }
    }

    // Exits the VM from a daemon thread. The daemon thread waits
    // 2 seconds then calls System.mExit(0). Since the VM should
    // mExit when only daemon threads are running, this makes sure
    // System.mExit(0) is only called if necessary. It is necessary
    // if the Java Sound system is running.
    public void lazilyExit()
    {
        Thread thread = new Thread()
        {
            public void run()
            {
                // First wait for the VM to mExit on its own.
                try
                {
                    Thread.sleep(2000);
                }

                catch (InterruptedException ex) { }

                // System is still running, so force an mExit.
                System.exit(0);
            }
        };

        thread.setDaemon(true);
        thread.start();
    }

    public void init()
    {
        mScreen = new ScreenManager();
        DisplayMode displayMode = mScreen.findCompatibleDisplayMode(mDISPLAYMODES);
        mScreen.setFullScreen(displayMode);

        Window window = mScreen.getFullScreenWindow();
        window.setFont(new Font("Dialog", Font.PLAIN, FONT_SIZE));
        window.setBackground(Color.blue);
        window.setForeground(Color.white);

        mIsRunning = true;
    }

    // Run the game loop until stop() is called.
    public void gameLoop()
    {
        long startTime = System.currentTimeMillis();
        long currentTime = startTime;

        while(mIsRunning)
        {
            long elapsedTime = System.currentTimeMillis() - currentTime;
            currentTime += elapsedTime;

            update(elapsedTime);

            Graphics2D g = mScreen.getGraphics();
            draw(g);
            g.dispose();
            mScreen.update();

            // Do not take a nap. Run as fast as possible.
			/*try
			{
				Thread.sleep(20);
			}

			catch(InterruptedException ex)
			{

			}*/
        }
    }

    public void stop()
    {
        mIsRunning = false;
    }

    public Image loadImage(String fileName)
    {
        ImageIcon imageIcon = new ImageIcon(fileName);
        Image image = imageIcon.getImage();
        return(image);
    }

    public void update(long elapsedTime)
    {
        // Do nothing.
    }

    public abstract void draw(Graphics2D g);
}


