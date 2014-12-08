package ld.entities.player;

import static org.lwjgl.opengl.GL11.GL_QUADS;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glBindTexture;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glTexCoord2d;
import static org.lwjgl.opengl.GL11.glVertex2d;

import java.awt.Rectangle;
import java.util.List;

import ld.entities.EntityCommoner;
import ld.entities.GameEntity;
import ld.entities.LivingGameEntity;
import ld.game.Dungeon;
import ld.game.Game;
import ld.graphics.Textures;
import ld.graphics.Tile;
import ld.graphics.TileColoredPixel;
import ld.main.Main;

import org.lwjgl.util.vector.Vector2f;
import org.newdawn.slick.opengl.Texture;

public class Player extends LivingGameEntity
{
	PlayerInputHandler input;
	Dungeon dungeon;
	Texture[] textures = new Texture[6];
	
	public Vector2f gridLocation;
	
	int currentResolution;
	int previousResolution;
	
	public Player(int gridx, int gridy)
	{
		textures[0] = Textures.player_1;
		textures[1] = Textures.player_2;
		textures[2] = Textures.player_3;
		textures[3] = Textures.player_4;
		textures[4] = Textures.player_5;
		textures[5] = Textures.commoner_black;
		
		
		super.textures = this.textures;
		speedConstant = 0.25f;
		entitySize = (int) (Game.currentResolution / 1.33);
		collisionBounds = new Rectangle((int) entitySize, (int) entitySize);
		
		super.x = (gridx * Game.currentResolution);
		super.y = (gridy * Game.currentResolution);
		
		gridLocation = new Vector2f((float) (x / Tile.standardSize), (float) (y / Tile.standardSize));
		input = new PlayerInputHandler(this);
		currentResolution = Game.currentResolution;
		previousResolution = currentResolution;
		
		super.health = 5;
	}
	
	public void update()
	{
		super.update();
		
		dungeon = Main.getMain().getGame().getDungeon();
		
		List<GameEntity> entities = dungeon.getEntityList();
		for(int i = 0; i < entities.size(); i++)
		{
			GameEntity entity = entities.get(i);
			
			if(entity instanceof EntityCommoner && collidesWith(entity))
			{
				dungeon.killEntity(entity);
				health--;
			}
		}
		if(health == 0)
		{
			dungeon.loseGame = true;
		}
		
		if(previousResolution != Game.currentResolution)
		{
			entitySize = (int) (Game.currentResolution / 1.33);     
		}

		previousResolution = currentResolution;
		currentResolution = Game.currentResolution;
		
		gridLocation = new Vector2f((int) ((x + (entitySize / 2)) / Tile.standardSize), (int) ((y + (entitySize / 2)) / Tile.standardSize));

		if(dungeon.getOnGroundTiles()[(int) gridLocation.x][(int) gridLocation.y] instanceof TileColoredPixel)
		{
			TileColoredPixel tile = (TileColoredPixel) dungeon.getOnGroundTiles()[(int) gridLocation.x][(int) gridLocation.y];
			int color = tile.color;

			if(color == 1 && dungeon.redCollected < dungeon.coloredPixelsToCollect)
			{
				Main.getMain().getGame().red += 0.075f;
				Main.getMain().getGame().getDungeon().redCollected++;
			}
			if(color == 2 && dungeon.greenCollected < dungeon.coloredPixelsToCollect)
			{
				Main.getMain().getGame().getDungeon().greenCollected++;
				Main.getMain().getGame().green += 0.075f;
			}
			if(color == 3 && dungeon.blueCollected < dungeon.coloredPixelsToCollect)
			{
				Main.getMain().getGame().getDungeon().blueCollected++;
				Main.getMain().getGame().blue += 0.075f;
			}
			dungeon.getOnGroundTiles()[(int) gridLocation.x][(int) gridLocation.y] = null;
			dungeon.coloredPixelsOut--;
		}
		
		input.update();
		render();
	}
	
	public void render()
	{
		System.out.println(health);
		
		if(health == 5)
		{
			glBindTexture(GL_TEXTURE_2D, textures[0].getTextureID());
		}
		else if(health == 4)
		{
			glBindTexture(GL_TEXTURE_2D, textures[1].getTextureID());
		}
		else if(health == 3)
		{
			glBindTexture(GL_TEXTURE_2D, textures[2].getTextureID());
		}
		else if(health == 2)
		{
			glBindTexture(GL_TEXTURE_2D, textures[3].getTextureID());
		}
		else if(health == 1)
		{
			glBindTexture(GL_TEXTURE_2D, textures[4].getTextureID());
		}
		else
		{
			glBindTexture(GL_TEXTURE_2D, textures[5].getTextureID());
		}
			
		glBegin(GL_QUADS);
		{			
			glTexCoord2d(1 / textures[0].getWidth(),  1 / textures[0].getHeight());
			glVertex2d(x, y);

			glTexCoord2d(1 / textures[0].getWidth(), 0);
			glVertex2d(x, entitySize + y);

			glTexCoord2d(0, 0);
			glVertex2d(entitySize + x, entitySize + y);

			glTexCoord2d(0, 1 / textures[0].getHeight());
			glVertex2d(entitySize + x, 0 + y);
		}
		glEnd();
		glBindTexture(GL_TEXTURE_2D, 0);
	}
	
	public Vector2f getCenter()
	{
		return new Vector2f(x + (this.getEntitySize() / 2), y + (this.getEntitySize() / 2));
	}
}
