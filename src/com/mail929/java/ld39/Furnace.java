package com.mail929.java.ld39;

import java.io.File;

public class Furnace extends AnimatedItem
{
	int coal;
	int oxyLevel;
	
	public Furnace(int startX, int startY, int width, int height)
	{
		super(startX, startY, width, height, new File("res/furnace.png"));
		coal = 0;
		oxyLevel = 1;
	}

	@Override
	public void run(InGame game)
	{
		game.energyProduction = coal;
		if(coal > 0)
		{
			game.storedEnergy += coal * oxyLevel;
			coal--;
		}
		if(oxyLevel > 1)
		{
			oxyLevel--;
		}
		
		for(int i = 0; i < game.entities.size(); i++)
		{
			Entity e = game.entities.get(i);
			if(isOn(e) && e instanceof Coal && game.player.carrying != e)
			{
				game.entities.remove(i);
				coal += 5;
			}
		}
	}
}
