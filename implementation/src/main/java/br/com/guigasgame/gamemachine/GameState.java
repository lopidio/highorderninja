package br.com.guigasgame.gamemachine;

import org.jsfml.graphics.RenderWindow;
import org.jsfml.window.event.Event;


public interface GameState
{

	public void update(float updateDelta);

	public void draw(RenderWindow renderWindow);

	public default void enterState(RenderWindow renderWindow)
	{

	}

	public default void exitState()
	{

	}

	public default void load()
	{

	}

	public default void unload()
	{

	}

	public default void handleEvent(Event event)
	{

	}
}
