package com.mail929.java.ld39;

import java.awt.Color;
import java.awt.Graphics;

public class AnimatedItem extends Entity
{
	int counts = 0;
	int resetCount = 2;
	
	public AnimatedItem(int startX, int startY, int width, int height)
	{
		super(startX, startY, width, height);
	}

	public void draw(Graphics g)
	{
		if(counts == resetCount)
		{
			counts = 0;
			//switch which image is drawn
		}
		g.setColor(Color.ORANGE);
		g.fillRect(x, y, width, height);
		counts++;
	}
	
	public boolean isOn(Entity e)
	{
		return e.getX() > x && e.getX() < x + width && e.getY() > y && e.getY() < y + height;
	}

	@Override
	public void run(InGame game){}

	@Override
	public void pickup(boolean up){}
}
