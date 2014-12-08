package ld.main.titlescreen;

import static org.lwjgl.opengl.GL11.GL_QUADS;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glBindTexture;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glTexCoord2d;
import static org.lwjgl.opengl.GL11.glVertex2d;
import ld.graphics.Textures;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.newdawn.slick.opengl.Texture;

public class WinScreen
{
	private Texture win_screen;

	public boolean returnRequested;
	
	public WinScreen()
	{
		returnRequested = false;
		win_screen = Textures.win_screen;
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
		glBindTexture(GL_TEXTURE_2D, win_screen.getTextureID());
		glBegin(GL_QUADS);

			glTexCoord2d(0, 1 * win_screen.getHeight());
			glVertex2d(0, 0);
	
			glTexCoord2d(0, 0);
			glVertex2d(0, Display.getHeight());
	
			glTexCoord2d(1 / win_screen.getWidth(), 0);
			glVertex2d(Display.getWidth(), Display.getHeight());
	
			glTexCoord2d(1 / win_screen.getWidth(), 1 * win_screen.getHeight());
			glVertex2d(Display.getWidth(), 0);
		
		glEnd();
		glBindTexture(GL_TEXTURE_2D, 0);
	}
	
	public boolean isReturnRequested()
	{
		return returnRequested;
	}
}
