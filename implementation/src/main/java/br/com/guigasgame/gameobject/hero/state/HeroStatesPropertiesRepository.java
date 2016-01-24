package br.com.guigasgame.gameobject.hero.state;

import java.util.Map;

import javax.xml.bind.JAXBException;

import br.com.guigasgame.animation.HeroAnimationsIndex;
import br.com.guigasgame.file.FilenameConstants;

public class HeroStatesPropertiesRepository
{
	private static HeroStatesPropertiesRepository singleton;
	private Map<HeroAnimationsIndex, HeroStatesProperties> statesMap;
	
	static
	{
		singleton = new HeroStatesPropertiesRepository();
	}
	
	
	private HeroStatesPropertiesRepository()
	{
		loadHeroStates();
	}
	
	public static HeroStatesProperties getStateProperties(HeroAnimationsIndex heroAnimationsIndex)
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
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

	}
	
}
