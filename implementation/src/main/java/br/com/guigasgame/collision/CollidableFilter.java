package br.com.guigasgame.collision;

import br.com.guigasgame.collision.CollidableFilterManipulator.CollidableFilterAddingCollision;
import br.com.guigasgame.collision.CollidableFilterManipulator.CollidableFilterRemovingCollision;


public class CollidableFilter 
{
	///What I am
	protected IntegerMask category;

	///What I collide with
	protected IntegerMask collider;

	public CollidableFilter(IntegerMask collidableCategory, IntegerMask collidesWith) 
	{
		category = collidableCategory;
		collider = new IntegerMask(collidesWith.value);
	}

	public CollidableFilter(CollidableCategory category)
	{
		this.category = category.getCategoryMask();
		collider = new IntegerMask();
	}

	public CollidableFilter()
	{
		this.category = new IntegerMask();
		collider = new IntegerMask();
	}

	public CollidableFilter(IntegerMask playerCategory)
	{
		this(playerCategory, new IntegerMask());
	}

	public CollidableFilter clone() 
	{
		return new CollidableFilter(category, new IntegerMask(collider.value));
	}
	
	public boolean matches(IntegerMask collidableCategory) 
	{
		return collider.matches(collidableCategory.value);
	}
	
	public IntegerMask getCategory()
	{
		return category;
	}
	
	public void setCategory(IntegerMask categoryMask)
	{
		category = categoryMask;
	}

	public IntegerMask getCollider()
	{
		return collider;
	}
	
	public CollidableFilterAddingCollision addCollisionWith(CollidableCategory category)
	{
		return new CollidableFilterAddingCollision(this.category, collider.set(category.getCategoryMask().value));
	}
	
	CollidableFilterAddingCollision addCollisionWith(IntegerMask mask)
	{
		return new CollidableFilterAddingCollision(this.category, collider.set(mask.value));
	}

	public CollidableFilter addCollisionWithEveryThing()
	{
		return new CollidableFilter(this.category, new IntegerMask().setAll());
	}
	
	public CollidableFilterRemovingCollision removeCollisionWithEveryThing()
	{
		return new CollidableFilterRemovingCollision(this.category, new IntegerMask());
	}
	
	public CollidableFilterRemovingCollision removeCollisionWith(CollidableCategory category) 
	{
		return new CollidableFilterRemovingCollision(this.category, collider.clear(category.getCategoryMask().value));
	}

	public CollidableFilterRemovingCollision removeCollisionWith(IntegerMask category) 
	{
		return new CollidableFilterRemovingCollision(this.category, collider.clear(category.value));
	}

}
