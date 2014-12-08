package ld.graphics;

import static org.lwjgl.opengl.GL11.GL_QUADS;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glBindTexture;
import static org.lwjgl.opengl.GL11.glColor4f;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glTexCoord2d;
import static org.lwjgl.opengl.GL11.glVertex2d;
import ld.main.Main;

import org.newdawn.slick.opengl.Texture;


public class TileFloor extends Tile
{

	Texture[] textures = new Texture[2];
	
	public TileFloor(int gridx, int gridy)
	{
		super(gridx, gridy);

		textures[0] = Textures.tile_floor_black;
		textures[1] = Textures.tile_floor_rainbow;
		
		super.textures = this.textures;
		super.isSolid = false;
		super.isTranslucent = false;
		super.translucence = 0.0f;
	}
	
	public void  update()
	{
		super.update();
	}
	
	public void render()
	{
		if(Main.getMain().getGame().getDungeon().winGame)
			glBindTexture(GL_TEXTURE_2D, textures[1].getTextureID());
		else
		{
			glColor4f(red, green, blue, 1.0f);
			glBindTexture(GL_TEXTURE_2D, textures[0].getTextureID());
		}
		
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
}
