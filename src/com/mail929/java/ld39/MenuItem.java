package com.mail929.java.ld39;

import java.awt.Color;
import java.awt.Graphics;

public class MenuItem
{
	String title;
	int x;
	int y;
	boolean selected;
	int cost;
	
	public MenuItem(String title, int x, int y, int cost, boolean selected)
	{
		this.title = title;
		this.x = x;
		this.y = y;
		this.cost = cost;
		this.selected = selected;
	}
	
	public void draw(Graphics g)
	{
		g.setColor(Color.BLACK);
		g.drawRect(x, y, 15, 15);
		if(selected)
		{
			g.drawString("BUY!", x + 5, y + g.getFont().getSize());
		}
		g.drawString(title + " $" + cost, x + 25, y + g.getFont().getSize());
	}
	
	public void selected(boolean selected)
	{
		this.selected = selected;
	}
}
