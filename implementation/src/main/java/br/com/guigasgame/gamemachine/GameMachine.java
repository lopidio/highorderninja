package br.com.guigasgame.gamemachine;

import java.util.Stack;
import java.util.Vector;

import org.jsfml.graphics.RenderWindow;
import org.jsfml.window.Keyboard;
import org.jsfml.window.VideoMode;
import org.jsfml.window.event.Event;


public class GameMachine
{

	private boolean isRunning;
	private RenderWindow renderWindow;
	private Vector<GameStateMachine> gameStates;

	public static void main(String[] args) throws Exception
	{
		GameMachine gameMachine = new GameMachine();
		MainGameState mainGameState = new MainGameState();
		gameMachine.popState();
		gameMachine.addState(mainGameState);
		gameMachine.execute();
	}

	private void popState()
	{
		if (gameStates.size() > 0) gameStates.remove(gameStates.lastElement());
	}

	private void addState(MainGameState gameState)
	{
		gameState.load();
		gameState.enterState(renderWindow);
		gameStates.add(gameState);
	}

	public GameMachine()
	{
		super();
		renderWindow = new RenderWindow(new VideoMode(1000, 600, 32), "Test");
		renderWindow.setFramerateLimit(60);

		isRunning = true;
		gameStates = new Stack<GameStateMachine>();
	}

	private void execute()
	{
		gameStates.lastElement().load();
		gameLoop();
		gameStates.lastElement().unload();
		gameStates.lastElement().exitState();
		renderWindow.clear();
	}

	private void gameLoop()
	{
		while (isRunning)
		{
			renderWindow.clear();
			handleEvents();
			gameStates.lastElement().update();
			gameStates.lastElement().draw(renderWindow);
			renderWindow.display();
		}
	}

	private void handleEvents()
	{
		Iterable<Event> events = renderWindow.pollEvents();
		for( Event event : events )
		{
			gameStates.lastElement().handleEvent(event);
			if (event.type == Event.Type.KEY_PRESSED)
			{
				if (event.asKeyEvent().key != Keyboard.Key.ESCAPE) break;
				isRunning = false;
			}
			if (event.type == Event.Type.CLOSED)
			{
				isRunning = false;
			}
		}
	}

}
