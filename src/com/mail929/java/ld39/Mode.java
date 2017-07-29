package com.mail929.java.ld39;

import java.awt.Graphics;
import java.awt.event.KeyEvent;

public abstract class Mode implements Runnable
{
	public abstract void draw(Graphics g);
	public abstract void drawStatus(Graphics g);
	
	@Override
	public abstract void run();
	
	public abstract void keyChange(KeyEvent e, boolean press);
}
