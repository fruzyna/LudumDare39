package com.mail929.java.ld39;

import java.awt.Graphics;

public abstract class Entity
{
	int x;
	int y;
	int height;
	int width;
	
	public Entity(int startX, int startY, int width, int height)
	{
		x = startX;
		y = startY;
		this.width = width;
		this.height = height;
	}
	
	public abstract void draw(Graphics g);
	
	public abstract void run(InGame game);
	
	public abstract void pickup(boolean up);
	
	public int getX()
	{
		return x + width/2;
	}
	
	public int getY()
	{
		return y + height/2;
	}
}
