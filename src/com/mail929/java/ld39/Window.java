package com.mail929.java.ld39;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Paint;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Window extends JFrame implements Runnable, KeyListener
{
	public static int width = 800;
	public static int height = 800;
	public static int statusHeight = 20;
	
	Panel mainPanel;
	StatusPanel statusBar;
	Thread paintThread;
	
	public Window()
	{
		super("Ludum Dare 39");

		addKeyListener(this);

		statusBar = new StatusPanel();
		statusBar.setPreferredSize(new Dimension(width, statusHeight));
		add(statusBar, BorderLayout.NORTH);
		
		mainPanel = new Panel();
		add(mainPanel, BorderLayout.CENTER);
		
		paintThread = new Thread(this);
		paintThread.start();
		
		setSize(width, height + statusHeight + 30);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	}

	public class Panel extends JPanel
	{
		protected void paintComponent(Graphics g)
		{
			//background fill
			g.setColor(Color.GREEN);
			g.fillRect(0, 0, width, height);
			
			Game.mode.draw(g);
		}
	}
	
	public class StatusPanel extends JPanel
	{
		protected void paintComponent(Graphics g)
		{
			//background fill
			g.setColor(Color.GREEN);
			g.fillRect(0, 0, width, statusHeight);
			
			Game.mode.drawStatus(g);
		}
	}

	@Override
	public void run()
	{
		while(Game.running)
		{
			long start = System.currentTimeMillis();
			repaint();
			try
			{
				Thread.sleep((1000 / Game.frameRate) - (System.currentTimeMillis() - start));//full frame time - time took to draw
			} catch (InterruptedException e)
			{
				e.printStackTrace();
			}
		}
	}

	@Override
	public void keyTyped(KeyEvent e){}

	@Override
	public void keyPressed(KeyEvent e)
	{
		Game.mode.keyChange(e, true);		
	}

	@Override
	public void keyReleased(KeyEvent e)
	{
		Game.mode.keyChange(e, false);		
	}
}
