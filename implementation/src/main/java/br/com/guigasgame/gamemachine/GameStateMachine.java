package br.com.guigasgame.gamemachine;

import org.jsfml.graphics.RenderWindow;

public interface GameStateMachine 
{
	public default void enterState()
	{
		
	}
	public void update();
	public void draw(RenderWindow renderWindow);
	public default void exitState()
	{
		
	}
	public default void load()
	{
		
	}
	public default void unload()
	{
		
	}
}
