package br.com.guigasgame.gameobject.hero.state;

import java.util.Map;

import javax.xml.bind.JAXBException;

import br.com.guigasgame.file.FilenameConstants;

public class HeroStatesPropertiesPool
{
	private static HeroStatesPropertiesPool singleton;
	private Map<HeroStateIndex, HeroStateProperties> statesMap;
	
	static
	{
		singleton = new HeroStatesPropertiesPool();
	}
	
	
	private HeroStatesPropertiesPool()
	{
		loadHeroStates();
	}
	
	public static HeroStateProperties getStateProperties(HeroStateIndex heroAnimationsIndex)
	{
		return singleton.statesMap.get(heroAnimationsIndex);
	}
	
	private void loadHeroStates()
	{
		HeroStatePropertiesFile heroStatePropertiesFile;
		try
		{
			heroStatePropertiesFile = HeroStatePropertiesFile.loadFromFile(FilenameConstants.getHeroStatesFilename());
			statesMap = heroStatePropertiesFile.getStatesMap();
		}
		catch (JAXBException e1)
		{
			e1.printStackTrace();
		}

	}
	
}
