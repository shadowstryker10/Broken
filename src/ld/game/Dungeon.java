package ld.game;

import static org.lwjgl.opengl.GL11.GL_QUADS;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glBindTexture;
import static org.lwjgl.opengl.GL11.glColor3f;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glPopMatrix;
import static org.lwjgl.opengl.GL11.glPushMatrix;
import static org.lwjgl.opengl.GL11.glTexCoord2d;
import static org.lwjgl.opengl.GL11.glTranslated;
import static org.lwjgl.opengl.GL11.glVertex2d;

import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import ld.entities.EntityCommoner;
import ld.entities.GameEntity;
import ld.entities.player.Player;
import ld.frame.DisplayManager;
import ld.graphics.Textures;
import ld.graphics.Tile;
import ld.graphics.TileColoredPixel;
import ld.graphics.TileFloor;
import ld.graphics.TileWall;
import ld.io.Preferences;
import ld.main.Main;
import ld.main.titlescreen.LoseScreen;
import ld.main.titlescreen.WinScreen;
import ld.sound.Sound;

import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector2f;
import org.newdawn.slick.opengl.Texture;

public class Dungeon
{
	public Tile[][] ground_tiles, on_ground_tiles, cieling_tiles;
	private Dimension dungeonSize;
	private Game game;
	
	public Player player;
	public List<GameEntity> game_entities = new ArrayList<GameEntity>();
	
	public int coloredPixelsOut;
	public int maxColoredPixelsOut;
	public int coloredPixelsToCollect;
	
	public int redCollected;
	public int greenCollected;
	public int blueCollected;
	
	public int mobSpawnWait;
	public int pixelSpawnWait;
	
	public boolean loseGame;
	public boolean winGame;

	public LoseScreen lose_screen;
	public WinScreen win_screen;

	private Random random = new Random();
	
	Texture rgbHUD;

	float end_alpha = 0.0f;
	
	public Dungeon(int width, int height)
	{
		game = Main.getMain().getGame();
		rgbHUD = Textures.rgbHUD;
		lose_screen = new LoseScreen();
		win_screen = new WinScreen();

		redCollected = 0;
		greenCollected = 0;
		blueCollected = 0;

		mobSpawnWait = 14;
		pixelSpawnWait = 25;
		
		player = new Player(4, 4);
		
		ground_tiles = new Tile[width][height];
		on_ground_tiles = new Tile[width][height];
		cieling_tiles = new Tile[width][height];
		
		maxColoredPixelsOut = 3;
		coloredPixelsToCollect = 10;
		
		dungeonSize = new Dimension(width, height);
		
		for(int x = 0; x < width; x++)
		{
			for(int y = 0; y < height; y++)
			{
				Tile tile;
				if(x == 0 || y == 0 || x == dungeonSize.width - 1 || y == dungeonSize.height - 1)
					tile = new TileWall(x, y);
				else
					tile = new TileFloor(x, y);
				
				ground_tiles[x][y] = tile;
			}
		}
	}
	
	public void update()
	{
		if(loseGame)
		{
			lose_screen.update();
			if(lose_screen.isReturnRequested())
				Main.dispose();
		}
		else if(winGame)
		{
			for(int x = 0; x < dungeonSize.width; x++)
			{
				for(int y = 0; y < dungeonSize.height; y++)
				{	
					glPushMatrix();
					glTranslated(x * Tile.standardSize, y * Tile.standardSize, 0);
					ground_tiles[x][y].update();
					glPopMatrix();
				}
			}
			win_screen.update();
			if(win_screen.isReturnRequested())
				Main.dispose();
		}
		else
		{
			if(redCollected == coloredPixelsToCollect && greenCollected == coloredPixelsToCollect && blueCollected == coloredPixelsToCollect)
			{
				winGame = true;
				if(!Preferences.mute)
					Sound.playWinSound();
			}
			
			if(redCollected > 5 || greenCollected > 5 || blueCollected > 5)
			{
				mobSpawnWait = 9;
			}
			else if(redCollected > 9 || greenCollected > 9 || blueCollected > 9)
			{
				mobSpawnWait = 8;
			}
			
			int chance = (int) (mobSpawnWait * (DisplayManager.getFPS() / 10)) + 1;

			if(random.nextInt(chance) == 1)
			{
				int gx = random.nextInt(16);
				int gy = random.nextInt(10);
				
				while(gx >= player.gridLocation.x - 1 && gx <= player.gridLocation.x + 1 || (gx <= 0 || gx >= 15))
				{
					gx = random.nextInt(16);
				}
				while(gy >= player.gridLocation.y - 1 && gy <= player.gridLocation.y + 1 || (gy <= 0 || gy >= 9))
				{
					gy = random.nextInt(10);
				}
				
				spawnEntity(new EntityCommoner(gx, gy, 0, 0));
			}
			
			chance = (int) (pixelSpawnWait * (DisplayManager.getFPS() / 10)) + 1;
			
			if(random.nextInt(chance) == 1 && coloredPixelsOut < maxColoredPixelsOut && !(redCollected >= coloredPixelsToCollect && greenCollected >= coloredPixelsToCollect && blueCollected >= coloredPixelsToCollect))
			{
				int x = random.nextInt(13) + 1;
				int y = random.nextInt(7) + 1;
				int color = random.nextInt(3) + 1;
				
				while((color == 1 && redCollected >= coloredPixelsToCollect) || (color == 2 && greenCollected >= coloredPixelsToCollect) || (color == 3 && blueCollected >= coloredPixelsToCollect))
				{
					if((color == 1 && redCollected >= coloredPixelsToCollect) && (color == 2 && greenCollected >= coloredPixelsToCollect) && (color == 3 && blueCollected >= coloredPixelsToCollect))
					{
						break;
					}
						color = random.nextInt(3) + 1;
				}
				
				on_ground_tiles[x + 1][y + 1] = new TileColoredPixel(x + 1, y + 1, color);
				coloredPixelsOut++;
			}
			game = Main.getMain().getGame();
			render();
			player.update();
			
			for(int i = 0; i < game_entities.size(); i++)
			{
				game_entities.get(i).update();
			}
		}
	}
	
	public void render()
	{
		for(int x = 0; x < dungeonSize.width; x++)
		{
			for(int y = 0; y < dungeonSize.height; y++)
			{	
				glPushMatrix();
				glTranslated(x * Tile.standardSize, y * Tile.standardSize, 0);
				ground_tiles[x][y].tintTile(game.red, game.green, game.blue);
				ground_tiles[x][y].update();
				glPopMatrix();
				

				glPushMatrix();
				glTranslated(x * Tile.standardSize, y * Tile.standardSize, 0);
				if(on_ground_tiles[x][y] != null)
					on_ground_tiles[x][y].update();
				glPopMatrix();
			}
		}
		renderHUD();
	}
	
	public void renderHUD()
	{
		double redlx = 0, redrx = 0, redty = 0, redby = 0;
		double greenlx = 0, greenrx = 0, greenty = 0, greenby = 0;
		double bluelx = 0, bluerx = 0, bluety = 0, blueby = 0;
		
			redlx = 262 * ((float)Game.currentResolution / (float)64.0);
			redrx = (262 + (10.2 * redCollected)) * ((float)Game.currentResolution / (float)64.0);
			redty = Display.getHeight() - (20 * ((float)Game.currentResolution / (float)64.0));
			redby = Display.getHeight() - (30 * ((float)Game.currentResolution / (float)64.0));

			greenlx = 483 * ((float)Game.currentResolution / (float)64.0);
			greenrx = (483 + (10.3 * greenCollected)) * ((float)Game.currentResolution / (float)64.0);
			greenty = Display.getHeight() - (20 * ((float)Game.currentResolution / (float)64.0));
			greenby = Display.getHeight() - (30 * ((float)Game.currentResolution / (float)64.0));

			bluelx = 693 * ((float)Game.currentResolution / (float)64.0);
			bluerx = (693 + (10.4 * blueCollected)) * ((float)Game.currentResolution / (float)64.0);
			bluety = Display.getHeight() - (20 * ((float)Game.currentResolution / (float)64.0));
			blueby = Display.getHeight() - (30 * ((float)Game.currentResolution / (float)64.0));
		
		glColor3f(1.0f, 0.0f, 0.0f);
		glBegin(GL_QUADS);
			glVertex2d(redlx, redby);
			
			glVertex2d(redlx, redty);
			
			glVertex2d(redrx, redty);
			
			glVertex2d(redrx, redby);
		glEnd();
		glColor3f(1.0f, 1.0f, 1.0f);
		
		

		glColor3f(0.0f, 1.0f, 0.0f);
		glBegin(GL_QUADS);
			glVertex2d(greenlx, greenby);
			
			glVertex2d(greenlx, greenty);
			
			glVertex2d(greenrx, greenty);
			
			glVertex2d(greenrx, greenby);
		glEnd();
		glColor3f(1.0f, 1.0f, 1.0f);
		
		

		glColor3f(0.0f, 0.0f, 1.0f);
		glBegin(GL_QUADS);
			glVertex2d(bluelx, blueby);
			
			glVertex2d(bluelx, bluety);
			
			glVertex2d(bluerx, bluety);
			
			glVertex2d(bluerx, blueby);
		glEnd();
		glColor3f(1.0f, 1.0f, 1.0f);
	
		glBindTexture(GL_TEXTURE_2D, rgbHUD.getTextureID());
		glBegin(GL_QUADS);

			glTexCoord2d(0, 1 * rgbHUD.getHeight());
			glVertex2d(0, 0);
	
			glTexCoord2d(0, 0);
			glVertex2d(0, Display.getHeight());
	
			glTexCoord2d(1 / rgbHUD.getWidth(), 0);
			glVertex2d(Display.getWidth(), Display.getHeight());
	
			glTexCoord2d(1 / rgbHUD.getWidth(), 1 * rgbHUD.getHeight());
			glVertex2d(Display.getWidth(), 0);
		
		glEnd();
		glBindTexture(GL_TEXTURE_2D, 0);
	}
	
	public List<GameEntity> getEntityList()
	{
		return game_entities;
	}
	
	public void spawnEntity(GameEntity entity)
	{
		game_entities.add(entity);
	}
	
	public void killEntity(GameEntity entity)
	{
		game_entities.remove(entity);
	}
	
	public Tile getGroundTileAtGrid(int x, int y)
	{
		return ground_tiles[x][y];
	}
	
	public Tile getGroundTileAt(double x, double y)
	{
		Vector2f gridLoc = getGridLocation(x, y);
		
		return ground_tiles[(int) gridLoc.x][(int) gridLoc.y];
	}
	
	public Tile[][] getGroundTiles()
	{
		return ground_tiles;
	}
	
	public Tile[][] getOnGroundTiles()
	{
		return on_ground_tiles;
	}
	
	public Tile[][] getCeilingTiles()
	{
		return cieling_tiles;
	}
	
	public Vector2f getGridLocation(double x, double y)
	{
		int gridx = (int) (x / Tile.standardSize);
		int gridy = (int) (y / Tile.standardSize);
		
		return new Vector2f(gridx, gridy);
	}

	public Player getPlayer()
	{
		return player;
	}
}
