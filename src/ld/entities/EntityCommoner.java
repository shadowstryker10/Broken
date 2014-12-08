package ld.entities;

import static org.lwjgl.opengl.GL11.GL_QUADS;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glBindTexture;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glTexCoord2d;
import static org.lwjgl.opengl.GL11.glVertex2d;

import java.awt.Rectangle;

import ld.entities.player.Player;
import ld.frame.DisplayManager;
import ld.game.Game;
import ld.graphics.Textures;
import ld.main.Main;

import org.newdawn.slick.opengl.Texture;

public class EntityCommoner extends LivingGameEntity
{
	Texture[] textures = new Texture[3];
	
	public int ai_function;
	public static final int AI_FUNCTION_CHASE_PLAYER = 0;
	public static final int AI_FUNCTION_CHASE_COLOR = 1;
	
	public EntityCommoner(int gridx, int gridy, int AI_FUNCTION, int texture)
	{
		textures[0] = Textures.commoner_black;
		super.textures = this.textures;
		speedConstant = 0.05f;
		entitySize = (int) (Game.currentResolution / 1.33);
		collisionBounds = new Rectangle((int)entitySize, (int)entitySize);
		
		super.x = (gridx * Game.currentResolution);
		super.y = (gridy * Game.currentResolution);
		
		ai_function = AI_FUNCTION;
	}
	
	public void update()
	{
		super.update();
		if(previousResolution != Game.currentResolution)
		{
			entitySize = (int) (Game.currentResolution / 1.33);     
		}

		previousResolution = currentResolution;
		currentResolution = Game.currentResolution;
		
		runAI();
		render();
	}
	
	public void render()
	{
		glBindTexture(GL_TEXTURE_2D, textures[0].getTextureID());
		glBegin(GL_QUADS);
		{			
			glTexCoord2d(1 / textures[0].getWidth(),  1 / textures[0].getHeight());
			glVertex2d(0 + x, 0 + y);

			glTexCoord2d(1 / textures[0].getWidth(), 0);
			glVertex2d(0 + x, entitySize + y);

			glTexCoord2d(0, 0);
			glVertex2d(entitySize + x, entitySize + y);

			glTexCoord2d(0, 1 / textures[0].getHeight());
			glVertex2d(entitySize + x, 0 + y);
		}
		glEnd();
		glBindTexture(GL_TEXTURE_2D, 0);
	}
	
	public void runAI()
	{
		Player player = Main.getMain().getGame().getDungeon().getPlayer();
		
		float movement = speed * DisplayManager.getDelta();

		if(ai_function == AI_FUNCTION_CHASE_PLAYER)
		{
			if(x < player.x) x += movement;
			if(x > player.x) x -= movement;
			if(y < player.y) y += movement;
			if(y > player.y) y -= movement;
		}
	}
}
