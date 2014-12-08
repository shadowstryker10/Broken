package ld.graphics;

import ld.game.Game;

import org.lwjgl.util.vector.Vector2f;
import org.newdawn.slick.opengl.Texture;

import static org.lwjgl.opengl.GL11.*;

public abstract class Tile implements ITile
{
	protected int id;
	
	public static final int minimumSize = 64;
	public static int standardSize = Game.currentResolution;
	public int prevStandardSize = standardSize;
	
	protected int size;
	
	protected Texture[] textures;
	
	protected Vector2f gridLocation;

	protected boolean isStandardSize;
	protected boolean isSolid;
	protected boolean isTranslucent;
	
	protected float translucence;
	
	protected float x, y;
	
	protected float red, green, blue;

	public Tile(int gridx, int gridy)
	{
		gridLocation = new Vector2f(gridx, gridy);
		size = standardSize;
		
		red = 0.3f;
		green = 0.3f;
		blue = 0.3f;
		
		x = gridLocation.x * size;
		y = gridLocation.y * size;
	}
	
	@Override
	public void update()
	{	
		if(standardSize != prevStandardSize)
		{
			this.size = standardSize;
			x = gridLocation.x * size;
			y = gridLocation.y * size;
		}
		
		prevStandardSize = standardSize;
		render();
	}

	@Override
	public void render()
	{
		glColor4f(red, green, blue, 1.0f);;
		glBindTexture(GL_TEXTURE_2D, textures[0].getTextureID());
		glBegin(GL_QUADS);
		{
			glTexCoord2d(0, 1 / textures[0].getHeight());
			glVertex2d(0, 0);

			glTexCoord2d(0, 0);
			glVertex2d(0, size);

			glTexCoord2d(1 / textures[0].getWidth(), 0);
			glVertex2d(size, size);

			glTexCoord2d(1 / textures[0].getWidth(),  1 / textures[0].getHeight());
			glVertex2d(size, 0);
		}
		glEnd();
		glBindTexture(GL_TEXTURE_2D, 0);
		glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
	}
	
	public void tintTile(float red, float green, float blue)
	{
		this.red = red;
		this.green = blue;
		this.blue = green;
	}
	
	public void resetTint()
	{
		this.red = 1.0f;
		this.green = 1.0f;
		this.blue = 1.0f;
	}
	
	public void setTranslucence(float translucence)
	{
		this.translucence = translucence;
	}

	@Override
	public boolean isStandardSize()
	{
		return isStandardSize;
	}

	@Override
	public int getSize()
	{
		return size;
	}

	@Override
	public Texture[] getTextures()
	{
		return textures;
	}
	
	@Override
	public int getTileID()
	{
		return id;
	}

	@Override
	public boolean isSolid()
	{
		return isSolid;
	}

	@Override
	public boolean isTranslucent()
	{
		return isTranslucent;
	}


	@Override
	public float getX()
	{
		return x;
	}

	@Override
	public float getY()
	{
		return y;
	}

	@Override
	public double getGridX()
	{
		return gridLocation.x;
	}

	@Override
	public double getGridY()
	{
		return gridLocation.y;
	}
}
