package br.com.guigasgame.gamemachine;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Stack;
import java.util.Vector;

import javax.xml.bind.JAXBException;

import org.jsfml.graphics.RenderWindow;
import org.jsfml.system.Clock;
import org.jsfml.window.Keyboard;
import org.jsfml.window.VideoMode;
import org.jsfml.window.event.Event;

import br.com.guigasgame.color.ColorBlender;
import br.com.guigasgame.file.FilenameConstants;
import br.com.guigasgame.gameobject.hero.attributes.HeroAttributesFile;
import br.com.guigasgame.gameobject.hero.attributes.playable.RoundHeroAttributes;
import br.com.guigasgame.gameobject.hero.input.GameHeroInputMap;
import br.com.guigasgame.gameobject.hero.input.GameHeroInputMap.HeroInputDevice;
import br.com.guigasgame.gameobject.hero.playable.PlayableHeroDefinition;
import br.com.guigasgame.round.RoundProperties;
import br.com.guigasgame.round.hud.RoundHudTopSkin;
import br.com.guigasgame.round.type.DeathMatchRoundType;
import br.com.guigasgame.scenery.creation.SceneryInitialize;
import br.com.guigasgame.scenery.file.SceneryFile;
import br.com.guigasgame.team.TeamIndex;
import br.com.guigasgame.team.TeamsController;


public class GameMachine
{

	public final int FRAME_RATE = 60;
	private boolean isRunning;
	private RenderWindow renderWindow;
	private Vector<GameState> gameStates;
	private ColorBlender backgroundColor;

	public static void main(String[] args) throws Exception
	{
		GameMachine gameMachine = new GameMachine();

		RoundGameState roundGameState = gameMachine.setupRoundState();

		gameMachine.popState();
		gameMachine.addState(roundGameState);
		gameMachine.execute();
	}

	public RoundGameState setupRoundState() throws Exception, JAXBException
	{
		TeamsController teamsController = setupTeams();

		SceneryInitialize scenery = new SceneryInitialize(SceneryFile.loadFromFile(FilenameConstants.getSceneryFilename()));

		RoundHeroAttributes roundHeroAttributes = setupAttributes();
//		teamsController.setRoundConfigurationsUp();
		RoundProperties roundProperties = new RoundProperties(roundHeroAttributes, teamsController, scenery, 10, new RoundHudTopSkin(), new DeathMatchRoundType(5));
		RoundGameState roundGameState = new RoundGameState(roundProperties);
		return roundGameState;
	}

	private static RoundHeroAttributes setupAttributes()
	{
		HeroAttributesFile attributesFile;
		try
		{
			attributesFile = HeroAttributesFile.loadFromFile(FilenameConstants.getHeroAttributesFilename());
		}
		catch (JAXBException e)
		{
			e.printStackTrace();
			attributesFile = new HeroAttributesFile();
		}
		RoundHeroAttributes roundHeroAttributes = new RoundHeroAttributes(attributesFile.getLife(), attributesFile.getShuriken(), attributesFile.getSmokeBomb());
		return roundHeroAttributes;
	}

	private static TeamsController setupTeams()
	{
		try
		{
			TeamsController teamsController = new TeamsController();
			teamsController.addHeroDefinition(new PlayableHeroDefinition(GameHeroInputMap.loadConfigFileFromDevice(HeroInputDevice.JOYSTICK), 0), TeamIndex.ALPHA);

			
			teamsController.addHeroDefinition(new PlayableHeroDefinition(GameHeroInputMap.loadConfigFileFromDevice(HeroInputDevice.JOYSTICK), 1), TeamIndex.ALPHA);
			teamsController.addHeroDefinition(new PlayableHeroDefinition(GameHeroInputMap.loadConfigFileFromDevice(HeroInputDevice.JOYSTICK), 2), TeamIndex.CHARLIE);
			teamsController.addHeroDefinition(new PlayableHeroDefinition(GameHeroInputMap.loadConfigFileFromDevice(HeroInputDevice.KEYBOARD), 3), TeamIndex.DELTA);
			teamsController.addHeroDefinition(new PlayableHeroDefinition(GameHeroInputMap.loadConfigFileFromDevice(HeroInputDevice.JOYSTICK), 4), TeamIndex.CHARLIE);
			teamsController.addHeroDefinition(new PlayableHeroDefinition(GameHeroInputMap.loadConfigFileFromDevice(HeroInputDevice.JOYSTICK), 5), TeamIndex.FOXTROT);
			teamsController.addHeroDefinition(new PlayableHeroDefinition(GameHeroInputMap.loadConfigFileFromDevice(HeroInputDevice.JOYSTICK), 6), TeamIndex.GOLF);
			teamsController.addHeroDefinition(new PlayableHeroDefinition(GameHeroInputMap.loadConfigFileFromDevice(HeroInputDevice.JOYSTICK), 7), TeamIndex.HOTEL);
			teamsController.setTeamsUp();
			return teamsController;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
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

	public GameMachine()
	{

		VideoMode[] modes = VideoMode.getFullscreenModes();
		Arrays.sort(modes, new Comparator<VideoMode>()
		{

			@Override
			public int compare(VideoMode o1, VideoMode o2)
			{
				int retorno = o1.height * o1.width - o2.height * o2.width;
				if (retorno == 0)
					return o1.bitsPerPixel - o2.bitsPerPixel;
				return retorno;
			}

		});
//		for( VideoMode videoMode : modes )
//		{
//			System.out.println(videoMode);
//		}
		final VideoMode best = modes[modes.length - 1];
//		final VideoMode worst = modes[4];

		renderWindow = new RenderWindow(best, "High Order Ninja");//, Window.FULLSCREEN);  //Window.TRANSPARENT
//		renderWindow = new RenderWindow(worst, "High Order Ninja");//, Window.FULLSCREEN);  //Window.TRANSPARENT
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
//		renderWindow.clear();
	}

	private void gameLoop()
	{
		// http://gafferongames.com/game-physics/fix-your-timestep/
		Clock clock = new Clock();
		float remainingAcumulator = 0f;
		final float updateDelta = (float) 1 / FRAME_RATE;
		while (isRunning)
		{
			float iterationTime = clock.restart().asSeconds();

			// max frame time to avoid spiral of death
			if (iterationTime > 0.25f)
				iterationTime = 0.25f;

			renderWindow.clear(backgroundColor.getSfmlColor());
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
