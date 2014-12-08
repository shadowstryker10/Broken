package ld.graphics;

import org.newdawn.slick.opengl.Texture;

public interface ITile
{
	public void update();
	public void render();
	
	public int getTileID();
	
	public boolean isStandardSize();
	public int getSize();
	
	public Texture[] getTextures();
	
	public boolean isSolid();
	public boolean isTranslucent();
	
	public float getX();
	public float getY();
	
	public double getGridX();
	public double getGridY();
}
