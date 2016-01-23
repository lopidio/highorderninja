package br.com.guigasgame.animation;

import javax.xml.bind.JAXBException;
import javax.xml.bind.annotation.XmlEnum;

import br.com.guigasgame.file.FilenameConstants;


public class AnimationsRepositoryCentral
{

	@XmlEnum
	public enum HeroAnimationsIndex
	{
		HERO_STANDING,
		HERO_RUNNING,
		HERO_ASCENDING,
		HERO_FALLING,
		HERO_WALLRIDING,
		HERO_WALLGRABBING,
		HERO_SLIDING,
	}

	private static AnimationsRepositoryCentral singleton;

	private AnimationPropertiesFile<HeroAnimationsIndex> heroAnimations;

	static
	{
		singleton = new AnimationsRepositoryCentral();
	}

	private AnimationsRepositoryCentral()
	{
		loadHeroAnimations();
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
	private void loadHeroAnimations()
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
