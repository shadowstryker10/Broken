package ld.main.titlescreen;

import static org.lwjgl.opengl.GL11.*;
import ld.graphics.Textures;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.newdawn.slick.opengl.Texture;

public class AboutMenu
{
	private Texture about_menu;

	boolean returnRequested;
	
	public AboutMenu()
	{
		returnRequested = false;
		about_menu = Textures.about_menu;
	}
	
	public void update()
	{
		updateKeyboard();
		updateMouse();
		render();
	}
	
	public void updateKeyboard()
	{
		while(Keyboard.next())
		{
			if(Keyboard.getEventKeyState())
			{
				if(Keyboard.getEventKey() == Keyboard.KEY_RETURN)
					returnRequested = true;
			}
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
		glBindTexture(GL_TEXTURE_2D, about_menu.getTextureID());
		glBegin(GL_QUADS);

			glTexCoord2d(0, 1 * about_menu.getHeight());
			glVertex2d(0, 0);
	
			glTexCoord2d(0, 0);
			glVertex2d(0, Display.getHeight());
	
			glTexCoord2d(1 / about_menu.getWidth(), 0);
			glVertex2d(Display.getWidth(), Display.getHeight());
	
			glTexCoord2d(1 / about_menu.getWidth(), 1 * about_menu.getHeight());
			glVertex2d(Display.getWidth(), 0);
		
		glEnd();
		glBindTexture(GL_TEXTURE_2D, 0);
	}
	
	public boolean isReturnRequested()
	{
		return returnRequested;
	}
}
