package ld.main.titlescreen;

import static org.lwjgl.opengl.GL11.*;

import ld.game.Game;
import ld.graphics.Textures;
import ld.io.Preferences;
import ld.sound.Sound;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.newdawn.slick.Color;
import org.newdawn.slick.opengl.Texture;

public class SettingsMenu
{
	private Texture settings_menu;
	private Texture selector;

	boolean returnRequested;
	
	int selectorInt = 0;
	
	public SettingsMenu()
	{
		returnRequested = false;
		settings_menu = Textures.settings_menu;
		selector = Textures.selector;
	}
	
	public void update()
	{
		updateMouse();
		updateSelector();
		render();
	}
	
	public void updateSelector()
	{
		if(Mouse.getY() > 455 && Mouse.getY() < 505) selectorInt = 0;
		else if(Mouse.getY() > 405 && Mouse.getY() < 454) selectorInt = 1;
		else if(Mouse.getY() > 360 && Mouse.getY() < 404) selectorInt = 2;
		else if(Mouse.getY() > 310 && Mouse.getY() < 403) selectorInt = 3;
		else if(Mouse.getY() > 245 && Mouse.getY() < 402) selectorInt = 4;
		else if(Mouse.getY() > 130 && Mouse.getY() < 401) selectorInt = 5;
		else if(Mouse.getY() > 0 && Mouse.getY() < 130) selectorInt = 6;
	}
	
	public void updateMouse()
	{
		while(Mouse.next())
		{
			if(Mouse.getEventButtonState())
			{
				if(Mouse.getEventButton() == 0)
				{
					if(selectorInt == 0)
						Preferences.preferredResolution = 0;
					if(selectorInt == 1)
						Preferences.preferredResolution = 1;
					if(selectorInt == 2)
						Preferences.preferredResolution = 2;
					if(selectorInt == 3)
						Preferences.preferredResolution = 3;
					if(selectorInt == 4)
						Preferences.preferredResolution = 4;
					if(selectorInt == 5)
					{
						Preferences.mute = !Preferences.mute;
						
						if(Preferences.mute == true)
							Sound.closeClips();
						else
							Sound.playSound(Sound.MAIN_MUSIC);
					}
					if(selectorInt == 6)
						returnRequested = true;
				}
			}
		}
	}
	
	public void render()
	{
		renderBackground();
		renderSelector();
	}
	
	public void renderSelector()
	{
		if(selectorInt == 0 || Preferences.preferredResolution == 0)
		{
			Color.red.bind();
			glBegin(GL_QUADS);

				glVertex2d(559, 471);
		
				glVertex2d(559, 489);
		
				glVertex2d(570, 489);
		
				glVertex2d(570, 471);
			
			glEnd();
			Color.white.bind();
		}
		if(selectorInt == 1 || Preferences.preferredResolution == 1)
		{
			Color.red.bind();
			glBegin(GL_QUADS);

				glVertex2d(562, 423);
		
				glVertex2d(562, 440);
		
				glVertex2d(574, 440);
		
				glVertex2d(574, 423);
			
			glEnd();
			Color.white.bind();
		}
		if(selectorInt == 2 || Preferences.preferredResolution == 2)
		{
			Color.red.bind();
			glBegin(GL_QUADS);

				glVertex2d(560, 375);
		
				glVertex2d(560, 392);
		
				glVertex2d(572, 392);
		
				glVertex2d(572, 375);
			
			glEnd();
			Color.white.bind();
		}
		if(selectorInt == 3 || Preferences.preferredResolution == 3)
		{
			Color.red.bind();
			glBegin(GL_QUADS);

				glVertex2d(562, 327);
		
				glVertex2d(562, 344);
		
				glVertex2d(574, 344);
		
				glVertex2d(574, 327);
			
			glEnd();
			Color.white.bind();
		}
		if(selectorInt == 4 || Preferences.preferredResolution == 4)
		{
			Color.red.bind();
			glBegin(GL_QUADS);

				glVertex2d(562, 280);
		
				glVertex2d(562, 296);
		
				glVertex2d(576, 296);
		
				glVertex2d(576, 280);
			
			glEnd();
			Color.white.bind();
		}
		if(selectorInt == 5 || Preferences.mute)
		{
			Color.red.bind();
			glBegin(GL_QUADS);

				glVertex2d(654, 187);
		
				glVertex2d(654, 215);
		
				glVertex2d(677, 215);
		
				glVertex2d(677, 187);
			
			glEnd();
			Color.white.bind();
		}
		if(selectorInt == 6)
		{
			glBindTexture(GL_TEXTURE_2D, selector.getTextureID());
			glBegin(GL_QUADS);

				glTexCoord2d(0, 1 * selector.getHeight());
				glVertex2d(338, -Display.getHeight() + 58);
		
				glTexCoord2d(0, 0);
				glVertex2d(338, -Display.getHeight() + 58 + selector.getImageHeight());
		
				glTexCoord2d(1 / selector.getWidth(), 0);
				glVertex2d(338 + selector.getImageWidth(), -Display.getHeight() + 58 + selector.getImageHeight());
		
				glTexCoord2d(1 / selector.getWidth(), 1 * selector.getHeight());
				glVertex2d(338 + selector.getImageWidth(), -Display.getHeight() + 58);

			glEnd();
			glBindTexture(GL_TEXTURE_2D, 0);
		}
	}

	public void renderBackground()
	{
		glBindTexture(GL_TEXTURE_2D, settings_menu.getTextureID());
		glBegin(GL_QUADS);

			glTexCoord2d(0, 1 * settings_menu.getHeight());
			glVertex2d(0, 0);
	
			glTexCoord2d(0, 0);
			glVertex2d(0, Display.getHeight());
	
			glTexCoord2d(1 / settings_menu.getWidth(), 0);
			glVertex2d(Display.getWidth(), Display.getHeight());
	
			glTexCoord2d(1 / settings_menu.getWidth(), 1 * settings_menu.getHeight());
			glVertex2d(Display.getWidth(), 0);
		
		glEnd();
		glBindTexture(GL_TEXTURE_2D, 0);
	}
	
	public boolean isReturnRequested()
	{
		return returnRequested;
	}
}
