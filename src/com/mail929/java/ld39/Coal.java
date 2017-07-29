package com.mail929.java.ld39;

import java.awt.Color;
import java.awt.Graphics;

public class Coal extends Entity
{
	boolean onConveyer;
	AnimatedItem conveyerBelt;
	
	public Coal(int startX, int startY, int width, int height, AnimatedItem conveyerBelt)
	{
		super(startX, startY, width, height);
		x -= width/2;
		y -= height/2;
		
		this.conveyerBelt = conveyerBelt;		
		onConveyer = true;
	}

	@Override
	public void draw(Graphics g)
	{
		g.setColor(Color.BLACK);
		g.fillRect(x, y, width, height);
	}

	@Override
	public void run(InGame game)
	{
		if(onConveyer)
		{
			if(x < conveyerBelt.x || x > conveyerBelt.x + conveyerBelt.width)
			{
				game.entities.remove(this);
			}
			x -= 10;
		}
	}

	@Override
	public void pickup(boolean up)
	{
		if(up)
		{
			onConveyer = false;
		}
		else
		{
			if(conveyerBelt.isOn(this))
			{
				onConveyer = true;
			}
		}
	}
}