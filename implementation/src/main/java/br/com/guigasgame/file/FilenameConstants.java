package br.com.guigasgame.file;

public class FilenameConstants
{

	private static String inputPlayerConfigFile = "InputConfigFile.xml";
	private static String heroAnimationFilename = "ninjaAnimationProperties.xml";
	private static String heroFixturesFilename = "HeroFixtures.xml";
	private static String heroStatesFilename = "heroStates.xml";
	private static String projectilePropertiesFilename = "projectiles.xml";
	private static String projectileAnimationFilename = "shurikenAnimation.xml";
	
	public static String getProjectileAnimationFilename()
	{
		return projectileAnimationFilename;
	}

	private static String projectileTextureFilename = "shuriken.png";

	public static String getInputPlayerConfigFilename(int playerID)
	{
		return inputPlayerConfigFile;
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
