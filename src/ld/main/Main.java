package ld.main;

import ld.frame.DisplayManager;
import ld.game.Game;
import ld.graphics.Textures;
import ld.io.Preferences;
import ld.main.titlescreen.MainMenu;
import ld.sound.Sound;

import org.lwjgl.opengl.Display;

public class Main
{
	public static Main instance;
	public MainMenu menu;
	
	public static final int 
			PRE_INIT = 0, 
			INIT = 1, 
			TITLE_SCREEN = 2, 
			GAME_SCREEN = 3;
	
	private int gameState;
	
	private Game game;
	
	public void start()
	{
		gameState = 0;
		instance = this;
		
		DisplayManager.createDisplay(1024, 640, "Broken");
		Sound.playSound(Sound.MAIN_MUSIC);
		gameLoop();
		
		dispose();
	}
	
	public void gameLoop()
	{			
		while(!Display.isCloseRequested())
		{
			if(gameState == PRE_INIT)
			{
				gameState = INIT;
			}
			if(gameState == INIT)
			{
				Textures.loadTextures();
				menu = new MainMenu();
				
				gameState = TITLE_SCREEN;
			}
			if(gameState == TITLE_SCREEN)
			{
				menu.update();
				
				if(menu.isStartRequested())
				{
					game = new Game();
					gameState = GAME_SCREEN;
				}
			}
			if(gameState == GAME_SCREEN)
			{
				game.update();
			}
		
			this.update();
			DisplayManager.updateDisplay();
		}
	}
	
	public void update()
	{
		
	}
	
	public static void dispose()
	{
		System.exit(0);
	}
	
	public static void main(String[] args)
	{
		Main main = new Main();
		main.start();
	}

	public static Main getMain()
	{
		return instance;
	}
	
	public Game getGame()
	{
		return game;
	}
	
	public int getState()
	{
		return gameState;
	}
}
