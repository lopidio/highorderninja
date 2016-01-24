package br.com.guigasgame.gamemachine;

import java.util.Stack;
import java.util.Vector;

import org.jsfml.graphics.Color;
import org.jsfml.graphics.RenderWindow;
import org.jsfml.system.Clock;
import org.jsfml.window.Keyboard;
import org.jsfml.window.VideoMode;
import org.jsfml.window.event.Event;


public class GameMachine
{

	public final int FRAME_RATE = 60;
	private boolean isRunning;
	private RenderWindow renderWindow;
	private Vector<GameState> gameStates;

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
		gameState.enterState(renderWindow);
		gameStates.add(gameState);
	}

	public GameMachine()
	{
		
		for (VideoMode list: VideoMode.getFullscreenModes())
		{
			System.out.println(list.toString());
		}
		
		renderWindow = new RenderWindow(new VideoMode(1366,  768, 32), "Test");//, Window.FULLSCREEN); //Window.TRANSPARENT
		renderWindow.setFramerateLimit(FRAME_RATE);
		renderWindow.setVerticalSyncEnabled(true);
		
		isRunning = true;
		gameStates = new Stack<GameState>();
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
		// http://gafferongames.com/game-physics/fix-your-timestep/
/*
double t = 0.0;
double dt = 0.01;

double currentTime = hires_time_in_seconds();
double accumulator = 0.0;

while ( !quit )
{
    double newTime = time();
    double frameTime = newTime - currentTime;
    if ( frameTime > 0.25 )
        frameTime = 0.25;

    accumulator += frameTime;

    while ( accumulator >= dt )
    {
        integrate( currentState, t, dt );
        accumulator -= dt;
    }

    render( state );
} */
		Clock clock = new Clock();
		float remainingAcumulator = 0f;
		final float updateDelta = (float)1/FRAME_RATE;
		while (isRunning)
		{
			float iterationTime = clock.getElapsedTime().asSeconds();
			clock.restart();
		   // max frame time to avoid spiral of death
		    if ( iterationTime > 0.25f )
		    	iterationTime = 0.25f;    
		    
			renderWindow.clear(new Color(128,  128, 128));
			handleEvents();
			
			remainingAcumulator += iterationTime;
			while (remainingAcumulator >= iterationTime)
			{
				gameStates.lastElement().update(updateDelta);
				remainingAcumulator -= updateDelta;
			}
			
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
				if (event.asKeyEvent().key != Keyboard.Key.ESCAPE)
					break;
				isRunning = false;
			}
			if (event.type == Event.Type.CLOSED)
			{
				isRunning = false;
			}
		}
	}

}
