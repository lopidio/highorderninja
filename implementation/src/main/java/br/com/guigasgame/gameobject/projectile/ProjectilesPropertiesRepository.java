package br.com.guigasgame.gameobject.projectile;

import java.util.Map;

import javax.xml.bind.JAXBException;

import br.com.guigasgame.file.FilenameConstants;


public class ProjectilesPropertiesRepository
{

	private Map<ProjectileIndex, ProjectileProperties> projectilePropertiesMap;
	private static ProjectilesPropertiesRepository singleton;

	static
	{
		singleton = new ProjectilesPropertiesRepository();
	}

	private ProjectilesPropertiesRepository()
	{
		loadProjectilesPropertiesStates();
	}

	public static ProjectileProperties getProjectileProperties(
			ProjectileIndex projectileIndex)
	{
		return singleton.projectilePropertiesMap.get(projectileIndex);
	}

	private void loadProjectilesPropertiesStates()
	{
		try
		{
			ProjectilePropertiesFile heroStatePropertiesFile = ProjectilePropertiesFile.loadFromFile(FilenameConstants.getProjectilePropertiesFilename());
			projectilePropertiesMap = heroStatePropertiesFile.getProjectileMap();
		}
		catch (JAXBException e1)
		{
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

}
