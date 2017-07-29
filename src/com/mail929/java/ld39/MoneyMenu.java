package com.mail929.java.ld39;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

public class MoneyMenu
{
	MenuItem coalProd;
	MenuItem coalBurn;
	MenuItem autoOxy;
	List<MenuItem> items;
	int selected = 0;
	
	public MoneyMenu()
	{
		items = new ArrayList<>();
		coalProd = new MenuItem("Increase coal production by 50%", 15, 15, 1000, true);
		items.add(coalProd);
		coalBurn = new MenuItem("Increase coal burn time by 60%", 15, 35, 1000, false);
		items.add(coalBurn);
		autoOxy = new MenuItem("Increase oxygen production by 60%", 15, 55, 1000, false);
		items.add(autoOxy);
	}
	
	public void draw(Graphics g)
	{
		g.setColor(Color.YELLOW);
		g.fillRect(0, 0, Window.width, Window.height);
		
		for(MenuItem item : items)
		{
			item.draw(g);
		}
	}

	public void run(){
		Game.log("Running");
	}

	public void keyChange(InGame game, KeyEvent e, boolean press)
	{
		if(press)
		{
			switch(e.getKeyCode())
			{
			case KeyEvent.VK_DOWN:
				selected++;
				break;
			case KeyEvent.VK_UP:
				selected--;
				break;
			case KeyEvent.VK_ENTER:
				if(game.money >= items.get(selected).cost)
				{
					game.money -= items.get(selected).cost;
					switch(selected)
					{
					case 0:
						game.coalRate /= 1.5;
						break;
					case 1:
						game.furnace.burnTime *= 1.6;
						break;
					case 2:
						game.bellow.airRate *= 1.6;
						break;
					}
				}
				break;
			case KeyEvent.VK_ESCAPE:
				game.menu = null;
				return;
			}
			
			if(selected < 0)
			{
				selected = 0;
			}
			else if(selected >= items.size())
			{
				selected = items.size() - 1;
			}
			
			for(int i = 0; i < items.size(); i++)
			{
				if(i == selected)
				{
					items.get(i).selected(true);
				}
				else
				{
					items.get(i).selected(false);
				}
			}
		}
	}

}
