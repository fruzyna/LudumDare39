package com.mail929.java.ld39;

public class Game
{
	static boolean running;
	static int frameRate = 60;
	static boolean debugMode = true;
	static Mode mode;
	
	public static void main(String[] args)
	{
		new Game();
	}
	
	public static void log(String output)
	{
		String className = Thread.currentThread().getStackTrace()[3].getClassName();
		String methodName = Thread.currentThread().getStackTrace()[3].getMethodName();
		int lineNumber = Thread.currentThread().getStackTrace()[3].getLineNumber();
		
		//remove package from class name
		if(className.contains("."))
		{
			className = className.substring(className.lastIndexOf(".") + 1);
		}
		
		//only print if debug mode is enabled
		if(debugMode)
		{
			System.out.println("[" + className + ":" + methodName + "] " + output);
		}
	}
	
	Window window;
	Thread gameThread;
	
	public Game()
	{
		mode = new InGame();
		gameThread = new Thread(mode);
		running = true;
		gameThread.start();
		window = new Window();
	}
}
