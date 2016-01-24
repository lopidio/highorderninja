package br.com.guigasgame.gameobject;

import java.util.ArrayList;
import java.util.List;

import org.jbox2d.common.Vec2;

import br.com.guigasgame.collision.Collidable;
import br.com.guigasgame.drawable.Drawable;
import br.com.guigasgame.updatable.UpdatableFromTime;


public abstract class GameObject extends Collidable
		implements UpdatableFromTime, Drawable
{

	protected boolean alive;
	private List<GameObject> childrenToAdd;

	public GameObject(Vec2 position)
	{
		super(position);
		childrenToAdd = new ArrayList<>();
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

	@Override
	public void beginContact(Collidable collidable)
	{
		// Default implementation
	}

	@Override
	public void endContact(Collidable collidable)
	{
		// Default implementation
	}

	public void load()
	{
		// Default implementation
	}

	public void unload()
	{
		// Default implementation
	}

	public void onEnter()
	{
		// Default implementation
	}

	public void onDestoy()
	{
		// Default implementation
	}

	public final void clearChildrenToAdd()
	{
		childrenToAdd.clear();
	}

	public final List<GameObject> getChildrenToAdd()
	{
		return childrenToAdd;
	}

	public final boolean hasChildrenToAdd()
	{
		return !childrenToAdd.isEmpty();
	}

	protected final void addChild(GameObject child)
	{
		childrenToAdd.add(child);
	}

}
