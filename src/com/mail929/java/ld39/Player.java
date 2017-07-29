package com.mail929.java.ld39;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;

public class Player extends Entity implements Runnable
{
	int xTrans = 0;
	int yTrans = 0;
	Thread playerThread;
	Entity carrying;
	BufferedImage image;
	
	public Player(int startX, int startY, int width, int height)
	{
		super(startX, startY, width, height);
		playerThread = new Thread(this);
		playerThread.start();
		try
		{
			image = ImageIO.read(new File("res/player.png"));
		} catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	@Override
	public void draw(Graphics g)
	{
		/*g.setColor(Color.GREEN);
		g.fillRect(x, y, width, height);*/
		if(xTrans >= 0)
		{
			g.drawImage(image, x, y, width, height, null);
		}
		else
		{
			g.drawImage(image, x + width, y, -width, height, null);
		}
	}

	@Override
	public void run()
	{
		while(true)
		{
			x += xTrans;
			y += yTrans;

			
			if(carrying != null)
			{
				carrying.x = x;
				carrying.y = y;
			}
			
			try
			{
				Thread.sleep(5);
			} catch (InterruptedException e)
			{
				e.printStackTrace();
			}
		}
	}
	
	@Override
	public void run(InGame game)
	{
	}

	public void moveX(int adj)
	{
		xTrans += adj;
		if(xTrans > 1)
		{
			xTrans = 1;
		}
		else if(xTrans < -1)
		{
			xTrans = -1;
		}
	}
	
	public void moveY(int adj)
	{
		yTrans += adj;
		if(yTrans > 1)
		{
			yTrans = 1;
		}
		else if(yTrans < -1)
		{
			yTrans = -1;
		}
	}
	
	public void action(InGame game)
	{
		if(carrying != null)
		{
			pickup(game.entities);
		}
		else
		{
			if(getDistance(game.bellow) <= width)
			{
				game.bellow.push(game);
			}
			else if(getDistance(game.bellow) <= width)
			{
				game.bellow.push(game);
			}
			else
			{
				boolean fixed = false;
				for(Wire wire : game.wires)
				{
					if(wire.broken && getDistance(wire) <= width)
					{
						wire.attemptFix(game);
						fixed = true;
						break;
					}
				}
				if(!fixed)
				{
					pickup(game.entities);
				}
			}
		}
	}
	
	public void pickup(List<Entity> entities)
	{
		if(carrying == null)
		{
			int closest = -1;
			for(int i = 0; i < entities.size(); i++)
			{
				int distance = getDistance(entities.get(i));
				if(distance <= width)
				{
					boolean good = closest == -1;
					if(!good)
					{
						good = distance < getDistance(entities.get(closest));
					}
					if(good)
					{
						closest = i;
					}
				}
			}

			if(closest >= 0)
			{
				carrying = entities.get(closest);
				carrying.pickup(true);
			}
		}
		else
		{
			carrying.pickup(false);
			carrying = null;
		}
	}
	
	public int getDistance(Entity e)
	{
		int x = Math.abs(getX() - e.getX());
		int y = Math.abs(getY() - e.getY());
		double c2 = Math.pow(x, 2) + Math.pow(y, 2);
		return (int) Math.sqrt(c2); 
	}

	@Override
	public void pickup(boolean up){}
}
