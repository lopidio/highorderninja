package br.com.guigasgame.animation;

import javax.xml.bind.JAXBException;

import br.com.guigasgame.file.FilenameConstants;
import br.com.guigasgame.gameobject.hero.state.HeroStateIndex;
import br.com.guigasgame.gameobject.item.GameItemIndex;
import br.com.guigasgame.gameobject.projectile.ProjectileIndex;


public class AnimationsCentralPool
{

	private static AnimationsCentralPool singleton;

	private AnimationPropertiesFile<GameItemIndex> gameItemAnimations;
	private AnimationPropertiesFile<HeroStateIndex> heroAnimations;
	private AnimationPropertiesFile<ProjectileIndex> projectilesAnimations;

	static
	{
		singleton = new AnimationsCentralPool();
	}

	private AnimationsCentralPool()
	{
		loadHeroAnimations();
		loadGameItemAnimations();
		loadProjectileAnimations();
	}

	private static AnimationsCentralPool getInstance()
	{
		return singleton;
	}
	
	public static AnimationPropertiesFile<GameItemIndex> getGameItemsAnimationRepository()
	{
		return getInstance().gameItemAnimations;
	}

	public static AnimationPropertiesFile<ProjectileIndex> getProjectileAnimationRepository()
	{
		return getInstance().projectilesAnimations;
	}

	public static AnimationPropertiesFile<HeroStateIndex> getHeroAnimationRepository()
	{
		return getInstance().heroAnimations;
	}
	

	@SuppressWarnings("unchecked")
	private void loadGameItemAnimations()
	{
		try
		{
			gameItemAnimations = (AnimationPropertiesFile<GameItemIndex>) AnimationPropertiesFile.loadFromFile(FilenameConstants.getItemsAnimationPropertiesFilename());
		}
		catch (JAXBException e)
		{
			e.printStackTrace();
		}
	}


	@SuppressWarnings("unchecked")
	private void loadHeroAnimations()
	{
		try
		{
			heroAnimations = (AnimationPropertiesFile<HeroStateIndex>) AnimationPropertiesFile.loadFromFile(FilenameConstants.getHeroAnimationFilename());
		}
		catch (JAXBException e)
		{
			e.printStackTrace();
		}

	}

	@SuppressWarnings("unchecked")
	private void loadProjectileAnimations()
	{
		try
		{
			projectilesAnimations = (AnimationPropertiesFile<ProjectileIndex>) AnimationPropertiesFile.loadFromFile(FilenameConstants.getProjectileAnimationFilename());
		}
		catch (JAXBException e)
		{
			e.printStackTrace();
		}
	}
}
