package ld.entities;

import ld.entities.inventory.Inventory;

public interface ILivingEntity extends IEntity
{
	public int getHealth();
	public void setHealth(int health);
	public float getSpeed();
	public void setSpeed(float speed);
	
	public boolean hasInventory();
	public void setHasInventory(boolean hasInventory);
	public Inventory getInventory();
	
}
