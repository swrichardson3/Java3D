// MyClass.java
import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.List;
import java.util.ArrayList;
import com.pushingpolygons.javagames.graphics3D.*;
import com.pushingpolygons.javagames.input.*;
import com.pushingpolygons.javagames.test.GameCore;
import com.pushingpolygons.javagames.math3D.*;

class Test extends GameCore
{
    protected GameAction mExit;
    //protected GameAction mMoveLeft;
    //protected GameAction mMoveRight;
    protected GameAction mPause;
    protected InputManager mInputManager;
    protected PolygonRenderer mPolygonRenderer;
    protected Camera mCamera;
    protected List<Polygon3D> mPolygon;

    private Transform mTransform = new Transform();
    private Image mBackgroundImage;
    private boolean mPaused;

    private boolean mDrawFrameRate = false;
    private boolean mDrawInstructions = true;

    // For calculating frame rate
    private int mNumFrames;
    private long mStartTime;
    private float mFrameRate;

    public Test()
    {

    }

    public void init()
    {
        super.init();

        Window window = mScreen.getFullScreenWindow();
        mInputManager = new InputManager(window);
        mInputManager.setRelativeMouseMode(true);
        mInputManager.setCursor(InputManager.INVISIBLE_CURSOR);

        mCamera = new Camera(new Vector4D(600.0f, 100.0f, 0.0f), new Vector4D(200.0f, 100.0f, -1200.0f), new Vector4D(0.0f, 1.0f, 0.0f));

        createGameActions();
        createPolygonRender(0, 0, mScreen.getWidth(), mScreen.getHeight());

        mPolygon = new ArrayList<Polygon3D>();
        createPolygons();
        mPaused = false;
    }

    // Creates GameActions and maps them to keys.
    public void createGameActions()
    {
        mExit = new GameAction("mExit", GameAction.DETECT_INITIAL_PRESS_ONLY);
        //mMoveLeft = new GameAction("moveLeft");
        //mMoveRight = new GameAction("moveRight");
        mPause = new GameAction("mPause", GameAction.DETECT_INITIAL_PRESS_ONLY);

        mInputManager.mapToKey(mExit, KeyEvent.VK_ESCAPE);
        mInputManager.mapToKey(mPause, KeyEvent.VK_P);

        // Move with the arrow keys.
        //mInputManager.mapToKey(mMoveLeft, KeyEvent.VK_LEFT);
        //mInputManager.mapToKey(mMoveRight, KeyEvent.VK_RIGHT);

        // Move with the A and D keys.
        //mInputManager.mapToKey(mMoveLeft, KeyEvent.VK_A);
        //mInputManager.mapToKey(mMoveRight, KeyEvent.VK_D);

        // Use these lines to map player movement to the mouse.
        //mInputManager.mapToMouse(mMoveLeft, mInputManager.MOUSE_MOVE_LEFT);
        //mInputManager.mapToMouse(mMoveRight, mInputManager.MOUSE_MOVE_RIGHT);
    }

    public void createPolygonRender(int left, int top, int width, int height)
    {
        mPolygonRenderer = new SolidPolygonRenderer(mCamera, left, top, width, height);
    }

    public void createPolygons()
    {
        SolidPolygon3D polygon;

        // Walls
        polygon = new SolidPolygon3D(
                new Vector4D(-200, 0, -1000),
                new Vector4D(200, 0, -1000),
                new Vector4D(200, 250, -1000),
                new Vector4D(-200, 250, -1000));
        polygon.setColor(Color.WHITE);
        mPolygon.add(polygon);
        polygon = new SolidPolygon3D(
                new Vector4D(-200, 0, -1400),
                new Vector4D(200, 0, -1400),
                new Vector4D(200, 250, -1400),
                new Vector4D(-200, 250, -1400));
        polygon.setColor(Color.WHITE);
        mPolygon.add(polygon);
        polygon = new SolidPolygon3D(
                new Vector4D(-200, 0, -1400),
                new Vector4D(-200, 0, -1000),
                new Vector4D(-200, 250, -1000),
                new Vector4D(-200, 250, -1400));
        polygon.setColor(Color.GRAY);
        mPolygon.add(polygon);
        polygon = new SolidPolygon3D(
                new Vector4D(200, 0, -1000),
                new Vector4D(200, 0, -1400),
                new Vector4D(200, 250, -1400),
                new Vector4D(200, 250, -1000));
        polygon.setColor(Color.GRAY);
        mPolygon.add(polygon);

        // Door and windows
        polygon = new SolidPolygon3D(
                new Vector4D(0, 0, -1000),
                new Vector4D(75, 0, -1000),
                new Vector4D(75, 125, -1000),
                new Vector4D(0, 125, -1000));
        polygon.setColor(new Color(0x660000));
        mPolygon.add(polygon);
        polygon = new SolidPolygon3D(
                new Vector4D(-150, 150, -1000),
                new Vector4D(-100, 150, -1000),
                new Vector4D(-100, 200, -1000),
                new Vector4D(-150, 200, -1000));
        polygon.setColor(new Color(0x660000));
        mPolygon.add(polygon);

        // Roof
        polygon = new SolidPolygon3D(
                new Vector4D(-200, 250, -1000),
                new Vector4D(200, 250, -1000),
                new Vector4D(75, 400, -1200),
                new Vector4D(-75, 400, -1200));
        polygon.setColor(new Color(0x660000));
        mPolygon.add(polygon);
        polygon = new SolidPolygon3D(
                new Vector4D(-200, 250, -1400),
                new Vector4D(-200, 250, -1000),
                new Vector4D(-75, 400, -1200));
        polygon.setColor(new Color(0x330000));
        mPolygon.add(polygon);
        polygon = new SolidPolygon3D(
                new Vector4D(200, 250, -1400),
                new Vector4D(-200, 250, -1400),
                new Vector4D(-75, 400, -1200),
                new Vector4D(75, 400, -1200));
        polygon.setColor(new Color(0x660000));
        mPolygon.add(polygon);
        polygon = new SolidPolygon3D(
                new Vector4D(200, 250, -1000),
                new Vector4D(200, 250, -1400),
                new Vector4D(75, 400, -1200));
        polygon.setColor(new Color(0x330000));
        mPolygon.add(polygon);
    }

    public boolean isPaused()
    {
        return mPaused;
    }

    public void setPaused(boolean p)
    {
        if(mPaused != p)
        {
            this.mPaused = p;
            mInputManager.resetAllGameActions();
        }
    }

    public void update(long elapsedTime)
    {
        // Check input that can happen whether mPaused or not.
        checkSystemInput();

        if(!isPaused())
        {
            // Check game input.
            checkGameInput();

            //mTransform.Identity();                    // m = I
            //mTransform.Translate(0.0f, 0.0f, 0.0f); // m = m * T = I * T
            //mTransform.RotateY(...);                // m = m * R = I * T * R
        }
    }

    // Checks input from GameActions that can be pressed
    // regardless of whether the game is mPaused or not.
    public void checkSystemInput()
    {
        if (mPause.isPressed())
        {
            setPaused(!isPaused());
        }

        if (mExit.isPressed())
        {
            stop();
        }
    }

    //Checks input from GameActions that can be pressed
    //only when the game is not mPaused.
    public void checkGameInput()
    {


    }

    public void draw(Graphics2D g)
    {
        mPolygonRenderer.startFrame(g);

        for(int i = 0; i < mPolygon.size(); i++)
        {
            mPolygonRenderer.draw(g, (Polygon3D) mPolygon.get(i));
        }

        mPolygonRenderer.endFrame(g);

        drawText(g);
    }

    public void drawText(Graphics g)
    {

    }

    public void calculateFrameRate()
    {

    }
}

public class MyClass
{
    public static void main(String[] args)
    {
        Test test;
        test = new Test();
        test.run();
    }
}

