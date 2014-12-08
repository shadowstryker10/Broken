package ld.entities.projectiles;

import ld.entities.GameEntity;
import ld.frame.DisplayManager;
import ld.game.Game;

public class ProjectileEntity extends GameEntity implements IProjectileEntity
{
	protected float speedConstant;
	protected float speed;
	protected float damage;
	protected float despawnTime;
	
	protected float angle;
	protected float angledX, angledY;
	
	public void update()
	{
		speed = (speedConstant * (DisplayManager.getDelta()/2)) * ((float)Game.currentResolution / (float)64.0);
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
	public float getDamage()
	{
		return damage;
	}

	@Override
	public void setDamage(float damage)
	{
		this.damage = damage;
	}

	@Override
	public float getDespawnTime()
	{
		return despawnTime;
	}

	@Override
	public void setDespawnTime(float despawnTime)
	{
		this.despawnTime = despawnTime;
	}

}
