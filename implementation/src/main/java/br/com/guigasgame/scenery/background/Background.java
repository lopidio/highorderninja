package br.com.guigasgame.scenery.background;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.jsfml.graphics.RenderWindow;
import org.jsfml.graphics.Texture;

import br.com.guigasgame.drawable.Drawable;
import br.com.guigasgame.resourcemanager.TextureResourceManager;
import br.com.guigasgame.updatable.UpdatableFromTime;

public class Background implements UpdatableFromTime
{
	private List<BackgroundGameEntity> backgroundGameObjects;
	private List<BackgroundGameEntity> foregroundGameObjects;

	public Background(BackgroundFile backgroundFile)
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
			Texture itemTexture =  TextureResourceManager.getInstance().getResource(backgroundItem.getTextureFilename());
			itemTexture.setSmooth(true);
			
			BackgroundGameEntity backgroundGameObject = new BackgroundGameEntity(backgroundItem, itemTexture);
			if (backgroundGameObject.getDistanceFromCamera() > 0)
				backgroundGameObjects.add(backgroundGameObject);
			else
				foregroundGameObjects.add(backgroundGameObject);
		}
	}
	
	public void drawBackgroundItems(RenderWindow renderWindow)
	{
		for( Drawable drawable : backgroundGameObjects )
		{
			drawable.draw(renderWindow);
		}
	}

	public void drawForegroundItems(RenderWindow renderWindow)
	{
		for( Drawable drawable : foregroundGameObjects )
		{
			drawable.draw(renderWindow);
		}
	}

	@Override
	public void update(float deltaTime)
	{
		for( BackgroundGameEntity gameObject : backgroundGameObjects )
		{
			gameObject.update(deltaTime);
		}
		for( BackgroundGameEntity gameObject : foregroundGameObjects )
		{
			gameObject.update(deltaTime);
		}
	}
	
}
