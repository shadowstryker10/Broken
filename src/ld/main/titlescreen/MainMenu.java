package ld.main.titlescreen;

import static org.lwjgl.opengl.GL11.*;
import ld.game.Game;
import ld.graphics.Textures;
import ld.main.Main;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.newdawn.slick.opengl.Texture;

public class MainMenu
{
	AboutMenu about_menu;
	SettingsMenu settings_menu;
	
	private Texture main_background;
	private Texture selector;

	boolean startGameRequested;
	boolean aboutMenuRequested;
	boolean settingsMenuRequested;
	
	int selectorInt = 0;
	
	public MainMenu()
	{
		about_menu = new AboutMenu();
		settings_menu = new SettingsMenu();
		
		startGameRequested = false;
		aboutMenuRequested = false;
		settingsMenuRequested = false;
		
		main_background = Textures.main_menu;
		selector = Textures.selector;
	}
	
	public void update()
	{
		if(settingsMenuRequested)
		{
			settings_menu.update();
			if(settings_menu.isReturnRequested()) 
			{
				settingsMenuRequested = false;
				settings_menu.returnRequested = false;
			}
		}
		else if(aboutMenuRequested)
		{
			about_menu.update();
			if(about_menu.isReturnRequested()) 
			{
				aboutMenuRequested = false;
				about_menu.returnRequested = false;
			}
		}
		else
		{
			updateMouse();
			updateSelector();
			render();
		}
	}
	
	public void updateMouse()
	{
		while(Mouse.next())
		{
			if(Mouse.getEventButtonState())
			{
				if(Mouse.getEventButton() == 0)
				{
					if(selectorInt == 0) startGameRequested = true;
					if(selectorInt == 1) settingsMenuRequested = true;
					if(selectorInt == 2) aboutMenuRequested = true;
					if(selectorInt == 3) Main.dispose();
				}
			}
		}
	}

	public void updateSelector()
	{
		if(Mouse.getY() < 400 && Mouse.getY() > 345) selectorInt = 0;
		if(Mouse.getY() < 345 && Mouse.getY() > 285) selectorInt = 1;
		if(Mouse.getY() < 284 && Mouse.getY() > 230) selectorInt = 2;
		if(Mouse.getY() < 225 && Mouse.getY() > 170) selectorInt = 3;
	}
	
	public void render()
	{	
		renderBackground();
		renderSelector();
	}

	public void renderBackground()
	{
		glBindTexture(GL_TEXTURE_2D, main_background.getTextureID());
		glBegin(GL_QUADS);

			glTexCoord2d(0, 1 * main_background.getHeight());
			glVertex2d(0, 0);
	
			glTexCoord2d(0, 0);
			glVertex2d(0, Display.getHeight());
	
			glTexCoord2d(1 / main_background.getWidth(), 0);
			glVertex2d(Display.getWidth(), Display.getHeight());
	
			glTexCoord2d(1 / main_background.getWidth(), 1 * main_background.getHeight());
			glVertex2d(Display.getWidth(), 0);
		
		glEnd();
		glBindTexture(GL_TEXTURE_2D, 0);
	}
	
	public void renderSelector()
	{		
		float selectorX = 0;
		float selectorY = 0;

		if(selectorInt == 0)
		{
			selectorX = 325;
			selectorY = -Display.getHeight() + 383;
		}
		if(selectorInt == 1)
		{
			selectorX = 275;
			selectorY = -Display.getHeight() + 325;
		}
		if(selectorInt == 2)
		{
			selectorX = 315;
			selectorY = -Display.getHeight() + 270;
		}
		if(selectorInt == 3)
		{
			selectorX = 345;
			selectorY = -Display.getHeight() + 212;
		}
		
		glBindTexture(GL_TEXTURE_2D, selector.getTextureID());
		glBegin(GL_QUADS);

			glTexCoord2d(0, 1 * selector.getHeight());
			glVertex2d(selectorX, selectorY);
	
			glTexCoord2d(0, 0);
			glVertex2d(selectorX, selectorY + selector.getImageHeight());
	
			glTexCoord2d(1 / selector.getWidth(), 0);
			glVertex2d(selectorX + selector.getImageWidth(), selectorY + selector.getImageHeight());
	
			glTexCoord2d(1 / selector.getWidth(), 1 * selector.getHeight());
			glVertex2d(selectorX + selector.getImageWidth(), selectorY);

		glEnd();
		glBindTexture(GL_TEXTURE_2D, 0);
	}
	
	public boolean isStartRequested()
	{
		return startGameRequested;
	}
}
