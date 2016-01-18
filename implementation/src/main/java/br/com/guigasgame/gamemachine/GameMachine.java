package br.com.guigasgame.gamemachine;

import java.util.Stack;
import java.util.Vector;

import org.jsfml.graphics.RenderWindow;
import org.jsfml.window.Keyboard;
import org.jsfml.window.VideoMode;
import org.jsfml.window.event.Event;

public class GameMachine {
	private boolean isRunning;
	private RenderWindow renderWindow;
	private Vector<GameStateMachine> gameStates;

	public GameMachine() {
		super();
		renderWindow = new RenderWindow(new VideoMode(800, 600, 32), "Test");
		isRunning = true;
		gameStates = new Stack<GameStateMachine>();
	}

	public static void main(String[] args) {
		GameMachine gameMachine = new GameMachine();
		MainGameState mainGameState = new MainGameState();
		mainGameState.enterState();
		gameMachine.gameStates.add(mainGameState);
		gameMachine.execute();
	}

	private void execute() {
		gameStates.lastElement().load();
		gameLoop();
		gameStates.lastElement().unload();
		gameStates.lastElement().exitState();
		renderWindow.clear();
	}

	private void gameLoop() {
		while (isRunning) {
			renderWindow.clear();
			handleEvents();
			gameStates.lastElement().update();
			gameStates.lastElement().draw(renderWindow);
			renderWindow.display();
		}

	}

	private void handleEvents() {
		Iterable<Event> events = renderWindow.pollEvents();
		for (Event event : events) {

			if (event.type == Event.Type.KEY_PRESSED) {
				if (event.asKeyEvent().key != Keyboard.Key.ESCAPE)
					break;
				isRunning = false;
			}
			if (event.type == Event.Type.CLOSED) {
				isRunning = false;
			}
		}
	}

}
