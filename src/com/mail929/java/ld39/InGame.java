package com.mail929.java.ld39;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class InGame extends Mode
{
	int speed = 1000;
	double floorPercent = .9;
	int energyDemand = 10;
	int energyProduction = 0;
	int storedEnergy = 0;
	Player player;
	List<Entity> entities;
	List<AnimatedItem> animatedItems;
	int xEdges;
	int yEdges;
	
	public InGame()
	{
		xEdges = (int) ((Window.width * (1 - floorPercent))/2);
		yEdges = (int) ((Window.height * (1 - floorPercent))/2);
		
		//add player
		player = new Player(50, 50, Window.height / 10, Window.height / 10);
		
		//entity lists
		entities = new ArrayList<>();
		animatedItems = new ArrayList<>();
		
		//add furnace
		int furnaceWidth = 100;
		animatedItems.add(new Furnace((Window.width - furnaceWidth) / 2, yEdges, furnaceWidth, 50));
		
		//add conveyer belt
		int beltHeight = 50;
		animatedItems.add(new AnimatedItem(xEdges, Window.height - yEdges - beltHeight, (int)(Window.width * floorPercent), beltHeight, new File("res/conveyer.png")));
	}
	
	@Override
	public void draw(Graphics g)
	{
		//draw walls
		g.setColor(Color.RED);
		g.fillRect(0, 0, Window.width, Window.height);
		
		//draw floor
		g.setColor(Color.DARK_GRAY);
		g.fillRect(xEdges, yEdges, (int) (Window.width * floorPercent), (int) (Window.height * floorPercent));

		//draw animated items
		for(AnimatedItem item : animatedItems)
		{
			item.draw(g);
		}

		//draw player
		player.draw(g);
	
		//draw entities
		for(Entity entity : entities)
		{
			entity.draw(g);
		}
		
		g.drawString("Energy Production: " + energyProduction + " Stored Energy: " + storedEnergy, 12, 12);
	}

	@Override
	public void run()
	{
		for(int loops = 0; Game.running; loops++)
		{
			long start = System.currentTimeMillis();
			
			//run animated items
			for(AnimatedItem item : animatedItems)
			{
				item.run(this);
			}
			
			//run entities
			for(int i = 0; i < entities.size(); i++)
			{
				entities.get(i).run(this);
			}
			
			double random = Math.random();
			if(random > 0.75)
			{
				random = 1;
			}
			else
			{
				random = 0;
			}
			for(int i = 0; i < random; i++)
			{
				int height = (int)(Math.random() * animatedItems.get(1).height);
				entities.add(new Coal((int)(Window.width * floorPercent) + xEdges, Window.height - yEdges - height, 10, 10, animatedItems.get(1)));
			}
			
			try
			{
				Thread.sleep(speed - (System.currentTimeMillis() - start));
			} catch (InterruptedException e)
			{
				e.printStackTrace();
			}
			
			if(loops % 100 == 0)
			{
				speed *= 0.75;
			}
		}
	}

	@Override
	public void keyChange(KeyEvent e, boolean press)
	{
		if(press)
		{
			switch(e.getKeyCode())
			{
			case KeyEvent.VK_UP:
				player.moveY(-1);
				break;
			case KeyEvent.VK_DOWN:
				player.moveY(1);
				break;
			case KeyEvent.VK_LEFT:
				player.moveX(-1);
				break;
			case KeyEvent.VK_RIGHT:
				player.moveX(1);
				break;
			}
		}
		else
		{
			switch(e.getKeyCode())
			{
			case KeyEvent.VK_UP:
				player.moveY(1);
				break;
			case KeyEvent.VK_DOWN:
				player.moveY(-1);
				break;
			case KeyEvent.VK_LEFT:
				player.moveX(1);
				break;
			case KeyEvent.VK_RIGHT:
				player.moveX(-1);
				break;
			case KeyEvent.VK_E:
				player.pickup(entities);
			}
		}
	}
}
