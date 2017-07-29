package com.mail929.java.ld39;

import java.awt.Color;
import java.awt.Graphics;

public class ProgressBar
{
	int maxValue;
	int value;
	int x;
	int y;
	int height;
	int width;
	String title;
	
	public ProgressBar(int startX, int startY, int width, int height, int value, int maxValue, String title)
	{
		x = startX;
		y = startY;
		this.width = width;
		this.height = height;
		this.value = value;
		this.maxValue = maxValue;
		this.title = title;
	}
	
	public void draw(Graphics g)
	{
		g.setColor(Color.BLACK);
		g.fillRect(x, y, width+2, height+2);
		double percent = (double)value / maxValue;
		int distance = (int) (percent * width);
		g.setColor(Color.GREEN);
		g.fillRect(x+1, y+1, distance, height);
		g.setColor(Color.WHITE);
		g.drawString(title, x + 5, y+g.getFont().getSize()+5);
	}
	
	public void update(int value, int maxValue)
	{
		this.value = value;
		this.maxValue = maxValue;
	}
	
}
