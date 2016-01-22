package br.com.guigasgame.animation;

import javax.xml.bind.JAXBException;
import javax.xml.bind.annotation.XmlEnum;

import br.com.guigasgame.file.FilenameConstants;


public class AnimationsRepositoryCentral
{

	@XmlEnum
	public enum HeroAnimationsIndex
	{
		HERO_STANDING, HERO_CAGANDO
	}

	private static AnimationsRepositoryCentral singleton;

	private AnimationPropertiesFile<HeroAnimationsIndex> heroAnimations;

	static
	{
		singleton = new AnimationsRepositoryCentral();
	}

	private AnimationsRepositoryCentral()
	{
		initializeHeroAnimations();
	}

	private static AnimationsRepositoryCentral getInstance()
	{
		return singleton;
	}

	public static AnimationPropertiesFile<HeroAnimationsIndex> getHeroAnimationRepository()
	{
		return getInstance().heroAnimations;
	}

	@SuppressWarnings("unchecked")
	private void initializeHeroAnimations()
	{
		this.heroAnimations = new AnimationPropertiesFile<HeroAnimationsIndex>(
				FilenameConstants.getHeroAnimationFilename());

		try
		{
			heroAnimations = (AnimationPropertiesFile<HeroAnimationsIndex>) AnimationPropertiesFile
					.loadFromFile(FilenameConstants.getHeroAnimationFilename());

			// Seto a textura de todas as animações
			for( AnimationProperties animation : heroAnimations
					.getAnimationsMap() )
			{
				animation.setTexture(heroAnimations.getSharedTexture());
			}
		}
		catch (JAXBException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
