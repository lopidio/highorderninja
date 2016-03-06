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

public class Background implements UpdatableFromTime, Drawable
{
	private List<BackgroundGameObject> backgroundGameObjects;
	private final Texture itemsTexture;
	private final Texture backgroundTexture;
	private List<Drawable> drawableList;

	public Background(BackgroundFile backgroundFile)
	{
		backgroundGameObjects = new ArrayList<>();
		itemsTexture = TextureResourceManager.getInstance().getResource(backgroundFile.getItemsTextureFilename());
		itemsTexture.setSmooth(true);
		backgroundTexture = TextureResourceManager.getInstance().getResource(backgroundFile.getBackgroundTextureFilename());
		backgroundTexture.setSmooth(true);
		drawableList = new ArrayList<>();
		drawableList.add(new TextureDrawable(backgroundTexture));
		
		initializeBackgroundItems(backgroundFile);
		sortBackgroundItems();
		addBackgroundItemsToDrawableList();
	}

	private void addBackgroundItemsToDrawableList()
	{
		for( BackgroundGameObject backgroundItem : backgroundGameObjects )
		{
			drawableList.add(backgroundItem);
		}
	}

	private void sortBackgroundItems()
	{
		backgroundGameObjects.sort(new Comparator<BackgroundGameObject>()
		{
			@Override
			public int compare(BackgroundGameObject a, BackgroundGameObject b)
			{
				return (int) (b.getDistanceFromCamera() - a.getDistanceFromCamera());
			}
		});
		
	}

	private void initializeBackgroundItems(BackgroundFile backgroundFile)
	{
		List<BackgroundFileItem> backgroundItems = backgroundFile.getBackgroundItems();
		for( BackgroundFileItem backgroundItem : backgroundItems )
		{
			BackgroundGameObject backgroundGameObject = new BackgroundGameObject(backgroundItem, itemsTexture);
			backgroundGameObjects.add(backgroundGameObject);
		}
	}
	
	@Override
	public void draw(RenderWindow renderWindow)
	{
		for( Drawable drawable : drawableList )
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
	}
	
}
