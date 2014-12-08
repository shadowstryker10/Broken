package ld.main.titlescreen;

import static org.lwjgl.opengl.GL11.*;

import ld.game.Game;
import ld.graphics.Textures;
import ld.io.Preferences;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.newdawn.slick.Color;
import org.newdawn.slick.opengl.Texture;

public class LoseScreen
{
	private Texture lose_screen;

	public boolean returnRequested;
	
	public LoseScreen()
	{
		returnRequested = false;
		lose_screen = Textures.lose_screen;
	}
	
	public void update()
	{
		updateMouse();
		render();
	}
	
	public void updateMouse()
	{
		while(Mouse.next())
		{
			if(Mouse.getEventButtonState())
			{
				if(Mouse.getEventButton() == 0)
				{
						returnRequested = true;
				}
			}
		}
	}
	
	public void render()
	{
		renderBackground();
	}


	public void renderBackground()
	{
		glBindTexture(GL_TEXTURE_2D, lose_screen.getTextureID());
		glBegin(GL_QUADS);

			glTexCoord2d(0, 1 * lose_screen.getHeight());
			glVertex2d(0, 0);
	
			glTexCoord2d(0, 0);
			glVertex2d(0, Display.getHeight());
	
			glTexCoord2d(1 / lose_screen.getWidth(), 0);
			glVertex2d(Display.getWidth(), Display.getHeight());
	
			glTexCoord2d(1 / lose_screen.getWidth(), 1 * lose_screen.getHeight());
			glVertex2d(Display.getWidth(), 0);
		
		glEnd();
		glBindTexture(GL_TEXTURE_2D, 0);
	}
	
	public boolean isReturnRequested()
	{
		return returnRequested;
	}
}
