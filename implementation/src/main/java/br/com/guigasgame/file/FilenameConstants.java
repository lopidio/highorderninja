package br.com.guigasgame.file;

import br.com.guigasgame.gameobject.hero.input.GameHeroInputMap.HeroInputDevice;


public class FilenameConstants
{

	private static String inputPlayerConfigFile1 = "InputConfigFile1.xml";
	private static String inputPlayerConfigFile2 = "InputConfigFile2.xml";

	private static String inputPlayerKeyboard = "defaultKeyboardInputConfig.xml";
	private static String inputPlayerJoystick = "defaultJoystickInputConfig.xml";
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
			return inputPlayerConfigFile1;
		else // if (playerID == 0)
			return inputPlayerConfigFile2;
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
				return inputPlayerJoystick;
			case KEYBOARD:
				return inputPlayerKeyboard;
			default:
				return "";
		}
	}

	public static String getSceneryFilename()
	{
		return "proScene.xml";
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

	public static String getCounterFontFilename()
	{
		return "GOUDYSTO.TTF";
	}

}
