package ld.entities;

import java.awt.Rectangle;

import ld.entities.inventory.Inventory;
import ld.game.Game;

public class LivingGameEntity extends GameEntity implements ILivingEntity
{
	protected int health;
	protected float speedConstant;
	protected float speed;
	protected Inventory inventory;

	public LivingGameEntity()
	{
		super();
		speed = speedConstant * ((float)Game.currentResolution / 64.0f);
	}
	
	public void update()
	{
		speed = speedConstant * ((float)Game.currentResolution / 64.0f);
	}
	
	public Rectangle getBounds()
	{
		return collisionBounds;
	}
	
	@Override
	public int getHealth()
	{
		return health;
	}

	@Override
	public void setHealth(int health)
	{
		this.health = health;
	}

	@Override
	public float getSpeed()
	{
		return speed;
	}

	@Override
	public void setSpeed(float speed)
	{
		this.speed = speed;
	}

	@Override
	public boolean hasInventory()
	{
		return (inventory != null);
	}

	@Override
	public void setHasInventory(boolean hasInventory)
	{
		if(hasInventory) inventory = new Inventory();
		else inventory = null;
	}

	@Override
	public Inventory getInventory()
	{
		return inventory;
	}

}
