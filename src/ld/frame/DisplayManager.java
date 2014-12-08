package ld.frame;

import static org.lwjgl.opengl.GL11.*;

import java.awt.Dimension;
import java.awt.Toolkit;

import org.lwjgl.LWJGLException;
import org.lwjgl.Sys;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;

public class DisplayManager
{
	private static int fps, delta, FPS_CAP;
	private static String TITLE;
	
	private static Timer timer;
	
	public static void createDisplay(int width, int height)
	{
		createDisplay(width, height, "");
	}
	
	public static void createDisplay(int width, int height, String title)
	{
		try
		{
			Display.setDisplayMode(new DisplayMode(width, height));
			Display.setResizable(false);
			Display.setTitle(title);
			TITLE = title;
			
			Display.create();
			createProjection();
			initTimer();
		}
		catch (LWJGLException e)
		{
			e.printStackTrace();
		}
	}
	
	public static void initTimer()
	{
		timer = new Timer();
		timer.getTime();
		timer.getDelta();
		timer.updateFPS();
		
	}
	
	public static void createProjection()
	{
		glEnable(GL_TEXTURE_2D);
		glEnable(GL_BLEND);
		
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA); 
		
        glViewport(0, 0, Display.getWidth(), Display.getHeight());
	    glMatrixMode(GL_PROJECTION);
	    glLoadIdentity();
	    glOrtho(0, Display.getWidth(), 0, Display.getHeight(),1, -1);
	    glMatrixMode(GL_MODELVIEW);
	    glLoadIdentity();
	}
	
	public static void updateDisplay()
	{
		if(Display.wasResized())
			createProjection();
		
		timer.updateFPS();
		fps = timer.getFPS();
		delta = timer.getDelta();
		
		Display.setTitle(TITLE + " | FPS: " + fps + " , DELTA: " + delta);
		
		Display.sync(FPS_CAP);
		Display.update();
		clearDisplay();
	}
	
	public static void clearDisplay()
	{
		glClear(GL_COLOR_BUFFER_BIT);
	}

	public static void setWindowSize(int width, int height)
	{
		try
		{
			Display.setDisplayMode(new DisplayMode(width, height));
			createProjection();
		}
		catch (LWJGLException e)
		{
			e.printStackTrace();
		}
	}
	
	public static double getMonitorWidth()
	{
		return (int) Toolkit.getDefaultToolkit().getScreenSize().getWidth();
	}

	public static double getMonitorHeight()
	{
		return (int)Toolkit.getDefaultToolkit().getScreenSize().getHeight();
	}
	
	public static void capFPS(int cap)
	{
		FPS_CAP = cap;
	}
	
	public static int getFPS()
	{
		return fps;
	}
	
	public static int getDelta()
	{
		return delta;
	}
}

class Timer
{

	long lastFrame;

	int fps;
	long lastFPS;
	
	int delta;
	
	int fpsToReturn;
	
	

	public Timer()
	{
		lastFPS = getTime();
	}

	public long getTime()
	{
		return (Sys.getTime() * 1000) / Sys.getTimerResolution();
	}

	public int getDelta()
	{
		long time = getTime();
		delta = (int) (time - lastFrame);
		lastFrame = time;

		return delta;
	}

	public void updateFPS()
	{
		if (getTime() - lastFPS > 1000)
		{			
			fpsToReturn = fps;
			
			fps = 0;
			lastFPS += 1000;
		}
		fps++;
	}

	public int getFPS()
	{
		return fpsToReturn;
	}
}
