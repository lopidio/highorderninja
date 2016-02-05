package br.com.guigasgame.file;

public class FilenameConstants
{

	private static String inputPlayerConfigFile1 = "newInputHandlers.xml";
	private static String inputPlayerConfigFile2 = "InputConfigFile1.xml";
	private static String heroAnimationFilename = "ninjaAnimationProperties.xml";
	private static String heroFixturesFilename = "HeroFixtures.xml";
	private static String heroStatesFilename = "statePrototype.xml";
	private static String projectilePropertiesFilename = "projectiles.xml";
	private static String projectileAnimationFilename = "shurikenAnimation.xml";
	private static String projectileTextureFilename = "shuriken.png";
	
	public static String getProjectileAnimationFilename()
	{
		return projectileAnimationFilename;
	}

	public static String getInputPlayerConfigFilename(int playerID)
	{
		if (playerID == 1)
			return inputPlayerConfigFile1;
		else //if (playerID == 0)
			return inputPlayerConfigFile2;
	}

	public static String getHeroAnimationFilename()
	{
		return heroAnimationFilename;
	}
	public static String getProjectileTextureFilename()
	{
		return projectileTextureFilename;
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

}
