package com.mail929.java.ld39;

import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

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
	
	public static void playSound(File soundFile)
	{
	    AudioInputStream stream;
	    AudioFormat format;
	    DataLine.Info info;
	    Clip clip;

	    try
		{
			stream = AudioSystem.getAudioInputStream(soundFile);
		    format = stream.getFormat();
		    info = new DataLine.Info(Clip.class, format);
		    clip = (Clip) AudioSystem.getLine(info);
		    clip.open(stream);
		    clip.start();
		} catch (UnsupportedAudioFileException | IOException | LineUnavailableException e)
		{
			e.printStackTrace();
		}
	}
}
