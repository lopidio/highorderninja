package br.com.guigasgame.scenery.background;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.jsfml.graphics.Texture;

import br.com.guigasgame.resourcemanager.TextureResourceManager;


public class BackgroundCreator
{

	private List<BackgroundGameEntity> backgroundGameObjects;
	private List<BackgroundGameEntity> foregroundGameObjects;

	public BackgroundCreator(BackgroundFile backgroundFile)
	{
		backgroundGameObjects = new ArrayList<>();
		foregroundGameObjects = new ArrayList<>();

		initializeBackgroundItems(backgroundFile);
		sortItems();

	}

	private void sortItems()
	{
		Comparator<BackgroundGameEntity> comparator = new Comparator<BackgroundGameEntity>()
		{

			@Override
			public int compare(BackgroundGameEntity a, BackgroundGameEntity b)
			{
				return (int) (b.getDistanceFromCamera() - a.getDistanceFromCamera());
			}
		};

		backgroundGameObjects.sort(comparator);
		foregroundGameObjects.sort(comparator);
	}

	private void initializeBackgroundItems(BackgroundFile backgroundFile)
	{
		List<BackgroundItemProperties> backgroundItems = backgroundFile.getBackgroundItems();
		for( BackgroundItemProperties backgroundItem : backgroundItems )
		{
			Texture itemTexture = TextureResourceManager.getInstance().getResource(backgroundItem.getTextureFilename());
			itemTexture.setSmooth(true);

			BackgroundGameEntity backgroundGameObject = new BackgroundGameEntity(backgroundItem, itemTexture);
			if (backgroundGameObject.getDistanceFromCamera() > 0)
				backgroundGameObjects.add(backgroundGameObject);
			else
				foregroundGameObjects.add(backgroundGameObject);
		}
	}

	public List<BackgroundGameEntity> getBackgroundGameObjects()
	{
		return backgroundGameObjects;
	}

	public List<BackgroundGameEntity> getForegroundGameObjects()
	{
		return foregroundGameObjects;
	}

	public Background createBackground()
	{
		return new Background(backgroundGameObjects, foregroundGameObjects);
	}

}
