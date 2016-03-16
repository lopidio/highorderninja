package br.com.guigasgame.scenery.background;

import java.util.List;

import org.jsfml.graphics.RenderWindow;

import br.com.guigasgame.drawable.Drawable;
import br.com.guigasgame.updatable.UpdatableFromTime;


public class Background implements UpdatableFromTime
{

	private List<BackgroundGameEntity> backgroundGameObjects;
	private List<BackgroundGameEntity> foregroundGameObjects;

	public Background(List<BackgroundGameEntity> backgroundGameObjects, List<BackgroundGameEntity> foregroundGameObjects)
	{
		this.backgroundGameObjects = backgroundGameObjects;
		this.foregroundGameObjects = foregroundGameObjects;
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
