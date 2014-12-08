package ld.entities;

import static org.lwjgl.opengl.GL11.GL_QUADS;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glBindTexture;
import static org.lwjgl.opengl.GL11.glColor4f;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glTexCoord2d;
import static org.lwjgl.opengl.GL11.glVertex2d;

import java.awt.Rectangle;

import ld.game.Game;
import ld.graphics.ITile;

import org.lwjgl.opengl.GL11;
import org.newdawn.slick.opengl.Texture;

public abstract class GameEntity implements IEntity
{
	public float x, y;
	public boolean isSolid;
	public Texture[] textures;
	protected int entitySize;

	protected int currentResolution;
	protected int previousResolution;
	public Rectangle collisionBounds;
	
	public GameEntity()
	{
		currentResolution = Game.currentResolution;
		previousResolution = currentResolution;
	}

	@Override
	public void update()
	{		

	}

	@Override
	public void render()
	{
		glBindTexture(GL_TEXTURE_2D, textures[0].getTextureID());
		glBegin(GL_QUADS);
		{
			glTexCoord2d(0, 1 / textures[0].getHeight());
			glVertex2d(x - (entitySize / 2), y - (entitySize / 2));

			glTexCoord2d(0, 0);
			glVertex2d(x - (entitySize / 2), y + (entitySize / 2));

			glTexCoord2d(1 / textures[0].getWidth(), 0);
			glVertex2d(x + (entitySize / 2), y + (entitySize / 2));

			glTexCoord2d(1 / textures[0].getWidth(),  1 / textures[0].getHeight());
			glVertex2d(x + (entitySize / 2), y - (entitySize / 2));
		}
		glEnd();
		glBindTexture(GL_TEXTURE_2D, 0);
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
	public Texture[] getTextures()
	{
		return textures;
	}

	@Override
	public boolean isSolid()
	{
		return isSolid;
	}

	@Override
	public boolean collidesWith(ITile tile)
	{
		return topCollidesWith(tile) || botCollidesWith(tile) || leftCollidesWith(tile) || rightCollidesWith(tile);
	}
	
	public boolean topCollidesWith(ITile tile)
	{
		float eLeft = this.x;
		float eRight = this.x + entitySize;
		float eTop = this.y + entitySize;
		
		int tLeft = (int) tile.getX();
		int tRight = (int) tile.getX() + tile.getSize();
		int tBot = (int) tile.getY();
		
		if(tile.isSolid())
			if(eRight > tLeft && eLeft < tRight)
				if(eTop >= tBot)
					return true;
		
		return false;
	}

	public boolean botCollidesWith(ITile tile)
	{
		float eLeft = this.x;
		float eRight = this.x + entitySize;
		float eBot = this.y;
		
		int tLeft = (int) tile.getX();
		int tRight = (int) tile.getX() + tile.getSize();
		int tTop = (int) tile.getY() + tile.getSize();
		
		if(tile.isSolid())
			if(eRight > tLeft && eLeft < tRight)
				if(eBot <= tTop)
					return true;
		
		return false;
	}

	public boolean leftCollidesWith(ITile tile)
	{
		float eLeft = this.x;
		float eTop = this.y + entitySize;
		float eBot = this.y;
		
		int tRight = (int) tile.getX() + tile.getSize();
		int tTop = (int) tile.getY() + tile.getSize();
		int tBot = (int) tile.getY();
		
		if(tile.isSolid())
			if(eTop > tBot && eBot < tTop)
				if(eLeft <= tRight)
					return true;
		
		return false;
	}

	public boolean rightCollidesWith(ITile tile)
	{
		float eRight = this.x + entitySize;
		float eTop = this.y + entitySize;
		float eBot = this.y;
		
		int tLeft = (int) tile.getX();
		int tTop = (int) tile.getY() + tile.getSize();
		int tBot = (int) tile.getY();
		
		if(tile.isSolid())
			if(eTop > tBot && eBot < tTop)
				if(eRight >= tLeft)
					return true;
		
		return false;
	}

	@Override
	public boolean collidesWith(GameEntity entity)
	{
		float thisLeft = x;
		float thisRight = x + collisionBounds.width;
		float thisTop = y + collisionBounds.height;
		float thisBot = y;

		float itsLeft = entity.getX();
		float itsRight = entity.getX() + entity.collisionBounds.height;
		float itsTop = entity.getY() + entity.collisionBounds.height;
		float itsBot = entity.getY();

		if(((thisLeft > itsLeft && thisLeft < itsRight) || (thisRight < itsRight && thisRight > itsLeft)) && ((thisTop > itsBot && thisTop < itsTop) || (thisBot > itsBot && thisBot < itsTop)))
			return true;
		
		return false;
	}
	
	@Override
	public float getEntitySize()
	{
		return entitySize;
	}
}
