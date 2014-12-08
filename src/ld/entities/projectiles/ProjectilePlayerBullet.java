package ld.entities.projectiles;

import java.awt.Rectangle;
import java.util.List;

import ld.entities.GameEntity;
import ld.game.Dungeon;
import ld.game.Game;
import ld.graphics.Textures;
import ld.io.Preferences;
import ld.main.Main;
import ld.sound.Sound;

import org.lwjgl.opengl.Display;
import org.newdawn.slick.opengl.Texture;

public class ProjectilePlayerBullet extends ProjectileEntity
{
	Texture[] textures = new Texture[1];
	Dungeon dungeon;
	
	public ProjectilePlayerBullet(float x, float y, float angle)
	{
		textures[0] = Textures.projectile_player_bullet;
		super.textures = textures;
		
		this.x = x;
		this.y = y;
		
		entitySize = (int) (Game.currentResolution / 4);
		this.collisionBounds = new Rectangle(entitySize, entitySize);
		speedConstant = 1.0f;
		super.angle = angle;
	}
	
	public void update()
	{
		super.update();
		dungeon = Main.getMain().getGame().getDungeon();
		
		if(previousResolution != Game.currentResolution)
		{
			entitySize = (int) (Game.currentResolution / 4);
		}

		previousResolution = currentResolution;
		currentResolution = Game.currentResolution;
		
		
		x += Math.cos(angle) * speed;
		y += Math.sin(angle) * speed;
		
		if(x < 0 || x > Display.getWidth() || y < 0 || y > Display.getHeight())
		{
			dungeon.killEntity(this);
		}
		
		List<GameEntity> entities = dungeon.getEntityList();
		for(int i = 0; i < entities.size(); i++)
		{
			if(collidesWith(entities.get(i)) && !(entities.get(i) instanceof ProjectilePlayerBullet))
			{
				dungeon.killEntity(entities.get(i));
				dungeon.killEntity(this);
				if(!Preferences.mute)
					Sound.playKillSound();
			}
		}
		
		render();
	}
}
