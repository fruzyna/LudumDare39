package com.mail929.java.ld39;

import java.io.File;

public class Generator extends AnimatedItem
{

	public Generator(int startX, int startY, int width, int height)
	{
		super(startX, startY, width, height, new File("res/generator.png"), new File("res/generatorB.png"));
	}

}
