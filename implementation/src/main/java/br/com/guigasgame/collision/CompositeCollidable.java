package br.com.guigasgame.collision;

import java.util.Collection;

import org.jbox2d.common.Vec2;

import br.com.guigasgame.composite.Composible;


public abstract class CompositeCollidable extends Collidable implements
		Composible<Collidable>
{

	private Collection<Collidable> collidableChild;

	public CompositeCollidable(Vec2 position)
	{
		super(position);
	}

	public CompositeCollidable(Vec2 position, Collection<Collidable> collidableChild)
	{
		super(position);
		this.collidableChild = collidableChild;
	}

	@Override
	public final void addChild(Collidable child)
	{
		collidableChild.add(child);
	}

	@Override
	public final boolean hasChildrenToAdd()
	{
		return collidableChild.size() > 0;
	}

	@Override
	public final Collection<Collidable> getChildrenList()
	{
		return collidableChild;
	}

	@Override
	public final void clearChildrenList()
	{
		collidableChild.clear();
	}

}
