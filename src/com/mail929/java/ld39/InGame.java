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
	int energyDemand = 10;
	int energyProduction = 0;
	int storedEnergy = 0;
	int maxStored = 10000;
	int demand = 0;
	int money = 0;
	double productionMulti = 1;
	double coalRate = .75;
	
	Player player;
	List<Entity> entities;
	List<AnimatedItem> animatedItems;
	int blockWidth;
	int blockHeight;
	Furnace furnace;
	Bellow bellow;
	List<Wire> wires;
	Generator leftGen;
	Generator rightGen;
	ProgressBar oxyLevel;
	ProgressBar prodLevel;
	ProgressBar storedLevel;
	ProgressBar demandLevel;
	
	MoneyMenu menu;
	
	
	public InGame()
	{
		blockWidth = Window.width / 10;
		blockHeight = Window.height / 10;
		
		//add player
		player = new Player(blockWidth, blockHeight, Window.height / 10, Window.height / 10);
		
		//entity lists
		entities = new ArrayList<>();
		animatedItems = new ArrayList<>();
		
		//add furnace
		int furnaceWidth = blockWidth * 2;
		furnace = new Furnace((Window.width - furnaceWidth) / 2, 0, furnaceWidth, blockHeight);
		animatedItems.add(furnace);

		//add conveyer belt
		animatedItems.add(new AnimatedItem(0, Window.height - blockHeight, Window.width, blockHeight, new File("res/conveyer.png"), new File("res/conveyerB.png")));
		
		//add conveyer belt
		bellow = new Bellow((Window.width + blockWidth) / 2, blockHeight , blockWidth, blockHeight);
		animatedItems.add(bellow);

		//add generators
		leftGen = new Generator(Window.width / 2 - furnaceWidth, 0, blockWidth, blockHeight);
		animatedItems.add(leftGen);
		rightGen = new Generator(Window.width / 2 + furnaceWidth/2, 0, blockWidth, blockHeight);
		animatedItems.add(rightGen);

		wires = new ArrayList<>();
		animatedItems.add(new Wire(0, 0, blockWidth*3, blockHeight));
		wires.add((Wire)animatedItems.get(animatedItems.size() - 1));
		animatedItems.add(new Wire(Window.width - blockWidth*3, 0, blockWidth*3, blockHeight));
		wires.add((Wire)animatedItems.get(animatedItems.size() - 1));
		
		//add progress bars
		oxyLevel = new ProgressBar(0, 0, 100, 20, furnace.oxyLevel, furnace.maxOxy, "Oxygen");
		prodLevel = new ProgressBar(105, 0, 100, 20, energyProduction, furnace.maxProduction, "Production");
		storedLevel = new ProgressBar(210, 0, 100, 20, storedEnergy, maxStored, "Stored Energy");
		demandLevel = new ProgressBar(315, 0, 100, 20, 1, 1, "Fullfillment");
	}
	
	@Override
	public void draw(Graphics g)
	{		
		//draw floor
		g.setColor(Color.DARK_GRAY);
		g.fillRect(0, 0, Window.width, Window.height);

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
		
		if(menu != null)
		{
			menu.draw(g);
		}
	}

	@Override
	public void drawStatus(Graphics g)
	{
		//draw floor
		g.setColor(Color.DARK_GRAY);
		g.fillRect(0, 0, Window.width, Window.statusHeight);
		
		oxyLevel.draw(g);
		prodLevel.draw(g);
		storedLevel.draw(g);
		demandLevel.draw(g);
		
		g.setColor(Color.GREEN);
		g.drawString("Money: " + money, 420, 2+g.getFont().getSize());
	}
	
	@Override
	public void run()
	{
		for(int loops = 0; Game.running; loops++)
		{
			while(menu != null)
			{
				try
				{
					Thread.sleep(50);
				} catch (InterruptedException e)
				{
					e.printStackTrace();
				}
			}
			long start = System.currentTimeMillis();
			
			demand = loops;
			
			energyProduction *= productionMulti;
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
			if(random > coalRate)
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
				entities.add(new Coal(Window.width, Window.height - height, blockWidth/5, blockHeight/5, animatedItems.get(1)));
			}
			
			storedEnergy -= demand;
			if(storedEnergy > 0)
			{
				demandLevel.update(demand, demand);
				money += demand;
			}
			else
			{
				demandLevel.update(demand + storedEnergy, demand);
				money += demand + storedEnergy;
				storedEnergy = 0;
			}
			oxyLevel.update(furnace.oxyLevel, furnace.maxOxy);
			prodLevel.update(energyProduction, furnace.maxProduction);
			storedLevel.update(storedEnergy, maxStored);
			
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
		if(menu != null)
		{
			menu.keyChange(this, e, press);
		}
		else if(press)
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
				player.action(this);
				break;
			case KeyEvent.VK_M:
				menu = new MoneyMenu();
				break;
			}
		}
	}
}
