package ld.graphics;

import static org.lwjgl.opengl.GL11.GL_QUADS;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glColor4f;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glVertex2d;
import ld.game.Dungeon;
import ld.main.Main;

public class TileColoredPixel extends Tile
{

	public int color;
	
	public TileColoredPixel(int gridx, int gridy, int color)
	{
		super(gridx, gridy);
		this.color = color;
		
		super.isSolid = false;
		super.isTranslucent = false;
		super.translucence = 0.0f;

		if(color == 1)
		{
			red = 1.0f;
			green = 0.0f;
			blue = 0.0f;
		}
		if(color == 2)
		{
			red = 0.0f;
			green = 1.0f;
			blue = 0.0f;
		}
		if(color == 3)
		{
			red = 0.0f;
			green = 0.0f;
			blue = 1.0f;
		}
	}
	
	public void update()
	{
		super.update();
		
		Dungeon dungeon = Main.getMain().getGame().getDungeon();

		if(color == 1)
		{
			if(dungeon.redCollected >= dungeon.coloredPixelsToCollect)
				dungeon.on_ground_tiles[(int) gridLocation.x][(int) gridLocation.y] = null;
		}
		if(color == 2)
		{
			if(dungeon.greenCollected >= dungeon.coloredPixelsToCollect)
				dungeon.on_ground_tiles[(int) gridLocation.x][(int) gridLocation.y] = null;
		}
		if(color == 3)
		{
			if(dungeon.blueCollected >= dungeon.coloredPixelsToCollect)
			{
				dungeon.on_ground_tiles[(int) gridLocation.x][(int) gridLocation.y] = null;
				System.err.println(dungeon.blueCollected + " | " + dungeon.coloredPixelsToCollect);
			}
		}
	}
	
	public void render()
	{
		glColor4f(red, green, blue, 1.0f);;
		glBegin(GL_QUADS);
		{
			glVertex2d(0, 0);

			glVertex2d(0, size);

			glVertex2d(size, size);

			glVertex2d(size, 0);
		}
		glEnd();
		glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
	}
}
