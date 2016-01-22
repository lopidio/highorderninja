package br.com.guigasgame.file;

public class FilenameConstants
{

	private static String inputPlayerConfigFile = "InputConfigFile.xml";
	private static String heroAnimationFilename = "AnimationDefinition.xml";
	private static String heroFixturesFilename = "HeroFixtures.xml";

	public static String getInputPlayerConfigFilename(int playerID)
	{
		return inputPlayerConfigFile;
	}

	public static String getHeroAnimationFilename()
	{
		return heroAnimationFilename;
	}

	public static String getHeroFixturesFilename()
	{
		return heroFixturesFilename;
	}

}
