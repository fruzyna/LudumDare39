package com.mail929.java.ld39;

import java.io.File;

public class Bellow extends AnimatedItem
{
	int airRate = 5;
	
	public Bellow(int startX, int startY, int width, int height)
	{
		super(startX, startY, width, height, new File("res/bellow.png"));
	}

	@Override
	public void run(InGame game)
	{
	}
	
	public void push(InGame game)
	{
		game.furnace.oxyLevel += airRate;
	}
}
