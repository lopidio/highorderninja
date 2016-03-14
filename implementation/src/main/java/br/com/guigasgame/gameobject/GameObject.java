package br.com.guigasgame.gameobject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.jbox2d.dynamics.World;
import org.jsfml.graphics.RenderWindow;

import br.com.guigasgame.collision.Collidable;
import br.com.guigasgame.collision.CollidableContactListener;
import br.com.guigasgame.drawable.Drawable;
import br.com.guigasgame.updatable.UpdatableFromTime;


public abstract class GameObject implements CollidableContactListener, UpdatableFromTime, Drawable
{

	// protected Vec2 position;
	protected List<Collidable> collidableList;
	protected List<Drawable> drawableList;
	protected boolean alive;
	private List<GameObject> children;

	public GameObject()
	{
		children = new ArrayList<>();
		alive = true;
		drawableList = new ArrayList<Drawable>();
		collidableList = new ArrayList<Collidable>();
	}

	public final boolean isDead()
	{
		return !alive;
	}

	public final void markToDestroy()
	{
		alive = false;
	}

	public void onEnter()
	{
		// Default implementation
	}

	public void onDestroy()
	{
		// Default implementation
	}

	public final void addChild(GameObject child)
	{
		children.add(child);
	}

	public final boolean hasChildrenToAdd()
	{
		return children.size() > 0;
	}

	public final Collection<GameObject> getChildrenList()
	{
		return children;
	}

	public final void clearChildrenList()
	{
		children.clear();
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
}
