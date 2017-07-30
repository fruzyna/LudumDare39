package com.mail929.java.ld39;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Wire extends AnimatedItem
{
	boolean broken;
	
	public Wire(int startX, int startY, int width, int height)
	{
		super(startX, startY, width, height, new File("res/wire.png"), new File("res/wireB.png"));
	}

	@Override
	public void run(InGame game)
	{
		if(game.energyProduction >= game.furnace.maxProduction * game.productionMulti && !broken)
		{
			broken = true;
			game.productionMulti -= 0.5;
			game.energyProduction = 0;
			Game.playSound(new File("res/wire-burst.wav"));
			try
			{
				image = ImageIO.read(new File("res/wire-broken.png"));
			} catch (IOException e)
			{
				e.printStackTrace();
			}
		}
	}
	
	public void attemptFix(InGame game)
	{
		double chance = Math.random();
		if(chance < 0.1)
		{
			broken = false;
			game.productionMulti += 0.5;
			try
			{
				image = ImageIO.read(new File("res/wire.png"));
			} catch (IOException e)
			{
				e.printStackTrace();
			}
		}
	}
}
