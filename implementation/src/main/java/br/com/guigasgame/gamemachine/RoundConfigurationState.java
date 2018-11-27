package br.com.guigasgame.gamemachine;

import javax.xml.bind.JAXBException;

import org.jsfml.graphics.RenderWindow;

import br.com.guigasgame.file.FilenameConstants;
import br.com.guigasgame.gameobject.hero.attributes.HeroAttributesFile;
import br.com.guigasgame.gameobject.hero.attributes.playable.RoundHeroAttributes;
import br.com.guigasgame.gameobject.hero.input.GameHeroInputMap;
import br.com.guigasgame.gameobject.hero.input.GameHeroInputMap.HeroInputDevice;
import br.com.guigasgame.gameobject.hero.playable.PlayableHeroDefinition;
import br.com.guigasgame.round.RoundProperties;
import br.com.guigasgame.round.hud.RoundHudTopSkin;
import br.com.guigasgame.round.type.DeathMatchRoundMode;
import br.com.guigasgame.scenery.creation.SceneryInitialize;
import br.com.guigasgame.scenery.file.SceneryFile;
import br.com.guigasgame.team.TeamIndex;
import br.com.guigasgame.team.TeamsController;

public class RoundConfigurationState implements GameState
{
	private RoundGameState roundGameState;

	public RoundConfigurationState()
	{
		 try
		{
			roundGameState = setupRoundState();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	public RoundGameState setupRoundState() throws Exception, JAXBException
	{
		TeamsController teamsController = setupTeams();

		SceneryInitialize scenery = new SceneryInitialize(SceneryFile.loadFromFile(FilenameConstants.getSceneryFilename()));

		RoundHeroAttributes roundHeroAttributes = setupAttributes();
//		teamsController.setRoundConfigurationsUp();
		RoundProperties roundProperties = new RoundProperties(roundHeroAttributes, teamsController, scenery, 10, new RoundHudTopSkin(), new DeathMatchRoundMode(2, 3, 3));
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
			teamsController.addHeroDefinition(new PlayableHeroDefinition(GameHeroInputMap.loadConfigFileFromDevice(HeroInputDevice.JOYSTICK), 1), TeamIndex.BRAVO);
			teamsController.addHeroDefinition(new PlayableHeroDefinition(GameHeroInputMap.loadConfigFileFromDevice(HeroInputDevice.JOYSTICK), 2), TeamIndex.CHARLIE);
			teamsController.addHeroDefinition(new PlayableHeroDefinition(GameHeroInputMap.loadConfigFileFromDevice(HeroInputDevice.KEYBOARD), 3), TeamIndex.DELTA);
//			teamsController.addHeroDefinition(new PlayableHeroDefinition(GameHeroInputMap.loadConfigFileFromDevice(HeroInputDevice.JOYSTICK), 4), TeamIndex.ECHO);
//			teamsController.addHeroDefinition(new PlayableHeroDefinition(GameHeroInputMap.loadConfigFileFromDevice(HeroInputDevice.JOYSTICK), 5), TeamIndex.FOXTROT);
//			teamsController.addHeroDefinition(new PlayableHeroDefinition(GameHeroInputMap.loadConfigFileFromDevice(HeroInputDevice.JOYSTICK), 6), TeamIndex.GOLF);
//			teamsController.addHeroDefinition(new PlayableHeroDefinition(GameHeroInputMap.loadConfigFileFromDevice(HeroInputDevice.JOYSTICK), 7), TeamIndex.HOTEL);
			teamsController.setTeamsUp();
			return teamsController;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}


	@Override
	public void update(float updateDelta)
	{
		GameMachine.getInstance().switchState(roundGameState);
	}

	@Override
	public void draw(RenderWindow renderWindow)
	{

	}
}
