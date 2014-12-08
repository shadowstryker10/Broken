package ld.entities.player;

import ld.entities.projectiles.ProjectileEntity;
import ld.entities.projectiles.ProjectilePlayerBullet;
import ld.frame.DisplayManager;
import ld.game.Dungeon;
import ld.game.Game;
import ld.graphics.Tile;
import ld.io.Preferences;
import ld.main.Main;
import ld.math.MathUtil;
import ld.sound.Sound;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

public class PlayerInputHandler
{
	Player player;
	Game game;
	Dungeon dungeon;
	
	public PlayerInputHandler(Player player)
	{
		this.player = player;
		this.game = Main.getMain().getGame();
	}
	
	public void update()
	{
		this.game = Main.getMain().getGame();
		this.dungeon = game.getDungeon();
		
		updateKeyboard();
		updateMouse();
	}
	
	private void updateKeyboard()
	{
		if(Keyboard.isKeyDown(Keyboard.KEY_W))
		{
			float newY = player.y + (player.getSpeed() * DisplayManager.getDelta());

			if(!player.topCollidesWith(dungeon.getGroundTileAt(player.x, newY + player.getEntitySize())))
			 if(!player.topCollidesWith(dungeon.getGroundTileAt(player.x + player.getEntitySize(), newY + player.getEntitySize()))) 
				 player.y = newY;

			if(player.topCollidesWith(dungeon.getGroundTileAt(player.x, player.y + player.getEntitySize())))
				 player.y = dungeon.getGroundTileAt(player.x, player.y + player.getEntitySize()).getY() - player.getEntitySize();
			if(player.leftCollidesWith(dungeon.getGroundTileAt(player.x, player.y)))
					 player.x = dungeon.getGroundTileAt(player.x, player.y + player.getEntitySize()).getX() + Tile.standardSize;
			if(player.rightCollidesWith(dungeon.getGroundTileAt(player.x + player.getEntitySize(), player.y)))
					 player.x = dungeon.getGroundTileAt(player.x + player.getEntitySize(), player.y).getX() - player.getEntitySize();
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_S))
		{
			float newY = player.y - (player.getSpeed() * DisplayManager.getDelta());

			if(!player.botCollidesWith(dungeon.getGroundTileAt(player.x, newY)))
			 if(!player.botCollidesWith(dungeon.getGroundTileAt(player.x + player.getEntitySize(), newY))) 
				 player.y = newY;
			
			if(player.botCollidesWith(dungeon.getGroundTileAt(player.x, player.y)))
				 player.y = dungeon.getGroundTileAt(player.x, player.y).getY() + Tile.standardSize;
			if(player.leftCollidesWith(dungeon.getGroundTileAt(player.x, player.y)))
					 player.x = dungeon.getGroundTileAt(player.x, player.y + player.getEntitySize()).getX() + Tile.standardSize;
			if(player.rightCollidesWith(dungeon.getGroundTileAt(player.x + player.getEntitySize(), player.y)))
					 player.x = dungeon.getGroundTileAt(player.x + player.getEntitySize(), player.y).getX() - player.getEntitySize();
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_A))
		{
			float newX = player.x - player.getSpeed() * DisplayManager.getDelta();

			if(!player.leftCollidesWith(dungeon.getGroundTileAt(newX, player.y)))
			 if(!player.leftCollidesWith(dungeon.getGroundTileAt(newX, player.y + player.getEntitySize()))) 
				 player.x = newX;

			if(player.leftCollidesWith(dungeon.getGroundTileAt(player.x, player.y)))
				 player.x = dungeon.getGroundTileAt(player.x, player.y + player.getEntitySize()).getX() + Tile.standardSize;
			if(player.topCollidesWith(dungeon.getGroundTileAt(player.x, player.y + player.getEntitySize())))
					 player.y = dungeon.getGroundTileAt(player.x, player.y + player.getEntitySize()).getY() - player.getEntitySize();
			if(player.botCollidesWith(dungeon.getGroundTileAt(player.x, player.y)))
					 player.y = dungeon.getGroundTileAt(player.x, player.y).getY() + Tile.standardSize;
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_D))
		{	
			float newX = player.x + player.getSpeed() * DisplayManager.getDelta();

			if(!player.rightCollidesWith(dungeon.getGroundTileAt(newX + player.getEntitySize(), player.y)))
			 if(!player.rightCollidesWith(dungeon.getGroundTileAt(newX + player.getEntitySize(), player.y + player.getEntitySize()))) 	 
				 player.x = newX;

			if(player.rightCollidesWith(dungeon.getGroundTileAt(player.x + player.getEntitySize(), player.y)))
				 player.x = dungeon.getGroundTileAt(player.x + player.getEntitySize(), player.y).getX() - player.getEntitySize();
			if(player.topCollidesWith(dungeon.getGroundTileAt(player.x, player.y + player.getEntitySize())))
					 player.y = dungeon.getGroundTileAt(player.x, player.y + player.getEntitySize()).getY() - player.getEntitySize();
			if(player.botCollidesWith(dungeon.getGroundTileAt(player.x, player.y)))
					 player.y = dungeon.getGroundTileAt(player.x, player.y).getY() + Tile.standardSize;
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_SPACE))
		{

			Game.changeResolution(Game.RESOLUTIONS[3]);
		}
	}
	
	private void updateMouse()
	{
		while(Mouse.next())
		{
			if(Mouse.getEventButtonState())
			{
				if(Mouse.getEventButton() == 0)
				{
					if(game.getState() == Game.IN_GAME)
					{
						float angle = MathUtil.getAngle(player.x + (player.getEntitySize() / 2), player.y + (player.getEntitySize() / 2), Mouse.getX(), Mouse.getY());
						
						game.getDungeon().spawnEntity(new ProjectilePlayerBullet(player.getCenter().x, player.getCenter().y, angle));
						if(!Preferences.mute)
							Sound.playShootSound();
					}
				}
			}
		}
	}
}
