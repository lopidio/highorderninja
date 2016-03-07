package br.com.guigasgame.background;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.jsfml.graphics.RenderWindow;
import org.jsfml.graphics.Texture;

import br.com.guigasgame.drawable.Drawable;
import br.com.guigasgame.drawable.TextureDrawable;
import br.com.guigasgame.resourcemanager.TextureResourceManager;
import br.com.guigasgame.updatable.UpdatableFromTime;

public class Background implements UpdatableFromTime
{
	private List<BackgroundGameObject> backgroundGameObjects;
	private List<BackgroundGameObject> foregroundGameObjects;
	private final Texture itemsTexture;
	private final Texture backgroundTexture;
	private TextureDrawable texturePanoramicDrawable;
//	private List<Drawable> drawableList;

	public Background(BackgroundFile backgroundFile)
	{
		backgroundGameObjects = new ArrayList<>();
		foregroundGameObjects = new ArrayList<>();
		itemsTexture = TextureResourceManager.getInstance().getResource(backgroundFile.getItemsTextureFilename());
		itemsTexture.setSmooth(true);
		backgroundTexture = TextureResourceManager.getInstance().getResource(backgroundFile.getBackgroundTextureFilename());
		backgroundTexture.setSmooth(true);
		
		texturePanoramicDrawable = new TextureDrawable(backgroundTexture);
		
		initializeBackgroundItems(backgroundFile);
		sortItems();
	}

	private void sortItems()
	{
		Comparator<BackgroundGameObject> comparator = new Comparator<BackgroundGameObject>()
		{
			@Override
			public int compare(BackgroundGameObject a, BackgroundGameObject b)
			{
				return (int) (b.getDistanceFromCamera() - a.getDistanceFromCamera());
			}
		};
		
		backgroundGameObjects.sort(comparator);
		foregroundGameObjects.sort(comparator);
	}

	private void initializeBackgroundItems(BackgroundFile backgroundFile)
	{
		List<BackgroundFileItem> backgroundItems = backgroundFile.getBackgroundItems();
		for( BackgroundFileItem backgroundItem : backgroundItems )
		{
			BackgroundGameObject backgroundGameObject = new BackgroundGameObject(backgroundItem, itemsTexture);
			if (backgroundGameObject.getDistanceFromCamera() > 0)
				backgroundGameObjects.add(backgroundGameObject);
			else
				foregroundGameObjects.add(backgroundGameObject);
		}
	}
	
	public void drawBackgroundItems(RenderWindow renderWindow)
	{
		texturePanoramicDrawable.draw(renderWindow);
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
		for( BackgroundGameObject gameObject : backgroundGameObjects )
		{
			gameObject.update(deltaTime);
		}
		for( BackgroundGameObject gameObject : foregroundGameObjects )
		{
			gameObject.update(deltaTime);
		}
	}
	
}
