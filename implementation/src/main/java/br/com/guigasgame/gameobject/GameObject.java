package br.com.guigasgame.gameobject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.jbox2d.dynamics.World;
import org.jsfml.graphics.RenderWindow;
import org.jsfml.system.Vector2f;

import br.com.guigasgame.box2d.debug.WorldConstants;
import br.com.guigasgame.collision.Collidable;
import br.com.guigasgame.collision.CollidableContactListener;
import br.com.guigasgame.destroyable.Destroyable;
import br.com.guigasgame.drawable.Drawable;
import br.com.guigasgame.reproductable.Reproductable;
import br.com.guigasgame.reproductable.ReproductableList;
import br.com.guigasgame.updatable.UpdatableFromTime;


public abstract class GameObject implements CollidableContactListener, UpdatableFromTime, Drawable, Destroyable, Reproductable
{

	// protected Vec2 position;
	protected List<Collidable> collidableList;
	protected List<Drawable> drawableList;
	protected boolean alive;
	private ReproductableList reproductableList;

	public GameObject()
	{
		reproductableList = new ReproductableList();
		alive = true;
		drawableList = new ArrayList<Drawable>();
		collidableList = new ArrayList<Collidable>();
	}

	@Override
	public final boolean isMarkedToDestroy()
	{
		return !alive;
	}

	@Override
	public void destroy()
	{
		for (Collidable collidable : collidableList) 
		{
			collidable.getBody().getWorld().destroyBody(collidable.getBody());
		}

		onDestroy();
	}
	
	@Override
	public final void markToDestroy()
	{
		alive = false;
	}

	public void onEnter()
	{
		// Default implementation
	}

	protected void onDestroy()
	{
		// Default implementation
	}

	@Override
	public <T extends Reproductable> void addChild(T child)
	{
		reproductableList.addChild(child);
	}
	
	@Override
	public final boolean hasChildrenToAdd()
	{
		return reproductableList.hasChildrenToAdd();
	}

	@SuppressWarnings("unchecked")
	public final Collection<GameObject> getChildrenList()
	{
		return reproductableList.getChildrenList();
	}

	@Override
	public final void clearChildrenList()
	{
		reproductableList.clearChildrenList();
	}

	public final List<Collidable> getCollidable()
	{
		return collidableList;
	}

	@Override
	public void draw(RenderWindow renderWindow)
	{
		for( Drawable drawable : drawableList )
		{
			drawable.draw(renderWindow);
		}
	}

	public void attachToWorld(World world)
	{
		for( Collidable collidable : collidableList )
		{
			collidable.attachToWorld(world);
		}
	}

	public void checkAgainstSceneryBoundaries(org.jsfml.graphics.FloatRect floatRect)
	{
		for( Collidable collidable : collidableList )
		{
			final Vector2f position = WorldConstants.physicsToSfmlCoordinates(collidable.getPosition());
			if (!floatRect.contains(position))
			{
				System.out.println("Game object out of scenery");
				gotOutOfScenery();
				markToDestroy();
				return;
			}
		}
	}

	protected void gotOutOfScenery()
	{
		//hook method
	}
}
