package ld.graphics;

import org.newdawn.slick.opengl.Texture;

public class TileWall extends Tile
{

	Texture[] textures = new Texture[1];
	
	public TileWall(int gridx, int gridy)
	{
		super(gridx, gridy);
		
		textures[0] = Textures.tile_wall_test;
		
		super.textures = this.textures;
		super.isSolid = true;
		super.isTranslucent = false;
		super.translucence = 0.0f;
	}
}
