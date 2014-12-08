package ld.entities;

import ld.graphics.ITile;

import org.newdawn.slick.opengl.Texture;

public interface IEntity
{
	public float getX();
	public float getY();
	
	public float getEntitySize();
	
	public void update();
	public void render();
	
	public boolean isSolid();
	
	public boolean collidesWith(GameEntity entity);
	public boolean collidesWith(ITile tile);
	
	public Texture[] getTextures();
}
