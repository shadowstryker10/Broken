package ld.game;

import ld.frame.DisplayManager;
import ld.graphics.Tile;
import ld.io.Preferences;
import ld.main.titlescreen.ExplanatoryScreen;

public class Game
{
	public static final int IN_GAME = 0, IN_EXPLAIN = 1;
	public static final int[] RESOLUTIONS = new int[]{48, 64, 70, 80, 90};
	
	public float red, green, blue;
	
	public static int currentResolution = RESOLUTIONS[1];
	
	private int classState;
	
	Dungeon dungeon;
	ExplanatoryScreen explain_screen;
	
	public Game()
	{
		explain_screen = new ExplanatoryScreen();
		dungeon = new Dungeon(16, 10);
		classState = IN_EXPLAIN;
		red = 0.15f;
		blue = 0.15f;
		green = 0.15f;
		
		
		changeResolution(RESOLUTIONS[Preferences.preferredResolution]);
	}
	
	public void update()
	{
		if(classState == IN_EXPLAIN)
		{
			explain_screen.update();
			if(explain_screen.isReturnRequested())
			{
				explain_screen.returnRequested = false;
				classState = IN_GAME;
			}
		}
		if(classState == IN_GAME)
		{
			dungeon.update();
		}
	}
	
	public static void changeResolution(int resolution)
	{
		currentResolution = resolution;
		Tile.standardSize = currentResolution;
		DisplayManager.setWindowSize(resolution * 16, resolution * 10);
	}
	
	public Dungeon getDungeon()
	{
		return dungeon;
	}
	
	public int getState()
	{
		return classState;
	}
}
