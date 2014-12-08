package ld.entities.projectiles;

import ld.entities.IEntity;

public interface IProjectileEntity extends IEntity
{
	public float getSpeed();
	public void setSpeed(float speed);
	
	public float getDamage();
	public void setDamage(float damage);
	
	public float getDespawnTime();
	public void setDespawnTime(float despawn);
}
