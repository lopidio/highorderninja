package br.com.guigasgame.animation;

import javax.xml.bind.JAXBException;

import br.com.guigasgame.file.FilenameConstants;


public class AnimationsRepositoryCentral
{

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
