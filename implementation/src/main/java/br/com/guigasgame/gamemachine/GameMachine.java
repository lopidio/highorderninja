package br.com.guigasgame.gamemachine;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Stack;
import java.util.Vector;

import javax.xml.bind.JAXBException;

import org.jsfml.graphics.RenderWindow;
import org.jsfml.system.Clock;
import org.jsfml.window.Keyboard;
import org.jsfml.window.VideoMode;
import org.jsfml.window.Window;
import org.jsfml.window.event.Event;

import br.com.guigasgame.file.FilenameConstants;
import br.com.guigasgame.gameobject.hero.attributes.HeroAttribute;
import br.com.guigasgame.gameobject.hero.attributes.HeroShootingAttribute;
import br.com.guigasgame.gameobject.hero.attributes.playable.RoundHeroAttributes;
import br.com.guigasgame.gameobject.hero.input.GameHeroInputMap;
import br.com.guigasgame.gameobject.hero.input.GameHeroInputMap.HeroInputDevice;
import br.com.guigasgame.gameobject.hero.playable.PlayableHeroDefinition;
import br.com.guigasgame.scenery.creation.SceneryCreator;
import br.com.guigasgame.scenery.file.SceneryFile;
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

		RoundGameState roundGameState = setupRoundState();

		gameMachine.popState();
		gameMachine.addState(roundGameState);
		gameMachine.execute();
	}

	private static RoundGameState setupRoundState() throws Exception, JAXBException {
		List<HeroTeam> teams = new ArrayList<>();
		
		setupTeams(teams);

		SceneryCreator scenery = new SceneryCreator(SceneryFile.loadFromFile(FilenameConstants.getSceneryFilename()));
		
		RoundHeroAttributes roundHeroAttributes = setupAttributes();
		RoundGameState roundGameState = new RoundGameState(teams, scenery, roundHeroAttributes );
		return roundGameState;
	}

	private static RoundHeroAttributes setupAttributes() {
		HeroAttribute life = new HeroAttribute(100, 5);
		HeroShootingAttribute shuriken = new HeroShootingAttribute(10, 2, 0);
		HeroShootingAttribute smokeBomb = new HeroShootingAttribute(1, 3, 0.2f);
		RoundHeroAttributes roundHeroAttributes = new RoundHeroAttributes(life, shuriken, smokeBomb);
		return roundHeroAttributes;
	}

	private static void setupTeams(List<HeroTeam> teams) {
		HeroTeam teamAlpha = new HeroTeam(0);
		HeroTeam teamBravo = new HeroTeam(1);
		HeroTeam teamCharlie = new HeroTeam(2);
		HeroTeam teamDelta = new HeroTeam(3);
		
		PlayableHeroDefinition playerOne = new PlayableHeroDefinition(GameHeroInputMap.loadConfigFileFromDevice(HeroInputDevice.JOYSTICK), 0);
		teamAlpha.addGameHero(playerOne);
		
		PlayableHeroDefinition playerTwo = new PlayableHeroDefinition(GameHeroInputMap.loadConfigFileFromDevice(HeroInputDevice.KEYBOARD), 1);
		PlayableHeroDefinition playerThree = new PlayableHeroDefinition(GameHeroInputMap.loadConfigFileFromDevice(HeroInputDevice.JOYSTICK), 2);
		teamBravo.addGameHero(playerTwo);
		teamBravo.addGameHero(playerThree);
		

		PlayableHeroDefinition playerFour = new PlayableHeroDefinition(GameHeroInputMap.loadConfigFileFromDevice(HeroInputDevice.JOYSTICK), 3);
		teamCharlie.addGameHero(playerFour);

		PlayableHeroDefinition playerFive = new PlayableHeroDefinition(GameHeroInputMap.loadConfigFileFromDevice(HeroInputDevice.KEYBOARD), 4);
		teamDelta.addGameHero(playerFive);

		
		teams.add(teamAlpha);
		teams.add(teamBravo);
		teams.add(teamCharlie);
		teams.add(teamDelta);
		
		for (HeroTeam heroTeam : teams) 
		{
			heroTeam.setUp();
		}
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
		
		VideoMode[] modes = VideoMode.getFullscreenModes();
		Arrays.sort(modes, new Comparator<VideoMode>() 
		{
			@Override
			public int compare(VideoMode o1, VideoMode o2) 
			{
				int retorno = o1.height*o1.width - o2.height*o2.width;
				if (retorno == 0)
					return o1.bitsPerPixel - o2.bitsPerPixel;
				return retorno;
			}

		});
		final VideoMode best = modes[modes.length - 1];
		
		renderWindow = new RenderWindow(best, "High order ninja");//, Window.FULLSCREEN); //Window.TRANSPARENT
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
			}
			if (event.type == Event.Type.CLOSED)
			{
				isRunning = false;
			}
		}
	}

}
