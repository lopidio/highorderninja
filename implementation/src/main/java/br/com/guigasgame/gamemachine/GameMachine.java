package br.com.guigasgame.gamemachine;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.Vector;

import org.jbox2d.common.Vec2;
import org.jsfml.graphics.RenderWindow;
import org.jsfml.system.Clock;
import org.jsfml.window.Keyboard;
import org.jsfml.window.VideoMode;
import org.jsfml.window.event.Event;

import br.com.guigasgame.file.FilenameConstants;
import br.com.guigasgame.gameobject.hero.GameHeroProperties;
import br.com.guigasgame.gameobject.input.hero.GameHeroInputMap;
import br.com.guigasgame.gameobject.input.hero.GameHeroInputMap.HeroInputDevice;
import br.com.guigasgame.scenery.Scenery;
import br.com.guigasgame.scenery.SceneryFile;
import br.com.guigasgame.team.HeroTeam;


public class GameMachine
{

	public final int FRAME_RATE = 60;
	private boolean isRunning;
	private RenderWindow renderWindow;
	private Vector<GameState> gameStates;

	public static void main(String[] args) throws Exception
	{
		GameMachine gameMachine = new GameMachine();

		List<HeroTeam> teams = new ArrayList<>();
		
		HeroTeam teamAlpha = new HeroTeam(0);
		HeroTeam teamBravo = new HeroTeam(1);
		HeroTeam teamCharlie = new HeroTeam(2);
		
		GameHeroProperties playerOne = new GameHeroProperties(GameHeroInputMap.loadConfigFileFromDevice(HeroInputDevice.JOYSTICK), 0);
		playerOne.setSpawnPosition(new Vec2(10, 5));
		teamAlpha.addGameHero(playerOne);
		
		GameHeroProperties playerTwo = new GameHeroProperties(GameHeroInputMap.loadConfigFileFromDevice(HeroInputDevice.KEYBOARD), 1);
		playerTwo.setSpawnPosition(new Vec2(40, 5));
		teamBravo.addGameHero(playerTwo);
		
		GameHeroProperties playerThree = new GameHeroProperties(GameHeroInputMap.loadConfigFileFromDevice(HeroInputDevice.JOYSTICK), 2);
		playerThree.setSpawnPosition(new Vec2(50, 18));
		teamCharlie.addGameHero(playerThree);

		teams.add(teamAlpha);
		teams.add(teamBravo);
		teams.add(teamCharlie);
		
		for (HeroTeam heroTeam : teams) 
		{
			heroTeam.setUp();
		}

		Scenery scenery = new Scenery(SceneryFile.loadFromFile(FilenameConstants.getSceneryFilename()));
		
		RoundGameState roundGameState = new RoundGameState(teams, scenery);

		gameMachine.popState();
		gameMachine.addState(roundGameState);
		gameMachine.execute();
	}

	private void popState()
	{
		if (gameStates.size() > 0) gameStates.remove(gameStates.lastElement());
	}

	private void addState(GameState gameState)
	{
		gameState.enterState(renderWindow);
		gameStates.add(gameState);
	}

	public GameMachine()
	{
		
//		for (VideoMode list: VideoMode.getFullscreenModes())
//		{
//			System.out.println(list.toString());
//		}
		
		renderWindow = new RenderWindow(new VideoMode(1366, 768, 32), "High order ninja");//, Window.FULLSCREEN); //Window.TRANSPARENT
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
		Clock clock = new Clock();
		float remainingAcumulator = 0f;
		final float updateDelta = (float)1/FRAME_RATE;
		while (isRunning)
		{
			float iterationTime = clock.restart().asSeconds();
			
		   // max frame time to avoid spiral of death
		    if ( iterationTime > 0.25f )
		    	iterationTime = 0.25f;    
		    
			renderWindow.clear();//new Color(128,  128, 128));
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
