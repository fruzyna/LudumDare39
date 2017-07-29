package com.mail929.java.ld39;

import java.awt.Graphics;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class AnimatedItem extends Entity
{
	int counts = 0;
	int resetCount = 30;
	BufferedImage image;
	BufferedImage imageB;
	boolean a = true;
	
	public AnimatedItem(int startX, int startY, int width, int height, File imageFile, File imageFileB)
	{
		super(startX, startY, width, height);
		try
		{
			image = ImageIO.read(imageFile);
			imageB = ImageIO.read(imageFileB);
		} catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	public void draw(Graphics g)
	{
		if(counts == resetCount)
		{
			counts = 0;
			a = !a;
			//switch which image is drawn
		}
		/*g.setColor(Color.ORANGE);
		g.fillRect(x, y, width, height);*/
		
		if(a)
		{
			g.drawImage(image, x, y, width, height, null);
		}
		else
		{
			g.drawImage(imageB, x, y, width, height, null);
		}
		/*for(int i = 0; i * image.getWidth() < width; i++)
		{
			if(a)
			{
				g.drawImage(image, x+(i * image.getWidth()), y, null);
			}
			else
			{
				g.drawImage(imageB, x+(i * image.getWidth()), y, null);
			}
		}*/
		
		counts++;
	}
	
	public boolean isOn(Entity e)
	{
		return e.getX() > x && e.getX() < x + width && e.getY() > y && e.getY() < y + height;
	}

	@Override
	public void run(InGame game){}

	@Override
	public void pickup(boolean up){}
}
