package com.mail929.java.ld39;

import java.io.File;

public class Furnace extends AnimatedItem
{
	int coal;
	int oxyLevel;
	int maxOxy = 100;
	int maxCoal = 15;
	int maxProduction;
	int burnTime = 5;
	
	public Furnace(int startX, int startY, int width, int height)
	{
		super(startX, startY, width, height, new File("res/furnace.png"), new File("res/furnaceB.png"));
		coal = 0;
		oxyLevel = 1;
	}

	@Override
	public void run(InGame game)
	{
		maxProduction = maxCoal * maxOxy;
		game.energyProduction = (int) (coal * oxyLevel * game.productionMulti);
		if(game.storedEnergy > game.maxStored)
		{
			game.storedEnergy = game.maxStored;
		}
		if(coal > 0)
		{
			game.storedEnergy += game.energyProduction;
			coal--;
		}
		if(oxyLevel > 1)
		{
			oxyLevel -= 2;
			if(oxyLevel < 1)
			{
				oxyLevel = 1;
			}
		}
		if(oxyLevel > maxOxy)
		{
			oxyLevel = maxOxy;
		}
		
		for(int i = 0; i < game.entities.size(); i++)
		{
			Entity e = game.entities.get(i);
			if(isOn(e) && e instanceof Coal && game.player.carrying != e && coal <= maxCoal)
			{
				game.entities.remove(i);
				coal += burnTime;
				if(coal > maxCoal)
				{
					coal = maxCoal;
				}
			}
		}
	}
}
