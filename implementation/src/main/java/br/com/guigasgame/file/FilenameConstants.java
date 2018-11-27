package br.com.guigasgame.file;

import br.com.guigasgame.gameobject.hero.input.GameHeroInputMap.HeroInputDevice;
import br.com.guigasgame.math.Randomizer;


public class FilenameConstants
{

	private static String heroFixturesFilename = "HeroFixtures.xml";
	private static String heroStatesFilename = "HeroStates.xml";
	private static String projectilePropertiesFilename = "projectilesProperties.xml";

	public static String getProjectileAnimationFilename()
	{
		return "projectilesAnimation.xml";
	}

	public static String getInputPlayerConfigFilename(int playerID)
	{
		if (playerID == 1)
			return "assets/config/InputConfigFile1.xml";
		else // if (playerID == 0)
			return "assets/config/InputConfigFile2.xml";
	}

	public static String getHeroAnimationFilename()
	{
		return "heroAnimationProperties.xml";
	}

	public static String getHeroFixturesFilename()
	{
		return heroFixturesFilename;
	}

	public static String getHeroStatesFilename()
	{
		return heroStatesFilename;
	}

	public static String getProjectilePropertiesFilename()
	{
		return projectilePropertiesFilename;
	}

	public static String getInputPlayerConfigFromDevice(HeroInputDevice device)
	{
		switch (device)
		{
			case JOYSTICK:
				return "assets/config/defaultJoystickInputConfig.xml";
			case KEYBOARD:
				return "assets/config/defaultKeyboardInputConfig.xml";
			default:
				return "";
		}
	}

	public static String getSceneryFilename()
	{
		switch(Randomizer.getRandomIntInInterval(0, 2))
		{
			case 0:
				return "assets/config/alternativeScene.xml";
			case 1:
				return "assets/config/proScene.xml";
			default: //case 2:
				return "assets/config/jungleArena.xml";
		}
	}

	public static String getItemsPropertiesFilename()
	{
		return "itemsProperties.xml";
	}

	public static String getItemsAnimationPropertiesFilename()
	{
		return "itemsAnimations.xml";
	}

	public static String getHeroAttributesFilename()
	{
		return "heroAttributes.xml";
	}

	public static String getTimerCounterFontFilename()
	{
		return "GOUDYSTO.TTF";
	}

	public static String getFragStatistcsFontFilename()
	{
		return "GOUDYSTO.TTF";
	}

	public static String getRopePieceAnimationFilename()
	{
		return "ropePiece.png";
	}

	public static String getLogoFilename()
	{
		return "assets/graphics/guilogo.png";
	}

}
