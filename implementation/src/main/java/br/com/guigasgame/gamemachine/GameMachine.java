package br.com.guigasgame.gamemachine;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.Vector;

import org.jsfml.graphics.RenderWindow;
import org.jsfml.system.Clock;
import org.jsfml.window.Keyboard;
import org.jsfml.window.VideoMode;
import org.jsfml.window.event.Event;

import br.com.guigasgame.background.Background;
import br.com.guigasgame.background.BackgroundFile;
import br.com.guigasgame.file.FilenameConstants;
import br.com.guigasgame.gameobject.hero.attributes.HeroAttribute;
import br.com.guigasgame.gameobject.hero.attributes.HeroShootingAttribute;
import br.com.guigasgame.gameobject.hero.attributes.playable.RoundHeroAttributes;
import br.com.guigasgame.gameobject.hero.input.GameHeroInputMap;
import br.com.guigasgame.gameobject.hero.input.GameHeroInputMap.HeroInputDevice;
import br.com.guigasgame.gameobject.hero.playable.PlayableHeroDefinition;
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
		HeroTeam teamDelta = new HeroTeam(23);
		
		PlayableHeroDefinition playerOne = new PlayableHeroDefinition(GameHeroInputMap.loadConfigFileFromDevice(HeroInputDevice.JOYSTICK), 0);
		teamAlpha.addGameHero(playerOne);
		
		PlayableHeroDefinition playerTwo = new PlayableHeroDefinition(GameHeroInputMap.loadConfigFileFromDevice(HeroInputDevice.KEYBOARD), 1);
		PlayableHeroDefinition playerThree = new PlayableHeroDefinition(GameHeroInputMap.loadConfigFileFromDevice(HeroInputDevice.JOYSTICK), 2);
		teamBravo.addGameHero(playerTwo);
		teamBravo.addGameHero(playerThree);
		

		PlayableHeroDefinition playerFour = new PlayableHeroDefinition(GameHeroInputMap.loadConfigFileFromDevice(HeroInputDevice.JOYSTICK), 3);
		teamCharlie.addGameHero(playerFour);

//		PlayableHeroDefinition playerFive = new PlayableHeroDefinition(GameHeroInputMap.loadConfigFileFromDevice(HeroInputDevice.KEYBOARD), 4);
//		teamDelta.addGameHero(playerFive);

		
		teams.add(teamAlpha);
		teams.add(teamBravo);
		teams.add(teamCharlie);
		teams.add(teamDelta);
		
		for (HeroTeam heroTeam : teams) 
		{
			heroTeam.setUp();
		}

		Scenery scenery = new Scenery(SceneryFile.loadFromFile(FilenameConstants.getSceneryFilename()));
		
		Background background = new Background(BackgroundFile.loadFromFile(FilenameConstants.getBackgroundFilename()));
		scenery.setBackground(background);
		
		HeroAttribute life = new HeroAttribute(100, 5);
		HeroShootingAttribute shuriken = new HeroShootingAttribute(10, 2);
		HeroShootingAttribute smokeBomb = new HeroShootingAttribute(5, 5);
		RoundHeroAttributes roundHeroAttributes = new RoundHeroAttributes(life, shuriken, smokeBomb);
		RoundGameState roundGameState = new RoundGameState(teams, scenery, roundHeroAttributes );

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
		renderWindow.setMouseCursorVisible(false);

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
		    
			renderWindow.clear();
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
			gameStates.lastElement().handleEvent(event, renderWindow);
			if (event.type == Event.Type.KEY_PRESSED)
			{
				if (event.asKeyEvent().key == Keyboard.Key.ESCAPE)
				{
					isRunning = false;
					break;
				}

//				if (event.asKeyEvent().key == Key.M)
//				{
//					view.zoom(1.1f);
//					renderWindow.setView(view);
//				}				
//				if (event.asKeyEvent().key == Key.N)
//				{
//					view.zoom(0.9f);
//					renderWindow.setView(view);
//				}				
				
			}
			if (event.type == Event.Type.CLOSED)
			{
				isRunning = false;
			}
		}
	}

}
