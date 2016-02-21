package br.com.guigasgame.gameobject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.World;

import br.com.guigasgame.collision.Collidable;
import br.com.guigasgame.collision.CollidableContactListener;
import br.com.guigasgame.composite.Composible;
import br.com.guigasgame.drawable.Drawable;
import br.com.guigasgame.updatable.UpdatableFromTime;


public abstract class GameObject implements Composible<GameObject>, CollidableContactListener, UpdatableFromTime
{

	protected Vec2 position;
	protected Collidable collidable;
	protected Drawable drawable;
	protected boolean alive;
	private List<GameObject> children;

	public GameObject()
	{
		children = new ArrayList<>();
		alive = true;
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

	@Override
	public final void addChild(GameObject child)
	{
		children.add(child);
	}

	@Override
	public final boolean hasChildrenToAdd()
	{
		return children.size() > 0;
	}

	@Override
	public final Collection<GameObject> getChildrenList()
	{
		return children;
	}

	@Override
	public final void clearChildrenList()
	{
		children.clear();
	}

	public final Collidable getCollidable()
	{
		return collidable;
	}

	public final Drawable getDrawable()
	{
		return drawable;
	}

	public void attachToWorld(World world)
	{
		if (collidable != null)
		{
			collidable.attachToWorld(world);
		}
	}
}
