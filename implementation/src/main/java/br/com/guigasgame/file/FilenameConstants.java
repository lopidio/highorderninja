package br.com.guigasgame.file;


public class FilenameConstants 
{
	private static String inputPlayerConfigFile = "InputConfigFile.xml";
	public static String getInputPlayerConfigFilename(int playerID)
	{
		return inputPlayerConfigFile;
	}
	public static String getInputPlayerAnimationFilename() {
		return "AnimationDefinition.xml";
	}

}
