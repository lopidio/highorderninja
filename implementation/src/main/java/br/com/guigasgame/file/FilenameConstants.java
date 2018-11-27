package br.com.guigasgame.file;

import br.com.guigasgame.gameobject.hero.input.GameHeroInputMap;
import br.com.guigasgame.math.Randomizer;


public class FilenameConstants
{

	private static String heroStatesFilename = "implementation/assets/config/HeroStates.xml";
	private static String projectilePropertiesFilename = "implementation/assets/config/projectilesProperties.xml";

	public static String getProjectileAnimationFilename()
	{
		return "implementation/assets/config/projectilesAnimation.xml";
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
		return "implementation/assets/config/heroAnimationProperties.xml";
	}

	public static String getHeroStatesFilename()
	{
		return heroStatesFilename;
	}

	public static String getProjectilePropertiesFilename()
	{
		return projectilePropertiesFilename;
	}

	public static String getInputPlayerConfigFromDevice(GameHeroInputMap.HeroInputDevice device)
	{
		switch (device)
		{
			case JOYSTICK:
				return "implementation/assets/config/defaultJoystickInputConfig.xml";
			case KEYBOARD:
				return "implementation/assets/config/defaultKeyboardInputConfig.xml";
			default:
				return "";
		}
	}

	public static String getSceneryFilename()
	{
		switch(Randomizer.getRandomIntInInterval(0, 2))
		{
			case 0:
				return "implementation/assets/config/alternativeScene.xml";
			case 1:
				return "implementation/assets/config/proScene.xml";
			default: //case 2:
				return "implementation/assets/config/jungleArena.xml";
		}
	}

	public static String getItemsPropertiesFilename()
	{
		return "implementation/assets/config/itemsProperties.xml";
	}

	public static String getItemsAnimationPropertiesFilename()
	{
		return "implementation/assets/config/itemsAnimations.xml";
	}

	public static String getHeroAttributesFilename()
	{
		return "implementation/assets/config/heroAttributes.xml";
	}

	public static String getTimerCounterFontFilename()
	{
		return "implementation/assets/graphics/GOUDYSTO.TTF";
	}

	public static String getFragStatistcsFontFilename()
	{
		return "implementation/assets/graphics/GOUDYSTO.TTF";
	}

	public static String getRopePieceAnimationFilename()
	{
		return "implementation/assets/graphics/ropePiece.png";
	}

	public static String getLogoFilename()
	{
		return "implementation/assets/graphics/guilogo.png";
	}

}
