package br.com.guigasgame.gamemachine;

import br.com.guigasgame.color.ColorBlender;
import org.jsfml.graphics.RenderWindow;
import org.jsfml.system.Clock;
import org.jsfml.window.Keyboard;
import org.jsfml.window.VideoMode;
import org.jsfml.window.event.Event;

import java.util.Arrays;
import java.util.Stack;
import java.util.Vector;


public class GameMachine
{
	private static GameMachine gameMachine;

	public static GameMachine getInstance()
	{
		if (gameMachine == null)
			gameMachine = new GameMachine();
		return gameMachine;
	}

	private final int FRAME_RATE = 60;
	private boolean isRunning;
	private RenderWindow renderWindow;
	private Vector<GameState> gameStates;
	private ColorBlender backgroundColor;

	public static void main(String[] args) throws Exception
	{
		GameMachine gameMachine = getInstance();

		gameMachine.popState();
		gameMachine.addState(new SplashScreenGameState(2f));
		gameMachine.execute();
	}

	private void popState()
	{
		if (gameStates.size() > 0)
			gameStates.remove(gameStates.lastElement());
	}

	private void addState(GameState gameState)
	{
		gameState.enterState(renderWindow);
		gameStates.add(gameState);
		backgroundColor = gameStates.lastElement().getBackgroundColor();
		if (backgroundColor == null)
			backgroundColor = new ColorBlender(200, 200, 250, 200);
	}

	private GameMachine()
	{
        backgroundColor = new ColorBlender(200, 200, 250, 200);

		VideoMode[] modes = VideoMode.getFullscreenModes();
		Arrays.sort(modes, (firstMode, secondMode) -> {
            int retorno = firstMode.height * firstMode.width - secondMode.height * secondMode.width;
            if (retorno == 0)
                return firstMode.bitsPerPixel - secondMode.bitsPerPixel;
            return retorno;
        });
//		for( VideoMode videoMode : modes )
//		{
//			System.out.println(videoMode);
//		}
		final VideoMode best = modes[modes.length - 1];
//		final VideoMode worst = modes[0];

		renderWindow = new RenderWindow(best, "High Order Ninja");//, Window.FULLSCREEN);  //Window.TRANSPARENT
//		renderWindow = new RenderWindow(worst, "High Order Ninja");//, Window.FULLSCREEN);  //Window.TRANSPARENT
		renderWindow.setFramerateLimit(FRAME_RATE);
		renderWindow.setVerticalSyncEnabled(true);
		renderWindow.setMouseCursorVisible(false);

		isRunning = true;
		gameStates = new Stack<>();
	}

	private void execute()
	{
//		gameStates.lastElement().load();
		gameLoop();
		gameStates.lastElement().unload();
		gameStates.lastElement().exitState();
//		renderWindow.clear();
	}

	private void gameLoop()
	{
		// http://gafferongames.com/game-physics/fix-your-timestep/
		Clock clock = new Clock();
		float remainingAccumulator = 0f;
		final float updateDelta = (float) 1 / FRAME_RATE;
		while (isRunning)
		{
			float iterationTime = clock.restart().asSeconds();

			// max frame time to avoid spiral of death
			if (iterationTime > 0.25f)
				iterationTime = 0.25f;

			renderWindow.clear(backgroundColor.getSfmlColor());
			handleEvents();

			remainingAccumulator += iterationTime;
			while (remainingAccumulator >= iterationTime)
			{
				gameStates.lastElement().update(updateDelta);
				remainingAccumulator -= updateDelta;
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
			gameStates.lastElement().handleEvent(event, renderWindow);
			if (event.type == Event.Type.KEY_PRESSED)
			{
				if (event.asKeyEvent().key == Keyboard.Key.ESCAPE)
				{
					isRunning = false;
					break;
				}
			}
			if (event.type == Event.Type.CLOSED)
			{
				isRunning = false;
			}
		}
	}

	void switchState(GameState gameState)
	{
		popState();
		addState(gameState);
	}

}
