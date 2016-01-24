package br.com.guigasgame.animation;

import javax.xml.bind.JAXBException;

import br.com.guigasgame.file.FilenameConstants;
import br.com.guigasgame.gameobject.projectile.ProjectileIndex;


public class AnimationsRepositoryCentral
{

	private static AnimationsRepositoryCentral singleton;

	private AnimationPropertiesFile<HeroAnimationsIndex> heroAnimations;
	private AnimationPropertiesFile<ProjectileIndex> projectilesAnimations;

	static
	{
		singleton = new AnimationsRepositoryCentral();
	}

	private AnimationsRepositoryCentral()
	{
		loadHeroAnimations();
		loadProjectileAnimations();
	}

	private static AnimationsRepositoryCentral getInstance()
	{
		return singleton;
	}
	
	public static AnimationPropertiesFile<ProjectileIndex> getProjectileAnimationRepository()
	{
		return getInstance().projectilesAnimations;
	}

	public static AnimationPropertiesFile<HeroAnimationsIndex> getHeroAnimationRepository()
	{
		return getInstance().heroAnimations;
	}

	@SuppressWarnings("unchecked")
	private void loadHeroAnimations()
	{
		try
		{
			heroAnimations = (AnimationPropertiesFile<HeroAnimationsIndex>) AnimationPropertiesFile.loadFromFile(FilenameConstants.getHeroAnimationFilename());

			// Seto a textura de todas as animações
			for( AnimationProperties animation : heroAnimations.getAnimationsMap() )
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

	@SuppressWarnings("unchecked")
	private void loadProjectileAnimations()
	{
		try
		{
			projectilesAnimations = (AnimationPropertiesFile<ProjectileIndex>) AnimationPropertiesFile.loadFromFile(FilenameConstants.getProjectileAnimationFilename());

			// Seto a textura de todas as animações
			for( AnimationProperties animation : projectilesAnimations.getAnimationsMap() )
			{
				animation.setTexture(projectilesAnimations.getSharedTexture());
			}
		}
		catch (JAXBException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
